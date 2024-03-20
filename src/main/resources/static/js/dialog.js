/**
 * @author trungnv_bks
 * @since 18-08-2023
 */


/**
 * This function is used to display a dialog box.
 * @param title - Title of dialog
 * @param body - content of dialog <html>
 * @param button - [{text, icon, class, click : () => }]
 * @param onClose - function after close
 * @param closeButton - bool
 */
function showDialog(title, body, buttons, onClose, closeButton){
    const listButton = [...buttons];
    if(closeButton){
        listButton.push({
            text: $.localeMessage('close') ,
            icon: "ui-icon-heart",
            class: 'btn btn-outline-secondary btn-sm',
            click: function() {
                $( this ).dialog( "close" );
            }
        })
    }
    const dialog = $('#dialog');
    const dialogBody = $('#dialogBody');
    dialogBody.html(body);
    dialog.dialog({
        open: function (event, ui) {
            $(this).closest(".ui-dialog")
                .find(".ui-dialog-titlebar-close")
                .removeClass("ui-dialog-titlebar-close")
                .html("<svg class='position-absolute' style='top: -3px; left: 0' width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                    "<path d=\"M7 7L17 17M7 17L17 7\" stroke=\"#111111\" stroke-linecap=\"round\" stroke-linejoin=\"round\"/>\n" +
                    "</svg>")
                .attr("class","ui-button ui-corner-all ui-widget ui-button-icon-only ui-dialog-titlebar-close");
        },
        buttons: listButton,
        beforeClose: function( event, ui ) {
            const dialog = $("#dialog")
            const dialogBody = $('#dialogBody');
            dialog.attr("title",'')
            dialogBody.html('');
            if(onClose){
                onClose();
            }
        },
        title: title
    });
}

/**
 * This function is used to close a dialog box.
 */
function closeDialog() {
    const dialog = $("#dialog")
    const dialogBody = $('#dialogBody');
    dialog.attr("title",'')
    dialogBody.html('');
    dialog.dialog('close');

}