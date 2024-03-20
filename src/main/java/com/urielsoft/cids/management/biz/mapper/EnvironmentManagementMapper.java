package com.urielsoft.cids.management.biz.mapper;

import com.urielsoft.cids.management.biz.dto.*;
import com.urielsoft.cids.management.biz.dto.api.param.ReadCidsMonitorManagerMonCmsNmDuplicateParamDTO;
import com.urielsoft.cids.management.biz.dto.api.param.ReadCidsMonitorManagerMonitorNameDuplicateParamDTO;
import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Cids Monitor Manager Mapper
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-20
 */
@Repository
@Db1Mapper
public interface EnvironmentManagementMapper {

    /**
     * Select All Data of Monitor from Database
     *
     * @return
     * @throws DataAccessException
     */
    public List<ViewEnvironmentManagementDTO> selectEnvironmentManagementDataAllList() throws DataAccessException;

    /**
     * Select Environment Management Data By Seq
     *
     * @param seqNo
     * @return
     * @throws DataAccessException
     */
    public ViewEnvironmentManagementDTO selectEnvironmentManagementDataBySeq(@Param("seqNo") int seqNo) throws DataAccessException;

    /**
     * Update Environment Management DTO
     *
     * @param updateEnvironmentManagementDTO
     * @return
     * @throws DataAccessException
     */
    public int updateEnvironmentManagementData(UpdateEnvironmentManagementDTO updateEnvironmentManagementDTO) throws DataAccessException;

}
