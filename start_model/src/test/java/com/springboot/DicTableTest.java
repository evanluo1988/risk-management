package com.springboot;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;
import com.springboot.domain.DicTable;
import com.springboot.service.DicTableService;
import com.springboot.util.ConvertUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/31 15:24
 * @Version 1.0
 */
public class DicTableTest extends ApplicationTest {

    @Autowired
    private DicTableService dicTableService;

    @Test
    public void init(){
        List<DicTableVo> datas = Lists.newArrayList();
        File file = new File("C:\\Users\\Administrator\\Desktop\\1.xlsx");
        EasyExcel.read(file, DicTableVo.class, new AnalysisEventListener<DicTableVo>() {

            @Override
            public void invoke(DicTableVo dicTableVo, AnalysisContext analysisContext) {
                datas.add(dicTableVo);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println(datas.size());
                List<DicTable> dicTables = ConvertUtils.sourceToTarget(datas, DicTable.class);
                dicTables.forEach(dicTable -> dicTable.setDicType("dicTables"));
                dicTableService.saveBatch(dicTables);
            }
        }).sheet().doRead();
    }
}
