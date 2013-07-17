package ibrdtn.example.ui;

import ibrdtn.example.api.APIHandlerType;
import ibrdtn.example.api.PayloadType;
import ibrdtn.api.APIException;
import ibrdtn.api.ExtendedClient.Encoding;
import ibrdtn.api.object.Bundle;
import ibrdtn.api.object.EID;
import ibrdtn.api.object.GroupEndpoint;
import ibrdtn.api.object.PayloadBlock;
import ibrdtn.api.object.SingletonEndpoint;
import ibrdtn.example.data.MessageData;
import ibrdtn.example.api.Constants;
import ibrdtn.example.api.DTNClient;
import ibrdtn.example.logging.WindowHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.text.DefaultCaret;

/**
 * An application demonstrating the use of the IBR-DTN API.
 *
 * @author Julian Timpner <timpner@ibr.cs.tu-bs.de>
 */
public class DTNExampleApp extends javax.swing.JFrame {

    private static final Logger logger = LogManager.getLogManager().getLogger("");
    public static boolean isAutoResponse = false;
    private DTNClient dtnClient;
    private WindowHandler handler = null;
    protected String PRIMARY_EID = "ibr-1";
    protected PayloadType PAYLOAD_TYPE = PayloadType.BYTE;
    protected APIHandlerType HANDLER_TYPE = APIHandlerType.PASSTHROUGH;

    /**
     * Creates a new DTN demonstration app.
     */
    public DTNExampleApp() {
        initComponents();

        // Set logging text area to auto-scroll.
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        // Append a window logger to the global logger instance. Thus, all log messages can be print on the GUI.
        handler = WindowHandler.getInstance(this);
        logger.addHandler(handler);

        // Init connection to daemon
        dtnClient = new DTNClient(PRIMARY_EID, PAYLOAD_TYPE, HANDLER_TYPE);

        logger.log(Level.INFO, dtnClient.getConfiguration());

        // Set destination address
        try {
            tfDestination.setText(dtnClient.getEC().getNodeName() + "/echo");
        } catch (APIException ex) {
            logger.log(Level.SEVERE, "Retrieving DTN configuration parameters failed");
            tfDestination.setText("dtn://mynode/echo");
        }

        //        Runtime.getRuntime().addShutdownHook(new Thread() {
        //            @Override
        //            public void run() {
        //                dtnClient.shutdown();
        //            }
        //        });
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        bgDestination = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tfDestination = new javax.swing.JTextField();
        rbUnicast = new javax.swing.JRadioButton();
        rbMulticast = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        cbPriority = new javax.swing.JComboBox();
        cbCustody = new javax.swing.JCheckBox();
        cbReports = new javax.swing.JCheckBox();
        cbGZIP = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        cbEncoding = new javax.swing.JComboBox();
        cbEncrypt = new javax.swing.JCheckBox();
        cbSign = new javax.swing.JCheckBox();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tfEid = new javax.swing.JTextField();
        tfGid = new javax.swing.JTextField();
        btnAddEID = new javax.swing.JButton();
        btnAddGID = new javax.swing.JButton();
        btnRemoveEID = new javax.swing.JButton();
        btnRemoveGID = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        cbOutput = new javax.swing.JComboBox();
        btnPrint = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        tfPayload = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfResponse = new javax.swing.JTextField();
        tfId = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        settingsMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("IBR-DTN Java API Example Application");
        setResizable(false);

        textArea.setEditable(false);
        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Bundle"));

        jLabel4.setText("Destination:");

        tfDestination.setText("dtn://");

        bgDestination.add(rbUnicast);
        rbUnicast.setSelected(true);
        rbUnicast.setText("Unicast");

        bgDestination.add(rbMulticast);
        rbMulticast.setText("Multicast");

        jLabel6.setText("Priority:");

        cbPriority.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NORMAL", "BULK", "EXPEDITED" }));

        cbCustody.setText("Custody");

        cbReports.setText("Reports");

        cbGZIP.setText("GZIP");

        jLabel9.setText("Encoding:");

        cbEncoding.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Base64", "Raw" }));
        cbEncoding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEncodingActionPerformed(evt);
            }
        });

        cbEncrypt.setText("Encrypt");

        cbSign.setText("Sign");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbUnicast)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbMulticast)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfDestination, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(59, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbGZIP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbPriority, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbEncoding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbCustody)
                                    .addComponent(cbReports))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbSign, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbEncrypt))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbUnicast)
                    .addComponent(rbMulticast))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbPriority, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCustody)
                    .addComponent(cbEncrypt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbReports)
                    .addComponent(cbSign))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbGZIP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbEncoding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Config"));

        jLabel7.setText("EID:");

        jLabel8.setText("GID:");

        tfGid.setText("dtn://");

        btnAddEID.setText("Add");
        btnAddEID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEIDActionPerformed(evt);
            }
        });

        btnAddGID.setText("Add");
        btnAddGID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddGIDActionPerformed(evt);
            }
        });

        btnRemoveEID.setText("Remove");
        btnRemoveEID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveEIDActionPerformed(evt);
            }
        });

        btnRemoveGID.setText("Remove");
        btnRemoveGID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveGIDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfEid)
                    .addComponent(tfGid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddEID)
                    .addComponent(btnAddGID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRemoveEID)
                    .addComponent(btnRemoveGID)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tfEid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddEID)
                    .addComponent(btnRemoveEID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tfGid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddGID)
                    .addComponent(btnRemoveGID))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Output"));

        cbOutput.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Primary EID", "Node Name", "Registrations", "Neighbors", "Neighbor Connections" }));

        btnPrint.setText("Print");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrint)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrint))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Payload"));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        tfPayload.setText("IBR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(19, 12, 0, 0);
        jPanel3.add(tfPayload, gridBagConstraints);

        jLabel1.setText("ID:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(6, 18, 0, 0);
        jPanel3.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Reply-ID:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(11, 18, 0, 0);
        jPanel3.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.insets = new java.awt.Insets(6, 7, 0, 0);
        jPanel3.add(tfResponse, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.insets = new java.awt.Insets(18, 7, 0, 0);
        jPanel3.add(tfId, gridBagConstraints);

        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.insets = new java.awt.Insets(1, 6, 18, 0);
        jPanel3.add(btnSend, gridBagConstraints);

        jLabel3.setText("Message:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(24, 18, 0, 0);
        jPanel3.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 254;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 18);
        jPanel3.add(jSeparator3, gridBagConstraints);

        menuBar.setName(""); // NOI18N

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");
        fileMenu.setEnabled(false);

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save As ...");
        saveAsMenuItem.setDisplayedMnemonicIndex(5);
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText("Edit");

        settingsMenuItem.setMnemonic('d');
        settingsMenuItem.setText("Settings");
        settingsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(settingsMenuItem);

        menuBar.add(editMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        contentsMenuItem.setMnemonic('c');
        contentsMenuItem.setText("Contents");
        contentsMenuItem.setEnabled(false);
        helpMenu.add(contentsMenuItem);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        dtnClient.shutdown();
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void btnRemoveGIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveGIDActionPerformed
        String group = tfGid.getText();
        if (group != null && group.length() > 3) {
            GroupEndpoint eid = new GroupEndpoint(group);
            try {
                dtnClient.getEC().removeRegistration(eid);
                logger.log(Level.INFO, "GID ''{0}'' removed", group);
            } catch (APIException ex) {
                logger.log(Level.SEVERE, ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnRemoveGIDActionPerformed

    private void btnRemoveEIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveEIDActionPerformed
        String eid = tfEid.getText();
        if (eid != null && eid.length() > 3) {
            try {
                dtnClient.getEC().removeEndpoint(eid);
                logger.log(Level.INFO, "Endpoint ''{0}'' removed", eid);
            } catch (APIException ex) {
                logger.log(Level.SEVERE, ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnRemoveEIDActionPerformed

    private void btnAddGIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddGIDActionPerformed
        String group = tfGid.getText();
        if (group != null && group.length() > 3) {
            GroupEndpoint gid = new GroupEndpoint(group);
            try {
                dtnClient.getEC().addRegistration(gid);
                logger.log(Level.INFO, "GID ''{0}'' added", gid);
            } catch (APIException ex) {
                logger.log(Level.SEVERE, ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnAddGIDActionPerformed

    private void btnAddEIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEIDActionPerformed
        String eid = tfEid.getText();
        if (eid != null && eid.length() > 3) {
            try {
                dtnClient.getEC().addEndpoint(eid);
                logger.log(Level.INFO, "Endpoint ''{0}'' added", eid);
            } catch (APIException ex) {
                logger.log(Level.SEVERE, ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnAddEIDActionPerformed

    private void settingsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsMenuItemActionPerformed
        new Settings(this).setVisible(true);
    }//GEN-LAST:event_settingsMenuItemActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        try {
            switch ((String) cbOutput.getSelectedItem()) {
                case "Primary EID":
                    logger.log(Level.INFO, "Primary EID {0}", dtnClient.getEC().getEndpoint().toString());
                    break;
                case "Node Name":
                    logger.log(Level.INFO, "Node Name {0}", dtnClient.getEC().getNodeName().toString());
                    break;
                case "Registrations":
                    logger.log(Level.INFO, "Registrations {0}", dtnClient.getEC().getRegistrations().toString());
                    break;
                case "Neighbors":
                    logger.log(Level.INFO, "Neighbors {0}", dtnClient.getEC().getNeighbors().toString());
                    break;
                case "Neighbor Connections":
                    logger.log(Level.INFO, "Neighbor connections {0}", dtnClient.getEC().getNeighborConnections().toString());
                    break;
                default:
                    logger.log(Level.WARNING, "Selected printing paramter unknown!");
            }
        } catch (APIException ex) {
            logger.log(Level.SEVERE, "Retrieving DTN configuration parameters failed");
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void cbEncodingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEncodingActionPerformed
        try {
            switch ((String) cbEncoding.getSelectedItem()) {
                case "Raw":
                    logger.log(Level.INFO, "set encoding raw");
                    dtnClient.getEC().setEncoding(Encoding.RAW);
                    break;
                case "Base64":
                    logger.log(Level.INFO, "set encoding base64");
                    dtnClient.getEC().setEncoding(Encoding.BASE64);
                    break;
                default:
                    logger.log(Level.WARNING, "Unsupported encoding selected!");
            }
        } catch (APIException ex) {
            logger.log(Level.SEVERE, "Switching encoding failed");
        }
    }//GEN-LAST:event_cbEncodingActionPerformed

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        EID destination;
        /*
         * Switch between unicast and multicast.
         */
        if (rbUnicast.isSelected()) {
            destination = new SingletonEndpoint(tfDestination.getText());
        } else {
            destination = new GroupEndpoint(tfDestination.getText());
        }

        SingletonEndpoint me = new SingletonEndpoint("api:me");

        // Create bundle to send
        Bundle bundle = new Bundle(destination, Constants.LIFETIME);
        bundle.setPriority(Bundle.Priority.valueOf((String) cbPriority.getSelectedItem()));

        if (cbReports.isSelected()) {
            bundle.setReportto(me);
        }

        if (cbCustody.isSelected()) {
            bundle.setCustodian(me);
            bundle.setFlag(Bundle.Flags.CUSTODY_REQUEST, true);
        }

        bundle.setFlag(Bundle.Flags.CUSTODY_REPORT, cbReports.isSelected());
        bundle.setFlag(Bundle.Flags.DELETION_REPORT, cbReports.isSelected());
        bundle.setFlag(Bundle.Flags.RECEPTION_REPORT, cbReports.isSelected());
        bundle.setFlag(Bundle.Flags.FORWARD_REPORT, cbReports.isSelected());
        bundle.setFlag(Bundle.Flags.DELIVERY_REPORT, cbReports.isSelected());
        bundle.setFlag(Bundle.Flags.COMPRESSION_REQUEST, cbGZIP.isSelected());

        // DTNSEC
        bundle.setFlag(Bundle.Flags.DTNSEC_REQUEST_ENCRYPT, cbEncrypt.isSelected());
        bundle.setFlag(Bundle.Flags.DTNSEC_REQUEST_SIGN, cbSign.isSelected());

        /*
         * Switch between binary and custom data format.
         */
        switch (PAYLOAD_TYPE) {
            case OBJECT:
                MessageData data = new MessageData();
                data.setId(tfId.getText());
                data.setCorrelationId(tfResponse.getText());
                data.setText(tfPayload.getText());

                bundle.appendBlock(new PayloadBlock(data));
                break;
            case BYTE:
                String text = tfPayload.getText();
                bundle.appendBlock(new PayloadBlock(text.getBytes()));
                break;
        }

        dtnClient.send(bundle);
    }//GEN-LAST:event_btnSendActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        new About(this).setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    /**
     * Prints a string in the app's text area.
     *
     * @param string the string to print
     */
    public final void print(String string) {
        textArea.append(string + "\n");
        textArea.validate();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DTNExampleApp().setVisible(true);
            }
        });
    }

    public DTNClient getDtnClient() {
        return dtnClient;
    }

    public void setDtnClient(DTNClient client) {
        this.dtnClient = client;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.ButtonGroup bgDestination;
    private javax.swing.JButton btnAddEID;
    private javax.swing.JButton btnAddGID;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRemoveEID;
    private javax.swing.JButton btnRemoveGID;
    private javax.swing.JButton btnSend;
    private javax.swing.JCheckBox cbCustody;
    private javax.swing.JComboBox cbEncoding;
    private javax.swing.JCheckBox cbEncrypt;
    private javax.swing.JCheckBox cbGZIP;
    private javax.swing.JComboBox cbOutput;
    private javax.swing.JComboBox cbPriority;
    private javax.swing.JCheckBox cbReports;
    private javax.swing.JCheckBox cbSign;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JRadioButton rbMulticast;
    private javax.swing.JRadioButton rbUnicast;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem settingsMenuItem;
    private javax.swing.JTextArea textArea;
    private javax.swing.JTextField tfDestination;
    private javax.swing.JTextField tfEid;
    private javax.swing.JTextField tfGid;
    private javax.swing.JTextField tfId;
    private javax.swing.JTextField tfPayload;
    private javax.swing.JTextField tfResponse;
    // End of variables declaration//GEN-END:variables
}
