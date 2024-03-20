package com.urielsoft.cids.management.biz.mapper;

import com.urielsoft.cids.management.biz.dto.ViewProtocolLogDTO;
import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Protocol Log Mapper
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-28
 */
@Repository
@Db1Mapper
public interface ProtocolLogMapper {
    /**
     * Select Protocol Log Data By TransactionId
     *
     * @param transactionId
     * @return
     * @throws DataAccessException
     */
    public List<ViewProtocolLogDTO> selectProtocolLogDataByTransactionId(String transactionId) throws DataAccessException;

    /**
     * Select Protocol Log Data By SeqNo
     *
     * @param protocolLogSeqNo
     * @return
     * @throws DataAccessException
     */
    public ViewProtocolLogDTO selectProtocolLogDataBySeq(@Param("protocolLogSeqNo") int protocolLogSeqNo) throws DataAccessException;

}
