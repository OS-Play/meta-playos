DESCRIPTION = "PlayOS Extra Linux kernel modules"

inherit packagegroup

PACKAGES = "\
        ${PN} \
        "

RDEPENDS:${PN} = "\
        kernel-module-playos-ds1302 \
        "
