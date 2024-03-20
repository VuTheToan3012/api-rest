package com.urielsoft.cids.management.biz.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.common.enums.Protocol;
import com.urielsoft.cids.management.biz.dto.CreateCidsMonitorOperationTmplatRequestSocketDTO;
import com.urielsoft.cids.management.biz.dto.SearchCidsMonitorConfigurationFindLocAndTyDTO;
import com.urielsoft.cids.management.biz.dto.UpdateCidsMonitorConfigurationDTO;
import com.urielsoft.cids.management.biz.dto.ViewCidsMonitorConfigurationDTO;
import com.urielsoft.cids.management.biz.dto.socket.CidsMonitorOperationTmplatRequestDataDTO;
import com.urielsoft.cids.management.biz.mapper.CidsMonitorConfigurationMapper;
import com.urielsoft.cids.management.biz.service.CidsLogService;
import com.urielsoft.cids.management.biz.service.CidsMonitorConfigurationService;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.biz.service.WebSocketService;
import com.urielsoft.cids.management.common.exception.BizException;
import com.urielsoft.cids.management.common.exception.ExceptionCode;
import com.urielsoft.cids.management.common.mybatis.MyBatisResultHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Cids Monitor Configuration Service Impl
 *
 * @author thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-08-05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CidsMonitorConfigurationServiceImpl implements CidsMonitorConfigurationService {
    /**
     * Cids Monitor Configuration Mapper
     */
    private final CidsMonitorConfigurationMapper cidsMonitorConfigurationMapper;

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
     * List All Cids Monitor Configuration Data
     *
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<ViewCidsMonitorConfigurationDTO> listAllCidsMonitorConfigurationData() throws DataAccessException {
        List<ViewCidsMonitorConfigurationDTO> viewCidsMonitorConfigurationDTOS = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewCidsMonitorConfigurationDTOS = this.cidsMonitorConfigurationMapper.selectCidsMonitorConfigurationDataAllList();

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETALl, "MonitorConfiguration");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "MonitorConfiguration");

            throw e;
        }
        return viewCidsMonitorConfigurationDTOS;
    }

    /**
     * Get Cids Monitor Configuration Data By Seq
     *
     * @param cidsMonitorManagerSeq
     * @return
     * @throws DataAccessException
     */
    @Override
    public ViewCidsMonitorConfigurationDTO getCidsMonitorConfigurationDataBySeq(int cidsMonitorManagerSeq) throws DataAccessException {
        ViewCidsMonitorConfigurationDTO viewCidsMonitorConfigurationDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewCidsMonitorConfigurationDTO = this.cidsMonitorConfigurationMapper.selectCidsMonitorConfigurationDataBySeq(cidsMonitorManagerSeq);
            if (viewCidsMonitorConfigurationDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "MonitorConfiguration");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "MonitorConfiguration");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "MonitorConfiguration");

            throw e;
        }

        return viewCidsMonitorConfigurationDTO;
    }

    /**
     * Modify Cids Monitor Configuration Data
     *
     * @param updateCidsMonitorConfigurationDto
     * @throws Exception
     */
    @Override
    public void modifyCidsMonitorConfigurationData(UpdateCidsMonitorConfigurationDTO updateCidsMonitorConfigurationDto) throws Exception {
        ViewCidsMonitorConfigurationDTO viewCidsMonitorConfigurationDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewCidsMonitorConfigurationDTO = this.cidsMonitorConfigurationMapper.selectCidsMonitorConfigurationDataBySeq(updateCidsMonitorConfigurationDto.getSeqNo());

            if (viewCidsMonitorConfigurationDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "MonitorConfiguration");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            int updatedRow = this.cidsMonitorConfigurationMapper.updateCidsMonitorConfigurationData(updateCidsMonitorConfigurationDto);

            // TODO: send websocket when change tmpl
            var createCidsMonitorOperationTmplatRequestSocketDTO = new CreateCidsMonitorOperationTmplatRequestSocketDTO();

            ObjectMapper objectMapper = new ObjectMapper();

            String respone = "";

            String request = null;

            try {
                // Create a new instance of CidsMonitorOperationTmplatRequestData
                CidsMonitorOperationTmplatRequestDataDTO requestData = new CidsMonitorOperationTmplatRequestDataDTO();

                if (updateCidsMonitorConfigurationDto.getTmplatSeqNo() != (viewCidsMonitorConfigurationDTO.getTmplatSeqNo())) {
                    requestData.setSpecifyId(cidsMonitorConfigurationMapper.selectCidsMonitorConfigurationDataBySeq(updateCidsMonitorConfigurationDto.getSeqNo()).getLocTySeqNo());
                    requestData.setSpecifyType("LOCATION");
                    // Set the requestData in createCidsMonitorOperationTmplatRequestSocketDto
                    createCidsMonitorOperationTmplatRequestSocketDTO.setRequestData(requestData);
                    createCidsMonitorOperationTmplatRequestSocketDTO.setProtocol(Protocol.CIDS_MONITOR_OPERATION_UICFG);
                    createCidsMonitorOperationTmplatRequestSocketDTO.setTransactionId(logId);

                    String jsonString = objectMapper.writeValueAsString(createCidsMonitorOperationTmplatRequestSocketDTO);

                    request = jsonString;

                    respone = webSocketService.sendMessage(jsonString);

                    this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.PUT, "Websocket CIDS_MONITOR_OPERATION_UICFG");

                    this.usageHistoryService.createProtocolLog(logId, Protocol.CIDS_MONITOR_OPERATION_UICFG.toString(), CidsLogLevelEnum.OK.toString(), request.toString(), respone.toString(), updateCidsMonitorConfigurationDto.getMdfyUserSeqNo());
                }
            } catch (Exception e) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "Websocket CIDS_MONITOR_OPERATION_UICFG");

                this.usageHistoryService.createProtocolLog(logId, Protocol.CIDS_MONITOR_OPERATION_UICFG.toString(), CidsLogLevelEnum.ERROR.toString(), request.toString(), respone.toString(), updateCidsMonitorConfigurationDto.getMdfyUserSeqNo());

                throw e;
            }

            MyBatisResultHelper.checkAffectedRow(updatedRow, 1);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.PUT, "MonitorConfiguration");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "MonitorConfiguration");

            throw e;
        }
    }

    /**
     * Find Template By Type And Location Data
     *
     * @param cidsMonitorConfigurationFindLocAndTyDTO
     * @return
     * @throws DataAccessException
     */
    @Override
    public ViewCidsMonitorConfigurationDTO findTemplateByTypeAndLocationData(SearchCidsMonitorConfigurationFindLocAndTyDTO cidsMonitorConfigurationFindLocAndTyDTO) throws DataAccessException {
        ViewCidsMonitorConfigurationDTO viewCidsMonitorConfigurationDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewCidsMonitorConfigurationDTO = this.cidsMonitorConfigurationMapper.findTemplateByTypeAndLocationData(cidsMonitorConfigurationFindLocAndTyDTO);
            if (viewCidsMonitorConfigurationDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "FindTemplateMonitorConfigurationByLocationAndType");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "FindTemplateMonitorConfigurationByLocationAndType");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "FindTemplateMonitorConfigurationByLocationAndType");
            throw e;
        }
        return viewCidsMonitorConfigurationDTO;
    }
}
