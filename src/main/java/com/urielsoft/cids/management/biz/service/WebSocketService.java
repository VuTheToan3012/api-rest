package com.urielsoft.cids.management.biz.service;

/**
 * WebSocket Service
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-03
 */
public interface WebSocketService {
    /**
     * send Message
     *
     * @param message
     * @return
     */
    String sendMessage(String message) throws InterruptedException;
}

