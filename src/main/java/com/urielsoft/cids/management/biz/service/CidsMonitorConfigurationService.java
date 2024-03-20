package com.urielsoft.cids.management.biz.service;

import com.urielsoft.cids.management.biz.dto.SearchCidsMonitorConfigurationFindLocAndTyDTO;
import com.urielsoft.cids.management.biz.dto.UpdateCidsMonitorConfigurationDTO;
import com.urielsoft.cids.management.biz.dto.ViewCidsMonitorConfigurationDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Cids Monitor Configuration Service
 *
 * @author thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-08-05
 */
public interface CidsMonitorConfigurationService {
    /**
     * View monitor Data
     *
     * @return
     * @throws Exception
     */
    public List<ViewCidsMonitorConfigurationDTO> listAllCidsMonitorConfigurationData() throws Exception;

    /**
     * Get Monitor By cidsMonitorManagerSeq
     *
     * @param cidsMonitorManagerSeq
     * @return
     * @throws Exception
     */
    public ViewCidsMonitorConfigurationDTO getCidsMonitorConfigurationDataBySeq(int cidsMonitorManagerSeq) throws Exception;

    /**
     * Update template Monitor
     *
     * @param updateCidsMonitorConfigurationDto
     * @throws Exception
     */
    public void modifyCidsMonitorConfigurationData(UpdateCidsMonitorConfigurationDTO updateCidsMonitorConfigurationDto) throws Exception;

    /**
     * Find Template By Type And Location Data
     *
     * @param cidsMonitorConfigurationFindLocAndTyDTO
     * @throws DataAccessException
     */
    public ViewCidsMonitorConfigurationDTO findTemplateByTypeAndLocationData(SearchCidsMonitorConfigurationFindLocAndTyDTO cidsMonitorConfigurationFindLocAndTyDTO) throws DataAccessException;
}
