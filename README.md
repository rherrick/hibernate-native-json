hibernate4-native-json
=================


[![Build Status](https://travis-ci.org/velo/hibernate-native-json.svg?branch=master)](https://travis-ci.org/velo/hibernate-native-json?branch=master) 
[![Coverage Status](https://coveralls.io/repos/github/velo/hibernate-native-json/badge.svg?branch=master)](https://coveralls.io/github/velo/hibernate-native-json?branch=master) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.marvinformatics.hibernate/hibernate-native-json/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.marvinformatics.hibernate/hibernate-native-json/) 
[![Issues](https://img.shields.io/github/issues/velo/hibernate-native-json.svg)](https://github.com/velo/hibernate-native-json/issues) 
[![Forks](https://img.shields.io/github/forks/velo/hibernate-native-json.svg)](https://github.com/velo/hibernate-native-json/network) 
[![Stars](https://img.shields.io/github/stars/velo/hibernate-native-json.svg)](https://github.com/velo/hibernate-native-json/stargazers)

:hp-tags: jpa, hibernate, json

Read/Write an object to JSON / JSON to object into a database table field (declared as a string column).
This also allow to query json.

Currently supported databases:
- postgresql

This project provided a hibernate UserType and a dialect with json support.

The UserType uses jackson object mappper to do a fast serialize/deserialize of a json string representation.  More information  [how to implements a user type](http://blog.xebia.com/2009/11/09/understanding-and-writing-hibernate-user-types/)

Check the src/test folder to see a full example.

### Example

You can serialize either a class or a Map<String, Object> (in any cases a more dynamic field is necessary).

```
@Entity
public class MyClass {

	@Type(type = "com.marvinformatics.hibernate.json.JsonListUserType")
	@Target(Label.class)
	private List<Label> labels = new ArrayList<Label>();

	@Type(type = "com.marvinformatics.hibernate.json.JsonUserType")
	private Map<String, String> extra;

}
```

Keep in mind, for collections @org.hibernate.annotations.Target is mandatory.

In order to be able to persist, query and generate DDL for this objects you need to set hibernate dialect to `PostgreSQLJsonDialect`.


```
  <property name="hibernate.dialect">com.marvinformatics.hibernate.json.PostgreSQLJsonDialect</property>
```


Now you can persist your object as a json using your hibernate session / jpa repository.

### Querying 

`json_text`: is equivalent to postgres `->>` get JSON object field as text
http://www.postgresql.org/docs/9.5/static/functions-json.html

This allow a HQL query like this:
```
	select
		json_text(i.label, 'value')
	from
		Item i
	where
		json_text(i.label, 'lang') = :lang
```

Witch will produce the following SQL:
```
    select
        item0_.label ->> 'value' as col_0_0_ 
    from
        items item0_ 
    where
        item0_.label ->> 'lang'=?
```


### DDL generation
Considering this class:
```
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Type(type = "com.marvinformatics.hibernate.json.JsonUserType")
    @Column(name = "label")
    private Label label;

    @Type(type = "com.marvinformatics.hibernate.json.JsonUserType")
    private Map<String, String> extra;
```

This in produce the following DDL:
```
    create table items (
        id int8 not null,
        extra jsonb,
        label jsonb,
        name varchar(255),
        ORDER_ID int8,
        primary key (id)
    )
```


More functions and more databases are comming.  Wanna sppeed up the process? Click [here](https://github.com/velo/hibernate-native-json/compare)

