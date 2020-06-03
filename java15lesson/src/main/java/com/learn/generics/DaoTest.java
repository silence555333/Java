package com.learn.generics;

public class DaoTest {
    public DaoTest() {
    }
    public static  void  main(String[] args){
        TeacherDao td=new TeacherDao();
        Teacher t=new Teacher();
        t.setID(1);
        t.setName("aLICE");
        td.insert(t);

        t=td.select(1);
        t.getID();
        t.getName();
    }
}
