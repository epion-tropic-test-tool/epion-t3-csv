package com.epion_t3.csv.command.runner;

import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import com.epion_t3.core.exception.SystemException;
import com.epion_t3.csv.bean.AssertCsvDataResult;
import com.epion_t3.csv.command.model.AssertCsvData;
import com.epion_t3.csv.configuration.model.FileFormatConfiguration;
import com.epion_t3.csv.messages.CsvMessages;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

public class AssertCsvDataRunner extends AbstractCommandRunner<AssertCsvData> {
    @Override
    public CommandResult execute(AssertCsvData command, Logger logger) throws Exception {

        var result = new AssertCsvDataResult();

        var fileFormatConfiguration = (FileFormatConfiguration) referConfiguration(command.getFileFormatConfigRef());

        CSVFormat csvFormat = CSVFormat.DEFAULT;

        // 期待値
        var expected = getCSVParser(command.getExpectedCSVDataPath(), StandardCharsets.UTF_8, csvFormat, logger);

        // 結果値
        var actual = getCSVParser(actualCSVDataPath(command, logger).toString(), StandardCharsets.UTF_8, csvFormat, logger);

        // レコード数アサート
        var expectedRecordSize = expected.getRecords().size();
        var actualRecordSize = actual.getRecords().size();

        // カラム数チェック
        var expectedColumnSize = expected.getHeaderMap().size();
        var actualColumnSize = actual.getHeaderMap().size();

        // データチェック
        for (var i = 0; i < expected.getRecords().size(); i++) {
            var expectedRecord = expected.getRecords().get(i);
            var actualRecord = expected.getRecords().get(i);
            for (Map.Entry<String, Integer> entry : expected.getHeaderMap().entrySet()) {
                if (command.getIgnoreIndexes().contains(entry.getValue())) {

                } else {
                    var targetIndex = entry.getValue();
                    var expectedValue = expectedRecord.get(targetIndex);
                    var actualValue = actualRecord.get(targetIndex);
                    if (Objects.equals(expectedValue, actualValue)) {

                    } else {

                    }
                }

            }

        }

        return null;
    }

    /**
     * 結果値のエビデンス配置パスを取得します.
     *
     * @param command コマンド
     * @param logger  ロガー
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
     * @param charset             文字コード
     * @param logger              ロガー
     * @return {@link CSVParser}
     */
    private CSVParser getCSVParser(String csvDataRelativePath, Charset charset, CSVFormat csvFormat, Logger logger) {

        // CSV Dataの配置パスは必須
        if (StringUtils.isEmpty(csvDataRelativePath)) {
            throw new SystemException(CsvMessages.CSV_ERR_0003);
        }

        // CSV Dataの配置パスを解決
        var csvDataPath = Paths.get(getBaseDirectory(), csvDataRelativePath);

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
}
