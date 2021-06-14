package com.xy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author x1yyy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picture {
    private int pictureKey;
    private String name;
    private String imgUrl;
    private String thumbUrl;
    private Date date;
    private double size;
    private Classify classify;
    private int userKey;
}
