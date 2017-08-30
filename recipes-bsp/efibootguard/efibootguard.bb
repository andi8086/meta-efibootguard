LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/siemens/efibootguard.git;protocol=https;branch=master"
SRCREV = "e3263ab8132b8d0be3b1e5513afc4a93f287e9e5"

S = "${WORKDIR}/git"

DEPENDS = "gnu-efi pciutils"

inherit autotools deploy

COMPATIBLE_HOST = "(x86_64.*|i.86.*)-linux"

PACKAGES = "${PN}-tools \
            ${PN}-tools-dbg \
            ${PN}-tools-staticdev \
            ${PN}-tools-dev \
            ${PN}-efi"

EXTRA_OECONF = "--with-gnuefi-sys-dir=${STAGING_DIR_HOST} \
                --with-gnuefi-include-dir=${STAGING_INCDIR}/efi \
                --with-gnuefi-lds-dir=${STAGING_LIBDIR} \
                --with-gnuefi-lib-dir=${STAGING_LIBDIR}"

FILES_${PN}-tools = "/usr/bin"
FILES_${PN}-tools-dbg = "/usr/src/debug"
FILES_${PN}-tools-staticdev = "/usr/lib/lib*.a"
FILES_${PN}-tools-dev = "/usr/include/*"
FILES_${PN}-efi = "/usr/lib/efibootguard"

do_deploy () {
	install ${B}/efibootguard*.efi ${DEPLOYDIR}
}
addtask deploy before do_build after do_compile

BBCLASSEXTEND = "native"
DEPENDS_class-native = ""

do_compile_class-native () {
	oe_runmake bg_setenv
}

do_install_class-native () {
	install -d ${D}${bindir}/
	install -m755 ${B}/bg_setenv ${D}${bindir}/
	ln -s bg_setenv ${D}${bindir}/bg_printenv
}

do_deploy_class-native () {
}

