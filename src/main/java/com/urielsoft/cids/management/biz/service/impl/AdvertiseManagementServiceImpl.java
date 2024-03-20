package com.urielsoft.cids.management.biz.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.common.enums.Protocol;
import com.urielsoft.cids.management.biz.dto.CreateAdvertiseManagementDTO;
import com.urielsoft.cids.management.biz.dto.CreateCidsMonitorOperationAdvRequestSocketDTO;
import com.urielsoft.cids.management.biz.dto.UpdateAdvertiseManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewAdvertiseManagementDTO;
import com.urielsoft.cids.management.biz.mapper.AdvertiseManagementMapper;
import com.urielsoft.cids.management.biz.service.AdvertiseManagementService;
import com.urielsoft.cids.management.biz.service.CidsLogService;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.biz.service.WebSocketService;
import com.urielsoft.cids.management.common.api.ApiResult;
import com.urielsoft.cids.management.common.exception.BizException;
import com.urielsoft.cids.management.common.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Advertise Management Service Impl
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdvertiseManagementServiceImpl implements AdvertiseManagementService {
    /**
     * Advertise Management Mapper
     */
    private final AdvertiseManagementMapper advertiseManagementMapper;


    /**
     * Web Socket Service
     */
    private final WebSocketService webSocketService;

    /**
     * Usage History Service
     */
    private final UsageHistoryService usageHistoryService;

    /**
     * Cids Log Service
     */
    private final CidsLogService cidsLogService;

    /**
     * List All Advertise Management Data
     *
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<ViewAdvertiseManagementDTO> listAllAdvertiseManagementData() throws DataAccessException {
        List<ViewAdvertiseManagementDTO> viewAdvertiseManagementDTOS = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewAdvertiseManagementDTOS = this.advertiseManagementMapper.selectAdvertiseManagementDataAllList();

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETALl, "AdvertiseManagement");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "AdvertiseManagement");

            throw e;
        }
        return viewAdvertiseManagementDTOS;
    }

    /**
     * Get Advertise Management Data By Seq
     *
     * @param advertiseManagementSeq
     * @return
     * @throws DataAccessException
     */
    @Override
    public ViewAdvertiseManagementDTO getAdvertiseManagementDataBySeq(int advertiseManagementSeq) throws DataAccessException {
        ViewAdvertiseManagementDTO viewAdvertiseManagementDTO = null;
        String logId = this.cidsLogService.createNewLogId();
        try {
            viewAdvertiseManagementDTO = this.advertiseManagementMapper.selectAdvertiseManagementDataBySeq(advertiseManagementSeq);
            if (viewAdvertiseManagementDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "AdvertiseManagement");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "AdvertiseManagement");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "AdvertiseManagement");

            throw e;
        }

        return viewAdvertiseManagementDTO;
    }

    /**
     * Environment
     */
    @Autowired
    private Environment env;

    /**
     * Get Upload Folder
     *
     * @return
     * @throws Exception
     */
    @Override
    public File getUploadFolder() throws Exception {

        String uploadDirectory = env.getProperty("upload.directory");

        log.info("Upload Directory: " + uploadDirectory);

        String uploadFolderPath;

        if (uploadDirectory.startsWith("/")) {
            uploadFolderPath = System.getProperty("user.dir") + uploadDirectory;
        } else {
            uploadFolderPath = uploadDirectory;
            log.info("Upload Directory: " + uploadDirectory);
        }

        File uploadFolder = new File(uploadFolderPath);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        return uploadFolder;
    }

    /**
     * Delete Old File from .upload/temp Directory
     *
     * @param seqNo
     * @throws Exception
     */
    @Override
    public void deleteOldFileInTemp(int seqNo) throws Exception {
        ViewAdvertiseManagementDTO viewAdvertiseManagementDTO = advertiseManagementMapper.selectAdvertiseManagementDataBySeq(seqNo);
        String oldFileName = viewAdvertiseManagementDTO.getFileNm();

        File tempFolder = new File(getUploadFolder(), "temp");
        String oldTempFileFullPath = tempFolder.getPath() + File.separator + oldFileName;
        File oldTempFile = new File(oldTempFileFullPath);

        if (oldTempFile.exists()) {
            oldTempFile.delete();
        }
    }

    /**
     * Handle File Upload
     *
     * @param file
     * @param updateAdvertiseManagementDTO
     * @throws Exception
     */
    @Override
    public ResponseEntity<ApiResult> handleFileUpload(MultipartFile file, UpdateAdvertiseManagementDTO updateAdvertiseManagementDTO) throws Exception {

        String fileName = null;
        String logId = this.cidsLogService.createNewLogId();

        if (file != null) {
            fileName = file.getOriginalFilename();
        }

        List<ViewAdvertiseManagementDTO> viewAdvertiseManagementDTO = advertiseManagementMapper.selectAdvertiseManagementDataAllList();
        try {
            if (viewAdvertiseManagementDTO.isEmpty()) {

                CreateAdvertiseManagementDTO createAdvertiseManagementDTO = new CreateAdvertiseManagementDTO();

                createAdvertiseManagementDTO.setStTm(updateAdvertiseManagementDTO.getStTm());
                createAdvertiseManagementDTO.setEndTm(updateAdvertiseManagementDTO.getEndTm());
                createAdvertiseManagementDTO.setUsageYn(updateAdvertiseManagementDTO.getUsageYn());

                createAdvertiseManagementDTO.setFileNm(fileName);
                createAdvertiseManagementDTO.setPath(getUploadFolder().getPath());

                File uploadedFile = new File(getUploadFolder(), fileName);
                file.transferTo(uploadedFile);

                advertiseManagementMapper.insertAdvertiseManagementData(createAdvertiseManagementDTO);
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.POST, "AdvertiseManagement");

                // TODO: Send message websocket
                var cidsMonitorOperationAdvRequestSocketDTO = new CreateCidsMonitorOperationAdvRequestSocketDTO();

                ObjectMapper objectMapper = new ObjectMapper();

                String respone = "";
                String request = null;

                try {
                    cidsMonitorOperationAdvRequestSocketDTO.setProtocol(Protocol.CIDS_MONITOR_OPERATION_ADV);
                    cidsMonitorOperationAdvRequestSocketDTO.setTransactionId(logId);
                    cidsMonitorOperationAdvRequestSocketDTO.setRequestData(null);

                    String jsonString = objectMapper.writeValueAsString(cidsMonitorOperationAdvRequestSocketDTO);

                    request = jsonString;

                    respone = this.webSocketService.sendMessage(jsonString);

                    this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.POST, "Websocket CIDS_MONITOR_OPERATION_ADV");

                    this.usageHistoryService.createProtocolLog(logId, Protocol.CIDS_MONITOR_OPERATION_ADV.toString(), CidsLogLevelEnum.OK.toString(), request, respone.toString(), updateAdvertiseManagementDTO.getMdfyUserSeqNo());

                } catch (Exception e) {
                    this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.POST, "Websocket CIDS_MONITOR_OPERATION_ADV");

                    this.usageHistoryService.createProtocolLog(logId, Protocol.CIDS_MONITOR_OPERATION_ADV.toString(), CidsLogLevelEnum.ERROR.toString(), request, respone.toString(), updateAdvertiseManagementDTO.getMdfyUserSeqNo());

                    throw e;
                }

                var apiResult = new ApiResult(true);

                return ResponseEntity.ok(apiResult);
            }
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.POST, "AdvertiseManagement");

            throw e;
        }
        try {
            if (viewAdvertiseManagementDTO.size() == 1) {

                Integer advertiseManagementSeqNo = viewAdvertiseManagementDTO.get(0).getSeqNo();

                updateAdvertiseManagementDTO.setSeqNo(advertiseManagementSeqNo);
                updateAdvertiseManagementDTO.setFileNm(viewAdvertiseManagementDTO.get(0).getFileNm());
                updateAdvertiseManagementDTO.setPath(viewAdvertiseManagementDTO.get(0).getPath());

                if (file != null && !fileName.isEmpty()) {

                    moveOldFileToTemp(updateAdvertiseManagementDTO.getSeqNo());

                    File uploadedFile = new File(getUploadFolder(), fileName);
                    file.transferTo(uploadedFile);

                    if (uploadedFile.exists()) {
                        // Delete old file if it exists
                        deleteOldFileInTemp(updateAdvertiseManagementDTO.getSeqNo());

                        updateAdvertiseManagementDTO.setFileNm(fileName);
                        updateAdvertiseManagementDTO.setPath(uploadedFile.getPath());
                    } else {
                        throw new IOException("Upload Error");
                    }
                }
                this.advertiseManagementMapper.updateAdvertiseManagementData(updateAdvertiseManagementDTO);

                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.PUT, "AdvertiseManagement");

                // TODO: Send message websocket
                var cidsMonitorOperationAdvRequestSocketDTO = new CreateCidsMonitorOperationAdvRequestSocketDTO();

                ObjectMapper objectMapper = new ObjectMapper();

                String respone = "";
                String request = null;

                try {
                    cidsMonitorOperationAdvRequestSocketDTO.setProtocol(Protocol.CIDS_MONITOR_OPERATION_ADV);
                    cidsMonitorOperationAdvRequestSocketDTO.setTransactionId(logId);
                    cidsMonitorOperationAdvRequestSocketDTO.setRequestData(null);

                    String jsonString = objectMapper.writeValueAsString(cidsMonitorOperationAdvRequestSocketDTO);

                    request = jsonString;

                    respone = this.webSocketService.sendMessage(jsonString);

                    this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.POST, "Websocket CIDS_MONITOR_OPERATION_ADV");

                    this.usageHistoryService.createProtocolLog(logId, Protocol.CIDS_MONITOR_OPERATION_ADV.toString(), CidsLogLevelEnum.OK.toString(), request.toString(), respone.toString(), updateAdvertiseManagementDTO.getMdfyUserSeqNo());

                } catch (Exception e) {
                    this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.POST, "Websocket CIDS_MONITOR_OPERATION_ADV");

                    this.usageHistoryService.createProtocolLog(logId, Protocol.CIDS_MONITOR_OPERATION_ADV.toString(), CidsLogLevelEnum.ERROR.toString(), request.toString(), respone.toString(), updateAdvertiseManagementDTO.getMdfyUserSeqNo());

                    throw e;
                }

                var apiResult = new ApiResult(true);
                apiResult.setData(updateAdvertiseManagementDTO);
                return ResponseEntity.ok(apiResult);
            } else {
                var apiResult = new ApiResult(false);
                return ResponseEntity.ok(apiResult);
            }
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "AdvertiseManagement");

            throw e;
        }
    }

    /**
     * Move Old File to .upload/temp Directory
     *
     * @param seqNo
     * @throws Exception
     */
    @Override
    public void moveOldFileToTemp(int seqNo) throws Exception {
        ViewAdvertiseManagementDTO viewAdvertiseManagementDTO = advertiseManagementMapper.selectAdvertiseManagementDataBySeq(seqNo);
        String oldFileName = viewAdvertiseManagementDTO.getFileNm();
        String oldFilePath = getUploadFolder().getPath();
        String oldFileFullPath = oldFilePath + File.separator + oldFileName;
        File oldFile = new File(oldFileFullPath);

        if (oldFile.exists()) {
            File tempFolder = new File(getUploadFolder(), "temp");
            if (!tempFolder.exists()) {
                tempFolder.mkdirs();
            }

            File newTempFile = new File(tempFolder, oldFileName);
            if (!oldFile.renameTo(newTempFile)) {
                log.error("Failed to move old file to .upload/temp directory.");
            }
        }
    }

}