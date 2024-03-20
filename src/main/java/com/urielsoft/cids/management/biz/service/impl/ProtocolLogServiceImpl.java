package com.urielsoft.cids.management.biz.service.impl;

import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.dto.ViewProtocolLogDTO;
import com.urielsoft.cids.management.biz.mapper.ProtocolLogMapper;
import com.urielsoft.cids.management.biz.service.CidsLogService;
import com.urielsoft.cids.management.biz.service.ProtocolLogService;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.common.exception.BizException;
import com.urielsoft.cids.management.common.exception.ExceptionCode;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Protocol Log Service Impl
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProtocolLogServiceImpl implements ProtocolLogService {
    /**
     * Cids Monitor Manager Mapper
     */
    private final ProtocolLogMapper protocolLogMapper;

    /**
     * Cids Log Service
     */
    private final CidsLogService cidsLogService;

    /**
     * Usage History Service
     */
    private final UsageHistoryService usageHistoryService;

    /**
     * List All Protocol Log By TransactionId Data
     *
     * @param transactionId
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<ViewProtocolLogDTO> listAllProtocolLogByTransactionIdData(String transactionId) throws DataAccessException {

        List<ViewProtocolLogDTO> data = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            data = this.protocolLogMapper.selectProtocolLogDataByTransactionId(transactionId);
            if (data == null){
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "ProtocolLog");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETALl, "ProtocolLog");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "ProtocolLog");
            throw e;
        }
        return data;
    }

    /**
     * Get Protocol Log Data By SeqNo
     *
     * @param protocolLogSeqNo
     * @return
     * @throws DataAccessException
     */
    @Override
    public ViewProtocolLogDTO getProtocolLogDataBySeqNo(int protocolLogSeqNo) throws DataAccessException {
        ViewProtocolLogDTO viewProtocolLogDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewProtocolLogDTO = this.protocolLogMapper.selectProtocolLogDataBySeq(protocolLogSeqNo);
            if (viewProtocolLogDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "ProtocolLog");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "ProtocolLog");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "ProtocolLog");
            throw e;
        }
        return viewProtocolLogDTO;

    }

}