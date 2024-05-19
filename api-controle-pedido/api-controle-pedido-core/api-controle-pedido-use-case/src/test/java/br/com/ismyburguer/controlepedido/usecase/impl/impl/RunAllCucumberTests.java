package br.com.ismyburguer.controlepedido.usecase.impl.impl;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;

@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class RunAllCucumberTests {
}