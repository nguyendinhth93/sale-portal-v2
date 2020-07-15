
function clickById(id) {
    $('#' + id).trigger('click');
}

function setFocusByClass(className) {
    $("." + className).focus();
}

