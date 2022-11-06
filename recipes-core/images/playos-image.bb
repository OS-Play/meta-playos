SUMMARY = "A playing operating system."

IMAGE_FEATURES += " ssh-server-openssh package-management splash hwcodecs"

LICENSE = "MIT"

# Base
IMAGE_INSTALL += "\
    packagegroup-core-full-cmdline \
    packagegroup-core-ssh-openssh \
    packagegroup-playos-base \
    packagegroup-coreui \
    kernel-image \
    kernel-modules \
    kernel-devicetree \
    wpa-supplicant \
    wayland \
    android-tools-adbd \
    libexecinfo \
    networkmanager-nmcli \
    bluez5 \
    ${@ bb.utils.contains('EXTRA_IMAGE_FEATURES', 'tools-profile', 'perf', '', d) } \
    "

# Services
IMAGE_INSTALL += "\
    rc-psplash \
    "

# Apps
IMAGE_INSTALL += "\
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
