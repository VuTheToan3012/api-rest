package com.urielsoft.cids.management.biz.service;

import com.urielsoft.cids.management.biz.dto.*;
import com.urielsoft.cids.management.biz.dto.api.param.ReadCidsMonitorManagerMonCmsNmDuplicateParamDTO;
import com.urielsoft.cids.management.biz.dto.api.param.ReadCidsMonitorManagerMonitorNameDuplicateParamDTO;
import com.urielsoft.cids.management.biz.dto.api.param.UpdateEnvironmentManagementParamDTO;
import com.urielsoft.cids.management.common.api.ApiResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Environment Management Service
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-12-22
 */
public interface EnvironmentManagementService {

    /**
     * List All Environment Management Data
     *
     * @return
     * @throws Exception
     */
    public List<ViewEnvironmentManagementDTO> listAllEnvironmentManagementData() throws Exception;

    /**
     * Get Environment Management Data By SeqNo
     *
     * @param seqNo
     * @return
     * @throws Exception
     */
    public ViewEnvironmentManagementDTO getEnvironmentManagementDataBySeqNo(int seqNo) throws Exception;

    /**
     * Update Environment Management DTO
     *
     * @param updateEnvironmentManagementDTO
     * @throws Exception
     */
    public void modifyEnvironmentManagementData(@RequestBody @Validated UpdateEnvironmentManagementDTO updateEnvironmentManagementDTO) throws Exception;

}
