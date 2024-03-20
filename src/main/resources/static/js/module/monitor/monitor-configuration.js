/**
 * @author TrungNV (trungnv.bks@gmail.com)
 * @since 2023-08-07
 */

$(document).ready(function () {
    initDatatable();
    filterDataTable();
    formAdvertisingSubmit();
    showPreviewWhenChangeTemplate();
});

let messageDialog = {
    saveCompleted: "save_cplt", // Save change ìnformation completed
    cantSaveInfo: "cant_chg", // Cannot change information
    emptyStartTime: "stime_empt", // Start time cannot be blank
    emptyEndTime: "End time cannot be blank", // End time cannot be blank
    emptyUsage: "USAGE cannot be blank", // Usage cannot be blank
    emptyFileCreate:"attch_vid", // Please attach media
    incorrectStartTime: "icr_stime", // Incorrect start time format
    incorrectEndTime: "icr_etime", // Incorrect end time format
    cantGetMonitorConfig: "cget_moncf", // Cannot get detail monitor config
    invalidFileType: "ivld_typef", //"Invalid file type. Please select an MP4 file.",
    fileTooLong: "large_file",// File exceeds the maximum size of 300MB. Please select a smaller file.
    close:"close",
}

$("#btnSubmitConfig").click(function () {
    saveConfiguration(true);
})

$("#btnApply").click(function () {
    saveConfiguration();
})

function initDatatable() {
    // fill option to search location
    fillOption(SEARCH_LOCATION_BY_TYPE.all, $("#searchLocation"));
    $("#searchType").change(function (e) {
        if (e.target.value === "") {
            fillOption(SEARCH_LOCATION_BY_TYPE.all, $("#searchLocation"));
        } else if (+e.target.value === WKTYPE.IMPORT) {
            fillOption(SEARCH_LOCATION_BY_TYPE.import, $("#searchLocation"));
        } else {
            fillOption(SEARCH_LOCATION_BY_TYPE.export, $("#searchLocation"));
        }
    })

    $("#tblMonitorConfiguration").removeAttr("width").DataTable({
        processing: false,
        serverSide: false, // Paging handle by the server side
        paging: false,
        searching: true,
        lengthChange: false,
        lengthMenu: [20, 40, 60],
        language: {
            emptyTable: $.localeMessage('tb_empt_tx'),
        },
        bInfo: false,
        ordering: false,
        autoWidth: true,
        ajax: {
            url: "/api/cids-monitor-configuration/list", type: "GET", cache: false
        }, columnDefs: [
            {
                targets: [1,2,3,4],
                className: 'dt-body-left'
            }
    ],
        columns: [
            {data: "wkTyNm"},
            {
                data: "locNm", render: function (data, type, full) {
                    return "<a href=\"#\" data-toggle=\"modal\" data-target=\"#modalMonitorConfig\" class='text-decoration-underline pl-157' onclick=\"showModalMonitorConfig('" + full.seqNo + "')\">" + data + "</a>";
                }
            },
            {data: "tmplatNm"},
            {
                data: "mdfyDt", render(data) {
                    let time = "";
                    if (data) {
                        time = data.substring(0, 16);
                    }
                    return "<div>" + time + "</div>"
                }
            },
            {data: "userNm"}
        ]
    });
}

/**
 * show a modal dialog box for a specific monitor configuration.
 * @param monitorConfigSeq
 */
function showModalMonitorConfig(monitorConfigSeq) {
    const modal = $("#modalMonitorConfig");
    modal.attr('monitor-seq', monitorConfigSeq);
    $.ajax({
        url: '/api/cids-monitor-configuration/detail/' + monitorConfigSeq,
        success: function (res) {
            const data = res.data;
            $("#locationData").text(data.wkTyNm + " - " + data.locNm);
            $("#current_template").text("TYPE #"+data.tmplatSeqNo);
            // $("#templateData").val(data.tmplatSeqNo);
            modal.modal("show");

            $("#templateData").attr("wkTyNm", data.wkTyNm);
            $("#templateData").attr("locNm", data.locNm);

            showPreviewByType(data.wkTyNm, data.locNm, data.tmplatSeqNo);
        },
        error: function () {
            showDialog($.localeMessage("error"), $.localeMessage(messageDialog.cantGetMonitorConfig), [], null, true);
        }
    })
}

function showPreviewWhenChangeTemplate(){
    $("#templateData").change(function (e){
        const typeName =  $(this).attr("wkTyNm");
        const locationName =  $(this).attr("locNm");
        showPreviewByType(typeName, locationName, e.target.value);
    })
}
function showModalAdvertisement() {
    const modal = $("#modalAdvertisement");
    modal.modal("show");
    let type;
    $.ajax({
        url: '/api/advertising-management/list',
        success: function (res) {
            const data = res.data;
            if (data.length === 0) {
                type = "create";
            } else {
                type = "edit";
                const media = data[0];
                $("#usage").val(media.usageYn);
                $("#startTime").val(media.stTm);
                $("#endTime").val(media.endTm)
                $("#fileName").text(media.fileNm);
                modal.attr("media-seq", media.seqNo);

                // TODO:  Show Current Video
                let videoPlay = document.getElementById("videoPlaying");
                videoPlay.innerHTML = '';
                let videoElement = document.createElement('video');
                videoElement.style.width = '100%';
                videoElement.style.aspectRatio = 16/9;
                videoElement.src = `/upload/${media.fileNm}`;
                videoElement.controls = true;
                videoPlay.appendChild(videoElement);

                }

            modal.attr("modal-type", type);
        }
    })

    // TODO: Show Video When Change Media
    $("#inputMedia").change(function (e) {
        const selectedFile = e.target.files[0];
        if (selectedFile) {
            let videoPlay = document.getElementById("videoPlaying");
            videoPlay.innerHTML = '';
            let videoElement = document.createElement('video');
            videoElement.style.width = '100%';
            videoElement.style.height = 'auto';
            videoElement.src = URL.createObjectURL(selectedFile);
            videoElement.controls = true;
            videoPlay.appendChild(videoElement);
        }
    });

    $("#btnSearch").click(function () {
        $("#inputMedia").click();
    })
}

function formAdvertisingSubmit() {
    $("#inputMedia").change(function (e) {
        const file = e.target.files[0];
        if (file.type !== "video/mp4") {
            showDialog($.localeMessage("error"), $.localeMessage(messageDialog.invalidFileType), [], null, true);
            return;
        }
        if (file.size > 300 * 1024 * 1024) {
            showDialog($.localeMessage("error"), $.localeMessage(messageDialog.fileTooLong), [], null, true);
            return;
        }
        $("#fileName").text(file.name);
    })
    $("#btnSubmit").click(function () {
        let errors = [];

        const modal = $("#modalAdvertisement");
        const modalType = modal.attr("modal-type");
        const advertisingSeqNo = modal.attr("media-seq");
        // get variable
        const usage = $("#usage").val();
        const startTime = $("#startTime").val();
        const endTime = $("#endTime").val();
        const media = $("#inputMedia").prop('files');
        // validate
        const validTime =  /^([01]?[0-9]|2[0-3])[0-5][0-9]$/;
        if (startTime === "") {
            errors.push($.localeMessage(messageDialog.emptyStartTime));
        }else if (!validTime.test(startTime)){
            errors.push($.localeMessage(messageDialog.incorrectStartTime));
        }
        if (endTime === "") {
            errors.push($.localeMessage(messageDialog.emptyEndTime));
        }else if (!validTime.test(endTime)){
            errors.push($.localeMessage(messageDialog.incorrectEndTime));
        }
        if (usage === ""){
            errors.push($.localeMessage(messageDialog.emptyUsage));
        }

        // Create
        if(modalType === "create"){
            if (media.length <= 0 ) {
                errors.push($.localeMessage(messageDialog.emptyFileCreate));
            } else if (media[0].type !== "video/mp4") {
                errors.push($.localeMessage(messageDialog.invalidFileType));
            } else if (media[0].size > 300 * 1024 * 1024) {
                errors.push($.localeMessage(messageDialog.fileTooLong));
            }
        }else{
            if (media.length <= 0 ) {
            } else if (media[0].type !== "video/mp4") {
                errors.push($.localeMessage(messageDialog.invalidFileType));
            } else if (media[0].size > 300 * 1024 * 1024) {
                errors.push($.localeMessage(messageDialog.fileTooLong));
            }
        }


        // If error show dialog
        if (errors.length > 0) {
            // show errors
            let dialogBody = "<ul>";
            errors.forEach(function (item) {
                if (item) {
                    dialogBody += `<li>${item}</li>`
                }
            })
            dialogBody += "</ul>";

            function onClose() {
                errors = [];
            }

            showDialog($.localeMessage('warning'), dialogBody, [], onClose, true);
        } else {
            const createAdvertiseManagementParamDTO = {
                stTm: startTime,
                endTm: endTime,
                usageYn: usage,
            }
            const formData = new FormData();
            formData.append("file", media.length > 0 ? media[0] : null);

            if (modalType === "add") {
                formData.append("createAdvertiseManagementParamDTO", new Blob([JSON.stringify(createAdvertiseManagementParamDTO)], {type: "application/json"}));
                $.ajax({
                    url: '/api/advertising-management',
                    type: "POST",
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function (response) {
                        if (response.success) {
                            const buttons = [{
                                text: $.localeMessage("confirm"), class: 'btn btn-outline-secondary btn-sm', click: function () {
                                    closeDialog();
                                    if(close === true){
                                        closeModal();
                                    }
                                }
                            }]
                            showDialog($.localeMessage("result"), $.localeMessage(messageDialog.saveCompleted), buttons, null, false);
                        }
                        // Request successful, handle the response here
                    },

                })
            }else{
                formData.append("updateAdvertiseManagementParamDTO", new Blob([JSON.stringify({...createAdvertiseManagementParamDTO, seqNo: advertisingSeqNo})], {type: "application/json"}));
                $.ajax({
                    url: '/api/advertising-management',
                    method: "PUT",
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function (response) {
                        if (response.success) {
                            const buttons = [{
                                text: $.localeMessage("confirm"), class: 'btn btn-outline-secondary btn-sm', click: function () {
                                    closeDialog();
                                    if(close === true){
                                        closeModal();
                                    }
                                }
                            }]
                            showDialog($.localeMessage("result"), $.localeMessage(messageDialog.saveCompleted), buttons, null, false);
                        }
                        // Request successful, handle the response here
                    },
                })
            }
        }


    })
}

/**
 * Save the configuration of a monitor.
 */
function saveConfiguration(close= false) {
    const modal = $("#modalMonitorConfig");
    const monitorConfigSeqNo = modal.attr("monitor-seq");
    const templateValue = $("#templateData").val();
    $.ajax({
        url: '/api/cids-monitor-configuration',
        method: "PUT",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            seqNo: monitorConfigSeqNo,
            tmplatSeqNo: templateValue
        }),
        success: function () {
            const buttons = [{
                text: $.localeMessage("confirm"), class: 'btn btn-outline-secondary btn-sm', click: function () {
                    closeDialog();
                    if(close === true){
                        closeModal();
                    }
                }
            }]
            showDialog($.localeMessage("result"), $.localeMessage(messageDialog.saveCompleted), buttons, null, false);
            $("#tblMonitorConfiguration").DataTable().ajax.reload();
        },
        error: function () {
            const buttons = [{
                text: $.localeMessage("confirm"), class: 'btn btn-outline-secondary btn-sm', click: function () {
                    closeDialog();
                }
            }]
            showDialog($.localeMessage("error"), $.localeMessage(messageDialog.cantSaveInfo), buttons, null, false);
        }
    })
};

/**
 * it uses the DataTable API to search for the selected values
 */
function filterDataTable() {
    $(".btnSearch").click(function () {
        const type = $("#searchType").val();
        const location = $("#searchLocation").val();
        const template = $("#searchTemplate").val();

        let locTypeName = "";
        let typeName = "";
        let templateName = "";

        if (+type === WKTYPE.EXPORT) {
            typeName = "EXPORT";
        } else if (+type === WKTYPE.IMPORT) {
            typeName = "IMPORT";
        }

        switch (+location) {
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
        $("#tblMonitorConfiguration").DataTable().column(0).search(typeName).column(1).search(locTypeName).column(2).search(template).draw();
    })
}

function showPreviewByType(type, location = '', template){
    const locationName = location.toLowerCase().replace(/ /g, '_');
    const typeName = type.toLowerCase();
    let fileName = `${typeName}_${locationName}_type${template}.png`;
    $("#previewImage").attr("src", `/images/screen-preview/${fileName}`)
}

function closeModal(){
    $("#modalMonitorConfig").modal('hide');
}

$("#btnCloseModalConfig").click(function() {
    $("#templateData").removeAttr("wkTyNm");
    $("#templateData").removeAttr("locNm");
    $("#templateData").val("0")
});


