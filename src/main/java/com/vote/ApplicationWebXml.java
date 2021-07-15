package com.vote;

import com.vote.config.DefaultProfileConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ApplicationWebXml extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        /**
         * 如果没有设置环境则设置为默认环境
         */
        DefaultProfileConfig.addDefaultProfile(application.application());
        return application.sources(VoteApplication.class);
    }

}