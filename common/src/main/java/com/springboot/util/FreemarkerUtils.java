package com.springboot.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @Author 刘宏飞
 * @Date 2020/12/24 14:05
 * @Version 1.0
 */
public class FreemarkerUtils {
    public static String loadFtlHtml(File baseDir, String fileName, Map globalMap){
        if(baseDir == null || !baseDir.isDirectory() || globalMap ==null || fileName == null || "".equals(fileName)){
            throw new IllegalArgumentException("Directory file");
        }

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        try {
            cfg.setDirectoryForTemplateLoading(baseDir);
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
