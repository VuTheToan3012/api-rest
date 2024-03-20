/**
 * @author trungNV (trungnv.bks@gmail.com)
 * @since 2023-07-20
 */

$(document).ready(function () {
    initDataTable();
    formSubmit();
    onSearch();
    $("#modalMonitorInfo").on('hidden.bs.modal', function () {
        onCloseModal();
    });
    handlePageChange();
})

/* VARIABLE */
const messageDialog = {
    saveCompleted: "save_cplt", // Save change ìnformation completed,
    locationNameEmpty: 'locnm_empt', // Location Name cannot be blank
    usageEmpty: 'usage_empt', // Usage cannot be blank
    locationEmpty: 'loc_empt', // Please select location
    monitorNameEmpty: 'monnm_empt', // Monitor Name can't be blank
    networkIpEmpty: 'ip_empt', // Network (IP) cannot be blank
    networkIpIncorrectFormat: 'wr_fm_ip', // Incorrect format Network(IP) address. Eg: 192.168.1.10
    duplicate: 'dupmsg', // The information currently entered is redundant.
    cantCreateMonitor: "notcrt_mon", // Cannot create monitor
    cantSaveMonitor: "notsavemon", // Cannot save monitor information
    cantGetMonitor: "notgetmon", // Cannot get monitor
    deleteSuccess: "del_mon_ss", // Delete monitor successfully!
    cantDeleteMon: "del_mon_f", // Cannot delete this monitor
    cantGetTemplate: "Template_f", // Cannot Get Template
    close:"close",
    delete:"delete",
    confirm: "confirm",
    duplicateTitle:"duplicate",
    noData: "No matching records found.",
}

/* FUNCTION */
/**
 * This function initializes a DataTable with specific configurations and retrieves data from a server endpoint to populate the table.
 */
function initDataTable() {
    const searchLocation = $("#searchLocation");
    // fill option to select location element
    fillOption(SEARCH_LOCATION_BY_TYPE.all, searchLocation)
    //     Fill option when change type (import/ export)
    $("#searchType").change(function (e){
        switch (e.target.value){
            case "1":
                fillOption(SEARCH_LOCATION_BY_TYPE.export, searchLocation);
                break;
            case "2":
                fillOption(SEARCH_LOCATION_BY_TYPE.import, searchLocation);
                break;
            default:
                fillOption(SEARCH_LOCATION_BY_TYPE.all, searchLocation)
        }
    })
    // fill data to table
    const table = $("#tblMonitor").removeAttr("width").DataTable({
        processing: false,
        serverSide: false, // Paging handle by the server side
        paging: true,
        searching: true,
        lengthChange: false,
        lengthMenu: [20, 40, 60],
        bInfo: false,
        autoWidth: true,
        ordering: false,
        language: {
            emptyTable: $.localeMessage('tb_empt_tx'),
            paginate: {
                "next": nextButton(),
                "previous" : previousButton()
            }
        },
        "initComplete": function (){
        },
        autoWidth: true,
        ajax: function (data, callback, setting) {
            $.get("/api/cids-monitor-manager/list", {
                limit: data.length,
                offset: data.start,
            }, function(res) {
                callback({
                    recordsTotal: res.recordsTotal,
                    recordsFiltered: res.recordsFiltered,
                    data: res.data
                });
            });
        },
        columnDefs: [
            {
                targets: [0,1,2,3,4,5,7,8],
                className: 'dt-body-left'
            },
            {
                targets: 3,
                className: 'text-uppercase'
            },
            {
                targets: 0,
                className: 'pl-0, al_c'
            },
            {
                targets: 6,
                className: 'al_c'
            }
        ],
        columns: [
            {data: "wkTyNm"},
            {
                data: "monNm", render: function (data, type, full, meta) {
                    return "<a href=\"#\" data-toggle=\"modal\" data-target=\"#modalMonitorInfo\" class='text-decoration-underline pl-26' onclick=\"showModal('edit'," + full.seqNo + ", '" + full.monCmsNm + "', '" + full.monNm + "', '" + full.ip + "' )\">" + data + "</a>";
                }
            },
            {
                data: "monCmsNm"
            },
            {data: "locNm"},
            {data: "ip"},
            {data: "tmplatNm"},
            {
                data: "usage", render(data) {
                    if (data === 1) {
                        return "<b>Y</b>"
                    }
                    return "N"
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
            {data: "userNm"},
        ]
    });
}

function showButtonExportData (){
    const table = $("#tblMonitor").DataTable();
    const numColumns = table.columns().nodes().length;
    const buttons = new $.fn.dataTable.Buttons(table, {
        buttons: [
            {
                extend: 'excelHtml5',
                autoFilter: false,
                className: 'btnExportExcel',
                text: $.localeMessage("export"),
                attr:{
                    style:'width:7em;'
                },
                filename: exportExcelName("CIDS Monitor Manager_"),
                title: exportExcelName("CIDS Monitor Manager_"),
                customize: function( xlsx ) {
                    var sheet = xlsx.xl.worksheets['sheet1.xml'];
                    $('row:first c', sheet).attr('s', '51');
                    $('row:nth-child(2) c', sheet).attr('s', '30');
                    $('row:not(:first):not(:nth-child(2)) c', sheet).attr('s', '25');
                },
                exportOptions: {
                    customizeData: function (d) {
                        // Check if d.body exists and is empty
                        if (d.body && d.body.length === 0) {
                            // If empty, add a merged cell with the message
                            d.body = [["No matching recording found"]];
                            for (let i = 1; i < numColumns; i++) {
                                d.body[0].push("");
                            }
                        } else if (d.body) {
                            d.body = d.body.map(row => {
                                return row.map(cell => {
                                    return cell.length > 32767 ? cell.substring(0, 32767) : cell;
                                });
                            });

                            const columnsToSort = [0,3];
                            d.body.sort(function(a, b) {
                                return sortData(a, b, columnsToSort, "asc");
                            });
                        }
                    }
                },
            }
        ]
    }).container().appendTo($('.btnExport'));
}

/**
 * This function is used to handle the form submission and validation for a monitor form.
 */
function formSubmit() {
    // Button for dialog duplicate
    const buttonsDuplicate = [{
        text: $.localeMessage(messageDialog.confirm), class: 'btn btn-outline-secondary btn-sm', click: function () {
            closeDialog();
        }
    }]

    // change location option when change type value
    $("#type").change(function (e){
        const locationVal = $("#location").val();
        let newLocationVal = locationVal;
        switch (e.target.value){
            case "1":
                fillOption(SEARCH_LOCATION_BY_TYPE_CHANGE.export, $("#location"),"font-weight-bold");

                if(+locationVal >= 5){
                    switch (+locationVal){
                        case 5:
                            newLocationVal = 1;
                            break;
                        case 6:
                            newLocationVal = 2;
                            break;
                        case 7:
                            newLocationVal = 4;
                            break;
                        case 8:
                            newLocationVal = 3;
                            break;
                    }
                }
                $("#location").val(newLocationVal);
                break;
            case "2":
                fillOption(SEARCH_LOCATION_BY_TYPE_CHANGE.import, $("#location"),"font-weight-bold");
                if(+locationVal < 5){
                    switch (+locationVal){
                        case 1:
                            newLocationVal = 5;
                            break;
                        case 2:
                            newLocationVal = 6;
                            break;
                        case 4:
                            newLocationVal = 7;
                            break;
                        case 3:
                            newLocationVal = 8;
                            break;
                    }
                }
                break;
            default:
                fillOption(SEARCH_LOCATION_BY_TYPE_CHANGE.all, $("#location"),"font-weight-bold");



        }
        $("#location").val(newLocationVal);

        const newTypeValue = $("#type").val();
        const newLocationValue = $("#location").val();
        getTemplate(newTypeValue, newLocationValue);
    })

    // Store errors message key
    let errors = [];

    // Flag check duplicate
    let isDuplicateLocationName = null;
    let isDuplicateName = null;
    let isDuplicateIp = null;

    // Handle when clicked button save;
    $('#btnSubmit').click(async function (e) {
        e.preventDefault();
        /* get variable */
        const modal = $("#modalMonitorInfo");
        const modalType = modal.attr('modal-type');
        const type = $("#type").val();
        const location = $("#location").val();
        const locationName = $("#locationName").val();
        const monitorName = $("#monitorName").val();
        const networkIp = $("#networkIp").val();
        const template = $("#template").val();
        const usage = $("#usage").val();
        const note = $("#note").val();

        const currentLocationName = modal.attr('current-location-name');
        const currentIp = modal.attr('current-ip');
        const currentName = modal.attr('current-name');
        const seqNo = modal.attr('seq-no');


        /* validate */
        if (!locationName || locationName === "") {
            errors.push($.localeMessage(messageDialog.locationNameEmpty));
        }
        if (!location || location === "") {
            errors.push($.localeMessage(messageDialog.locationEmpty));
        }
        if (!monitorName || monitorName === "") {
            errors.push($.localeMessage(messageDialog.monitorNameEmpty));
        }
        if (!networkIp || networkIp === "") {
            errors.push($.localeMessage(messageDialog.networkIpEmpty));
        } else if (!Regexs.ip.test(networkIp)) {
            errors.push($.localeMessage(messageDialog.networkIpIncorrectFormat));
        }
        if(usage === ""){
            errors.push($.localeMessage(messageDialog.usageEmpty));
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

            showDialog($.localeMessage('warning'), dialogBody, [], onClose, true);
        } else {
            let hasDuplicate = false;
            // Skip when edit
            if(modalType === "edit"){
                if(currentLocationName === locationName){
                    isDuplicateLocationName = false;
                }
                if(currentName === monitorName){
                    isDuplicateName = false;
                }
                if(currentIp === networkIp){
                    isDuplicateIp = false;
                }
            }
            //if duplicate is null => call func check duplicate
            if (isDuplicateLocationName === null) {
                const preCheckDuplicate = await checkDuplicateLocationName(locationName, type, location);
                if (preCheckDuplicate) {
                    onDuplicateTrue($("#locationName"));
                    hasDuplicate = true;
                }
                // if duplicate === true => break
            } else if (isDuplicateLocationName === true) {
                onDuplicateTrue($("#locationName"));
                hasDuplicate = true
            }

            //if duplicate is null => call func check duplicate
            if (isDuplicateName === null) {
                const preCheckDuplicate = await checkDuplicateMonitorName(monitorName,type, location);
                if (preCheckDuplicate) {
                    onDuplicateTrue($("#monitorName"));
                    hasDuplicate = true
                }
                // if duplicate === true => break
            } else if (isDuplicateName === true) {
                onDuplicateTrue($("#monitorName"));
                hasDuplicate = true
            }

            //if duplicate is null => call func check duplicate
            if (isDuplicateIp === null) {
                const preCheckDuplicate = await checkDulicateIpAddress(networkIp);
                if (preCheckDuplicate) {
                    onDuplicateTrue($("#networkIp"));
                    hasDuplicate = true
                }
                // if duplicate === true => break
            } else if (isDuplicateIp === true) {
                onDuplicateTrue($("#networkIp"));
               hasDuplicate = true
            }

            if(hasDuplicate === true){
                showDialog(`${$.localeMessage(messageDialog.duplicateTitle)}`, $.localeMessage(messageDialog.duplicate), buttonsDuplicate, null, false);
                return;
            }

            if (modalType === 'add') {
                $.ajax({
                    url: '/api/cids-monitor-manager',
                    method: 'POST',
                    data: JSON.stringify({
                        monNm: monitorName,
                        monCmsNm: locationName,
                        wkTySeqNo: type,
                        locTySeqNo: location,
                        usage,
                        ip: networkIp,
                        note,
                    }),

                    contentType: "application/json",
                    dataType: "json",
                    success: function (res) {
                        if (res.success) {

                            reloadDataTable(true);
                            // show dialog after create successful
                            const buttons = [{
                                text: $.localeMessage('confirm'), class: 'btn btn-outline-secondary btn-sm', click: function () {
                                    closeDialog();
                                    onCloseModal();
                                }
                            }]
                            showDialog($.localeMessage("result"), $.localeMessage(messageDialog.saveCompleted), buttons, null, false);
                        }
                    },
                    error: function () {
                        showDialog($.localeMessage('error'), $.localeMessage(messageDialog.cantCreateMonitor), [], null, true);
                    }
                })
            } else {
                $.ajax({
                    url: '/api/cids-monitor-manager',
                    method: 'PUT',
                    data: JSON.stringify({
                        seqNo: seqNo,
                        monNm: monitorName,
                        monCmsNm: locationName,
                        wkTySeqNo: type,
                        locTySeqNo: location,
                        usage,
                        ip: networkIp,
                        note,
                    }),
                    contentType: "application/json",
                    dataType: "json",
                    success: function (res) {
                        if (res.success) {
                            reloadDataTable(true);
                            // show dialog when update successful
                            const buttons = [{
                                text: $.localeMessage('confirm'), class: 'btn btn-outline-secondary btn-sm', click: function () {
                                    closeDialog();
                                    onCloseModal();
                                }
                            }]
                            showDialog($.localeMessage("result"), $.localeMessage(messageDialog.saveCompleted), buttons, null, false);
                        }
                    },
                    error: function () {
                        showDialog($.localeMessage('error'), $.localeMessage(messageDialog.cantSaveMonitor), [], null, true);
                    }
                })
            }

        }
    })

    // reset when change monitor input value
    $("#locationName").keyup(function () {
        isDuplicateLocationName = null;
        clearDuplicateStyle($("#locationName"));
    })

    // handle when click duplicate check monitorCmsName button
    $("#duplicateCheckLocNameBtn").click(async function (e) {
        const modal = $("#modalMonitorInfo");
        const modalType = modal.attr("modal-type");
        const currentLocName = modal.attr("current-location-name");
        const locationName = $("#locationName").val();
        const monitorName= $("#monitorName").val();
        const wkTySeqNo = $("#type").val();
        const locTySeqNo = $("#location").val();
        if(modalType === "edit" && currentLocName === locationName){
            onDuplicateFalse($("#locationName"))
            isDuplicateLocationName = false;
            return;
        }

        const duplicate = await checkDuplicateLocationName(locationName, wkTySeqNo, locTySeqNo);
        if (duplicate) {
            isDuplicateLocationName = true;
            onDuplicateTrue($("#locationName"))
            showDialog(`${$.localeMessage(messageDialog.duplicateTitle)}`, `<div>${$.localeMessage(messageDialog.duplicate)}</div>`, buttonsDuplicate, null, false);
        } else {
            onDuplicateFalse($("#locationName"))
            isDuplicateLocationName = false;
        }
    })

    // reset when change monitor input value
    $("#monitorName").keyup(function () {
        isDuplicateName = null;
        clearDuplicateStyle($("#monitorName"));
    })

    // handle when click duplicate check monitorName button
    $("#duplicateCheckNameBtn").click(async function (e) {
        const modal = $("#modalMonitorInfo");
        const modalType = modal.attr("modal-type");
        const currentName = modal.attr("current-name");
        const monitorName = $("#monitorName").val();
        const wkTySeqNo = $("#type").val();
        const locTySeqNo = $("#location").val();
        if(modalType === "edit" && currentName === monitorName){
            onDuplicateFalse($("#monitorName"))
            isDuplicateName = false;
            return;
        }

        const duplicate = await checkDuplicateMonitorName(monitorName, wkTySeqNo, locTySeqNo);
        if (duplicate) {
            onDuplicateTrue($("#monitorName"));
            isDuplicateName = true;
            showDialog(`${$.localeMessage(messageDialog.duplicateTitle)}`, `<div>${$.localeMessage(messageDialog.duplicate)}</div>`, buttonsDuplicate, null, false);
        } else {
            onDuplicateFalse($("#monitorName"));
            isDuplicateName = false;
        }
    })

    // reset when change ip input value
    $("#networkIp").keyup(function () {
        isDuplicateIp = null;
        clearDuplicateStyle($("#networkIp"));;
    })
    // handle when click duplicate check monitorName button
    $("#duplicateCheckIPBtn").click(async function (e) {
        const modal = $("#modalMonitorInfo");
        const modalType = modal.attr("modal-type");
        const currentIp = modal.attr("current-ip");
        const ipAddress = $("#networkIp").val();
        if(modalType === "edit" && currentIp === ipAddress){
            onDuplicateFalse($("#networkIp"))
            isDuplicateIp = false;
            return;
        }
        const duplicate = await checkDulicateIpAddress(ipAddress);
        if (duplicate) {
            isDuplicateIp = true;
            onDuplicateTrue($("#networkIp"));
            showDialog($.localeMessage('duplicate'), `<div>${$.localeMessage(messageDialog.duplicate)}</div>`, buttonsDuplicate, null, false);
        } else {
            onDuplicateFalse($("#networkIp"));
            isDuplicateIp = false;
        }
    })

    // FUNCTION
    /**
     * Checks if the monitor name already exists or not
     * @param locationName
     * @returns {Promise<boolean>}
     */
    async function checkDuplicateLocationName(monCmsNm,wkTySeqNo, locTySeqNo) {
        let isDuplicate = true;
        const checkDuplicateMonCmsNm = {
            monCmsNm,
            wkTySeqNo,
            locTySeqNo
        }
        try {
            const checkResult = await $.ajax({
                url: '/api/cids-monitor-manager/check-duplicate-mon-cms-nm',
                method: 'POST',
                data : JSON.stringify(checkDuplicateMonCmsNm),
                contentType: 'application/json'
            })
            isDuplicate = checkResult.success;
        } catch (e) {
            isDuplicate = false;
        }
        return isDuplicate
    }

    /**
     * Checks if the monitor name already exists or not
     * @param monNm
     * @param wkTySeqNo
     * @param locTySeqNo
     * @returns {Promise<*>}
     */
    async function checkDuplicateMonitorName(monNm, wkTySeqNo, locTySeqNo) {
        const checkDuplicateMonNm = {
            monNm,
            wkTySeqNo,
            locTySeqNo
        }
        let isDuplicate = true;
        try {
            const checkResult = await $.ajax({
                url: `/api/cids-monitor-manager/check-duplicate-monitor-name`,
                method: 'POST',
                data : JSON.stringify(checkDuplicateMonNm),
                contentType: 'application/json'
            })
            isDuplicate = checkResult.success;
        } catch (e) {
            isDuplicate = false;
        }
        return isDuplicate
    }

    /**
     * Checks if the network ip already exists or not
     * @param ipAddress
     * @returns {Promise<boolean>}
     */
    async function checkDulicateIpAddress(ipAddress) {
        let isDuplicate = true;
        try {
            const checkResult = await $.ajax({
                url: `/api/cids-monitor-manager/check-duplicate-ip/${encodeURIComponent(ipAddress)}`,
            })
            isDuplicate = checkResult.success;
        } catch (e) {
            isDuplicate = false;
        }
        return isDuplicate
    }
}

/**
 * This function is used to show a modal window create or monitor detail
 * @param type - add | edit
 * @param monitorSeqNo
 * @param currentIp
 * @param currentName
 * */
async function showModal(type, monitorSeqNo, currentLocationName, currentName, currentIp) {

    const modal = $("#modalMonitorInfo");
    modal.attr("modal-type", type);
    // reset old value form input
    $("#form")[0].reset();
    // clear template when show modal
    $("#template").text("");

    if (type === "edit" && currentIp && currentName) {
        $(".templateGroup").show();
        $(".templateGroup").addClass("d-flex");
        modal.attr("seq-no", monitorSeqNo );
        modal.attr("current-ip", currentIp);
        modal.attr("current-name", currentName);
        modal.attr("current-location-name", currentLocationName);
        // show button delete when view detail

            const deleteButton = `<button type="button" class="btn btn-sm btn-danger font-weight-bold" style="width: 100px;background-color: #FD443D " onClick="deleteMonitor(${monitorSeqNo})"><div>${$.localeMessage(messageDialog.delete)}</div></button>`;

        // append delete button
        $(".rightAction").html(deleteButton);
        // fill data to input
        await $.ajax({
            url: '/api/cids-monitor-manager/detail/' + monitorSeqNo,
            hideLoading: false,
            success: function (res) {
                const data = res.data;
                const {
                    ip,
                    locNm,
                    locTySeqNo,
                    monNm,
                    note,
                    tmplatSeqNo,
                    tmplatNm,
                    usage,
                    wkTySeqNo,
                    monCmsNm
                } = data;
                // // set value to input form
                $("#type").val(wkTySeqNo);
                $("#locationName").val(monCmsNm);
                $("#monitorName").val(monNm);
                $("#networkIp").val(ip);
                $("#usage").val(usage);
                $("#note").val(note);
                $("#template").val(tmplatSeqNo);
                $("#template").attr("disabled", true);
                if(wkTySeqNo === WKTYPE.IMPORT){
                    fillOption(LOCATION_BY_TYPE.import, $("#location"), "font-weight-bold");
                }else{
                    fillOption(LOCATION_BY_TYPE.export, $("#location"),"font-weight-bold");
                }
                $("#location").val(locTySeqNo);
                $("#template").text(tmplatNm);
            }, error: function () {
                showDialog($.localeMessage('error'), '', [], null, true);
            },
        })
    }
    else{
        const close = ` <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                             <span aria-hidden="true">&times;</span></button>`;
        $(".rightAction").html(close);
        $(".templateGroup").addClass("d-flex");
        $("#template").attr("disabled", true);
        fillOption(LOCATION_BY_TYPE.export, $("#location"),"font-weight-bold");

        const newTypeValue = $("#type").val();
        const newLocationValue = $("#location").val();
        getTemplate(newTypeValue, newLocationValue);
    }
    modal.modal("show");
}

/**
 * This function is used to handle the closing of a modal window.
 */
function onCloseModal() {
    const modal = $("#modalMonitorInfo");
    modal.attr("modal-type", "");
    modal.removeAttr("current-name")
    modal.removeAttr("current-ip")
    $(".rightAction").html("");
    modal.modal('hide');

    // clear style check duplicate
    clearDuplicateStyle($("#networkIp"));
    clearDuplicateStyle($("#locationName"));;
    clearDuplicateStyle($("#monitorName"));;
}

/**
 * This function is used to delete a monitor from the system.
 * @param monitorSeqNo
 */
function deleteMonitor(monitorSeqNo) {
    const buttons = [{
        text: $.localeMessage('yes'), class: 'btn btn-outline-secondary btn-sm', click: function () {
            $.ajax({
                url: '/api/cids-monitor-manager/' + monitorSeqNo, method: 'DELETE', success: function (res) {
                    if (res.success) {
                        showDialog($.localeMessage('result'), $.localeMessage(messageDialog.deleteSuccess), [], () => {reloadDataTable();onCloseModal();}, true)
                    } else {
                        showDialog($.localeMessage('error'), $.localeMessage(messageDialog.cantDeleteMon), [], null, true)
                    }

                }, error: function () {
                    showDialog($.localeMessage('error'), $.localeMessage(messageDialog.cantDeleteMon), [], null, true);
                }
            })
        }
    }, {
        text: $.localeMessage('no'), class: 'btn btn-outline-secondary btn-sm', click: function () {
            closeDialog();
        }
    }]
    showDialog($.localeMessage("del_item"), $.localeMessage('chk_YN_del'), buttons, null, false);
}

/**
 * This function handles the search filter
 */
function onSearch() {
    $(".btnSearch").click(function () {
        const type = $("#searchType").val();
        const locationType = $("#searchLocation").val();
        const searchMonitorName = $("#searchMonitorName").val().trim();
        let locTypeName = "";
        let typeName = "";

        if (+type === WKTYPE.EXPORT) {
            typeName = "EXPORT";
        } else if(+type === WKTYPE.IMPORT) {
            typeName = "IMPORT";
        }

        switch (+locationType) {
            case 1:
                locTypeName = "Truck Dock"
                break;
            case 2:
                locTypeName = "Security";
                break;
            case 3:
                locTypeName = "Enter MHS";
                break;
            case 4:
                locTypeName = "Exit MHS";
                break;
            case 5:
                locTypeName = "Truck Dock";
                break;
            case 6:
                locTypeName = "Customs";
                break;
            case 7:
                locTypeName = "Exit MHS";
                break;
            case 8:
                locTypeName = "Enter MHS";
                break;
        }

        $("#tblMonitor").DataTable().column(1).search(searchMonitorName).column(0).search(typeName).column(3).search(locTypeName).draw();


    })
}

    var input = document.getElementById("searchMonitorName");
    input.addEventListener("keypress", function(event) {
        if (event.key === "Enter") {
            document.getElementById("btnSearch").click();
        }
    });

/**
 * This function is used to reload the data in a DataTable. It targets the table with the ID "tblMonitor" and calls the DataTable() function on it
 */
function reloadDataTable(keepPage = true) {
    if(keepPage){
        $("#tblMonitor").DataTable().ajax.reload(null, false);
    }else {
        $("#tblMonitor").DataTable().ajax.reload();
    }
}

function handlePageChange(){
    const table = $("#tblMonitor").DataTable();
    // get page params on url
    var urlParams = new URLSearchParams(window.location.search);
    const searchPage = urlParams.get("page") ?? 1;
    table.ajax.reload(null, searchPage-1);


    table.on("page.dt", function(){
        var info = table.page.info();
        var page = info.page+1;
        const url = new URL(window.location);
        url.searchParams.set("page", page ? page : 1);
        window.history.pushState({}, '', url)
        // console.log(info, page);
    })
}

/**
 * Get Template By Location And Type
 * @param wkTySeqNo
 * @param locTySeqNo
 */
    function getTemplate (wkTySeqNo, locTySeqNo){
        $.ajax({
            url: '/api/cids-monitor-configuration/find-temp-by-loc-and-type',
            method: 'POST',
            data: JSON.stringify({
                wkTySeqNo: wkTySeqNo,
                locTySeqNo: locTySeqNo
            }),
            hideLoading: false,
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.success) {
                    const data = res.data;
                    $("#template").text("TYPE#" + data);
                }
            },
            error: function () {
                showDialog($.localeMessage('error'), $.localeMessage(messageDialog.cantGetTemplate), [], null, true);
            }
        })
    }

    /**
     * Update Template When Location Change
     */
    $("#location").on("change", function () {
                const newTypeValue = $("#type").val();
                const newLocationValue = $("#location").val();

                getTemplate(newTypeValue, newLocationValue);

    });

//input mask bundle ip address
var ipv4_address = $('#networkIp');
ipv4_address.inputmask({
    alias: "ip",
    greedy: false //The initial mask shown will be "" instead of "-____".
});
