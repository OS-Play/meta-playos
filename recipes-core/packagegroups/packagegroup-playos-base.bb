DESCRIPTION = "PlayOS Base Package Groups"

inherit packagegroup

PACKAGES = "\
        ${PN} \
        "

RDEPENDS:${PN} = "\
        sysenv \
        ttf-wqy-zenhei \
        eudev \
        openrc \
        "
