package com.kw.udtf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;

import java.util.ArrayList;

/**
 * @author yangfei
 * @create 2020-09-04 14:44
 */
public class RelParse  extends GenericUDTF {
    @Override
    public void process(Object[] objects) throws HiveException {

        String input = objects[0].toString();

        ArrayList<String> res=ParArr(input);
        for (int i=0;i<res.size()-1;i++){
            forward(new Object[]{res.get(i).split(",")[0],res.get(i).split(",")[1]});
        }
    }
    @Override
    public void close() throws HiveException {

    }
    @Override
    public StructObjectInspector initialize(ObjectInspector[] args)
            throws UDFArgumentException {
        if (args.length != 1) {
            throw new UDFArgumentLengthException("ExplodeMap takes only one argument");
        }
        if (args[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
            throw new UDFArgumentException("ExplodeMap takes string as a parameter");
        }


        ArrayList<String> fieldNames = new ArrayList<String>();
        ArrayList<ObjectInspector> fieldOIs = new ArrayList<ObjectInspector>();
        fieldNames.add("fromid");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldNames.add("toid");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        //定义了行的列数和类型
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames,fieldOIs);
    }

    public ArrayList<String> ParArr(String s){

        ArrayList<String> arr_result = new ArrayList<String>();
        if(s.contains(",")){
            //进行数组循环

            String[] arr = s.split(",");
            for (int i=0;i<arr.length;i++){
                for (int j=1;j<arr.length;j++){

                    arr_result.add(arr[i]+","+arr[j]);
                }
            }
        }

        return  arr_result;
    }
    public static void main(String[] args) throws HiveException {
        RelParse rl= new RelParse();
        Object o = "123,224,345,122";
        Object[] o1=new Object[1];
        o1[0]=o;
        rl.process(o1);


    }
}
