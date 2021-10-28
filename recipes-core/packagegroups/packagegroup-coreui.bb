DESCRIPTION = "PlayOS CoreUI Package Groups"

inherit packagegroup

PACKAGES = "\
        ${PN} \
        "

RDEPENDS:${PN} = "\
        playos-panel \
        playos-coreui \
        playos-settings \
        playos-wallpaper \
        "
