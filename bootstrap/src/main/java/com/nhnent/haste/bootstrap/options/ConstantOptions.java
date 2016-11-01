/*
* Copyright 2016 NHN Entertainment Corp.
*
* NHN Entertainment Corp. licenses this file to you under the Apache License,
* version 2.0 (the "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at:
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.nhnent.haste.bootstrap.options;

import com.nhnent.haste.common.Check;

import java.util.HashMap;
import java.util.Map;

public abstract class ConstantOptions<T extends Constant> {
    private final Map<String, T> constants = new HashMap<>();

    private int nextId = 1;

    public T valueOf(String name) {
        T c;

        synchronized ((constants)) {
            if (exists(name)) {
                c = constants.get(name);
            } else {
                c = setValue(name);
            }
        }

        return c;
    }

    public T setValue(String name) {
        synchronized (constants) {
            if (exists(name)) {
                throw new IllegalArgumentException(name + " already exists");
            }

            T value = newConstant(nextId, name);
            constants.put(name, value);
            return value;
        }
    }

    public boolean exists(String name) {
        Check.NotEmpty(name, "name");
        synchronized (constants) {
            return constants.containsKey(name);
        }

    }

    protected abstract T newConstant(int id, String name);
}
