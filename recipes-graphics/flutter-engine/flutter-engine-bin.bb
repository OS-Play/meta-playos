require flutter-engine-desc.inc

S = "${WORKDIR}"

COMPATIBLE_HOST = "aarch64.*-linux"

SRC_URI = "file://embedder.h \
           file://libflutter_engine.so \
           file://icudtl.dat \
           "

SRC_URI[sha256sum] = "211a0a814cf5b1847d9b41e7b75f8328a036497d0ce6ca8403c083c463575be7"
SRC_URI[sha256sum] = "e329b364b778575d5ffa396d0114efc30eec422c7aeef841157c19dee2bc9148"
SRC_URI[sha256sum] = "762a0ae89a8cbf8b5173cc33e1104c6b9321251588a7baa445156a69ba4f69c9"

DEPENDS = "libexecinfo"

INSANE_SKIP:${PN} = "ldflags already-stripped"
# INHIBIT_PACKAGE_STRIP = "1"
# INHIBIT_SYSROOT_STRIP = "1"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

do_install() {
    install -D -p -m0644 ${S}/embedder.h ${D}${includedir}/embedder.h
    install -D -p -m0644 ${S}/libflutter_engine.so ${D}${libdir}/libflutter_engine.so
    install -D -p -m0644 ${S}/icudtl.dat ${D}${datadir}/flutter/resources/icu/icudtl.dat
}

FILES:${PN} += "${datadir}/flutter/resources/icu/icudtl.dat"
