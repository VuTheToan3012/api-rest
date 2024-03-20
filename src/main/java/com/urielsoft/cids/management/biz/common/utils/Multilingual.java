//package com.urielsoft.cids.management.biz.common.utils;
//
//import com.urielsoft.cids.management.biz.dto.ViewMenuManagementDTO;
//import com.urielsoft.cids.management.biz.dto.ViewMultilingualManagementDTO;
//import com.urielsoft.cids.management.biz.service.MultilingualManagementService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.i18n.LocaleContextHolder;
//
//import java.util.List;
//import java.util.Locale;
///**
// *  Multilingual control
// *
// * @author Thangdt_bks (tdaotrong77@gmail.com)
// * @version 1.0
// * @since 2023-08-25
// */
//@Slf4j
//@RequiredArgsConstructor
//@Configuration
//public class Multilingual {
//
//    private static MultilingualManagementService multilingualManagementService;
//
//    @Bean(name = "menuMultilingualUtils")
//    public MenuTranslatedText multilingual() {
//        return (String tagId, List<ViewMenuManagementDTO> viewMenuManagementDTOS) -> {
//            Locale locale = LocaleContextHolder.getLocale();
//            ViewMenuManagementDTO viewMenuManagementDTO = viewMenuManagementDTOS.stream().filter(d -> d.getTagId().equals(tagId)).findFirst().orElse(new ViewMenuManagementDTO());
//            switch (locale.getLanguage()) {
//                case "en":
//                    return viewMenuManagementDTO.getMenuNmEng();
//                case "bn":
//                    return viewMenuManagementDTO.getMenuNmBen();
//                case "ko":
//                    return viewMenuManagementDTO.getMenuNmKor();
//                default:
//                    return "{"+tagId+"}";
//            }
//        };
//    }
//
//    @Bean(name = "multilingualUtils")
//    public ItemTranslatedText itemMultilingual() {
//        return (String tagId, List<ViewMultilingualManagementDTO> viewMultilingualManagementDTOS) -> {
//            Locale locale = LocaleContextHolder.getLocale();
//            ViewMultilingualManagementDTO viewMultilingualManagementDTO = viewMultilingualManagementDTOS.stream().filter(d -> d.getTagId().equals(tagId)).findFirst().orElse(new ViewMultilingualManagementDTO());
//            switch (locale.getLanguage()) {
//                case "en":
//                    return viewMultilingualManagementDTO.getItemNmEng();
//                case "bn":
//                    return viewMultilingualManagementDTO.getItemNmBen();
//                case "ko":
//                    return viewMultilingualManagementDTO.getItemNmKor();
//                default:
//                    return "{"+tagId+"}";
//            }
//        };
//    }
//}
//
