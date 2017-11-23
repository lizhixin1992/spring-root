package com.blue.spring.util.ftp;
public interface ftpListener {

	/**
	 * 通知进度事件
	 */
	public void processEvent(int process, long fileSize);
	
}