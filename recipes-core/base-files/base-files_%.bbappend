
do_install:append() {
    if [ ${@ oe.types.boolean('${VOLATILE_TMP_DIR}') } = True ]; then
        rmdir ${D}/tmp
        ln -sf var/tmp ${D}/tmp
    fi
}

FILES:${PN} += "/tmp"
