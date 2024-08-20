/*
 * Copyright (C) 2022 [www.mobaijun.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobaijun.xxljob.trace;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.handler.impl.MethodJobHandler;
import java.lang.reflect.Method;

/**
 * Description: 对 XxlJobSpringExecutor 做了增强，在注册 JobHandler 时使用 TraceMethodJobHandler
 * Author: [mobaijun]
 * Date: [2024/8/20 9:52]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class TraceXxlJobSpringExecutor extends XxlJobSpringExecutor {

    @Override
    protected void registJobHandler(XxlJob xxlJob, Object bean, Method executeMethod) {
        if (xxlJob == null) {
            return;
        }

        String name = xxlJob.value();
        // make and simplify the variables since they'll be called several times later
        Class<?> clazz = bean.getClass();
        String methodName = executeMethod.getName();
        if (name.trim().isEmpty()) {
            throw new RuntimeException(
                    "xxl-job method-jobhandler name invalid, for[" + clazz + "#" + methodName + "] .");
        }
        if (loadJobHandler(name) != null) {
            throw new RuntimeException("xxl-job jobhandler[" + name + "] naming conflicts.");
        }

        executeMethod.setAccessible(true);

        // init and destroy
        Method initMethod = null;
        Method destroyMethod = null;

        if (!xxlJob.init().trim().isEmpty()) {
            try {
                initMethod = clazz.getDeclaredMethod(xxlJob.init());
                initMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(
                        "xxl-job method-jobhandler initMethod invalid, for[" + clazz + "#" + methodName + "] .");
            }
        }
        if (!xxlJob.destroy().trim().isEmpty()) {
            try {
                destroyMethod = clazz.getDeclaredMethod(xxlJob.destroy());
                destroyMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(
                        "xxl-job method-jobhandler destroyMethod invalid, for[" + clazz + "#" + methodName + "] .");
            }
        }

        // registry jobhandler
        MethodJobHandler jobHandler = new MethodJobHandler(bean, executeMethod, initMethod, destroyMethod);
        TraceMethodJobHandler traceMethodJobHandler = new TraceMethodJobHandler(jobHandler);
        XxlJobExecutor.registJobHandler(name, traceMethodJobHandler);
    }
}
