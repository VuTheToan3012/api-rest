package com.urielsoft.cids.management.biz.controller.api;

import com.urielsoft.cids.management.biz.common.utils.DateTimeUtils;
import com.urielsoft.cids.management.biz.dto.*;
import com.urielsoft.cids.management.biz.dto.api.param.ReadSystemLogByDateParamDTO;
import com.urielsoft.cids.management.biz.dto.api.result.*;
import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;
import com.urielsoft.cids.management.biz.service.SystemLogService;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.common.api.ApiResult;
import com.urielsoft.cids.management.common.api.ApiResultPagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * System Log Api Controller
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-27
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/system-log")
public class SystemLogApiController {
    /**
     * System Log Service
     */
    private final SystemLogService systemLogService;

    /**
     * Detail System Log Data
     *
     * @param systemLogSeqNo
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/detail/{systemLogSeqNo}")
    public ResponseEntity<ApiResult> detailSystemLogData(@PathVariable("systemLogSeqNo") int systemLogSeqNo) throws Exception {
        // Get System Log Information By Seq
        ViewSystemLogDTO dbInfo = this.systemLogService.getSystemLogDataBySeq(systemLogSeqNo);
        //  Use dbInfo to set Data for resultInfo
        var resultInfo = new SystemLogDetailResultDTO();

        resultInfo.setSeqNo(dbInfo.getSeqNo());
        resultInfo.setSysTyNm(dbInfo.getSysTyNm());
        resultInfo.setLogId(dbInfo.getLogId());
        resultInfo.setLogLvl(dbInfo.getLogLvl());
        resultInfo.setLogData(dbInfo.getLogData());
        resultInfo.setMonSeqNo(dbInfo.getMonSeqNo());
        resultInfo.setOCCRRNCDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getOCCRRNCDt()));

        var apiResult = new ApiResult(true);
        apiResult.setData(resultInfo);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * List Pagination System Log Data
     *
     * @param dataTableRequest
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/list-pagination-search")
    public ResponseEntity<ApiResultPagination> listPaginationSystemLogData(@RequestBody DataTableRequest dataTableRequest) throws Exception {

        // Call the service method to retrieve paginated and filtered data

        SearchAndPaginationSystemLogResultsDTO filteredAndSearchData = systemLogService.getSystemLogDataListPaginationAndSearch(dataTableRequest);

        List<ViewSystemLogDTO> filteredData = filteredAndSearchData.getViewSystemLogDTOS();

        // Convert the data to DTO format as required by the API response
        List<SystemLogListItemResultDTO> resultList = filteredData.stream().map(dbInfo -> {

            var systemLogListItemResultDTO = new SystemLogListItemResultDTO();

            systemLogListItemResultDTO.setSeqNo(dbInfo.getSeqNo());
            systemLogListItemResultDTO.setSysTyNm(dbInfo.getSysTyNm());
            systemLogListItemResultDTO.setLogId(dbInfo.getLogId());
            systemLogListItemResultDTO.setLogLvl(dbInfo.getLogLvl());
            systemLogListItemResultDTO.setLogData(dbInfo.getLogData());
            systemLogListItemResultDTO.setMonSeqNo(dbInfo.getMonSeqNo());
            systemLogListItemResultDTO.setOCCRRNCDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getOCCRRNCDt()));

            return systemLogListItemResultDTO;
        }).collect(Collectors.toList());

        // Create the response object
        var apiResultPagination = new ApiResultPagination();

        apiResultPagination.setSuccess(true);
        apiResultPagination.setData(resultList);
        apiResultPagination.setDraw(dataTableRequest.getDraw());
        apiResultPagination.setRecordsTotal(systemLogService.listAllSystemLogData().get(0).getCountFilterData());
        apiResultPagination.setRecordsFiltered(filteredAndSearchData.getCountFilterData());

        return ResponseEntity.ok(apiResultPagination);
    }

    /**
     * List System Log Data By Date
     *
     * @param readSystemLogByDateParamDTO
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/list-data-by-date")
    public ResponseEntity<ApiResult> detailSystemLogByDate(@RequestBody ReadSystemLogByDateParamDTO readSystemLogByDateParamDTO) throws Exception {
        // Get All Monitor Information
        List<ViewSystemLogDTO> dbList = this.systemLogService.getSystemLogDataByDate(readSystemLogByDateParamDTO);
        // Use dbList to set Data for resultList
        List<SystemLogListItemResultDTO> resultList = CollectionUtils.emptyIfNull(dbList).stream()
                .map(dbInfo -> {
                    var systemLogListItemResultDTO = new SystemLogListItemResultDTO();

                    systemLogListItemResultDTO.setSeqNo(dbInfo.getSeqNo());
                    systemLogListItemResultDTO.setSysTyNm(dbInfo.getSysTyNm());
                    systemLogListItemResultDTO.setLogId(dbInfo.getLogId());
                    systemLogListItemResultDTO.setLogLvl(dbInfo.getLogLvl());
                    systemLogListItemResultDTO.setLogData(dbInfo.getLogData());
                    systemLogListItemResultDTO.setMonSeqNo(dbInfo.getMonSeqNo());
                    systemLogListItemResultDTO.setOCCRRNCDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getOCCRRNCDt()));

                    return systemLogListItemResultDTO;
                }).collect(Collectors.toList());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultList);

        return ResponseEntity.ok(apiResult);
    }
}
