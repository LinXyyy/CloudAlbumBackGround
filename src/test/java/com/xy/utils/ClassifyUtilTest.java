package com.xy.utils;

import junit.framework.TestCase;

import java.io.File;

public class ClassifyUtilTest extends TestCase {

    public void testGetClassify() {
        System.out.println(ClassifyUtil.getClassify(new File("C:\\Users\\x1yyy\\Pictures\\Camera Roll\\13.jpg")));
    }
}