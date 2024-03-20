package com.urielsoft.cids.management.biz.service;

import com.urielsoft.cids.management.biz.dto.CreateCidsMonitorManagerDTO;
import com.urielsoft.cids.management.biz.dto.UpdateCidsMonitorManagerDTO;
import com.urielsoft.cids.management.biz.dto.ViewCidsMonitorManagerDTO;
import com.urielsoft.cids.management.biz.dto.api.param.ReadCidsMonitorManagerMonCmsNmDuplicateParamDTO;
import com.urielsoft.cids.management.biz.dto.api.param.ReadCidsMonitorManagerMonitorNameDuplicateParamDTO;
import com.urielsoft.cids.management.common.api.ApiResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Cids Monitor Manager Service
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-20
 */
public interface CidsMonitorManagerService {
    /**
     * Create new monitor
     *
     * @param createCidsMonitorManagerDTO
     * @throws Exception
     */
    public void createCidsMonitorManagerData(CreateCidsMonitorManagerDTO createCidsMonitorManagerDTO) throws Exception;

    /**
     * View monitor Data
     *
     * @return
     * @throws Exception
     */
    public List<ViewCidsMonitorManagerDTO> listAllCidsMonitorManagerData() throws Exception;

    /**
     * Get Monitor By Seq
     *
     * @param cidsMonitorManagerSeq
     * @return
     * @throws Exception
     */
    public ViewCidsMonitorManagerDTO getCidsMonitorManagerDataBySeq(int cidsMonitorManagerSeq) throws Exception;

    /**
     * Update Monitor
     *
     * @param updateCidsMonitorManagerDTO
     * @throws Exception
     */
    public void modifyCidsMonitorManagerData(UpdateCidsMonitorManagerDTO updateCidsMonitorManagerDTO) throws Exception;

    /**
     * Delete CidsMonitorManager by seq
     *
     * @param cidsMonitorManagerSeq
     * @throws Exception
     */
    public void removeCidsMonitorManagerDataBySeq(int cidsMonitorManagerSeq) throws Exception;

    /**
     * Check Duplicate For Monitor Name
     *
     * @param readCidsMonitorManagerMonitorNameDuplicateParamDTO
     * @return
     * @throws DataAccessException
     */
    public ResponseEntity<ApiResult> CidsMonitorManagerCheckDuplicateForMonitorName(@RequestBody ReadCidsMonitorManagerMonitorNameDuplicateParamDTO readCidsMonitorManagerMonitorNameDuplicateParamDTO) throws DataAccessException;

    /**
     * Check Duplicate For Ip
     *
     * @param cidsMonitorManagerIp
     * @return
     * @throws DataAccessException
     */
    public ResponseEntity<ApiResult> CidsMonitorManagerCheckDuplicateForIp(@Param("cidsMonitorManagerIp") String cidsMonitorManagerIp) throws DataAccessException;

    /**
     * Check Duplicate For Location Name
     *
     * @param cidsMonitorManagerLocNm
     * @return
     * @throws DataAccessException
     */
    public ResponseEntity<ApiResult> CidsMonitorManagerCheckDuplicateForLocNm(@Param("cidsMonitorManagerLocNm") String cidsMonitorManagerLocNm) throws DataAccessException;

    /**
     * Check Duplicate For Monitor CMS Name
     *
     * @param readCidsMonitorManagerMonCmsNmDuplicateParamDTO
     * @return
     * @throws DataAccessException
     */
    public ResponseEntity<ApiResult> CidsMonitorManagerCheckDuplicateForMonCmsNm(@RequestBody ReadCidsMonitorManagerMonCmsNmDuplicateParamDTO readCidsMonitorManagerMonCmsNmDuplicateParamDTO) throws DataAccessException;
}
