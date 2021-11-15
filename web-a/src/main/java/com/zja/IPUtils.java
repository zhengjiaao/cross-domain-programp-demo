/**
 * @Company: 上海数慧系统技术有限公司
 * @Department: 数据中心
 * @Author: 郑家骜[ào]
 * @Email: zhengja@dist.com.cn
 * @Date: 2021-11-02 14:55
 * @Since:
 */
package com.zja;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取客户端真实ip地址的工具类方式，兼容代理与k8s集群获取Ip方式
 */
public class IPUtils {

    private static Logger logger = LoggerFactory.getLogger(IPUtils.class);
    private static final String IP_UTILS_FLAG = ",";
    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST_IP = "0:0:0:0:0:0:0:1";
    private static final String LOCALHOST_IP1 = "127.0.0.1";

    /**
     * 获取IP地址
     * <p>
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = null;
        try {
            //以下两个获取在k8s中，将真实的客户端IP，放到了x-Original-Forwarded-For。而将WAF的回源地址放到了 x-Forwarded-For了。
            ip = request.getHeader("X-Original-Forwarded-For");
            if (ObjectUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }
            //获取nginx等代理的ip
            if (ObjectUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("x-forwarded-for");
            }
            /*if (ObjectUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                //代理客户端真实IP，可能不是第一个客户端真实的IP
                ip = request.getHeader("X-Real-IP");
            }*/
            if (ObjectUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ObjectUtils.isEmpty(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ObjectUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ObjectUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            //兼容k8s集群获取ip
            if (ObjectUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (LOCALHOST_IP1.equalsIgnoreCase(ip) || LOCALHOST_IP.equalsIgnoreCase(ip)) {
                    //根据网卡取本机配置的IP
                    InetAddress iNet = null;
                    try {
                        iNet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        logger.error("getClientIp error: {}", e);
                    }
                    ip = iNet.getHostAddress();
                }
            }
        } catch (Exception e) {
            logger.error("IPUtils ERROR ", e);
        }

        //使用代理，则获取第一个IP地址
        if (!ObjectUtils.isEmpty(ip) && ip.indexOf(IP_UTILS_FLAG) > 0) {
            ip = ip.substring(0, ip.indexOf(IP_UTILS_FLAG));
        }

        return ip;
    }
}
