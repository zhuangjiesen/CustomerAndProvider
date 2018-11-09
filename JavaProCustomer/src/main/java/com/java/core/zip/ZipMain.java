package com.java.core.zip;

import lombok.extern.slf4j.Slf4j;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/10/30
 */
@Slf4j
public class ZipMain {

    public static void main(String[] args) throws Exception {
        String path = "/Users/zhuangjiesen/develop/wx_micsoft/sources/压缩包测试";
        String zippath = "/Users/zhuangjiesen/develop/wx_micsoft/sources/压缩包测试.zip";

        File file = new File(path);
        File[] srcFile = file.listFiles();

        int len;
        byte[] buf = new byte[2048];
        OutputStream oStream = new FileOutputStream(zippath);
        ZipOutputStream zipOut = new ZipOutputStream(oStream);
        for (int i = 0; i < srcFile.length; i++) {
            FileInputStream in = new FileInputStream(srcFile[i]);
            zipOut.putNextEntry(new ZipEntry(srcFile[i].getName()));
            // 支持中文
            zipOut.setEncoding("GBK");
            log.info("压缩里的文件的文件名="+srcFile[i].getName());
            while ((len = in.read(buf)) > 0) {
                zipOut.write(buf, 0, len);
            }
            zipOut.closeEntry();
            in.close();
        }
        zipOut.close();

    }

}
