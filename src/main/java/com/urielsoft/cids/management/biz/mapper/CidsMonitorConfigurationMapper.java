package com.urielsoft.cids.management.biz.mapper;

import com.urielsoft.cids.management.biz.dto.SearchCidsMonitorConfigurationFindLocAndTyDTO;
import com.urielsoft.cids.management.biz.dto.UpdateCidsMonitorConfigurationDTO;
import com.urielsoft.cids.management.biz.dto.ViewCidsMonitorConfigurationDTO;
import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Cids Monitor Configuration Mapper
 *
 * @author thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-08-05
 */
@Repository
@Db1Mapper
public interface CidsMonitorConfigurationMapper {
    /**
     * Select All Data of Monitor from Database
     *
     * @return
     * @throws DataAccessException
     */
    public List<ViewCidsMonitorConfigurationDTO> selectCidsMonitorConfigurationDataAllList() throws DataAccessException;

    /**
     * Select Data Of Monitor By cidsMonitorManagerSeq
     *
     * @param cidsMonitorManagerSeq
     * @return
     * @throws DataAccessException
     */
    public ViewCidsMonitorConfigurationDTO selectCidsMonitorConfigurationDataBySeq(@Param("cidsMonitorManagerSeq") int cidsMonitorManagerSeq) throws DataAccessException;

    /**
     * Update  template Monitor
     *
     * @param updateCidsMonitorConfigurationDto
     * @return
     * @throws DataAccessException
     */
    public int updateCidsMonitorConfigurationData(UpdateCidsMonitorConfigurationDTO updateCidsMonitorConfigurationDto) throws DataAccessException;

    /**
     * Find Template By Type And Location Data
     * @param cidsMonitorConfigurationFindLocAndTyDTO
     * @return
     */
    public ViewCidsMonitorConfigurationDTO findTemplateByTypeAndLocationData(SearchCidsMonitorConfigurationFindLocAndTyDTO cidsMonitorConfigurationFindLocAndTyDTO);
}
