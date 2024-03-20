package com.urielsoft.cids.management.biz.service.impl;


import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.dto.CreateAirlineManagerDTO;
import com.urielsoft.cids.management.biz.dto.UpdateAirlineManagerDTO;
import com.urielsoft.cids.management.biz.dto.ViewAirlineManagerDTO;
import com.urielsoft.cids.management.biz.mapper.AirlineManagerMapper;
import com.urielsoft.cids.management.biz.service.AirlineManagerService;
import com.urielsoft.cids.management.biz.service.CidsLogService;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.common.api.ApiResult;
import com.urielsoft.cids.management.common.exception.BizException;
import com.urielsoft.cids.management.common.exception.ExceptionCode;
import com.urielsoft.cids.management.common.mybatis.MyBatisResultHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Airline Manager Service Impl
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-07-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AirlineManagerServiceImpl implements AirlineManagerService {
    /**
     * Airline Manager Mapper
     */
    private final AirlineManagerMapper airlineMapper;

    /**
     * Usage History Service
     */
    private final UsageHistoryService usageHistoryService;

    /**
     * Cids Log Service
     */
    private final CidsLogService cidsLogService;

    /**
     * Get All Airline Method
     *
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<ViewAirlineManagerDTO> listAllAirlineData() throws DataAccessException {
        String logId = this.cidsLogService.createNewLogId();

        List<ViewAirlineManagerDTO> viewAirlineManagerDTOS = null;
        try {
            viewAirlineManagerDTOS = this.airlineMapper.selectAirlineDataAllList();

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETALl, "AirLineManager");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "AirLineManager");

            throw e;
        }
        return viewAirlineManagerDTOS;
    }

    /**
     * Get Airline Data By AlCode3
     *
     * @param alCode3
     * @return
     * @throws DataAccessException
     * @throws Exception
     */
    @Override
    public ResponseEntity<ApiResult> getAirlineDataByAlCode3(String alCode3) throws DataAccessException {
        ViewAirlineManagerDTO viewAirlineDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewAirlineDTO = airlineMapper.selectAirlineDataByAlCode3(alCode3);
            if (viewAirlineDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "GetAirlineDataByAlCode3");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "GetAirlineDataByAlCode3");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "GetAirlineDataByAlCode3");

            throw e;
        }

        return ResponseEntity.ok(new ApiResult(true));

    }

    /**
     * Get Airline Data By AlCode
     *
     * @param alCode
     * @return
     * @throws DataAccessException
     * @throws Exception
     */
    @Override
    public ResponseEntity<ApiResult> getAirlineDataByAlCode(String alCode) throws DataAccessException, Exception {
        ViewAirlineManagerDTO viewAirlineDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewAirlineDTO = airlineMapper.selectAirlineDataByAlCode(alCode);
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "GetAirlineDataByAlCode");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "GetAirlineDataByAlCode");

            throw e;
        }
        if (viewAirlineDTO == null) {

            return ResponseEntity.ok(new ApiResult(false));
        }

        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * Get Airline Data By AlCodea
     *
     * @param alCodea
     * @return
     * @throws DataAccessException
     * @throws Exception
     */
    @Override
    public ResponseEntity<ApiResult> getAirlineDataByAlCodea(String alCodea) throws DataAccessException {
        ViewAirlineManagerDTO viewAirlineDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewAirlineDTO = airlineMapper.selectAirlineDataByAlCodea(alCodea);
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "Get Airline Data By AlCodea");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "Get Airline Data By AlCodea");

            throw e;
        }

        if (viewAirlineDTO == null) {

            return ResponseEntity.ok(new ApiResult(false));
        }

        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * create Airline Method
     *
     * @param createAirlineManagerDTO
     * @throws DataAccessException
     */
    @Transactional
    @Override
    public void createAirlineData(CreateAirlineManagerDTO createAirlineManagerDTO) throws DataAccessException {
        String logId = this.cidsLogService.createNewLogId();

        try {
            int insertedRow = this.airlineMapper.insertAirlineData(createAirlineManagerDTO);
            MyBatisResultHelper.checkAffectedRow(insertedRow, 1);
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.POST, "AirLineManager");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.POST, "AirLineManager");
            throw e;
        }
    }

    /**
     * Get Airline Data By AirLine Seq
     *
     * @param airLineManagerSeq
     * @throws DataAccessException
     */
    @Override
    public ViewAirlineManagerDTO getAirlineDataByAirLineSeq(int airLineManagerSeq) throws DataAccessException {
        ViewAirlineManagerDTO viewAirlineDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewAirlineDTO = this.airlineMapper.selectAirlineDataByAirLineSeq(airLineManagerSeq);
            if (viewAirlineDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "Get Airline Data By AirLine Seq");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "Get Airline Data By AirLine Seq");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "Get Airline Data By AirLine Seq");

            throw e;
        }

        return viewAirlineDTO;
    }

    /**
     * Update Airline Method
     *
     * @param updateAirlineManagerDTO
     * @throws DataAccessException
     */
    @Override
    public void modifyAirlineData(UpdateAirlineManagerDTO updateAirlineManagerDTO) throws DataAccessException {
        ViewAirlineManagerDTO viewAirlineDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewAirlineDTO = this.airlineMapper.selectAirlineDataByAirLineSeq(updateAirlineManagerDTO.getSeqNo());
            if (viewAirlineDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "AirLineManager");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            int updatedRow = this.airlineMapper.updateAirlineData(updateAirlineManagerDTO);
            MyBatisResultHelper.checkAffectedRow(updatedRow, 1);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.PUT, "AirLineManager");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "AirLineManager");
            throw e;
        }
    }

    /**
     * DELETE airline Method
     *
     * @param airLineManagerSeq
     * @throws DataAccessException
     */
    @Transactional
    @Override
    public void removeAirlineDataBySeqNo(int airLineManagerSeq) throws DataAccessException {
        ViewAirlineManagerDTO viewAirlineDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewAirlineDTO = this.airlineMapper.selectAirlineDataByAirLineSeq(airLineManagerSeq);
            if (viewAirlineDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.DELETE, "AirLineManager");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            int deletedRow = this.airlineMapper.deleteAirlineDataBySeqNo(airLineManagerSeq);
            MyBatisResultHelper.checkAffectedRow(deletedRow, 1);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.DELETE, "AirLineManager");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.DELETE, "AirLineManager");

            throw e;
        }
    }
}
