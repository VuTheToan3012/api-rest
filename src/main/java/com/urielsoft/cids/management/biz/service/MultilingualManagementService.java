package com.urielsoft.cids.management.biz.service;

import com.urielsoft.cids.management.biz.dto.CreateMultilingualManagementDTO;
import com.urielsoft.cids.management.biz.dto.UpdateMultilingualManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewMultilingualManagementDTO;
import com.urielsoft.cids.management.common.api.ApiResult;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Multilingual Management Service
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-08-01
 */

public interface MultilingualManagementService {

    /**
     * View MultilingualManagement Data
     *
     * @return
     * @throws Exception
     */
    public List<ViewMultilingualManagementDTO> listAllMultilingualManagementData() throws Exception;

    /**
     * Get MultilingualManagement Data By tagId
     *
     * @param tagId
     * @return
     * @throws Exception
     */
    public ResponseEntity<ApiResult> getMultilingualManagementByTagId(String tagId) throws DataAccessException, Exception;


    /**
     * Get Multilingual Management Data By multilingualManagementSeq
     *
     * @param multilingualManagementSeq
     * @return
     * @throws Exception
     */
    public ViewMultilingualManagementDTO getMultilingualManagementDataBySeq(int multilingualManagementSeq) throws Exception;

    /**
     * Create new Multilingual Management
     *
     * @param createMultilingualManagementDTO
     * @throws Exception
     */
    public void createMultilingualManagementData(CreateMultilingualManagementDTO createMultilingualManagementDTO) throws Exception;

    /**
     * Update  Multilingual Management
     *
     * @param updateMultilingualManagementDTO
     * @throws Exception
     */
    public void modifyMultilingualManagementData(UpdateMultilingualManagementDTO updateMultilingualManagementDTO) throws Exception;

}
