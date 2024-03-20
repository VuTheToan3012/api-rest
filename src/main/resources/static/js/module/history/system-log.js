/**
 * @author TrungNV (trungnv.bks@gmail.com)
 * @since 2023-07-26
 */
$(document).ready(function () {
    initDatatable()
    onSearch()
    handleFilter();
    $("#btnCloseModal").click(function () {
        onCloseModal();
    })
    handleExport();
})

/* VARIABLE */
const messageDialog = {
    noData: "No matching records found.",
}

/**
 * init get data & show in table
 */
function initDatatable() {
    $("#tblSystemLog").removeAttr("width").DataTable({
        processing: false,
        serverSide: true, // Paging handle by the server side
        paging: true,
        searching: true,
        lengthChange: false,
        lengthMenu: [20, 40, 60],
        bInfo: false,
        ordering: false,
        autoWidth: true,
        language: {
            emptyTable: $.localeMessage('tb_empt_tx'),
            zeroRecords: $.localeMessage('tb_empt_tx'),
            paginate: {
                "next": nextButton(),
                "previous": previousButton()
            }
        },
        ajax: {
            url: "/api/system-log/list-pagination-search",
            type: "POST",
            cache: false,
            contentType: "application/json",
            data: function (d) {
                return JSON.stringify(d)
            }
        },
        columnDefs: [
            {
                targets: [3, 4],
                className: 'dt-body-left'
            }
        ],
        columns: [
            {
                data: "seqNo", render: function (data) {
                    return "<a href='#' class='text-decoration-underline' onclick='showModalDetailLog(" + data + ")'>" + data + "</a>"
                }
            },
            {
                data: "sysTyNm", render: function (data, type, full) {
                    switch (data) {
                        case "OPR-COLLECT":
                            return $.localeMessage(SYSTEM["OPR-COLLECT"]);
                        case "OPR-MONITOR":
                            return $.localeMessage(SYSTEM["OPR-MONITOR"]);
                        case "OPR-SITE":
                            return $.localeMessage(SYSTEM["OPR-SITE"]);
                        case "OPR-MANAGER":
                            return $.localeMessage(SYSTEM["OPR-MANAGER"]);
                        default:
                            return;
                    }
                }
            },
            {
                data: "logLvl", render: function (data, type, full) {
                    switch (data) {
                        case LOG_LEVEL.INFO:
                            return data
                        case  LOG_LEVEL.ERROR:
                            return `<span style="color: red">${data}</span>`
                        default:
                            return data;
                    }
                }
            },
            {
                data: "logData", width: "40%", render: function (data) {
                    return limitString(data, 50);
                }
            },
            {
                data: "occrrncdt", render(data, type, full, meta) {
                    let time = "";
                    if (data) {
                        time = data.substring(0, 16);
                    }
                    return time;
                }
            },
        ]
    });
}

function handleFilter() {
    $("#searchDate").datepicker({
        dateFormat: 'yy/mm/dd',
        placeholder: 'yyyy/mm/dd',
        showButtonPanel: true,
        closeText: 'Clear',
        onClose: function (dateText, inst) {
            if ($(window.event.srcElement).hasClass('ui-datepicker-close')) {
                document.getElementById(this.id).value = '';
            }
        }
    });
    // set default is today
    $('#searchDate').datepicker('setDate', new Date());
}

/**
 * hande modal detail
 * @param seqNo
 */
async function showModalDetailLog(seqNo) {
    const modal = $("#modalDetailSystemLog");
    // get detail
    let detailPromise = $.ajax({
        url: '/api/system-log/detail/' + seqNo,
        hideLoading: false
    });

    let relatePromise = detailPromise.then(res => {
        const {logId} = res.data;
        return $.ajax({
            url: '/api/protocol-log/list/' + logId,
            method: 'get',
            hideLoading: false
        });
    });

    let [detailRes, relateRes] = await Promise.all([detailPromise, relatePromise]);

    const {
        sysTyNm,
        logId,
        logLvl,
        logData,
        monSeqNo,
        countFilterData,
        occrrncdt
    } = detailRes.data;

    $("#logIdValue").val(logId);
    $("#systemValue").val(sysTyNm);
    $("#logLevelValue").val(logLvl);
    $("#dateValueData").val(occrrncdt);
    $("#logDataValue").val(logData);

    const relateData = relateRes.data;
    let stringDataTable = "";
    relateData.forEach(item => {
        stringDataTable += "<tr>" +
            "<td>" +
            item.seqNo +
            "</td><td>"
            +
            item.protNm
            +
            "</td><td>" +
            item.rslt
            +
            "</td><td class='text-blue text-decoration-underline' onclick='openProtocolLog(" + item.seqNo + ")' style='cursor: pointer'>" +
            item.occrrncdt +
            "</td>" +
            "</tr>"
    })
    $("#relateDatalog").html(stringDataTable)

    modal.modal('show');
}


function handleExport() {
    $(".btnExport").click(function () {
        $("#modalExport").modal('show');
    });
    const currentDate = new Date();
    const currentDateString = currentDate.toISOString().slice(0, 10).replace(/-/g, '/');

    $("#btnCloseModalExport").click(function () {
        $("#modalExport").modal('hide');
        $("#startDate").val(currentDateString);
        $("#endDate").val(currentDateString);
        $("#btnExportTemp").html("");
    })

    const startDate = $("#startDate")
    const endDate = $("#endDate")
    $("#startDate").val(currentDateString);
    $("#endDate").val(currentDateString);

    startDate.datepicker({
        dateFormat: 'yy/mm/dd',
        placeholder: 'yyyy/mm/dd'
    });
    endDate.datepicker({
        dateFormat: 'yy/mm/dd',
        placeholder: 'yyyy/mm/dd'
    });

    $("#btnSubmitExport").click(function () {
        if (!startDate.val() || !endDate.val()) {
            showDialog($.localeMessage("warning"), "Please enter complete information", [], null, true);
            return;
        } else if (new Date(endDate.val()) < new Date(startDate.val())) {
            showDialog($.localeMessage("warning"), "The end date must be greater than the start date", [], null, true);
            return;
        }
    })
}

async function creatExport() {
    // generate button export for data table
    async function getAllDataExport() {
        var startDate = $("#startDate");
        var endDate = $("#endDate");

        var jsonResult = await $.ajax({
            url: '/api/system-log/list-data-by-date',
            data: JSON.stringify({
                startDate: startDate.val(),
                endDate: endDate.val(),
            }),
            contentType: "application/json",
            method: "POST"
        });

        var exportBody = jsonResult.data;
        return exportBody.map(function (el) {
            const cols = [0, 1, 2, 3, 4]
            return cols.map(function (col) {
                switch (col) {
                    case 0:
                        return el.seqNo;
                    case 1:
                        switch (el.sysTyNm) {
                            case "OPR-MANAGER":
                                return $.localeMessage(SYSTEM["OPR-MANAGER"]);
                            case "OPR-SITE":
                                return $.localeMessage(SYSTEM["OPR-SITE"]);
                            case "OPR-MANAGER":
                                return $.localeMessage(SYSTEM["OPR-MANAGER"]);
                            case "OPR-COLLECT":
                                return $.localeMessage(SYSTEM["OPR-COLLECT"]);
                            case "OPR-MONITOR":
                                return $.localeMessage(SYSTEM["OPR-MONITOR"]);
                            default:
                                return "";
                        }
                    case 2:
                        switch (el.logLvl) {
                            case LOG_LEVEL.INFO:
                                return el.logLvl
                            case  LOG_LEVEL.ERROR:
                                return el.logLvl;
                            default:
                                return el.logLvl;
                        }
                    case 3:
                        return el.logData;
                    case 4:
                        return el.occrrncdt;
                }
            });
        });
    }

    var exportBody = await getAllDataExport();
    exportDataToExcel([{index: 0, order: 'asc'}], {
        0: 10,
        1: 25,
        3: 50,
        4: 25
    }, $('#tblSystemLog'), 'System_log_', exportBody);
}

/**
 * handle search function
 */
function onSearch() {
    $(".btnSearch").click(function () {
        let searchDate = $("#searchDate").val();
        const searchSystem = $("#searchSystem").val();
        const searchLogLevel = $("#searchLogLevel").val();

        if (searchDate.length > 0) {
            searchDate = searchDate.split('/').join('-');
        }

        $("#tblSystemLog").DataTable().column(1).search(searchSystem).column(2).search(searchLogLevel).column(4).search(searchDate).draw();
    })
}


function openProtocolLog(protocolLogId) {
    var btnCloseX1 = document.getElementById('btnCloseX1');
    btnCloseX1.style.display = 'none';
    const modalContent = $(".modal-content");
    const operationLogContent = $(".operationLogContent");
    const protocolLogContent = $(".protocolLogContent");
    const borderCenter = $(".borderCenter");
    borderCenter.show();

    modalContent.css({
        width: "100%"
    });
    operationLogContent.css({
        width: "50%"
    })
    protocolLogContent.css({
        display: "block"
    })
    $.ajax({
        url: '/api/protocol-log/detail/' + protocolLogId,
        method: "GET",
        success: function (res) {
            const {protNm, rslt, reqData, repnData, occrrncdt} = res.data;
            $("#protocolNameValue").val(protNm);
            $("#resultValue").val(rslt);
            $("#dateValue").val(occrrncdt);
            $("#requestDataValue").val(reqData);
            $("#resDataValue").val(repnData);
        }
    })
}

function closeProtocolLog() {
    var btnCloseX1 = document.getElementById('btnCloseX1');
    btnCloseX1.style.display = 'block';
    const modalContent = $(".modal-content");
    const modalContentLog = $(".modal-content-log");

    const operationLogContent = $(".operationLogContent");
    const protocolLogContent = $(".protocolLogContent");
    const borderCenter = $(".borderCenter");
    borderCenter.hide();
    modalContent.css({
        width: "50%"
    });
    modalContentLog.css({
        width: "100%"
    });
    operationLogContent.css({
        width: "100%"
    })
    protocolLogContent.css({
        display: "none"
    })
    $("#protocolForm")[0].reset();
}

function onCloseModal() {
    closeProtocolLog();
    $("#relateDatalog").html('');
    $("#operationForm")[0].reset();
    document.getElementById("logDataValue").setAttribute('style', 'height: 100px; max-height: 150px; overflow-y: scroll; resize: vertical');
    document.getElementById("requestDataValue").setAttribute('style', 'resize: vertical; height: 100px; max-height: 150px; overflow-y: scroll');
    document.getElementById("resDataValue").setAttribute('style', 'resize: vertical; height: 100px; max-height: 150px; overflow-y: scroll');
}

/**
 * Check The Validity Of The Time
 * @param input
 */
function limitInputLength(input) {
    var year = new Date().getFullYear();
    var value = input.value;
    var parts = value.split('/');
    if (parts[0] && parts[0].length > 4) {
        parts[0] = year.toString();
    }
    if (parts[1] && (parts[1].length > 2 || parseInt(parts[1]) > 12)) {
        parts[1] = '01';
    }
    if (parts[2] && (parts[2].length > 2 || parseInt(parts[2]) > 31)) {
        parts[2] = '01';
    }
    // Only add '/' if year and month have been fully entered
    if (parts.length === 1 && parts[0].length === 4) {
        input.value = parts.join('/') + '/';
    } else if (parts.length === 2 && parts[1].length === 2) {
        input.value = parts.join('/') + '/';
    } else {
        input.value = parts.join('/');
    }
}