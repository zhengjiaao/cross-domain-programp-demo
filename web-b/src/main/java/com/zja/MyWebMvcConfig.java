/**
 * @Company: 上海数慧系统技术有限公司
 * @Department: 数据中心
 * @Author: 郑家骜[ào]
 * @Email: zhengja@dist.com.cn
 * @Date: 2021-11-02 13:56
 * @Since:
 */
package com.zja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 方式四：拦截器解决跨域
 */
@Configuration
@EnableWebMvc
public class MyWebMvcConfig implements WebMvcConfigurer {

    /**
     * 配置跨域拦截器
     */
    /*@Autowired
    private CorsInterceptor corsInterceptor;
    */

    /**
     * 方式四：拦截器解决跨域
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //将CorsInterceptor拦截器添加进来
//        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(createCorsInterceptor()).addPathPatterns("/**");
    }

    /**
     * 创建跨域拦截器
     * @return CorsInterceptor
     */
    /*private CorsInterceptor createCorsInterceptor(){
        return new CorsInterceptor();
    }*/

}
