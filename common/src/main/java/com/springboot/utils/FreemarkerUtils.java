package com.springboot.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;

/**
 * @Author 刘宏飞
 * @Date 2020/12/24 14:05
 * @Version 1.0
 */
public class FreemarkerUtils {
    public static String loadFtlHtml(String basePackagePath, String fileName, Map globalMap) {
        if (globalMap == null || fileName == null || "".equals(fileName)) {
            throw new IllegalArgumentException("Directory file");
        }

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        try {
            cfg.setClassForTemplateLoading(FreemarkerUtils.class, basePackagePath);
            cfg.setDefaultEncoding("UTF-8");
            //.RETHROW
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setClassicCompatible(true);
            Template temp = cfg.getTemplate(fileName);

            StringWriter stringWriter = new StringWriter();
            temp.process(globalMap, stringWriter);

            return stringWriter.toString();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            throw new RuntimeException("load fail file");
        }
    }
}
