package com.impltech.web.rest.util;

import java.io.*;

/**
 * Created by dima
 */
public class BankDetailsUploaderUtils {

    public static ByteArrayOutputStream  convertFileToByteArrayOutputStream(File file) {

        try(InputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ByteArrayOutputStream convertFileToByteArrayOutputStream(String fileName) {

        int bytesRead;

        try(InputStream inputStream = new FileInputStream(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
