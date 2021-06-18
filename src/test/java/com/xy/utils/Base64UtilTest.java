package com.xy.utils;

import junit.framework.TestCase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

public class Base64UtilTest extends TestCase {

    public void testEncode() throws FileNotFoundException {
        File file = new File("C:\\Users\\x1yyy\\Pictures\\Camera Roll\\qwfraw.jpg");

        BufferedImage bi;
        try {
            bi = ImageIO.read(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();
//            System.out.println(Base64Util.encode(bytes));
            //return encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}