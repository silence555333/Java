package com.learn.loadclass;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MyClassloader extends ClassLoader{
    protected  Class<?> findClass(String name) throws ClassNotFoundException{
        String classFileName=name.replace(".","/")+".class";
        byte[] classBytes=null;
        Path path=null;
        try{
            path= Paths.get(getResource(classFileName).toURI());
            classBytes= Files.readAllBytes(path);
        }catch (IOException | URISyntaxException e){
            e.printStackTrace();
        }

        Class<?> clazz=defineClass(name,classBytes,0,classBytes.length);
        return clazz;
    }
}
