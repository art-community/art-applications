/*
 * ART Java
 *
 * Copyright 2019 ART
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.art.remote.scheduler.api.model;

import lombok.*;
import ru.art.entity.Value;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class InfinityProcess {
    @Setter
    private String id;

    private String executableServletPath;
    private String executableServiceId;
    private String executableMethodId;
    private Value executableRequest;
    private long executionPeriodSeconds;
    private long executionDelay;

    public InfinityProcess(String id, InfinityProcessRequest request) {
        this.id = id;
        this.executableServletPath = request.getExecutableServletPath();
        this.executableServiceId = request.getExecutableServiceId();
        this.executableMethodId = request.getExecutableMethodId();
        this.executableRequest = request.getExecutableRequest();
        this.executionPeriodSeconds = request.getExecutionPeriodSeconds();
        this.executionDelay = request.getExecutionDelay();
    }
}

