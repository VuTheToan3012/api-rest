package com.urielsoft.cids.management.biz.service;


import com.urielsoft.cids.management.biz.dto.CreateMenuManagementDTO;
import com.urielsoft.cids.management.biz.dto.UpdateMenuManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewMenuManagementDTO;
import com.urielsoft.cids.management.common.api.ApiResult;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Menu Management Service
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */
public interface MenuManagementService {

    /**
     * Create new Menu
     *
     * @param createMenuDTO
     * @throws Exception
     */
    public void createMenuData(CreateMenuManagementDTO createMenuDTO) throws Exception;

    /**
     * View Menu Data
     *
     * @return
     * @throws Exception
     */
    public List<ViewMenuManagementDTO> listAllMenuData() throws Exception;

    /**
     * Get Menu Data By SeqNo
     *
     * @param seqNo
     * @return
     * @throws Exception
     */
    public ViewMenuManagementDTO getMenuDataBySeqNo(Integer seqNo) throws Exception;

    /**
     * Update Menu Data
     *
     * @param updateMenuDTO
     * @throws Exception
     */
    public void modifyMenuData(UpdateMenuManagementDTO updateMenuDTO) throws Exception;

    /**
     * Menu Management CheckDuplicate For Tag Id
     *
     * @param menuManagementTagId
     * @return
     */
    ResponseEntity<ApiResult> menuManagementCheckDuplicateForTagId(String menuManagementTagId);
}
