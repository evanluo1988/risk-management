package com.springboot;

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
        String reqId = "1b20d84f-3e71-41c6-8430-abd41af63016";
        EntHealthReportVo entHealthReportVo = dataHandleService.getEntHealthReportVo(reqId);
        HashMap data = new HashMap();
        data.put("data",entHealthReportVo);

        File file = new File(this.getClass().getResource("/templates").getFile());
        String html = FreemarkerUtils.loadFtlHtml(file, "pdf.ftl",data);
        FileOutputStream out = new FileOutputStream("c:/pdf.pdf");
        ITextUtils.convertHtmlToPdf(out,html);
    }
}
