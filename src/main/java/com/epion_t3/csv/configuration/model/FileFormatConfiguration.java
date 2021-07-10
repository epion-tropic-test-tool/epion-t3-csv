/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.csv.configuration.model;

import com.epion_t3.core.common.annotation.CustomConfigurationDefinition;
import com.epion_t3.core.common.bean.scenario.Configuration;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * ファイルフォーマット設定.
 */
@Getter
@Setter
@CustomConfigurationDefinition(id = "FileFormatConfiguration")
public class FileFormatConfiguration extends Configuration {

    /** フィールドデリミタ. */
    private String delimiter = ",";

    /** レコードデリミタ. */
    private String recordSeparator = "LF";

    /** エスケープ文字列. */
    private String escape;

    /** 囲い文字. */
    private String quote = "\"";

    /** 文字コード. */
    private String encoding;

    /** 要素の前後の空白を除去するか. */
    private Boolean trim;

    /** 空行をSKIPするか. */
    private Boolean ignoreEmptyLines;

    /** 最初の行がヘッダーかどうか. */
    private Boolean firstRecordIsHeader;

}
