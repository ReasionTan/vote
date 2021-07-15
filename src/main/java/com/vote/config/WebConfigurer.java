package com.vote.config;

import com.vote.utils.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.EnumSet;


@Configuration
public class WebConfigurer implements ServletContextInitializer {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    @Inject
    private Environment env;

    @Inject
    private ProjectProperties projectProperties;

    private String key = "SNOW_FLAKE_WORKER_ID";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Bean
    public IdWorker idWorker() {
        String workerIdStr = redisTemplate.opsForList().leftPop(key);
        if (workerIdStr != null) {
            Long id = Long.parseLong(workerIdStr);
            redisTemplate.opsForList().rightPush(key, workerIdStr);
            return new IdWorker(id, id);
        }

        return new IdWorker(projectProperties.getId().getWorkerId(), projectProperties.getId().getDatacenterId());
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web应用配置, 使用环境: {}", Arrays.toString(env.getActiveProfiles()));
        }
        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);
        log.info("Web配置完成");
    }


    /**
     * 解析静态资源的前缀
     */
    private String resolvePathPrefix() {
        String fullExecutablePath = this.getClass().getResource("").getPath();
        String rootPath = Paths.get(".").toUri().normalize().getPath();
        String extractedPath = fullExecutablePath.replace(rootPath, "");
        int extractionEndIndex = extractedPath.indexOf("target/");
        if (extractionEndIndex <= 0) {
            return "";
        }
        return extractedPath.substring(0, extractionEndIndex);
    }


    @Bean
    @ConditionalOnProperty(name = "project.cors.allowed-origins")
    public CorsFilter corsFilter() {
        log.debug("注册 CORS 过滤器");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = projectProperties.getCors();
        source.registerCorsConfiguration("/api/**", config);
        source.registerCorsConfiguration("/v2/api-docs", config);
        source.registerCorsConfiguration("/oauth/**", config);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
