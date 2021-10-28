SUMMARY = "PlayOS GUI Environment."
DESCRIPTION = "PlayOS GUI Environment base on wlroots."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "gitsm://git@git.playos.xyz/PlayOS/playos-coreui.git;protocol=ssh;branch=dev \
           file://init \
           file://coreui.cfg \
           "
S = "${WORKDIR}/git"

SRCREV = "b06793dcdb0fc528a4b07d6fee46190296d745cb"
TARGET_CC_ARCH += "${LDFLAGS}"

PACKAGES:append = "\
        playos-panel \
        playos-settings \
        playos-wallpaper \
        "

DEPENDS = "wlroots \
        virtual/flutter-engine \
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
