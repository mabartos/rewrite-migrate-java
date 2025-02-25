/*
 * Copyright 2021 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.java.migrate.logging;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

class MigrateGetLoggingMXBeanToGetPlatformMXBeanTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new MigrateGetLoggingMXBeanToGetPlatformMXBean());
    }

    @Test
    void getLoggingMXBeanToGetPlatformMXBean() {
        //language=java
        rewriteRun(
          java(
            """
              import java.util.logging.LoggingMXBean;
              import java.util.logging.LogManager;

              class Test {
                  static void method() {
                      LoggingMXBean loggingBean = LogManager.getLoggingMXBean();
                  }
              }
              """,
            """
              import java.lang.management.ManagementFactory;
              import java.lang.management.PlatformLoggingMXBean;

              class Test {
                  static void method() {
                      PlatformLoggingMXBean loggingBean = ManagementFactory.getPlatformMXBean(PlatformLoggingMXBean.class);
                  }
              }
              """
          )
        );
    }
}
