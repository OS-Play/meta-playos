SUMMARY = "Flutter makes it easy and fast to build beautiful apps for mobile and beyond."
DESCRIPTION = "Flutter is Google's SDK for crafting beautiful, fast user experiences for \
mobile, web, and desktop from a single codebase. Flutter works with existing code, is used \
by developers and organizations around the world, and is free and open source."
HOMEPAGE = "https://flutter.dev"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1d84cf16c48e571923f837136633a265"

S = "${WORKDIR}/git"

PV = "3.3.7"
SRCREV = "e99c9c7cd9f6c0b2f8ae6e3ebfd585239f5568f4"
SRC_URI = "git://github.com/flutter/flutter.git;protocol=https;nobranch=1 \
           "

DEPENDS += "\
    ca-certificates-native \
    curl-native \
    unzip-native \
    "

RDEPENDS:${PN}-native += "ca-certificates-native curl-native perl perl-modules unzip-native"
RDEPENDS:nativesdk-${PN} += "ca-certificates-native curl-native perl perl-modules unzip-native"

INSANE_SKIP:${PN} = "ldflags already-stripped"

do_unpack[network] = "1"
do_patch[network] = "1"
do_compile[network] = "1"

export PUB_HOSTED_URL = "${@d.getVar('BB_ORIGENV').getVar('PUB_HOSTED_URL')}"
PUB_HOSTED_URL[vardepsexclude] = "BB_ORIGENV PUB_HOSTED_URL"
export FLUTTER_STORAGE_BASE_URL = "${@d.getVar('BB_ORIGENV').getVar('FLUTTER_STORAGE_BASE_URL')}"
FLUTTER_STORAGE_BASE_URL[vardepsexclude] = "BB_ORIGENV FLUTTER_STORAGE_BASE_URL"

do_compile() {
    export CURL_CA_BUNDLE=${STAGING_DIR_NATIVE}/etc/ssl/certs/ca-certificates.crt
    export PATH=${S}/bin:$PATH
    export PUB_CACHE=${S}/.pub-cache
    export http_proxy=${http_proxy}
    export https_proxy=${https_proxy}

    flutter config --clear-features -v
    flutter config --enable-custom-devices
    flutter config --no-analytics
    dart --disable-analytics

    bbnote `flutter config`
    bbnote `flutter doctor -v`
}

do_install:class-native() {
    install -d "${D}/${bindir}"

    cat <<EOF > ${D}/${bindir}/flutter
#!/bin/bash

export PATH=${S}/bin:\$PATH
flutter \$@
EOF
    cat <<EOF > ${D}/${bindir}/dart
#!/bin/bash

export PATH=${S}/bin:\$PATH
dart \$@
EOF

    chmod a+x ${D}/${bindir}/flutter
    chmod a+x ${D}/${bindir}/dart
}

do_install:class-nativesdk() {
    rm -rf ${S}/bin/cache/pkg/sky_engine/
    rm -rf ${S}/bin/cache/artifacts/*

    chmod a+rw ${S} -R

    FLUTTER_PARENT_DIR=${D}/${base_prefix}/opt/

    mkdir -p ${FLUTTER_PARENT_DIR}/ ${D}/${bindir}/
    cp -r ${S} ${FLUTTER_PARENT_DIR}/flutter
    ln -sf ../../opt/flutter/bin/flutter ${D}/${bindir}/flutter
    ln -sf ../../opt/flutter/bin/dart ${D}/${bindir}/dart
}

FILES:${PN} = "${base_prefix}/opt/flutter ${bindir}/flutter"

BBCLASSEXTEND = "native nativesdk"
