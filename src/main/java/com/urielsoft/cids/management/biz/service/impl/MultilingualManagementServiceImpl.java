package com.urielsoft.cids.management.biz.service.impl;

import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.dto.CreateMultilingualManagementDTO;
import com.urielsoft.cids.management.biz.dto.UpdateMultilingualManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewMultilingualManagementDTO;
import com.urielsoft.cids.management.biz.mapper.MultilingualManagementMapper;
import com.urielsoft.cids.management.biz.service.CidsLogService;
import com.urielsoft.cids.management.biz.service.MultilingualManagementService;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.common.api.ApiResult;
import com.urielsoft.cids.management.common.exception.BizException;
import com.urielsoft.cids.management.common.exception.ExceptionCode;
import com.urielsoft.cids.management.common.message.LocaleMessageService;
import com.urielsoft.cids.management.common.mybatis.MyBatisResultHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Multilingual Management Service Impl
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-08-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MultilingualManagementServiceImpl implements MultilingualManagementService {
    /**
     * Multilingual Management Mapper
     */
    private final MultilingualManagementMapper multilingualManagementMapper;

    /**
     * Usage History Service
     */
    private final UsageHistoryService usageHistoryService;

    /**
     * Locale Message Service
     */
    private final LocaleMessageService localeMessageService;

    /**
     * Cids Log Service
     */
    private final CidsLogService cidsLogService;

    /**
     * Get All Multilingual Management Method
     *
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<ViewMultilingualManagementDTO> listAllMultilingualManagementData() throws DataAccessException {
        List<ViewMultilingualManagementDTO> viewMultilingualManagementDTOS = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewMultilingualManagementDTOS = this.multilingualManagementMapper.selectMultilingualManagementDataAllList();
            for (ViewMultilingualManagementDTO item: viewMultilingualManagementDTOS
                 ) {
                if (item.getWkTySeqNo() != null && item.getLocTySeqNo() != null){
                    item.setItemCategory("CIDS MONITOR - "+ item.getWkTyNm()+" " + item.getLocNm());
                }else {
                    item.setItemCategory("CIDS ADMIN");
                }
            }
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETALl, "MultilingualManagement");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "MultilingualManagement");

            throw e;
        }
        return viewMultilingualManagementDTOS;
    }

    /**
     * Get Multilingual Management By TagId
     *
     * @param tagId
     * @return
     * @throws DataAccessException
     * @throws Exception
     */
    @Override
    public ResponseEntity<ApiResult> getMultilingualManagementByTagId(String tagId) throws DataAccessException {
        ViewMultilingualManagementDTO viewMultilingualManagementDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewMultilingualManagementDTO = multilingualManagementMapper.selectMultilingualManagementByTagId(tagId);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "GetMultilingualManagementByTagId");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "GetMultilingualManagementByTagId");

            throw e;
        }

        if (viewMultilingualManagementDTO == null) {

            return ResponseEntity.ok(new ApiResult(false));
        }

        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * Get Multilingual Management By Seq
     *
     * @param multilingualManagementSeq
     * @return
     * @throws DataAccessException
     */
    @Override
    public ViewMultilingualManagementDTO getMultilingualManagementDataBySeq(int multilingualManagementSeq) throws DataAccessException {
        ViewMultilingualManagementDTO viewMultilingualManagementDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewMultilingualManagementDTO = this.multilingualManagementMapper.selectMultilingualManagementDataBySeq(multilingualManagementSeq);
            if (viewMultilingualManagementDTO == null) {

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "MultilingualManagement");
            if (viewMultilingualManagementDTO.getWkTySeqNo() != null && viewMultilingualManagementDTO.getLocTySeqNo() != null){
                viewMultilingualManagementDTO.setItemCategory("CIDS MONITOR - "+ viewMultilingualManagementDTO.getWkTyNm()+" " + viewMultilingualManagementDTO.getLocNm());
            }else {
                viewMultilingualManagementDTO.setItemCategory("CIDS ADMIN");
            }
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "MultilingualManagement");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "MultilingualManagement");

            throw e;
        }
        return viewMultilingualManagementDTO;
    }

    /**
     * create Multilingual Management Method
     *
     * @param createMultilingualManagementDTO
     * @throws DataAccessException
     */
    @Override
    public void createMultilingualManagementData(CreateMultilingualManagementDTO createMultilingualManagementDTO) throws DataAccessException {
        String logId = this.cidsLogService.createNewLogId();

        try {
            int insertedRow = this.multilingualManagementMapper.insertMultilingualManagementData(createMultilingualManagementDTO);
            MyBatisResultHelper.checkAffectedRow(insertedRow, 1);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.POST, "MultilingualManagement");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.POST, "MultilingualManagement");

            throw e;
        }
    }

    /**
     * Update MultilingualManagement
     *
     * @param updateMultilingualManagementDTO
     * @throws DataAccessException
     */
    @Override
    public void modifyMultilingualManagementData(UpdateMultilingualManagementDTO updateMultilingualManagementDTO) throws DataAccessException {
        String logId = this.cidsLogService.createNewLogId();

        try {
            ViewMultilingualManagementDTO viewMultilingualManagementDTO = this.multilingualManagementMapper.selectMultilingualManagementDataBySeq(updateMultilingualManagementDTO.getSeqNo());
            if (viewMultilingualManagementDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "MultilingualManagement");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            int updatedRow = this.multilingualManagementMapper.updateMultilingualManagementData(updateMultilingualManagementDTO);
            MyBatisResultHelper.checkAffectedRow(updatedRow, 1);

            // cache eviction data
            this.localeMessageService.refreshAllLocaleMessageItemList();
            this.localeMessageService.refreshLocaleMessageList();

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.PUT, "MultilingualManagement");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "MultilingualManagement");

            throw e;
        }
    }
}
