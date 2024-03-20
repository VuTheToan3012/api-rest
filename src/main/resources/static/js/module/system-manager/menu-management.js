/**
 *  @author trungNV (trungnv.bks@gmail.com)
 * @since 25-07-2023
 */
$(document).ready(function () {
    initDataTable();
    formSubmit();
    $("#btnCloseModal").click(function () {
        onCloseModal();
    })
})

/**
 * constant object error message
 * @type {{emptyKorTxt: string, emptyBenTxt: string}}
 */
const messageError = {
    emptyBenTxt: "m_n_bn_e", // Menu Name (Ben) can't be blank
    emptyKorTxt: "m_n_kr_e", // Menu Name (Kor) can't be blank
    emptyEngTxt: "m_n_en_e", // Menu Name (Kor) can't be blank
    tagIdExist: "dupmsg",
}

/* VARIABLE */
const messageDialog = {
    noData: "No matching records found.",
}

const tagIdElement = $("#tagId");


/* FUNCTION */
/**
 * This function initializes a DataTable with specific configurations and retrieves data from a server endpoint to populate the table.
 */
function initDataTable() {
    $("#tblMenu").removeAttr("width").DataTable({
        processing: false,
        serverSide: false, // Paging handle by the server side
        paging: true,
        searching: true,
        lengthChange: false,
        lengthMenu: [20, 40, 60],
        bInfo: false,
        ordering: false,
        autoWidth: true,
        language: {
            emptyTable: $.localeMessage('tb_empt_tx'),
            paginate: {
                "next": nextButton(),
                "previous" : previousButton()
            }
        },
        columnDefs: [
            {
                targets: [1,2,3,4],
                className: 'dt-body-left'
            }
        ],
        initComplete: function (){
            // showButtonExportData();
        },
        ajax: {
            url: "/api/menu-management/list", type: "GET", cache: false
        },
        columns: [
            {data: "seqNo"},
            {data: "menuNmEng", render: function (data, type, full, meta) {
                    return "<a href=\"#\" data-toggle=\"modal\" data-target=\"#modalMenuDetail\"  class='text-decoration-underline pl-65' onclick=\"showModalDetail(" + full.seqNo + ", '"+full.tagId+"')\">" + data + "</a>";
                }
            },
            {data: "menuNmBen"},
            {data: "menuNmKor"},
            {data: "mdfyDt", render (data, type, full, meta) {
                    let time = "";
                    if(data){
                        time = data.substring(0, 16);
                    }
                    return "<div>"+time+"</div>"
                }
            }
        ]
    });
}

/**
 * This function is used to display an export data button on a table.
 */
function showButtonExportData (){
    const table = $("#tblMenu").DataTable();
    new $.fn.dataTable.Buttons(table, {
        buttons: [
            { extend: 'excelHtml5', 
                className: 'btnExportExcel', 
                text: $.localeMessage("export"),
                attr: {style: 'width: 7em;'
                },
                filename: exportExcelName("Menu Management_"),
                title: exportExcelName("Menu Management_"),
                customize: function( xlsx ) {
                    var sheet = xlsx.xl.worksheets['sheet1.xml'];

                    $('row:first c', sheet).attr('s', '51');

                    $('row:nth-child(2) c', sheet).attr('s', '30');

                    $('row:not(:first):not(:nth-child(2)) c', sheet).attr('s', '25');

                },
                exportOptions: {
                    customizeData: function (d) {
                        d.body = d.body.map(row => {
                            return row.map(cell => {
                                return cell.length > 32767 ? cell.substring(0, 32767) : cell;
                            });
                        });
                        
                        const columnsToSort = [0];
                        d.body.sort(function(a, b) {
                            return sortData(a, b, columnsToSort);
                        });
                    }
                },
            }  
        ]
    }).container().appendTo($('.btnExport'));
}


/**
 * This function is used to show a modal with details of a menu item.
 @param menuSeq
 * @param menuSeq */
function showModalDetail (menuSeq, tagId) {
    // reset form when open modal
    $("#form")[0].reset();

    const modal = $("#modalMenuDetail");
    modal.attr("data-seq", menuSeq);
    modal.attr("tag-id", tagId);

    //get menu detail by seq
    $.ajax({
        url: '/api/menu-management/detail/'+menuSeq,
        success: function (res){
            const {
                menuNmBen, menuNmEng, menuNmKor, tagId, vwYn
            } = res.data

            // fill data to form
            $("#menuEngVal").text(menuNmEng);
            $("#menuBenVal").val(menuNmBen);
            $("#menuKorVal").val(menuNmKor);
            $("#visible").val(vwYn);
            $("#tagId").val(tagId);
        }
    })
    modal.show();
}

/**
 * This function is triggered when the button save is clicked.
 */
function formSubmit(){
    // Button for dialog duplicate
    const buttonsDuplicate = [{
        text: $.localeMessage('confirm'), class: 'btn btn-outline-secondary btn-sm', click: function () {
            closeDialog();
        }
    }]
    let isDuplicate = null;
    let errors = [];

    $("#btnSubmit").click(async function () {
        const modal = $("#modalMenuDetail");
        // get input value
        const menuSeqNo = modal.attr("data-seq");
        const menuBenVal = $("#menuBenVal").val();
        const menuKorVal = $("#menuKorVal").val();
        const visible = $("#visible").val();
        const tagId = $("#tagId").val();

        const currentTagId = modal.attr("tag-id");
        // validate

        if (!menuBenVal || menuBenVal === "") {
            errors.push($.localeMessage(messageError.emptyBenTxt))
        }
        if (!menuKorVal || menuKorVal === "") {
            errors.push($.localeMessage(messageError.emptyKorTxt))
        }

        // Show error
        if (errors.length > 0) {
            // show errors
            let dialogBody = "<div>";
            errors.forEach(function (item) {
                if (item) {
                    dialogBody += `<p class="${errors.length > 1 ? 'text-left' : 'text-center'}">${item}</p>`
                }
            })
            dialogBody += "</div>";

            function onClose() {
                errors = [];
            }

            showDialog($.localeMessage('warning'), dialogBody, [], onClose, true);
            return;
        }

        // modal type equal 'edit', skip check duplicate userId;
        if (currentTagId === tagId) {
            isDuplicate = false;
            onDuplicateFalse(tagIdElement);
        }
        //if duplicate is null => call func check duplicate
        if (isDuplicate === null) {
            const preCheckDuplicate = await checkDulicateTagId(tagId);
            if (preCheckDuplicate) {
                onDuplicateTrue(tagIdElement);
                showDialog($.localeMessage('duplicate'), $.localeMessage(messageError.tagIdExist), buttonsDuplicate, null, false);
                return;
            }
            // if duplicate === true => break
        } else if (isDuplicate === true) {
            onDuplicateTrue(tagIdElement);
            showDialog($.localeMessage('duplicate'), $.localeMessage(messageError.tagIdExist), buttonsDuplicate, null, false);
            return;
        }

        $.ajax({
            url: '/api/menu-management',
            method: "PUT",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                seqNo: menuSeqNo,
                menuNmBen: menuBenVal,
                menuNmKor: menuKorVal,
                vwYn: visible,
            }),
            language: {
                emptyTable: $.localeMessage('tb_empt_tx'),
                zeroRecords: $.localeMessage('tb_empt_tx'),
            },
            success: function (res) {
                if (res.success) {
                    // show dialog after create successful
                    const buttons = [{
                        text: $.localeMessage('confirm'), class: 'btn btn-outline-secondary btn-sm', click: function () {
                            onCloseModal();
                            closeDialog();
                            location.reload();
                        }
                    }]
                    showDialog($.localeMessage('result'), $.localeMessage('save_cplt'), buttons, null, false);
                }
            }
        })
    })

    // reset when change tag Id value
    $("#tagId").keyup(function (){
        isDuplicate = null;
        clearDuplicateStyle(tagIdElement);
    })

    // handle when click duplicate check button
    $("#duplicateCheckBtn").click( async function (e) {
        const modal = $("#modalMenuDetail");
        const modalType = modal.attr("modal-type");
        const tagId = tagIdElement.val();
        const currentTagId = modal.attr("tag-id");
        if( currentTagId === tagId){
            onDuplicateFalse($("#tagId"))
            isDuplicate = false;
            return;
        }
        const duplicate =  await checkDulicateTagId(tagId);
        if (duplicate) {
            isDuplicate = true;
            onDuplicateTrue(tagIdElement);
            showDialog($.localeMessage('duplicate'), $.localeMessage(messageError.tagIdExist), buttonsDuplicate, null, false);
        }else {
            isDuplicate = false;
            onDuplicateFalse(tagIdElement);
        }
    })

    /**
     * Checks if the tagId already exists or not
     * @param tagId
     * @returns {Promise<boolean>}
     */
    async function checkDulicateTagId(tagId) {
        let isDuplicate = true;
        try {
            const checkResult  = await $.ajax({
                url: '/api/menu-management/check-duplicate-tag-id/' + tagId,
            })
            if(checkResult.success){
                isDuplicate = true;
            }else{
                isDuplicate = false;
            }
        }catch (e) {
            isDuplicate = false;
        }

        return isDuplicate
    }
}

/**
 * this function is used to clean up and update certain elements and data after closing a modal.
 */
function onCloseModal() {
    const modal = $("#modalMenuDetail");
    modal.removeAttr("data-seq")
    modal.modal('hide');
    $("#tblMenu").DataTable().ajax.reload();
    clearDuplicateStyle($("#tagId"));
}