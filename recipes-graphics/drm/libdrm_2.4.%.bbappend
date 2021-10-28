
PV = "2.4.113"

SRC_URI = "http://dri.freedesktop.org/libdrm/${BP}.tar.xz \
          "

SRC_URI[sha256sum] = "7fd7eb2967f63beb4606f22d50e277d993480d05ef75dd88a9bd8e677323e5e1"

PACKAGECONFIG ??= "vmwgfx omap freedreno vc4 etnaviv install-test-programs"

# No such option
PACKAGECONFIG[libkms] = ""

PACKAGECONFIG[intel] = "-Dintel=enabled,-Dintel=disabled,libpciaccess"
PACKAGECONFIG[radeon] = "-Dradeon=enabled,-Dradeon=disabled"
PACKAGECONFIG[amdgpu] = "-Damdgpu=enabled,-Damdgpu=disabled"
PACKAGECONFIG[nouveau] = "-Dnouveau=enabled,-Dnouveau=disabled"
PACKAGECONFIG[vmwgfx] = "-Dvmwgfx=enabled,-Dvmwgfx=disabled"
PACKAGECONFIG[omap] = "-Domap=enabled,-Domap=disabled"
PACKAGECONFIG[exynos] = "-Dexynos=enabled,-Dexynos=disabled"
PACKAGECONFIG[freedreno] = "-Dfreedreno=enabled,-Dfreedreno=disabled"
PACKAGECONFIG[tegra] = "-Dtegra=enabled,-Dtegra=disabled"
PACKAGECONFIG[vc4] = "-Dvc4=enabled,-Dvc4=disabled"
PACKAGECONFIG[etnaviv] = "-Detnaviv=enabled,-Detnaviv=disabled"
PACKAGECONFIG[valgrind] = "-Dvalgrind=enabled,-Dvalgrind=disabled,valgrind"
PACKAGECONFIG[cairo-tests] = "-Dcairo-tests=enabled,-Dcairo-tests=disabled"
PACKAGECONFIG[manpages] = "-Dman-pages=enabled,-Dman-pages=disabled,libxslt-native xmlto-native python3-docutils-native"
