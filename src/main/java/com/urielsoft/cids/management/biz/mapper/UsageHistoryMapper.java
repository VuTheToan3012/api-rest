package com.urielsoft.cids.management.biz.mapper;

import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;
import com.urielsoft.cids.management.biz.dto.ViewUsageHistoryDTO;
import com.urielsoft.cids.management.biz.dto.SearchUsageHistoryPaginationStatusDTO;
import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Usage History Mapper
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-26
 */
@Repository
@Db1Mapper
public interface UsageHistoryMapper {
    /**
     * Select All Data of Usage History from Database
     *
     * @return
     * @throws DataAccessException
     */
    public List<ViewUsageHistoryDTO> selectUsageHistoryDataAllList() throws DataAccessException;

    /**
     * Select Usage History By usageHistorySeq
     *
     * @param usageHistorySeq
     * @return
     * @throws DataAccessException
     */
    public ViewUsageHistoryDTO selectUsageHistoryDataBySeq(@Param("usageHistorySeq") int usageHistorySeq) throws DataAccessException;

    /**
     * Select Usage History Data List Pagination And Search
     *
     * @param start
     * @param length
     * @param searchUsageHistoryPaginationStatusDTO
     * @return
     */
    List<ViewUsageHistoryDTO> selectUsageHistoryDataListPaginationAndSearch(int start, int length, SearchUsageHistoryPaginationStatusDTO searchUsageHistoryPaginationStatusDTO);
}
