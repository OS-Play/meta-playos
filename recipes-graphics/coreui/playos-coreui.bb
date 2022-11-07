SUMMARY = "PlayOS GUI Environment."
DESCRIPTION = "PlayOS GUI Environment base on wlroots."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "gitsm://github.com/OS-Play/playos-coreui.git;protocol=https;branch=master \
           file://init \
           file://coreui.cfg \
           "
S = "${WORKDIR}/git"

PV = "0.1.0"
SRCREV = "54ef29874361b95900d70bbc6a11acb3d776bfb8"
TARGET_CC_ARCH += "${LDFLAGS}"

PACKAGES:append = "\
        playos-panel \
        playos-settings \
        playos-wallpaper \
        "

DEPENDS = "wlroots \
        flutter-engine \
        flutter-engine-tools-native \
        flutter-native \
        wayland-native \
        "
RDEPENDS:playos-panel = "${PN}"
RDEPENDS:playos-settings = "${PN}"
RDEPENDS:playos-wallpaper = "${PN}"

inherit cmake pkgconfig openrc-run

EXTRA_OECMAKE:append = "-DBUILD_WLROOTS=OFF \
                        -DFETCHCONTENT_UPDATES_DISCONNECTED=ON \
                        -DEP_UPDATE_DISCONNECTED=ON \
                        "

do_compile[depends] += "flutter-native:do_populate_sysroot"

do_configure[network] = "1"
do_compile[network] = "1"

INSANE_SKIP:playos-panel = "ldflags"
INSANE_SKIP:playos-settings = "ldflags"
INSANE_SKIP:playos-wallpaper = "ldflags"

export PLAYOS_FLUTTER_SDK_PATH
PLAYOS_FLUTTER_SDK_PATH = "${WORKDIR}/recipe-sysroot-native/opt/flutter-engine"

do_install:append() {
    install -d ${D}${sysconfdir}/init.d/
    install -Dm0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/coreui

    install -d ${D}${sysconfdir}/conf.d/
    install -Dm0755 ${WORKDIR}/coreui.cfg ${D}${sysconfdir}/conf.d/coreui
}

FILES:${PN} = "${bindir}/coreui \
                ${sysconfdir}/init.d/coreui \
                ${sysconfdir}/conf.d/coreui"
FILES:${PN}-dev = "${libdir}/ ${includedir}"
FILES:playos-panel = "${prefix}/Applications/playos-panel.app"
FILES:playos-settings = "${prefix}/Applications/playos-settings.app"
FILES:playos-wallpaper = "${prefix}/Applications/playos-wallpaper.app"

RC_INITSCRIPT_NAME = "coreui"
RC_INITSCRIPT_PARAMS = "default"
