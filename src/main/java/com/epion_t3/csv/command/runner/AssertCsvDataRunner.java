/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.csv.command.runner;

import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import com.epion_t3.core.common.type.AssertStatus;
import com.epion_t3.core.common.type.CommandStatus;
import com.epion_t3.core.exception.SystemException;
import com.epion_t3.csv.bean.AssertCsvDataResult;
import com.epion_t3.csv.bean.AssertResultColumn;
import com.epion_t3.csv.bean.AssertResultRow;
import com.epion_t3.csv.command.model.AssertCsvData;
import com.epion_t3.csv.configuration.model.FileFormatConfiguration;
import com.epion_t3.csv.messages.CsvMessages;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * CSVファイル同士のアサートコマンド.
 */
public class AssertCsvDataRunner extends AbstractCommandRunner<AssertCsvData> {

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResult execute(AssertCsvData command, Logger logger) throws Exception {

        // 結果オブジェクト生成
        var result = new AssertCsvDataResult();
        result.setStatus(CommandStatus.RUNNING);

        // ファイルフォーマットの設定を解決
        var fileFormatConfiguration = (FileFormatConfiguration) referConfiguration(command.getFileFormatConfigRef());

        // デフォルトをベースにしてファイルフォーマット設定を反映する
        var csvFormat = CSVFormat.DEFAULT;
        csvFormat = csvFormat.withDelimiter(fileFormatConfiguration.getDelimiter().charAt(0));
        csvFormat = csvFormat.withRecordSeparator(fileFormatConfiguration.getRecordSeparator());
        if (StringUtils.isNotEmpty(fileFormatConfiguration.getQuote())) {
            csvFormat = csvFormat.withQuote(fileFormatConfiguration.getQuote().charAt(0));
        }
        if (StringUtils.isNotEmpty(fileFormatConfiguration.getEscape())) {
            csvFormat = csvFormat.withEscape(fileFormatConfiguration.getEscape().charAt(0));
        }
        if (fileFormatConfiguration.getTrim() != null) {
            csvFormat = csvFormat.withTrim(fileFormatConfiguration.getTrim());
        }
        if (fileFormatConfiguration.getIgnoreEmptyLines() != null) {
            csvFormat = csvFormat.withIgnoreEmptyLines(fileFormatConfiguration.getIgnoreEmptyLines());
        }
        if (fileFormatConfiguration.getFirstRecordIsHeader() != null) {
            if (fileFormatConfiguration.getFirstRecordIsHeader()) {
                csvFormat = csvFormat.withFirstRecordAsHeader().withSkipHeaderRecord(true);
            }
        }

        var encoding = StringUtils.isEmpty(fileFormatConfiguration.getEncoding()) ? System.getProperty("file.encoding")
                : fileFormatConfiguration.getEncoding();
        // 文字コードチェック
        if (!Charset.isSupported(encoding)) {
            throw new SystemException(CsvMessages.CSV_ERR_0005, encoding);
        }

        // 期待値
        var expected = getCSVParser(command.getExpectedDataSetPath(), Charset.forName(encoding), csvFormat, logger);

        // 結果値
        var actual = getCSVParser(actualCSVDataPath(command, logger).toString(), Charset.forName(encoding), csvFormat,
                logger);

        // レコード数アサート
        var expectedRecords = expected.getRecords();
        var actualRecords = actual.getRecords();
        var expectedRecordSize = expectedRecords.size();
        var actualRecordSize = actualRecords.size();
        result.setRecordNumAssert(expectedRecordSize == actualRecordSize ? AssertStatus.OK : AssertStatus.NG);

        // カラム数アサート
        if (csvFormat.getHeader() != null) {
            var expectedColumnSize = expected.getHeaderMap().size();
            var actualColumnSize = actual.getHeaderMap().size();
            result.setColumnNumAssert(expectedColumnSize == actualColumnSize ? AssertStatus.OK : AssertStatus.NG);
        } else {
            // ヘッダがない場合には、実レコードのカラム数で比較する.
            var expectedColumnSize = expectedRecords.get(0).size();
            var actualColumnSize = actualRecords.get(0).size();
            result.setColumnNumAssert(expectedColumnSize == actualColumnSize ? AssertStatus.OK : AssertStatus.NG);
            result.setColumnNumAssert(AssertStatus.OK);
        }

        if (result.getColumnNumAssert() == AssertStatus.NG || result.getRecordNumAssert() == AssertStatus.NG) {
            // レコード数アサート か カラム数アサートがNGである場合はこの時点で処理を終える
            result.setAssertStatus(AssertStatus.NG);
            result.setStatus(CommandStatus.SUCCESS);
            return result;
        }

        // データチェック
        for (var i = 0; i < expectedRecords.size(); i++) {
            var expectedRecord = expectedRecords.get(i);
            var actualRecord = actualRecords.get(i);
            var assertResultRow = new AssertResultRow();

            var hasHeaderMap = expected.getHeaderMap() != null;
            var hasIgnores = CollectionUtils.isNotEmpty(command.getIgnores());
            if (hasHeaderMap) {
                for (Map.Entry<String, Integer> entry : expected.getHeaderMap().entrySet()) {
                    var targetIndex = entry.getValue();
                    var expectedValue = expectedRecord.get(targetIndex);
                    var actualValue = actualRecord.get(targetIndex);
                    if (hasIgnores && isIgnoreColumn(command, entry)) {
                        assertResultRow.addColumns(AssertResultColumn.builder()
                                .index(targetIndex)
                                .expected(expectedValue)
                                .actual(actualValue)
                                .ignore(true)
                                .status(AssertStatus.OK)
                                .build());
                    } else {
                        assertResultRow.addColumns(AssertResultColumn.builder()
                                .index(targetIndex)
                                .expected(expectedValue)
                                .actual(actualValue)
                                .ignore(false)
                                .status(Objects.equals(expectedValue, actualValue) ? AssertStatus.OK : AssertStatus.NG)
                                .build());
                    }
                }
            } else {
                for (var j = 0; j < expectedRecord.size(); j++) {
                    var targetIndex = j;
                    var expectedValue = expectedRecord.get(targetIndex);
                    var actualValue = actualRecord.get(targetIndex);
                    if (hasIgnores && isIgnoreColumn(command, targetIndex)) {
                        assertResultRow.addColumns(AssertResultColumn.builder()
                                .index(targetIndex)
                                .expected(expectedValue)
                                .actual(actualValue)
                                .ignore(true)
                                .status(AssertStatus.OK)
                                .build());
                    } else {
                        assertResultRow.addColumns(AssertResultColumn.builder()
                                .index(targetIndex)
                                .expected(expectedValue)
                                .actual(actualValue)
                                .ignore(false)
                                .status(Objects.equals(expectedValue, actualValue) ? AssertStatus.OK : AssertStatus.NG)
                                .build());
                    }
                }
            }
            assertResultRow.setRowAssert(assertResultRow.hasNgColumn() ? AssertStatus.NG : AssertStatus.OK);
            result.addRow(assertResultRow);
        }

        // 最終的なアサート結果を判定
        result.setRowAssert(result.getNgRowCount() == 0 ? AssertStatus.OK : AssertStatus.NG);
        result.setAssertStatus(result.getRowAssert());

        // コマンド実行結果はSUCCESS
        result.setStatus(CommandStatus.SUCCESS);
        return result;
    }

    /**
     * 結果値のエビデンス配置パスを取得します.
     *
     * @param command コマンド
     * @param logger ロガー
     * @return 結果値のエビデンスファイルパス
     */
    private Path actualCSVDataPath(AssertCsvData command, Logger logger) {

        // 結果値参照のFlowIDを取得
        var flowId = command.getActualFlowId();

        // 結果値参照のFlowIDは必須
        if (StringUtils.isEmpty(flowId)) {
            throw new SystemException(CsvMessages.CSV_ERR_0004);
        }

        // 結果値の配置パスを解決
        return referFileEvidence(flowId);
    }

    /**
     * CSV Data読込.
     *
     * @param csvDataRelativePath 読み込むCSV/TSVファイルの相対パス
     * @param charset 文字コード
     * @param logger ロガー
     * @return {@link CSVParser}
     */
    private CSVParser getCSVParser(String csvDataRelativePath, Charset charset, CSVFormat csvFormat, Logger logger) {

        // CSV Dataの配置パスは必須
        if (StringUtils.isEmpty(csvDataRelativePath)) {
            throw new SystemException(CsvMessages.CSV_ERR_0003);
        }

        // CSV Dataの配置パスを解決
        var csvDataPath = (Path) null;
        if (Paths.get(csvDataRelativePath).isAbsolute()) {
            csvDataPath = Paths.get(csvDataRelativePath);
        } else {
            csvDataPath = Paths.get(getBaseDirectory(), csvDataRelativePath);
        }

        // CSV Dataの配置パスが存在しなかった場合はエラー
        if (Files.notExists(csvDataPath)) {
            throw new SystemException(CsvMessages.CSV_ERR_0001, csvDataPath.toString());
        }

        try {
            return CSVParser.parse(csvDataPath, charset, csvFormat);
        } catch (IOException e) {
            throw new SystemException(e, CsvMessages.CSV_ERR_0002, csvDataPath);
        }
    }

    /**
     * 無視カラムかどうか判定します.
     *
     * @param command コマンド
     * @param entry 列エントリー
     * @return 判断結果
     */
    private boolean isIgnoreColumn(AssertCsvData command, Map.Entry<String, Integer> entry) {
        return Optional.ofNullable(command.getIgnores())
                .map(Collection::stream)
                .orElseGet(Stream::empty)
                .anyMatch(x -> {
                    if (StringUtils.isNotEmpty(x.getHeaderName())) {
                        return entry.getKey().equals(x.getHeaderName());
                    } else if (x.getIndex() != null) {
                        return entry.getValue().equals(x.getIndex());
                    } else {
                        throw new SystemException(CsvMessages.CSV_ERR_0006);
                    }
                });
    }

    /**
     * 無視カラムかどうか判定します.
     *
     * @param command コマンド
     * @param index インデックス
     * @return 判断結果
     */
    private boolean isIgnoreColumn(AssertCsvData command, Integer index) {
        return Optional.ofNullable(command.getIgnores())
                .map(Collection::stream)
                .orElseGet(Stream::empty)
                .anyMatch(x -> x.getIndex() != null && index.equals(x.getIndex()));
    }
}
