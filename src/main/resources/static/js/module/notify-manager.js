/**
 * @author trungNV ( trungnv.bks@gmail.com)
 * @since 2023-07-27
 */

$(document).ready(function () {
    initialDataTable();
    handleFilter();
    handleNotifyLength();
    formSubmit();
    onSearch();
    closeModal()
})

const NOTIFY_LOCATION_BY_TYPE = {
    all: [
        {label: "ALL", value: ""},
        {label: "ALL Export", value: 9},
        {label: "ALL Import", value: 10},
        ...SEARCH_LOCATION_BY_TYPE.all.slice(1, SEARCH_LOCATION_BY_TYPE.all.size)
    ],
    allAll: [
        {label: "ALL", value:""},
    ],
    import: [
        {label: "ALL", value: 10},
        ...SEARCH_LOCATION_BY_TYPE.import.slice(1, SEARCH_LOCATION_BY_TYPE.import.size)
    ],
    export: [
        {label: "ALL", value: 9},
        ...SEARCH_LOCATION_BY_TYPE.export.slice(1, SEARCH_LOCATION_BY_TYPE.export.size)
    ],
}

const SEARCH_NOTIFY_LOCATION_BY_TYPE = {
    all: [
        {label: "ALL", value: ""},
        {label: "ALL Export", value: 9},
        {label: "ALL Import", value: 10},
        ...SEARCH_LOCATION_BY_TYPE.all.slice(1, SEARCH_LOCATION_BY_TYPE.all.size)
    ],
    import: [
        {label: "ALL(Import)", value: ""},
        {label: "ALL", value: 10},
        ...SEARCH_LOCATION_BY_TYPE.import.slice(1, SEARCH_LOCATION_BY_TYPE.import.size)
    ],
    export: [
        {label: "ALL(Export)", value: ""},
        {label: "ALL", value: 9},
        ...SEARCH_LOCATION_BY_TYPE.export.slice(1, SEARCH_LOCATION_BY_TYPE.export.size)
    ],
}

const errorMessage = {
    emptyNotifyEng: "n_en_empt", // Notify (ENG) can't be blank
    emptyNotifyBen: "n_bn_empt", // Notify (BEN) can't be blank
    notifyEngTooLong: "n_en_long", // Notify (ENG): The maximum length is 255 characters
    notifyBenTooLong: "n_bn_long" // Notify (BEN): The maximum length is 255 characters,
}
const modal = $('#modalNotifyDetail');
const btnSubmitElement = $("#btnSubmit");
const notifyEng = $("#notifyEng")
const notifyBen = $("#notifyBen")

function initialDataTable() {
    // fill option search location
    fillOption(SEARCH_NOTIFY_LOCATION_BY_TYPE.all, $("#searchLocation"));
    $("#searchType").change(function (e) {
        if (+e.target.value === WKTYPE.IMPORT) {
            fillOption(SEARCH_NOTIFY_LOCATION_BY_TYPE.import, $("#searchLocation"));
        } else if (+e.target.value === WKTYPE.EXPORT){
            fillOption(SEARCH_NOTIFY_LOCATION_BY_TYPE.export, $("#searchLocation"));
        } else {
            fillOption(SEARCH_NOTIFY_LOCATION_BY_TYPE.all, $("#searchLocation"));
        }
    })

    $("#tblNotify").removeAttr("width").DataTable({
        processing: false,
        serverSide: true, // Paging handle by the server side
        paging: true,
        searching: true,
        lengthChange: false,
        language: {
            emptyTable: $.localeMessage('tb_empt_tx'),
            paginate: {
                "next": nextButton(),
                "previous" : previousButton()
            }
        },
        "initComplete": function (){
            // showButtonExportData()
        },
        lengthMenu: [20, 40, 60],
        bInfo: false,
        ordering: false,
        autoWidth: true,
        ajax: {
            url: "/api/cids-notify-manager/list-pagination-search",
            type: "POST",
            cache: false,
            contentType: "application/json",
            data: function (d) {
                return JSON.stringify(d);
            }
        },
        columns: [
            {
                data: "seqNo" ,render: function (data, type, full, meta) {
                    return "<a href=\"#\" data-toggle=\"modal\" data-target=\"#modalMonitorInfo\" class='text-decoration-underline' onclick=\"showModal('edit'," + full.seqNo + ")\">" + data + "</a>";
                }
            },
            {
                data: "wkTySeqNo",className: "pl-65", width: "8%", render(data, type, full) {
                    return full.wkTyNm
                }
            },
            {
                data: "locTySeqNo",className: "pl-65", render(data, type, full) {
                    if(full.locNm){
                        return full.locNm
                    }else{
                        return full.wkTySeqNo === 3 ? "ALL" : '';
                    }
                }
            }, {
                data: "notiEng", width:"35%", render(data, type, full, meta) {
                    return limitString(full.notiEng, 20) + " | " + limitString(full.notiBen, 20)
                }
            },
            {
                data: "msgFwdYn", render(data) {
                    return data === 1 ? "<b>Y</b>" : "N";
                }
            },
            {
                data: "regDt", render(data, type, full, meta) {
                    let time = "";
                    if (data) {
                        time = data.substring(0, 16);
                    }
                    return full.msgFwdYn == 1 ? time : ''
                }
            },
            {
                data: "userNm"
            },
        ]
    });
}

function showButtonExportData (){
    const table = $("#tblNotify").DataTable();

    function getAllDataExport (){
        var jsonResult = $.ajax({
            url: '/api/cids-notify-manager/list-pagination-search',
            data: JSON.stringify({
                search: {},
                order: [],
                columns: [],
                draw: 10,
                start: 0,
                length: 2400000,
            }),
            contentType: "application/json",
            method: "POST",
            success: function (result) {},
            async: false
        });
        var exportBody = jsonResult.responseJSON.data;
        return exportBody.map(function (el) {
            const cols = [0, 1, 2, 3, 4, 5, 6]
            return cols.map(function (col) {
                switch (col){
                    case 0:
                        return el.seqNo;
                    case 1:
                        return el.wkTyNm;
                    case 2:
                        return el.locNm;
                    case 3:
                        return el.notiEng + " | " + el.notiBen;
                    case 4:
                        return el.msgFwdYn ? "Y" : "N";
                    case 5:
                        return el.mdfyDt;
                    case 6:
                        return el.userNm;
                }
            });
        });
    }
    new $.fn.dataTable.Buttons(table, {
        buttons: [
            {
                extend: 'excel',
                className: 'btnExportExcel',
                text: $.localeMessage("export"),
                attr:{style:'width:7em;'},
                exportOptions: {
                    customizeData: function (d) {
                        var exportBody = getAllDataExport();
                        d.body.length = 0;
                        d.body.push.apply(d.body, exportBody);
                    }
                }
            }
        ]
    }).container().appendTo($('.btnExport'));
}

function closeModal(){
    $("#btnCloseModal").click(function(){
        onCloseModal();
    })
    $(".close").click(function(){
        onCloseModal();
    })
}

function handleFilter(){
    $(".btnCalendar").click(function (){
        $("#searchDate").datepicker("show");
    })
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
 * function handle show modal
 * @param type
 * @param notifySeqNo
 */
function showModal(type, notifySeqNo) {
    // fill option to location option
    fillOption(NOTIFY_LOCATION_BY_TYPE.all, $("#location"), "font-weight-bold");

    $("#type").change(function (e) {
        if (+e.target.value === WKTYPE.IMPORT) {
            fillOption(NOTIFY_LOCATION_BY_TYPE.import, $("#location"),"font-weight-bold");
        } else if(+e.target.value === WKTYPE.EXPORT) {
            fillOption(NOTIFY_LOCATION_BY_TYPE.export, $("#location"),"font-weight-bold");
        } else{
            fillOption(NOTIFY_LOCATION_BY_TYPE.all, $("#location"), "font-weight-bold");
        }
    })
    // reset form
    $("#form")[0].reset();
    if (type === "edit") {
        // disable input when edit
        $("#type").attr("disabled", true);
        $("#location").attr("disabled", true);
        notifyBen.attr("readonly", true);
        notifyEng.attr("readonly", true);
        $(".modal-footer").find("#btnSubmit").remove('');
        // get detail data by seqno
        $.ajax({
            url: '/api/cids-notify-manager/detail/' + notifySeqNo, success: function (res) {
                const data = res.data;
                const {
                    locTySeqNo, notiBen, notiEng, wkTyNm, wkTySeqNo, msgFwdYn
                } = data;
                fillOption(wkTySeqNo === WKTYPE.EXPORT ? NOTIFY_LOCATION_BY_TYPE.export : NOTIFY_LOCATION_BY_TYPE.import, $("#location"));
                // set value to input form
                $("#type").val(wkTySeqNo);
                notifyEng.val(notiEng);
                notifyBen.val(notiBen);
                $("#processing").val(msgFwdYn)
                $("#location").val(wkTySeqNo === WKTYPE.ALL ? 10 : locTySeqNo);

                // set length
                handleNotifyLength();
            }, error: function () {
                showDialog($.localeMessage('error'), $.localeMessage('error'), [], null, true);
            }

        })
    } else {
        // hide process input beacause it readonly
        // $("#processRow").removeClass("d-flex");
        // $("#processRow").hide();

        $(".modal-footer").prepend(btnSubmitElement);
        $("#type").removeAttr("disabled");
        $("#location").removeAttr("disabled");
        notifyBen.removeAttr("readonly");
        notifyEng.removeAttr("readonly");
    }
    modal.modal('show');
}

/**
 * function handle user click to submit form
 */
function formSubmit() {
    let errors = [];
    $(document).on('click', "#btnSubmit", function () {
        const type = $("#type").val();
        const location = $("#location").val();
        const notifyEngValue = notifyEng.val();
        const notifyBenValue = notifyBen.val();
        // validate
        if (notifyEngValue === "" || notifyEngValue.length === 0) {
            errors.push($.localeMessage(errorMessage.emptyNotifyEng));
        } else if (notifyEngValue.length > 255) {
            errors.push($.localeMessage(errorMessage.notifyEngTooLong));
        }
        if (notifyBenValue === "" || notifyBenValue.length === 0) {
            errors.push($.localeMessage(errorMessage.emptyNotifyBen));
        } else if (notifyBenValue.length > 255) {
            errors.push($.localeMessage(errorMessage.notifyBenTooLong));
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

            showDialog('Warning!', dialogBody, [], onClose, true);
        } else {
            $.ajax({
                url: '/api/cids-notify-manager',
                method: "POST",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    wkTySeqNo: type,
                    locTySeqNo: location,
                    notiEng: notifyEngValue,
                    notiBen: notifyBenValue,
                }),
                success: function (res) {
                    if (res.success) {
                        reloadDataTable(true);
                        // show dialog after create successful
                        const buttons = [{
                            text: $.localeMessage("confirm"), class: 'btn btn-outline-secondary btn-sm', click: function () {
                                closeDialog();
                                onCloseModal();
                            }
                        }]
                        showDialog($.localeMessage('result'), $.localeMessage('save_cplt'), buttons, null, false);
                    }
                },
                error: function () {
                    showDialog($.localeMessage('error'), $.localeMessage("sth_w_w"), [], null, true);
                }
            })
        }
    });
}

/**
 * handle on close modal
 */
function onCloseModal() {
    modal.attr("modal-type", "");
    modal.modal('hide');
    const notifyEngLength = $("#notifyEndLength");
    const notifyBenLength = $("#notifyBenLength");
    notifyEngLength.text("(0/255)")
    notifyBenLength.text("(0/255)")
    // $("#tblNotify").DataTable().ajax.reload();
}

/**
 * count length of textarea notify
 */
function handleNotifyLength() {
    const notifyEngValue = notifyEng.val();
    const notifyBenValue = notifyBen.val();
    const notifyEngLength = $("#notifyEndLength");
    const notifyBenLength = $("#notifyBenLength");
    notifyEngLength.text("(" + notifyEngValue.length + "/255)")
    notifyBenLength.text("(" + notifyBenValue.length + "/255)")

    notifyEng.on("keyup", function (e) {
        const val = e.target.value;
        notifyEngLength.text("(" + val.length + "/255)");
    })
    notifyBen.on("keyup", function (e) {
        const val = e.target.value;
        notifyBenLength.text("(" + val.length + "/255)")
    })
}


/**
 * This function handles the search filter
 */
function onSearch() {
    $(".btnSearch").click(function () {
        let searchDate = $("#searchDate").val();
        const type = $("#searchType").val();
        const locationType = $("#searchLocation").val();
        if(searchDate.length > 0){
            searchDate = searchDate.split('/').join('-');
        }
        let locationTypeValue = locationType;
        if(type === ""){
            switch(locationType){
                case "1":
                    locationTypeValue = "1|5";
                    break;
                case "2": // security
                    locationTypeValue = "2";
                    break;
                case "6": // custom
                    locationTypeValue = "6";
                    break;
                case "3":
                    locationTypeValue = "3|8";
                    break;
                case "4":
                    locationTypeValue = "4|7";
                    break;

                default:
                    locationTypeValue = locationType;
                    break;
            }
        }
        $("#tblNotify").DataTable().column(5).search(searchDate).column(1).search(type).column(2).search(locationTypeValue).draw();
    })
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

