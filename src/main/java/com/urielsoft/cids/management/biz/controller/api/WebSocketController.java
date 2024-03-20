package com.urielsoft.cids.management.biz.controller.api;

import com.urielsoft.cids.management.biz.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WebSocket Controller
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-03
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/api/websocket")
public class WebSocketController {
    /**
     * WebSocket Service
     */
    private final WebSocketService webSocketService;

    /**
     * send Message
     *
     * @param message
     * @return
     */
    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) throws InterruptedException {
        return webSocketService.sendMessage(message);
    }

}
