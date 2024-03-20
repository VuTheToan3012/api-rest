package com.urielsoft.cids.management.biz.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.common.enums.Protocol;
import com.urielsoft.cids.management.biz.common.utils.DateTimeUtils;
import com.urielsoft.cids.management.biz.dto.*;
import com.urielsoft.cids.management.biz.dto.search.Columns;
import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;
import com.urielsoft.cids.management.biz.dto.search.Order;
import com.urielsoft.cids.management.biz.dto.search.Search;
import com.urielsoft.cids.management.biz.mapper.CidsNotifyManagerMapper;
import com.urielsoft.cids.management.biz.service.CidsLogService;
import com.urielsoft.cids.management.biz.service.CidsNotifyManagerService;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.biz.service.WebSocketService;
import com.urielsoft.cids.management.common.exception.BizException;
import com.urielsoft.cids.management.common.exception.ExceptionCode;
import com.urielsoft.cids.management.common.mybatis.MyBatisResultHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Cids Notify Manager Service Impl
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-07-24
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class CidsNotifyManagerServiceImpl implements CidsNotifyManagerService {
    /**
     * Cids Notify Manager Mapper
     */
    private final CidsNotifyManagerMapper cidsNotifyManagerMapper;

    /**
     * Usage History Service
     */
    private final UsageHistoryService usageHistoryService;

    /**
     * Web Socket Service
     */
    private final WebSocketService webSocketService;

    /**
     * Cids Log Service
     */
    private final CidsLogService cidsLogService;

    /**
     * Get All Notify manager Method
     *
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<ViewCidsNotifyManagerDTO> listAllCidsNotifyManagerData() throws DataAccessException {
        List<ViewCidsNotifyManagerDTO> viewCidsNotifyManagerDTOS = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewCidsNotifyManagerDTOS = this.cidsNotifyManagerMapper.selectNotifyManagerDataAllList();

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.Count, "NotifyManager");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.Count, "NotifyManager");

            throw e;
        }
        return viewCidsNotifyManagerDTOS;
    }

    /**
     * Get Cids Notify Manager Data By Seq
     *
     * @param cidsNotifyManagerSeq
     * @return
     * @throws DataAccessException
     */
    @Override
    public ViewCidsNotifyManagerDTO getCidsNotifyManagerDataBySeq(int cidsNotifyManagerSeq) throws DataAccessException {
        ViewCidsNotifyManagerDTO viewCidsNotifyManagerDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewCidsNotifyManagerDTO = this.cidsNotifyManagerMapper.selectCidsNotifyManagerDataBySeq(cidsNotifyManagerSeq);
            if (viewCidsNotifyManagerDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "NotifyManager");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }
            if (viewCidsNotifyManagerDTO.getMsgFwdYn() == 1) {
                viewCidsNotifyManagerDTO.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(viewCidsNotifyManagerDTO.getMdfyDt()));
            } else {
                viewCidsNotifyManagerDTO.setMdfyDt(null);
            }

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "NotifyManager");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "NotifyManager");

            throw e;
        }
        return viewCidsNotifyManagerDTO;
    }

    /**
     * Create Cids Notify Manager Data
     *
     * @param createCidsNotifyManagerDTO
     * @throws DataAccessException
     */
    @Override
    public void createCidsNotifyManagerData(CreateCidsNotifyManagerDTO createCidsNotifyManagerDTO) throws Exception {
        String logId = this.cidsLogService.createNewLogId();

        try {
            int insertedRow = this.cidsNotifyManagerMapper.insertCidsNotifyManagerData(createCidsNotifyManagerDTO);
            MyBatisResultHelper.checkAffectedRow(insertedRow, 1);

            // TODO: Send WebSocket When Post Notify
            var createCidsMonitorOperationMsgRequestSocketDTO = new CreateCidsMonitorOperationMsgRequestSocketDTO();

            ObjectMapper objectMapper = new ObjectMapper();

            String respone = "";

            String request = null;
            try {
                createCidsMonitorOperationMsgRequestSocketDTO.setProtocol(Protocol.CIDS_MONITOR_OPERATION_MSG);
                createCidsMonitorOperationMsgRequestSocketDTO.setTransactionId(logId);
                createCidsMonitorOperationMsgRequestSocketDTO.setRequestData(null);

                String jsonString = objectMapper.writeValueAsString(createCidsMonitorOperationMsgRequestSocketDTO);

                request = jsonString;

                respone = webSocketService.sendMessage(jsonString);

                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.POST, "Websocket CIDS_MONITOR_OPERATION_MSG");

                this.usageHistoryService.createProtocolLog(logId, Protocol.CIDS_MONITOR_OPERATION_MSG.toString(), CidsLogLevelEnum.OK.toString(), request.toString(), respone.toString(), createCidsNotifyManagerDTO.getMdfyUserSeqNO());

            } catch (Exception e) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.POST, "Websocket CIDS_MONITOR_OPERATION_MSG");

                this.usageHistoryService.createProtocolLog(logId, Protocol.CIDS_MONITOR_OPERATION_MSG.toString(), CidsLogLevelEnum.ERROR.toString(), request.toString(), respone.toString(), createCidsNotifyManagerDTO.getMdfyUserSeqNO());

                throw e;
            }

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.POST, "NotifyManager");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.POST, "NotifyManager");

            throw e;
        }
    }

    /**
     * Modify Cids Notify Manager Data
     *
     * @param updateCidsNotifyManagerDTO
     * @throws DataAccessException
     */
    @Override
    public void modifyCidsNotifyManagerData(UpdateCidsNotifyManagerDTO updateCidsNotifyManagerDTO) throws DataAccessException {
        ViewCidsNotifyManagerDTO viewCidsNotifyManagerDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewCidsNotifyManagerDTO = this.cidsNotifyManagerMapper.selectCidsNotifyManagerDataBySeq(updateCidsNotifyManagerDTO.getSeqNo());
            if (viewCidsNotifyManagerDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "NotifyManager");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            int updatedRow = this.cidsNotifyManagerMapper.updateCidsNotifyManagerData(updateCidsNotifyManagerDTO);
            MyBatisResultHelper.checkAffectedRow(updatedRow, 1);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.PUT, "NotifyManager");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "NotifyManager");

            throw e;
        }
    }

    /**
     * Get Cids Notify Manager Data List Pagination And Search
     *
     * @param dataTableRequest
     * @return
     */
    @Override
    public SearchAndPaginationCidsNotifyManagerResultsDTO getCidsNotifyManagerDataListPaginationAndSearch(DataTableRequest dataTableRequest) {
        String logId = this.cidsLogService.createNewLogId();

        try {
            int start = dataTableRequest.getStart();
            int length = dataTableRequest.getLength();

            SearchCidsNotifyManagerPaginationStatusDTO searchCidsNotifyManagerPaginationStatusDTO = new SearchCidsNotifyManagerPaginationStatusDTO();
            // GET column data
            List<Columns> columns = dataTableRequest.getColumns();
            for (Columns column : columns) {
                String columnName = column.getData();
                Search search = column.getSearch();

                if ("locTySeqNo".equals(columnName)) {
                    searchCidsNotifyManagerPaginationStatusDTO.setSearChableLocTySeqNo(column.isSearchable());
                    searchCidsNotifyManagerPaginationStatusDTO.setOrderableLocTySeqNo(column.isOrderable());
                    if (searchCidsNotifyManagerPaginationStatusDTO.isSearChableLocTySeqNo() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            String locSearchValue = search.getValue();
                            String[] listLocSearchVal = locSearchValue.split("\\|");
                            searchCidsNotifyManagerPaginationStatusDTO.setSearchLocTySeqNoValue(listLocSearchVal);
                        }
                    }
                } else if ("wkTySeqNo".equals(columnName)) {
                    searchCidsNotifyManagerPaginationStatusDTO.setSearChableWkTySeqNo(column.isSearchable());
                    searchCidsNotifyManagerPaginationStatusDTO.setOrderableWkTySeqNo(column.isOrderable());
                    if (searchCidsNotifyManagerPaginationStatusDTO.isSearChableWkTySeqNo() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            searchCidsNotifyManagerPaginationStatusDTO.setSearchWkTySeqNoValue(search.getValue());
                        }
                    }
                } else if ("regDt".equals(columnName)) {
                    searchCidsNotifyManagerPaginationStatusDTO.setSearChableRegDt(column.isSearchable());
                    searchCidsNotifyManagerPaginationStatusDTO.setOrderableRegDt(column.isOrderable());
                    if (searchCidsNotifyManagerPaginationStatusDTO.isSearChableRegDt() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            LocalDate regDate = LocalDate.parse(search.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            searchCidsNotifyManagerPaginationStatusDTO.setSearchRegDtValue(String.valueOf(regDate));
                        }
                    }
                } else if ("mdfyDt".equals(columnName)) {
                    searchCidsNotifyManagerPaginationStatusDTO.setSearChableRegDt(column.isSearchable());
                    searchCidsNotifyManagerPaginationStatusDTO.setOrderableRegDt(column.isOrderable());
                    if (searchCidsNotifyManagerPaginationStatusDTO.isSearChableRegDt() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            LocalDate mdfyDate = LocalDate.parse(search.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            searchCidsNotifyManagerPaginationStatusDTO.setSearchRegDtValue(String.valueOf(mdfyDate));
                        }
                    }
                }
            }

            // GET Order Data
            List<Order> orders = dataTableRequest.getOrder();
            for (Order order : orders
            ) {
                String columnName = order.getColumn();

                if (searchCidsNotifyManagerPaginationStatusDTO.isOrderByColumn(columnName)) {
                    searchCidsNotifyManagerPaginationStatusDTO.setDir(order.getDir());
                    searchCidsNotifyManagerPaginationStatusDTO.setSortBy(order.getColumn());
                }

            }
            SearchAndPaginationCidsNotifyManagerResultsDTO dblist = new SearchAndPaginationCidsNotifyManagerResultsDTO();

            dblist.setViewCidsNotifyManagerDTOS(this.cidsNotifyManagerMapper.selectCidsNotifyManagerDataListPaginationAndSearch(start, length, searchCidsNotifyManagerPaginationStatusDTO));

            if (dblist.getViewCidsNotifyManagerDTOS() == null || dblist.getViewCidsNotifyManagerDTOS().size() == 0) {
                dblist.setCountFilterData(0);
            } else {
                dblist.setCountFilterData(dblist.getViewCidsNotifyManagerDTOS().get(0).getCountFilterData());
            }

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETALl, "NotifyManager");

            return dblist;
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "NotifyManager");

            throw e;
        }
    }
}
