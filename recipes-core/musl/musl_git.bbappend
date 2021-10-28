FILESEXTRAPATHS:append := ":${THISDIR}/${PN}"

SRC_URI:append = " file://100-werror-sign-compare-fix.patch \
           "

TARGET_CFLAGS:append = " -fPIC"
BUILDSDK_CFLAGS:append = " -fPIC"
