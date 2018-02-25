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
/*
  Copyright (C) 2016 Marvin Herman Froeder (marvin@marvinformatics.com)

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.marvinformatics.hibernate51.json;

import com.marvinformatics.hibernate51.json.model.Label;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JsonUserTypeTest {

    private Logger log = LoggerFactory.getLogger(JsonUserTypeTest.class);
    private JsonUserType type = null;

    @Before
    public void createType() {
        type = new JsonUserType() {
            @Override
            public Class<?> returnedClass() {
                return Label.class;
            }
        };
    }

    @Test
    public void testConvertJsonToObject() {
        String json = "{\"value\": \"french label\", \"lang\":\"fr\"}";

        Label label = (Label) type.convertJsonToObject(json);

        assertThat(label, notNullValue());
        assertThat(label, allOf(
                hasProperty("value", is("french label")),
                hasProperty("lang", is("fr"))));
    }

    @Test
    public void testConvertObjectToJson() {
        Label label = new Label("french label", "fr", 1);

        String json = type.convertObjectToJson(label);
        log.debug("Found json: {}", json);
        assertThat(json, is("{\"value\":\"french label\",\"lang\":\"fr\",\"order\":1}"));
    }

    @Test
    public void testConvertJsonToObjectEmpty() {
        String json = "";

        Label label = (Label) type.convertJsonToObject(json);
        assertThat(label, nullValue());
    }

    @Test
    public void testConvertJsonToObjectNull() {
        String json = null;

        @SuppressWarnings("ConstantConditions")
        Label label = (Label) type.convertJsonToObject(json);
        assertThat(label, nullValue());
    }

    @Test
    public void testDeepCopy() {
        Label label = new Label("french label", "fr", 2);
        Label copy = (Label) type.deepCopy(label);

        assertThat(copy, equalTo(label));
        assertThat(copy, not(sameInstance(label)));

        assertThat(label, allOf(
                hasProperty("value", is("french label")),
                hasProperty("order", is(2)),
                hasProperty("lang", is("fr"))));

    }
}
