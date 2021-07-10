/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.csv.messages;

import com.epion_t3.core.message.Messages;

/**
 * csv用メッセージ定義Enum.<br>
 *
 * @author epion-t3-devtools
 */
public enum CsvMessages implements Messages {

    /** 期待値ファイルのCSV/TSV読み込みに失敗しました. Path: {0} */
    CSV_ERR_0002("com.epion_t3.csv.err.0002"),

    /** 期待値ファイルが見つかりません. Path: {0} */
    CSV_ERR_0001("com.epion_t3.csv.err.0001"),

    /** 結果値を取得したFlowIDは必須です. */
    CSV_ERR_0004("com.epion_t3.csv.err.0004"),

    /** 期待値ファイルのパスは必須です. */
    CSV_ERR_0003("com.epion_t3.csv.err.0003"),

    /** 指定されたエンコーディングは不正です. エンコーディング : {0} */
    CSV_ERR_0005("com.epion_t3.csv.err.0005");

    /** メッセージコード */
    private final String messageCode;

    /**
     * プライベートコンストラクタ<br>
     *
     * @param messageCode メッセージコード
     */
    private CsvMessages(final String messageCode) {
        this.messageCode = messageCode;
    }

    /**
     * messageCodeを取得します.<br>
     *
     * @return messageCode
     */
    public String getMessageCode() {
        return this.messageCode;
    }
}
