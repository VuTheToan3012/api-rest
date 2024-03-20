package com.urielsoft.cids.management.biz.mapper;

import com.urielsoft.cids.management.biz.dto.ViewErrorHistoryDTO;
import com.urielsoft.cids.management.biz.dto.SearchErrorHistoryPaginationStatusDTO;
import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Error History Mapper
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-01
 */
@Repository
@Db1Mapper
public interface ErrorHistoryMapper {
    /**
     * Select All Data of Error History from Database
     *
     * @return
     * @throws DataAccessException
     */
    public List<ViewErrorHistoryDTO> selectErrorHistoryDataAllList() throws DataAccessException;

    /**
     * Select Error History By errorHistorySeq
     *
     * @param errorHistorySeq
     * @return
     * @throws DataAccessException
     */
    public ViewErrorHistoryDTO selectErrorHistoryDataBySeq(@Param("errorHistorySeq") int errorHistorySeq) throws DataAccessException;

    /**
     * Select Error History Data List Pagination And Search
     *
     * @param start
     * @param length
     * @param searchErrorHistoryPaginationStatusDTO
     * @return
     */
    List<ViewErrorHistoryDTO> selectErrorHistoryDataListPaginationAndSearch(int start, int length, SearchErrorHistoryPaginationStatusDTO searchErrorHistoryPaginationStatusDTO);
}