package com.epion_t3.csv.command.model;

import com.epion_t3.core.common.annotation.CommandDefinition;
import com.epion_t3.core.common.bean.scenario.Command;
import com.epion_t3.csv.command.runner.AssertCsvDataRunner;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@CommandDefinition(id = "AssertCSVData", runner = AssertCsvDataRunner.class, assertCommand = true)
public class AssertCsvData extends Command {

    private String fileFormatConfigRef;
    private String expectedCSVDataPath;
    private String actualFlowId;
    private List<Integer> ignoreIndexes;

}
