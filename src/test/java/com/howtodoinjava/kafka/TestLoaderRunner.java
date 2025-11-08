package com.howtodoinjava.kafka;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features") // 1. Tells Cucumber where to find .feature files
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.howtodoinjava.kafka") // 2. Tells Cucumber where to find step definitions (glue code)
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports/index.html") // 3. Generates the final reports
public class TestLoaderRunner {
    // This class remains empty and is only used for annotations.
}