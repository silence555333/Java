package com.data.lineage.bean;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author yangfei
 * @create 2020-06-19 15:20
 */
public class Block {
    private String condition;
    private Set<String> colSet = new LinkedHashSet<String>();
    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    public Set<String> getColSet() {
        return colSet;
    }
    public void setColSet(Set<String> colSet) {
        this.colSet = colSet;
    }
}
