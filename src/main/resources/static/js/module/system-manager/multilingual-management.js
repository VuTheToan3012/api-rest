/**
 * @author TrungNV (trungnv.bks@gmail.com
 * @Since 2023-07-31
 */

$(document).ready(function () {
    initDataTable();
    formSubmit();
    onSearch();

    $("#btnCloseModal").click(function () {
        onCloseModal();
    })
})

const messageError = {
    tagIdDuplicate: "dupmsg",
    tagIdBlank: "tag_id_e", // TagId can't be blank
    tagItemCateBlank:"tag_cate_e", //item Category can't be blank
    itemNameBenBlank: "i_nm_bn_e", // Item Name (BEN) can't be blank
    itemNameEngBlank: "i_nm_en_e", // Item Name (ENG) can't be blank
    itemNameKorBlank: "i_nm_kr_e", // Item Name (KOR) can't be blank
}

/* VARIABLE */
const messageDialog = {
    noData: "No matching records found.",
}

const modal = $("#modalItemDetail");


/* FUNCTION */

/**
 * This function initializes a DataTable with specific configurations and retrieves data from a server endpoint to populate the table.
 */
function initDataTable() {
    $("#tblMultilingual").removeAttr("width").DataTable({
        processing: false,
        serverSide: false, // Paging handle by the server side
        paging: true,
        language: {
            emptyTable: $.localeMessage('tb_empt_tx'),
        },
        initComplete: function (){
            // showButtonExportData();
        },
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
        ajax: {
            url: "/api/multilingual-management/list", type: "GET", cache: false
        },
        columnDefs: [
            {
                targets: [1,2,3,4,5],
                className: 'dt-body-left'
            },
            {
                targets: [0],
                className: 'pl-0'
            },
            {
                targets: [1],
                className: 'pl-65'
            }
        ],
        columns: [
            {data: "seqNo"},
            {data: "itemCategory"},
            {
                data: "itemNmEng", render: function (data, type, full, meta) {
                    return "<a href=\"#\" data-toggle=\"modal\" data-target=\"#modalMenuDetail\"  class='text-decoration-underline pl-65'  onclick=\"showModal('edit'," + full.seqNo + ", '" + full.tagId + "')\">" + limitString(full.itemNmEng,30) + "</a>";
                }
            },
            {data: "itemNmBen",render(data,type,full,meta){
                    return limitString(full.itemNmBen, 30);
                }},
            {data: "itemNmKor",render(data,type,full,meta){
                    return limitString(full.itemNmKor, 30);
                }},
            {
                data: "mdfyDt", render(data, type, full, meta) {
                    let time = "";
                    if (data) {
                        time = data.substring(0, 16);
                    }
                    return "<div>" + time + "</div>"
                }
            }
        ]
    });
}

/**
 * This function is used to display an export data button on a table.
 */
function showButtonExportData (){
    const table = $("#tblMultilingual").DataTable();
    const buttons = new $.fn.dataTable.Buttons(table, {
        buttons: [
            { extend: 'excelHtml5', 
                className: 'btnExportExcel', 
                text: $.localeMessage("export"), 
                attr:{
                    style:'width:7em;'
                    },
                filename: exportExcelName("Multilingual Management_"),
                title: exportExcelName("Multilingual Management_"),
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
 * Function handle show modal
 * @param type
 * @param itemSeqNo
 * @param itemTagId
 */
function showModal(type, itemSeqNo, itemTagId) {
    modal.attr("modal-type", type);
    modal.attr("current-tag", itemTagId);
    modal.attr("current-seq", itemSeqNo);

    // reset form on modal open
    $("#form")[0].reset();

    if (type === 'edit') {
        $.ajax({
            url: '/api/multilingual-management/detail/' +
                itemSeqNo,
            success: function (res) {
                const data = res.data
                const {itemNmBen, itemNmEng, itemNmKor, tagId,itemCategory} = data;
                $("#itemEngVal").val(itemNmEng);
                $("#itemBenVal").val(itemNmBen);
                $("#itemKorVal").val(itemNmKor);
                $("#tagId").val(tagId);
                $("#itemCate").val(itemCategory);
            },
            error: function (e) {
                showDialog($.localeMessage('error'), $.localeMessage('cn_get_it'), [], null, true);
                return;
            }
        })
    }

    modal.modal("show");
}

/**
 * This function is used to handle the closing of a modal window.
 */
function onCloseModal() {
    modal.removeAttr("modal-type");
    modal.removeAttr("current-tag", '');
    modal.removeAttr("current-seq", '');
    $(".rightAction").html("");
    clearDuplicateStyle($("#tagId"));
    modal.modal('hide');
    $("#tblMultilingual").DataTable().ajax.reload();
}

/**
 * handle submit data
 */
function formSubmit() {

// Button for dialog duplicate
    const buttonsDuplicate = [{
        text: $.localeMessage("confirm"), class: 'btn btn-outline-secondary btn-sm', click: function () {
            closeDialog();
        }
    }]
    // flag check duplicate
    let isDuplicate = null;
    let errors = [];
    $("#btnSubmit").click(async function () {
        // get input value
        const tagId = $("#tagId").val();
        const itemCate=$("#itemCate").val();
        const itemNameEng = $("#itemEngVal").val();
        const itemNameBen = $("#itemBenVal").val();
        const itemNameKor = $("#itemKorVal").val();

        //get attr modal
        const modalType = modal.attr("modal-type");
        const currentTagId = modal.attr("current-tag");
        const currentSeqNo = modal.attr("current-seq");

        // validate
        if (!tagId || tagId === "") {
            errors.push(messageError.tagIdBlank);
        }
        if (!itemCate || itemCate === "") {
            errors.push(messageError.tagItemCateBlank);
        }
        if (!itemNameEng || itemNameEng === "") {
            errors.push(messageError.itemNameEngBlank);
        }
        if (!itemNameBen || itemNameBen === "") {
            errors.push(messageError.itemNameBenBlank);
        }
        if (!itemNameKor || itemNameKor === "") {
            errors.push(messageError.itemNameKorBlank);
        }
        if (errors.length > 0) {
            // show errors
            let dialogBody = "<div>";
            errors.forEach(function (item) {
                if (messageError[item]) {
                    dialogBody += `<p class="${errors.length > 1 ? 'text-left' : 'text-center'}">${messageError[item]}</p>`
                }
            })
            dialogBody += "</div>";

            function onClose() {
                errors = [];
            }

            showDialog($.localeMessage('warning'), dialogBody, [], onClose, true);
        } else {
            // modal type equal 'edit', skip check duplicate userId;
            if (modalType === 'edit' && currentTagId === tagId) {
                isDuplicate = false;
                onDuplicateFalse($("#tagId"))
            }
            //if duplicate is null => call func check duplicate
            if (isDuplicate === null) {
                const preCheckDuplicate = await checkDulicateTagId(tagId);
                if (preCheckDuplicate) {
                    onDuplicateTrue($("#tagId"));
                    showDialog($.localeMessage('duplicate'), $.localeMessage(messageError.tagIdDuplicate), buttonsDuplicate, null, false);
                    return;
                }
                // if duplicate === true => break
            } else if (isDuplicate === true) {
                onDuplicateTrue($("#tagId"));
                showDialog($.localeMessage('duplicate'), $.localeMessage(messageError.tagIdDuplicate), buttonsDuplicate, null, false);
                return;
            }
            if (modalType === "add") {
                $.ajax({
                    url: "/api/multilingual-management",
                    method: "POST",
                    contentType: "application/json",
                    dataType: "json",
                    data: JSON.stringify({
                        itemNmEng: itemNameEng,
                        itemNmBen: itemNameBen,
                        itemNmKor: itemNameKor,
                        tagId,
                    }),
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
                            showDialog($.localeMessage('confirm'), "<p style='margin-bottom: 0px;'>" + $.localeMessage('save_cplt') + "</p><p>" + $.localeMessage('save_cplt1') + "</p>", buttons, null, false);
                        }
                    },
                    error: function () {
                        showDialog($.localMessage('error'), $.localeMessage('sth_w_w'), [], null, true);
                    }
                })
            } else {
                $.ajax({
                    url: "/api/multilingual-management",
                    method: "PUT",
                    contentType: "application/json",
                    dataType: "json",
                    data: JSON.stringify({
                        itemNmEng: itemNameEng,
                        itemNmBen: itemNameBen,
                        itemNmKor: itemNameKor,
                        tagId,
                        seqNo: currentSeqNo,
                    }),
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
                            showDialog($.localeMessage('result'), "<p style='margin-bottom: 0px;'>" + $.localeMessage('save_cplt') + "</p><p>" + $.localeMessage('save_cplt1') + "</p>", buttons, null, false);
                        }
                    },
                    error: function () {
                        showDialog($.localeMessage('error'), $.localeMessage('sth_w_w'), [], null, true);
                    }
                })
            }
        }
    })


    // reset when change tagId value
    $("#tagId").keyup(function () {
        isDuplicate = null;
        clearDuplicateStyle($("#tagId"));
    })

    // handle when click duplicate check button
    $("#duplicateCheckBtn").click(async function (e) {
        const modal = $("#modalItemDetail");
        const modalType = modal.attr("modal-type");
        const currentTag = modal.attr("current-tag");
        const tagId = $("#tagId").val();
        if(modalType === "edit" && currentTag === tagId){
            onDuplicateFalse($("#tagId"))
            isDuplicate = false;
            return;
        }
        const duplicate = await checkDulicateTagId(tagId);
        if (duplicate) {
            isDuplicate = true;
            onDuplicateTrue($("#tagId"));
            showDialog($.localeMessage('duplicate'), $.localeMessage(messageError.tagIdDuplicate), buttonsDuplicate, null, false);
        } else {
            onDuplicateFalse($("#tagId"));
            isDuplicate = false;
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
            const checkResult = await $.ajax({
                url: '/api/multilingual-management/check-duplicate-tagId/' + tagId,
            })
            if (checkResult.success) {
                isDuplicate = true;
            } else {
                isDuplicate = false;
            }
        } catch (e) {
            isDuplicate = false;
        }

        return isDuplicate
    }
}

/**
 * This function handles the search filter
 */
function onSearch() {
    $(".btnSearch").click(function () {
        const searchItemEngName = $("#searchNameEng").val().trim();
        $("#tblMultilingual").DataTable().column(2).search(searchItemEngName).draw();
    })
}

var inputSearchNameEng = document.getElementById("searchNameEng");
inputSearchNameEng.addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        document.getElementById("btnSearch").click();
    }
});