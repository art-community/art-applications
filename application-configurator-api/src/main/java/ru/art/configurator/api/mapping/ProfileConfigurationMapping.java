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

import ru.art.configurator.api.model.ProfileConfiguration;
import ru.art.entity.*;
import ru.art.entity.mapper.ValueFromModelMapper.*;
import ru.art.entity.mapper.*;
import ru.art.entity.mapper.ValueToModelMapper.*;
import static ru.art.entity.Entity.*;
import static ru.art.entity.mapper.ValueMapper.*;

public interface ProfileConfigurationMapping {
    EntityToModelMapper<ProfileConfiguration> toModel = entity -> ProfileConfiguration.builder()
            .profileId(entity.getString("profileId"))
            .configuration(entity.getEntity("configuration"))
            .build();

    EntityFromModelMapper<ProfileConfiguration> fromModel = model -> entityBuilder()
            .stringField("profileId", model.getProfileId())
            .entityField("configuration", model.getConfiguration())
            .build();

    ValueMapper<ProfileConfiguration, Entity> profileConfigurationMapper = mapper(fromModel, toModel);
}
