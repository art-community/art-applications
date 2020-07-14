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

package ru.art.remote.scheduler.specification;

import lombok.*;
import ru.art.grpc.server.model.*;
import ru.art.grpc.server.specification.*;
import ru.art.http.server.model.*;
import ru.art.http.server.specification.*;
import ru.art.remote.scheduler.api.model.*;
import ru.art.service.*;
import ru.art.service.exception.*;
import static ru.art.core.caster.Caster.*;
import static ru.art.core.factory.CollectionsFactory.*;
import static ru.art.entity.PrimitiveMapping.*;
import static ru.art.grpc.server.constants.GrpcServerModuleConstants.*;
import static ru.art.grpc.server.model.GrpcService.GrpcMethod.*;
import static ru.art.grpc.server.model.GrpcService.*;
import static ru.art.http.constants.MimeToContentTypeMapper.*;
import static ru.art.http.server.constants.HttpServerModuleConstants.*;
import static ru.art.http.server.model.HttpService.*;
import static ru.art.http.server.module.HttpServerModule.*;
import static ru.art.remote.scheduler.api.constants.RemoteSchedulerApiConstants.Methods.*;
import static ru.art.remote.scheduler.api.constants.RemoteSchedulerApiConstants.*;
import static ru.art.remote.scheduler.api.mapping.DeferredTaskMappers.DeferredTaskCollectionMapper.*;
import static ru.art.remote.scheduler.api.mapping.DeferredTaskMappers.DeferredTaskMapper.*;
import static ru.art.remote.scheduler.api.mapping.DeferredTaskMappers.DeferredTaskRequestMapper.*;
import static ru.art.remote.scheduler.api.mapping.InfinityProcessMappers.InfinityProcessCollectionMapper.*;
import static ru.art.remote.scheduler.api.mapping.InfinityProcessMappers.InfinityProcessRequestMapper.*;
import static ru.art.remote.scheduler.api.mapping.PeriodicTaskMappers.PeriodicTaskCollectionMapper.*;
import static ru.art.remote.scheduler.api.mapping.PeriodicTaskMappers.PeriodicTaskMapper.*;
import static ru.art.remote.scheduler.api.mapping.PeriodicTaskMappers.PeriodicTaskRequestMapper.*;
import static ru.art.remote.scheduler.api.mapping.TaskIdMapper.*;
import static ru.art.remote.scheduler.constants.RemoteSchedulerModuleConstants.*;
import static ru.art.remote.scheduler.service.RemoteSchedulerService.*;
import static ru.art.service.constants.RequestValidationPolicy.*;
import java.util.*;

@Getter
public class RemoteSchedulerServiceSpecification implements Specification, HttpServiceSpecification, GrpcServiceSpecification {
    private final String serviceId = REMOTE_SCHEDULER_SERVICE_ID;

    private final HttpService httpService = httpService()
            .post(ADD_DEFERRED_TASK)
            .consumes(applicationJsonUtf8())
            .fromBody()
            .validationPolicy(VALIDATABLE)
            .requestMapper(deferredTaskRequestToModelMapper)
            .produces(applicationJsonUtf8())
            .responseMapper(taskIdMapper.getFromModel())
            .listen(ADD_DEFERRED_PATH)

            .post(ADD_PERIODIC_TASK)
            .consumes(applicationJsonUtf8())
            .fromBody()
            .validationPolicy(VALIDATABLE)
            .requestMapper(periodicTaskRequestToModelMapper)
            .produces(applicationJsonUtf8())
            .responseMapper(taskIdMapper.getFromModel())
            .listen(ADD_PERIODIC_PATH)

            .post(ADD_INFINITY_PROCESS)
            .consumes(applicationJsonUtf8())
            .fromBody()
            .validationPolicy(VALIDATABLE)
            .requestMapper(infinityProcessRequestToModelMapper)
            .produces(applicationJsonUtf8())
            .responseMapper(taskIdMapper.getFromModel())
            .listen(ADD_PROCESS_PATH)

            .post(GET_DEFERRED_TASK_BY_ID)
            .consumes(applicationJsonUtf8())
            .fromBody()
            .validationPolicy(NOT_NULL)
            .requestMapper(taskIdMapper.getToModel())
            .produces(applicationJsonUtf8())
            .responseMapper(deferredTaskFromModelMapper)
            .listen(GET_DEFERRED_PATH)

            .post(GET_PERIODIC_TASK_BY_ID)
            .consumes(applicationJsonUtf8())
            .fromBody()
            .validationPolicy(NOT_NULL)
            .requestMapper(taskIdMapper.getToModel())
            .produces(applicationJsonUtf8())
            .responseMapper(periodicTaskFromModelMapper)
            .listen(GET_PERIODIC_PATH)

            .post(GET_ALL_DEFERRED_TASKS)
            .consumes(applicationJsonUtf8())
            .responseMapper(deferredTaskCollectionFromModelMapper)
            .listen(GET_ALL_DEFERRED_PATH)

            .post(GET_ALL_PERIODIC_TASKS)
            .consumes(applicationJsonUtf8())
            .responseMapper(periodicTaskCollectionFromModelMapper)
            .listen(GET_ALL_PERIODIC_PATH)

            .post(GET_ALL_INFINITY_PROCESSES)
            .consumes(applicationJsonUtf8())
            .responseMapper(processCollectionFromModelMapper)
            .listen(GET_ALL_PROCESSES_PATH)

            .post(CANCEL_PERIODIC_TASK)
            .consumes(applicationJsonUtf8())
            .fromBody()
            .validationPolicy(NOT_NULL)
            .requestMapper(taskIdMapper.getToModel())
            .listen(cancel_PATH)

            .serve(httpServerModule().getPath());

    private final GrpcService grpcService = grpcService()
            .method(ADD_DEFERRED_TASK, grpcMethod()
                    .requestMapper(deferredTaskRequestToModelMapper)
                    .responseMapper(stringMapper.getFromModel()))
            .method(ADD_PERIODIC_TASK, grpcMethod()
                    .requestMapper(periodicTaskRequestToModelMapper)
                    .responseMapper(stringMapper.getFromModel()))
            .method(GET_DEFERRED_TASK_BY_ID, grpcMethod()
                    .requestMapper(stringMapper.getToModel())
                    .validationPolicy(NOT_NULL)
                    .responseMapper(deferredTaskFromModelMapper))
            .method(GET_PERIODIC_TASK_BY_ID, grpcMethod()
                    .requestMapper(stringMapper.getToModel())
                    .validationPolicy(NOT_NULL)
                    .responseMapper(periodicTaskFromModelMapper))
            .method(GET_ALL_DEFERRED_TASKS, grpcMethod()
                    .responseMapper(deferredTaskCollectionFromModelMapper))
            .method(GET_ALL_PERIODIC_TASKS, grpcMethod()
                    .responseMapper(periodicTaskCollectionFromModelMapper))
            .method(GET_ALL_INFINITY_PROCESSES, grpcMethod()
                    .responseMapper(processCollectionFromModelMapper))
            .method(CANCEL_PERIODIC_TASK, grpcMethod()
                    .requestMapper(stringMapper.getToModel())
                    .validationPolicy(NOT_NULL))
            .serve();

    private final List<String> serviceTypes = fixedArrayOf(GRPC_SERVICE_TYPE, HTTP_SERVICE_TYPE);


    @Override
    public <P, R> R executeMethod(String methodId, P request) {
        switch (methodId) {
            case ADD_DEFERRED_TASK:
                return cast(addDeferredTask((DeferredTaskRequest) request));
            case ADD_PERIODIC_TASK:
                return cast(addPeriodicTask((PeriodicTaskRequest) request));
            case ADD_INFINITY_PROCESS:
                return cast(addInfinityProcess((InfinityProcessRequest) request));
            case GET_DEFERRED_TASK_BY_ID:
                return cast(getDeferredTaskById((String) request));
            case GET_PERIODIC_TASK_BY_ID:
                return cast(getPeriodicTaskById((String) request));
            case GET_ALL_DEFERRED_TASKS:
                return cast(getAllDeferredTasks());
            case GET_ALL_PERIODIC_TASKS:
                return cast(getAllPeriodicTasks());
            case GET_ALL_INFINITY_PROCESSES:
                return cast(getAllInfinityProcesses());
            case CANCEL_PERIODIC_TASK:
                cancelPeriodicTask((String) request);
                return null;
            default:
                throw new UnknownServiceMethodException(serviceId, methodId);
        }
    }
}
