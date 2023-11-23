package com.lt.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成 dimens文件 目前声明生成：dp  sp
 * 项目中不使用该类
 */
public final class DimenInstaller {
    private static final int MAX_DP = 312;
    private static final int MAX_SP = 120;
    private static final String out_path = "d:/dimen_dp.xml";

    public static void main(String[] args) throws IOException {
        List<String> dp = createDP();
//        List<String> sp = createSP();
        write(dp);
    }

    public static List<String> createSP() {
        ArrayList<String> spList = new ArrayList<>(MAX_SP);
        for (int i = 0; i < MAX_DP; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("<dimen name=\"sp_");
            sb.append(i);
            sb.append("\">");
            sb.append(i).append("sp").append("</dimen>");
            spList.add(sb.toString());
        }
        return spList;
    }

    public static List<String> createDP() {
        ArrayList<String> dpList = new ArrayList<>(MAX_DP);
        for (int i = 0; i < MAX_DP * 2; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("<dimen name=\"dp_");
            sb.append(i);
            sb.append("\">");
            sb.append(i).append("dp").append("</dimen>");
            dpList.add(sb.toString());
        }
        return dpList;
    }

    public static void write(List<String> stringList) throws IOException {
        File fi = new File(out_path);
        fi.delete();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out_path)));
        bw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<resources>");
        for (String s : stringList) {
            bw.write(s);
            bw.newLine();
            bw.flush();
        }
        bw.write("</resources>");
        bw.flush();
        bw.close();
        System.out.println("写文件完毕");
    }
}
