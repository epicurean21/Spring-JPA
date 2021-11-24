package com.example.springjpa.common.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListResult<T> extends CommonResult {
    private List<T> list;
}
