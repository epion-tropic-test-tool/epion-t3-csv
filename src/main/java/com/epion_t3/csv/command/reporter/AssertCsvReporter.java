/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.csv.command.reporter;

import com.epion_t3.core.command.reporter.impl.AbstractThymeleafCommandReporter;
import com.epion_t3.core.common.bean.ExecuteCommand;
import com.epion_t3.core.common.bean.ExecuteFlow;
import com.epion_t3.core.common.bean.ExecuteScenario;
import com.epion_t3.core.common.context.ExecuteContext;
import com.epion_t3.csv.bean.AssertCsvDataResult;
import com.epion_t3.csv.command.model.AssertCsvData;

import java.util.Map;

/**
 * AssertCSVDataのレポート処理.
 */
public class AssertCsvReporter extends AbstractThymeleafCommandReporter<AssertCsvData, AssertCsvDataResult> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String templatePath() {
        return "assert-csv-data-report";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVariables(Map<String, Object> variable, AssertCsvData command, AssertCsvDataResult commandResult,
            ExecuteContext executeContext, ExecuteScenario executeScenario, ExecuteFlow executeFlow,
            ExecuteCommand executeCommand) {
        variable.put("result", commandResult);
    }
}
