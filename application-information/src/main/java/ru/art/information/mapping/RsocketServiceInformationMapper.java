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

package ru.art.information.mapping;

import ru.art.entity.Entity;
import ru.art.entity.PrimitiveMapping;
import ru.art.entity.mapper.ValueFromModelMapper;
import ru.art.entity.mapper.ValueToModelMapper;
import ru.art.information.model.RsocketServiceInformation;

import static ru.art.core.checker.CheckerForEmptiness.isNotEmpty;

public interface RsocketServiceInformationMapper {
    String id = "id";

    String methods = "methods";

    ValueToModelMapper<RsocketServiceInformation, Entity> toRsocketServiceInformation = entity -> isNotEmpty(entity) ? RsocketServiceInformation.builder()
            .id(entity.getString(id))
            .methods(entity.getMap(methods, PrimitiveMapping.StringPrimitive.toModel, RsocketServiceMethodInformationMapper.toRsocketServiceMethodInformation))
            .build() : RsocketServiceInformation.builder().build();

    ValueFromModelMapper<RsocketServiceInformation, Entity> fromRsocketServiceInformation = model -> isNotEmpty(model) ? Entity.entityBuilder()
            .stringField(id, model.getId())
            .mapField(methods, model.getMethods(), PrimitiveMapping.StringPrimitive.fromModel, RsocketServiceMethodInformationMapper.fromRsocketServiceMethodInformation)
            .build() : Entity.entityBuilder().build();
}
