package com.blue.spring.util.ftp;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ftpHandler implements ftpListener {

    public ftpHandler() {

    }


    /**
     * 通知进度事件
     */
    public void processEvent(int process, long fileSize) {
        System.out.println("进度=========：" + process);
        if (process >= 100) {
            //如果镜像成功了，
            //修改镜像进度

        }
    }
}