SUMMARY = "Play OS system environment variables"
DESCRIPTION = "Play OS system environment variables."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://profile \
          "

S = "${WORKDIR}"

do_install() {
    install -d /etc/profile.d
    install -D -m644 ${S}/profile ${D}/${sysconfdir}/profile.d/sysenv.sh
}

FILES:${PN} = "${sysconfdir}/profile.d/sysenv.sh"
