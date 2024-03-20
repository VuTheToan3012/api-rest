package com.urielsoft.cids.management.biz.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for managing observe the screens.
 * @author trungNV (trungnv.bks@gmail.com)
 * @since 2023-07-14
 */
@Controller
@RequestMapping("/cids-monitor")
public class CidsMonitorController {

    /**
     * Monitoring page
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("")
    public String monitoringPage (Model model) throws Exception {
        int[] array = new int[20];
        model.addAttribute("data", array);
        return "/monitor/monitoring";
    }

    /**
     * Detail monitoring page
     * @return
     */
    @GetMapping("/detail/{monitorId}")
    public String popupDetail(
            Model model,
            @PathVariable("monitorId") int monitorId
    ) {
        model.addAttribute("monitorId", monitorId);
        return "/monitor/monitor-detail";
    }

    @GetMapping("/configuration")
    public String monitorConfiguration(){
        return "/monitor/monitor-configuration";
    }
}
