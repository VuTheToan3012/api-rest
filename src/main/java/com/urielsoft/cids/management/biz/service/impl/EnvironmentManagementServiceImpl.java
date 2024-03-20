package com.urielsoft.cids.management.biz.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.common.enums.Protocol;
import com.urielsoft.cids.management.biz.dto.CreateEnvironmentManagementRequestSocketDTO;
import com.urielsoft.cids.management.biz.dto.UpdateEnvironmentManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewEnvironmentManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewUserManagementDTO;
import com.urielsoft.cids.management.biz.dto.socket.EnvironmentManagementRequestDataDTO;
import com.urielsoft.cids.management.biz.mapper.EnvironmentManagementMapper;
import com.urielsoft.cids.management.biz.service.*;
import com.urielsoft.cids.management.common.exception.BizException;
import com.urielsoft.cids.management.common.exception.ExceptionCode;
import com.urielsoft.cids.management.common.mybatis.MyBatisResultHelper;
import com.urielsoft.cids.management.common.security.LoginUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Environment Management Service Impl
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-12-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EnvironmentManagementServiceImpl implements EnvironmentManagementService {
    /**
     * Environment Management Mapper
     */
    private final EnvironmentManagementMapper environmentManagementMapper;

    /**
     * Cids Log Service
     */
    private final CidsLogService cidsLogService;

    /**
     * Usage History Service
     */
    private final UsageHistoryService usageHistoryService;

    /**
     * Websocket Service
     */
    private final WebSocketService webSocketService;

    /**
     * UserManagement Service
     */
    private final UserManagementService userManagementService;

    /**
     * List All Environment Management Data
     *
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<ViewEnvironmentManagementDTO> listAllEnvironmentManagementData() throws DataAccessException {
        List<ViewEnvironmentManagementDTO> data = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            data = this.environmentManagementMapper.selectEnvironmentManagementDataAllList();

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETALl, "EnvironmentManagement");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "EnvironmentManagement");
            throw e;
        }
        return data;
    }

    /**
     * Get Environment Management Data By SeqNo
     *
     * @param seqNo
     * @return
     * @throws DataAccessException
     */
    @Override
    public ViewEnvironmentManagementDTO getEnvironmentManagementDataBySeqNo(int seqNo) throws DataAccessException {
        ViewEnvironmentManagementDTO viewEnvironmentManagementDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewEnvironmentManagementDTO = this.environmentManagementMapper.selectEnvironmentManagementDataBySeq(seqNo);
            if (viewEnvironmentManagementDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "EnvironmentManagement");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "EnvironmentManagement");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "EnvironmentManagement");
            throw e;
        }
        return viewEnvironmentManagementDTO;

    }

    /**
     * Update Environment Management DTO
     *
     * @param updateEnvironmentManagementDTO
     * @throws DataAccessException
     */
    @Override
    public void modifyEnvironmentManagementData(@RequestBody @Validated UpdateEnvironmentManagementDTO updateEnvironmentManagementDTO) throws Exception {
        String logId = this.cidsLogService.createNewLogId();
        try {
            ViewEnvironmentManagementDTO viewEnvironmentManagementDTO = this.environmentManagementMapper.selectEnvironmentManagementDataBySeq(updateEnvironmentManagementDTO.getSeqNo());
            if (viewEnvironmentManagementDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.PUT, "EnvironmentManagement");
                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            // Get current user info
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();
            ViewUserManagementDTO viewUserManagementDTO = this.userManagementService.getUserManagementDataById(loginUserDetails.getUserId());

            updateEnvironmentManagementDTO.setMdfyUserSeqNo(viewUserManagementDTO.getSeqNo());

            // Update in to DB
            int updatedRow = this.environmentManagementMapper.updateEnvironmentManagementData(updateEnvironmentManagementDTO);
            MyBatisResultHelper.checkAffectedRow(updatedRow, 1);
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.PUT, "EnvironmentManagement");

            if (viewEnvironmentManagementDTO.getEnvCtgry().equals("MONITOR")){
                //TODO: SEND SOCKET TO MONITOR MANAGER
                var createEnvironmentManagementRequestSocketDTO = new CreateEnvironmentManagementRequestSocketDTO();

                ObjectMapper objectMapper = new ObjectMapper();

                String respone = "";

                String request = null;
                try {
                    EnvironmentManagementRequestDataDTO requestDataDTO = new EnvironmentManagementRequestDataDTO();
                    Integer locationId = viewEnvironmentManagementDTO.getLocTySeqNo();
                    requestDataDTO.setSpecifyType("LOCATION");
                    requestDataDTO.setSpecifyId(locationId);

                    createEnvironmentManagementRequestSocketDTO.setRequestData(requestDataDTO);
                    createEnvironmentManagementRequestSocketDTO.setTransactionId(logId);
                    createEnvironmentManagementRequestSocketDTO.setProtocol(Protocol.CIDS_MONITOR_OPERATION_UICFG);

                    String jsonString = objectMapper.writeValueAsString(createEnvironmentManagementRequestSocketDTO);

                    request = jsonString;

                    respone = webSocketService.sendMessage(jsonString);

                    this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.PUT, "Websocket CIDS_MONITOR_OPERATION_UICFG");

                    this.usageHistoryService.createProtocolLog(logId, Protocol.CIDS_MONITOR_OPERATION_UICFG.toString(), CidsLogLevelEnum.OK.toString(), request.toString(), respone.toString(), updateEnvironmentManagementDTO.getMdfyUserSeqNo());
                } catch (Exception e) {
                    this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "Websocket CIDS_MONITOR_OPERATION_UICFG");

                    this.usageHistoryService.createProtocolLog(logId, Protocol.CIDS_MONITOR_OPERATION_UICFG.toString(), CidsLogLevelEnum.ERROR.toString(), request.toString(), respone.toString(), updateEnvironmentManagementDTO.getMdfyUserSeqNo());

                    throw e;
                }
            }

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "EnvironmentManagement");
            throw e;
        }

    }

}