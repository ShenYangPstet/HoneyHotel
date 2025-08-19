package com.photonstudio.common;

import com.photonstudio.pojo.Appuser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    private static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        return requestAttributes.getRequest();
    }

    /**
     * 从请求头中获取database
     *
     * @return 此次访问的数据库名称
     */
    public static String getDatabase() {
        return getRequest().getHeader("database");
    }

    public static Appuser getAppuser(String dBname) {
        HttpSession session = getRequest().getSession();
        return (Appuser) session.getAttribute(dBname);
    }

    public static void setAppuser(String dBname, Appuser appuser) {
        HttpSession session = getRequest().getSession();
        if (appuser != null) {
            session.setAttribute(dBname, appuser);
            // session过期时间设置，以秒为单位，即在没有活动30分钟后，session将失效 //
            // session.setMaxInactiveInterval(30 * 60);
        }
    }

    public static void clear(String dBname) {
        HttpSession session = getRequest().getSession();
        session.removeAttribute(dBname);
    }

    public static void clearAll() {
        HttpSession session = getRequest().getSession();
        session.invalidate();
    }
}
