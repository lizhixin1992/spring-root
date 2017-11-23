package com.blue.spring.util.ftp;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 支持断点续传的FTP实用类
 *
 * @version 0.3 实现中文目录创建及中文文件创建，添加对于中文的支持
 */
public class ftpUtil {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(ftpUtil.class);
    // 枚举类UploadStatus代码

    public enum UploadStatus {
        Create_Directory_Fail, // 远程服务器相应目录创建失败
        Create_Directory_Success, // 远程服务器闯将目录成功
        Upload_New_File_Success, // 上传新文件成功
        Upload_New_File_Failed, // 上传新文件失败
        File_Exits, // 文件已经存在
        Remote_Bigger_Local, // 远程文件大于本地文件
        Upload_From_Break_Success, // 断点续传成功
        Upload_From_Break_Failed, // 断点续传失败
        Delete_Remote_Faild; // 删除远程文件失败
    }

    // 枚举类DownloadStatus代码
    public enum DownloadStatus {
        Remote_File_Noexist, // 远程文件不存在
        Local_Bigger_Remote, // 本地文件大于远程文件
        Download_From_Break_Success, // 断点下载文件成功
        Download_From_Break_Failed, // 断点下载文件失败
        Download_New_Success, // 全新下载文件成功
        Download_New_Failed; // 全新下载文件失败
    }

    public FTPClient ftpClient = new FTPClient();
//	private String ftpURL, username, pwd, ftpport;

    public ftpUtil() {
        // 设置将过程中使用到的命令输出到控制台
        this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
    }

    /**
     * 连接到FTP服务器
     *
     * @param hostname 主机名
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @return 是否连接成功
     * @throws IOException
     */
    public boolean connect(String hostname, int port, String username, String password) {
        logger.info("******************************   连接到FTP服务器  *******************************start");
        logger.info("hostname:{},port:{},username:{},password:{}",hostname,port,username,password);
        try {
            ftpClient.connect(hostname, port);
            ftpClient.setControlEncoding("utf-8");
            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                if (ftpClient.login(username, password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            logger.error("连接到FTP服务器方法异常，异常信息:{}", e);
        }
        disconnect();
        logger.info("******************************  连接到FTP服务器   *******************************end");
        return false;
    }


    /**
     * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报
     *
     * @param remote 远程文件路径
     * @param local  本地文件路径
     * @return 上传的状态
     * @throws IOException
     */
    public DownloadStatus download(String remote, String local, ftpListener handler) throws IOException {
        logger.info("******************************  从FTP服务器上下载文件,支持断点续传，上传百分比汇报   *******************************start");
        logger.info("remote:{},local:{}",remote,local);
        DownloadStatus result = null;
        try {
            // 设置被动模式
            ftpClient.enterLocalPassiveMode();
            // 设置以二进制方式传输
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 检查远程文件是否存在
            FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("utf-8"), "iso-8859-1"));
            String[] filess = ftpClient.listNames(remote);
            logger.info("filess:{},files.length:{}", filess, files.length);
            if (files.length != 1) {
                logger.info("---------远程文件不存在----------" + files);
                return DownloadStatus.Remote_File_Noexist;
            }

            long lRemoteSize = files[0].getSize();
            File f = new File(local);
            // 本地存在文件，进行断点下载
            if (f.exists()) {
                long localSize = f.length();
                // 判断本地文件大小是否大于远程文件大小
                if (localSize >= lRemoteSize) {
                    logger.info("本地文件大于远程文件，下载中止");
                    return DownloadStatus.Local_Bigger_Remote;
                }

                // 进行断点续传，并记录状态
                FileOutputStream out = new FileOutputStream(f, true);
                ftpClient.setRestartOffset(localSize);
                InputStream in = ftpClient.retrieveFileStream(new String(remote.getBytes("utf-8"), "iso-8859-1"));
                byte[] bytes = new byte[1024];
                long step = lRemoteSize / 100;
                long process = localSize / step;
                int c;
                while ((c = in.read(bytes)) != -1) {
                    out.write(bytes, 0, c);
                    localSize += c;
                    long nowProcess = localSize / step;
                    if (nowProcess > process) {
                        process = nowProcess;
                        if (process % 10 == 0)
                            // TODO 更新文件下载进度,值存放在process变量中
                            handler.processEvent(new Long(process).intValue(), localSize);
                        logger.info("下载进度:{}", process);
                    }
                }
                in.close();
                out.close();
                boolean isDo = ftpClient.completePendingCommand();
                if (isDo) {
                    result = DownloadStatus.Download_From_Break_Success;
                } else {
                    result = DownloadStatus.Download_From_Break_Failed;
                }
            } else {
                OutputStream out = new FileOutputStream(f);
                InputStream in = ftpClient.retrieveFileStream(new String(remote.getBytes("utf-8"), "iso-8859-1"));
                byte[] bytes = new byte[1024];
                long step = lRemoteSize / 100;
                long process = 0;
                long localSize = 0L;
                int c;
                while ((c = in.read(bytes)) != -1) {
                    out.write(bytes, 0, c);
                    localSize += c;
                    long nowProcess = localSize / step;
                    if (nowProcess > process) {
                        // TODO 更新文件下载进度,值存放在process变量中
                        process = nowProcess;
                        if (process % 10 == 0)
                            logger.info("下载进度:{}", process);
                    }
                }
                in.close();
                out.close();
                boolean upNewStatus = ftpClient.completePendingCommand();
                if (upNewStatus) {
                    result = DownloadStatus.Download_New_Success;
                } else {
                    result = DownloadStatus.Download_New_Failed;
                }
            }
        } catch (IOException e) {
            logger.error("从FTP服务器上下载文件,支持断点续传，上传百分比汇报方法异常，异常信息:{}", e);
        }
        logger.info("******************************  从FTP服务器上下载文件,支持断点续传，上传百分比汇报   *******************************end");
        return result;
    }


    public UploadStatus upload(String local, String remote) throws IOException {
        return upload(local, remote, null);
    }

    /**
     * 上传文件到FTP服务器，支持断点续传
     *
     * @param local  本地文件名称，绝对路径
     * @param remote 远程文件路径，使用/home/directory1/subdirectory/file.ext或是
     *               http://www.guihua.org /subdirectory/file.ext
     *               按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
     * @return 上传结果
     * @throws IOException
     */
    public UploadStatus upload(String local, String remote, ftpListener handler){
        logger.info("******************************   上传文件到FTP服务器，支持断点续传  *******************************start");
        logger.info("remote:{},local:{}",remote,local);
        UploadStatus result = null;
        try {
            // 设置PassiveMode传输
            ftpClient.enterLocalPassiveMode();
            // 设置以二进制流的方式传输
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding("utf-8");
            // 对远程目录的处理
            String remoteFileName = remote;
            if (remote.contains("/")) {
                remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
                // 创建服务器远程目录结构，创建失败直接返回
                if (CreateDirecroty(remote, ftpClient) == UploadStatus.Create_Directory_Fail) {
                    return UploadStatus.Create_Directory_Fail;
                }
            }

            // 检查远程是否存在文件
            FTPFile[] files = ftpClient.listFiles(new String(remoteFileName.getBytes("utf-8"), "iso-8859-1"));
            logger.info("files.length:{}" + files.length);
            if (files.length == 1) {
                long remoteSize = files[0].getSize();
                File f = new File(local);
                long localSize = f.length();
                if (remoteSize == localSize) {
                    logger.info("---------文件已存在------------" + f);
                    long step = localSize / 100;
                    long process = 0;
                    process = remoteSize / step;
                    logger.info("---------文件进度------------" + process);
                    if (handler != null) {
                        handler.processEvent(new Long(process).intValue(), localSize);
                    }
                    return UploadStatus.File_Exits;
                } else if (remoteSize > localSize) {
                    return UploadStatus.Remote_Bigger_Local;
                }

                // 尝试移动文件内读取指针,实现断点续传
                logger.info("----------------开始长传--------------------");
                result = uploadFile(remoteFileName, f, ftpClient, remoteSize, handler);

                // 如果断点续传没有成功，则删除服务器上文件，重新上传
                if (result == UploadStatus.Upload_From_Break_Failed) {
                    if (!ftpClient.deleteFile(remoteFileName)) {
                        return UploadStatus.Delete_Remote_Faild;
                    }
                    result = uploadFile(remoteFileName, f, ftpClient, 0, handler);
                }
            } else {
                result = uploadFile(remoteFileName, new File(local), ftpClient, 0, handler);
            }
        } catch (IOException e) {
            logger.error("上传文件到FTP服务器，支持断点续传方法异常，异常信息:{}", e);
        }
        logger.info("******************************   上传文件到FTP服务器，支持断点续传  *******************************end");
        return result;
    }

    /** */
    /**
     * 断开与远程服务器的连接
     *
     * @throws IOException
     */
    public void disconnect() {
        logger.info("******************************  断开与远程服务器的连接   *******************************start");
        try {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            logger.error("断开与远程服务器的连接方法异常，异常信息:{}", e);
        }
        logger.info("******************************  断开与远程服务器的连接   *******************************end");
    }

    /**
     * 递归创建远程服务器目录
     *
     * @param remote    远程服务器文件绝对路径
     * @param ftpClient FTPClient 对象
     * @return 目录创建是否成功
     * @throws IOException
     */
    public UploadStatus CreateDirecroty(String remote, FTPClient ftpClient){
        logger.info("******************************  递归创建远程服务器目录   *******************************start");
        logger.info("remote:{}",remote);
        UploadStatus status = UploadStatus.Create_Directory_Success;
        try {
            String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
            if (!directory.equalsIgnoreCase("/")
                    && !ftpClient.changeWorkingDirectory(new String(directory.getBytes("utf-8"), "iso-8859-1"))) {
                // 如果远程目录不存在，则递归创建远程服务器目录
                int start = 0;
                int end = 0;
                if (directory.startsWith("/")) {
                    start = 1;
                } else {
                    start = 0;
                }
                end = directory.indexOf("/", start);
                while (true) {
                    String subDirectory = new String(remote.substring(start, end).getBytes("utf-8"), "iso-8859-1");
                    if (!ftpClient.changeWorkingDirectory(subDirectory)) {
                        if (ftpClient.makeDirectory(subDirectory)) {
                            ftpClient.changeWorkingDirectory(subDirectory);
                        } else {
                            logger.error("----------------创建目录失败-------------------");
                            return UploadStatus.Create_Directory_Fail;
                        }
                    }

                    start = end + 1;
                    end = directory.indexOf("/", start);

                    // 检查所有目录是否创建完毕
                    if (end <= start) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            logger.error("递归创建远程服务器目录方法异常，异常信息:{},",e);
        }
        logger.info("******************************  递归创建远程服务器目录   *******************************end");
        return status;
    }

    /** */
    /**
     * 上传文件到服务器,新上传和断点续传
     *
     * @param remoteFile  远程文件名，在上传之前已经将服务器工作目录做了改变
     * @param localFile   本地文件 File句柄，绝对路径
     * @param processStep 需要显示的处理进度步进值
     * @param ftpClient   FTPClient 引用
     * @return
     * @throws IOException
     */
    public UploadStatus uploadFile(String remoteFile, File localFile, FTPClient ftpClient, long remoteSize, ftpListener handler) {
        logger.info("******************************  上传文件到服务器,新上传和断点续传   *******************************start");
        logger.info("remoteFile:{},remoteSize:{}",remoteFile,remoteSize);
        UploadStatus status = null;
        try {
            // 显示进度的上传
            long step = localFile.length() / 100;
            long process = 0;
            long localreadbytes = 0L;
            RandomAccessFile raf = new RandomAccessFile(localFile, "r");
            OutputStream out = ftpClient.appendFileStream(new String(remoteFile.getBytes("utf-8"), "iso-8859-1"));
            // 断点续传
            logger.info("远端文件大小:{}", remoteSize);
            if (remoteSize > 0) {
                logger.info("---------------------远端存在该文件，启动断点续传------------------");
                ftpClient.setRestartOffset(remoteSize);
                process = remoteSize / step;
                raf.seek(remoteSize);
                localreadbytes = remoteSize;
            }
            byte[] bytes = new byte[1024];
            int c;
            logger.info("--------------进入流传送--------------");
            while ((c = raf.read(bytes)) != -1) {
                out.write(bytes, 0, c);
                localreadbytes += c;
                if (localreadbytes / step != process) {
                    process = localreadbytes / step;
                    if (handler != null)
                        handler.processEvent(new Long(process).intValue(), localreadbytes);
                    logger.info("已传输百分比:{}",process);
                }
            }
            out.flush();
            raf.close();
            out.close();
            boolean result = ftpClient.completePendingCommand();
            if (remoteSize > 0) {
                status = result ? UploadStatus.Upload_From_Break_Success : UploadStatus.Upload_From_Break_Failed;
            } else {
                status = result ? UploadStatus.Upload_New_File_Success : UploadStatus.Upload_New_File_Failed;
            }
        } catch (Exception e) {
            logger.error("上传文件到服务器,新上传和断点续传异常，异常信息:{},",e);
        }
        logger.info("******************************  上传文件到服务器,新上传和断点续传   *******************************end");
        return status;
    }

    /**
     *@description: 删除方法
     *@params:
     *@author: lizhixin
     *@createDate: 11:06 2017/11/23
    */
    public void deleteFile(String path) {
        logger.info("******************************  删除方法   *******************************start");
        logger.info("path:{}",path);
        try {
            ftpClient.deleteFile(path);
        } catch (Exception e) {
            logger.error("删除方法异常，异常信息:{},",e);
        }
        logger.info("******************************  删除方法   *******************************end");
    }

    /**
     * 判断FTP服务器上的文件大小和传的文件大小是否一致
     *
     * @param ftpClient
     * @param path
     * @param fileSize
     * @return
     * @throws Exception
     * @author lizhixin
     * @createDate 2017年7月17日
     */
    public boolean selectFile(String path, Long fileSize){
        logger.info("******************************  断FTP服务器上的文件大小和传的文件大小是否一致   *******************************start");
        logger.info("path:{},fileSize:{}",path,fileSize);
        boolean flag = false;
        try {
            FTPFile[] files = ftpClient.listFiles(new String(path.getBytes("utf-8"), "iso-8859-1"));
            logger.info("files.length。。。。。。。" + files.length);
            if (files.length == 1) {
                long ftpFileSize = files[0].getSize();
                logger.info("ftpFileSize=====" + ftpFileSize + "；fileSize=====" + fileSize);
                if (ftpFileSize == fileSize) {
                    logger.info("FTP上的文件大小和传的文件大小相等");
                    flag = true;
                } else {
                    logger.info("FTP上的文件大小和传的文件大小不相等");
                }
            } else {
                logger.info("FTP获取文件出错！");
            }
        } catch (IOException e) {
            logger.error("判断FTP服务器上的文件大小和传的文件大小是否一致方法异常，异常信息:{},",e);
        }
        logger.info("******************************  断FTP服务器上的文件大小和传的文件大小是否一致   *******************************end");
        return false;
    }

    /**
     *@description: 判断文件是否存在
     *@params:
     *@author: lizhixin
     *@createDate: 11:08 2017/11/23
    */
    public boolean fileIsExist(String path) {
        logger.info("******************************  判断文件是否存在   *******************************start");
        logger.info("path:{}",path);
        boolean flag = false;
        try {
            FTPFile[] listFiles = ftpClient.listFiles(new String(path.getBytes("utf-8"), "iso-8859-1"));
            logger.info("files.length。。。。。。。" + listFiles.length);
            if (listFiles.length == 1) {
                flag = true;
            }
        } catch (Exception e) {
            logger.error("判断文件是否存在方法异常，异常信息:{},",e);
        }
        logger.info("******************************  判断文件是否存在   *******************************end");
        return flag;
    }


    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

//		try {
//			String ftpURL = "114.112.71.214";
//			String ftpport = "21";
//			String username = "ftp";
//			String pwd = "7$DMmUOT";
//			
//			FtpUtil ftp = new FtpUtil();
//			ftp.connect(ftpURL, new java.lang.Integer(ftpport), username, pwd);
//			String file1 = "/IN/testin/素材/2.jpg";
//			String file2 = "D:\\f.jpg";
////			ftp.download(file1, file2);
//			TMaterialFile t = new TMaterialFile();
//			FtpHandler handler = new FtpHandler(t);
//			//ftp.upload(file2, file1,handler);
//			ftp.download(file1, file2,handler);
//			//System.out.println(myFtp.upload("c:\\a.iso", "/a.iso"));
//			ftp.disconnect();
//		} catch (IOException e) {
//			System.out.println("连接FTP出错：" + e.getMessage());
//		}

//		String ftpURL = "192.168.20.102";
//		String ftpport = "21";
//		String username = "ftp";
//		String pwd = "7$DMmUOT";
//		
//		FtpUtil ftp = new FtpUtil();
//		boolean connect = ftp.connect(ftpURL, new java.lang.Integer(ftpport), username, pwd);
//		System.out.println("connect======================" + connect);


    }

}
