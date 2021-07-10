/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.csv.command.model;

import com.epion_t3.core.common.annotation.CommandDefinition;
import com.epion_t3.core.common.bean.scenario.Command;
import com.epion_t3.csv.command.reporter.AssertCsvReporter;
import com.epion_t3.csv.command.runner.AssertCsvDataRunner;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * CSVデータのアサートコマンド.
 */
@Getter
@Setter
@CommandDefinition(id = "AssertCsvData", runner = AssertCsvDataRunner.class, assertCommand = true, reporter = AssertCsvReporter.class)
public class AssertCsvData extends Command {

    /** ファイルフォーマット定義の参照. */
    private String fileFormatConfigRef;

    /** 期待値パス. */
    private String expectedDataSetPath;

    /** 結果値の取得したFlowのID. */
    private String actualFlowId;

    /** 比較を無視するインデックスリスト. */
    private List<Integer> ignoreIndexes;

}
