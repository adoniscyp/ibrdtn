LOCAL_PATH:=$(call my-dir)

# SUBDIR parameter in androgenizer expects <project>_TOP variables to be defined
ibrcommon_TOP:=$(abspath $(LOCAL_PATH))/ibrcommon
ibrdtn_TOP:=$(abspath $(LOCAL_PATH))/ibrdtn
dtnd_TOP:=$(abspath $(LOCAL_PATH))/dtnd

# force old gcc
NDK_TOOLCHAIN_VERSION=4.4.3

# Optimizations
#APP_OPTIM:=release
APP_OPTIM:=debug

# Build target
APP_ABI:=armeabi x86
# mips not working currently!
#APP_ABI:=armeabi armeabi-v7a x86 mips
#APP_ABI:=all

# API 9 has RW Mutex implementation in pthread lib
APP_PLATFORM:=android-9
#APP_PLATFORM:=android-14

# See for documentation on Androids c++ support: $(NDK_PATH)/docs/CPLUSPLUS-SUPPORT.html
# select c++ gnu stl, because we need exception support
APP_STL:=gnustl_static

#system          -> Use the default minimal system C++ runtime library.
#gabi++_static   -> Use the GAbi++ runtime as a static library.
#gabi++_shared   -> Use the GAbi++ runtime as a shared library.
#stlport_static  -> Use the STLport runtime as a static library.
#stlport_shared  -> Use the STLport runtime as a shared library.
#gnustl_static   -> Use the GNU STL as a static library.
#gnustl_shared   -> Use the GNU STL as a shared library.

# enable exceptions and rtti (information about data types at runtime)
APP_CPPFLAGS:=-fexceptions -frtti
APP_CFLAGS:=

# ibrcommon
# openssl headers
# include openssl headers for ibrcommon/ibrcommon/ssl/gcm/gcm_aes.c, APP_CFLAGS are also used for c++
APP_CFLAGS+=-I$(abspath $(LOCAL_PATH))/openssl/include

# ibrdtn
# include ibrcommon headers
APP_CPPFLAGS+=-I$(abspath $(LOCAL_PATH))/ibrcommon

# dtnd
# also include ibrdtn headers
APP_CPPFLAGS+=-I$(abspath $(LOCAL_PATH))/ibrdtn


# If APP_MODULES is not set, all modules are compiled!
APP_MODULES:=dtnd
#APP_MODULES:=ibrcommon ibrdtn dtnd