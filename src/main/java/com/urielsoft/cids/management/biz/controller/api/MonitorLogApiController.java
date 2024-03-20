package com.urielsoft.cids.management.biz.controller.api;

import com.urielsoft.cids.management.biz.common.utils.DateTimeUtils;
import com.urielsoft.cids.management.biz.dto.SearchAndPaginationMonitorLogResultsDTO;
import com.urielsoft.cids.management.biz.dto.ViewMonitorLogDTO;
import com.urielsoft.cids.management.biz.dto.api.param.ReadMonitorLogByDateParamDTO;
import com.urielsoft.cids.management.biz.dto.api.result.MonitorLogDetailResultDTO;
import com.urielsoft.cids.management.biz.dto.api.result.MonitorLogListItemResultDTO;
import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;
import com.urielsoft.cids.management.biz.service.MonitorLogService;
import com.urielsoft.cids.management.common.api.ApiResult;
import com.urielsoft.cids.management.common.api.ApiResultPagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Monitor Log Api Controller
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-27
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/monitor-log")
public class MonitorLogApiController {
    /**
     * Monitor Log Service
     */
    private final MonitorLogService monitorLogService;

    /**
     * Detail Monitor Log Data
     *
     * @param monitorLogSeqNo
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/detail/{monitorLogSeqNo}")
    public ResponseEntity<ApiResult> detailMonitorLogData(@PathVariable("monitorLogSeqNo") int monitorLogSeqNo) throws Exception {
        // Get Monitor Log Information By Seq
        ViewMonitorLogDTO dbInfo = this.monitorLogService.getMonitorLogDataBySeq(monitorLogSeqNo);
        //  Use dbInfo to set Data for resultInfo
        var resultInfo = new MonitorLogDetailResultDTO();

        resultInfo.setSeqNo(dbInfo.getSeqNo());
        resultInfo.setSysTyNm(dbInfo.getSysTyNm());
        resultInfo.setLogId(dbInfo.getLogId());
        resultInfo.setLogLvl(dbInfo.getLogLvl());
        resultInfo.setLogData(dbInfo.getLogData());
        resultInfo.setMonSeqNo(dbInfo.getMonSeqNo());
        resultInfo.setOCCRRNCDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getOCCRRNCDt()));

        resultInfo.setWkTyNm(dbInfo.getWkTyNm());
        resultInfo.setLocNm(dbInfo.getLocNm());
        resultInfo.setMonNm(dbInfo.getMonNm());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultInfo);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * List Pagination Usage History Data
     *
     * @param dataTableRequest
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/list-pagination-search")
    public ResponseEntity<ApiResultPagination> listPaginationMonitorLogData(@RequestBody DataTableRequest dataTableRequest) throws Exception {

        // Call the service method to retrieve paginated and filtered data

        SearchAndPaginationMonitorLogResultsDTO filteredAndSearchData = monitorLogService.getMonitorLogDataListPaginationAndSearch(dataTableRequest);

        List<ViewMonitorLogDTO> filteredData = filteredAndSearchData.getViewMonitorLogDTOS();

        // Convert the data to DTO format as required by the API response
        List<MonitorLogListItemResultDTO> resultList = filteredData.stream().map(dbInfo -> {

            var monitorLogListItemResultDTO = new MonitorLogListItemResultDTO();

            monitorLogListItemResultDTO.setSeqNo(dbInfo.getSeqNo());
            monitorLogListItemResultDTO.setSysTyNm(dbInfo.getSysTyNm());
            monitorLogListItemResultDTO.setLogId(dbInfo.getLogId());
            monitorLogListItemResultDTO.setLogLvl(dbInfo.getLogLvl());
            monitorLogListItemResultDTO.setLogData(dbInfo.getLogData());
            monitorLogListItemResultDTO.setMonSeqNo(dbInfo.getMonSeqNo());
            monitorLogListItemResultDTO.setOCCRRNCDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getOCCRRNCDt()));

            monitorLogListItemResultDTO.setWkTyNm(dbInfo.getWkTyNm());
            monitorLogListItemResultDTO.setLocNm(dbInfo.getLocNm());
            monitorLogListItemResultDTO.setMonNm(dbInfo.getMonNm());

            return monitorLogListItemResultDTO;
        }).collect(Collectors.toList());

        // Create the response object
        var apiResultPagination = new ApiResultPagination();

        apiResultPagination.setSuccess(true);
        apiResultPagination.setData(resultList);
        apiResultPagination.setDraw(dataTableRequest.getDraw());
        if(!monitorLogService.listAllMonitorLogData().isEmpty()){
            apiResultPagination.setRecordsTotal(monitorLogService.listAllMonitorLogData().get(0).getCountFilterData());

        }
        apiResultPagination.setRecordsFiltered(filteredAndSearchData.getCountFilterData());

        return ResponseEntity.ok(apiResultPagination);
    }

    /**
     * List Monitor Log Data By Date
     *
     * @param readMonitorLogByDateParamDTO
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/list-data-by-date")
    public ResponseEntity<ApiResult> detailMonitorLogByDate(@RequestBody ReadMonitorLogByDateParamDTO readMonitorLogByDateParamDTO) throws Exception {
        // Get All Monitor Information
        List<ViewMonitorLogDTO> dbList = this.monitorLogService.getMonitorLogDataByDate(readMonitorLogByDateParamDTO);
        // Use dbList to set Data for resultList
        List<MonitorLogListItemResultDTO> resultList = CollectionUtils.emptyIfNull(dbList).stream()
                .map(dbInfo -> {
                    var resultInfo = new MonitorLogListItemResultDTO();

                    resultInfo.setSeqNo(dbInfo.getSeqNo());
                    resultInfo.setSysTyNm(dbInfo.getSysTyNm());
                    resultInfo.setLogId(dbInfo.getLogId());
                    resultInfo.setLogLvl(dbInfo.getLogLvl());
                    resultInfo.setLogData(dbInfo.getLogData());
                    resultInfo.setMonSeqNo(dbInfo.getMonSeqNo());
                    resultInfo.setOCCRRNCDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getOCCRRNCDt()));

                    resultInfo.setWkTyNm(dbInfo.getWkTyNm());
                    resultInfo.setLocNm(dbInfo.getLocNm());
                    resultInfo.setMonNm(dbInfo.getMonNm());

                    return resultInfo;
                }).collect(Collectors.toList());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultList);

        return ResponseEntity.ok(apiResult);
    }
}
