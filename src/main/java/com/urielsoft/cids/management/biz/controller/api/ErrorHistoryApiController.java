package com.urielsoft.cids.management.biz.controller.api;

import com.urielsoft.cids.management.biz.common.utils.DateTimeUtils;
import com.urielsoft.cids.management.biz.dto.SearchAndPaginationErrorHistoryResultsDTO;
import com.urielsoft.cids.management.biz.dto.ViewErrorHistoryDTO;
import com.urielsoft.cids.management.biz.dto.api.result.ErrorHistoryDetailResultDTO;
import com.urielsoft.cids.management.biz.dto.api.result.ErrorHistoryListItemResultDTO;
import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;
import com.urielsoft.cids.management.biz.service.ErrorHistoryService;
import com.urielsoft.cids.management.common.api.ApiResult;
import com.urielsoft.cids.management.common.api.ApiResultPagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Error History CONTROLLER
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-01
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/error-history")
public class ErrorHistoryApiController {
    /**
     * Error History Service
     */
    private final ErrorHistoryService errorHistoryService;

    /**
     * Get Error History By seq
     *
     * @param errorHistorySeq
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/detail/{errorHistorySeq}")
    public ResponseEntity<ApiResult> detailErrorHistoryData(@PathVariable("errorHistorySeq") int errorHistorySeq) throws Exception {
        // Get Error History Information By Seq
        ViewErrorHistoryDTO dbInfo = this.errorHistoryService.getErrorHistoryDataBySeq(errorHistorySeq);
        //  Use dbInfo to set Data for resultInfo
        var resultInfo = new ErrorHistoryDetailResultDTO();

        resultInfo.setSeqNo(dbInfo.getSeqNo());
        resultInfo.setWkTyNm(dbInfo.getWkTyNm());
        resultInfo.setWkTySeqNo(dbInfo.getWkTySeqNo());
        resultInfo.setLocTySeqNo(dbInfo.getLocTySeqNo());
        resultInfo.setMonNm(dbInfo.getMonNm());
        resultInfo.setMonSeqNo(dbInfo.getMonSeqNo());
        resultInfo.setLogData(dbInfo.getLogData());
        resultInfo.setLocNm(dbInfo.getLocNm());
        resultInfo.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));

        var apiResult = new ApiResult(true);
        apiResult.setData(resultInfo);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * List Pagination Error History Data
     *
     * @param dataTableRequest
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/list-pagination-search")
    public ResponseEntity<ApiResultPagination> listPaginationErrorHistoryData(@RequestBody DataTableRequest dataTableRequest) throws Exception {

        // Call the service method to retrieve paginated and filtered data
        SearchAndPaginationErrorHistoryResultsDTO filteredAndSearchData = errorHistoryService.getErrorHistoryDataListPaginationAndSearch(dataTableRequest);

        List<ViewErrorHistoryDTO> filteredData = filteredAndSearchData.getViewErrorHistoryDTOS();

        // Convert the data to DTO format as required by the API response
        List<ErrorHistoryListItemResultDTO> resultList = filteredData.stream().map(dbInfo -> {
            var errorHistoryListItemResultDTO = new ErrorHistoryListItemResultDTO();

            // Map the properties from ViewUsageHistoryDTO to UsageHistoryListItemResultDTO
            errorHistoryListItemResultDTO.setSeqNo(dbInfo.getSeqNo());
            errorHistoryListItemResultDTO.setWkTyNm(dbInfo.getWkTyNm());
            errorHistoryListItemResultDTO.setWkTySeqNo(dbInfo.getWkTySeqNo());
            errorHistoryListItemResultDTO.setLocTySeqNo(dbInfo.getLocTySeqNo());
            errorHistoryListItemResultDTO.setMonNm(dbInfo.getMonNm());
            errorHistoryListItemResultDTO.setMonSeqNo(dbInfo.getMonSeqNo());
            errorHistoryListItemResultDTO.setLogData(dbInfo.getLogData());
            errorHistoryListItemResultDTO.setLocNm(dbInfo.getLocNm());
            errorHistoryListItemResultDTO.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));

            return errorHistoryListItemResultDTO;
        }).collect(Collectors.toList());

        // Create the response object
        var apiResultPagination = new ApiResultPagination();

        apiResultPagination.setSuccess(true);
        apiResultPagination.setData(resultList);
        apiResultPagination.setDraw(dataTableRequest.getDraw());
        apiResultPagination.setRecordsTotal(errorHistoryService.listAllErrorHistoryData().get(0).getCountFilterData());
        apiResultPagination.setRecordsFiltered(filteredAndSearchData.getCountFilterData());

        return ResponseEntity.ok(apiResultPagination);
    }
}
