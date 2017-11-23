package com.blue.spring.util.ftp;

import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ftpHandler implements ftpListener {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(ftpHandler.class);

    public ftpHandler() {

    }


    /**
     * 通知进度事件
     */
    public void processEvent(int process, long fileSize) {
        logger.info("process:{},fileSize:{}",process,fileSize);
        if (process >= 100) {
            //如果镜像成功了，
            //修改镜像进度

        }
    }
}