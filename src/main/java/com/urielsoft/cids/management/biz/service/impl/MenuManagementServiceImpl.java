package com.urielsoft.cids.management.biz.service.impl;


import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.dto.CreateMenuManagementDTO;
import com.urielsoft.cids.management.biz.dto.UpdateMenuManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewMenuManagementDTO;
import com.urielsoft.cids.management.biz.mapper.MenuManagementMapper;
import com.urielsoft.cids.management.biz.service.CidsLogService;
import com.urielsoft.cids.management.biz.service.MenuManagementService;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Menu Management Service Impl
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuManagementServiceImpl implements MenuManagementService {
    /**
     * Menu Management Mapper
     */
    private final MenuManagementMapper menuMapper;

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
     * Create New Menu Method
     *
     * @param createMenuDTO
     * @throws DataAccessException
     */
    @Transactional
    @Override
    public void createMenuData(CreateMenuManagementDTO createMenuDTO) throws DataAccessException {
        String logId = this.cidsLogService.createNewLogId();

        try {
            int insertedRow = this.menuMapper.insertMenuData(createMenuDTO);
            MyBatisResultHelper.checkAffectedRow(insertedRow, 1);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.POST, "MenuManagement");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.POST, "MenuManagement");

            throw e;
        }
    }

    /**
     * Get All Menu Method
     *
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<ViewMenuManagementDTO> listAllMenuData() throws DataAccessException {
        List<ViewMenuManagementDTO> viewMenuManagementDTOS = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewMenuManagementDTOS = this.menuMapper.selectMenuDataAllList();

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETALl, "MenuManagement");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "MenuManagement");

            throw e;
        }
        return viewMenuManagementDTOS;
    }

    /**
     * Get Menu Data By SeqNo
     *
     * @param seqNo
     * @return
     * @throws DataAccessException
     */
    @Override
    public ViewMenuManagementDTO getMenuDataBySeqNo(Integer seqNo) throws DataAccessException {
        ViewMenuManagementDTO viewMenuDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewMenuDTO = this.menuMapper.selectMenuDataBySeqNo(seqNo);
            if (viewMenuDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "MenuManagement");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "MenuManagement");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "MenuManagement");

            throw e;
        }
        return viewMenuDTO;
    }

    /**
     * Modify Menu Data
     *
     * @param updateMenuDTO
     * @throws DataAccessException
     */
    @Override
    public void modifyMenuData(UpdateMenuManagementDTO updateMenuDTO) throws DataAccessException {
        String logId = this.cidsLogService.createNewLogId();

        try {
            ViewMenuManagementDTO viewMenuDTO = this.menuMapper.selectMenuDataBySeqNo(updateMenuDTO.getSeqNo());
            if (viewMenuDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "MenuManagement");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            int updateRow = this.menuMapper.updateMenuData(updateMenuDTO);
            MyBatisResultHelper.checkAffectedRow(updateRow, 1);

            this.localeMessageService.refreshLocaleMessageMenuList();
            this.localeMessageService.refreshLocaleMessageList();

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.PUT, "MenuManagement");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "MenuManagement");

            throw e;
        }
    }

    /**
     * Menu Management Check Duplicate For TagId
     *
     * @param menuManagementTagId
     * @return
     */
    @Override
    public ResponseEntity<ApiResult> menuManagementCheckDuplicateForTagId(String menuManagementTagId) {
        String logId = this.cidsLogService.createNewLogId();

        ViewMenuManagementDTO viewMenuManagementDTO = null;
        try {
            viewMenuManagementDTO = menuMapper.selectMenuManagementDataByTagId(menuManagementTagId);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "MenuManagement");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "MenuManagement");

            throw e;
        }
        if (viewMenuManagementDTO == null) {
            return ResponseEntity.ok(new ApiResult(false));
        }
        return ResponseEntity.ok(new ApiResult(true));
    }
}