package com.urielsoft.cids.management.biz.controller.api;


import com.urielsoft.cids.management.biz.common.utils.DateTimeUtils;
import com.urielsoft.cids.management.biz.dto.CreateMenuManagementDTO;
import com.urielsoft.cids.management.biz.dto.UpdateMenuManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewMenuManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewUserManagementDTO;
import com.urielsoft.cids.management.biz.dto.api.param.CreateMenuManagementParamDTO;
import com.urielsoft.cids.management.biz.dto.api.param.UpdateMenuManagementParamDTO;
import com.urielsoft.cids.management.biz.dto.api.result.MenuManagementDetailResultDTO;
import com.urielsoft.cids.management.biz.dto.api.result.MenuManagementListItemResultDTO;
import com.urielsoft.cids.management.biz.service.MenuManagementService;
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
 * Menu Management Controller
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/menu-management")
public class MenuManagementController {
    /**
     * Menu Management Service
     */
    private final MenuManagementService menuManagementService;

    /**
     * User Management Service
     */
    private final UserManagementService userManagementService;

    /**
     * Get Menu Data List
     *
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/list")
    public ResponseEntity<ApiResult> listMenuData() throws Exception {
        // Get All User Information
        List<ViewMenuManagementDTO> dbList = this.menuManagementService.listAllMenuData();
        // Use dbList to set Data for resultList
        List<MenuManagementListItemResultDTO> resultList = CollectionUtils.emptyIfNull(dbList).stream()
                .map(dbInfo -> {
                    var menuManagementListItemResultDTO = new MenuManagementListItemResultDTO();

                    menuManagementListItemResultDTO.setSeqNo(dbInfo.getSeqNo());
                    menuManagementListItemResultDTO.setMenuNmEng(dbInfo.getMenuNmEng());
                    menuManagementListItemResultDTO.setMenuNmBen(dbInfo.getMenuNmBen());
                    menuManagementListItemResultDTO.setMenuNmKor(dbInfo.getMenuNmKor());
                    menuManagementListItemResultDTO.setTagId(dbInfo.getTagId());
                    menuManagementListItemResultDTO.setVwYn(dbInfo.getVwYn());
                    menuManagementListItemResultDTO.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));
                    menuManagementListItemResultDTO.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getMdfyDt()));
                    menuManagementListItemResultDTO.setMdfyUserSeqNo(dbInfo.getMdfyUserSeqNo());

                    return menuManagementListItemResultDTO;
                }).collect(Collectors.toList());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultList);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Get Detail Menu Data by seqNo
     *
     * @param seqNo
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/detail/{seqNo}")
    public ResponseEntity<ApiResult> detailMenuData(@PathVariable("seqNo") Integer seqNo) throws Exception {
        // Get Menu Information by Menu Name English
        ViewMenuManagementDTO dbInfo = this.menuManagementService.getMenuDataBySeqNo(seqNo);
        //  Use dbInfo to set Data for resultInfo
        var resultInfo = new MenuManagementDetailResultDTO();

        resultInfo.setSeqNo(dbInfo.getSeqNo());
        resultInfo.setMenuNmEng(dbInfo.getMenuNmEng());
        resultInfo.setMenuNmBen(dbInfo.getMenuNmBen());
        resultInfo.setMenuNmKor(dbInfo.getMenuNmKor());
        resultInfo.setTagId(dbInfo.getTagId());
        resultInfo.setVwYn(dbInfo.getVwYn());
        resultInfo.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));
        resultInfo.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getMdfyDt()));
        resultInfo.setMdfyUserSeqNo(dbInfo.getMdfyUserSeqNo());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultInfo);

        return ResponseEntity.ok(apiResult);
    }


    /**
     * Create new Menu
     *
     * @param createMenuManagementParamDTO
     * @return
     * @throws Exception
     */
    @PostMapping
    public ResponseEntity<ApiResult> createMenuData(@RequestBody @Validated CreateMenuManagementParamDTO createMenuManagementParamDTO) throws Exception {
        // Set createMenuParamDTO information to createMenuDTO
        var createMenuManagementDTO = new CreateMenuManagementDTO();
        // Get current user info
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();
        ViewUserManagementDTO viewUserManagementDTO = this.userManagementService.getUserManagementDataById(loginUserDetails.getUserId());

        createMenuManagementDTO.setMenuNmEng(createMenuManagementParamDTO.getMenuNmEng());
        createMenuManagementDTO.setMenuNmBen(createMenuManagementParamDTO.getMenuNmBen());
        createMenuManagementDTO.setMenuNmKor(createMenuManagementParamDTO.getMenuNmKor());
        createMenuManagementDTO.setTagId(createMenuManagementParamDTO.getTagId());
        createMenuManagementDTO.setVwYn(createMenuManagementParamDTO.getVwYn());

        createMenuManagementDTO.setMdfyUserSeqNo(viewUserManagementDTO.getSeqNo());

        this.menuManagementService.createMenuData(createMenuManagementDTO);

        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * Update Menu Data
     *
     * @param updateMenuManagementParamDTO
     * @return
     * @throws Exception
     */
    @PutMapping
    public ResponseEntity<ApiResult> modifyMenuData(@RequestBody @Validated UpdateMenuManagementParamDTO updateMenuManagementParamDTO) throws Exception {
        // Set updateMenuParamDTO information to updateMenuDTO
        var updateMenuManagementDTO = new UpdateMenuManagementDTO();
        // Get current user info
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();
        ViewUserManagementDTO viewUserManagementDTO = this.userManagementService.getUserManagementDataById(loginUserDetails.getUserId());

        updateMenuManagementDTO.setSeqNo(updateMenuManagementParamDTO.getSeqNo());
        updateMenuManagementDTO.setVwYn(updateMenuManagementParamDTO.getVwYn());
        updateMenuManagementDTO.setMenuNmBen(updateMenuManagementParamDTO.getMenuNmBen());
        updateMenuManagementDTO.setMenuNmKor(updateMenuManagementParamDTO.getMenuNmKor());

        updateMenuManagementDTO.setMdfyUserSeqNo(viewUserManagementDTO.getSeqNo());

        this.menuManagementService.modifyMenuData(updateMenuManagementDTO);

        var apiResult = new ApiResult(true);
        apiResult.setData(updateMenuManagementDTO);

        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * Check Duplicate For TagId
     *
     * @param menuManagementTagId
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/check-duplicate-tag-id/{menuManagementTagId}")
    public ResponseEntity<ApiResult> checkDuplicateTagId(@PathVariable(name = "menuManagementTagId") String menuManagementTagId) throws Exception {
        return menuManagementService.menuManagementCheckDuplicateForTagId(menuManagementTagId);
    }
}
