FILESEXTRAPATHS:append := ":${THISDIR}/${PN}"

PV = "1.21.0"
SRC_URI = "https://gitlab.freedesktop.org/wayland/wayland/-/releases/${PV}/downloads/${BPN}-${PV}.tar.xz \
        "
SRC_URI[sha256sum] = "6dc64d7fc16837a693a51cfdb2e568db538bfdc9f457d4656285bb9594ef11ac"
