package com.urielsoft.cids.management.biz.service;

import com.urielsoft.cids.management.biz.dto.CreateCidsNotifyManagerDTO;
import com.urielsoft.cids.management.biz.dto.SearchAndPaginationCidsNotifyManagerResultsDTO;
import com.urielsoft.cids.management.biz.dto.UpdateCidsNotifyManagerDTO;
import com.urielsoft.cids.management.biz.dto.ViewCidsNotifyManagerDTO;
import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;

import java.util.List;

/**
 * Cids Notify Manager Service
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-07-24
 */
public interface CidsNotifyManagerService {
    /**
     * View Notify Data
     *
     * @return
     * @throws Exception
     */
    public List<ViewCidsNotifyManagerDTO> listAllCidsNotifyManagerData() throws Exception;

    /**
     * Get Notify By cidsNotifyManagerSeq
     *
     * @param cidsNotifyManagerSeq
     * @return
     * @throws Exception
     */
    public ViewCidsNotifyManagerDTO getCidsNotifyManagerDataBySeq(int cidsNotifyManagerSeq) throws Exception;

    /**
     * Create new Notify
     *
     * @param createCidsNotifyManagerDTO
     * @throws Exception
     */
    public void createCidsNotifyManagerData(CreateCidsNotifyManagerDTO createCidsNotifyManagerDTO) throws Exception;

    /**
     * Update Notify
     *
     * @param updateCidsNotifyManagerDTO
     * @throws Exception
     */
    public void modifyCidsNotifyManagerData(UpdateCidsNotifyManagerDTO updateCidsNotifyManagerDTO) throws Exception;

    /**
     * Get Cids Notify Manager Data List Pagination And Search
     *
     * @param dataTableRequest
     * @return
     */
    public SearchAndPaginationCidsNotifyManagerResultsDTO getCidsNotifyManagerDataListPaginationAndSearch(DataTableRequest dataTableRequest);
}
