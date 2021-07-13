/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.csv.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Ignores implements Serializable {
    private List<IgnoreElement> ignores;
}
