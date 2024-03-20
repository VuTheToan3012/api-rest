package com.urielsoft.cids.management.biz.mapper;

import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-09-06
 */
@Db1Mapper
@Repository
public interface CidsLogMapper {

	/**
	 * Insert CIDS Operation Log
	 *
	 * @param systemTypeName
	 * @param logId
	 * @param logLevel
	 * @param logData
	 * @param monSeqNo
	 * @return
	 * @throws DataAccessException
	 */
	public int insertCidsOperationLog(@Param("systemTypeName") String systemTypeName, @Param("logId") String logId, @Param("logLevel") String logLevel, @Param("logData") String logData, @Param("monSeqNo") Integer monSeqNo) throws DataAccessException;

	/**
	 * Insert CIDS Protocol Log
	 * @param transactionId
	 * @param protocolName
	 * @param monSeqNo
	 * @param result
	 * @param requestData
	 * @param responseData
	 * @param occrrncDtUserSeqNo
	 * @return
	 * @throws DataAccessException
	 */
	public int insertCidsProtocolLog(@Param("transactionId") String transactionId, @Param("protocolName") String protocolName, @Param("monSeqNo") Integer monSeqNo, @Param("result") String result, @Param("requestData") String requestData, @Param("responseData") String responseData, @Param("occrrncDtUserSeqNo") int occrrncDtUserSeqNo ) throws DataAccessException;

	/**
	 * Insert Cids Protocol Log No Monitor SeqNo
	 * @param transactionId
	 * @param protocolName
	 * @param result
	 * @param requestData
	 * @param responseData
	 * @param occrrncUserSeqNo
	 * @return
	 * @throws DataAccessException
	 */
	public int insertCidsProtocolLogNoMonitorSeqNo(@Param("transactionId") String transactionId, @Param("protocolName") String protocolName, @Param("result") String result, @Param("requestData") String requestData, @Param("responseData") String responseData, @Param("occrrncUserSeqNo") int occrrncUserSeqNo ) throws DataAccessException;
}
