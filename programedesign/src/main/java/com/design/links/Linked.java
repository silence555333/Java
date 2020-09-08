package com.design.links;

/**
 * @author yangfei
 * @create 2020-08-03 20:43
 */
public class Linked {
    private Integer  value;
    private Linked nextlink;
    private Linked prelink;

    public Linked(Integer value) {
        this.value = value;
    }

    public Linked(Integer value, Linked nextlink, Linked prelink) {
        this.value = value;
        this.nextlink = nextlink;
        this.prelink = prelink;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Linked getNextlink() {
        return nextlink;
    }

    public void setNextlink(Linked nextlink) {
        this.nextlink = nextlink;
    }

    public Linked getPrelink() {
        return prelink;
    }

    public void setPrelink(Linked prelink) {
        this.prelink = prelink;
    }
}
