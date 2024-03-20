package com.urielsoft.cids.management.biz.service;

import com.urielsoft.cids.management.biz.dto.CreateUserManagementDTO;
import com.urielsoft.cids.management.biz.dto.UpdateUserManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewUserManagementDTO;
import com.urielsoft.cids.management.common.api.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * User Management Service
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */
public interface UserManagementService {
    /**
     * Create new User Management
     *
     * @param createUserManagementDTO
     * @throws Exception
     */
    public void createUserManagementData(CreateUserManagementDTO createUserManagementDTO) throws Exception;

    /**
     * View User Management Data
     *
     * @return
     * @throws Exception
     */
    public List<ViewUserManagementDTO> listAllUserManagementData() throws Exception;

    /**
     * Get User Management Data By userManagementId
     *
     * @param userManagementId
     * @return
     * @throws Exception
     */
    public ViewUserManagementDTO getUserManagementDataById(String userManagementId) throws Exception;

    /**
     * Get User Management Data By userManagementSeq
     *
     * @param userManagementSeq
     * @return
     * @throws Exception
     */
    public ViewUserManagementDTO getUserManagementDataBySeq(int userManagementSeq) throws Exception;

    /**
     * Update User Management Data
     *
     * @param updateuserManagementDTO
     * @throws Exception
     */
    public ResponseEntity<ApiResult> modifyUserManagementData(UpdateUserManagementDTO updateuserManagementDTO) throws Exception;

    /**
     * DELETE User Management
     *
     * @param userManagementSeq
     * @throws Exception
     */
    public ResponseEntity<ApiResult> removeUserManagementDataBySeq(int userManagementSeq) throws Exception;

    /**
     * Check checkDuplicate for userManagementId
     *
     * @param userManagementId
     * @return
     * @throws Exception
     */
    public ResponseEntity<ApiResult> checkDuplicateUserManagementById(@PathVariable(name = "userManagementId") String userManagementId) throws Exception;
}
