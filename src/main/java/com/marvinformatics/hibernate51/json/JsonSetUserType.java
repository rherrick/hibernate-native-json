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

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.usertype.UserCollectionType;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JsonSetUserType extends JsonUserType implements UserCollectionType {
    public JavaType createJavaType(ObjectMapper mapper) {
        return mapper.getTypeFactory().constructCollectionType(Set.class, this.returnedClass());
    }

    public PersistentCollection instantiate(SessionImplementor session, CollectionPersister persister) throws HibernateException {
        return new PersistentSet(session);
    }

    private PersistentSet cast(Object collection) {
        return (PersistentSet) collection;
    }

    public PersistentCollection wrap(SessionImplementor session, Object collection) {
        return new PersistentSet(session, (Set<?>) collection);
    }

    public Iterator<?> getElementsIterator(Object collection) {
        return this.cast(collection).iterator();
    }

    public boolean contains(Object collection, Object entity) {
        return this.cast(collection).contains(entity);
    }

    public Object indexOf(Object collection, Object entity) {
        throw new UnsupportedOperationException();
    }

    public Object replaceElements(Object original,
            Object target,
            CollectionPersister persister,
            Object owner,
            Map copyCache,
            SessionImplementor session) throws HibernateException {
        PersistentSet originalSet = this.cast(original);
        PersistentSet targetSet = this.cast(target);
        targetSet.clear();
        targetSet.addAll(originalSet);
        return target;
    }

    public Object instantiate(int anticipatedSize) {
        return new PersistentSet();
    }
}
