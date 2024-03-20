package com.urielsoft.cids.management.biz.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for managing monitors
 * @author trungNV (trungnv.bks@gmail.com)
 * @since 2023-07-20
 */

@Controller
@RequestMapping("/monitor-manager")
public class MonitorManagerController {

    /**
     * Monitor management page
     * @return
     * @throws Exception
     */
    @GetMapping
    public String monitorManagermentPage () throws Exception{
        return "/monitor-manager";
    }
}
