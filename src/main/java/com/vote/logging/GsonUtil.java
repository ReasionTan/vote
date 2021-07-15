package com.vote.logging;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.Map;

public final class GsonUtil {

    private GsonUtil() {

    }

    /**
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return toJson(obj, null, false);
    }

    /**
     * @param obj
     * @param datePattern
     * @param isPrettyPrinting
     * @return
     */
    public static String toJson(Object obj, String datePattern, Boolean isPrettyPrinting) {
        return toJson(obj, datePattern, isPrettyPrinting, null);
    }

    /**
     * @param obj
     * @param datePattern
     * @param isPrettyPrinting
     * @param isExportNull
     * @return
     */
    public static String toJson(Object obj, String datePattern, Boolean isPrettyPrinting, Boolean isExportNull) {
        GsonBuilder builder = new GsonBuilder();

        if (isPrettyPrinting != null && isPrettyPrinting) {
            builder.setPrettyPrinting();
        }
        if (StringUtils.isNotBlank(datePattern)) {
            datePattern = "yyyy-MM-dd HH:mm:ss";
        }
        if (isExportNull != null && isExportNull) {
            builder.serializeNulls();
        }
        builder.disableHtmlEscaping();
        builder.setDateFormat(datePattern);
        Gson gson = builder.create();
        return gson.toJson(obj);
    }

    /**
     * 转化Json成为Map<String, Object>
     *
     * @param json 待转化的Json
     * @return Map类型的结果
     */
    public static Map<String, Object> parse2Map(String json) {

        Type stringStringMap = new TypeToken<Map<String, Object>>() {
        }.getType();

        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(json, stringStringMap);

        return map;
    }

}
