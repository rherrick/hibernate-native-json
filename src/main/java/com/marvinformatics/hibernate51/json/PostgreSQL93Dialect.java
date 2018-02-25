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

import java.sql.Types;

import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StringType;

/**
 * An SQL dialect for Postgres 9.3
 * 
 * Includes support to JSON type
 * <a href="http://www.postgresql.org/docs/9.3/static/datatype-json.html">http://www.postgresql.org/docs/9.3/static/datatype-json.html</a>.
 * 
 * For jsonb {@link PostgreSQL94Dialect}
 *
 * @author Marvin H Froeder
 */
@SuppressWarnings("deprecation")
public class PostgreSQL93Dialect extends PostgreSQLDialect {

    public PostgreSQL93Dialect() {
        super();
        registerColumnType(Types.JAVA_OBJECT, "json");

        registerFunction("json_text",
                new SQLFunctionTemplate(StringType.INSTANCE, "?1 ->> ?2"));
    }

}
