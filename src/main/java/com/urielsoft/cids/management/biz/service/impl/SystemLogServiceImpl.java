package com.urielsoft.cids.management.biz.service.impl;

import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.dto.SearchAndPaginationSystemLogResultsDTO;
import com.urielsoft.cids.management.biz.dto.SearchSystemLogPaginationStatusDTO;
import com.urielsoft.cids.management.biz.dto.ViewSystemLogDTO;
import com.urielsoft.cids.management.biz.dto.api.param.ReadSystemLogByDateParamDTO;
import com.urielsoft.cids.management.biz.dto.search.Columns;
import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;
import com.urielsoft.cids.management.biz.dto.search.Order;
import com.urielsoft.cids.management.biz.dto.search.Search;
import com.urielsoft.cids.management.biz.mapper.SystemLogMapper;
import com.urielsoft.cids.management.biz.service.CidsLogService;
import com.urielsoft.cids.management.biz.service.SystemLogService;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.common.exception.BizException;
import com.urielsoft.cids.management.common.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * System Log Service Impl
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemLogServiceImpl implements SystemLogService {
    /**
     * Error History Mapper
     */
    private final SystemLogMapper systemLogMapper;

    /**
     * Usage History Service
     */
    private final UsageHistoryService usageHistoryService;

    /**
     * Cids Log Service
     */
    private final CidsLogService cidsLogService;

    /**
     * List All System Log Data Count
     *
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<ViewSystemLogDTO> listAllSystemLogData() throws DataAccessException {
        List<ViewSystemLogDTO> viewSystemLogDTOS = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewSystemLogDTOS = this.systemLogMapper.selectSystemLogDataAllList();

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.Count, "SystemLog");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.Count, "SystemLog");

            throw e;
        }
        return viewSystemLogDTOS;
    }

    /**
     * Get System Log Data By Seq
     *
     * @param systemLogSeq
     * @return
     * @throws DataAccessException
     */
    @Override
    public ViewSystemLogDTO getSystemLogDataBySeq(int systemLogSeq) throws DataAccessException {
        ViewSystemLogDTO viewSystemLogDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewSystemLogDTO = this.systemLogMapper.selectSystemLogDataBySeq(systemLogSeq);
            if (viewSystemLogDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "SystemLog");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "SystemLog");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "SystemLog");

            throw e;
        }
        return viewSystemLogDTO;
    }

    /**
     * Get System Log Data By Date
     *
     * @param readSystemLogByDateParamDTO
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<ViewSystemLogDTO> getSystemLogDataByDate(ReadSystemLogByDateParamDTO readSystemLogByDateParamDTO) throws DataAccessException {
        List<ViewSystemLogDTO> viewSystemLogDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewSystemLogDTO = this.systemLogMapper.selectSystemLogDataByDate(readSystemLogByDateParamDTO);
            if (viewSystemLogDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "SystemLog");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETALl, "SystemLog");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "SystemLog");

            throw e;
        }
        return viewSystemLogDTO;
    }

    /**
     * Get System Log Data List Pagination And Search
     *
     * @param dataTableRequest
     * @return
     */
    @Override
    public SearchAndPaginationSystemLogResultsDTO getSystemLogDataListPaginationAndSearch(DataTableRequest dataTableRequest) {
        String logId = this.cidsLogService.createNewLogId();

        try {
            int start = dataTableRequest.getStart();
            int length = dataTableRequest.getLength();

            SearchSystemLogPaginationStatusDTO searchSystemLogPaginationStatusDTO = new SearchSystemLogPaginationStatusDTO();
            // GET column data
            List<Columns> columns = dataTableRequest.getColumns();
            for (Columns column : columns) {
                String columnName = column.getData();
                Search search = column.getSearch();

                if ("occrrncdt".equals(columnName)) {
                    searchSystemLogPaginationStatusDTO.setSearChableoCCRRNCDt(column.isSearchable());
                    searchSystemLogPaginationStatusDTO.setOrderableoCCRRNCDt(column.isOrderable());
                    if (searchSystemLogPaginationStatusDTO.isSearChableoCCRRNCDt() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            searchSystemLogPaginationStatusDTO.setSearchoCCRRNCDtValue(search.getValue());
                        }
                    }
                } else if ("sysTyNm".equals(columnName)) {
                    searchSystemLogPaginationStatusDTO.setSearChableSysTyNm(column.isSearchable());
                    searchSystemLogPaginationStatusDTO.setOrderableSysTyNm(column.isOrderable());
                    if (searchSystemLogPaginationStatusDTO.isSearChableSysTyNm() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            searchSystemLogPaginationStatusDTO.setSearchSysTyNmValue(search.getValue());
                        }
                    }
                } else if ("logLvl".equals(columnName)) {
                    searchSystemLogPaginationStatusDTO.setSearChableLogLvl(column.isSearchable());
                    searchSystemLogPaginationStatusDTO.setOrderableLogLvl(column.isOrderable());
                    if (searchSystemLogPaginationStatusDTO.isSearChableLogLvl() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            searchSystemLogPaginationStatusDTO.setSearchLogLvlValue(search.getValue());
                        }
                    }
                }
            }

            // GET Order Data
            List<Order> orders = dataTableRequest.getOrder();
            for (Order order : orders
            ) {
                String columnName = order.getColumn();
                if (searchSystemLogPaginationStatusDTO.isOrderByColumn(columnName)) {
                    searchSystemLogPaginationStatusDTO.setDir(order.getDir());
                    searchSystemLogPaginationStatusDTO.setSortBy(order.getColumn());
                }

            }

            SearchAndPaginationSystemLogResultsDTO dblist = new SearchAndPaginationSystemLogResultsDTO();

            dblist.setViewSystemLogDTOS(this.systemLogMapper.selectSystemLogDataListPaginationAndSearch(start, length, searchSystemLogPaginationStatusDTO));

            if (dblist.getViewSystemLogDTOS() == null || dblist.getViewSystemLogDTOS().size() == 0) {
                dblist.setCountFilterData(0);
            } else {
                dblist.setCountFilterData(dblist.getViewSystemLogDTOS().get(0).getCountFilterData());
            }

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETALl, "SystemLog");

            return dblist;
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "SystemLog");

            throw e;
        }

    }
}