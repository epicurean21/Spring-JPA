package com.example.springjpa.common.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommonResult {
    private boolean success;
    private Integer code;
    private String msg;
}
