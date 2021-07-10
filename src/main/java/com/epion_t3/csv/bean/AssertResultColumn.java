/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.csv.bean;

import com.epion_t3.core.common.type.AssertStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;

/**
 * アサート結果カラム値.
 *
 * @author takashno
 */
@Getter
@Setter
@Builder
public class AssertResultColumn implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * カラムインデックス.
     */
    @NonNull
    private int index;

    /**
     * 期待値.
     */
    @NonNull
    private Object expected;

    /**
     * 結果値.
     */
    @NonNull
    private Object actual;

    /**
     * 無視カラム.
     */
    @NonNull
    private boolean ignore = false;

    /**
     * アサート結果.
     */
    private AssertStatus status = AssertStatus.WAIT;
}
