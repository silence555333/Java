package com.learn.generics;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

public class BaseDaoImpl<T> implements BaseDao<T> {
    private Class<T> TableCls;//实际的泛型参数类


    public BaseDaoImpl() {
        ParameterizedType type= (ParameterizedType) getClass().getGenericSuperclass();
        TableCls = (Class<T>) type.getActualTypeArguments()[0];
    }


    public T select(int id) {
        String sqlstr="select * from " +TableCls.getSimpleName() +"where id"+id;
        /**
         * JDBC执行sql
         */
        T t=null;
        try{
            t=TableCls.newInstance();
            Field fields[]=TableCls.getDeclaredFields();
            //模拟赋值
            fields[0].set(t,1);
            fields[1].set(t,"Alicr");
        } catch (Exception e){

        }
        return t;
    }

    @Override
    public T select(T t) {
        return null;
    }

    @Override
    public void insert(T t) {

        String sqlstr="insert into "+TableCls.getSimpleName()+"(";

        Field fields[]=TableCls.getDeclaredFields();

        for (Field field:fields){
            field.setAccessible(true);
            String colName=field.getName();
            sqlstr+=colName+",";
        }
        sqlstr+=") values(";
        for (Field field:fields){
            field.setAccessible(true);
            try{
                sqlstr=field.get(t)+",";

            }catch (Exception e){

            }
        }
        sqlstr+=")";
        System.out.println(sqlstr);
    }

    @Override
    public void update(T t) {

    }

    @Override
    public void delete(T t) {

    }
}
