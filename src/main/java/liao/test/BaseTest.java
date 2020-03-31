package liao.test;

import liao.utils.CommonUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by cheng on 2019/9/2.
 */
public class BaseTest {
    public void test() throws InvocationTargetException, IllegalAccessException, IOException {
        clearFile();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getInputFile())));
        System.out.println("启动完成请输入要测试的方法名称：");
        String methodName = null;
        while((methodName = readLine(reader)) != null) {
            if(methodName.isEmpty()){//空行忽略
                continue;
            }
            if(methodName.equals("##")){
                break;
            }
            Method method = getCallMethod(methodName.trim());
            if(method == null){
                System.out.println(System.lineSeparator()+System.lineSeparator()+System.lineSeparator()+"不存在的方法："+methodName);
                continue;
            }
            try {
                System.out.println(System.lineSeparator()+System.lineSeparator()+System.lineSeparator()+"开始调用方法："+methodName);
                method.invoke(this, null);
            }catch (Throwable t){
                t.printStackTrace();
            }
        }
    }

    public String readLine(BufferedReader reader){
        try{
            while (true) {
                String methodName = reader.readLine();
                if(CommonUtils.isEmpty(methodName)){
                    Thread.sleep(100);
                    continue;
                }

                System.out.println(methodName);
                if(methodName.equals("##")){
                    reader.close();
                    return methodName;
                }
                return methodName.trim();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "##";
    }
    private Method getCallMethod(String methodName){
        Method[] methods = this.getClass().getMethods();
        for(Method method : methods){
            if(method.getName().equals(methodName)){
                return method;
            }
        }
        return null;
    }

    protected String getInputFile(){
        String hostName = getHostName();
        if ("liao".equals(hostName)) {
            hostName = "cheng";
        }
        return "C:\\Users\\" + hostName + "\\Desktop\\input.log";
    }

    public static String getHostName() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new IllegalStateException(e);
        }
        String ip=addr.getHostAddress().toString(); //获取本机ip
        String hostName=addr.getHostName().toString(); //获取本机计算机名称
        System.out.println("本机IP："+ip+"\n本机名称:"+hostName);
        return hostName;
    }

    public void clearFile(){
        File file =new File(getInputFile());
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
