FILESEXTRAPATHS:append := ":${THISDIR}/${PN}"

SRC_URI:append = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'openrc', 'file://openrc_compat.cfg', '', d)} \
    ${@ 'file://openrc_init.cfg' if d.getVar('VIRTUAL-RUNTIME_init_manager') == 'openrc' else '' } \
    "

FILESEXTRAPATHS:prepend = "${THISDIR}/${PN}:"
