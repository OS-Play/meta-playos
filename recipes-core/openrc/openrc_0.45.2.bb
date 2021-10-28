SUMMARY = "The OpenRC init system"
DESCRIPTION = "OpenRC is a dependency-based init system that works with the \
                system-provided init program, normally /sbin/init"
HOMEPAGE = "https://github.com/OpenRC/openrc"
SECTION = "base"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2307fb28847883ac2b0b110b1c1f36e0 \
                    "

SRCREV = "3e5420b911922a14dd6b5cc3d2143dc30559caf4"
SRC_URI = "git://github.com/OpenRC/openrc;protocol=https;branch=0.45.x \
          file://volatiles \
          file://conf.d \
          "
inherit meson features_check update-alternatives

S = "${WORKDIR}/git"

DEPENDS = " ${@bb.utils.contains('DISTRO_FEATURES', 'pam', 'libpam', '', d)}"
RDEPENDS:${PN} = " perl"
RPROVIDES:${PN} += "/sbin/openrc-run"

REQUIRED_DISTRO_FEATURES = "openrc"

EXTRA_OEMESON:append = "\
        -Dpam=true \
        -Dos=Linux \
        -Dsysvinit=${@ 'True' if d.getVar('VIRTUAL-RUNTIME_init_manager') == '${PN}' else 'False'} \
        -Dlocal_prefix=/usr \
        -Drootprefix=${@bb.utils.contains('DISTRO_FEATURES', 'usrmerge', '/usr', '/', d)} \
        "

set_option() {
    sed -i "s/^\#\?\($1=\)\".*\"/\1\"$2\"/g" ${D}${sysconfdir}/rc.conf
}

do_install:append() {
    if [ "${@d.getVar('VIRTUAL-RUNTIME_init_manager')}" == "${PN}" ]; then
        ln -sf openrc-init ${D}${sbindir}/init
    fi

    tmp="${SERIAL_CONSOLES}"
    for i in $tmp
    do
        j=`echo ${i} | sed -e s/^.*\;//g -e s/\:.*//g`

        ln -sf /etc/init.d/agetty ${D}${sysconfdir}/init.d/agetty.${j}
        ln -sf /etc/init.d/agetty.${j} ${D}${sysconfdir}/runlevels/default/agetty.${j}
    done

    # Disable keymaps and save-keymaps, they are rdepends kbd
    rm -f ${D}${sysconfdir}/runlevels/boot/keymaps
    rm -f ${D}${sysconfdir}/runlevels/boot/save-keymaps

    set_option rc_parallel YES
    set_option rc_logger YES

    install -m644 -D ${WORKDIR}/conf.d/volatiles ${D}${sysconfdir}/conf.d/volatiles
    install -m0755 -D ${WORKDIR}/volatiles ${D}${sysconfdir}/init.d/volatiles
    ln -sf /etc/init.d/volatiles ${D}${sysconfdir}/runlevels/sysinit/volatiles

    if [ ${@ oe.types.boolean('${VOLATILE_LOG_DIR}') } = True ]; then
        sed -i 's/^\(VOLATILE_LOG_DIR\).*/\1=yes/g' \
            ${D}${sysconfdir}/conf.d/volatiles
    fi
}

FILES:${PN} = "${sysconfdir} ${base_libdir} ${base_sbindir} ${base_bindir} ${datadir}"
ALTERNATIVE:${PN} = "start-stop-daemon"
ALTERNATIVE_LINK_NAME[start-stop-daemon] = "${base_sbindir}/start-stop-daemon"
ALTERNATIVE_PRIORITY = "200"
