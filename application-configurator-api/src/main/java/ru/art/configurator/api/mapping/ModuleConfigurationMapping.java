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

package ru.art.configurator.api.mapping;

import ru.art.configurator.api.model.ModuleConfiguration;
import ru.art.entity.*;
import ru.art.entity.mapper.ValueFromModelMapper.*;
import ru.art.entity.mapper.*;
import ru.art.entity.mapper.ValueToModelMapper.*;
import static ru.art.configurator.api.mapping.ModuleKeyMapping.*;
import static ru.art.entity.Entity.*;
import static ru.art.entity.mapper.ValueMapper.*;

public interface ModuleConfigurationMapping {
    EntityToModelMapper<ModuleConfiguration> toModel = entity -> ModuleConfiguration.builder()
            .moduleKey(moduleKeyMapper.map(entity.getEntity("moduleKey")))
            .configuration(entity.getEntity("configuration"))
            .build();

    EntityFromModelMapper<ModuleConfiguration> fromModel = model -> entityBuilder()
            .entityField("moduleKey", model.getConfiguration())
            .entityField("configuration", model.getConfiguration())
            .build();

    ValueMapper<ModuleConfiguration, Entity> moduleConfigurationMapper = mapper(fromModel, toModel);
}
