#!/bin/bash

if ! $(return >/dev/null 2>&1) ; then
    echo 'envsetup.sh: error: this script must be sourced'
    echo ''
    echo 'Usage: source ./scripts/envsetup.sh'
    echo ''
    return
fi

CUR_DIR=$(pwd)

export DISTRO=playos
export PLAYOS_ROOT=$CUR_DIR
export BUILDDIR=$CUR_DIR/build/
export DL_DIR=$CUR_DIR/downloads/
export TEMPLATECONF=$CUR_DIR/meta-playos/conf

cd core
. ./oe-init-build-env $BUILDDIR
cd -
cd $BUILDDIR
