package com.urielsoft.cids.management.biz.mapper;

import com.urielsoft.cids.management.biz.dto.*;
import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Advertise Management Mapper
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-23
 */
@Repository
@Db1Mapper
public interface AdvertiseManagementMapper {

    /**
     * Insert Advertise Management Data
     *
     * @param createCidsMonitorManagerDTO
     * @return
     * @throws DataAccessException
     */
    public int insertAdvertiseManagementData(CreateAdvertiseManagementDTO createCidsMonitorManagerDTO) throws DataAccessException;

    /**
     * Select All Data of Advertise Mng
     *
     * @return
     * @throws DataAccessException
     */
    public List<ViewAdvertiseManagementDTO> selectAdvertiseManagementDataAllList() throws DataAccessException;

    /**
     * Select Advertise Management Data By Seq
     *
     * @param advertiseManagementSeq
     * @return
     * @throws DataAccessException
     */
    public ViewAdvertiseManagementDTO selectAdvertiseManagementDataBySeq(@Param("advertiseManagementSeq") int advertiseManagementSeq) throws DataAccessException;

    /**
     * Update Advertise Management Data
     *
     * @param updateAdvertiseManagementDTO
     * @return
     * @throws DataAccessException
     */
    public int updateAdvertiseManagementData(UpdateAdvertiseManagementDTO updateAdvertiseManagementDTO) throws DataAccessException;

    /**
     * DELETE AdvertiseMng Data
     *
     * @param advertiseManagementSeq
     * @return
     * @throws DataAccessException
     */
    public int deleteAdvertiseManagementDataBySeq(@Param("advertiseManagementSeq") int advertiseManagementSeq) throws DataAccessException;

}
