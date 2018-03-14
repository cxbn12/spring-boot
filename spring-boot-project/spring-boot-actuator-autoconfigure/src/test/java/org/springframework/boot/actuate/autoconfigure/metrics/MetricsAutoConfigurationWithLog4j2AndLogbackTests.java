/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.instrument.binder.logging.LogbackMetrics;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportMessage;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.testsupport.runner.classpath.ClassPathOverrides;
import org.springframework.boot.testsupport.runner.classpath.ModifiedClassPathRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MetricsAutoConfiguration} when both Log4j2 and Logback are on the
 * classpath.
 *
 * @author Andy Wilkinson
 */
@RunWith(ModifiedClassPathRunner.class)
@ClassPathOverrides({ "org.apache.logging.log4j:log4j-core:2.9.0",
		"org.apache.logging.log4j:log4j-slf4j-impl:2.9.0" })
public class MetricsAutoConfigurationWithLog4j2AndLogbackTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(MetricsAutoConfiguration.class));

	@Test
	public void doesNotConfigureLogbackMetrics() {
		this.contextRunner.run((context) -> {
			System.out.println(
					new ConditionEvaluationReportMessage(ConditionEvaluationReport
							.get((ConfigurableListableBeanFactory) context
									.getAutowireCapableBeanFactory())));
			assertThat(context).doesNotHaveBean(LogbackMetrics.class);
		});
	}

}