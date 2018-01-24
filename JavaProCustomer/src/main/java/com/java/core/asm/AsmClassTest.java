package com.java.core.asm;

import org.apache.shiro.util.ThreadState;

import java.util.concurrent.SynchronousQueue;

/**
 * Created by zhuangjiesen on 2017/5/17.
 */
public class AsmClassTest {


    static {
        System.out.println("xxxxxxxxxx");
    }

    public AsmClassTest(){
    }

    public AsmClassTest(long constructorArgLong , String constructorArg){
        String localConArg1 = "局部变量1";
        String localConArg2 = "局部变量2";
    }

    /*
    * 没有参数，没有返回值的方法
    * */
    public void doMethodFirst(){
    }

    /*
    * 有参数，没有返回值的方法
    * */
    public void doMethodSecond(long methodArgLong , String methodArgStr1 , String methodArgStr2){
    }


    /*
    * 有参数，有返回值的方法
    * */
    public String doMethodThird(long methodArgLong , String methodArgStr1 , String methodArgStr2){
        String loacalRetValue = "本地变量1";
        String loacalRetValue2 = "本地变量2";
        return loacalRetValue;
    }

    /*
    * 有参数，有返回值的方法
    * */
    public String doMethodForth(long methodArgLong , String methodArgStr1 , String methodArgStr2 , long methodArgLong3 ){
        String loacalRetValue = "本地变量1";
        String loacalRetValue2 = "本地变量2";
        String loacalRetValue3 = "本地变量2";
        return loacalRetValue;
    }
    public static String doStaticMethodFifth(long methodArgLong , String methodArgStr1 , String methodArgStr2 , long methodArgLong3 ){


        String loacalRetValue = "本地变量1";
        String loacalRetValue2 = "本地变量2";
        String loacalRetValue3 = "本地变量2";
        return loacalRetValue;
    }
}
