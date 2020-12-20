SUMMARY = "A playing operating system."

IMAGE_FEATURES += " splash hwcodecs"
EXTRA_IMAGE_FEATURES += " package-management"

LICENSE = "MIT"

IMAGE_INSTALL_append += " apt wayland weston"

inherit core-image
