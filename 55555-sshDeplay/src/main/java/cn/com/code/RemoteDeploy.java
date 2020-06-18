package cn.com.code;

import com.jcraft.jsch.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: RemoteDeploy
 * @Description: TODO
 * @author: 55555
 * @date: 2020年06月17日 10:16 下午
 */
@Mojo(name="remoteDeploy", defaultPhase= LifecyclePhase.PACKAGE)
public class RemoteDeploy extends AbstractMojo {



    /**
     * 本地全路径
     */
    @Parameter
    private String fullClientName;



    @Parameter
    private List<Map> serverDeploy;

    private static final String HOST = "host";
    private static final String PORT = "port";
    private static final String USER = "user";
    private static final String PASS = "pass";
    private static final String DEPLOY_URL = "deployUrl";
    private static final String JAVA_HOME = "javaHome";



    public void execute() throws MojoExecutionException, MojoFailureException {

        if(serverDeploy == null || serverDeploy.isEmpty()){
            System.err.println("serverDeploy is empty!");
        }
        for (int i = 0; i < serverDeploy.size(); i++) {

            final Map server = serverDeploy.get(0);
            final Session session = getSession(server);
            //获取服务器存储文件路径
            final String deployUrl = server.get(DEPLOY_URL).toString();
            final String jarName = fullClientName.substring(fullClientName.lastIndexOf("/") + 1);

            //上传文件
            upload(deployUrl, fullClientName, session);
            System.out.println("start jar : " + jarName);
            //启动jar
            startJar(server, session, jarName);
            try {
                String packageName = jarName.substring(0, jarName.indexOf("."));
                printLog(session, deployUrl, packageName);
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取会话
     * @param server
     * @return
     */
    private Session getSession(Map server){
        if(server == null){
            System.err.println("serverDeploy is empty");
        }


        JSch jSch = new JSch();
        Session session = null;
        try {
            final String user = server.get(USER).toString();
            final String host = server.get(HOST).toString();
            final int port = Integer.parseInt(server.get(PORT).toString());
            session = jSch.getSession(user, host, port);
            session.setPassword(server.get(PASS).toString());
            session.setConfig("StrictHostKeyChecking", "no");
            //测试连接
            session.connect(3000);

        } catch (JSchException e) {
            System.err.println("session create error! Please check the serverUrl configuration !");
            e.printStackTrace();
        }
        return session;
    }


    /**
     * 上传文件
     *
     * @param directory  上传的目录
     * @param uploadFile 要上传的文件
     * @param session
     */
    public static void upload(String directory, String uploadFile, Session session) {
        try {
            System.out.println("copy jar : " + uploadFile + " to " + directory);
            final ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect(3000);
            if (!directory.equals("")) {
                sftp.cd(directory);
            }
            File file = new File(uploadFile);
            sftp.put(new FileInputStream(file), file.getName());
            sftp.disconnect();
        } catch (Exception e) {
            System.err.println("File upload failed!");
            e.printStackTrace();
        }
    }

    /**
     * 启动脚本
     * @param server
     * @param session
     */
    public static void startJar(Map<String, String> server, Session session, String jarName){
        ChannelExec openChannel = null;
        String result = "";
        try {

            openChannel = (ChannelExec) session.openChannel("exec");
            String packageName = jarName.substring(0, jarName.indexOf("."));
            String s = server.get(JAVA_HOME);
            if(StringUtils.isBlank(s)){
                System.err.println("javaHome is empty");
            }
            if(!s.endsWith("/")){
                s += "/";
            }
            String command =
                    "export PATH=$PATH:" + server.get(JAVA_HOME) + "\n" +
                    "cd " + server.get(DEPLOY_URL) + "\n" +
                    "echo " + jarName +"\n" +
                    "  if [ ! -d \"../ " + packageName + "\" ]; then\n" +
                    "    mkdir \"../" + packageName + "\"\n" +
                    "  fi\n" +
                    "  ps -ef | grep \"" + jarName + "\" | grep -v grep | awk '{print $2}' | xargs kill -9\n" +
                    "  cp -f " + jarName + " ../" + packageName + "/" + jarName + "\n" +
                    " cd ../" + packageName + "\n" +
                    "nohup "+ s +"bin/java -jar -Dfile.encoding=UTF-8 -Xmx512m " + jarName + " --spring.profiles.active=test > output & \n/n";
            System.out.println("exec command : \n" + command);
            openChannel.setCommand(command);
            openChannel.connect();

            InputStream in = openChannel.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            String buf = null;
            while ((buf = reader.readLine()) != null) {
                result += " " + buf;
            }
            System.out.println("command result : " + result);
            return;
        } catch (JSchException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(openChannel != null){
                openChannel.disconnect();
            }
        }
    }

    /**
     * 打印日志
     * @param session
     * @throws JSchException
     * @throws SftpException
     * @throws IOException
     * @throws InterruptedException
     */
    private static void printLog(Session session, String deployUrl, String packageName) throws JSchException, SftpException, IOException, InterruptedException {
        System.out.println("--------------------wait log -------------------------");

        final ChannelSftp sftp = (ChannelSftp)session.openChannel("sftp");
        sftp.connect(3000);
        InputStream stream;
        String log = "";
        int lastTimeFileSize = 0;
        final String logPreFix = deployUrl.substring(0, deployUrl.lastIndexOf("/"));
        final String logFile = logPreFix + "/" + packageName + "/output";
        System.out.println("logFilePath : " + logFile);
        try {
            while (true){

                stream = sftp.get(logFile);
                final String string = getString(stream);
                final String substring = string.substring(lastTimeFileSize);
                if(!StringUtils.isBlank(substring)){
                    System.out.println(substring);
                    if(substring.contains("系统启动成功")){
                        break;
                    }
                }
                lastTimeFileSize = string.length();
                stream.close();
                Thread.sleep(300);
            }

        } finally {
        }
    }


    public static String getString(InputStream inputStream){
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = null;
        try {
            in = new InputStreamReader(inputStream, "UTF-8");

        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toString();
    }
}
