package com.urielsoft.cids.management.biz.mapper;

import com.urielsoft.cids.management.biz.dto.*;
import com.urielsoft.cids.management.biz.dto.api.param.CidsMonitorConfigurationFindLocAndTyParamDTO;
import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Cids Monitor Monitoring Mapper
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */
@Repository
@Db1Mapper
public interface CidsMonitorMonitoringMapper {
    /**
     * Add New Monitor Monitoring
     *
     * @param createCidsMonitorMonitoringDTO
     * @return
     * @throws DataAccessException
     */
    public int insertCidsMonitorMonitoringData(CreateCidsMonitorMonitoringDTO createCidsMonitorMonitoringDTO) throws DataAccessException;

    /**
     * Select All Data of Monitor Monitoring from Database
     *
     * @param  wkTySeqNo
     * @return
     * @throws DataAccessException
     */
    public List<ViewCidsMonitorMonitoringDTO> selectCidsMonitorMonitoringDataAllList(Integer wkTySeqNo) throws DataAccessException;

    /**
     * Select Data Of Monitor Monitoring By monitorSeqNo
     *
     * @param cidsMonitorMonitoringSeq
     * @return
     * @throws DataAccessException
     */
    public ViewCidsMonitorMonitoringDTO selectCidsMonitorMonitoringDataBySeq(@Param("cidsMonitorMonitoringSeq") int cidsMonitorMonitoringSeq) throws DataAccessException;

    /**
     * Update Monitor Monitoring
     *
     * @param updateCidsMonitorMonitoringDTO
     * @return
     * @throws DataAccessException
     */
    public int updateCidsMonitorMonitoringData(UpdateCidsMonitorMonitoringDTO updateCidsMonitorMonitoringDTO) throws DataAccessException;

    /**
     * Find Cids Monitor Manager By Loc And Ty
     * @param cidsMonitorConfigurationFindLocAndTyParamDTO
     * @return
     * @throws DataAccessException
     */
    public ViewCidsMonitorMonitoringDTO findCidsMonitorManagerByLocAndTy(CidsMonitorConfigurationFindLocAndTyParamDTO cidsMonitorConfigurationFindLocAndTyParamDTO) throws DataAccessException;

}
