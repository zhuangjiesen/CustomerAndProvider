package com.java.core.io.nio;

import sun.nio.ch.SelectionKeyImpl;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/10/26
 */
public class IOMain {

    public static void main(String[] args) {
        System.out.println(SelectionKey.OP_READ);
        System.out.println(SelectionKey.OP_WRITE);
        System.out.println(SelectionKey.OP_CONNECT);
        System.out.println(SelectionKey.OP_ACCEPT);
        System.out.println(SelectionKey.OP_CONNECT|SelectionKey.OP_WRITE|SelectionKey.OP_READ|SelectionKey.OP_ACCEPT);





    }

}
