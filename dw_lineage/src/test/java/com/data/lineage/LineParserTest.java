package com.data.lineage;

import com.data.lineage.bean.ColLine;
import com.data.lineage.bean.SQLResult;
import com.data.lineage.parse.LineParser;
import com.data.lineage.util.Check;
import com.data.lineage.util.PropertyFileUtil;
import junit.framework.TestCase;
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.ParseDriver;

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
    public void testParseCreateTable() throws Exception {
        Set<String> inputTablesExpected = new HashSet<String>();
        Set<String> outputTablesExpected = new HashSet<String>();
        Set<String> conditions = new HashSet<String>();
        Set<ColLine> lineSetExpected = new HashSet<ColLine>();
        Set<String> outputTablesActual;
        Set<String> inputTablesActual;
        List<ColLine> lineListActualed;
        String sql1 = "create table target_table LOCATION '/data/location' as select a from source_table;" +
                "create table target_table2 LOCATION '/data/location' as select a2 from source_table2;";
        List<SQLResult> srList = parse.parse(sql1);
        inputTablesExpected.add("default.source_table");
        outputTablesExpected.add("default.target_table");
        Set<String> clone1 = clone(conditions);
        Set<String> fromNameSet1 = new LinkedHashSet<String>();
        fromNameSet1.add("default.source_table.a");
        ColLine col1 = new ColLine("a", null, fromNameSet1, clone1, null, null);
        lineSetExpected.add(col1);
        SQLResult sr = srList.get(0);
        outputTablesActual = sr.getOutputTables();
        inputTablesActual = sr.getInputTables();
        lineListActualed = sr.getColLineList();
        assertSetEquals(outputTablesExpected, outputTablesActual);
        assertSetEquals(inputTablesExpected, inputTablesActual);
        assertCoLineSetEqual(lineSetExpected, lineListActualed);

        inputTablesExpected.clear();
        outputTablesExpected.clear();
        lineSetExpected.clear();
        inputTablesExpected.add("default.source_table2");
        outputTablesExpected.add("default.target_table2");
        Set<String> clone2 = clone(conditions);
        Set<String> fromNameSet2 = new LinkedHashSet<String>();
        fromNameSet2.add("default.source_table2.a2");
        ColLine col2 = new ColLine("a2", null, fromNameSet2, clone2, null, null);
        lineSetExpected.add(col2);
        SQLResult sr2 = srList.get(1);
        outputTablesActual = sr2.getOutputTables();
        inputTablesActual = sr2.getInputTables();
        lineListActualed = sr2.getColLineList();
        assertSetEquals(outputTablesExpected, outputTablesActual);
        assertSetEquals(inputTablesExpected, inputTablesActual);
        assertCoLineSetEqual(lineSetExpected, lineListActualed);
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
}
