/**
 * @Company: 上海数慧系统技术有限公司
 * @Department: 数据中心
 * @Author: 郑家骜[ào]
 * @Email: zhengja@dist.com.cn
 * @Date: 2021-11-02 13:53
 * @Since:
 */
package com.zja;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 方式四：拦截器解决跨域
 * Cors 跨域拦截器
 */
@Slf4j
//@Component
public class CorsInterceptor implements HandlerInterceptor {

    //从上述配置文件中读出allowOrigins
    @Value("${config.allowOrigins}")
    private String allowOrigins;

    //正则匹配符集合
    private Set<Pattern> allowOriginPatterns;

    //根据配置初始化白名单的正则表达式
    @PostConstruct
    private void init() {
        allowOriginPatterns = new HashSet<>();

        log.debug("allowOrigins = {}", allowOrigins);

        if (ObjectUtils.isEmpty(allowOrigins)) {
            return;
        }

        String[] origins = allowOrigins.split(",");
        for (String origin : origins) {
            if (ObjectUtils.isEmpty(origin)) {
                continue;
            }
            //将开头第一个星号*替换为.*，将所有的点号配置为\.，方便做正则表达式匹配
            //由于在正在表达式中“.”和“*”都是特殊字符，因此需要转义
            origin = origin.trim().replace("\\.", "\\\\.").replace("*", ".*");
            allowOriginPatterns.add(Pattern.compile(origin));
        }

        log.debug("allowOriginPatterns = {}", allowOriginPatterns);

    }

    /**
     * 返回true则会继续执行拦截器链中的后续拦截器， 否则不往后执行后续拦截器。
     *
     * 详细说明：
     * 在业务处理器Ccontroller处理请求之前被调用。
     *
     * （1）按拦截器链中的顺序执行所有拦截器的preHandle()方法，直到所有拦截器执行完为止（或者到该方法返回false的拦截器为止）；
     * （2）然后执行被拦截的Controller。
     * （3）往回执行所有已执行过preHandle()方法的拦截器的postHandle()方法，与第（1）步中的执行方向相反。
     * （4）渲染ModelView（如果Controller返回ModelView，比如jsp页面），前后端分离的忽略该步骤。
     * （5）往回执行所有已执行过postHandle()方法的拦截器的afterCompletion()方法，与第（1）步中的执行方向相反。
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {

        log.debug(" cors processing begin ");
        //获取请求头中的 origin
        String headerOrigin = request.getHeader("Origin");
        log.debug("request url : {} , header origin : {}", request.getRequestURL(), headerOrigin);

        if (ObjectUtils.isEmpty(headerOrigin)) {
            return true;
        }

        for (Pattern pattern : allowOriginPatterns) {
            //白名单匹配
            if (pattern.matcher(headerOrigin).matches()) {
                log.debug("set '{}' to 'Access-Control-Allow-Origin' for response header ", headerOrigin);
                //允许跨域配置：http://www.ruanyifeng.com/blog/2016/04/cors.html
                //Access-Control-Allow-Origin:该字段是必须的。它的值要么是请求时Origin字段的值，要么是一个*，表示接受任意域名的请求。
                response.setHeader("Access-Control-Allow-Origin", headerOrigin);
                response.setHeader("Access-Control-Allow-Methods", "GET");
                //Access-Control-Allow-Credentials:该字段可选。它的值是一个布尔值，表示是否允许发送Cookie。默认情况下，Cookie不包括在CORS请求之中。设为true，即表示服务器明确许可，Cookie可以包含在请求中，一起发给服务器。这个值也只能设为true，如果服务器不要浏览器发送Cookie，删除该字段即可。
                response.setHeader("Access-Control-Allow-Credentials", "true");
                //response.setHeader("Access-Control-Max-Age", "3600");
                //response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
                break;
            }
        }

        log.debug(" cors processing end ");

        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.debug("postHandle()");
    }

    /**
     *
     * 在DispatcherServlet完全处理完请求后被调用，
     * 会从当前拦截器往回执行所有的拦截器的afterCompletion()
     *
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        log.debug("afterCompletion()");
    }

}
