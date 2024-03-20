package com.urielsoft.cids.management.biz.service;

import com.urielsoft.cids.management.biz.dto.UpdateAdvertiseManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewAdvertiseManagementDTO;
import com.urielsoft.cids.management.common.api.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Advertise Management Service
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-23
 */
public interface AdvertiseManagementService {
    /**
     * View Advertise Mng
     *
     * @return
     * @throws Exception
     */
    public List<ViewAdvertiseManagementDTO> listAllAdvertiseManagementData() throws Exception;

    /**
     * Get Advertise Mng By seq
     *
     * @param advertiseManagementSeq
     * @return
     * @throws Exception
     */
    public ViewAdvertiseManagementDTO getAdvertiseManagementDataBySeq(int advertiseManagementSeq) throws Exception;

    /**
     * Get Upload Folder
     *
     * @return
     * @throws Exception
     */
    public File getUploadFolder() throws Exception;

    /**
     * Delete Old File
     *
     * @param seqNo
     * @throws Exception
     */
    public void deleteOldFileInTemp(int seqNo) throws Exception;

    /**
     * Handle File Upload
     *
     * @param file
     * @param updateAdvertiseManagementDTO
     * @throws Exception
     */
    public ResponseEntity<ApiResult> handleFileUpload(MultipartFile file, UpdateAdvertiseManagementDTO updateAdvertiseManagementDTO) throws Exception;

    /**
     * Move Old File To Temp
     *
     * @param seqNo
     * @throws Exception
     */
    public void moveOldFileToTemp(int seqNo) throws Exception;
}
