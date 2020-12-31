package com.springboot;

import com.springboot.enums.OrgEnum;
import com.springboot.service.DataHandleService;
import com.springboot.util.FreemarkerUtils;
import com.springboot.util.ITextUtils;
import com.springboot.vo.risk.EntHealthReportVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * @Author 刘宏飞
 * @Date 2020/12/25 14:46
 * @Version 1.0
 */
public class PdfTest extends ApplicationTest{

    @Autowired
    private DataHandleService dataHandleService;

    @Test
    public void test() throws FileNotFoundException {
        String reqId = "f228853f-161f-41d8-a31c-a00707f7cfeb";
        //科技局 -- 全量
        EntHealthReportVo entHealthReportVo = dataHandleService.getEntHealthReportVo(reqId, OrgEnum.SCIENCE_OFFICE);
        HashMap data = new HashMap();
        data.put("data",entHealthReportVo);

        File file = new File(this.getClass().getResource("/templates").getFile());
        String html = FreemarkerUtils.loadFtlHtml(file, "pdf-science.ftl",data);
        FileOutputStream out = new FileOutputStream("c:/pdf-science.pdf");
        ITextUtils.convertHtmlToPdf(out,html);
    }

    @Test
    public void test1() throws FileNotFoundException {
        String reqId = "f228853f-161f-41d8-a31c-a00707f7cfeb";
        //金融办 -- 知识产权去掉
        EntHealthReportVo entHealthReportVo = dataHandleService.getEntHealthReportVo(reqId, OrgEnum.FINANCE_OFFICE);
        HashMap data = new HashMap();
        data.put("data",entHealthReportVo);

        File file = new File(this.getClass().getResource("/templates").getFile());
        String html = FreemarkerUtils.loadFtlHtml(file, "pdf-finance.ftl",data);
        FileOutputStream out = new FileOutputStream("c:/pdf-finance.pdf");
        ITextUtils.convertHtmlToPdf(out,html);
    }
}
