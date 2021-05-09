package com.epion_t3.csv.configuration.model;

import com.epion_t3.core.common.annotation.CustomConfigurationDefinition;
import com.epion_t3.core.common.bean.scenario.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@CustomConfigurationDefinition(id="FileFormatConfiguration")
public class FileFormatConfiguration extends Configuration {

    private String delimiter = ",";

    private String escape;

    private String  quote = "\"";

    private String recordSeparator = "LF";
}
