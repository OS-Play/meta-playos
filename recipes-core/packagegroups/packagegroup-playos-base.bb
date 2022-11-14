DESCRIPTION = "PlayOS Base Package Groups"

inherit packagegroup

PACKAGES = "\
        ${PN} \
        "

RDEPENDS:${PN} = "\
        sysenv \
        sys-setup \
        ttf-wqy-zenhei \
        eudev \
        openrc \
        vim-tiny \
        alsa-utils-alsactl \
        pulseaudio-server \
        "
