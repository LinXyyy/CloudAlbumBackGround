package com.xy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author x1yyy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int userKey;
    private String account;
    private String password;
}
