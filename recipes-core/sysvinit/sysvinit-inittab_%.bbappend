
do_install:append() {
    if [ -n "${@bb.utils.contains('DISTRO_FEATURES', 'openrc', 'true', '', d)}" ]; then
        cat <<EOF >> ${D}${sysconfdir}/inittab
::sysinit:/sbin/openrc sysinit
::wait:/sbin/openrc boot
::wait:/sbin/openrc
EOF
    fi
}
