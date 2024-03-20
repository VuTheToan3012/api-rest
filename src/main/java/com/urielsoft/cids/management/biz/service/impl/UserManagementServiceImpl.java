package com.urielsoft.cids.management.biz.service.impl;

import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.dto.CreateUserManagementDTO;
import com.urielsoft.cids.management.biz.dto.UpdateUserManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewUserManagementDTO;
import com.urielsoft.cids.management.biz.mapper.UserManagementMapper;
import com.urielsoft.cids.management.biz.service.CidsLogService;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.biz.service.UserManagementService;
import com.urielsoft.cids.management.common.api.ApiError;
import com.urielsoft.cids.management.common.api.ApiResult;
import com.urielsoft.cids.management.common.exception.BizException;
import com.urielsoft.cids.management.common.exception.ExceptionCode;
import com.urielsoft.cids.management.common.mybatis.MyBatisResultHelper;
import com.urielsoft.cids.management.common.security.LoginUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User Management Services Impl
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {
    /**
     * Password Encoder
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * User Management Mapper
     */
    private final UserManagementMapper userManagementMapper;

    /**
     * Usage History Service
     */
    private final UsageHistoryService usageHistoryService;

    /**
     * Cids Log Service
     */
    private final CidsLogService cidsLogService;

    /**
     * Create New UserManagement Method
     *
     * @param createUserManagementDTO
     * @throws DataAccessException
     */
    @Transactional
    @Override
    public void createUserManagementData(CreateUserManagementDTO createUserManagementDTO) throws DataAccessException {
        String logId = this.cidsLogService.createNewLogId();

        try {
            int insertedRow = this.userManagementMapper.insertUserManagementData(createUserManagementDTO);
            MyBatisResultHelper.checkAffectedRow(insertedRow, 1);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.POST, "UserManagement");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.POST, "UserManagement");

            throw e;
        }
    }

    /**
     * Get All User Management Method
     *
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<ViewUserManagementDTO> listAllUserManagementData() throws DataAccessException {
        List<ViewUserManagementDTO> viewUserManagementDTOS = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewUserManagementDTOS = this.userManagementMapper.selectUserManagementDataAllList();

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETALl, "UserManagement");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETALl, "UserManagement");

            throw e;
        }
        return viewUserManagementDTOS;
    }

    /**
     * Get User Management By Id
     *
     * @param userManagementId
     * @return
     * @throws DataAccessException
     */
    @Override
    public ViewUserManagementDTO getUserManagementDataById(String userManagementId) throws DataAccessException {
        ViewUserManagementDTO viewUserManagementDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewUserManagementDTO = this.userManagementMapper.selectUserManagementDataById(userManagementId);
            if (viewUserManagementDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "GetUserManagementDataById");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "GetUserManagementDataById");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "GetUserManagementDataById");

            throw e;
        }
        return viewUserManagementDTO;
    }

    /**
     * Get User Management By Seq
     *
     * @param userManagementSeq
     * @return
     * @throws DataAccessException
     */
    @Override
    public ViewUserManagementDTO getUserManagementDataBySeq(int userManagementSeq) throws DataAccessException {
        ViewUserManagementDTO viewUserManagementDTO = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            viewUserManagementDTO = this.userManagementMapper.selectUserManagementDataBySeq(userManagementSeq);
            if (viewUserManagementDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "GetUserManagementDataBySeq");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "GetUserManagementDataBySeq");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "GetUserManagementDataBySeq");

            throw e;
        }
        return viewUserManagementDTO;
    }

    /**
     * Update User Management
     *
     * @param updateuserManagementDTO
     * @throws DataAccessException
     */
    @Override
    public ResponseEntity<ApiResult> modifyUserManagementData(UpdateUserManagementDTO updateuserManagementDTO) throws DataAccessException {
        String logId = this.cidsLogService.createNewLogId();

        try {
            ViewUserManagementDTO viewUserManagementDTO = userManagementMapper.selectUserManagementDataBySeq(updateuserManagementDTO.getSeqNo());
            if (viewUserManagementDTO == null) {
                this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "UserManagement");

                throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
            }
            System.out.println(updateuserManagementDTO.getUserPw());
            // Checking If we have new password value , set it to updateuserDTO
            if (updateuserManagementDTO.getUserPw() != null && !updateuserManagementDTO.getUserPw().isEmpty()) {

                updateuserManagementDTO.setUserPw(passwordEncoder.encode(updateuserManagementDTO.getUserPw()));
            }
            // Checking If we don't have new password value , set current passsword to updateuserDTO
            else {
                updateuserManagementDTO.setUserPw(viewUserManagementDTO.getUserPw());
            }
            // Check UserID, return EXCEPTION If null , Return Current USERID if not null
            ViewUserManagementDTO dbInfo = this.userManagementMapper.selectUserManagementDataById(updateuserManagementDTO.getUserId());
            if (dbInfo == null) {
                ApiResult<ApiError> apiResult = new ApiResult<>();
                apiResult.setSuccess(false);
                apiResult.setData(ApiError.of(ExceptionCode.BIZ_DATA_NOT_FOUND));
                return ResponseEntity.ok(apiResult);
            } else {
                updateuserManagementDTO.setUserId(dbInfo.getUserId());
            }

            int updatedRow = this.userManagementMapper.updateUserManagementData(updateuserManagementDTO);
            MyBatisResultHelper.checkAffectedRow(updatedRow, 1);

            var apiResult = new ApiResult(true);
            apiResult.setData(updateuserManagementDTO);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.PUT, "UserManagement");

            return ResponseEntity.ok(new ApiResult(true));
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.PUT, "UserManagement");

            throw e;
        }
    }

    /**
     * DELETE User Management
     *
     * @param userManagementSeq
     * @throws DataAccessException
     */
    @Transactional
    @Override
    public ResponseEntity<ApiResult> removeUserManagementDataBySeq(int userManagementSeq) throws DataAccessException {
        String logId = this.cidsLogService.createNewLogId();

        try {
            // GET Infomation of current user login
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();
            // Get  user  info by userSeq . if UserId of current login equal with UserId of userSeq param.
            // Return ERROR , If not : Delete Method
            ViewUserManagementDTO viewUserManagementDTO = userManagementMapper.selectUserManagementDataBySeq(userManagementSeq);
            if (viewUserManagementDTO != null) {
                if (loginUserDetails.getUserId().equals(viewUserManagementDTO.getUserId())) {
                    ApiResult<ApiError> apiResult = new ApiResult<>();
                    apiResult.setSuccess(false);
                    apiResult.setData(ApiError.of(ExceptionCode.BIZ_ERROR));

                    this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.DELETE, "UserManagement");

                    return ResponseEntity.ok(apiResult);
                } else {
                    int deletedRow = this.userManagementMapper.deleteUserManagementDataBySeq(userManagementSeq);
                    MyBatisResultHelper.checkAffectedRow(deletedRow, 1);

                    this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.DELETE, "UserManagement");

                    return ResponseEntity.ok(new ApiResult(true));
                }
            }
            ApiResult<ApiError> apiResult = new ApiResult<>();
            apiResult.setSuccess(false);
            apiResult.setData(ApiError.of(ExceptionCode.BIZ_DATA_NOT_FOUND));

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.DELETE, "UserManagement");

            return ResponseEntity.ok(apiResult);
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.DELETE, "UserManagement");

            throw e;
        }
    }

    /**
     * check Duplicate User Management Id
     *
     * @param userManagementId
     * @return
     * @throws DataAccessException
     */
    @Override
    public ResponseEntity<ApiResult> checkDuplicateUserManagementById(String userManagementId) throws DataAccessException {
        ViewUserManagementDTO dbInfo = null;
        String logId = this.cidsLogService.createNewLogId();

        try {
            // Get User Information By userId
            dbInfo = this.userManagementMapper.selectUserManagementDataById(userManagementId);

            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.INFO, CidsLogMethod.GETDetail, "CheckDuplicateUserManagementById");
        } catch (Exception e) {
            this.usageHistoryService.createOperationLog(logId, CidsLogLevelEnum.ERROR, CidsLogMethod.GETDetail, "CheckDuplicateUserManagementById");

            throw e;
        }

        // Return EXCEPTION when cannot find this User
        if (dbInfo == null) {
            return ResponseEntity.ok(new ApiResult(false));
        }
        return ResponseEntity.ok(new ApiResult(true));
    }
}