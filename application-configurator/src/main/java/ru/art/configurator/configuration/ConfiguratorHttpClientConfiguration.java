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

package ru.art.configurator.configuration;

import lombok.*;
import ru.art.core.mime.*;
import ru.art.http.client.configuration.HttpClientModuleConfiguration.*;
import ru.art.http.mapper.*;
import static ru.art.configurator.http.content.mapping.ConfiguratorHttpContentMapping.*;
import java.util.*;

@Getter
public class ConfiguratorHttpClientConfiguration extends HttpClientModuleDefaultConfiguration {
    private final Map<MimeType, HttpContentMapper> contentMappers = configureContentMappers(super.getContentMappers());
}