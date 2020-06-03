package com.learn.generics;

public interface BaseDao<T> {
    public T select(T t);
    public void  insert(T t);
    public void  update(T t);
    public void  delete(T t);

}
