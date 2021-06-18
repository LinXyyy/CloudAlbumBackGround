package com.xy.utils;

import junit.framework.TestCase;

import java.io.File;

public class ClassifyUtilTest extends TestCase {

    public void testGetClassify() {
        System.out.println(ClassifyUtil.getClassify(new File("C:\\www\\CloudAlbum\\777777\\ycy3.jpg")));
    }
}