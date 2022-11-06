require flutter-engine-desc.inc

FILESEXTRAPATHS:append := ":${THISDIR}/flutter-engine-bin"
S = "${WORKDIR}"

COMPATIBLE_HOST = "aarch64.*-linux"
PROVIDES = "flutter-engine"

SRC_URI = "file://include/embedder.h \
           file://libs/aarch64_musl/libflutter_engine.so \
           file://data/icudtl.dat \
           "

DEPENDS = "libexecinfo"

INSANE_SKIP:${PN} = "ldflags already-stripped"
# INHIBIT_PACKAGE_STRIP = "1"
# INHIBIT_SYSROOT_STRIP = "1"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

do_install() {
    install -D -p -m0644 ${S}/include/embedder.h ${D}${includedir}/embedder.h
    install -D -p -m0644 ${S}/data/icudtl.dat ${D}${datadir}/flutter/resources/icu/icudtl.dat
    install -D -p -m0644 ${S}/libs/aarch64_musl/libflutter_engine.so ${D}${libdir}/libflutter_engine.so
}

FILES:${PN} += "${datadir}/flutter/resources/icu/icudtl.dat"
