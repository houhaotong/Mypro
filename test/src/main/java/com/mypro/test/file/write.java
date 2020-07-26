package com.mypro.test.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class write {
    public static void main(String[] args) {
        FileOutputStream outputStream=null;
        try {
            File file=new File("D:/userId.txt");
            if (file.createNewFile()) {
                outputStream=new FileOutputStream(file);
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 1; i <= 1000; i++) {
                    stringBuffer.append(i+",\r\n");
                }
                outputStream.write(stringBuffer.toString().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (outputStream!=null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
