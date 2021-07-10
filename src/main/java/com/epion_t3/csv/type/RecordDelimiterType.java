/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.csv.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RecordDelimiterType {

    CRLF("\r\n"),

    LF("\n");

    private String value;

    public static RecordDelimiterType valueOfByValue(final String value) {
        return Arrays.stream(values()).filter(x -> x.value.equals(value)).findFirst().orElse(null);
    }
}
