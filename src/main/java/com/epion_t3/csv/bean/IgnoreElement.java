/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.csv.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * アサート無視要素.
 *
 * @since 0.1.1
 */
@Getter
@Setter
public class IgnoreElement implements Serializable {

    /**
     * デフォルトシリアルバージョンUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 無視対象インデックス.無視対象ヘッダ名称とどちらかが指定されている必要がある.
     */
    private Integer index;

    /**
     * 無視対象ヘッダ名称.無視対象インデックスとどちらかが指定されている必要がある.
     */
    private String headerName;

}
