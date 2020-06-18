package cn.com.test;

/**
 * @ClassName: JschUtils
 * @Description: TODO
 * @author: 55555
 * @date: 2020年06月17日 6:31 下午
 */
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class JschUtils {
    private static Logger logger = LoggerFactory.getLogger(JschUtils.class);


    public static ChannelSftp connectSftp(Session sshSession) {
        ChannelSftp sftp;
        try {
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            e.printStackTrace();
            sftp = null;
        }
        return sftp;
    }


    public static void exec(Session sshSession, String command) {
        ChannelExec channelExec = null;
        try {
            Channel channel = sshSession.openChannel("exec");
            channelExec = (ChannelExec) channel;
            channelExec.setCommand(command);
            channelExec.connect();
        } catch (Exception e) {
            logger.debug("{}", e);
            channelExec = null;
        } finally {
            channelExec.disconnect();
        }
    }


    /**
     * 上传文件
     *
     * @param directory  上传的目录
     * @param uploadFile 要上传的文件
     * @param sftp
     */
    public static String upload(String directory, String uploadFile, ChannelSftp sftp) {
        try {
            if (!directory.equals("")) {
                sftp.cd(directory);
            }
            File file = new File(uploadFile);
            sftp.put(new FileInputStream(file), file.getName());
            logger.debug("上传完成");
            sftp.disconnect();
            sftp.getSession().disconnect();
            return "传输成功";
        } catch (Exception e) {
            e.printStackTrace();
            sftp.disconnect();
            try {
                sftp.getSession().disconnect();
            } catch (JSchException e1) {
                e1.printStackTrace();
            }
            return "传输失败，请检查配置！";
        }
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     * @param sftp
     */
    public static void download(String directory, String downloadFile,
                         String saveFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
            sftp.disconnect();
            sftp.getSession().disconnect();
        } catch (Exception e) {
            sftp.disconnect();
            try {
                sftp.getSession().disconnect();
            } catch (JSchException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public static List<String> read(String directory, String readFile, ChannelSftp sftp, String charSetName) {
        List<String> stringlist = new ArrayList<>();
        InputStream inputStream = null;
        try {
            sftp.cd(directory);
            inputStream = sftp.get(readFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charSetName));
            String line = null;
            line = br.readLine();
            while (line != null) {
                stringlist.add(line);
                line = br.readLine(); // 一次读入一行数据
            }
            br.close();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringlist;
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @param sftp
     */
    public static void delete(String directory, String deleteFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @param sftp
     * @return
     * @throws SftpException
     */
    public static Vector listFiles(String directory, ChannelSftp sftp)
            throws SftpException {
        return sftp.ls(directory);
    }

    /**
     * 将输入流的数据上传到sftp作为文件
     *
     * @param directory    上传到该目录
     * @param sftpFileName sftp端文件名
     * @param input        输入流
     * @throws SftpException
     * @throws Exception
     */
    public static void upload(String directory, String sftpFileName, InputStream input, ChannelSftp sftp) throws SftpException, JSchException {
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            logger.warn("directory is not exist");
            sftp.mkdir(directory);
            sftp.cd(directory);
        }
        sftp.put(input, sftpFileName);
        if (sftp.isConnected()) {
            sftp.disconnect();
        }
        if (sftp.getSession().isConnected()) {
            sftp.getSession().disconnect();
        }
        logger.info("file:{" + sftpFileName + "} is upload successful");
    }
}