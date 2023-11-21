package com.lt.safe;

import com.sgcc.epri.iscp.ProxySocketImplFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpPortConvert {

    public static final String SAFETY_PLATFORM_IP = "127.0.0.1";

    /**
     * 将输入的地址转换成进行安全交互的地址
     * @param url x
     * @return x
     */
    public static String enter2Safety(String url){
        StringBuilder url_bak = new StringBuilder();
        if (url.contains("//")) {
            String[] splitTemp = url.split("//");
            url_bak = new StringBuilder(splitTemp[0] + "//");
            if (!splitTemp[1].contains(":")){
                return null;
            }
            String ip = splitTemp[1].substring(0, splitTemp[1].indexOf(":"));
            String[] splitTemp1 = splitTemp[1].split(":");
            if (splitTemp1.length < 2){
                return null;
            }
            int intport;
            if (splitTemp1[1].contains("/")) {
                String port = splitTemp1[1].substring(0, splitTemp1[1].indexOf("/"));
                intport = Integer.parseInt(port);
            } else {
                intport = Integer.parseInt(splitTemp1[1]);
            }
            int fport = -1;
            try {
                InetAddress inetAddress = InetAddress.getByName(ip);
                for (ProxySocketImplFactory proxySocketImplFactory : SafetyConnectManager.getInstance().getSocketProxyMap().values()) {
                    fport = proxySocketImplFactory.queryLocalPort(inetAddress, intport);
                    if (fport != -1) {
                        break;
                    }
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            if (fport == -1) {
                return null;
            }
            url_bak.append(SAFETY_PLATFORM_IP).append(":").append(fport);

            if (splitTemp[1].contains("/")) {
                String[] urlTemp2 = splitTemp[1].split("/");
                if (urlTemp2.length > 1) {
                    for (int i = 1; i < urlTemp2.length; i++) {
                        url_bak.append("/").append(urlTemp2[i]);
                    }
                }
            }
        }
        return url_bak.toString();
    }

}
