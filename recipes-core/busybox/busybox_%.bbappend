FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://watchdog.cfg \
            file://watchdog.sh"

do_install_append() {
	install -d "${D}${sysconfdir}/init.d"
	install -m 0755 "${WORKDIR}/watchdog.sh" "${D}${sysconfdir}/init.d/watchdog.sh"
	install -d "${D}${sysconfdir}/rcS.d"
	ln -sf ../init.d/watchdog.sh "${D}${sysconfdir}/rcS.d/S00watchdog.sh"
}
