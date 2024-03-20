package com.urielsoft.cids.management.biz.service.impl;

import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.service.CidsLogService;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.biz.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * WebSocket Service Impl
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-03
 */
@Service
@RequiredArgsConstructor
public class WebSocketServiceImpl implements WebSocketService {

    /**
     * Standard WebSocket Client
     */
    private final StandardWebSocketClient client = new StandardWebSocketClient();

    /**
     * Usage History Service
     */
    private final UsageHistoryService usageHistoryService;

    /**
     * Cids Log Service
     */
    private final CidsLogService cidsLogService;

    /**
     * url
     */
    @Value("${websocket.url}")
    private String url;

    /**
     * response
     */
    private String response;

    /**
     * send Message
     *
     * @param message
     * @return
     */
    @Override
    public String sendMessage(String message) throws InterruptedException {
        String logId = this.cidsLogService.createNewLogId();
        CountDownLatch latch = new CountDownLatch(1);
            WebSocketHandler handler = new WebSocketHandler() {

                /**
                 * after Connection Established
                 * @param session
                 * @throws Exception
                 */
                @Override
                public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                    session.sendMessage(new TextMessage(message));
                }

                /**
                 * handle Message
                 * @param session
                 * @param message
                 * @throws Exception
                 */
                @Override
                public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                    response = message.getPayload().toString();
                    session.close();
                    latch.countDown();
                }

                /**
                 * handle Transport Error
                 * @param session
                 * @param exception
                 * @throws Exception
                 */
                @Override
                public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
                }

                /**
                 * after Connection Closed
                 * @param session
                 * @param closeStatus
                 * @throws Exception
                 */
                @Override
                public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                }

                /**
                 * supports Partial Messages
                 * @return
                 */
                @Override
                public boolean supportsPartialMessages() {
                    return false;
                }
            };

            client.doHandshake(handler, url);

            latch.await(10, TimeUnit.SECONDS); // wait for 10 seconds before closing the connection to allow for server response.

            return response;
    }
}
