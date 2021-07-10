/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.csv.bean;

import com.epion_t3.core.common.type.AssertStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * アサート結果行.
 *
 * @author takashno
 */
@Getter
@Setter
public class AssertResultRow implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 行アサート結果.
     */
    private AssertStatus rowAssert = AssertStatus.WAIT;

    /**
     * アサート結果カラム値リスト.
     */
    private List<AssertResultColumn> columns = new ArrayList<>();

    /**
     * OKカラム数.
     */
    private Integer okColumnCount = 0;

    /**
     * NGカラム数.
     */
    private Integer ngColumnCount = 0;

    /**
     * AssertResultColumnを追加.
     *
     * @param column {@link AssertResultColumn}
     */
    public void addColumns(AssertResultColumn column) {
        columns.add(column);
        if (AssertStatus.NG == column.getStatus()) {
            addNgColumnCount();
        } else if (AssertStatus.OK == column.getStatus()) {
            addOkColumnCount();
        }
    }

    /**
     * NGカラムをインクリメント.
     */
    private void addNgColumnCount() {
        ngColumnCount++;
    }

    /**
     * OKカラムをインクリメント.
     */
    private void addOkColumnCount() {
        okColumnCount++;
    }

    /**
     * NGカラムが存在するか.
     * 
     * @return true:存在する、false:存在しない
     */
    public boolean hasNgColumn() {
        return ngColumnCount > 0;
    }

}
