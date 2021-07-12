/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.csv.messages;

import com.epion_t3.core.message.Messages;

/**
 * csv用メッセージ定義Enum.<br>
 *
 * @author epion-t3-devtools
 */
public enum CsvMessages implements Messages {

    /** 比較無視カラムの設定ファイルが見つかりません. 比較無視カラム設定ファイル : {0} */
    CSV_ERR_0008("com.epion_t3.csv.err.0008"),

    /** 比較無視カラムの設定ファイルは「yaml」「yml」「json」のいずれかで指定して下さい. 比較無視カラム設定ファイル : {0} */
    CSV_ERR_0007("com.epion_t3.csv.err.0007"),

    /** 比較無視カラムの設定ファイルを正しく読み込めませんでした. 比較無視カラム設定ファイル : {0} */
    CSV_ERR_0009("com.epion_t3.csv.err.0009"),

    /** 期待値ファイルのCSV/TSV読み込みに失敗しました. Path: {0} */
    CSV_ERR_0002("com.epion_t3.csv.err.0002"),

    /** 期待値ファイルが見つかりません. Path: {0} */
    CSV_ERR_0001("com.epion_t3.csv.err.0001"),

    /** 結果値を取得したFlowIDは必須です. */
    CSV_ERR_0004("com.epion_t3.csv.err.0004"),

    /** 期待値ファイルのパスは必須です. */
    CSV_ERR_0003("com.epion_t3.csv.err.0003"),

    /** 無視カラムの指定には、ヘッダ名称かインデックスの指定のいずれかが必須です. */
    CSV_ERR_0006("com.epion_t3.csv.err.0006"),

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
