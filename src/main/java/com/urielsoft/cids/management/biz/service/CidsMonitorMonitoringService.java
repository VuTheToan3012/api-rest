package com.urielsoft.cids.management.biz.service;

import com.urielsoft.cids.management.biz.dto.CreateCidsMonitorMonitoringDTO;
import com.urielsoft.cids.management.biz.dto.ViewCidsMonitorMonitoringDTO;
import com.urielsoft.cids.management.biz.dto.api.param.CidsMonitorConfigurationFindLocAndTyParamDTO;

import java.util.List;

/**
 * Cids Monitor Monitoring Service
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */
public interface CidsMonitorMonitoringService {

    /**
     * Create new monitor monitoring
     *
     * @param createCidsMonitorMonitoringDTO
     * @throws Exception
     */
    public void createCidsMonitorMonitoringData(CreateCidsMonitorMonitoringDTO createCidsMonitorMonitoringDTO) throws Exception;

    /**
     * View monitor monitoring Data
     *
     * @param wkTySeqNo
     * @return
     * @throws Exception
     */
    public List<ViewCidsMonitorMonitoringDTO> listAllCidsMonitorMonitoringData(Integer wkTySeqNo) throws Exception;

    /**
     * Get Monitor Monitoring By monitoriSeqNo
     *
     * @param cidsMonitorMonitoringSeq
     * @return
     * @throws Exception
     */
    public ViewCidsMonitorMonitoringDTO getCidsMonitorMonitoringDataBySeq(int cidsMonitorMonitoringSeq) throws Exception;

    /**
     * Find Cids Monitor Monitoring Manager Data
     *
     * @param cidsMonitorConfigurationFindLocAndTyParamDTO
     * @return
     * @throws Exception
     */
    public ViewCidsMonitorMonitoringDTO findCidsMonitorMonitoringManagerData(CidsMonitorConfigurationFindLocAndTyParamDTO cidsMonitorConfigurationFindLocAndTyParamDTO) throws Exception;
}
