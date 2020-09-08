package com.data.lineage;

import com.data.lineage.bean.ColLine;
import com.data.lineage.bean.SQLResult;
import com.data.lineage.parse.LineParser;
import com.data.lineage.util.PropertyFileUtil;
import junit.framework.TestCase;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yangfei
 * @create 2020-06-19 15:34
 */
public class LineParserTest  extends TestCase {
    LineParser parse = null;

    @Override
    protected void setUp() throws Exception {
        PropertyFileUtil.init(); //设置环境变量
        parse = new LineParser();
    }


    public void testParseAllColumn2() throws Exception {

		Set<String> conditions = new HashSet<String>();

		Set<String> outputTablesActual;
		Set<String> inputTablesActual;
		List<ColLine> lineListActualed;
		String sql1 = "with aa as (select entid,REGCAP from dwt_mart.dwt_company_e00_cr)\n" +
                "insert into dwm_mart.dwm_comp_invinf_C00_cr select a.data_date,\n" +
                "regexp_replace(reflect(\"java.util.UUID\", \"randomUUID\"), \"-\", \"\"),a.entid,\n" +
                "a.invname,a.invid,a.blicno, a.blictype_cn,a.subconam,a.subconam,a.accconam,-1,\n" +
                "a.subcondate,a.sconform,a.currency, case when a.iscp = 'P' then '1' when a.iscp = 'C' then '2' end as invtype,\n" +
                "a.DATA_DATE,case when c.entid is not null then c.REGCAP else '-1' end as regcap\n" +
                "from dwc_mart.dwc_crawler_d_comp_invinf_c00_cr a\n" +
                "inner join dwm_mart.dwm_company_map_e00_cr b on  a.invid = b.entid and b.step_fs = '88' left join aa c on a.entid = c.entid ;\n";
		List<SQLResult> srList = parse.parse(sql1);
		SQLResult sr = srList.get(0);
		outputTablesActual = sr.getOutputTables();
		inputTablesActual = sr.getInputTables();
		lineListActualed = sr.getColLineList();
		printRestult(outputTablesActual, inputTablesActual, lineListActualed);
	}

    private Set<String> clone(Set<String> set){
        Set<String> list2 = new HashSet<String>(set.size());
        for (String string : set) {
            list2.add(string);
        }
        return list2;
    }

    private void assertSetEquals(Set<String> expected, Set<String> actual) {
        assertEquals(expected.size(), actual.size());
        for (String string : expected) {
            assertTrue(actual.contains(string));
        }
    }
    private void assertCoLineSetEqual(Set<ColLine> lineSetExpected,
                                      List<ColLine> lineListActualed) {
        assertEquals(lineSetExpected.size(), lineListActualed.size());
        for (ColLine colLine : lineListActualed) {
            int i = 0;
            for (ColLine colLine2 : lineSetExpected) {
                i++;
                if (colLine.getToNameParse().equals(colLine2.getToNameParse())) {
                    assertEquals(colLine2.getFromNameSet(), colLine.getFromNameSet());
                    assertSetEquals(colLine2.getAllConditionSet(), colLine.getAllConditionSet());
                    i = 0;
                    break;
                }
                if(i == lineListActualed.size()) {
                    assertFalse(true);
                }
            }
        }
    }

    private void printRestult(Set<String> outputTablesActual,
                              Set<String> inputTablesActual, List<ColLine> lineListActualed) {
        System.out.println("inputTable:"+inputTablesActual);
        System.out.println("outputTable:"+outputTablesActual);
        for (ColLine colLine : lineListActualed) {
            System.out.println("ToTable:" + colLine.getToTable() + ",ToNameParse:" + colLine.getToNameParse() + ",ToName:" + colLine.getToName() + ",FromName:" + colLine.getFromNameSet() + ",AllCondition:" + colLine.getAllConditionSet());
        }
    }
}
