/**
 * @author trungNV (trungnv.bks@gmail.com)
 * @since 2023-07-14
 */
$(document).ready(function () {
    initEvent();
    initData();


})

const boardSampleElement = $(".board");

function initData() {
    let intervalId;
    getDataByType(WKTYPE.EXPORT);
    $("#tabImport").click(function () {
        clearInterval(intervalId);
        getDataByType(WKTYPE.IMPORT);
    })
    $("#tabExport").click(function () {
        clearInterval(intervalId);
        getDataByType(WKTYPE.EXPORT);
    })

    function getDataByType(wkType) {
        // Refresh every 20 seconds
        function fetchData() {
            $.ajax({
                url: '/api/cids-monitor-monitoring/list-export-import/' + wkType,
                method: "POST",
                hideLoading: true,
                success: function (res) {
                    const {data} = res;
                    // Modified Data
                    const objData = data.reduce(function (result, obj) {
                        var index = result.findIndex(function (item) {
                            return item.name === obj.locNm;
                        });
                        if (index === -1) {
                            result.push({
                                name: obj.locNm,
                                data: [obj]
                            });
                        } else {
                            result[index].data.push(obj);
                        }
                        return result;
                    }, []);

                    //reset before append data
                    $(".stack-container").html("");
                    $(".board").first().remove();

                    /*
                         Export - Truck / Security / EnterMHS / Exit MHS
                         Import - Truck / Custom / ExitMHS / EnterMHS
                     */
                    const sortOrderByExport = ["Truck Dock", "Security", "Enter MHS", "Exit MHS"];
                    const sortOrderByImport = ["Truck Dock", "Customs", "Exit MHS", "Enter MHS"];
                    let listMonitoring = objData;
                    let listLocation;
                    if (+wkType === WKTYPE.EXPORT) {
                        listLocation = sortOrderByExport
                        listMonitoring = listMonitoring.sort((a, b) => sortOrderByExport.indexOf(a.name) - sortOrderByExport.indexOf(b.name))
                    } else {
                        listMonitoring = listMonitoring.sort((a, b) => sortOrderByImport.indexOf(a.name) - sortOrderByImport.indexOf(b.name))
                        listLocation = sortOrderByImport
                    }


                    // clone data & append to element
                    listLocation.forEach(function (_item, index) {
                        const stack = boardSampleElement.clone();
                        // set label stack name
                        stack.find(".label").text(_item);
                        // render list monitor
                        let htmlListMonitor = "";
                        const listMonitor = listMonitoring.find(m => m.name === _item);
                        if(listMonitor){
                            listMonitor.data.forEach(function (monitoring) {
                                htmlListMonitor += "<div class=\"monitor-item\" monitor-seq=" + monitoring.seqNo + ">\n" +
                                    "                                    <div class=\"circle\"\n" +
                                    "                                    >\n" +
                                    "                                        <div class=\"text-center\">\n" +
                                    "                                            <img src='/images/monitor-icon.png' class='icon' /> \n" +
                                    "                                        </div>\n" +
                                    "                                        <div class=\"status " + (monitoring.stsCd === 1 ? 'active' : 'none') + "\">\n" +
                                    "                                        </div>\n" +
                                    "                                    </div>\n" +
                                    "                                    <div class=\"name\" title=\"" + monitoring.monNm + "\">\n" +
                                    limitString(monitoring.monNm, 22) +
                                    "                                    </div>\n" +
                                    "                                </div>"
                            })
                            stack.find('.list-monitor').html(htmlListMonitor);
                        }
                        if(index === listLocation.length - 1){
                            stack.find(".borderLine").remove();
                        }
                        $(".stack-container").append(stack);
                        // Enable tooltip jquery-ui
                        $(".name").tooltip({
                            content: $(".name").attr("title")
                        })
                    })

                }
            })
        }

        fetchData()
        intervalId = setInterval(() => {
            fetchData();
            $(".tooltip").each(function(){
                $(this).remove();
            })
        }, 10000);
    }
}

function initEvent() {
    $(document).on('click', '.monitor-item', function () {
        const monitoringSeqNo = $(this).attr("monitor-seq");
        $(".modal-body").load("/cids-monitor/detail/" + monitoringSeqNo);
        $("#staticBackdrop").modal();
    })

    $('#tabImport').click(function () {
        $('#tabImport').addClass('active');
        $('#tabExport').removeClass('active')
    });

    $('#tabExport').click(function () {
        $('#tabExport').addClass('active');
        $('#tabImport').removeClass('active')
    });


}