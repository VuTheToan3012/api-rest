package com.urielsoft.cids.management.biz.controller.api;

import com.urielsoft.cids.management.biz.common.utils.DateTimeUtils;
import com.urielsoft.cids.management.biz.dto.CreateMultilingualManagementDTO;
import com.urielsoft.cids.management.biz.dto.UpdateMultilingualManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewMultilingualManagementDTO;
import com.urielsoft.cids.management.biz.dto.api.param.CreateMultilingualManagementParamDTO;
import com.urielsoft.cids.management.biz.dto.api.param.UpdateMultilingualManagementParamDTO;
import com.urielsoft.cids.management.biz.dto.api.result.MultilingualManagementDetailResultDTO;
import com.urielsoft.cids.management.biz.dto.api.result.MultilingualManagementListItemResultDTO;
import com.urielsoft.cids.management.biz.service.MultilingualManagementService;
import com.urielsoft.cids.management.biz.service.UserManagementService;
import com.urielsoft.cids.management.common.api.ApiResult;
import com.urielsoft.cids.management.common.security.LoginUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MULTILINGUAL MANAGEMENT CONTROLLER
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-08-01
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/multilingual-management")
public class MultilingualManagementController {
    /**
     * Multilingual Management Service
     */
    private final MultilingualManagementService multilingualManagementService;


    /**
     * User Management Service
     */
    private final UserManagementService userManagementService;

    /**
     * Get All List Multilingual Management
     *
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/list")
    public ResponseEntity<ApiResult> listMultilingualManagementData() throws Exception {
        // Get All Monitor Information
        List<ViewMultilingualManagementDTO> dbList = this.multilingualManagementService.listAllMultilingualManagementData();
        // Use dbList to set Data for resultList
        List<MultilingualManagementListItemResultDTO> resultList = CollectionUtils.emptyIfNull(dbList).stream()
                .map(dbInfo -> {
                    var multilingualManagementListItemResultDTO = new MultilingualManagementListItemResultDTO();
                    multilingualManagementListItemResultDTO.setSeqNo(dbInfo.getSeqNo());
                    multilingualManagementListItemResultDTO.setItemNmBen(dbInfo.getItemNmBen());
                    multilingualManagementListItemResultDTO.setItemNmEng(dbInfo.getItemNmEng());
                    multilingualManagementListItemResultDTO.setItemNmKor(dbInfo.getItemNmKor());
                    multilingualManagementListItemResultDTO.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));
                    multilingualManagementListItemResultDTO.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getMdfyDt()));
                    multilingualManagementListItemResultDTO.setTagId(dbInfo.getTagId());
                    multilingualManagementListItemResultDTO.setMdfyUserSeqNO(dbInfo.getMdfyUserSeqNO());
                    multilingualManagementListItemResultDTO.setWkTySeqNo(dbInfo.getWkTySeqNo());
                    multilingualManagementListItemResultDTO.setLocTySeqNo(dbInfo.getLocTySeqNo());
                    multilingualManagementListItemResultDTO.setItemCategory(dbInfo.getItemCategory());

                    return multilingualManagementListItemResultDTO;
                }).collect(Collectors.toList());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultList);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Get Detail Multilingual Management Data by MultilingualManagementSeq
     *
     * @param multilingualManagementSeq
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/detail/{multilingualManagementSeq}")
    public ResponseEntity<ApiResult> detailMultilingualData(@PathVariable("multilingualManagementSeq") int multilingualManagementSeq) throws Exception {
        // Get User Information By userManagementSeq
        ViewMultilingualManagementDTO dbInfo = this.multilingualManagementService.getMultilingualManagementDataBySeq(multilingualManagementSeq);
        //  Use dbInfo to set Data for resultInfo
        var resultInfo = new MultilingualManagementDetailResultDTO();


        resultInfo.setItemNmBen(dbInfo.getItemNmBen());
        resultInfo.setItemNmEng(dbInfo.getItemNmEng());
        resultInfo.setItemNmKor(dbInfo.getItemNmKor());
        resultInfo.setSeqNo(dbInfo.getSeqNo());
        resultInfo.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));
        resultInfo.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getMdfyDt()));
        resultInfo.setTagId(dbInfo.getTagId());
        resultInfo.setMdfyUserSeqNO(dbInfo.getMdfyUserSeqNO());
        resultInfo.setWkTySeqNo(dbInfo.getWkTySeqNo());
        resultInfo.setLocTySeqNo(dbInfo.getLocTySeqNo());
        resultInfo.setItemCategory(dbInfo.getItemCategory());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultInfo);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Check Duplicate By TagId
     *
     * @param tagId
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/check-duplicate-tagId/{tagId}")
    public ResponseEntity<ApiResult> checkDuplicateByTagId(@PathVariable String tagId) throws Exception {
        return multilingualManagementService.getMultilingualManagementByTagId(tagId);
    }

    /**
     * Create New Multilingual Management
     *
     * @param createMultilingualManagementParamDTO
     * @return
     * @throws Exception
     */
    @PostMapping
    public ResponseEntity<ApiResult> createMultilingualManagementData(@RequestBody @Validated CreateMultilingualManagementParamDTO createMultilingualManagementParamDTO) throws Exception {

        var createMultilingualManagementDTO = new CreateMultilingualManagementDTO();

        createMultilingualManagementDTO.setItemNmBen(createMultilingualManagementParamDTO.getItemNmBen());
        createMultilingualManagementDTO.setItemNmEng(createMultilingualManagementParamDTO.getItemNmEng());
        createMultilingualManagementDTO.setItemNmKor(createMultilingualManagementParamDTO.getItemNmKor());
        createMultilingualManagementDTO.setTagId(createMultilingualManagementParamDTO.getTagId());
        // Get Loggin User Info
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();

        createMultilingualManagementDTO.setMdfyUserSeqNO(userManagementService.getUserManagementDataById(loginUserDetails.getUserId()).getSeqNo());

        this.multilingualManagementService.createMultilingualManagementData(createMultilingualManagementDTO);

        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * Update Multilingual Management Information
     *
     * @param updateMultilingualManagementParamDTO
     * @return
     * @throws Exception
     */
    @PutMapping
    public ResponseEntity<ApiResult> modifyMultilingualManagementData(@RequestBody @Validated UpdateMultilingualManagementParamDTO updateMultilingualManagementParamDTO) throws Exception {

        var updateMultilingualManagementDTO = new UpdateMultilingualManagementDTO();

        updateMultilingualManagementDTO.setSeqNo(updateMultilingualManagementParamDTO.getSeqNo());
        updateMultilingualManagementDTO.setItemNmBen(updateMultilingualManagementParamDTO.getItemNmBen());
        updateMultilingualManagementDTO.setItemNmEng(updateMultilingualManagementParamDTO.getItemNmEng());
        updateMultilingualManagementDTO.setItemNmKor(updateMultilingualManagementParamDTO.getItemNmKor());
        updateMultilingualManagementDTO.setTagId(updateMultilingualManagementParamDTO.getTagId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();

        updateMultilingualManagementDTO.setMdfyUserSeqNO(userManagementService.getUserManagementDataById(loginUserDetails.getUserId()).getSeqNo());

        this.multilingualManagementService.modifyMultilingualManagementData(updateMultilingualManagementDTO);

        var apiResult = new ApiResult(true);
        apiResult.setData(updateMultilingualManagementDTO);

        return ResponseEntity.ok(new ApiResult(true));
    }
}
