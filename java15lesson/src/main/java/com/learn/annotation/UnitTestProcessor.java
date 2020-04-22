package com.learn.annotation;

import com.sun.xml.internal.stream.buffer.AbstractProcessor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes("UnitTest")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class UnitTestProcessor extends AbstractProcessor {

    public  boolean process(Set<? extends TypeElement> annotation, RoundEnvironment roundEnvironment){
        for (Element clz:roundEnvironment.getElementsAnnotatedWith(UnitTest.class)){
            UnitTest ut=clz.getAnnotation(UnitTest.class);
            String newFileStr="public class "+ut.prefix()+clz.getSimpleName()+"{\n\n";
            newFileStr="}\n\n";//end of contructor

            //add main()
            newFileStr += "public static void main (String[] args) {\n";

            //new instance
            newFileStr += clz.getSimpleName() + "clz =new "+clz.getSimpleName() +"() ;\n";

            //add test methos

            for (Element testmethod :clz.getEnclosedElements()){
                if (!testmethod.getSimpleName().toString().equals("<init>")&& testmethod.asType() instanceof ExecutableType){
                    //add test method
                    newFileStr += "clz." +testmethod.getSimpleName() +"(); \n";
                }
            }

            newFileStr+="}\n";//end of main()
            newFileStr+="}\n";//end of class

//            try {

//                JavaFileObject jfo= processingEnv.createSourceFile(ut.prefix() + clz.getSimpleName(),clz);

//                Writer writer=jfo.openWriter();
//                writer.append(newFileStr);
//                writer.flush();
//                writer.close();
//            }catch (IOException ex){

//            }

        }
        return true;
    }
}
