package com.urielsoft.cids.management.biz.controller.api;

import com.urielsoft.cids.management.biz.common.utils.DateTimeUtils;
import com.urielsoft.cids.management.biz.dto.CreateUserManagementDTO;
import com.urielsoft.cids.management.biz.dto.UpdateUserManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewUserManagementDTO;
import com.urielsoft.cids.management.biz.dto.api.param.CreateUserManagementParamDTO;
import com.urielsoft.cids.management.biz.dto.api.param.UpdateUserManagementParamDTO;
import com.urielsoft.cids.management.biz.dto.api.result.UserManagementDetailResultDTO;
import com.urielsoft.cids.management.biz.dto.api.result.UserManagementListItemResultDTO;
import com.urielsoft.cids.management.biz.mapper.UserManagementMapper;
import com.urielsoft.cids.management.biz.service.UserManagementService;
import com.urielsoft.cids.management.common.api.ApiResult;
import com.urielsoft.cids.management.common.security.LoginUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User Management Controller
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/user-management")
public class UserManagementApiController {
    /**
     * User Management Service
     */
    private final UserManagementService userManagementService;

    /**
     * Password Encoder
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * User Management Mapper
     */
    private final UserManagementMapper userManagementMapper;

    /**
     * Get User Management Data List
     *
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/list")
    public ResponseEntity<ApiResult> listUserManagementData() throws Exception {
        // Get All User Information
        List<ViewUserManagementDTO> dbList = this.userManagementService.listAllUserManagementData();
        // Use dbList to set Data for resultList
        List<UserManagementListItemResultDTO> resultList = CollectionUtils.emptyIfNull(dbList).stream().map(dbInfo -> {
            var userManagementListItemResultDTO = new UserManagementListItemResultDTO();

            userManagementListItemResultDTO.setUserNm(dbInfo.getUserNm());
            userManagementListItemResultDTO.setAuthTyNm(dbInfo.getAuthTyNm());
            userManagementListItemResultDTO.setUserId(dbInfo.getUserId());
            userManagementListItemResultDTO.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));
            userManagementListItemResultDTO.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getMdfyDt()));
            userManagementListItemResultDTO.setSeqNo(dbInfo.getSeqNo());
            userManagementListItemResultDTO.setAuthSeqNo(dbInfo.getAuthSeqNo());
            userManagementListItemResultDTO.setMdfyUserSeqNo(dbInfo.getMdfyUserSeqNo());

            return userManagementListItemResultDTO;
        }).collect(Collectors.toList());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultList);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Get Detail User Management Data by userManagementSeq
     *
     * @param userManagementSeq
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/detail/{userManagementSeq}")
    public ResponseEntity<ApiResult> detailUserData(@PathVariable("userManagementSeq") int userManagementSeq) throws Exception {
        // Get User Information By userManagementSeq
        ViewUserManagementDTO dbInfo = this.userManagementService.getUserManagementDataBySeq(userManagementSeq);
        //  Use dbInfo to set Data for resultInfo
        var resultInfo = new UserManagementDetailResultDTO();

        resultInfo.setUserNm(dbInfo.getUserNm());
        resultInfo.setUserId(dbInfo.getUserId());
        resultInfo.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));
        resultInfo.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getMdfyDt()));
        resultInfo.setSeqNo(dbInfo.getSeqNo());
        resultInfo.setAuthSeqNo(dbInfo.getAuthSeqNo());
        resultInfo.setAuthTyNm(dbInfo.getAuthTyNm());
        resultInfo.setMdfyUserSeqNo(dbInfo.getMdfyUserSeqNo());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultInfo);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Check Duplicate for userManagementId
     *
     * @param userManagementId
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/check-duplicate-user-management-id/{userManagementId}")
    public ResponseEntity<ApiResult> checkDuplicateUserManagementById(@PathVariable(name = "userManagementId") String userManagementId) throws Exception {
        return this.userManagementService.checkDuplicateUserManagementById(userManagementId);
    }

    /**
     * Create new User Management
     *
     * @param createUserManagementParamDTO
     * @return
     * @throws Exception
     */
    @PostMapping
    public ResponseEntity<ApiResult> createUserManagementData(@RequestBody @Validated CreateUserManagementParamDTO createUserManagementParamDTO) throws Exception {
        // Set createUserManagementParamDTO information to createUserManagementDTO
        var createUserManagementDTO = new CreateUserManagementDTO();

        createUserManagementDTO.setAuthSeqNo(createUserManagementParamDTO.getAuthSeqNo());
        createUserManagementDTO.setUserNm(createUserManagementParamDTO.getUserNm());
        createUserManagementDTO.setUserId(createUserManagementParamDTO.getUserId());
        createUserManagementDTO.setUserPw(this.passwordEncoder.encode(createUserManagementParamDTO.getUserPw()));

        // GET Infomation of current user login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();

        createUserManagementDTO.setMdfyUserSeqNo(userManagementMapper.selectUserManagementDataById(loginUserDetails.getUserId()).getSeqNo());

        this.userManagementService.createUserManagementData(createUserManagementDTO);

        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * Update User Management Data
     *
     * @param updateUserManagementParamDTO
     * @return
     * @throws Exception
     */
    @PutMapping
    public ResponseEntity<ApiResult> modifyUserData(@RequestBody @Validated UpdateUserManagementParamDTO updateUserManagementParamDTO) throws Exception {
        var updateUserManagementDTO = new UpdateUserManagementDTO();

        updateUserManagementDTO.setAuthSeqNo(updateUserManagementParamDTO.getAuthSeqNo());
        updateUserManagementDTO.setSeqNo(updateUserManagementParamDTO.getSeqNo());
        updateUserManagementDTO.setUserNm(updateUserManagementParamDTO.getUserNm());
        updateUserManagementDTO.setUserPw(updateUserManagementParamDTO.getUserPw());
        updateUserManagementDTO.setUserId(updateUserManagementParamDTO.getUserId());
        // Get Current User Login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();

        updateUserManagementDTO.setMdfyUserSeqNo(userManagementMapper.selectUserManagementDataById(loginUserDetails.getUserId()).getSeqNo());

        return this.userManagementService.modifyUserManagementData(updateUserManagementDTO);
    }

    /**
     * DELETE User Management Data By userManagementSeq
     *
     * @param userManagementSeq
     * @return
     * @throws Exception
     */
    @DeleteMapping(path = "/{userManagementSeq}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResult> removeUserData(@PathVariable("userManagementSeq") int userManagementSeq) throws Exception {
        return this.userManagementService.removeUserManagementDataBySeq(userManagementSeq);
    }
}
