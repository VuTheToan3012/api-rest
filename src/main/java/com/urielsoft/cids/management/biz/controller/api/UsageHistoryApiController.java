package com.urielsoft.cids.management.biz.controller.api;

import com.urielsoft.cids.management.biz.common.utils.DateTimeUtils;
import com.urielsoft.cids.management.biz.dto.SearchAndPaginationUsageHistoryResultsDTO;
import com.urielsoft.cids.management.biz.dto.ViewUsageHistoryDTO;
import com.urielsoft.cids.management.biz.dto.api.result.UsageHistoryDetailResultDTO;
import com.urielsoft.cids.management.biz.dto.api.result.UsageHistoryListItemResultDTO;
import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.common.api.ApiResult;
import com.urielsoft.cids.management.common.api.ApiResultPagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Usage History CONTROLLER
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-26
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/usage-history")
public class UsageHistoryApiController {
    /**
     * Usage History Service
     */
    private final UsageHistoryService usageHistoryService;

    /**
     * Get Usage History By Seq
     *
     * @param usageHistorySeq
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/detail/{usageHistorySeq}")
    public ResponseEntity<ApiResult> detailUsageHistoryData(@PathVariable("usageHistorySeq") int usageHistorySeq) throws Exception {
        // Get Usage History Information By Seq
        ViewUsageHistoryDTO dbInfo = this.usageHistoryService.getUsageHistoryDataBySeq(usageHistorySeq);
        //  Use dbInfo to set Data for resultInfo
        var resultInfo = new UsageHistoryDetailResultDTO();

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
     * List Pagination Usage History Data
     *
     * @param dataTableRequest
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/list-pagination-search")
    public ResponseEntity<ApiResultPagination> listPaginationUsageHistoryData(@RequestBody DataTableRequest dataTableRequest) throws Exception {

        // Call the service method to retrieve paginated and filtered data

        SearchAndPaginationUsageHistoryResultsDTO filteredAndSearchData = usageHistoryService.getUsageHistoryDataListPaginationAndSearch(dataTableRequest);

        List<ViewUsageHistoryDTO> filteredData = filteredAndSearchData.getViewUsageHistoryDTOS();

        // Convert the data to DTO format as required by the API response
        List<UsageHistoryListItemResultDTO> resultList = filteredData.stream().map(dbInfo -> {

            var usageHistoryListItemResultDTO = new UsageHistoryListItemResultDTO();
            // Map the properties from ViewUsageHistoryDTO to UsageHistoryListItemResultDTO
            usageHistoryListItemResultDTO.setSeqNo(dbInfo.getSeqNo());
            usageHistoryListItemResultDTO.setWkTyNm(dbInfo.getWkTyNm());
            usageHistoryListItemResultDTO.setWkTySeqNo(dbInfo.getWkTySeqNo());
            usageHistoryListItemResultDTO.setLocTySeqNo(dbInfo.getLocTySeqNo());
            usageHistoryListItemResultDTO.setMonNm(dbInfo.getMonNm());
            usageHistoryListItemResultDTO.setMonSeqNo(dbInfo.getMonSeqNo());
            usageHistoryListItemResultDTO.setLogData(dbInfo.getLogData());
            usageHistoryListItemResultDTO.setLocNm(dbInfo.getLocNm());
            usageHistoryListItemResultDTO.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));

            return usageHistoryListItemResultDTO;
        }).collect(Collectors.toList());

        // Create the response object
        var apiResultPagination = new ApiResultPagination();

        apiResultPagination.setSuccess(true);
        apiResultPagination.setData(resultList);
        apiResultPagination.setDraw(dataTableRequest.getDraw());
        apiResultPagination.setRecordsTotal(usageHistoryService.listAllUsageHistoryData().get(0).getCountFilterData());
        apiResultPagination.setRecordsFiltered(filteredAndSearchData.getCountFilterData());

        return ResponseEntity.ok(apiResultPagination);
    }
}
