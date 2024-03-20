package com.urielsoft.cids.management.biz.controller.api;

import com.urielsoft.cids.management.biz.common.utils.DateTimeUtils;
import com.urielsoft.cids.management.biz.dto.ViewCidsMonitorMonitoringDTO;
import com.urielsoft.cids.management.biz.dto.api.result.CidsMonitorMonitoringDetailResultDTO;
import com.urielsoft.cids.management.biz.dto.api.result.CidsMonitorMonitoringListItemResultDTO;
import com.urielsoft.cids.management.biz.service.CidsMonitorMonitoringService;
import com.urielsoft.cids.management.common.api.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CIDS Monitor Monitoring CONTROLLER
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-07-20
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/cids-monitor-monitoring")
public class CidsMonitorMonitoringApiController {
    /**
     * Cids Monitor Monitoring Service
     */
    private final CidsMonitorMonitoringService cidsMonitorMonitoringService;

    /**
     * Get All List Cids Monitor Monitoring
     *
     * @param wkTySeqNo
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/list-export-import/{wkTySeqNo}")
    public ResponseEntity<ApiResult> listCidsMonitorMonitoringData(@PathVariable("wkTySeqNo") Integer wkTySeqNo) throws Exception {
        // Get All Monitor Information
        List<ViewCidsMonitorMonitoringDTO> dbList = this.cidsMonitorMonitoringService.listAllCidsMonitorMonitoringData(wkTySeqNo);
        // Use dbList to set Data for resultList
        List<CidsMonitorMonitoringListItemResultDTO> resultList = CollectionUtils.emptyIfNull(dbList).stream()
                .map(dbInfo -> {
                    var cidsMonitorMonitoringListItemResultDTO = new CidsMonitorMonitoringListItemResultDTO();

                    cidsMonitorMonitoringListItemResultDTO.setWkTyNm(dbInfo.getWkTyNm());
                    cidsMonitorMonitoringListItemResultDTO.setWkTySeqNo(dbInfo.getWkTySeqNo());
                    cidsMonitorMonitoringListItemResultDTO.setSeqNo(dbInfo.getSeqNo());
                    cidsMonitorMonitoringListItemResultDTO.setMonSeqNo(dbInfo.getMonSeqNo());
                    cidsMonitorMonitoringListItemResultDTO.setSts(dbInfo.getSts());
                    cidsMonitorMonitoringListItemResultDTO.setStsCd(dbInfo.getStsCd());
                    cidsMonitorMonitoringListItemResultDTO.setMonNm(dbInfo.getMonNm());
                    cidsMonitorMonitoringListItemResultDTO.setLocNm(dbInfo.getLocNm());
                    cidsMonitorMonitoringListItemResultDTO.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));
                    cidsMonitorMonitoringListItemResultDTO.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getMdfyDt()));

                    return cidsMonitorMonitoringListItemResultDTO;
                }).collect(Collectors.toList());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultList);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Get Monitor Monitoring By Seq
     *
     * @param cidsMonitorMonitoringSeq
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/detail/{cidsMonitorMonitoringSeq}")
    public ResponseEntity<ApiResult> detailCidsMonitorManagerData(@PathVariable("cidsMonitorMonitoringSeq") int cidsMonitorMonitoringSeq) throws Exception {
        // Get User Information By id
        ViewCidsMonitorMonitoringDTO dbInfo = this.cidsMonitorMonitoringService.getCidsMonitorMonitoringDataBySeq(cidsMonitorMonitoringSeq);
        //  Use dbInfo to set Data for resultInfo
        var resultInfo = new CidsMonitorMonitoringDetailResultDTO();

        resultInfo.setWkTyNm(dbInfo.getWkTyNm());
        resultInfo.setSeqNo(dbInfo.getSeqNo());
        resultInfo.setWkTySeqNo(dbInfo.getWkTySeqNo());
        resultInfo.setMonSeqNo(dbInfo.getMonSeqNo());
        resultInfo.setSts(dbInfo.getSts());
        resultInfo.setStsCd(dbInfo.getStsCd());
        resultInfo.setMonNm(dbInfo.getMonNm());
        resultInfo.setLocNm(dbInfo.getLocNm());
        resultInfo.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));
        resultInfo.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getMdfyDt()));

        var apiResult = new ApiResult(true);
        apiResult.setData(resultInfo);

        return ResponseEntity.ok(apiResult);
    }

}
