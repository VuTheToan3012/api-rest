package com.urielsoft.cids.management.biz.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * History page controller
 * @author trungNV (trungnv.bks@gmail.com)
 * @since 2023-07-26
 */

@Controller
@RequestMapping("/history")
public class HistoryController {

    /**
     * System log page
     * @return
     * @throws Exception
     */
    @GetMapping("/system-log")
    public String usageHistoryPage() throws Exception {
        return "/history/system-log";
    }

    /**
     * Monitor log page
     * @return
     * @throws Exception
     */
    @GetMapping("/monitor-log")
    public String errorHistoryPage() throws Exception {
        return "/history/monitor-log";
    }
}
