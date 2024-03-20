package com.urielsoft.cids.management.biz.mapper;

import com.urielsoft.cids.management.biz.dto.CreateCidsMonitorManagerDTO;
import com.urielsoft.cids.management.biz.dto.UpdateCidsMonitorManagerDTO;
import com.urielsoft.cids.management.biz.dto.ViewCidsMonitorManagerDTO;
import com.urielsoft.cids.management.biz.dto.api.param.ReadCidsMonitorManagerMonCmsNmDuplicateParamDTO;
import com.urielsoft.cids.management.biz.dto.api.param.ReadCidsMonitorManagerMonitorNameDuplicateParamDTO;
import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Cids Monitor Manager Mapper
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-20
 */
@Repository
@Db1Mapper
public interface CidsMonitorManagerMapper {
    /**
     * Add New Monitor
     *
     * @param createCidsMonitorManagerDTO
     * @return
     * @throws DataAccessException
     */
    public int insertCidsMonitorManagerData(CreateCidsMonitorManagerDTO createCidsMonitorManagerDTO) throws DataAccessException;

    /**
     * Select All Data of Monitor from Database
     *
     * @return
     * @throws DataAccessException
     */
    public List<ViewCidsMonitorManagerDTO> selectCidsMonitorManagerDataAllList() throws DataAccessException;

    /**
     * Select Data Of Monitor By seqNo
     *
     * @param cidsMonitorManagerSeq
     * @return
     * @throws DataAccessException
     */
    public ViewCidsMonitorManagerDTO selectCidsMonitorManagerDataBySeq(@Param("cidsMonitorManagerSeq") int cidsMonitorManagerSeq) throws DataAccessException;

    /**
     * Update Monitor
     *
     * @param updateCidsMonitorManagerDTO
     * @return
     * @throws DataAccessException
     */
    public int updateCidsMonitorManagerData(UpdateCidsMonitorManagerDTO updateCidsMonitorManagerDTO) throws DataAccessException;

    /**
     * DELETE Monitor Data
     *
     * @param cidsMonitorManagerSeq
     * @return
     * @throws DataAccessException
     */
    public int deleteCidsMonitorManagerDataBySeq(@Param("cidsMonitorManagerSeq") int cidsMonitorManagerSeq) throws DataAccessException;

    /**
     * Check Duplicate For Monitor Name
     *
     * @param readCidsMonitorManagerMonitorNameDuplicateParamDTO
     * @return
     * @throws DataAccessException
     */
    public ViewCidsMonitorManagerDTO selectCidsMonitorManagerDataByMonitorName(@RequestBody ReadCidsMonitorManagerMonitorNameDuplicateParamDTO readCidsMonitorManagerMonitorNameDuplicateParamDTO) throws DataAccessException;

    /**
     * Check Duplicate for Ip
     *
     * @param cidsMonitorManagerIp
     * @return
     * @throws DataAccessException
     */
    public ViewCidsMonitorManagerDTO selectCidsMonitorManagerDataByIp(@Param("cidsMonitorManagerIp") String cidsMonitorManagerIp) throws DataAccessException;

    /**
     * Check Duplicate For Location Name
     *
     * @param cidsMonitorManagerLocNm
     * @return
     * @throws DataAccessException
     */
    public ViewCidsMonitorManagerDTO selectCidsMonitorManagerDataByLocNm(@Param("cidsMonitorManagerLocNm") String cidsMonitorManagerLocNm) throws DataAccessException;

    /**
     * Check Duplicate For Monitor CMS Name
     *
     * @param readCidsMonitorManagerMonCmsNmDuplicateParamDTO
     * @return
     * @throws DataAccessException
     */
    public ViewCidsMonitorManagerDTO selectCidsMonitorManagerDataByMonCmsNm(@RequestBody ReadCidsMonitorManagerMonCmsNmDuplicateParamDTO readCidsMonitorManagerMonCmsNmDuplicateParamDTO) throws DataAccessException;

}
