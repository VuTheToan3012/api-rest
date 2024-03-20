package com.urielsoft.cids.management.biz.controller.api;

import com.urielsoft.cids.management.biz.common.utils.DateTimeUtils;
import com.urielsoft.cids.management.biz.dto.CreateCidsMonitorManagerDTO;
import com.urielsoft.cids.management.biz.dto.UpdateCidsMonitorManagerDTO;
import com.urielsoft.cids.management.biz.dto.ViewCidsMonitorManagerDTO;
import com.urielsoft.cids.management.biz.dto.api.param.CreateCidsMonitorManagerParamDTO;
import com.urielsoft.cids.management.biz.dto.api.param.ReadCidsMonitorManagerMonCmsNmDuplicateParamDTO;
import com.urielsoft.cids.management.biz.dto.api.param.ReadCidsMonitorManagerMonitorNameDuplicateParamDTO;
import com.urielsoft.cids.management.biz.dto.api.param.UpdateCidsMonitorManagerParamDTO;
import com.urielsoft.cids.management.biz.dto.api.result.CidsMonitorManagerDetailResultDTO;
import com.urielsoft.cids.management.biz.dto.api.result.CidsMonitorManagerListItemResultDTO;
import com.urielsoft.cids.management.biz.service.CidsMonitorManagerService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CIDS Monitor Manager CONTROLLER
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-20
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/cids-monitor-manager")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CidsMonitorManagerApiController {
    /**
     * Cids Monitor Manager Service
     */
    private final CidsMonitorManagerService cidsMonitorManagerService;

    /**
     * User Management Service
     */
    private final UserManagementService userManagementService;

    /**
     * Get All List Cids Monitor Manager
     *
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/list")
    public ResponseEntity<ApiResult> listCidsMonitorManagerData() throws Exception {
        // Get All Monitor Information
        List<ViewCidsMonitorManagerDTO> dbList = this.cidsMonitorManagerService.listAllCidsMonitorManagerData();
        // Use dbList to set Data for resultList
        List<CidsMonitorManagerListItemResultDTO> resultList = CollectionUtils.emptyIfNull(dbList).stream()
                .map(dbInfo -> {
                    var cidsMonitorManagerListItemResultDTO = new CidsMonitorManagerListItemResultDTO();

                    cidsMonitorManagerListItemResultDTO.setSeqNo(dbInfo.getSeqNo());
                    cidsMonitorManagerListItemResultDTO.setMonNm(dbInfo.getMonNm());
                    cidsMonitorManagerListItemResultDTO.setMonCmsNm(dbInfo.getMonCmsNm());
                    cidsMonitorManagerListItemResultDTO.setWkTyNm(dbInfo.getWkTyNm());
                    cidsMonitorManagerListItemResultDTO.setWkTySeqNo(dbInfo.getWkTySeqNo());
                    cidsMonitorManagerListItemResultDTO.setLocNm(dbInfo.getLocNm());
                    cidsMonitorManagerListItemResultDTO.setLocTySeqNo(dbInfo.getLocTySeqNo());
                    cidsMonitorManagerListItemResultDTO.setTmplatSeqNo(dbInfo.getTmplatSeqNo());
                    cidsMonitorManagerListItemResultDTO.setUsage(dbInfo.getUsage());
                    cidsMonitorManagerListItemResultDTO.setIp(dbInfo.getIp());
                    cidsMonitorManagerListItemResultDTO.setNote(dbInfo.getNote());
                    cidsMonitorManagerListItemResultDTO.setRegDt(dbInfo.getRegDt());
                    cidsMonitorManagerListItemResultDTO.setMdfyUserSeqNo(dbInfo.getMdfyUserSeqNo());
                    cidsMonitorManagerListItemResultDTO.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getMdfyDt()));
                    cidsMonitorManagerListItemResultDTO.setTmplatNm(dbInfo.getTmplatNm());
                    cidsMonitorManagerListItemResultDTO.setUserNm(dbInfo.getUserNm());

                    return cidsMonitorManagerListItemResultDTO;
                }).collect(Collectors.toList());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultList);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Get Monitor Manager By Seq
     *
     * @param cidsMonitorManagerSeq
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/detail/{cidsMonitorManagerSeq}")
    public ResponseEntity<ApiResult> detailCidsMonitorManagerData(@PathVariable("cidsMonitorManagerSeq") int cidsMonitorManagerSeq) throws Exception {
        // Get User Information By id
        ViewCidsMonitorManagerDTO dbInfo = this.cidsMonitorManagerService.getCidsMonitorManagerDataBySeq(cidsMonitorManagerSeq);
        //  Use dbInfo to set Data for resultInfo
        var resultInfo = new CidsMonitorManagerDetailResultDTO();

        resultInfo.setSeqNo(dbInfo.getSeqNo());
        resultInfo.setMonNm(dbInfo.getMonNm());
        resultInfo.setMonCmsNm(dbInfo.getMonCmsNm());
        resultInfo.setWkTyNm(dbInfo.getWkTyNm());
        resultInfo.setWkTySeqNo(dbInfo.getWkTySeqNo());
        resultInfo.setLocNm(dbInfo.getLocNm());
        resultInfo.setLocTySeqNo(dbInfo.getLocTySeqNo());
        resultInfo.setTmplatSeqNo(dbInfo.getTmplatSeqNo());
        resultInfo.setUsage(dbInfo.getUsage());
        resultInfo.setIp(dbInfo.getIp());
        resultInfo.setNote(dbInfo.getNote());
        resultInfo.setRegDt(dbInfo.getRegDt());
        resultInfo.setMdfyUserSeqNo(dbInfo.getMdfyUserSeqNo());
        resultInfo.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getMdfyDt()));

        resultInfo.setTmplatNm(dbInfo.getTmplatNm());
        resultInfo.setUserNm(dbInfo.getUserNm());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultInfo);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Check Duplicate For Monitor Name
     *
     * @param readCidsMonitorManagerMonitorNameDuplicateParamDTO
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/check-duplicate-monitor-name")
    public ResponseEntity<ApiResult> checkDuplicateMonitorName(@RequestBody ReadCidsMonitorManagerMonitorNameDuplicateParamDTO readCidsMonitorManagerMonitorNameDuplicateParamDTO) throws Exception {
        return cidsMonitorManagerService.CidsMonitorManagerCheckDuplicateForMonitorName(readCidsMonitorManagerMonitorNameDuplicateParamDTO);
    }

    /**
     * Check Duplicate For Ip
     *
     * @param cidsMonitorManagerIp
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/check-duplicate-ip/{cidsMonitorManagerIp}")
    public ResponseEntity<ApiResult> checkDuplicateIp(@PathVariable(name = "cidsMonitorManagerIp") String cidsMonitorManagerIp) throws Exception {
        return cidsMonitorManagerService.CidsMonitorManagerCheckDuplicateForIp(cidsMonitorManagerIp);
    }

    /**
     * Check Duplicate For Location Name
     *
     * @param cidsMonitorManagerLocNm
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/check-duplicate-loc-nm/{cidsMonitorManagerLocNm}")
    public ResponseEntity<ApiResult> checkDuplicateLocNm(@PathVariable(name = "cidsMonitorManagerLocNm") String cidsMonitorManagerLocNm) throws Exception {
        return cidsMonitorManagerService.CidsMonitorManagerCheckDuplicateForLocNm(cidsMonitorManagerLocNm);
    }

    /**
     * Check Duplicate For Monitor CMS Name
     *
     * @param readCidsMonitorManagerMonCmsNmDuplicateParamDTO
     * @return
     * @throws Exception
     */
    @PostMapping (path = "/check-duplicate-mon-cms-nm")
    public ResponseEntity<ApiResult> checkDuplicateMonCmsNm(@RequestBody ReadCidsMonitorManagerMonCmsNmDuplicateParamDTO readCidsMonitorManagerMonCmsNmDuplicateParamDTO) throws Exception {
        return cidsMonitorManagerService.CidsMonitorManagerCheckDuplicateForMonCmsNm(readCidsMonitorManagerMonCmsNmDuplicateParamDTO);
    }

    /**
     * Create new monitor
     *
     * @param createCidsMonitorManagerParamDTO
     * @return
     * @throws Exception
     */
    @PostMapping
    public ResponseEntity<ApiResult> createCidsMonitorManagerData(@RequestBody @Validated CreateCidsMonitorManagerParamDTO createCidsMonitorManagerParamDTO) throws Exception {
        // Set createUserParamDTO information to createUserDTO
        var createCidsMonitorManagerDTO = new CreateCidsMonitorManagerDTO();

        createCidsMonitorManagerDTO.setMonNm(createCidsMonitorManagerParamDTO.getMonNm());
        createCidsMonitorManagerDTO.setMonCmsNm(createCidsMonitorManagerParamDTO.getMonCmsNm());
        createCidsMonitorManagerDTO.setWkTySeqNo(createCidsMonitorManagerParamDTO.getWkTySeqNo());
        createCidsMonitorManagerDTO.setLocTySeqNo(createCidsMonitorManagerParamDTO.getLocTySeqNo());
        createCidsMonitorManagerDTO.setUsage(createCidsMonitorManagerParamDTO.getUsage());
        createCidsMonitorManagerDTO.setIp(createCidsMonitorManagerParamDTO.getIp());
        createCidsMonitorManagerDTO.setNote(createCidsMonitorManagerParamDTO.getNote());
        // Get Loggin User Info
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();

        createCidsMonitorManagerDTO.setMdfyUserSeqNo(userManagementService.getUserManagementDataById(loginUserDetails.getUserId()).getSeqNo());

        this.cidsMonitorManagerService.createCidsMonitorManagerData(createCidsMonitorManagerDTO);

        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * Update Monitor Manager Information
     *
     * @param updateCidsMonitorManagerParamDTO
     * @return
     * @throws Exception
     */
    @PutMapping
    public ResponseEntity<ApiResult> modifyCidsMonitorManagerData(@RequestBody @Validated UpdateCidsMonitorManagerParamDTO updateCidsMonitorManagerParamDTO) throws Exception {

        var updateCidsMonitorManagerDTO = new UpdateCidsMonitorManagerDTO();
        updateCidsMonitorManagerDTO.setSeqNo(updateCidsMonitorManagerParamDTO.getSeqNo());
        updateCidsMonitorManagerDTO.setMonNm(updateCidsMonitorManagerParamDTO.getMonNm());
        updateCidsMonitorManagerDTO.setMonCmsNm(updateCidsMonitorManagerParamDTO.getMonCmsNm());
        updateCidsMonitorManagerDTO.setWkTySeqNo(updateCidsMonitorManagerParamDTO.getWkTySeqNo());
        updateCidsMonitorManagerDTO.setLocTySeqNo(updateCidsMonitorManagerParamDTO.getLocTySeqNo());
        updateCidsMonitorManagerDTO.setUsage(updateCidsMonitorManagerParamDTO.getUsage());
        updateCidsMonitorManagerDTO.setIp(updateCidsMonitorManagerParamDTO.getIp());
        updateCidsMonitorManagerDTO.setNote(updateCidsMonitorManagerParamDTO.getNote());
        // Get Loggin User Info
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();

        updateCidsMonitorManagerDTO.setMdfyUserSeqNo(userManagementService.getUserManagementDataById(loginUserDetails.getUserId()).getSeqNo());

        this.cidsMonitorManagerService.modifyCidsMonitorManagerData(updateCidsMonitorManagerDTO);

        var apiResult = new ApiResult(true);
        apiResult.setData(updateCidsMonitorManagerDTO);

        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * DELETE Cids Monitor Manager Data By Seq
     *
     * @param cidsMonitorManagerSeq
     * @return
     * @throws Exception
     */
    @DeleteMapping(path = "/{cidsMonitorManagerSeq}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResult> removeCidsMonitorManagerData(@PathVariable("cidsMonitorManagerSeq") Integer cidsMonitorManagerSeq) throws Exception {
        this.cidsMonitorManagerService.removeCidsMonitorManagerDataBySeq(cidsMonitorManagerSeq);
        return ResponseEntity.ok(new ApiResult(true));
    }

}
