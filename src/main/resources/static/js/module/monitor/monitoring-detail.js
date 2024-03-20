$(document).ready(function () {
    loadMonitorDetail();

    $(".close").click(function (){
        $("#staticBackdrop").modal('hide');
    })

    $(".btnSearch").click(function(){
        loadMonitorDetail();
    })
})

function loadMonitorDetail() {
    const monitorDetail = $(".monitor-detail-item")
    const monitorName = $("#monitor-name-data")
    const monitoringSeq = monitorDetail.attr("monitor-seq");
    monitorDetail.hide();
    $.ajax({
        url: '/api/cids-monitor-monitoring/detail/' + monitoringSeq,
        success: function (res) {
            const data = res.data
            if (data) {
                $("#location-data").text(data.wkTyNm + "-" + data.locNm);
                monitorName.text(limitString(data.monNm, 10));
                monitorName.attr("title", data.monNm);
                $("#monitor-status-data").text(data.stsCd === 1 ? $.localeMessage('on_line') : $.localeMessage('off_line'));
                $("#status-color").css("background-color", data.stsCd === 1 ? "#00b44e" : "#FD443D");
                monitorDetail.attr("monitor-id", data.monSeqNo);

                monitorName.tooltip({
                    content: data.monNm
                })
                requestMonitorScreenShot();

            //     show content after data loaded
                monitorDetail.show();

            }
        }
    })
    $("#refreshBtn").click(function () {
        requestMonitorScreenShot();
    })
}

function requestMonitorScreenShot() {
    const monitorSeq = $(".monitor-detail-item").attr("monitor-id");
    if (WEBSOCKET_MANAGER.webSocketConnected) {
        wsSendMessage(WEBSOCKET_PROTOCOL.CIDS_MONITOR_OPERATION_PRTSCN, {monitorId: monitorSeq}, null);
    }
}