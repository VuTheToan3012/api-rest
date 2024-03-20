package com.urielsoft.cids.management.biz.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.common.enums.MonitorStatus;
import com.urielsoft.cids.management.biz.common.enums.Protocol;
import com.urielsoft.cids.management.biz.dto.*;
import com.urielsoft.cids.management.biz.dto.api.param.ReadCidsMonitorManagerMonCmsNmDuplicateParamDTO;
import com.urielsoft.cids.management.biz.dto.api.param.ReadCidsMonitorManagerMonitorNameDuplicateParamDTO;
import com.urielsoft.cids.management.biz.dto.socket.CidsMonitorOperationModifyMonitorRequestDataDTO;
import com.urielsoft.cids.management.biz.dto.socket.CidsMonitorOperationRestartRequestDataDTO;
import com.urielsoft.cids.management.biz.dto.socket.CidsSocketMessageDTO;
import com.urielsoft.cids.management.biz.mapper.CidsMonitorManagerMapper;
import com.urielsoft.cids.management.biz.mapper.CidsMonitorMonitoringMapper;
import com.urielsoft.cids.management.biz.service.CidsLogService;
import com.urielsoft.cids.management.biz.service.CidsMonitorManagerService;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.biz.service.WebSocketService;
import com.urielsoft.cids.management.common.api.ApiResult;
import com.urielsoft.cids.management.common.exception.BizException;
import com.urielsoft.cids.management.common.exception.ExceptionCode;
import com.urielsoft.cids.management.common.mybatis.MyBatisResultHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Cids Monitor Manager Services Impl
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CidsMonitorManagerServiceImpl implements CidsMonitorManagerService {
    /**
     * Cids Monitor Manager Mapper
     */
    private final CidsMonitorManagerMapper cidsMonitorManagerMapper;

    /**
     * Cids Log Service
     */
    private final CidsLogService cidsLogService;

    /**
     * Cids Monitor Monitoring Mapper
     */
    private final CidsMonitorMonitoringMapper cidsMonitorMonitoringMapper;

    /**
     * Usage History Service
     */
    private final UsageHistoryService usageHistoryService;

    /**
     * Websocket Service
     */
    private final WebSocketService webSocketService;


    /**
     * Create Cids Monitor Manager Data
     *
     * @param createCidsMonitorManagerDTO
     * @throws DataAccessException
     */
    @Override
    public void createCidsMonitorManagerData(CreateCidsMonitorManagerDTO createCidsMonitorManagerDTO) throws DataAccessException {
        String logId = this.cidsLogService.createNewLogId();

        try {
            int insertedRow = this.cidsMonitorManagerMapper.insertCidsMonitorManagerData(createCidsMonitorManagerDTO);
            MyBatisResultHelper.checkAffectedRow(insertedRow, 1);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.POST, "MonitorManager");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.POST, "MonitorManager");

            throw e;
        }
        try {
            // Create New Monitor Monitoring
            CreateCidsMonitorMonitoringDTO createCidsMonitorMonitoringDTO = new CreateCidsMonitorMonitoringDTO();
            createCidsMonitorMonitoringDTO.setMonSeqNo(createCidsMonitorManagerDTO.getSeqNo());
            createCidsMonitorMonitoringDTO.setSts(MonitorStatus.OFFLINE);
            createCidsMonitorMonitoringDTO.setStsCd(MonitorStatus.OFFLINE.getValue());

            int insertedRow2 = this.cidsMonitorMonitoringMapper.insertCidsMonitorMonitoringData(createCidsMonitorMonitoringDTO);
            MyBatisResultHelper.checkAffectedRow(insertedRow2, 1);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.POST, "Monitoring");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.POST, "Monitoring");
        }

    }

    /**
     * List All Cids Monitor Manager Data
     *
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<ViewCidsMonitorManagerDTO> listAllCidsMonitorManagerData() throws DataAccessException {
        List<ViewCidsMonitorManagerDTO> data = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            data = this.cidsMonitorManagerMapper.selectCidsMonitorManagerDataAllList();

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETALl, "MonitorManager");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "MonitorManager");
            throw e;
        }
        return data;
    }

    /**
     * Get Cids Monitor Manager Data By Seq
     *
     * @param cidsMonitorManagerSeq
     * @return
     * @throws DataAccessException
     */
    @Override
    public ViewCidsMonitorManagerDTO getCidsMonitorManagerDataBySeq(int cidsMonitorManagerSeq) throws DataAccessException {
        ViewCidsMonitorManagerDTO viewCidsMonitorManagerDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewCidsMonitorManagerDTO = this.cidsMonitorManagerMapper.selectCidsMonitorManagerDataBySeq(cidsMonitorManagerSeq);
            if (viewCidsMonitorManagerDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "MonitorManager");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "MonitorManager");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "MonitorManager");
            throw e;
        }
        return viewCidsMonitorManagerDTO;

    }

    /**
     * Modify Cids Monitor Manager Data
     *
     * @param updateCidsMonitorManagerDTO
     * @throws DataAccessException
     */
    @Override
    public void modifyCidsMonitorManagerData(UpdateCidsMonitorManagerDTO updateCidsMonitorManagerDTO) throws Exception {
        String logId = this.cidsLogService.createNewLogId();
        boolean isMonNmChange = false;
        try {
            ViewCidsMonitorManagerDTO viewCidsMonitorManagerDTO = this.cidsMonitorManagerMapper.selectCidsMonitorManagerDataBySeq(updateCidsMonitorManagerDTO.getSeqNo());
            if (viewCidsMonitorManagerDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.PUT, "MonitorManager");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            // Check what Data Change
                if (viewCidsMonitorManagerDTO.getMonNm().equals(updateCidsMonitorManagerDTO.getMonNm())
                        && viewCidsMonitorManagerDTO.getLocTySeqNo().equals(updateCidsMonitorManagerDTO.getLocTySeqNo())
                        && viewCidsMonitorManagerDTO.getMonCmsNm().equals(updateCidsMonitorManagerDTO.getMonCmsNm())
                        && viewCidsMonitorManagerDTO.getWkTySeqNo().equals(updateCidsMonitorManagerDTO.getWkTySeqNo())
                        && viewCidsMonitorManagerDTO.getIp().equals(updateCidsMonitorManagerDTO.getIp())
                        && viewCidsMonitorManagerDTO.getUsage().equals(updateCidsMonitorManagerDTO.getUsage())
                        && viewCidsMonitorManagerDTO.getNote().equals(updateCidsMonitorManagerDTO.getNote())
                ){
                    isMonNmChange = false;
                } else if (!viewCidsMonitorManagerDTO.getMonNm().equals(updateCidsMonitorManagerDTO.getMonNm())
                        && viewCidsMonitorManagerDTO.getLocTySeqNo().equals(updateCidsMonitorManagerDTO.getLocTySeqNo())
                        && viewCidsMonitorManagerDTO.getMonCmsNm().equals(updateCidsMonitorManagerDTO.getMonCmsNm())
                        && viewCidsMonitorManagerDTO.getWkTySeqNo().equals(updateCidsMonitorManagerDTO.getWkTySeqNo())
                        && viewCidsMonitorManagerDTO.getIp().equals(updateCidsMonitorManagerDTO.getIp())
                        && viewCidsMonitorManagerDTO.getUsage().equals(updateCidsMonitorManagerDTO.getUsage())
                        && viewCidsMonitorManagerDTO.getNote().equals(updateCidsMonitorManagerDTO.getNote())
                ) {
                    isMonNmChange = false;
                } else{
                    isMonNmChange = true;
                }

            // Update in to DB
            int updatedRow = this.cidsMonitorManagerMapper.updateCidsMonitorManagerData(updateCidsMonitorManagerDTO);
            MyBatisResultHelper.checkAffectedRow(updatedRow, 1);
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.PUT, "MonitorManager");


            CreateCidsMonitorOperationModifyMonitorRequestSocketDTO createCidsMonitorOperationModifyMonitorRequestSocketDTO = new CreateCidsMonitorOperationModifyMonitorRequestSocketDTO();
            CidsMonitorOperationModifyMonitorRequestDataDTO requestSocketData = new CidsMonitorOperationModifyMonitorRequestDataDTO();

            ObjectMapper objectMapper = new ObjectMapper();

            String respone = "";

            String request = null;

            if( isMonNmChange == false){
                try {
                    // TODO : Send Socket Change UI Config If Only Modify Monitor Name

                    requestSocketData.setSpecifyType("MONITOR");
                    requestSocketData.setSpecifyId(updateCidsMonitorManagerDTO.getSeqNo());

                    createCidsMonitorOperationModifyMonitorRequestSocketDTO.setProtocol(Protocol.CIDS_MONITOR_OPERATION_UICFG);
                    createCidsMonitorOperationModifyMonitorRequestSocketDTO.setRequestData(requestSocketData);
                    createCidsMonitorOperationModifyMonitorRequestSocketDTO.setTransactionId(logId);

                    String jsonString = objectMapper.writeValueAsString(createCidsMonitorOperationModifyMonitorRequestSocketDTO);

                    request = jsonString;

                    respone = webSocketService.sendMessage(jsonString);

                    this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.PUT, "Websocket CIDS_MONITOR_OPERATION_UICFG");

                    this.usageHistoryService.createProtocolLog(logId, Protocol.CIDS_MONITOR_OPERATION_UICFG.toString(), CidsLogLevelEnum.OK.toString(), request.toString(), respone.toString(), updateCidsMonitorManagerDTO.getMdfyUserSeqNo());


                }catch (Exception e){

                    this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "Websocket CIDS_MONITOR_OPERATION_UICFG");

                    this.usageHistoryService.createProtocolLog(logId, Protocol.CIDS_MONITOR_OPERATION_UICFG.toString(), CidsLogLevelEnum.ERROR.toString(), request.toString(), respone.toString(), updateCidsMonitorManagerDTO.getMdfyUserSeqNo());

                    throw e;
                }
            }else if (isMonNmChange == true){
                // TODO: Send Socket To Reset Monitor If Modify More Than Monitor Name

                CidsSocketMessageDTO messageDTO = new CidsSocketMessageDTO();

                CidsMonitorOperationRestartRequestDataDTO requestDataDTO = new CidsMonitorOperationRestartRequestDataDTO();

                try {
                        requestDataDTO.setMonitorId(viewCidsMonitorManagerDTO.getSeqNo());
                        messageDTO.setTransactionId(logId);
                        messageDTO.setRequestData(requestDataDTO);
                        messageDTO.setProtocol(Protocol.CIDS_MONITOR_OPERATION_RESTART);
                        String jsonString = objectMapper.writeValueAsString(messageDTO);

                        request = jsonString;

                        respone = this.webSocketService.sendMessage(jsonString);

                        this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.PUT, "Websocket CIDS_MONITOR_OPERATION_RESTART");

                        this.usageHistoryService.createProtocolLog(logId, Protocol.CIDS_MONITOR_OPERATION_RESTART.toString(), CidsLogLevelEnum.OK.toString(), request.toString(), respone.toString(), updateCidsMonitorManagerDTO.getMdfyUserSeqNo());

                } catch (Exception e) {
                    this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "Websocket CIDS_MONITOR_OPERATION_RESTART");

                    this.usageHistoryService.createProtocolLog(logId, Protocol.CIDS_MONITOR_OPERATION_RESTART.toString(), CidsLogLevelEnum.ERROR.toString(), request.toString(), respone, updateCidsMonitorManagerDTO.getMdfyUserSeqNo());

                    throw e;
                }
            }

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "MonitorManager");
            throw e;
        }

        log.info(String.valueOf(isMonNmChange));
    }

    /**
     * Remove Cids Monitor Manager Data By Seq
     *
     * @param cidsMonitorManagerSeq
     * @throws DataAccessException
     */
    @Override
    public void removeCidsMonitorManagerDataBySeq(int cidsMonitorManagerSeq) throws DataAccessException {
        String logId = this.cidsLogService.createNewLogId();

        try {
            ViewCidsMonitorManagerDTO viewCidsMonitorManagerDTO = this.cidsMonitorManagerMapper.selectCidsMonitorManagerDataBySeq(cidsMonitorManagerSeq);
            if (viewCidsMonitorManagerDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.DELETE, "MonitorManager");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            int deletedRow = this.cidsMonitorManagerMapper.deleteCidsMonitorManagerDataBySeq(cidsMonitorManagerSeq);
            MyBatisResultHelper.checkAffectedRow(deletedRow, 1);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.DELETE, "MonitorManager");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.DELETE, "MonitorManager");
            throw e;
        }

    }

    /**
     * Check Duplicate For Monitor Name
     *
     * @param readCidsMonitorManagerMonitorNameDuplicateParamDTO
     * @return
     * @throws DataAccessException
     */
    @Override
    public ResponseEntity<ApiResult> CidsMonitorManagerCheckDuplicateForMonitorName(ReadCidsMonitorManagerMonitorNameDuplicateParamDTO readCidsMonitorManagerMonitorNameDuplicateParamDTO) throws DataAccessException {
        ViewCidsMonitorManagerDTO viewCidsMonitorManagerDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewCidsMonitorManagerDTO = cidsMonitorManagerMapper.selectCidsMonitorManagerDataByMonitorName(readCidsMonitorManagerMonitorNameDuplicateParamDTO);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.CheckDuplication, "MonitorName");
        } catch (Exception e) {

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.CheckDuplication, "MonitorName");
            throw e;
        }

        if (viewCidsMonitorManagerDTO == null) {
            return ResponseEntity.ok(new ApiResult(false));
        }
        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * Check Duplicate For Ip
     *
     * @param cidsMonitorManagerIp
     * @return
     * @throws DataAccessException
     */
    @Override
    public ResponseEntity<ApiResult> CidsMonitorManagerCheckDuplicateForIp(String cidsMonitorManagerIp) throws DataAccessException {
        ViewCidsMonitorManagerDTO viewCidsMonitorManagerDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewCidsMonitorManagerDTO = cidsMonitorManagerMapper.selectCidsMonitorManagerDataByIp(cidsMonitorManagerIp);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.CheckDuplication, "MonitorManagerIp");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.CheckDuplication, "MonitorManagerIp");

            throw e;
        }

        if (viewCidsMonitorManagerDTO == null) {
            return ResponseEntity.ok(new ApiResult(false));
        }
        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * Check Duplicate For Location Name
     *
     * @param cidsMonitorManagerLocNm
     * @return
     * @throws DataAccessException
     */
    @Override
    public ResponseEntity<ApiResult> CidsMonitorManagerCheckDuplicateForLocNm(String cidsMonitorManagerLocNm) throws DataAccessException {
        ViewCidsMonitorManagerDTO viewCidsMonitorManagerDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewCidsMonitorManagerDTO = cidsMonitorManagerMapper.selectCidsMonitorManagerDataByLocNm(cidsMonitorManagerLocNm);
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.CheckDuplication, "cidsMonitorManagerLocNm");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.CheckDuplication, "cidsMonitorManagerLocNm");
            throw e;
        }

        if (viewCidsMonitorManagerDTO == null) {
            return ResponseEntity.ok(new ApiResult(false));
        }
        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * Check Duplicate For Monitor CMS Name
     *
     * @param readCidsMonitorManagerMonCmsNmDuplicateParamDTO
     * @return
     * @throws DataAccessException
     */
    @Override
    public ResponseEntity<ApiResult> CidsMonitorManagerCheckDuplicateForMonCmsNm(ReadCidsMonitorManagerMonCmsNmDuplicateParamDTO readCidsMonitorManagerMonCmsNmDuplicateParamDTO) throws DataAccessException {
        ViewCidsMonitorManagerDTO viewCidsMonitorManagerDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewCidsMonitorManagerDTO = cidsMonitorManagerMapper.selectCidsMonitorManagerDataByMonCmsNm(readCidsMonitorManagerMonCmsNmDuplicateParamDTO);
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.CheckDuplication, "cidsMonitorManagerMonCmsNm");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.CheckDuplication, "cidsMonitorManagerMonCmsNm");
            throw e;
        }

        if (viewCidsMonitorManagerDTO == null) {
            return ResponseEntity.ok(new ApiResult(false));
        }
        return ResponseEntity.ok(new ApiResult(true));
    }
}