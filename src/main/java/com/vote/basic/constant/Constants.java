package com.vote.basic.constant;

import java.util.regex.Pattern;

public class Constants {

    public static final Pattern CHARACTER_REPLACEMENT = Pattern.compile("\\{(\\d)\\}");

    /**
     * Spring profile：开发以及生产环境
     */
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";

    /**
     * Spring profile 开启 swagger
     */
    public static final String SPRING_PROFILE_SWAGGER = "swagger";

}
