/**
 * Copyright (C) 2016 Marvin Herman Froeder (marvin@marvinformatics.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.marvinformatics.hibernate51.json;

import java.util.HashSet;
import java.util.Set;
import com.marvinformatics.hibernate51.json.model.Label;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

public class JsonSetUserTypeTest {

    private JsonSetUserType type = null;

    @Before
    public void createType() {
        type = new JsonSetUserType() {
            @Override
            public Class<?> returnedClass() {
                return Label.class;
            }
        };
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testConvertJsonToObject() throws Exception {
        String json = "[{\"value\": \"eclair\", \"lang\":\"fr\"}, {\"value\": \"saffron bun\", \"lang\":\"en\"}]";

        Set<Label> labels = (Set<Label>) type.convertJsonToObject(json);

        assertThat(labels, hasSize(2));
        assertThat(labels, containsInAnyOrder(
                allOf(hasProperty("value", is("eclair")), hasProperty("lang", is("fr"))),
                allOf(hasProperty("value", is("saffron bun")), hasProperty("lang", is("en")))));
    }

    @Test
    public void testConvertObjectToJson() throws Exception {
        Set<Label> labels = new HashSet<>();
        labels.add(new Label("eclair", "fr", 1));
        labels.add(new Label("saffron bun", "en", 2));

        String json = type.convertObjectToJson(labels);

        assertThat(json, containsString("{\"value\":\"eclair\",\"lang\":\"fr\",\"order\":1}"));
        assertThat(json, containsString("{\"value\":\"saffron bun\",\"lang\":\"en\",\"order\":2}"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDeepCopy() throws Exception {
        Set<Label> labels = new HashSet<>();
        labels.add(new Label("eclair", "fr", 3));
        labels.add(new Label("saffron bun", "en", 4));

        Set<Label> copy = (Set<Label>) type.deepCopy(labels);

        assertThat(labels, hasSize(2));
        assertThat(copy, equalTo(labels));
        assertThat(copy, not(sameInstance(labels)));

        assertThat(labels, containsInAnyOrder(
                allOf(hasProperty("value", is("eclair")), hasProperty("lang", is("fr"))),
                allOf(hasProperty("value", is("saffron bun")), hasProperty("lang", is("en")))));

    }

}
