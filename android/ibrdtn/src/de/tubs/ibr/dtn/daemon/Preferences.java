/*
 * Preferences.java
 * 
 * Copyright (C) 2011 IBR, TU Braunschweig
 *
 * Written-by: Johannes Morgenroth <morgenroth@ibr.cs.tu-bs.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package de.tubs.ibr.dtn.daemon;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import de.tubs.ibr.dtn.DTNService;
import de.tubs.ibr.dtn.R;
import de.tubs.ibr.dtn.service.DaemonService;
import de.tubs.ibr.dtn.service.DaemonService.LocalDTNService;
import de.tubs.ibr.dtn.service.P2pManager;
import de.tubs.ibr.dtn.stats.CollectorService;

public class Preferences extends PreferenceActivity {
	
	private static final String TAG = "Preferences";
	
	public static final String KEY_ENABLED = "enabledSwitch";
	public static final String KEY_ENDPOINT_ID = "endpoint_id";
	public static final String KEY_DISCOVERY_MODE = "discovery_mode";
	public static final String KEY_P2P_ENABLED = "p2p_enabled";

	// These preferences show their value as summary
	private final static String[] mSummaryPrefs = {
		KEY_ENDPOINT_ID, "routing", "security_mode", "log_options", "log_debug_verbosity",
			"timesync_mode", "storage_mode", "uplink_mode", KEY_DISCOVERY_MODE
	};

	private Boolean mBound = false;
	private DTNService mService = null;
	private DaemonService mDaemon = null;

	private Switch actionBarSwitch = null;
	private CheckBoxPreference checkBoxPreference = null;
	private InterfacePreferenceCategory mInterfacePreference = null;
	private SwitchPreference mP2pSwitch = null;

	private ServiceConnection mConnection = new ServiceConnection() {
		@SuppressLint("NewApi")
		public void onServiceConnected(ComponentName name, IBinder service) {
			Preferences.this.mService = DTNService.Stub.asInterface(service);
			
			// get direct access to the daemon
			mDaemon = ((LocalDTNService)service).getLocal();
			
			if (Log.isLoggable(TAG, Log.DEBUG))
				Log.d(TAG, "service connected");

			// get the daemon version
			try {
				String version[] = Preferences.this.mService.getVersion();
				setVersion("dtnd: " + version[0] + ", build: " + version[1]);
			} catch (RemoteException e) {
				Log.e(TAG, "Can not query the daemon version", e);
			}
			
			// enable / disable P2P switch
			if (mP2pSwitch != null) {
				mP2pSwitch.setEnabled(mDaemon.isP2pActive());
			}
		}

		public void onServiceDisconnected(ComponentName name) {
			if (Log.isLoggable(TAG, Log.DEBUG))
				Log.d(TAG, "service disconnected");
			mService = null;
		}
	};

	public static void showStatisticLoggerDialog(final Activity activity) {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
				PreferenceActivity prefactivity = (PreferenceActivity) activity;

				@SuppressWarnings("deprecation")
				CheckBoxPreference cb = (CheckBoxPreference) prefactivity
						.findPreference("collect_stats");

				switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						prefs.edit().putBoolean("collect_stats", true)
								.putBoolean("collect_stats_initialized", true).commit();
						cb.setChecked(true);
						break;

					case DialogInterface.BUTTON_NEGATIVE:
						prefs.edit().putBoolean("collect_stats", false)
								.putBoolean("collect_stats_initialized", true).commit();
						cb.setChecked(false);
						break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(R.string.alert_statistic_logger_title);
		builder.setMessage(activity.getResources()
				.getString(R.string.alert_statistic_logger_dialog));
		builder.setPositiveButton(activity.getResources().getString(android.R.string.yes),
				dialogClickListener);
		builder.setNegativeButton(activity.getResources().getString(android.R.string.no),
				dialogClickListener);
		builder.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);

		if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
			menu.findItem(R.id.itemSendDataNow).setVisible(true);
		} else {
			menu.findItem(R.id.itemSendDataNow).setVisible(false);
		}

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.itemShowLog: {
				Intent i = new Intent(Preferences.this, LogActivity.class);
				startActivity(i);
				return true;
			}

			case R.id.itemClearStorage: {
				Intent i = new Intent(Preferences.this, DaemonService.class);
				i.setAction(DaemonService.ACTION_CLEAR_STORAGE);
				startService(i);
				return true;
			}

			case R.id.itemSendDataNow: {
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
				Calendar now = Calendar.getInstance();
				prefs.edit().putLong("stats_timestamp", now.getTimeInMillis()).commit();

				// open activity
				Intent i = new Intent(this, CollectorService.class);
				i.setAction(CollectorService.DELIVER_DATA);
				startService(i);
				return true;
			}

			case R.id.itemApps: {
				Intent i = new Intent(Preferences.this, AppListActivity.class);
				startActivity(i);
				return true;
			}

			case R.id.itemNeighbors: {
				// open neighbor list activity
				Intent i = new Intent(Preferences.this, NeighborActivity.class);
				startActivity(i);
				return true;
			}

			case R.id.itemStats: {
				// open statistic activity
				Intent i = new Intent(Preferences.this, StatsActivity.class);
				startActivity(i);
				return true;
			}

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public static void initializeDefaultPreferences(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

		if (prefs.getBoolean("initialized", false))
			return;

		Editor e = prefs.edit();
		e.putString(KEY_ENDPOINT_ID, Preferences.getEndpoint(context).toString());

		// set preferences to initialized
		e.putBoolean("initialized", true);

		e.commit();
	}

	@TargetApi(14)
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set default preference values
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

		// initialize default values if configured set already
		initializeDefaultPreferences(this);

		addPreferencesFromResource(R.xml.preferences);

		mInterfacePreference = (InterfacePreferenceCategory) findPreference("prefcat_interfaces");

		// connect daemon controls
		checkBoxPreference = (CheckBoxPreference) findPreference(KEY_ENABLED);
		if (checkBoxPreference == null) {
			// use custom actionbar switch
			actionBarSwitch = new Switch(this);

			final int padding = this.getResources().getDimensionPixelSize(
					R.dimen.action_bar_switch_padding);
			actionBarSwitch.setPadding(0, 0, padding, 0);
			this.getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
					ActionBar.DISPLAY_SHOW_CUSTOM);
			this.getActionBar().setCustomView(actionBarSwitch, new ActionBar.LayoutParams(
					ActionBar.LayoutParams.WRAP_CONTENT,
					ActionBar.LayoutParams.WRAP_CONTENT,
					Gravity.CENTER_VERTICAL | Gravity.RIGHT));

			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(Preferences.this);

			// read initial state of the switch
			actionBarSwitch.setChecked(prefs.getBoolean(KEY_ENABLED, true));

			actionBarSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton arg0, boolean val) {

					if (val) {
						// set KEY_ENABLED preference to true
						SharedPreferences prefs = PreferenceManager
								.getDefaultSharedPreferences(Preferences.this);
						prefs.edit().putBoolean(KEY_ENABLED, true).commit();
					}
					else
					{
						// set KEY_ENABLED preference to false
						SharedPreferences prefs = PreferenceManager
								.getDefaultSharedPreferences(Preferences.this);
						prefs.edit().putBoolean(KEY_ENABLED, false).commit();
					}
				}
			});
		}

		// set initial version
		setVersion(null);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			// hide P2p control
			mP2pSwitch = (SwitchPreference)findPreference(KEY_P2P_ENABLED);
			mP2pSwitch.setEnabled(false);
		}

		// Bind the summaries of EditText/List/Dialog/Ringtone preferences to
		// their values. When their values change, their summaries are updated
		// to reflect the new value, per the Android Design guidelines.
		for (String prefKey : mSummaryPrefs) {
			bindPreferenceSummaryToValue(findPreference(prefKey));
		}
		
		// register to preference changes
		PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(mOnOffSwitchListener);
	}

	@Override
	public void onDestroy() {
		// unregister to preference changes
		PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(mOnOffSwitchListener);
		
		if (mBound) {
			// Detach our existing connection.
			unbindService(mConnection);
			mBound = false;
		}

		super.onDestroy();
	}

	@SuppressWarnings("deprecation")
	private void setVersion(String versionValue) {
		// version information
		Preference version = findPreference("system_version");
		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
			if (versionValue == null) {
				version.setSummary("app: " + info.versionName);
			} else {
				version.setSummary("app: " + info.versionName + ", " + versionValue);
			}
		} catch (NameNotFoundException e) {
		}
		;
	}

	@Override
	protected void onPause() {
		super.onPause();

		unregisterReceiver(mNetworkConditionListener);
		unregisterReceiver(mP2pConditionListener);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onResume() {
		if (!mBound) {
			// Establish a connection with the service. We use an explicit
			// class name because we want a specific service implementation that
			// we know will be running in our own process (and thus won't be
			// supporting component replacement by other applications).
			bindService(new Intent(Preferences.this,
					DaemonService.class), mConnection, Context.BIND_AUTO_CREATE);
			mBound = true;
		}

		super.onResume();

		// on first startup ask for permissions to collect statistical data
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Preferences.this);
		if (!prefs.getBoolean("collect_stats_initialized", false)) {
			showStatisticLoggerDialog(Preferences.this);
		}

		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		registerReceiver(mNetworkConditionListener, filter);
		
		IntentFilter p2p_filter = new IntentFilter(P2pManager.INTENT_P2P_STATE_CHANGED);
		registerReceiver(mP2pConditionListener, p2p_filter);
		
		// enable / disable P2P switch
		if ((mP2pSwitch != null) && (mDaemon != null)) {
			mP2pSwitch.setEnabled(mDaemon.isP2pActive());
		}

		mInterfacePreference.updateInterfaceList();
	}

	private BroadcastReceiver mNetworkConditionListener = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mInterfacePreference.updateInterfaceList();
				}
			});
		}
	};
	
	private BroadcastReceiver mP2pConditionListener = new BroadcastReceiver() {
		@SuppressLint("NewApi")
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "p2p changed");
			if (mP2pSwitch == null) return;
			
			// enable / disable P2P switch
			mP2pSwitch.setEnabled(intent.getBooleanExtra(P2pManager.EXTRA_P2P_STATE, false));
		}
	};
	
	private SharedPreferences.OnSharedPreferenceChangeListener mOnOffSwitchListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
		@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			if (KEY_ENABLED.equals(key)) {
				// update switch state
				if (checkBoxPreference == null) {
					actionBarSwitch.setChecked(sharedPreferences.getBoolean(KEY_ENABLED, true));
				} else {
					checkBoxPreference.setChecked(sharedPreferences.getBoolean(KEY_ENABLED, true));
				}
			}
		}
	};

	/**
	 * A preference value change listener that updates the preference's summary
	 * to reflect its new value.
	 */
	private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
		@Override
		public boolean onPreferenceChange(Preference preference, Object value) {
			boolean ret = true;
			String stringValue = value.toString();

			for (String prefKey : mSummaryPrefs) {
				if (prefKey.equals(preference.getKey())) {
					if (Preferences.KEY_ENDPOINT_ID.equals(prefKey)) {
						// exception for "endpoint_id", it shows the endpoint
						if ("dtn".equals( stringValue )) {
							preference.setSummary( getDefaultEndpoint(preference.getContext(), "dtn") );
						}
						else if ("ipn".equals( stringValue )) {
							preference.setSummary( getDefaultEndpoint(preference.getContext(), "ipn") );
						}
						else {
							preference.setSummary( stringValue );
						}
					} else if (preference instanceof ListPreference) {
						// For list preferences, look up the correct display
						// value in
						// the preference's 'entries' list.
						ListPreference listPreference = (ListPreference) preference;
						int index = listPreference.findIndexOfValue(stringValue);

						// Set the summary to reflect the new value.
						preference.setSummary(
								index >= 0
										? listPreference.getEntries()[index]
										: null);

					} else {
						// For all other preferences, set the summary to the
						// value's
						// simple string representation.
						preference.setSummary(stringValue);
					}
					return ret;
				}
			}

			return ret;
		}
	};

	/**
	 * Binds a preference's summary to its value. More specifically, when the
	 * preference's value is changed, its summary (line of text below the
	 * preference title) is updated to reflect the value. The summary is also
	 * immediately updated upon calling this method. The exact display format is
	 * dependent on the type of preference.
	 * 
	 * @see #sBindPreferenceSummaryToValueListener
	 */
	private static void bindPreferenceSummaryToValue(Preference preference) {
		// Set the listener to watch for value changes.
		preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

		// Trigger the listener immediately with the preference's
		// current value.
		sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
				PreferenceManager
						.getDefaultSharedPreferences(preference.getContext())
						.getString(preference.getKey(), ""));
	}
	
	public static String getDefaultEndpoint(Context context, String scheme) {
		final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		MessageDigest md;
		
		String dtnId;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(androidId.getBytes());
			dtnId = "android-" + toHex(digest).substring(4, 12);
		} catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "md5 not available");
			dtnId = "android-" + androidId.substring(4, 12);
		}
		
		if ("ipn".equals(scheme)) {
			Long number = 0L;
			try {
				md = MessageDigest.getInstance("MD5");
				byte[] digest = md.digest(dtnId.getBytes());
				number += ByteBuffer.wrap(digest).getInt() & 0x7fffffff;
			} catch (NoSuchAlgorithmException e) {
				Log.e(TAG, "md5 not available");
				number += ByteBuffer.wrap(androidId.getBytes()).getInt() & 0x7fffffff;
			}
			number |= 0x80000000L;
			return "ipn:" + number;
		}
		else {
			return "dtn://" + dtnId + ".dtn";
		}
	}
	
	public static String getEndpoint(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		if (prefs.contains(KEY_ENDPOINT_ID)) {
			String endpointValue = prefs.getString(KEY_ENDPOINT_ID, "dtn");
			if ("dtn".equals( endpointValue )) {
				return getDefaultEndpoint(context, "dtn");
			}
			else if ("ipn".equals( endpointValue )) {
				return getDefaultEndpoint(context, "ipn");
			}
			else {
				return endpointValue;
			}
		}
		
		return getDefaultEndpoint(context, "dtn");
	}
	
	/**
	 * Create Hex String from byte array
	 * 
	 * @param data
	 * @return
	 */
	private static String toHex(byte[] data)
	{
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < data.length; i++)
			hexString.append(Integer.toHexString(0xFF & data[i]));
		return hexString.toString();
	}
}
