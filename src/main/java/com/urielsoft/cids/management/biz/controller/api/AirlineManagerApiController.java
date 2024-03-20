package com.urielsoft.cids.management.biz.controller.api;


import com.urielsoft.cids.management.biz.common.utils.DateTimeUtils;
import com.urielsoft.cids.management.biz.dto.CreateAirlineManagerDTO;
import com.urielsoft.cids.management.biz.dto.UpdateAirlineManagerDTO;
import com.urielsoft.cids.management.biz.dto.ViewAirlineManagerDTO;
import com.urielsoft.cids.management.biz.dto.api.param.CreateAirlineManagerParamDTO;
import com.urielsoft.cids.management.biz.dto.api.param.UpdateAirlineManagerParamDTO;
import com.urielsoft.cids.management.biz.dto.api.result.AirlineManagerDetailResultDTO;
import com.urielsoft.cids.management.biz.dto.api.result.AirlineManagerListItemResultDTO;
import com.urielsoft.cids.management.biz.service.AirlineManagerService;
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
 * Airline Manager Api Controller
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-07-20
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/airline-manager")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AirlineManagerApiController {
    /**
     * Airline Manager Service
     */
    private final AirlineManagerService airlineManagerService;

    /**
     * User Management Service
     */
    private final UserManagementService userManagementService;

    /**
     * Get airline Data List
     *
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/list")
    public ResponseEntity<ApiResult> listAirlineData() throws Exception {
        // Lấy thông tin hàng không từ cơ sở dữ liệu
        List<ViewAirlineManagerDTO> dbList = this.airlineManagerService.listAllAirlineData();

        // Sử dụng dbList để tạo dữ liệu cho kết quả resultList
        List<AirlineManagerListItemResultDTO> resultList = CollectionUtils.emptyIfNull(dbList).stream()
                .map(dbInfo -> {
                    var airlineManagerListItemResultDTO = new AirlineManagerListItemResultDTO();

                    airlineManagerListItemResultDTO.setSeqNo(dbInfo.getSeqNo());
                    airlineManagerListItemResultDTO.setAlCode(dbInfo.getAlCode());
                    airlineManagerListItemResultDTO.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));
                    airlineManagerListItemResultDTO.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getMdfyDt()));
                    airlineManagerListItemResultDTO.setLogoImg(dbInfo.getLogoImg());

                    airlineManagerListItemResultDTO.setUsageYn(dbInfo.getUsageYn());

                    airlineManagerListItemResultDTO.setAlCodea(dbInfo.getAlCodea());
                    airlineManagerListItemResultDTO.setUserNm(dbInfo.getUserNm());
                    airlineManagerListItemResultDTO.setAlCode3(dbInfo.getAlCode3());
                    airlineManagerListItemResultDTO.setAlNm(dbInfo.getAlNm());
                    airlineManagerListItemResultDTO.setMdfyUserSeqNo(dbInfo.getMdfyUserSeqNo());

                    return airlineManagerListItemResultDTO;
                }).collect(Collectors.toList());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultList);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Get Detail Airline Data by airLineSeq
     *
     * @param airLineManagerSeq
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/detail/{airLineManagerSeq}")
    public ResponseEntity<ApiResult> detailUserData(@PathVariable("airLineManagerSeq") int airLineManagerSeq) throws Exception {
        // Get Information By airLineSeq
        ViewAirlineManagerDTO dbInfo = this.airlineManagerService.getAirlineDataByAirLineSeq(airLineManagerSeq);
        //  Airline dbInfo to set Data for resultInfo
        var resultInfo = new AirlineManagerDetailResultDTO();

        resultInfo.setUserNm(dbInfo.getUserNm());
        resultInfo.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));
        resultInfo.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getMdfyDt()));
        resultInfo.setSeqNo(dbInfo.getSeqNo());
        resultInfo.setAlNm(dbInfo.getAlNm());
        resultInfo.setAlCode(dbInfo.getAlCode());
        resultInfo.setAlCode3(dbInfo.getAlCode3());
        resultInfo.setLogoImg(dbInfo.getLogoImg());
        resultInfo.setAlCodea(dbInfo.getAlCodea());
        resultInfo.setUsageYn(dbInfo.getUsageYn());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultInfo);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Check Duplicate By AlCode3
     *
     * @param alCode3
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/check-duplicate-icaocode/{alCode3}")
    public ResponseEntity<ApiResult> checkDuplicateAirline(@PathVariable String alCode3) throws Exception {
        return airlineManagerService.getAirlineDataByAlCode3(alCode3);
    }

    /**
     * Check Duplicate By AlCode
     *
     * @param alCode
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/check-duplicate-iata2letter/{alCode}")
    public ResponseEntity<ApiResult> checkDuplicateAirlineByIata2Letter(@PathVariable String alCode) throws Exception {
        return airlineManagerService.getAirlineDataByAlCode(alCode);
    }

    /**
     * Check Duplicate By AlCodea
     *
     * @param alCodea
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/check-duplicate-iata3digit/{alCodea}")
    public ResponseEntity<ApiResult> checkDuplicateAirlineByIata3Digit(@PathVariable String alCodea) throws Exception {
        return airlineManagerService.getAirlineDataByAlCodea(alCodea);
    }


    /**
     * Create New Airline
     *
     * @param createAirlineManagerParamDTO
     * @return
     * @throws Exception
     */
    @PostMapping
    public ResponseEntity<ApiResult> createAirlineData(@RequestBody @Validated CreateAirlineManagerParamDTO createAirlineManagerParamDTO) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();
        // Set createUserParamDTO information to createUserDTO
        var createAirlineDTO = new CreateAirlineManagerDTO();

        createAirlineDTO.setAlNm(createAirlineManagerParamDTO.getAlNm());
        createAirlineDTO.setAlCode(createAirlineManagerParamDTO.getAlCode());
        createAirlineDTO.setAlCode3(createAirlineManagerParamDTO.getAlCode3());
        createAirlineDTO.setAlCodea(createAirlineManagerParamDTO.getAlCodea());
        createAirlineDTO.setLogoImg(createAirlineManagerParamDTO.getLogoImg());
        createAirlineDTO.setMdfyUserSeqNo(userManagementService.getUserManagementDataById(loginUserDetails.getUserId()).getSeqNo());
        //setUsageYn value yes(1) and no (0)
        createAirlineDTO.setUsageYn(createAirlineManagerParamDTO.getUsageYn());

        this.airlineManagerService.createAirlineData(createAirlineDTO);
        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * Update Airline Data
     *
     * @param updateAirlineManagerParamDTO
     * @return
     * @throws Exception
     */
    @PutMapping
    public ResponseEntity<ApiResult> modifyAirlineData(@RequestBody @Validated UpdateAirlineManagerParamDTO updateAirlineManagerParamDTO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();
        // Set updateAirline
        var updateAirlineDTO = new UpdateAirlineManagerDTO();
        updateAirlineDTO.setSeqNo(updateAirlineManagerParamDTO.getSeqNo());
        updateAirlineDTO.setAlCode(updateAirlineManagerParamDTO.getAlCode());
        updateAirlineDTO.setAlCode3(updateAirlineManagerParamDTO.getAlCode3());
        updateAirlineDTO.setAlCodea(updateAirlineManagerParamDTO.getAlCodea());
        updateAirlineDTO.setAlNm(updateAirlineManagerParamDTO.getAlNm());
        updateAirlineDTO.setLogoImg(updateAirlineManagerParamDTO.getLogoImg());
        updateAirlineDTO.setUsageYn(updateAirlineManagerParamDTO.getUsageYn());
        updateAirlineDTO.setMdfyUserSeqNo(userManagementService.getUserManagementDataById(loginUserDetails.getUserId()).getSeqNo());

        this.airlineManagerService.modifyAirlineData(updateAirlineDTO);

        var apiResult = new ApiResult(true);
        apiResult.setData(updateAirlineDTO);

        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * DELETE Airline Data By SeqNo
     *
     * @param airLineManagerSeq
     * @return
     * @throws Exception
     */
    @DeleteMapping(path = "/{airLineManagerSeq}")
    public ResponseEntity<ApiResult> removeAirlineData(@PathVariable("airLineManagerSeq") int airLineManagerSeq) throws Exception {
        this.airlineManagerService.removeAirlineDataBySeqNo(airLineManagerSeq);

        return ResponseEntity.ok(new ApiResult(true));
    }
}