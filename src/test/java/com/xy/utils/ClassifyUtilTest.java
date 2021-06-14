package com.xy.utils;

import junit.framework.TestCase;

public class ClassifyUtilTest extends TestCase {

    public void testGetClassify() {
        System.out.println(ClassifyUtil.getClassify("http://49.234.149.121:8080/image/762736/thumb/DetectLabel1.jpg"));
    }
}