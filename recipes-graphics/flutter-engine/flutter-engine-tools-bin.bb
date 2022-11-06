require flutter-engine-desc.inc

S = "${WORKDIR}"

COMPATIBLE_HOST = "x86_64.*-linux"
PROVIDES = "flutter-engine-tools-native nativesdk-flutter-engine-tools"

SRC_URI = "file://flutter_patched_sdk/ \
           file://frontend_server.dart.snapshot \
           file://x64/bin/gen_snapshot \
           "

INSANE_SKIP:${PN} = "already-stripped"

install_tools() {
    mkdir -p ${D}${base_prefix}/opt/flutter-engine ${D}/${bindir}/ ${D}${base_prefix}/environment-setup.d/

    cp -r ${S}/flutter_patched_sdk ${D}/${base_prefix}/opt/flutter-engine/
    cp -r ${S}/frontend_server.dart.snapshot ${D}/${base_prefix}/opt/flutter-engine/
    install -D -m0755 ${S}/x64/bin/gen_snapshot ${D}/${base_prefix}/opt/flutter-engine/
    ln -sf ../../opt/flutter-engine/gen_snapshot ${D}${bindir}/

    cat <<EOF > ${D}${base_prefix}/environment-setup.d/playos_flutter_sdk_path.sh
export PLAYOS_FLUTTER_SDK_PATH=${base_prefix}/opt/flutter-engine
EOF
}

do_install:class-native() {
    install_tools
}

do_install:class-nativesdk() {
    install_tools
}

FILES:${PN} += "${base_prefix}/opt/flutter-engine/"

SYSROOT_DIRS_NATIVE += "${base_prefix}/opt/flutter-engine/ ${base_prefix}/environment-setup.d"

BBCLASSEXTEND = "native nativesdk"
