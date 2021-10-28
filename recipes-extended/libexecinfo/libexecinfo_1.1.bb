SUMMARY = "A quick-n-dirty BSD licensed clone of the GNU libc backtrace facility."
DESCRIPTION = "A quick-n-dirty BSD licensed clone of the GNU libc backtrace facility."
HOMEPAGE = "https://www.freshports.org/devel/libexecinfo"
SECTION = "base"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

SRC_URI = "http://distcache.freebsd.org/local-distfiles/itetcu/libexecinfo-${PV}.tar.bz2 \
           file://10-execinfo.patch \
           file://20-define-gnu-source.patch \
           file://30-linux-makefile.patch \
           "

SRC_URI[sha512sum] = "51fea7910ef6873061a25c22434ce4da724e9d8e37616a069ad0a58c0463755be4c6c7da88cd747484c2f3373909d7be4678b32a4bd91b6d9e0f74526094e92c"

TARGET_CC_ARCH += "${LDFLAGS}"
TARGET_CFLAGS:append = " -fPIC"
BUILDSDK_CFLAGS:append = " -fPIC"

inherit siteinfo

do_configure() {
    :
}

do_compile() {
    oe_runmake
}

do_install() {
    install -D -m755 execinfo.h \
            ${D}/usr/include/execinfo.h
    install -D -m755 stacktraverse.h \
            ${D}/usr/include/stacktraverse.h

    install -D -m755 libexecinfo.a \
            ${D}/usr/lib/libexecinfo.a
    install -D -m755 libexecinfo.so.1 \
            ${D}/usr/lib/libexecinfo.so.1
    ln -s /usr/lib/libexecinfo.so.1 \
            ${D}/usr/lib/libexecinfo.so
}
