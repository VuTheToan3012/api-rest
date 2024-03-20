package com.urielsoft.cids.management.biz.service;

import com.urielsoft.cids.management.biz.dto.CreateAirlineManagerDTO;
import com.urielsoft.cids.management.biz.dto.UpdateAirlineManagerDTO;
import com.urielsoft.cids.management.biz.dto.ViewAirlineManagerDTO;
import com.urielsoft.cids.management.common.api.ApiResult;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Airline Manager Service
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-07-20
 */
public interface AirlineManagerService {
    /**
     * View Airline Data
     *
     * @return
     * @throws Exception
     */
    public List<ViewAirlineManagerDTO> listAllAirlineData() throws Exception;


    /**
     * Get Airline Data By AlCode3
     *
     * @param alCode3
     * @return
     * @throws Exception
     */
    public ResponseEntity<ApiResult> getAirlineDataByAlCode3(String alCode3) throws DataAccessException, Exception;

    /**
     * Get Airline Data By alCode
     *
     * @param alCode
     * @return
     * @throws Exception
     */
    public ResponseEntity<ApiResult> getAirlineDataByAlCode(String alCode) throws DataAccessException, Exception;

    /**
     * Get Airline Data By AlCodea
     *
     * @param alCodea
     * @return
     * @throws Exception
     */
    public ResponseEntity<ApiResult> getAirlineDataByAlCodea(String alCodea) throws DataAccessException, Exception;

    /**
     * Create Airline Data
     *
     * @param createAirlineManagerDTO
     * @throws Exception
     */
    public void createAirlineData(CreateAirlineManagerDTO createAirlineManagerDTO) throws Exception;

    /**
     * Get Airline Data By AirLineSeq
     *
     * @param airLineManagerSeq
     * @return
     * @throws Exception
     */
    public ViewAirlineManagerDTO getAirlineDataByAirLineSeq(int airLineManagerSeq) throws Exception;

    /**
     * Modify Airline Data
     *
     * @param updateAirlineManagerDTO
     * @throws Exception
     */
    public void modifyAirlineData(UpdateAirlineManagerDTO updateAirlineManagerDTO) throws Exception;

    /**
     * Remove Airline Data By SeqNo
     *
     * @param airLineManagerSeq
     * @throws Exception
     */
    public void removeAirlineDataBySeqNo(int airLineManagerSeq) throws Exception;
}
