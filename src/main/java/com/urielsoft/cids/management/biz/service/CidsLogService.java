package com.urielsoft.cids.management.biz.service;

import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-09-06
 */
public interface CidsLogService {

    /**
     * Create New Log ID
     *
     * @return Log ID
     */
    public String createNewLogId();

    /**
     * Register Operation Log
     *
     * @param logId
     * @param cidsLogLevel
     * @param logData
     */
    public void registerOperationLog(String logId, CidsLogLevelEnum cidsLogLevel, String logData);

    /**
     * Register Operation Log With MonitorId
     *
     * @param logId
     * @param cidsLogLevel
     * @param logData
     * @param monitorId
     */
    public void registerOperationLog(String logId, CidsLogLevelEnum cidsLogLevel, String logData, Integer monitorId);

    /**
     * Register Protocol Log
     *
     * @param transactionId
     * @param protocolName
     * @param monitorId
     * @param result
     * @param requestData
     * @param responseData
     */
    public void registerProtocolLog(String transactionId, String protocolName, Integer monitorId, String result, String requestData, String responseData, int occrrncUserSeqNo);

    /**
     * Register Protocol Log No Monitor Id
     * @param transactionId
     * @param protocolName
     * @param result
     * @param requestData
     * @param responseData
     * @param occrrncUserSeqNo
     */
    public void registerProtocolLog(String transactionId, String protocolName, String result, String requestData, String responseData, int occrrncUserSeqNo);
}
