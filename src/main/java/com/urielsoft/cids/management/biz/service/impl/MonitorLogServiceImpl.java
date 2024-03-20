package com.urielsoft.cids.management.biz.service.impl;

import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.dto.SearchAndPaginationMonitorLogResultsDTO;
import com.urielsoft.cids.management.biz.dto.SearchMonitorLogPaginationStatusDTO;
import com.urielsoft.cids.management.biz.dto.ViewMonitorLogDTO;
import com.urielsoft.cids.management.biz.dto.api.param.ReadMonitorLogByDateParamDTO;
import com.urielsoft.cids.management.biz.dto.search.Columns;
import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;
import com.urielsoft.cids.management.biz.dto.search.Order;
import com.urielsoft.cids.management.biz.dto.search.Search;
import com.urielsoft.cids.management.biz.mapper.MonitorLogMapper;
import com.urielsoft.cids.management.biz.service.CidsLogService;
import com.urielsoft.cids.management.biz.service.MonitorLogService;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.common.exception.BizException;
import com.urielsoft.cids.management.common.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Monitor Log Service Impl
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MonitorLogServiceImpl implements MonitorLogService {
    /**
     * Error History Mapper
     */
    private final MonitorLogMapper monitorLogMapper;

    /**
     * Usage History Service
     */
    private final UsageHistoryService usageHistoryService;

    /**
     * Cids Log Service
     */
    private final CidsLogService cidsLogService;

    /**
     * Get All List Monitor Log
     *
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<ViewMonitorLogDTO> listAllMonitorLogData() throws DataAccessException {
        List<ViewMonitorLogDTO> viewMonitorLogDTOS = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewMonitorLogDTOS = this.monitorLogMapper.selectMonitorLogDataAllList();

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.Count, "MonitorLog");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.Count, "MonitorLog");

            throw e;
        }
        return viewMonitorLogDTOS;
    }

    /**
     * Get Monitor Log By Seq
     *
     * @param monitorLogSeq
     * @return
     * @throws DataAccessException
     */
    @Override
    public ViewMonitorLogDTO getMonitorLogDataBySeq(int monitorLogSeq) throws DataAccessException {
        ViewMonitorLogDTO viewMonitorLogDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewMonitorLogDTO = this.monitorLogMapper.selectMonitorLogDataBySeq(monitorLogSeq);
            if (viewMonitorLogDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "MonitorLog");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "MonitorLog");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "MonitorLog");

            throw e;
        }
        return viewMonitorLogDTO;
    }

    /**
     * Get Monitor Log Data By Date
     *
     * @param readMonitorLogByDateParamDTO
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<ViewMonitorLogDTO> getMonitorLogDataByDate(ReadMonitorLogByDateParamDTO readMonitorLogByDateParamDTO) throws DataAccessException {
        List<ViewMonitorLogDTO> viewMonitorLogDTOS = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewMonitorLogDTOS = this.monitorLogMapper.selectMonitorLogDataByDate(readMonitorLogByDateParamDTO);
            if (viewMonitorLogDTOS == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "SystemLog");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETALl, "SystemLog");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "SystemLog");

            throw e;
        }
        return viewMonitorLogDTOS;
    }

    /**
     * Get Error History Data List Pagination And Search
     *
     * @param dataTableRequest
     * @return
     */
    @Override
    public SearchAndPaginationMonitorLogResultsDTO getMonitorLogDataListPaginationAndSearch(DataTableRequest dataTableRequest) {
        String logId = this.cidsLogService.createNewLogId();

        try {
            int start = dataTableRequest.getStart();
            int length = dataTableRequest.getLength();

            SearchMonitorLogPaginationStatusDTO searchMonitorLogPaginationStatusDTO = new SearchMonitorLogPaginationStatusDTO();
            // GET column data
            List<Columns> columns = dataTableRequest.getColumns();
            for (Columns column : columns) {
                String columnName = column.getData();
                Search search = column.getSearch();

                if ("occrrncdt".equals(columnName)) {
                    searchMonitorLogPaginationStatusDTO.setSearchableOCCRRNCDt(column.isSearchable());
                    searchMonitorLogPaginationStatusDTO.setOrderableOCCRRNCDt(column.isOrderable());
                    if (searchMonitorLogPaginationStatusDTO.isSearchableOCCRRNCDt() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            searchMonitorLogPaginationStatusDTO.setSearchOCCRRNCDtValue(search.getValue());
                        }
                    }
                } else if ("wkTySeqNo".equals(columnName)) {
                    searchMonitorLogPaginationStatusDTO.setSearChableWkTySeqNo(column.isSearchable());
                    searchMonitorLogPaginationStatusDTO.setOrderableWkTySeqNo(column.isOrderable());
                    if (searchMonitorLogPaginationStatusDTO.isSearChableWkTySeqNo() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            searchMonitorLogPaginationStatusDTO.setSearchWkTySeqNoValue(search.getValue());
                        }
                    }
                } else if ("locTySeqNo".equals(columnName)) {
                    searchMonitorLogPaginationStatusDTO.setSearChableLocTySeqNo(column.isSearchable());
                    searchMonitorLogPaginationStatusDTO.setOrderableLocTySeqNo(column.isOrderable());
                    if (searchMonitorLogPaginationStatusDTO.isSearChableLocTySeqNo() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            String locSearchValue = search.getValue();
                            String[] listLocSearchVal = locSearchValue.split("\\|");
                            searchMonitorLogPaginationStatusDTO.setSearchLocTySeqNoValue(listLocSearchVal);
                        }
                    }
                } else if ("monNm".equals(columnName)) {
                    searchMonitorLogPaginationStatusDTO.setSearChableMonNm(column.isSearchable());
                    searchMonitorLogPaginationStatusDTO.setOrderableMonNm(column.isOrderable());
                    if (searchMonitorLogPaginationStatusDTO.isSearChableMonNm() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            searchMonitorLogPaginationStatusDTO.setSearchMonNmValue(search.getValue());
                        }
                    }
                } else if ("logLvl".equals(columnName)) {
                    searchMonitorLogPaginationStatusDTO.setSearChableLogLvl(column.isSearchable());
                    searchMonitorLogPaginationStatusDTO.setOrderableLogLvl(column.isOrderable());
                    if (searchMonitorLogPaginationStatusDTO.isSearChableMonNm() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            searchMonitorLogPaginationStatusDTO.setSearchLogLvlValue(search.getValue());
                        }
                    }
                }
                else if ("sysTyNm".equals(columnName)) {
                    searchMonitorLogPaginationStatusDTO.setSearChableSysTyNm(column.isSearchable());
                    searchMonitorLogPaginationStatusDTO.setOrderableSysTyNm(column.isOrderable());
                    if (searchMonitorLogPaginationStatusDTO.isSearChableSysTyNm() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            searchMonitorLogPaginationStatusDTO.setSearchSysTyNmValue(search.getValue());
                        }
                    }
                }

            }

            // GET Order Data
            List<Order> orders = dataTableRequest.getOrder();
            for (Order order : orders
            ) {
                String columnName = order.getColumn();
                if (searchMonitorLogPaginationStatusDTO.isOrderByColumn(columnName)) {
                    searchMonitorLogPaginationStatusDTO.setDir(order.getDir());
                    searchMonitorLogPaginationStatusDTO.setSortBy(order.getColumn());
                }

            }

            SearchAndPaginationMonitorLogResultsDTO dblist = new SearchAndPaginationMonitorLogResultsDTO();

            dblist.setViewMonitorLogDTOS(this.monitorLogMapper.selectMonitorLogDataListPaginationAndSearch(start, length, searchMonitorLogPaginationStatusDTO));

            if (dblist.getViewMonitorLogDTOS() == null || dblist.getViewMonitorLogDTOS().size() == 0) {
                dblist.setCountFilterData(0);
            } else {
                dblist.setCountFilterData(dblist.getViewMonitorLogDTOS().get(0).getCountFilterData());
            }

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETALl, "MonitorLog");

            return dblist;
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "MonitorLog");

            throw e;
        }

    }
}