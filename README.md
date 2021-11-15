# 跨域研究

### Nginx 解决跨域

```python

#方案一：

http {

	# nginx代理后跨域，增加下面3行解决跨域
    add_header Access-Control-Allow-Origin *;
    add_header Access-Control-Allow-Headers X-Requested-With;
    add_header Access-Control-Allow-Methods GET,POST,OPTIONS,DELETE;

    server {
        listen       18080;
        server_name  localhost;
        
		# 请求允许携带token 默认 off
		underscores_in_headers on;
        
    	# 本地环境-后台-web-a Server Proxy
		location /web-a/ {
			proxy_buffering off;
			proxy_pass http://127.0.0.1:8081/web-a/;
			proxy_set_header Host $host;
			proxy_set_header X-Real-Ip $remote_addr;
			proxy_set_header X-Forwarded-For $remote_addr;
        }
        
    }
}


#############################################################################
#方案二：

http {

    server {
        listen       18080;
        server_name  localhost;
        
		# 请求允许携带token 默认 off
		underscores_in_headers on;
        
    	# 本地环境-后台-web-a Server Proxy
		location /web-a/ {
            # nginx代理后跨域，增加下面3行解决跨域：
            # Credential=true 则 Origin 必须填充
            add_header 'Access-Control-Allow-Credentials' 'true';
            # 例：http://localhost:18080
			add_header 'Access-Control-Allow-Origin' $http_origin;
			add_header 'Access-Control-Allow-Methods' 'GET,POST,DELETE,OPTIONS';
            
			proxy_buffering off;
			proxy_pass http://127.0.0.1:8081/web-a/;
			proxy_set_header Host $host;
			proxy_set_header X-Real-Ip $remote_addr;
			proxy_set_header X-Forwarded-For $remote_addr;
        }
        
    }
}

```

### SringBoot 解决跨域

```java

@Configuration
public class CorsConfig {

    /**
     * 配置全局跨域
     */
    @Bean
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
    }
}

```

### Tomcat 解决跨域

> 把下面的代码粘贴到web.xml的552行下即可

```xml

    <filter>
	  <filter-name>CorsFilter</filter-name>
	  <filter-class>org.apache.catalina.filters.CorsFilter</filter-class>
	  <init-param>
		<param-name>cors.allowed.origins</param-name>
		<param-value>*</param-value>
	  </init-param>
	</filter>
	<filter-mapping>
	  <filter-name>CorsFilter</filter-name>
	  <url-pattern>/*</url-pattern>
	</filter-mapping>

```


# nginx 代理配置IP

```
		# 本地环境-后台-web-a Server Proxy
		location /web-a/ {
			
			proxy_buffering off;
			proxy_pass http://127.0.0.1:8081/web-a/;
			
			
			# 配置访问真实IP
			#	客户端IP 可选的
			#proxy_set_header Host $host; 
			#	客户端真实IP 必需的
			#proxy_set_header X-Real-IP $remote_addr; 
			#	客户端或上一级端口 可选的
			#proxy_set_header X-Real-Port $remote_port; 
			# 	每经过一个反向代理，就会把反向代理IP存放在X-Forwarded-For里 可选的
			# 		格式：客户端ip， 一级代理ip， 二级代理ip...
			#proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
			
			# $remote_addr nginx服务ip
			# $proxy_add_x_forwarded_for 格式：客户端ip， 一级代理ip， 二级代理ip... 相等于=$http_x_forwarded_for, $remote_addr
			
			#proxy_set_header X-Real-IP 172.25.1.1;
			#proxy_set_header Host $host;
			#proxy_set_header X-Real-Ip $remote_addr;
			#proxy_set_header X-Forwarded-For $remote_addr;
			#proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
			
			
			# 自定义模拟IP 后台获取真实IP为 172.25.1.1
			#第一个客户端真实IP
			#proxy_set_header X-Forwarded-For 172.25.1.1;
			
			#所有客户端 IP组 第一个为真实请求的客户端IP
			#proxy_set_header X-Forwarded-For 172.25.1.1,172.25.1.2;
			
			#当前客户端真实IP
			#proxy_set_header X-Real-IP 172.25.1.1;
        }
```
