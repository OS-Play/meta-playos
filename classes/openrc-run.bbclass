RCUPDATEPN ?= "${PN}"

RDEPENDS:${PN}:append = " openrc"

RCUPDATE = "rc-update"
RCUPDATE:class-cross = ""
RCUPDATE:class-native = ""
RCUPDATE:class-nativesdk = ""

RC_INITSCRIPT_PARAMS ?= "default"

INIT_D_DIR = "${sysconfdir}/init.d"


rcupdate_postinst() {
if [ -z "$D" ] && [ type rc-update >/dev/null 2>/dev/null ]; then
    rc-update add ${RC_INITSCRIPT_NAME} ${RC_INITSCRIPT_PARAMS}
else
    TARGET=${INIT_D_DIR}/${RC_INITSCRIPT_NAME}
    for level in "${RC_INITSCRIPT_PARAMS}";
    do
        ln -sf $TARGET "$D${sysconfdir}/runlevels/$level/${RC_INITSCRIPT_NAME}"
    done
fi
}

rcupdate_prerm() {
if [ -a -x "${INIT_D_DIR}/${RC_INITSCRIPT_NAME}" ]; then
    ${INIT_D_DIR}/${RC_INITSCRIPT_NAME} stop || :
fi
}

rcupdate_postrm() {
if [ type rc-update >/dev/null 2>/dev/null ]; then
    rc-update del ${RC_INITSCRIPT_NAME} ${RC_INITSCRIPT_PARAMS}
else
    TARGET=${INIT_D_DIR}/${RC_INITSCRIPT_NAME}
    for level in "${RC_INITSCRIPT_PARAMS}";
    do
        rm -f "$D${sysconfdir}/runlevels/$level/${RC_INITSCRIPT_NAME}"
    done
fi
}

def update_rc_after_parse(d):
    if d.getVar('RC_INITSCRIPT_PACKAGES', False) == None:
        if d.getVar('RC_INITSCRIPT_NAME', False) == None:
            bb.fatal("%s inherits rc-update but doesn't set RC_INITSCRIPT_NAME" % d.getVar('FILE', False))
        if d.getVar('RC_INITSCRIPT_PARAMS', False) == None:
            bb.fatal("%s inherits rc-update but doesn't set RC_INITSCRIPT_PARAMS" % d.getVar('FILE', False))

python __anonymous() {
    update_rc_after_parse(d)
}

PACKAGESPLITFUNCS:prepend = "${@bb.utils.contains('DISTRO_FEATURES', 'openrc', 'populate_packages_rcupdate ', '', d)}"
PACKAGESPLITFUNCS:remove:class-nativesdk = "populate_packages_rcupdate "

populate_packages_rcupdate[vardeps] += "rcupdate_prerm rcupdate_postrm rcupdate_postinst"
populate_packages_rcupdate[vardepsexclude] += "OVERRIDES"

python populate_packages_rcupdate () {
    def update_rcd_auto_depend(pkg):
        import subprocess
        import os
        path = d.expand("${D}${INIT_D_DIR}/${RC_INITSCRIPT_NAME}")
        if not os.path.exists(path):
            return
        statement = "grep -q -w '/etc/init.d/functions.sh' %s" % path
        if subprocess.call(statement, shell=True) == 0:
            mlprefix = d.getVar('MLPREFIX') or ""
            d.appendVar('RDEPENDS:' + pkg, ' %sinitd-functions' % (mlprefix))

    def update_rcd_package(pkg):
        bb.debug(1, 'adding rc-update calls to postinst/prerm/postrm for %s' % pkg)

        localdata = bb.data.createCopy(d)
        overrides = localdata.getVar("OVERRIDES")
        localdata.setVar("OVERRIDES", "%s:%s" % (pkg, overrides))

        update_rcd_auto_depend(pkg)

        postinst = d.getVar('pkg_postinst:%s' % pkg)
        if not postinst:
            postinst = '#!/bin/sh\n'
        postinst += localdata.getVar('rcupdate_postinst')
        d.setVar('pkg_postinst:%s' % pkg, postinst)

        prerm = d.getVar('pkg_prerm:%s' % pkg)
        if not prerm:
            prerm = '#!/bin/sh\n'
        prerm += localdata.getVar('rcupdate_prerm')
        d.setVar('pkg_prerm:%s' % pkg, prerm)

        postrm = d.getVar('pkg_postrm:%s' % pkg)
        if not postrm:
                postrm = '#!/bin/sh\n'
        postrm += localdata.getVar('rcupdate_postrm')
        d.setVar('pkg_postrm:%s' % pkg, postrm)

        d.appendVar('RRECOMMENDS:' + pkg, " ${MLPREFIX}${RCUPDATE}")

    # Check that this class isn't being inhibited (generally, by
    # systemd.bbclass) before doing any work.
    if not d.getVar("INHIBIT_RCUPDATE_BBCLASS"):
        pkgs = d.getVar('RC_INITSCRIPT_PACKAGES')
        if pkgs == None:
            pkgs = d.getVar('RCUPDATEPN')
            packages = (d.getVar('PACKAGES') or "").split()
            if not pkgs in packages and packages != []:
                pkgs = packages[0]
        for pkg in pkgs.split():
            update_rcd_package(pkg)
}
