package com.urielsoft.cids.management.biz.mapper;

import com.urielsoft.cids.management.biz.dto.CreateUserManagementDTO;
import com.urielsoft.cids.management.biz.dto.UpdateUserManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewUserManagementDTO;
import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User Management Mapper.
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */
@Repository
@Db1Mapper
public interface UserManagementMapper {
    /**
     * Add new User Management
     *
     * @param createUserManagementDTO
     * @return
     * @throws DataAccessException
     */
    public int insertUserManagementData(CreateUserManagementDTO createUserManagementDTO) throws DataAccessException;

    /**
     * Select All Data of User Management from Database
     *
     * @return
     * @throws DataAccessException
     */
    public List<ViewUserManagementDTO> selectUserManagementDataAllList() throws DataAccessException;

    /**
     * Select Data Of User Management By seq
     *
     * @param userManagementSeq
     * @return
     * @throws DataAccessException
     */
    public ViewUserManagementDTO selectUserManagementDataBySeq(@Param("userManagementSeq") int userManagementSeq) throws DataAccessException;

    /**
     * @param userManagementId
     * @return
     * @throws DataAccessException
     */
    public ViewUserManagementDTO selectUserManagementDataById(@Param("userManagementId") String userManagementId) throws DataAccessException;

    /**
     * Update User Management Data
     *
     * @param updateUserManagementDTO
     * @return
     * @throws DataAccessException
     */
    public int updateUserManagementData(UpdateUserManagementDTO updateUserManagementDTO) throws DataAccessException;

    /**
     * DELETE User Managerment Data
     *
     * @param userManagementSeq
     * @return
     * @throws DataAccessException
     */
    public int deleteUserManagementDataBySeq(@Param("userManagementSeq") int userManagementSeq) throws DataAccessException;
}
