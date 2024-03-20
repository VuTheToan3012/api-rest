package com.urielsoft.cids.management.biz.service.impl;

import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.dto.SearchAndPaginationErrorHistoryResultsDTO;
import com.urielsoft.cids.management.biz.dto.SearchErrorHistoryPaginationStatusDTO;
import com.urielsoft.cids.management.biz.dto.ViewErrorHistoryDTO;
import com.urielsoft.cids.management.biz.dto.search.Columns;
import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;
import com.urielsoft.cids.management.biz.dto.search.Order;
import com.urielsoft.cids.management.biz.dto.search.Search;
import com.urielsoft.cids.management.biz.mapper.ErrorHistoryMapper;
import com.urielsoft.cids.management.biz.service.CidsLogService;
import com.urielsoft.cids.management.biz.service.ErrorHistoryService;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.common.exception.BizException;
import com.urielsoft.cids.management.common.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Error History Services Impl
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorHistoryServiceImpl implements ErrorHistoryService {
    /**
     * Error History Mapper
     */
    private final ErrorHistoryMapper errorHistoryMapper;

    /**
     * Usage History Service
     */
    private final UsageHistoryService usageHistoryService;

    /**
     * Cids Log Service
     */
    private final CidsLogService cidsLogService;

    /**
     * Get All List Log
     *
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<ViewErrorHistoryDTO> listAllErrorHistoryData() throws DataAccessException {
        List<ViewErrorHistoryDTO> viewErrorHistoryDTOS = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewErrorHistoryDTOS = this.errorHistoryMapper.selectErrorHistoryDataAllList();

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.Count, "ErrorHistory");

        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.Count, "ErrorHistory");

            throw e;
        }
        return viewErrorHistoryDTOS;
    }

    /**
     * Get Error History By Seq
     *
     * @param errorHistorySeq
     * @return
     * @throws DataAccessException
     */
    @Override
    public ViewErrorHistoryDTO getErrorHistoryDataBySeq(int errorHistorySeq) throws DataAccessException {
        ViewErrorHistoryDTO viewErrorHistoryDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewErrorHistoryDTO = this.errorHistoryMapper.selectErrorHistoryDataBySeq(errorHistorySeq);
            if (viewErrorHistoryDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "ErrorHistory");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "ErrorHistory");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "ErrorHistory");

            throw e;
        }
        return viewErrorHistoryDTO;
    }

    /**
     * Get Error History Data List Pagination And Search
     *
     * @param dataTableRequest
     * @return
     */
    @Override
    public SearchAndPaginationErrorHistoryResultsDTO getErrorHistoryDataListPaginationAndSearch(DataTableRequest dataTableRequest) {
        String logId = this.cidsLogService.createNewLogId();

        try {
            int start = dataTableRequest.getStart();
            int length = dataTableRequest.getLength();

            SearchErrorHistoryPaginationStatusDTO searchErrorHistoryPaginationStatusDTO = new SearchErrorHistoryPaginationStatusDTO();
            // GET column data
            List<Columns> columns = dataTableRequest.getColumns();
            for (Columns column : columns) {
                String columnName = column.getData();
                Search search = column.getSearch();

                if ("locTySeqNo".equals(columnName)) {
                    searchErrorHistoryPaginationStatusDTO.setSearChableLocTySeqNo(column.isSearchable());
                    searchErrorHistoryPaginationStatusDTO.setOrderableLocTySeqNo(column.isOrderable());
                    if (searchErrorHistoryPaginationStatusDTO.isSearChableLocTySeqNo() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            searchErrorHistoryPaginationStatusDTO.setSearchLocTySeqNoValue(search.getValue());
                        }
                    }
                } else if ("monNm".equals(columnName)) {
                    searchErrorHistoryPaginationStatusDTO.setSearChableMonNm(column.isSearchable());
                    searchErrorHistoryPaginationStatusDTO.setOrderableMonNm(column.isOrderable());
                    if (searchErrorHistoryPaginationStatusDTO.isSearChableMonNm() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            searchErrorHistoryPaginationStatusDTO.setSearchMonNmValue(search.getValue());
                        }
                    }
                } else if ("wkTySeqNo".equals(columnName)) {
                    searchErrorHistoryPaginationStatusDTO.setSearChableWkTySeqNo(column.isSearchable());
                    searchErrorHistoryPaginationStatusDTO.setOrderableWkTySeqNo(column.isOrderable());
                    if (searchErrorHistoryPaginationStatusDTO.isSearChableWkTySeqNo() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            searchErrorHistoryPaginationStatusDTO.setSearchWkTySeqNoValue(search.getValue());
                        }
                    }
                } else if ("regDt".equals(columnName)) {
                    searchErrorHistoryPaginationStatusDTO.setSearChableRegDt(column.isSearchable());
                    searchErrorHistoryPaginationStatusDTO.setOrderableRegDt(column.isOrderable());
                    if (searchErrorHistoryPaginationStatusDTO.isSearChableRegDt() == true) {
                        if (search.getValue() != null && search.getValue() != "") {
                            LocalDate regDate = LocalDate.parse(search.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            searchErrorHistoryPaginationStatusDTO.setSearchRegDtValue(String.valueOf(regDate));
                        }
                    }
                }
            }

            // GET Order Data
            List<Order> orders = dataTableRequest.getOrder();
            for (Order order : orders
            ) {
                String columnName = order.getColumn();
                if (searchErrorHistoryPaginationStatusDTO.isOrderByColumn(columnName)) {
                    searchErrorHistoryPaginationStatusDTO.setDir(order.getDir());
                    searchErrorHistoryPaginationStatusDTO.setSortBy(order.getColumn());
                }

            }

            SearchAndPaginationErrorHistoryResultsDTO dblist = new SearchAndPaginationErrorHistoryResultsDTO();

            dblist.setViewErrorHistoryDTOS(this.errorHistoryMapper.selectErrorHistoryDataListPaginationAndSearch(start, length, searchErrorHistoryPaginationStatusDTO));

            if (dblist.getViewErrorHistoryDTOS() == null || dblist.getViewErrorHistoryDTOS().size() == 0) {
                dblist.setCountFilterData(0);
            } else {
                dblist.setCountFilterData(dblist.getViewErrorHistoryDTOS().get(0).getCountFilterData());
            }

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETALl, "ErrorHistory");

            return dblist;
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "ErrorHistory");

            throw e;
        }

    }
}