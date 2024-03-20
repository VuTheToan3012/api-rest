/**
 * Environment Management
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-12-22
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
const messageDialog = {
    noData: "No matching records found.",
}

const modal = $("#modalEnvInfo");


/* FUNCTION */

/**
 * This function initializes a DataTable with specific configurations and retrieves data from a server endpoint to populate the table.
 */
function initDataTable() {
    $("#tblEnvMng").removeAttr("width").DataTable({
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
            url: "/api/environment-management/list", type: "GET", cache: false
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
            {data: "envCtgry"},
            {
                data: "envPropNm" , render(data, type, full, meta) {
                    return "<a href=\"#\" data-toggle=\"modal\" data-target=\"#modalEnvInfo\" class='text-decoration-underline' onclick=\"showModal('edit'," + full.seqNo + ")\">" + limitString(data,100) + "</a>";
                }
            },
            {
                data: "envPropVal", render(data, type, full, meta) {
                    return limitString(data,35);
                }
            },
            {
                data: "mdfyDt", render(data, type, full, meta) {
                    let time = "";
                    if (data) {
                        time = data.substring(0, 16);
                    }
                    return "<div>" + time + "</div>"
                }
            },
            {data: "userNm"},
        ]
    });
}

/**
 * List maximum properties
 * @type {{IC_NOTIFY_FONT_SIZE: string, IC_CUSTOMER_DATA: string, EX_FLIGHT_IMAGE_DATA: {width: string, height: string}, EX_ULD_NO_COL_NAME: string, IC_NOTIFY_IMAGE_DATA: {width: string, height: string}, EN_NOTIFY_IMAGE_DATA: {width: string, height: string}, ES_DATE_DATA: string, EX_BAY_NO_COL_NAME: string, ET_NOTIFY_FONT_SIZE: string, IX_MONITOR_NAME: string, IX_DATE_DATA: string, IN_FLIGHT_COL_NAME: string, ES_NOTIFY_IMAGE_DATA: {width: string, height: string}, IC_DATE_TIME: string, ES_AWB_DATA: string, EX_PAGINNATION: string, IN_NOTIFY_FONT_SIZE: string, ET_NOTIFY_IMAGE_DATA: {width: string, height: string}, EX_BAY_NO_DATA: string, IX_FLIGHT_IMAGE_DATA: {width: string, height: string}, IC_DATE_COL_NAME: string, IX_ULD_COL_DATA: string, ES_NOTIFY_FONT_SIZE: string, IN_NOTIFY_IMAGE_DATA: {width: string, height: string}, IX_FLIGHT_COL_NAME: string, ET_DATE_TIME: string, IT_MONITOR_NAME: string, IT_NOTIFY_IMAGE_DATA: {width: string, height: string}, IX_DATE_TIME: string, IC_AWB_QTY_DATA: string, ES_DATE_TIME: string, IN_BAYNO_COL_NAME: string, IC_FLIGHT_IMAGE_DATA: {width: string, height: string}, ES_SPECIAL_DATA: string, EX_ETD_DATA: string, EX_NOTIFY_IMAGE_DATA: {width: string, height: string}, IN_FLIGHT_DATA: string, ET_MONITOR_NAME: string, IC_MONITOR_NAME: string, ES_MONITOR_NAME: string, IN_STA_DATA: string, IX_NOTIFY_IMAGE_DATA: {width: string, height: string}, EX_ULD_NO_DATA: string, IN_MONITOR_NAME: string, ES_FLIGHT_COL_NAME: string, ET_TRUCK_NUM_COL_NAME: string, IN_STATUS_DATA: string, EN_MONITOR_NAME: string, IN_STATUS_COL_NAME: string, IN_STA_COL_NAME: string, IT_NOTIFY_FONT_SIZE: string, IC_FLIGHT_DATA: string, IC_PAGINNATION: string, IC_CUSTOMER_COL_NAME: string, IC_AWB_QTY_COL_NAME: string, ES_SPECIAL_COL_NAME: string, EX_FLIGHT_COL_NAME: string, EX_STD_COL_NAME: string, EN_ULD_NO_DATA: string, EX_NOTIFY_FONT_SIZE: string, EN_NOTIFY_FONT_SIZE: string, IN_BAYNO_DATA: string, EX_STD_DATA: string, IX_ULD_COL_NAME: string, IC_AWB_COL_NAME: string, IN_ETA_DATA: string, ES_AWB_COL_NAME: string, IT_DATE_TIME: string, IN_ETA_COL_NAME: string, IC_FLIGHT_COL_NAME: string, ES_DATE_COL_NAME: string, IT_TRUCK_DATA: string, EX_MONITOR_NAME: string, ET_TRUCK_DATA: string, EN_PAGINNATION: string, ES_FLIGHT_DATA: string, IN_PAGINNATION: string, IT_TRUCKNUM_COL_NAME: string, ES_PAGINNATION: string, IX_DATE_COL_NAME: string, EN_FLIGHT_COL_NAME: string, IX_PAGINNATION: string, EN_FLIGHT_IMAGE_DATA: {width: string, height: string}, ES_AWB_QTY_DATA: string, EX_FLIGHT_DATA: string, ES_FLIGHT_IMAGE_DATA: {width: string, height: string}, IN_FLIGHT_IMAGE_DATA: {width: string, height: string}, IC_DATE_DATA: string, ES_AWB_QTY_COL_NAME: string, EX_DATE_TIME: string, EN_STD_COL_NAME: string, IC_AWB_DATA: string, IX_FLIGHT_DATA: string, IX_NOTIFY_FONT_SIZE: string, EN_DATE_TIME: string, EX_ETD_COL_NAME: string}}
 */
const listMaximumPropertiesSize = {
    "ET_DATE_TIME": "50px",
    "ES_DATE_TIME": "50px",
    "EN_DATE_TIME": "50px",
    "EX_DATE_TIME": "50px",
    "IT_DATE_TIME": "50px",
    "IC_DATE_TIME": "50px",
    "IX_DATE_TIME": "50px",
    "ET_MONITOR_NAME": "110px",
    "ES_MONITOR_NAME": "80px",
    "EN_MONITOR_NAME": "80px",
    "EX_MONITOR_NAME": "80px",
    "IT_MONITOR_NAME": "110px",
    "IC_MONITOR_NAME": "80px",
    "IX_MONITOR_NAME": "80px",
    "IN_MONITOR_NAME": "110px",
    "ES_PAGINNATION": "80px",
    "EN_PAGINNATION": "80px",
    "EX_PAGINNATION": "80px",
    "IC_PAGINNATION": "80px",
    "IX_PAGINNATION": "80px",
    "IN_PAGINNATION": "80px",
    "IC_DATE_COL_NAME": "60px",
    "IC_DATE_DATA": "60px",
    "IC_FLIGHT_COL_NAME": "75px",
    "IC_FLIGHT_DATA": "60px",
    "IC_FLIGHT_IMAGE_DATA": {"width" : "100%" , "height" : "13vh" },
    "IC_AWB_COL_NAME": "120px",
    "IC_AWB_DATA": "60px",
    "IC_AWB_QTY_COL_NAME": "60px",
    "IC_AWB_QTY_DATA": "70px",
    "IC_CUSTOMER_COL_NAME": "80px",
    "IC_CUSTOMER_DATA": "90px",
    "IT_TRUCKNUM_COL_NAME": "115px",
    "IT_TRUCK_DATA": "260px",
    "IX_DATE_COL_NAME": "110px",
    "IX_DATE_DATA": "110px",
    "IX_FLIGHT_COL_NAME": "110px",
    "IX_FLIGHT_DATA": "100px",
    "IX_FLIGHT_IMAGE_DATA": {
        "width": "100%",
        "height": "13vh"
    },
    "IX_ULD_COL_NAME": "100px",
    "IX_ULD_COL_DATA": "120px",
    "IN_STA_COL_NAME": "120px",
    "IN_STA_DATA": "80px",
    "IN_ATA_COL_NAME": "120px",
    "IN_ATA_DATA": "80px",
    "IN_ETA_COL_NAME": "120px",
    "IN_ETA_DATA": "80px",
    "IN_FLIGHT_COL_NAME": "120px",
    "IN_FLIGHT_DATA": "85px",
    "IN_FLIGHT_IMAGE_DATA": {
        "width": "100%",
        "height": "13vh"
    },
    "IN_STATUS_COL_NAME": "85px",
    "IN_STATUS_DATA": "70px",
    "IN_BAYNO_COL_NAME": "60px",
    "IN_BAYNO_DATA": "80px",
    "ET_TRUCK_NUM_COL_NAME": "120px",
    "ET_TRUCK_DATA": "260px",
    "ES_DATE_COL_NAME": "60px",
    "ES_DATE_DATA": "60px",
    "ES_FLIGHT_COL_NAME": "75px",
    "ES_FLIGHT_DATA": "60px",
    "ES_FLIGHT_IMAGE_DATA": {
        "width": "100%",
        "height": "13vh"
    },
    "ES_AWB_COL_NAME": "120px",
    "ES_AWB_DATA": "60px",
    "ES_AWB_QTY_COL_NAME": "65px",
    "ES_AWB_QTY_DATA": "100px",
    "ES_SPECIAL_COL_NAME": "60px",
    "ES_SPECIAL_DATA": "90px",
    "EX_STD_COL_NAME": "110px",
    "EX_STD_DATA": "80px",
    "EX_ETD_COL_NAME": "110px",
    "EX_ETD_DATA": "80px",
    "EX_FLIGHT_COL_NAME": "100px",
    "EX_FLIGHT_DATA": "80px",
    "EX_FLIGHT_IMAGE_DATA": {
        "width": "100%",
        "height": "13vh"
    },
    "EX_ULD_NO_COL_NAME": "120px",
    "EX_ULD_NO_DATA": "80px",
    "EX_BAY_NO_COL_NAME": "58px",
    "EX_BAY_NO_DATA": "80px",
    "EN_STD_COL_NAME": "130px",
    "EN_FLIGHT_COL_NAME": "130px",
    "EN_FLIGHT_IMAGE_DATA": {
        "width": "100%",
        "height": "13vh"
    },
    "EN_ULD_NO_DATA": "180px",
    "ET_NOTIFY_IMAGE_DATA": {
        "width": "130px",
        "height": "130px"
    },
    "ES_NOTIFY_IMAGE_DATA": {
        "width": "130px",
        "height": "130px"
    },
    "EN_NOTIFY_IMAGE_DATA": {
        "width": "130px",
        "height": "130px"
    },
    "EX_NOTIFY_IMAGE_DATA": {
        "width": "130px",
        "height": "130px"
    },
    "IT_NOTIFY_IMAGE_DATA": {
        "width": "130px",
        "height": "130px"
    },
    "IC_NOTIFY_IMAGE_DATA": {
        "width": "130px",
        "height": "130px"
    },
    "IX_NOTIFY_IMAGE_DATA": {
        "width": "130px",
        "height": "130px"
    },
    "IN_NOTIFY_IMAGE_DATA": {
        "width": "130px",
        "height": "130px"
    },
    "ET_NOTIFY_FONT_SIZE": "120px",
    "ES_NOTIFY_FONT_SIZE": "120px",
    "EN_NOTIFY_FONT_SIZE": "120px",
    "EX_NOTIFY_FONT_SIZE": "120px",
    "IT_NOTIFY_FONT_SIZE": "120px",
    "IC_NOTIFY_FONT_SIZE": "120px",
    "IN_NOTIFY_FONT_SIZE": "120px",
    "IX_NOTIFY_FONT_SIZE": "120px"
}

/**
 * Check maximum size of properties
 * @param envCtgry
 */
function checkMaximumSizeProp(envCtgry) {
    if (listMaximumPropertiesSize.hasOwnProperty(envCtgry)) {
        const sizeValue = listMaximumPropertiesSize[envCtgry];

        const divElement = document.getElementById("prop_max_value");
        if (divElement) {
            if (typeof sizeValue === 'object' && sizeValue !== null){
                divElement.textContent = JSON.stringify(sizeValue);
            }else {
                divElement.textContent = sizeValue;
            }
        } else {
            console.log("not found id prop_wn_n");
        }
    } else {
        console.log("not found prop " + envCtgry + " in listMaximumPropertiesSize");
    }
}

/**
 * Check prop value before save
 * @returns {boolean}
 */
function checkPropValue() {
    // show dialog after create successful
    const buttons = [{
        text: $.localeMessage('confirm'), class: 'btn btn-outline-secondary btn-sm', click: function () {
            closeDialog();
        }
    }]
    const propNm = modal.attr('current-envPropNm');
    let inputValue = document.getElementById("prop_val").value;

    // Check if inputValue is a JSON-like string
    if (inputValue.startsWith("{")) {
        inputValue = inputValue.replace(/'/g, '"'); // replace single quotes with double quotes
        inputValue = inputValue.slice(1, -2).split(',').reduce((obj, item) => {
            const [key, value] = item.split(':');
            obj[key.trim()] = value.trim().replace(/\"|/g, '');
            return obj;
        }, {});
    }

    if (listMaximumPropertiesSize.hasOwnProperty(propNm)) {
        const sizeValue = listMaximumPropertiesSize[propNm];

        if (typeof sizeValue === 'object' && sizeValue !== null && propNm.includes('IMAGE_DATA')) {
            const inputValues = parseValue(inputValue);
            const maxValues = parseValue(sizeValue);

            for (let prop in inputValues) {
                if (inputValues[prop] > maxValues[prop]) {
                    document.getElementById("prop_val").style.border = "2px solid red";
                    showDialog($.localeMessage('warning'), $.localeMessage('prop_wn_s') + JSON.stringify(sizeValue),buttons , null, false);
                    return false;
                }else {
                    document.getElementById("prop_val").style.border = "";
                }
            }
            return true;

        } else {
            const inputNumber = parseFloat(inputValue);
            const sizeNumber = parseFloat(sizeValue);

            if (inputNumber > sizeNumber) {
                document.getElementById("prop_val").style.border = "2px solid red";
                showDialog($.localeMessage('result'), $.localeMessage('prop_wn_s') + JSON.stringify(sizeValue),buttons , null, false);
                return false;
            } else {
                document.getElementById("prop_val").style.border = "";
                return true;
            }
        }
    } else {
        console.log("not found prop " + propNm + " in listMaximumPropertiesSize");
    }
}

function parseValue(value) {
    if (typeof value === 'string') {
        return parseFloat(value);
    } else if (typeof value === 'object') {
        return {width: parseFloat(value.width), height: parseFloat(value.height)};
    }
    return value;
}

/**
 * Function handle show modal
 * @param type
 * @param itemSeqNo
 */
function showModal(type, itemSeqNo) {
    modal.attr('modal-type', type);
    modal.attr('current-seq', itemSeqNo);

    // reset form on modal open
    $("#form")[0].reset();

    if (type === 'edit') {
        $.ajax({
            url: '/api/environment-management/detail/' +
                itemSeqNo,
            success: function (res) {
                const data = res.data
                const {envCtgry, envPropNm, envPropVal, note, envPropValTy} = data;
                modal.attr('current-envPropValTy', envPropValTy);
                modal.attr('current-envPropNm', envPropNm);
                $("#env_ctgry").val(envCtgry);
                $("#prop_nm").val(envPropNm);
                $("#prop_val").val(envPropVal);
                if (envPropValTy === "NUMBER"){
                    $("#prop_wn_n").css('display', 'block');
                    $("#prop_wn_s").css('display', 'none');
                    $("#prop_val").attr('type', 'number');
                }else if(envPropValTy === "STRING"){
                    $("#prop_wn_n").css('display', 'none');
                    $("#prop_wn_s").css('display', 'block');
                    $("#prop_val").attr('type', 'text');
                    checkMaximumSizeProp(envPropNm);
                }
                $("#des").val(note);
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
    modal.removeAttr("current-seq", '');
    modal.removeAttr('current-envPropValTy', '');
    modal.removeAttr('current-envPropNm', '');
    document.getElementById("prop_val").style.border = "";
    document.getElementById("des").setAttribute('style', 'width: 80% !important; resize: vertical; height: 100px; max-height: 200px; overflow-y: scroll');
    $("#prop_val").removeAttr('type', '');
    $(".rightAction").html("");
    modal.modal('hide');
}

/**
 * handle submit data
 */
function formSubmit() {
    let errors = [];
    $("#btnSubmit").click(async function () {
        const seqNo = modal.attr("current-seq");
        checkPropValue();
        if (checkPropValue() === false){
            return ;
        }
        else {
            $.ajax({
                url: "/api/environment-management",
                method: "PUT",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    seqNo : seqNo,
                    envPropVal : $("#prop_val").val(),
                    note : $("#des").val(),
                }),
                success: function (res) {
                    if (res.success) {
                        reloadDataTable(true);
                        // show dialog after create successful
                        const buttons = [{
                            text: $.localeMessage('confirm'), class: 'btn btn-outline-secondary btn-sm', click: function () {
                                onCloseModal();
                                closeDialog();
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
    })
}
/**
 * This function handles the search filter
 */
function onSearch() {
    $(".btnSearch").click(function () {
        const searchPropertyName = $("#searchPropertyName").val().trim();
        $("#tblEnvMng").DataTable().column(2).search(searchPropertyName).draw();
    })
}

var inputSearchNameEng = document.getElementById("searchPropertyName");
inputSearchNameEng.addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        document.getElementById("btnSearch").click();
    }
});

/**
 * This function is used to reload the data in a DataTable. It targets the table with the ID "tblMonitor" and calls the DataTable() function on it
 */
function reloadDataTable(keepPage = true) {
    if(keepPage){
        $("#tblEnvMng").DataTable().ajax.reload(null, false);
    }else {
        $("#tblEnvMng").DataTable().ajax.reload();
    }
}