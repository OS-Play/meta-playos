SUMMARY = "A playing operating system."

IMAGE_FEATURES += " ssh-server-openssh package-management splash hwcodecs"

LICENSE = "MIT"

PREFERRED_PROVIDER_android-tools-conf = "android-tools-conf-configfs"

# Base
IMAGE_INSTALL:append = "\
    packagegroup-core-boot \
    packagegroup-core-full-cmdline \
    packagegroup-core-ssh-openssh \
    packagegroup-coreui \
    kernel-image \
    kernel-modules \
    kernel-devicetree \
    wpa-supplicant \
    wayland \
    android-tools-adbd \
    openrc \
    flutter-engine-bin \
    libexecinfo \
    sysenv \
    networkmanager-nmcli \
    bluez5 \
    "

# Services
IMAGE_INSTALL:append = "\
    rc-psplash \
    "

# Apps
IMAGE_INSTALL:append = "\
    packagegroup-retroarch \
    packagegroup-playos-libretro-cores \
    "

# 1G Rootfs
IMAGE_ROOTFS_SIZE = "1048576"

inherit core-image

post_install_command() {

# setup flutter owner after sdk installed
UG=`id | sed -e 's/.*uid=\([0-9]*\).* gid=\([0-9]*\).*/\1:\2/g'`
if [ -n "$UG" ] && [ "$UG" != "0:0" ]; then
    $SUDO_EXEC chown -R $UG ${native_sysroot}/opt/flutter
fi

}

SDK_POST_INSTALL_COMMAND += "${post_install_command}"
