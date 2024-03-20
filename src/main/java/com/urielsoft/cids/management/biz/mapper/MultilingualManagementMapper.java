package com.urielsoft.cids.management.biz.mapper;

import com.urielsoft.cids.management.biz.dto.*;
import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Multilingual Management Mapper
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-08-01
 */

@Repository
@Db1Mapper
public interface MultilingualManagementMapper {
    /**
     * Select All Data of Multilingual Management from Database
     *
     * @return
     * @throws DataAccessException
     */
    public List<ViewMultilingualManagementDTO> selectMultilingualManagementDataAllList() throws DataAccessException;

    /**
     * Select Data Of Multilingual Management By seq
     *
     * @param multilingualManagementSeq
     * @return
     * @throws DataAccessException
     */
    public ViewMultilingualManagementDTO selectMultilingualManagementDataBySeq(@Param("multilingualManagementSeq") int multilingualManagementSeq) throws DataAccessException;

    /**
     * Select Data Of Multilingual Management By TagId
     *
     * @param tagId
     * @return
     * @throws DataAccessException
     */
    public ViewMultilingualManagementDTO selectMultilingualManagementByTagId(@Param("tagId") String tagId) throws DataAccessException;

    /**
     * Add new Multilingual Management
     *
     * @param createMultilingualManagementDTO
     * @return
     * @throws DataAccessException
     */
    public int insertMultilingualManagementData(CreateMultilingualManagementDTO createMultilingualManagementDTO) throws DataAccessException;

    /**
     * Update Multilingual Management
     *
     * @param updateMultilingualManagementDTO
     * @return
     * @throws DataAccessException
     */
    public int updateMultilingualManagementData(UpdateMultilingualManagementDTO updateMultilingualManagementDTO) throws DataAccessException;

}
