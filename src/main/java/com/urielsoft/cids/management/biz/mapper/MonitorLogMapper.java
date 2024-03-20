package com.urielsoft.cids.management.biz.mapper;

import com.urielsoft.cids.management.biz.dto.SearchMonitorLogPaginationStatusDTO;
import com.urielsoft.cids.management.biz.dto.ViewMonitorLogDTO;
import com.urielsoft.cids.management.biz.dto.api.param.ReadMonitorLogByDateParamDTO;
import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Monitor Log Mapper
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-27
 */
@Repository
@Db1Mapper
public interface MonitorLogMapper {
    /**
     * Select Monitor Log Data All List
     *
     * @return
     * @throws DataAccessException
     */
    public List<ViewMonitorLogDTO> selectMonitorLogDataAllList() throws DataAccessException;

    /**
     * Select Monitor Log Data By Seq
     *
     * @param monitorLogSeq
     * @return
     * @throws DataAccessException
     */
    public ViewMonitorLogDTO selectMonitorLogDataBySeq(@Param("monitorLogSeq") int monitorLogSeq) throws DataAccessException;

    /**
     * Select System Log Data By Date
     *
     * @param readMonitorLogByDateParamDTO
     * @return
     * @throws DataAccessException
     */
    public List<ViewMonitorLogDTO> selectMonitorLogDataByDate(ReadMonitorLogByDateParamDTO readMonitorLogByDateParamDTO) throws DataAccessException;

    /**
     * Select Monitor Log Data List Pagination And Search
     *
     * @param start
     * @param length
     * @param searchMonitorLogPaginationStatusDTO
     * @return
     */
    List<ViewMonitorLogDTO> selectMonitorLogDataListPaginationAndSearch(int start, int length, SearchMonitorLogPaginationStatusDTO searchMonitorLogPaginationStatusDTO);
}