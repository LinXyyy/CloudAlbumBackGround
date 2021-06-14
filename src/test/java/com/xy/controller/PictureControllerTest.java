package com.xy.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml"
})
public class PictureControllerTest {

    @Autowired
    WebApplicationContext context;
    MockMvc mockMvc;

    @Before
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testGetPictureByUserKey() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getPictureByUserKey").header("userKey", "306205")).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        System.out.println(response.getContentAsString());
    }

    @Test
    public void testGetPictureByUserKeyAndClassify() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getPictureByUserKeyAndClassify").header("userKey", "306205").param("classifyKey", "500397")).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        System.out.println(response.getContentAsString());
    }

    @Test
    public void testAddPicture() throws Exception {
        File file1 = new File("C:\\img\\qwfraw.jpg");
        File file2 = new File("C:\\Users\\x1yyy\\Pictures\\Camera Roll\\2.jpg");

        FileInputStream fileInputStream = new FileInputStream(file1);
        FileInputStream fileInputStream1 = new FileInputStream(file2);

        MockMultipartFile multipartFile = new MockMultipartFile(
                "qwfraw.jpg", "qwfraw.jpg", "image/jpg", fileInputStream
        );
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "2.jpg", "2.jpg", "image/jpg", fileInputStream1
        );

        MultipartFile[] img = { multipartFile, mockMultipartFile };

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addPicture", img).header("userKey", "306205")).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        System.out.println(response.getContentAsString());
    }

    @Test
    public void testDeletePicture() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/deletePicture").header("userKey", "306205").param("pictureNames", "2.jpg", "DetectLabel1.jpg")).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        System.out.println(response.getContentAsString());
    }
}