package com.urielsoft.cids.management.biz.mapper;

import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;
import com.urielsoft.cids.management.biz.dto.*;
import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Cids Notify Manager Mapper
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-07-24
 */

@Repository
@Db1Mapper
public interface CidsNotifyManagerMapper {
    /**
     * Select All Data of CidsNotifyManager from Database
     *
     * @return
     * @throws DataAccessException
     */
    public List<ViewCidsNotifyManagerDTO> selectNotifyManagerDataAllList() throws DataAccessException;

    /**
     * Select Data Of Notify By cidsNotifyManagerSeq
     *
     * @param cidsNotifyManagerSeq
     * @return
     * @throws DataAccessException
     */
    public ViewCidsNotifyManagerDTO selectCidsNotifyManagerDataBySeq(@Param("cidsNotifyManagerSeq") int cidsNotifyManagerSeq) throws DataAccessException;

    /**
     * Add New Notify
     *
     * @param createCidsNotifyManagerDTO
     * @return
     * @throws DataAccessException
     */
    public int insertCidsNotifyManagerData(CreateCidsNotifyManagerDTO createCidsNotifyManagerDTO) throws DataAccessException;

    /**
     * Update Notify
     *
     * @param updateCidsNotifyManagerDTO
     * @return
     * @throws DataAccessException
     */
    public int updateCidsNotifyManagerData(UpdateCidsNotifyManagerDTO updateCidsNotifyManagerDTO) throws DataAccessException;

    /**
     * Select Cids Notify Manager Data List Pagination And Search
     * @param start
     * @param length
     * @param searchCidsNotifyManagerPaginationStatusDTO
     * @return
     */
    List<ViewCidsNotifyManagerDTO> selectCidsNotifyManagerDataListPaginationAndSearch(int start, int length, SearchCidsNotifyManagerPaginationStatusDTO searchCidsNotifyManagerPaginationStatusDTO)throws DataAccessException;
}
