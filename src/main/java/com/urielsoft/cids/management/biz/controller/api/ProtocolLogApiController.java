package com.urielsoft.cids.management.biz.controller.api;

import com.urielsoft.cids.management.biz.common.utils.DateTimeUtils;
import com.urielsoft.cids.management.biz.dto.ViewProtocolLogDTO;
import com.urielsoft.cids.management.biz.dto.api.result.ProtocolLogDetailResultDTO;
import com.urielsoft.cids.management.biz.dto.api.result.ProtocolLogListItemResultDTO;
import com.urielsoft.cids.management.biz.service.ProtocolLogService;
import com.urielsoft.cids.management.common.api.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Protocol Log Api Controller
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-28
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/protocol-log")
public class ProtocolLogApiController {
    /**
     * Protocol Log Service
     */
    private final ProtocolLogService protocolLogService;

    /**
     * Get All List Protocol Log Data
     *
     * @param transactionId
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/list/{transactionId}")
    public ResponseEntity<ApiResult> listProtocolLogData(@PathVariable("transactionId") String transactionId) throws Exception {
        // Get All Protocol Log By Transaction Id
        List<ViewProtocolLogDTO> dbList = this.protocolLogService.listAllProtocolLogByTransactionIdData(transactionId);
        // Use dbList to set Data for resultList
        List<ProtocolLogListItemResultDTO> resultList = CollectionUtils.emptyIfNull(dbList).stream()
                .map(dbInfo -> {
                    var protocolLogListItemResultDTO = new ProtocolLogListItemResultDTO();

                    protocolLogListItemResultDTO.setSeqNo(dbInfo.getSeqNo());
                    protocolLogListItemResultDTO.setTranId(dbInfo.getTranId());
                    protocolLogListItemResultDTO.setProtNm(dbInfo.getProtNm());
                    protocolLogListItemResultDTO.setMonSeqNo(dbInfo.getMonSeqNo());
                    protocolLogListItemResultDTO.setRslt(dbInfo.getRslt());
                    protocolLogListItemResultDTO.setReqData(dbInfo.getReqData());
                    protocolLogListItemResultDTO.setRepnData(dbInfo.getRepnData());
                    protocolLogListItemResultDTO.setOCCRRNCUserSeqNo(dbInfo.getOCCRRNCUserSeqNo());
                    protocolLogListItemResultDTO.setOCCRRNCDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getOCCRRNCDt()));

                    return protocolLogListItemResultDTO;
                }).collect(Collectors.toList());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultList);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Detail Protocol Log Data
     *
     * @param protocolLogSeqNo
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/detail/{protocolLogSeqNo}")
    public ResponseEntity<ApiResult> detailProtocolLogData(@PathVariable("protocolLogSeqNo") int protocolLogSeqNo) throws Exception {
        // Get Protocol Log Data By SeqNo
        ViewProtocolLogDTO dbInfo = this.protocolLogService.getProtocolLogDataBySeqNo(protocolLogSeqNo);
        //  Use dbInfo to set Data for resultInfo
        var resultInfo = new ProtocolLogDetailResultDTO();

        resultInfo.setSeqNo(dbInfo.getSeqNo());
        resultInfo.setTranId(dbInfo.getTranId());
        resultInfo.setProtNm(dbInfo.getProtNm());
        resultInfo.setMonSeqNo(dbInfo.getMonSeqNo());
        resultInfo.setRslt(dbInfo.getRslt());
        resultInfo.setReqData(dbInfo.getReqData());
        resultInfo.setRepnData(dbInfo.getRepnData());
        resultInfo.setOCCRRNCUserSeqNo(dbInfo.getOCCRRNCUserSeqNo());
        resultInfo.setOCCRRNCDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getOCCRRNCDt()));

        var apiResult = new ApiResult(true);
        apiResult.setData(resultInfo);

        return ResponseEntity.ok(apiResult);
    }
}
