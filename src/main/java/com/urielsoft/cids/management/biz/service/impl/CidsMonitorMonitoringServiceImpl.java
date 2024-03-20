package com.urielsoft.cids.management.biz.service.impl;

import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.dto.CreateCidsMonitorMonitoringDTO;
import com.urielsoft.cids.management.biz.dto.ViewCidsMonitorMonitoringDTO;
import com.urielsoft.cids.management.biz.dto.api.param.CidsMonitorConfigurationFindLocAndTyParamDTO;
import com.urielsoft.cids.management.biz.mapper.CidsMonitorMonitoringMapper;
import com.urielsoft.cids.management.biz.service.CidsLogService;
import com.urielsoft.cids.management.biz.service.CidsMonitorMonitoringService;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.common.exception.BizException;
import com.urielsoft.cids.management.common.exception.ExceptionCode;
import com.urielsoft.cids.management.common.mybatis.MyBatisResultHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Cids Monitor Monitoring Services Impl
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CidsMonitorMonitoringServiceImpl implements CidsMonitorMonitoringService {
    /**
     * Cids Monitor Monitoring Mapper
     */
    private final CidsMonitorMonitoringMapper cidsMonitorMonitoringMapper;

    /**
     * Usage History Service
     */
    private final UsageHistoryService usageHistoryService;

    /**
     * Cids Log Service
     */
    private final CidsLogService cidsLogService;

    /**
     * Create new Monitor monitoring
     *
     * @param createCidsMonitorMonitoringDTO
     * @throws DataAccessException
     */
    @Override
    public void createCidsMonitorMonitoringData(CreateCidsMonitorMonitoringDTO createCidsMonitorMonitoringDTO) throws DataAccessException {
        String logId = this.cidsLogService.createNewLogId();

        try {
            int insertedRow = this.cidsMonitorMonitoringMapper.insertCidsMonitorMonitoringData(createCidsMonitorMonitoringDTO);
            MyBatisResultHelper.checkAffectedRow(insertedRow, 1);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.POST, "MonitorMonitoring");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.POST, "MonitorMonitoring");

            throw e;
        }
    }

    /**
     * List All Cids Monitor Monitoring Data
     *
     * @param wkTySeqNo
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<ViewCidsMonitorMonitoringDTO> listAllCidsMonitorMonitoringData(Integer wkTySeqNo) throws DataAccessException {
        List<ViewCidsMonitorMonitoringDTO> viewCidsMonitorMonitoringDTOS = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewCidsMonitorMonitoringDTOS = this.cidsMonitorMonitoringMapper.selectCidsMonitorMonitoringDataAllList(wkTySeqNo);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETALl, "MonitorMonitoring");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "MonitorMonitoring");
            throw e;
        }
        return viewCidsMonitorMonitoringDTOS;
    }

    /**
     * Get Cids Monitor Monitoring Data By Seq
     *
     * @param cidsMonitorMonitoringSeq
     * @return
     * @throws DataAccessException
     */
    @Override
    public ViewCidsMonitorMonitoringDTO getCidsMonitorMonitoringDataBySeq(int cidsMonitorMonitoringSeq) throws DataAccessException {
        ViewCidsMonitorMonitoringDTO viewCidsMonitorMonitoringDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewCidsMonitorMonitoringDTO = this.cidsMonitorMonitoringMapper.selectCidsMonitorMonitoringDataBySeq(cidsMonitorMonitoringSeq);
            if (viewCidsMonitorMonitoringDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "MonitorMonitoring");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "MonitorMonitoring");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "MonitorMonitoring");

            throw e;
        }

        return viewCidsMonitorMonitoringDTO;
    }

    /**
     * Find Cids Monitor Monitoring Manager Data
     *
     * @param cidsMonitorConfigurationFindLocAndTyParamDTO
     * @return
     * @throws DataAccessException
     */
    @Override
    public ViewCidsMonitorMonitoringDTO findCidsMonitorMonitoringManagerData(CidsMonitorConfigurationFindLocAndTyParamDTO cidsMonitorConfigurationFindLocAndTyParamDTO) throws DataAccessException {
        ViewCidsMonitorMonitoringDTO viewCidsMonitorMonitoringDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewCidsMonitorMonitoringDTO = this.cidsMonitorMonitoringMapper.findCidsMonitorManagerByLocAndTy(cidsMonitorConfigurationFindLocAndTyParamDTO);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "FindCidsMonitorManagerByLocAndTy");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "FindCidsMonitorManagerByLocAndTy");

            throw e;
        }
        return viewCidsMonitorMonitoringDTO;
    }

}

