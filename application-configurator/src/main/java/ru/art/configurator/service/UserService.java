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

package ru.art.configurator.service;

import ru.art.configurator.api.model.UserRequest;
import ru.art.configurator.api.model.UserResponse;

import static ru.art.configurator.dao.UserDao.*;
import static ru.art.core.checker.CheckerForEmptiness.*;

public interface UserService {
    static UserResponse login(UserRequest userRequest) {
        if (!userExists(userRequest.getName(), userRequest.getPassword())) return new UserResponse(false);
        return new UserResponse(true, getToken());
    }

    static void register(String userName, String password) {
        saveUser(userName, password);
    }

    static boolean checkToken(String requestToken) {
        String token = getToken();
        if (isEmpty(token)) return false;
        return token.equals(requestToken);
    }
}
