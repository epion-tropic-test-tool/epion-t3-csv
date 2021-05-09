/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.csv.bean;

import com.epion_t3.core.command.bean.AssertCommandResult;
import com.epion_t3.core.common.type.AssertStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV/TSVファイルのアサーション結果.
 *
 * @author takashno
 */
@Getter
@Setter
public class AssertCsvDataResult extends AssertCommandResult {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * レコード数アサート.
     */
    private AssertStatus recordNumAssert = AssertStatus.WAIT;

    /**
     * カラム数アサート結果.
     */
    private AssertStatus columnNumAssert = AssertStatus.WAIT;

    /**
     * カラムインデックスアサート結果.
     */
    private AssertStatus columnIndexAssert = AssertStatus.WAIT;

    /**
     * 行アサート結果.
     */
    private AssertStatus rowAssert = AssertStatus.WAIT;

    /**
     * 行.
     */
    private List<AssertResultRow> rows = new ArrayList<>();

    /**
     * OK行数.
     */
    private Integer okRowCount = 0;

    public void addOkRowCount() {
        okRowCount++;
    }

    /**
     * NG行数.
     */
    private Integer ngRowCount = 0;

    public void addNgRowCount() {
        ngRowCount++;
    }

}
