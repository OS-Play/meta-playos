SUMMARY = "A modular Wayland compositor library."
DESCRIPTION = "Pluggable, composable, unopinionated modules for building a Wayland compositor; or about 60,000 lines of code you were going to write anyway.."
HOMEPAGE = "https://gitlab.freedesktop.org/wlroots/wlroots"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7578fad101710ea2d289ff5411f1b818"

SRC_URI = "git://gitlab.freedesktop.org/wlroots/wlroots.git;protocol=https;branch=master \
           "
S = "${WORKDIR}/git"

SRCREV = "052ea7b475e0c24c82ff7ccdefad63f3aeb8b020"
TARGET_CC_ARCH += "${LDFLAGS}"

inherit meson pkgconfig

DEPENDS = "libinput \
           libxkbcommon \
           pixman \
           cairo \
           wayland \
           wayland-protocols \
           virtual/egl \
           udev \
           seatd \
           wayland-native \
           libdrm \
           "
RDEPENDS:${PN} = "xkeyboard-config"

EXTRA_OEMESON:append = " --default-library=both \
                -Dxwayland=disabled \
                -Dxcb-errors=disabled"
