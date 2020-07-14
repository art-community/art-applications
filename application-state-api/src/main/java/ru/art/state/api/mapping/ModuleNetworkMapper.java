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

package ru.art.state.api.mapping;

import ru.art.entity.*;
import ru.art.entity.mapper.*;
import ru.art.state.api.model.*;
import static java.util.Comparator.*;
import static ru.art.core.factory.CollectionsFactory.*;

public interface ModuleNetworkMapper {
    ValueToModelMapper<ModuleNetwork, Entity> toModuleNetwork = entity -> ModuleNetwork.builder()
            .endpoints(priorityQueueOf(comparingInt(ModuleEndpoint::getSessions), entity.getEntityList("endpoints", ModuleEndpointMapper.toModuleEndpoint)))
            .build();

    ValueFromModelMapper<ModuleNetwork, Entity> fromModuleNetwork = model -> Entity.entityBuilder()
            .entityCollectionField("endpoints", model.getEndpoints(), ModuleEndpointMapper.fromModuleEndpoint)
            .build();
}
