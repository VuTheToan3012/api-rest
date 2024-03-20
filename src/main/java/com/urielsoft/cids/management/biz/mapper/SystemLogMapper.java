package com.urielsoft.cids.management.biz.mapper;

import com.urielsoft.cids.management.biz.dto.SearchSystemLogPaginationStatusDTO;
import com.urielsoft.cids.management.biz.dto.ViewSystemLogDTO;
import com.urielsoft.cids.management.biz.dto.api.param.ReadSystemLogByDateParamDTO;
import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * System Log Mapper
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-27
 */
@Repository
@Db1Mapper
public interface SystemLogMapper {
    /**
     * Select System Log Data All List
     *
     * @return
     * @throws DataAccessException
     */
    public List<ViewSystemLogDTO> selectSystemLogDataAllList() throws DataAccessException;

    /**
     * Select System Log Data By Seq
     *
     * @param systemLogSeq
     * @return
     * @throws DataAccessException
     */
    public ViewSystemLogDTO selectSystemLogDataBySeq(@Param("systemLogSeq") int systemLogSeq) throws DataAccessException;

    /**
     * Select System Log Data By Date
     *
     * @param readSystemLogByDateParamDTO
     * @return
     * @throws DataAccessException
     */
    public List<ViewSystemLogDTO> selectSystemLogDataByDate(ReadSystemLogByDateParamDTO readSystemLogByDateParamDTO) throws DataAccessException;

    /**
     * Select System Log Data List Pagination And Search
     *
     * @param start
     * @param length
     * @param searchSystemLogPaginationStatusDTO
     * @return
     */
    List<ViewSystemLogDTO> selectSystemLogDataListPaginationAndSearch(int start, int length, SearchSystemLogPaginationStatusDTO searchSystemLogPaginationStatusDTO);
}