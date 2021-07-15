package com.vote;

import com.vote.basic.constant.Constants;
import com.vote.config.DefaultProfileConfig;
import com.vote.config.ProjectProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@ComponentScan
@MapperScan("com.vote.dao.mapper")
@EnableAutoConfiguration(exclude = {LiquibaseAutoConfiguration.class})
@EnableConfigurationProperties({ProjectProperties.class})
public class VoteApplication {

    private static final Logger log = LoggerFactory.getLogger(VoteApplication.class);

    @Inject
    private Environment env;


    /**
     * 初始化环境
     * <p>
     * Spring profiles 可以通过参数启动： --spring.profiles.active=your-active-profile
     * <p>
     */
    @PostConstruct
    public void initApplication() {
        log.info("当前正在运行的环境是 : {}", Arrays.toString(env.getActiveProfiles()));
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)) {
            log.error("*** 当前环境配置错误：不应该同时激活开发环境[prod]与测试环境[dev]！ ***");
        }
    }

    /**
     * Main函数---SpringBoot入口
     *
     * @param args 参数
     * @throws UnknownHostException Localhost不能被解析成为地址
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(VoteApplication.class);
        DefaultProfileConfig.addDefaultProfile(app);
        ConfigurableApplicationContext run = app.run(args);
        Environment env = run.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "应用 '{}' 正在运行! 链接地址为:\n\t" +
                        "swagger-ui:\thttp://localhost:{}{}/swagger-ui.html\n\t" +
                        "本地地址: \thttp://localhost:{}\n\t" +
                        "网络地址: \thttp://{}:{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
    }
}
