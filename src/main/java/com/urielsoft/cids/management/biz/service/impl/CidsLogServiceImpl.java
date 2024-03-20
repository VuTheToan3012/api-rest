package com.urielsoft.cids.management.biz.service.impl;

import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.mapper.CidsLogMapper;
import com.urielsoft.cids.management.biz.service.CidsLogService;
import com.urielsoft.cids.management.common.mybatis.MyBatisResultHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.UUID;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-09-06
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CidsLogServiceImpl implements CidsLogService {

    private final CidsLogMapper cidsLogMapper;

    private static final String LOG_SYSTEM_TYPE_NAME = "OPR-SITE";

    @Override
    public String createNewLogId() {
        return UUID.randomUUID().toString();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void registerOperationLog(String logId, CidsLogLevelEnum cidsLogLevel, String logData) {
        Assert.hasText(logId, "Log ID is required.");
        Assert.notNull(cidsLogLevel, "Log Level is required.");
        Assert.hasText(logData, "Log Data is required.");

        if (CidsLogLevelEnum.INFO == cidsLogLevel) {
            log.info(logData);
        } else if (CidsLogLevelEnum.ERROR == cidsLogLevel) {
            log.error(logData);
        }

        MyBatisResultHelper.checkAffectedRow(this.cidsLogMapper.insertCidsOperationLog(LOG_SYSTEM_TYPE_NAME, logId, cidsLogLevel.name(), logData, null), 1);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void registerOperationLog(String logId, CidsLogLevelEnum cidsLogLevel, String logData, Integer monitorId) {
        Assert.hasText(logId, "Log ID is required.");
        Assert.notNull(cidsLogLevel, "Log Level is required.");
        Assert.hasText(logData, "Log Data is required.");
        Assert.notNull(monitorId, "Monitor ID is Required.");

        if (CidsLogLevelEnum.INFO == cidsLogLevel) {
            log.info(logData);
        } else if (CidsLogLevelEnum.ERROR == cidsLogLevel) {
            log.error(logData);
        }

        MyBatisResultHelper.checkAffectedRow(this.cidsLogMapper.insertCidsOperationLog(LOG_SYSTEM_TYPE_NAME, logId, cidsLogLevel.name(), logData, monitorId), 1);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void registerProtocolLog(String transactionId, String protocolName, Integer monitorId, String result, String requestData, String responseData, int occrrncUserSeqNo) {
        Assert.hasText(transactionId, "Transaction ID is required.");
        Assert.hasText(protocolName, "Protocol Name is required.");
        Assert.notNull(monitorId, "Monitor ID is required.");
        Assert.hasText(result, "Result is required.");
        Assert.hasText(requestData, "Request Data is required.");

        MyBatisResultHelper.checkAffectedRow(this.cidsLogMapper.insertCidsProtocolLog(transactionId, protocolName, monitorId, result, requestData, responseData, occrrncUserSeqNo), 1);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void registerProtocolLog(String transactionId, String protocolName, String result, String requestData, String responseData, int occrrncUserSeqNo) {
        Assert.hasText(transactionId, "Transaction ID is required.");
        Assert.hasText(protocolName, "Protocol Name is required.");
        Assert.hasText(result, "Result is required.");
        Assert.hasText(requestData, "Request Data is required.");

        MyBatisResultHelper.checkAffectedRow(this.cidsLogMapper.insertCidsProtocolLogNoMonitorSeqNo(transactionId, protocolName, result, requestData, responseData, occrrncUserSeqNo), 1);
    }
}
