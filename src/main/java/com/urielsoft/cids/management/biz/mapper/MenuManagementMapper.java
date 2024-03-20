package com.urielsoft.cids.management.biz.mapper;


import com.urielsoft.cids.management.biz.dto.CreateMenuManagementDTO;
import com.urielsoft.cids.management.biz.dto.UpdateMenuManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewMenuManagementDTO;
import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Menu Management Mapper
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */
@Repository
@Db1Mapper
public interface MenuManagementMapper {

    /**
     * Insert Menu Data
     *
     * @param createMenuDTO
     * @return
     * @throws DataAccessException
     */
    public int insertMenuData(CreateMenuManagementDTO createMenuDTO) throws DataAccessException;

    /**
     * Select All Data of Menu from Database
     *
     * @return
     * @throws DataAccessException
     */
    public List<ViewMenuManagementDTO> selectMenuDataAllList() throws DataAccessException;

    /**
     * Select Menu Data By SeqNo
     * @param seqNo
     * @return
     * @throws DataAccessException
     */
    public ViewMenuManagementDTO selectMenuDataBySeqNo(Integer seqNo) throws DataAccessException;

    /**
     * Update Menu Data
     *
     * @param updateMenuDTO
     * @return
     * @throws DataAccessException
     */
    public int updateMenuData(UpdateMenuManagementDTO updateMenuDTO) throws DataAccessException;

    /**
     * Select Menu Management Data By TagId
     *
     * @param menuManagementTagId
     * @return
     */
    public ViewMenuManagementDTO selectMenuManagementDataByTagId(String menuManagementTagId);
}
