package com.springboot;

import com.springboot.enums.OrgEnum;
import com.springboot.service.DataHandleService;
import com.springboot.utils.FreemarkerUtils;
import com.springboot.utils.ITextUtils;
import com.springboot.vo.risk.EntHealthReportVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
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
        String reqId = "b43c1c67-15cf-478a-a357-46004286f306";
        //科技局 -- 全量
        EntHealthReportVo entHealthReportVo = dataHandleService.getEntHealthReportVo(reqId, OrgEnum.SCIENCE_OFFICE);
        HashMap data = new HashMap();
        data.put("data",entHealthReportVo);

        InputStream file = this.getClass().getResourceAsStream("/templates");
        String html = FreemarkerUtils.loadFtlHtml(file, "pdf-science.ftl",data);
        FileOutputStream out = new FileOutputStream("d:/pdf-science.pdf");
        ITextUtils.convertHtmlToPdf(out,html);
    }

    @Test
    public void test1() throws FileNotFoundException {
        String reqId = "b43c1c67-15cf-478a-a357-46004286f306";
        //金融办 -- 知识产权去掉
        EntHealthReportVo entHealthReportVo = dataHandleService.getEntHealthReportVo(reqId, OrgEnum.FINANCE_OFFICE);
        HashMap data = new HashMap();
        data.put("data",entHealthReportVo);

        InputStream file = this.getClass().getResourceAsStream("/templates");
        String html = FreemarkerUtils.loadFtlHtml(file, "pdf-finance.ftl",data);
        FileOutputStream out = new FileOutputStream("d:/pdf-finance.pdf");
        ITextUtils.convertHtmlToPdf(out,html);
    }
}
