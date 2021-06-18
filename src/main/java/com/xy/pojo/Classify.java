package com.xy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author x1yyy
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classify {
    private int classifyKey;
    private String classify;
    private int userKey;
}
