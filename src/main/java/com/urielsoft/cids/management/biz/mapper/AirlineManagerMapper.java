package com.urielsoft.cids.management.biz.mapper;

import com.urielsoft.cids.management.biz.dto.*;
import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Airline Manager Mapper
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-07-20
 */

@Repository
@Db1Mapper
public interface AirlineManagerMapper {
    /**
     * Select Airline Data All List
     *
     * @return
     * @throws DataAccessException
     */
    public List<ViewAirlineManagerDTO> selectAirlineDataAllList() throws DataAccessException;

    /**
     * Select Airline Data By AlCode3
     * @param alCode3
     * @return
     * @throws DataAccessException
     */
    public ViewAirlineManagerDTO selectAirlineDataByAlCode3(@Param("alCode3") String alCode3) throws DataAccessException;

    /**
     * Select Airline Data By AlCode
     * @param alCode
     * @return
     * @throws DataAccessException
     */
    public ViewAirlineManagerDTO selectAirlineDataByAlCode(@Param("alCode") String alCode) throws DataAccessException;

    /**
     * select Airline Data By AlCodea
     *
     * @param alCodea
     * @return
     * @throws DataAccessException
     */
    public ViewAirlineManagerDTO selectAirlineDataByAlCodea(@Param("alCodea") String alCodea) throws DataAccessException;

    /**
     * Insert Airline Data
     * @param createAirlineManagerDTO
     * @return
     * @throws DataAccessException
     */
    public int insertAirlineData(CreateAirlineManagerDTO createAirlineManagerDTO) throws DataAccessException;

     /**
     * Update Airline Data
     * @param updateAirlineManagerDTO
     * @return
     * @throws DataAccessException
     */
    public int updateAirlineData(UpdateAirlineManagerDTO updateAirlineManagerDTO) throws DataAccessException;

    /**
     * Select Airline Data By AirLine Seq
     *
     * @param seqNo
     * @return
     * @throws DataAccessException
     */
    public ViewAirlineManagerDTO selectAirlineDataByAirLineSeq(@Param("seqNo") int seqNo) throws DataAccessException;

    /**
     * Delete Airline Data By SeqNo
     *
     * @param seqNo
     * @return
     * @throws DataAccessException
     */
    public int deleteAirlineDataBySeqNo(@Param("seqNo") int seqNo) throws DataAccessException;
}
