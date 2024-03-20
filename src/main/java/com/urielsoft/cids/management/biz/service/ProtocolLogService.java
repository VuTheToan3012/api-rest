package com.urielsoft.cids.management.biz.service;

import com.urielsoft.cids.management.biz.dto.ViewProtocolLogDTO;

import java.util.List;

/**
 * Protocol Log Service
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-28
 */
public interface ProtocolLogService {
    /**
     * List All Protocol Log Data
     *
     * @return
     * @throws Exception
     */
    public List<ViewProtocolLogDTO> listAllProtocolLogByTransactionIdData(String transactionId) throws Exception;

    /**
     * Get Protocol Log Data By SeqNo
     *
     * @param protocolLogSeqNo
     * @return
     * @throws Exception
     */
    public ViewProtocolLogDTO getProtocolLogDataBySeqNo(int protocolLogSeqNo) throws Exception;
}
