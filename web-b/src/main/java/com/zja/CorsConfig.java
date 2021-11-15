/**
 * @Company: 上海数慧系统技术有限公司
 * @Department: 数据中心
 * @Author: 郑家骜[ào]
 * @Email: zhengja@dist.com.cn
 * @Date: 2021-11-02 14:04
 * @Since:
 */
package com.zja;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;
import java.util.Arrays;

/**
 * 全局跨域配置
 */
@Configuration
public class CorsConfig {

    /**
     * 方式二：配置全局跨域
     */
    /*@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*")
//                        .allowedOrigins("*")
//                        .allowedOrigins("http://127.0.0.1:8081")   // allowCredentials=true 这时的allowedOrigins不能为 *，且必须填写,例：http://127.0.0.1:8081
                        .allowedOriginPatterns("*")   // allowCredentials=true 这时的allowedOrigins不能为 *，且必须填写,例：http://127.0.0.1:8081
                        .allowCredentials(true) // allowCredentials=true 允许携带Cookie, 且 使用 allowedOriginPatterns
                        .allowedMethods("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH")
                        .maxAge(3600);
            }
        };
    }*/

    /**
     * 方式三：过滤器解决跨域
     * 异常信息：
     * When allowCredentials is true, allowedOrigins cannot contain the special value "*" since that cannot be set on the "Access-Control-Allow-Origin" response header. To allow credentials to a set of origins, list them explicitly or consider using "allowedOriginPatterns" instead.
     * 当allowCredentials 为true 时， allowedOrigins 不能包含特殊值“*”，因为它不能在“Access-Control-Allow-Origin”响应头中设置。要允许一组来源的凭据，请明确列出它们或考虑改
     */
    /*@Bean
    public CorsFilter corsFilter() {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //放行哪些原始域
        //异常：当allowCredentials 为true 时， allowedOrigins 不能包含特殊值“*”，因为它不能在“Access-Control-Allow-Origin”响应头中设置。要允许一组来源的凭据，请明确列出它们或考虑改
//        config.addAllowedOrigin("*");
        config.addAllowedOriginPattern("*");
        //是否发送Cookie信息
        config.setAllowCredentials(true);
        //放行哪些原始域(请求方式)
        config.addAllowedMethod("*");
        //放行哪些原始域(头部信息)
        config.addAllowedHeader("*");
        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
        config.addExposedHeader("x-auth-token");
        //跨域允许时间
        config.setMaxAge(Duration.ofSeconds(3600));

        //2.添加映射路径
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        //3.返回新的CorsFilter.
        return new CorsFilter(configSource);
    }*/

    /**
     * 方式四
     */
    /*@Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //支持cookie 跨域
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setMaxAge(300L);//设置时间有效

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }*/

}
