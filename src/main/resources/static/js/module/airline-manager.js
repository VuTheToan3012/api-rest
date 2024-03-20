/**
 * @author trungNV (trungnv.bks@gmail.com)
 * @since 2023-07-21
 */

$(document).ready(function () {
    initDataTable();
    formSubmit();
    onSearch();

    $("#btnCloseModal").click(function () {
        onCloseModal();
    })
})

/* VARIABLE */
const messageError = {
    emptyAirlineName: "empt_al_n", // Airline name can't be blank,
    emptyIata2Letter: "empt_iata2", // IATA 2LETTER can't be blank
    emptyIata3Digit: "empt_iata3", // IATA 3DIGIT can't be blank
    emptyIcaoCode: "empt_icao", // Icao Code can't be blank,
    duplicate: "dupmsg",
    iata2LetterLength: "iata2_len", // IATA 2Letter: The length must be 2 characters
    iata3DigitLength: "iata3_len", // IATA 3Digit: The length must be 3 characters
    iata3DigitOnlyNumberAllowed: "iata3_fm", // IATA 3Digit: Only number are allowed
    icaoCodeTooLong: "icao_long", // ICAO CODE: The maximum length is 3 characters
    delete: "delete",
    save: "save",
}

/* VARIABLE */
const messageDialog = {
    noData: "No matching records found.",
}

/* VALUE */
const modal = $("#modalAirlineInfo");
const modalType = modal.attr('modal-type');
const airlineName = $("#airlineName")
const iata2Letter = $("#iata2Letter")
const iata3Digit = $("#iata3Digit")
const icaoCode = $("#icaoCode")
const usage = $("#usage")
const logo = $("#airlineLogo")

/* FUNCTION */
/**
 * This function initializes a DataTable with specific configurations and retrieves data from a server endpoint to populate the table.
 */
function initDataTable() {
    const table = $("#tblAirline").removeAttr("width").DataTable({
        processing: false,
        serverSide: false, // Paging handle by the server side
        paging: true,
        searching: true,
        lengthChange: false,
        lengthMenu: [20, 40, 60],
        bInfo: false,
        ordering: false,
        autoWidth: true,
        initComplete: function (){
            // showButtonExportData();
        },
        language: {
            emptyTable: $.localeMessage('tb_empt_tx'),
            paginate: {
                "next": nextButton(),
                "previous" : previousButton()
            }
        },
        ajax: {
            url: "/api/airline-manager/list", type: "GET", cache: false
        },columnDefs: [
            {
                targets: [1,2,3,6,7],
                className: 'dt-body-left'
            }

        ],
        columns: [
            {data: "seqNo"},
            {
                data: "alNm", render: function (data, type, full, meta) {
                    return "<a href=\"#\" data-toggle=\"modal\" data-target=\"#modalMonitorInfo\" class='text-decoration-underline' onclick=\"showModal('edit', " + full.seqNo + ", '" + full.alCode3 + "', '" + full.alCode + "', '" + full.alCodea + "' )\">" + data + "</a>";
                }
            },

            {data: "alCode"},
            {data: "alCodea"},
            {data: "alCode3"},

            {
                data: "usageYn", render(data) {
                    return data === 1 ? "<b>Y</b>" : "N"
                }
            },
            {
                data: "mdfyDt", render(data, type, full, meta) {
                    let time = "";
                    if (full && full.mdfyDt) {
                        time = full.mdfyDt.substring(0, 16);
                    }
                    return "<div>" + time + "</div>"
                }
            },
            {data: "userNm"}
        ]
    });
}

function showButtonExportData (){
    const table = $("#tblAirline").DataTable();
    const buttons = new $.fn.dataTable.Buttons(table, {
        buttons: [
            { extend: 'excelHtml5', 
              className: 'btnExportExcel', 
              text: $.localeMessage("export"),
              attr:{
                style:'width:7em;'
                },
                filename: exportExcelName("Airline Manager_"),
                title: exportExcelName("Airline Manager_"),
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
                }
            }
        ]
    }).container().appendTo($('.btnExport'));
}


/**
 * This function is used to handle the form submission and validation for a user form.
 */
function formSubmit() {
    // Button for dialog duplicate
    const buttonsDuplicate = [{
        text: $.localeMessage("confirm"), class: 'btn btn-outline-secondary btn-sm', click: function () {
            closeDialog();
        }
    }]
    // Store errors message key
    let errors = [];

    // Flag check duplicate
    let isDuplicateIcaoCode = null;
    let isDuplicateIata2Letter = null;
    let isDuplicateIata3Digit = null;

    // Handle when clicked button save;
    $('#btnSubmit').click(async function (e) {
        e.preventDefault();
        /* get variable */
        const modalType = modal.attr('modal-type');
        const airlineNameValue = airlineName.val();
        const iata2LetterValue = iata2Letter.val();
        const iata3DigitValue = iata3Digit.val();
        const icaoCodeValue = icaoCode.val();
        const usageValue = usage.val();
        const logoValue = logo.attr("data-logo");
        // get from edit
        const currentIcaoCode = modal.attr("current-icaocode");
        const currentIata2Letter = modal.attr("current-iata2letter");
        const currentIata3Digit = modal.attr("current-iata3digit");
        const airlineSeqNo = modal.attr("airline-seq");
        /* validate */
        if (!airlineNameValue || airlineNameValue === "") {
            errors.push($.localeMessage(messageError.emptyAirlineName));
        }
        if (!iata2LetterValue || iata2LetterValue === "") {
            errors.push($.localeMessage(messageError.emptyIata2Letter));
        } else if (iata2LetterValue.length != 2) {
            errors.push($.localeMessage(messageError.iata2LetterLength));
        }
        if (!iata3DigitValue || iata3DigitValue === "") {
            errors.push($.localeMessage(messageError.emptyIata3Digit));
        } else if (iata3DigitValue.length != 3) {
            errors.push($.localeMessage(messageError.iata3DigitLength));
        } else if (!Regexs.isNumber.test(iata3DigitValue)) {
            errors.push($.localeMessage(messageError.iata3DigitOnlyNumberAllowed));
        }
        if (!icaoCodeValue || icaoCodeValue === "") {
            errors.push($.localeMessage(messageError.emptyIcaoCode));
        } else if (icaoCodeValue.length > 3) {
            errors.push($.localeMessage(messageError.icaoCodeTooLong));
        }

        // If error show dialog
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

            showDialog($.localeMessage("warning"), dialogBody, [], onClose, true);
        } else {
            let hasDuplicate = false;
            // Skip check duplicate when value don't change
            if (modalType === "edit") {
                if (currentIcaoCode === icaoCodeValue) {
                    isDuplicateIcaoCode = false;
                }
                if (currentIata2Letter === iata2LetterValue) {
                    isDuplicateIata2Letter = false;
                }
                if (currentIata3Digit === iata3DigitValue) {
                    isDuplicateIata3Digit = false;
                }
            }
            // check duplicate iata2Letter
            if (isDuplicateIata2Letter === null) {
                const preCheckDuplicate = await checkDulicateIata2Letter(iata2LetterValue);
                if (preCheckDuplicate) {
                    onDuplicateTrue(iata2Letter)
                    hasDuplicate = true;
                }
                // if duplicate === true => break
            } else if (isDuplicateIata2Letter === true) {
                onDuplicateTrue(iata2Letter)
                hasDuplicate = true;
            }
            // check duplicate iata3Digit
            if (isDuplicateIata3Digit === null) {
                const preCheckDuplicate = await checkDulicateIata3Digit(iata3DigitValue);
                if (preCheckDuplicate) {
                    onDuplicateTrue(iata3Digit)
                    hasDuplicate = true;
                }
                // if duplicate === true => break
            } else if (isDuplicateIata3Digit === true) {
                onDuplicateTrue(iata3Digit)
                hasDuplicate = true;
            }

            // check duplicate icaocode
            if (isDuplicateIcaoCode === null) {
                const preCheckDuplicate = await checkDulicateIcaoCode(icaoCodeValue);
                if (preCheckDuplicate) {
                    onDuplicateTrue(icaoCode);
                    hasDuplicate = true;
                }
                // if duplicate === true => break
            } else if (isDuplicateIcaoCode === true) {
                onDuplicateTrue(icaoCode);
                hasDuplicate = true;
            }

            if (hasDuplicate === true) {
                showDialog($.localeMessage('duplicate'), $.localeMessage(messageError.duplicate), buttonsDuplicate, null, false);
                return;
            }

            //     Call api submit
            if (modalType === 'add') {
                $.ajax({
                    url: '/api/airline-manager', method: 'POST',
                    data: JSON.stringify({
                        alNm: airlineNameValue,
                        alCode: iata2LetterValue,
                        alCode3: icaoCodeValue,
                        alCodea: iata3DigitValue,
                        usageYn: usageValue,
                        logoImg: logoValue,
                    }),
                    contentType: "application/json",
                    dataType: "json",
                    success: function (res) {
                        if (res.success) {
                            // show dialog after create successful
                            const buttons = [{
                                text: $.localeMessage("confirm"),
                                class: 'btn btn-outline-secondary btn-sm',
                                click: function () {
                                    closeDialog();
                                    onCloseModal();
                                    reloadDataTable(false);
                                }
                            }]
                            showDialog($.localeMessage('result'), $.localeMessage('save_cplt'), buttons, null, false);
                        }
                    },
                    error: function () {
                        showDialog($.localeMessage('error'), $.localeMessage('sth_w_w'), [], null, true);
                    }
                })
            } else {
                $.ajax({
                    url: '/api/airline-manager', method: 'PUT',
                    data: JSON.stringify({
                        alNm: airlineNameValue,
                        alCode: iata2LetterValue,
                        alCode3: icaoCodeValue,
                        alCodea: iata3DigitValue,
                        usageYn: usageValue,
                        logoImg: logoValue,
                        seqNo: airlineSeqNo,
                    }),
                    contentType: "application/json",
                    dataType: "json",
                    success: function (res) {

                        if (res.success) {

                            // show dialog when update successful
                            const buttons = [{
                                text: $.localeMessage('confirm'),
                                class: 'btn btn-outline-secondary btn-sm',
                                click: function () {
                                    closeDialog();
                                    onCloseModal();
                                    reloadDataTable(true);
                                }
                            }]
                            showDialog($.localeMessage('result'), $.localeMessage('save_cplt'), buttons, null, false);
                        }
                    },
                    error: function () {
                        showDialog($.localeMessage('error'), $.localeMessage('sth_w_w'), [], null, true);
                    }
                })
            }

        }
    })

    // reset flag when change input value duplicate
    $("#icaoCode").keyup(function () {
        isDuplicateIcaoCode = null;
        clearDuplicateStyle(icaoCode);
    })
    $("#iata2Letter").keyup(function () {
        isDuplicateIata2Letter = null;
        clearDuplicateStyle(iata2Letter);

    })
    $("#iata3Digit").keyup(function () {
        isDuplicateIata3Digit = null;
        clearDuplicateStyle(iata3Digit);
    })

    // handle when click duplicate check button
    $("#duplicateCheckBtn").click(async function (e) {
        const modal = $("#modalAirlineInfo");
        const modalType = modal.attr("modal-type");
        const currentIcaoCode = modal.attr("current-icaocode");
        const icaoCodeValue = icaoCode.val();
        if(modalType === "edit" && currentIcaoCode === icaoCodeValue){
            onDuplicateFalse($("#icaoCode"))
            isDuplicateIcaoCode = false;
            return;
        }
        const duplicate = await checkDulicateIcaoCode(icaoCodeValue);
        if (duplicate) {
            isDuplicateIcaoCode = true;
            onDuplicateTrue(icaoCode);
            showDialog($.localeMessage('duplicate'), $.localeMessage(messageError.duplicate), buttonsDuplicate, null, false);
        } else {
            onDuplicateFalse(icaoCode);
            isDuplicateIcaoCode = false;
        }
    })

    $("#duplicateCheckBtnIata2Letter").click(async function (e) {
        const modal = $("#modalAirlineInfo");
        const modalType = modal.attr("modal-type");
        const currentIata2Letter = modal.attr("current-iata2letter");
        const iata2letterValue = iata2Letter.val();
        if(modalType === "edit" && currentIata2Letter === iata2letterValue){
            onDuplicateFalse($("#iata2Letter"))
            isDuplicateIata2Letter = false;
            return;
        }
        const duplicate = await checkDulicateIata2Letter(iata2letterValue);
        if (duplicate) {
            isDuplicateIata2Letter = true;
            onDuplicateTrue(iata2Letter)
            showDialog($.localeMessage('duplicate'), $.localeMessage(messageError.duplicate), buttonsDuplicate, null, false);
        } else {
            onDuplicateFalse(iata2Letter)
            isDuplicateIata2Letter = false;
        }
    })

    $("#duplicateCheckBtnIata3Digit").click(async function (e) {

        const modal = $("#modalAirlineInfo");
        const modalType = modal.attr("modal-type");
        const currentIata3Digit = modal.attr("current-iata3digit");
        const iata3DigitValue = iata3Digit.val();
        if(modalType === "edit" && currentIata3Digit === iata3DigitValue){
            onDuplicateFalse($("#iata3Digit"))
            isDuplicateIata3Digit = false;
            return;
        }
        const duplicate = await checkDulicateIata3Digit(iata3DigitValue);
        if (duplicate) {
            isDuplicateIata3Digit = true;
            onDuplicateTrue(iata3Digit)
            showDialog($.localeMessage('duplicate'), $.localeMessage(messageError.duplicate), buttonsDuplicate, null, false);
        } else {
            onDuplicateFalse(iata3Digit)
            isDuplicateIata3Digit = false;
        }
    })

    // Trigger input type file click when click search logo button
    $("#uploadLogo").click(function () {
        // click to input type file
        $("#inputFile").click();
    })

    // on input change
    $("#inputFile").on('change', async function (e) {
        const file = e.target.files[0];
        // validate file
        const validImage = validateAirlineLogo(file);
        if (validImage) {
            // convert img to base64;
            const base64Img = await convertImageToBase64(file);
            $("#airlineLogo").attr("src", base64Img);
            $("#airlineLogo").attr('data-logo', base64Img);
        }
    })

    /**
     * Checks if the icaoCode already exists or not
     * @param icaoCode
     * @returns {Promise<boolean>}
     */
    async function checkDulicateIcaoCode(icaoCode) {
        let isDuplicate = true;
        try {
            const checkResult = await $.ajax({
                url: '/api/airline-manager/check-duplicate-icaocode/' + icaoCode,
            })
            isDuplicate = checkResult.success;
        } catch (e) {
            isDuplicate = false;
        }

        return isDuplicate
    }

    /**
     * Checks if the iata2Letter already exists or not
     * @param iata2Letter
     * @returns {Promise<boolean>}
     */
    async function checkDulicateIata2Letter(iata2Letter) {
        let isDuplicate = true;
        try {
            const checkResult = await $.ajax({
                url: '/api/airline-manager/check-duplicate-iata2letter/' + iata2Letter,
            })
            isDuplicate = checkResult.success;
        } catch (e) {
            isDuplicate = false;
        }

        return isDuplicate
    }

    /**
     * Checks if the iata3Digit already exists or not
     * @param iata3Digit
     * @returns {Promise<boolean>}
     */
    async function checkDulicateIata3Digit(iata3Digit) {
        let isDuplicate = true;
        try {
            const checkResult = await $.ajax({
                url: '/api/airline-manager/check-duplicate-iata3digit/' + iata3Digit,
            })
            isDuplicate = checkResult.success;
        } catch (e) {
            isDuplicate = false;
        }
        return isDuplicate
    }

    /**
     * This function is used to validate an airline logo file
     * @param file
     * @returns {boolean}
     */
    function validateAirlineLogo(file) {
        const allowedFormats = ['image/png', 'image/jpeg'];
        const maxSize = 260 * 70; // maximum size in pixels

        // Check if image format is allowed
        if (!allowedFormats.includes(file.type)) {
            const buttons = [{
                text: $.localeMessage("confirm"), class: 'btn btn-outline-secondary btn-sm', click: function () {
                    closeDialog();
                }
            }]
            showDialog($.localeMessage("wrong_fm"), $.localeMessage('wr_fm_img'), buttons, null, false);
            return false;
        }

        // Check if image size is within the limit
        if (file.size > maxSize) {
            const buttons = [{
                text: $.localeMessage("confirm"), class: 'btn btn-outline-secondary btn-sm', click: function () {
                    closeDialog();
                }
            }]
            showDialog($.localeMessage("wr_format"), $.localeMessage('acc_img_s'), buttons, null, false);
            return false;
        }

        return true;
    }
}

/**
 * This function is used to show a modal window create or monitor detail
 * @param type - add | edit
 * @param airlineSeqNo
 * @param currentIcaoCode
 * */
async function showModal(type, airlineSeqNo, currentIcaoCode, currentIata2Letter, currentIata3Digit) {
    const modal = $("#modalAirlineInfo");
    const btnSubmit = $("#btnSubmit");
    modal.attr("modal-type", type);
    // show text button submit
    if (type === "edit") {
        btnSubmit.text($.localeMessage("save"));
    } else {
        $("#form")[0].reset();
        btnSubmit.text($.localeMessage("save"));
    }
    if (type === "edit" && currentIcaoCode) {
        modal.attr('current-icaocode', currentIcaoCode);
        modal.attr('current-iata2letter', currentIata2Letter);
        modal.attr('current-iata3Digit', currentIata3Digit);
        modal.attr('airline-seq', airlineSeqNo);
        // show button delete when view detail
        const deleteButton = `<button type='button' style="width: 100px;background-color: #FD443D !important" class="btn btn-sm btn-danger font-weight-bold" onClick="deleteAirline(${airlineSeqNo})">${$.localeMessage('delete')}</button>`;
        // append delete button
        $(".rightAction").html(deleteButton);
        // fill data to input
        await $.ajax({
            url: '/api/airline-manager/detail/' + airlineSeqNo,
            success: function (res) {
                const data = res.data;
                const {alCode, alCode3, alCodea, alNm, logoImg, usageYn} = data;
                airlineName.val(alNm);
                iata2Letter.val(alCode);
                icaoCode.val(alCode3);
                iata3Digit.val(alCodea);
                usage.val(usageYn);
                logo.attr("src", logoImg);
                logo.attr("data-logo", logoImg);
            }, error: function () {
                showDialog('Something went wrong', 'Can\'t get airline detail', [], null, true);
            }
        })
    } else {
        const close = ` <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                             <span aria-hidden="true">&times;</span></button>`;
        $(".rightAction").html(close);
        logo.removeAttr("src");
        logo.removeAttr("data-logo");
    }
    modal.modal("show");
}

/**
 * This function is used to handle the closing of a modal window.
 */
function onCloseModal() {
    const modal = $("#modalAirlineInfo");
    modal.attr("modal-type", "");

    // remove delete button
    $(".rightAction").html("");
    // remove img data
    logo.removeAttr("src");
    logo.removeAttr("data-logo");
    $("#inputFile").val('');
    modal.modal('hide');

    clearDuplicateStyle($("#iata2Letter"));
    clearDuplicateStyle($("#iata3Digit"));
    clearDuplicateStyle($("#icaoCode"));
}

/**
 * This function is used to delete an airline from the system.
 * @param airlineSeqNo
 */
function deleteAirline(airlineSeqNo) {
    const buttons = [{
        text: $.localeMessage('yes'), class: 'btn btn-outline-secondary btn-sm', click: function () {
            $.ajax({
                url: '/api/airline-manager/' + airlineSeqNo, method: 'DELETE', success: function (res) {
                    if (res.success) {
                        showDialog('Result', 'Delete airline successfully!', [], () => {
                            onCloseModal();
                            reloadDataTable();
                        }, true)
                    } else {
                        showDialog('Result', 'Error when delete airline', [], null, true)
                    }

                }, error: function () {
                    showDialog('Something went wrong', 'Can\'t delete this airline', [], null, true);
                }
            })
        }
    }, {
        text: $.localeMessage('no'), class: 'btn btn-outline-secondary btn-sm', click: function () {
            closeDialog();
        }
    }]
    showDialog($.localeMessage("del_item"), $.localeMessage('chk_YN_del'), buttons, onCloseModal, false);
}


/**
 * This function is used to reload the data in a DataTable. It targets the table with the ID "tblAirline" and calls the DataTable() function on it
 */
function reloadDataTable(keepPage = true) {
    if(keepPage){
        $("#tblAirline").DataTable().ajax.reload(null, false);
    }else {
        $("#tblAirline").DataTable().ajax.reload();
    }
}

/**
 * This function handles the search filter
 */
function onSearch() {
    $(".btnSearch").click(function () {
        const searchAirlineName = $("#searchAirlineName");
        const searchIata2Letter = $("#searchIATA2Letter");
        const searchIata3Digit = $("#searchIATA3Digit");
        const searchIcaoCode = $("#searchIcaoCode");
        $("#tblAirline").DataTable().column(1).search(searchAirlineName.val().trim()).column(2).search(searchIata2Letter.val().trim()).column(3).search(searchIata3Digit.val().trim()).column(4).search(searchIcaoCode.val().trim()).draw();
    })
}

var inputSearchAirlineName = document.getElementById("searchAirlineName");
inputSearchAirlineName.addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        document.getElementById("btnSearch").click();
    }
});

var inputSearchIATA2Letter = document.getElementById("searchIATA2Letter");
inputSearchIATA2Letter.addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        document.getElementById("btnSearch").click();
    }
});

var inputSearchIATA3Digit = document.getElementById("searchIATA3Digit");
inputSearchIATA3Digit.addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        document.getElementById("btnSearch").click();
    }
});

var inputSearchIcaoCode = document.getElementById("searchIcaoCode");
inputSearchIcaoCode.addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        document.getElementById("btnSearch").click();
    }
});







