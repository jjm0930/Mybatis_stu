# MyBatis 

环境：

- JDK1.8

- MySql8.0.28

- maven3.8.5



## 1、简介

### 1.1什么是MyBatis

- MyBatis 是一款优秀的**持久层框架**
- MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集的过程
- MyBatis 可以使用简单的 XML 或注解来配置和映射原生信息，将接口和 Java 的 实体类 【Plain Old Java Objects,普通的 Java对象】映射成数据库中的记录。
- MyBatis 本是apache的一个开源项目ibatis, 2010年这个项目由apache 迁移到了google code，并且改名为MyBatis 。
- 2013年11月迁移到**Github** .
- Mybatis官方文档 : http://www.mybatis.org/mybatis-3/zh/index.html
- GitHub : https://github.com/mybatis/mybatis-3

### 1.2持久化

**持久化是将程序数据在持久状态和瞬时状态间转换的机制。**

- 即把数据（如内存中的对象）保存到可永久保存的存储设备中（如磁盘）。持久化的主要应用是**将内存中的对象存储在数据库中**，或者存储在磁盘文件中、XML数据文件中等等。
- JDBC就是一种持久化机制。文件IO也是一种持久化机制。
- 在生活中 : 将鲜肉冷藏，吃的时候再解冻的方法也是。将水果做成罐头的方法也是。

**为什么需要持久化服务呢？那是由于内存本身的缺陷引起的**

- 内存断电后数据会丢失，但有一些对象是无论如何都不能丢失的，比如银行账号等，遗憾的是，人们还无法保证内存永不掉电。
- 内存过于昂贵，与硬盘、光盘等外存相比，内存的价格要高2~3个数量级，而且维持成本也高，至少需要一直供电吧。所以即使对象不需要永久保存，也会因为内存的容量限制不能一直呆在内存中，需要持久化来缓存到外存。

### 1.3什么是持久层

**什么是持久层？**

- 完成持久化工作的代码块 .  ---->  dao层 【DAO (Data Access Object)  数据访问对象】
- 大多数情况下特别是企业级应用，数据持久化往往也就意味着将内存中的数据保存到磁盘上加以固化，而持久化的实现过程则大多通过各种**关系数据库**来完成。
- 不过这里有一个字需要特别强调，也就是所谓的“层”。对于应用系统而言，数据持久功能大多是必不可少的组成部分。也就是说，我们的系统中，已经天然的具备了“持久层”概念？也许是，但也许实际情况并非如此。之所以要独立出一个“持久层”的概念,而不是“持久模块”，“持久单元”，也就意味着，我们的系统架构中，应该有一个相对独立的逻辑层面，专注于数据持久化逻辑的实现.
- 与系统其他部分相对而言，这个层面应该具有一个较为清晰和严格的逻辑边界。【说白了就是用来操作数据库存在的！】

### 1.4为什么需要使用MyBatis

- Mybatis就是帮助程序猿将数据存入数据库中 , 和从数据库中取数据 .

- 传统的jdbc操作 , 有很多重复代码块 .比如 : 数据取出时的封装 , 数据库的建立连接等等... , 通过框架可以减少重复代码,提高开发效率 .

- MyBatis 是一个半自动化的**ORM框架 (Object Relationship Mapping) -->对象关系映射**

- 所有的事情，不用Mybatis依旧可以做到，只是用了它，所有实现会更加简单！**技术没有高低之分，只有使用这个技术的人有高低之别**

- MyBatis的优点

- - 简单易学：本身就很小且简单。没有任何第三方依赖，最简单安装只要两个jar文件+配置几个sql映射文件就可以了，易于学习，易于使用，通过文档和源代码，可以比较完全的掌握它的设计思路和实现。

- - 灵活：mybatis不会对应用程序或者数据库的现有设计强加任何影响。sql写在xml里，便于统一管理和优化。通过sql语句可以满足操作数据库的所有需求。
  - 解除sql与程序代码的耦合：通过提供DAO层，将业务逻辑和数据访问逻辑分离，使系统的设计更清晰，更易维护，更易单元测试。sql和代码的分离，提高了可维护性。
  - 提供xml标签，支持编写动态sql。
  - .......

- 最重要的一点，使用的人多！公司需要！

## 2、第一个MyBatis程序

**思路流程：搭建环境-->导入Mybatis--->编写代码--->测试**

### 2.1创建数据库

```sql
CREATE DATABASE mybatis;
use mybatis;
CREATE TABLE user(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL DEFAULT '',
	pwd VARCHAR(50) NOT NULL DEFAULT ''
	);
	INSERT INTO user
	VALUES(NULL,'李白',MD5(123456));
	SELECT *
	FROM user;
```

### 2.2导入相关jar包

```xml
 <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.28</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.9</version>
        </dependency>
```

### 2.3编写MyBatis核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value="12345678"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="com/jjm/dao/userMapper.xml"/>
    </mappers>
</configuration>
```

### 2.4编写MyBatis工具类

```java
package com.jjm.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author jjm
 * @version 1.0
 */
public class MyBatisUtil {
    private static  SqlSessionFactory sqlSessionFactory;
    static {
        try {
            String resource="mybatis-config.xml";
            InputStream inputStream= Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SqlSession getSession(){
        return sqlSessionFactory.openSession();
    }
}
```

### 2.5编写实体类

```java
public class User {
    private int id;
    private String name;
    private String pwd;

    public User(){}

    public User( String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }
		get、set以及toString方法
```

### 2.6编写接口类

```java
package com.jjm.dao;

import com.jjm.pojo.User;

import java.util.List;

/**
 * @author jjm
 * @version 1.0
 */
public interface UserMapper {
    List<User> getAllUser();
    int addUser(User user);	//添加
    int updateUser(int id);	//修改
   	int deleteUser(String name);//删除

}
```

### 2.7 编写Mapper.xml配置文件

**namespace 十分重要，不能写错**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.jjm.dao.UserMapper">
    <select id="getAllUser" resultType="com.jjm.pojo.User">
        select * from user
    </select>
    <insert id="addUser" parameterType="com.jjm.pojo.User">
        insert into user(name ,pwd) values (#{name},MD5(#{pwd}))
    </insert>
    <update id="updateUser" >
        update user set pwd =MD5(123456) where id=#{id}
    </update>
  	  <delete id="deleteUser">
        delete from user where name =#{name}
    </delete>
</mapper>
```

问题：**Maven静态资源过滤问题**

在pom.xml中添加如下代码

```xml
<build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
    </resources>
</build>
```

### 2.8测试类

```java
package com.jjm;

import com.jjm.dao.UserMapper;
import com.jjm.pojo.User;
import com.jjm.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import sun.security.provider.MD5;

import java.util.List;

/**
 * @author jjm
 * @version 1.0
 */
public class mainTest {
    @Test
    public void addTest() {
        SqlSession session = MyBatisUtil.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        if (mapper.addUser(new User("花木兰", "123455")) > 0)
            System.out.println("插入成功");
        session.commit();
        session.close();
    }
    @Test
    public void update(){
        SqlSession session = MyBatisUtil.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        if(mapper.updateUser(2)>0)
            System.out.println("修改成功");
        session.commit();
        session.close();
    }
   @Test
    public void delete(){
        SqlSession session = MyBatisUtil.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        if(mapper.deleteUser("李白")>0){
            System.out.println("删除成功！");
        }
        session.commit();
        session.close();
    }
    public static void main(String[] args) {
        SqlSession session = MyBatisUtil.getSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<User> allUser = mapper.getAllUser();
            for (User u : allUser) {
                System.out.println(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

}
```

## 3、CURD操作

上述已完成

**配置文件中namespace中的名称为对应Mapper接口或者Dao接口的完整包名,必须一致！**

```xml
<mapper namespace="com.jjm.dao.UserMapper">
    <select id="getAllUser" resultType="com.jjm.pojo.User">
        select * from user
    </select>
    <insert id="addUser" parameterType="com.jjm.pojo.User">
        insert into user(name ,pwd) values (#{name},MD5(#{pwd}))
    </insert>
    <update id="updateUser" >
        update user set pwd =MD5(123456) where id=#{id}
    </update>
    <delete id="deleteUser">
        delete from user where name =#{name}
    </delete>
</mapper>
```

### 3.1 Select

- select标签是mybatis中最常用的标签之一

一个简单查询的 select 元素是非常简单的。比如：

```xml
<select id="selectPerson" parameterType="int" resultType="hashmap">
  SELECT * FROM PERSON WHERE ID = #{id}
</select>
```

这个语句名为 selectPerson，接受一个 int（或 Integer）类型的参数，并返回一个 HashMap 类型的对象，其中的键是列名，值便是结果行中的对应值。

注意参数符号：

```xml
#{id}
```

这就告诉 MyBatis 创建一个预处理语句（PreparedStatement）参数，在 JDBC 中，这样的一个参数在 SQL 中会由一个“?”来标识，并被传递到一个新的预处理语句中，就像这样：

```java
// 近似的 JDBC 代码，非 MyBatis 代码...
String selectPerson = "SELECT * FROM PERSON WHERE ID=?";
PreparedStatement ps = conn.prepareStatement(selectPerson);
ps.setInt(1,id);
```

当然，使用 JDBC 就意味着使用更多的代码，以便提取结果并将它们映射到对象实例中，而这就是 MyBatis 的拿手好戏。参数和结果映射的详细细节会分别在后面单独的小节中说明。

select 元素允许你配置很多属性来配置每条语句的行为细节。

- select语句有很多属性可以详细配置每一条SQL语句

- - 接口中的方法名与映射文件中的SQL语句ID 一一对应

- ![image-20220511215413739](https://tva1.sinaimg.cn/large/e6c9d24egy1h24t3hsragj225d0u0am6.jpg)

根据用户名密码查询

```Java
 User selectUserByNP(@Param("username") String username, @Param("pwd") String pwd);
```

```xml
<select id="selectUserByNP" resultType="com.jjm.pojo.User">
    select * from user where name =#{username} and pwd=MD5(#{pwd})
</select>
```

也可以用map实现

### 3.2 Insert、Update、Delete

```xml
 <insert id="addUser" parameterType="com.jjm.pojo.User">
        insert into user(name ,pwd) values (#{name},MD5(#{pwd}))
    </insert>
    <update id="updateUser" >
        update user set pwd =MD5(123456) where id=#{id}
    </update>
    <delete id="deleteUser">
        delete from user where name =#{name}
    </delete>
```

**注意增、删、改操作需要提交事务！**

```java
 public void delete(){
        SqlSession session = MyBatisUtil.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        if(mapper.deleteUser("李白")>0){
            System.out.println("删除成功！");
        }
        session.commit();//提交事务,重点!不写的话不会提交到数据库
        session.close();
    }
```

### 3.3小结

- 所有的增删改操作都需要提交事务！
- 接口所有的普通参数，尽量都写上@Param参数，尤其是多个参数时，必须写上！
- 有时候根据业务的需求，可以考虑使用map传递参数！
- 为了规范操作，在SQL的配置文件中，我们尽量将Parameter参数和resultType都写上！

### 3.4万能的Map

**如果参数过多，我们可以考虑直接使用Map实现，如果参数比较少，直接传递参数即可**

1、在接口方法中，参数直接传递Map；

```java
User selectUserByNP2(Map<String,Object> map);
```

2、编写sql语句的时候，需要传递参数类型，参数类型为map

```xml
<select id="selectUserByNP2" parameterType="map" resultType="com.kuang.pojo.User">
select * from user where name = #{username} and pwd = #{pwd}
</select>
```

3、在使用方法的时候，Map的 key 为 sql中取的值即可，没有顺序要求！

```java
Map<String, Object> map = new HashMap<String, Object>();
map.put("username","小明");
map.put("pwd","123456");
User user = mapper.selectUserByNP2(map);
```

### 3.5模糊匹配

**模糊查询like语句该怎么写?**

第1种：在Java代码中添加sql通配符。

```xml
string wildcardname = “%smi%”;
list<name> names = mapper.selectlike(wildcardname);

<select id=”selectlike”>
select * from foo where bar like #{value}
</select>
```

第2种：在sql语句中拼接通配符，会引起sql注入

```xml
string wildcardname = “smi”;
list<name> names = mapper.selectlike(wildcardname);

<select id=”selectlike”>
    select * from foo where bar like "%"#{value}"%"
</select>
```

## 4、配置解析

### 4.1核心配置文件

- mybatis-config.xml 系统核心配置文件
- MyBatis 的配置文件包含了会深深影响 MyBatis 行为的设置和属性信息。

- configuration（配置）

  - [properties（属性）](https://mybatis.org/mybatis-3/zh/configuration.html#properties)
  - [settings（设置）](https://mybatis.org/mybatis-3/zh/configuration.html#settings)
  - [typeAliases（类型别名）](https://mybatis.org/mybatis-3/zh/configuration.html#typeAliases)
  - [typeHandlers（类型处理器）](https://mybatis.org/mybatis-3/zh/configuration.html#typeHandlers)
  - [objectFactory（对象工厂）](https://mybatis.org/mybatis-3/zh/configuration.html#objectFactory)
  - [plugins（插件）](https://mybatis.org/mybatis-3/zh/configuration.html#plugins)
  - environments（环境配置）
  - environment（环境变量）
    - transactionManager（事务管理器）
    - dataSource（数据源）

  - [databaseIdProvider（数据库厂商标识）](https://mybatis.org/mybatis-3/zh/configuration.html#databaseIdProvider)
  - [mappers（映射器）](https://mybatis.org/mybatis-3/zh/configuration.html#mappers)

**注意元素节点的顺序！顺序不对会报错**

### 4.2环境配置

MyBatis 可以配置成适应多种环境，这种机制有助于将 SQL 映射应用于多种数据库之中

**尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境。**

environments 元素定义了如何配置环境。

- MyBatis默认事务管理器就是JDBC，连接池POOLED。
- 默认使用的环境 ID（比如：default="development"）。
- 每个 environment 元素定义的环境 ID（比如：id="development"）。

```xml
<environments default="development">
  <environment id="development">
    <transactionManager type="JDBC">
      <property name="..." value="..."/>
    </transactionManager>
    <dataSource type="POOLED">
      <property name="driver" value="${driver}"/>
      <property name="url" value="${url}"/>
      <property name="username" value="${username}"/>
      <property name="password" value="${password}"/>
    </dataSource>
  </environment>
  <environment id="test">
    <transactionManager type="JDBC">
      <property name="..." value="..."/>
    </transactionManager>
    <dataSource type="POOLED">
      <property name="driver" value="${driver}"/>
      <property name="url" value="${url}"/>
      <property name="username" value="${username}"/>
      <property name="password" value="${password}"/>
    </dataSource>
  </environment>
</environments>
```

### 4.3属性

些属性可以在外部进行配置，并可以进行动态替换。你既可以在典型的 Java 属性文件中配置这些属性，也可以在 properties 元素的子元素中设置【db.properties】

编写一个配置文件 db.properties

```properties
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis
username=root
password=12345678"
```

在核心配置文件中引入

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
  	<!--引入外部配置文件-->
   	<properties resourse="db.properties">
  		<property name="password" value="123456"/>
  	</properties>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
      					<property name="url" value="${url}"/>
      					<property name="username" value="${username}"/>
      					<property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="com/jjm/dao/userMapper.xml"/>
    </mappers>
</configuration>
```

- 可以直接引入外部配置文件
- 可以在其中增加一些属性配置
- 如果有两个文件有同一个字段，**优先使用外部配置文件**

### 4.4别名

类型别名是为 Java 类型设置一个短的名字。它只和 XML 配置有关，存在的意义仅在于用来减少类完全限定名的冗余。

```xml
<!--配置别名,注意顺序-->
<typeAliases>
   <typeAlias type="com.jjm.pojo.User" alias="User"/>
</typeAliases>
```

当这样配置时，`User`可以用在任何使用`com.jjm.pojo.User`的地方。

可以指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean，比如:

```xml
<typeAliases>
   <package name="com.jjm.pojo"/>
</typeAliases>
```

每一个在包 `com.jjm.pojo` 中的 Java Bean，在没有注解的情况下，会使用 Bean 的首字母小写的非限定类名来作为它的别名。

若有注解，则别名为其注解值。见下面的例子：

```java
@Alias("user")
public class User {
  ...
}
```

### 4.5设置

![image-20220511232705853](https://tva1.sinaimg.cn/large/e6c9d24egy1h24vs2vuo9j21tq07yt9m.jpg)

### 4.6映射器

- 映射器 : 定义映射SQL语句文件
- 既然 MyBatis 的行为其他元素已经配置完了，我们现在就要定义 SQL 映射语句了。但是首先我们需要告诉 MyBatis 到哪里去找到这些语句。Java 在自动查找这方面没有提供一个很好的方法，所以最佳的方式是告诉 MyBatis 到哪里去找映射文件。你可以使用相对于类路径的资源引用， 或完全限定资源定位符（包括 `file:///` 的 URL），或类名和包名等。映射器是MyBatis中最核心的组件之一，在MyBatis 3之前，只支持xml映射器，即：所有的SQL语句都必须在xml文件中配置。而从MyBatis 3开始，还支持接口映射器，这种映射器方式允许以Java代码的方式注解定义SQL语句，非常简洁。

**引入资源方式**

```xml
<!-- 使用相对于类路径的资源引用 -->
<mappers>
 <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>
<!-- 使用完全限定资源定位符（URL） -->
<mappers>
 <mapper url="file:///var/mappers/AuthorMapper.xml"/>
</mappers>
<!--
使用映射器接口实现类的完全限定类名
需要配置文件名称和接口名称一致，并且位于同一目录下
-->
<mappers>
 <mapper class="org.mybatis.builder.AuthorMapper"/>
</mappers>
<!--
将包内的映射器接口实现全部注册为映射器
但是需要配置文件名称和接口名称一致，并且位于同一目录下
-->
<mappers>
 <package name="org.mybatis.builder"/>
</mappers>
```

**Mapper文件**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
       PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
       "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.kuang.mapper.UserMapper">
   
</mapper>
```

- namespace中文意思：命名空间，作用如下：

- - namespace的命名必须跟某个接口同名
  - 接口中的方法与映射文件中sql语句id应该一一对应

- 1. namespace和子元素的id联合保证唯一  , 区别不同的mapper
  2. 绑定DAO接口
  3. namespace命名规则 : 包名+类名

MyBatis 的真正强大在于它的映射语句，这是它的魔力所在。由于它的异常强大，映射器的 XML 文件就显得相对简单。如果拿它跟具有相同功能的 JDBC 代码进行对比，你会立即发现省掉了将近 95% 的代码。MyBatis 为聚焦于 SQL 而构建，以尽可能地为你减少麻烦。

### 4.7生命周期和作用域（Scope）

理解我们目前已经讨论过的不同作用域和生命周期类是至关重要的，因为错误的使用会导致非常严重的并发问题。

我们可以先画一个流程图，分析一下Mybatis的执行过程！



![图片](https://tva1.sinaimg.cn/large/e6c9d24egy1h24w9it98nj20mw0g2wf2.jpg)

**作用域理解**

- SqlSessionFactoryBuilder 的作用在于创建 SqlSessionFactory，创建成功后，SqlSessionFactoryBuilder 就失去了作用，所以它只能存在于创建 SqlSessionFactory 的方法中，而不要让其长期存在。因此 **SqlSessionFactoryBuilder 实例的最佳作用域是方法作用域**（也就是局部方法变量）。

- SqlSessionFactory 可以被认为是一个数据库连接池，它的作用是创建 SqlSession 接口对象。因为 MyBatis 的本质就是 Java 对数据库的操作，所以 **SqlSessionFactory 的生命周期存在于整个 MyBatis 的应用之中**，所以一旦创建了 SqlSessionFactory，就要长期保存它，直至不再使用 MyBatis 应用，所以可以认为 SqlSessionFactory 的生命周期就等同于 MyBatis 的应用周期。

- 由于 SqlSessionFactory 是**一个对数据库的连接池**，所以它占据着数据库的连接资源。如果创建多个 SqlSessionFactory，那么就存在多个数据库连接池，这样不利于对数据库资源的控制，也会导致数据库连接资源被消耗光，出现系统宕机等情况，所以尽量避免发生这样的情况。

- 因此在一般的应用中我们往往希望 SqlSessionFactory 作为一个单例，让它在应用中被共享。所以说 **SqlSessionFactory 的最佳作用域是应用作用域。**

- 如果说 SqlSessionFactory 相当于数据库连接池，那么 SqlSession 就相当于一个数据库连接（Connection 对象），你可以在一个事务里面执行多条 SQL，然后通过它的 commit、rollback 等方法，提交或者回滚事务。所以它应该存活在一个业务请求中，处理完整个请求后，应该关闭这条连接，让它归还给 SqlSessionFactory，否则数据库资源就很快被耗费精光，系统就会瘫痪，所以用 try...catch...finally... 语句来保证其正确关闭。

- **所以 SqlSession 的最佳的作用域是请求或方法作用域。**

  

  ![图片](https://tva1.sinaimg.cn/large/e6c9d24egy1h24wa3rmd8j20mv0h50tp.jpg)

## 5、结果映射

`resultMap` 元素是 MyBatis 中最重要最强大的元素。它可以让你从 90% 的 JDBC `ResultSets` 数据提取代码中解放出来，并在一些情形下允许你进行一些 JDBC 不支持的操作。实际上，在为一些比如连接的复杂语句编写映射代码的时候，一份 `resultMap` 能够代替实现同等功能的数千行代码。ResultMap 的设计思想是，对简单的语句做到零配置，对于复杂一点的语句，只需要描述语句之间的关系就行了。

在学习了上面的知识后，你会发现上面的例子没有一个需要显式配置 `ResultMap`，这就是 `ResultMap` 的优秀之处——你完全可以不用显式地配置它们。 虽然上面的例子不用显式配置 `ResultMap`。 但为了讲解，我们来看看如果在刚刚的示例中，显式使用外部的 `resultMap` 会怎样，这也是**解决列名不匹配**的另外一种方式。

```xml
<resultMap id="userResultMap" type="User">
  <id property="id" column="user_id" />
  <result property="username" column="user_name"/>
  <result property="password" column="hashed_password"/>
</resultMap>
```

然后在引用它的语句中设置 `resultMap` 属性就行了（注意我们去掉了 `resultType` 属性）。比如:

```xml
<select id="selectUsers" resultMap="userResultMap">
  select user_id, user_name, hashed_password
  from some_table
  where id = #{id}
</select>
```

如果这个世界总是这么简单就好了。

## 6、日志

### 6.1日志工厂

Mybatis 通过使用内置的日志工厂提供日志功能。内置日志工厂将会把日志工作委托给下面的实现之一：

- SLF4J
- Apache Commons Logging
- Log4j 2
- Log4j (deprecated since 3.5.9)
- JDK logging

![image-20220511232705853](https://tva1.sinaimg.cn/large/e6c9d24egy1h25v40qhdtj21tq07yq3u.jpg)

指定 MyBatis 应该使用哪个日志记录实现。如果此设置不存在，则会自动发现日志记录实现。

```xml
<settings>
    <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>
```

### 6.2 Log4j

**简介：**

- Log4j是Apache的一个开源项目
- 通过使用Log4j，我们可以控制日志信息输送的目的地：控制台，文本，GUI组件....
- 我们也可以控制每一条日志的输出格式；
- 通过定义每一条日志信息的级别，我们能够更加细致地控制日志的生成过程。最令人感兴趣的就是，这些可以通过一个配置文件来灵活地进行配置，而不需要修改应用的代码。

1.导入log4j的包

```xml
<!-- https://mvnrepository.com/artifact/log4j/log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

2.编写配置文件

```properties
#将等级为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下面的代码
log4j.rootLogger=DEBUG,console,file

#控制台输出的相关设置
log4j.appender.console = org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.out
log4j.appender.console.Threshold=DEBUG
log4j.appender.console.layout = org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=[%c]-%m%n

#文件输出的相关设置
log4j.appender.file = org.apache.log4j.RollingFileAppender
log4j.appender.file.File=./log/jjm.log
log4j.appender.file.MaxFileSize=10mb
log4j.appender.file.Threshold=DEBUG
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=[%p][%d{yy-MM-dd}][%c]%m%n

#日志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
```

3.配置log4j的实现

```xml
<settings>    
	<setting name="logImpl" value="LOG4J"/>
</settings>
```

4.在程序中使用Log4j进行输出！

```java
//注意导包：org.apache.log4j.Logger
static Logger logger = Logger.getLogger(MyTest.class);

@Test
public void selectUser() {
   logger.info("info：进入selectUser方法");
   logger.debug("debug：进入selectUser方法");
   logger.error("error: 进入selectUser方法");
   SqlSession session = MybatisUtils.getSession();
   UserMapper mapper = session.getMapper(UserMapper.class);
   List<User> users = mapper.selectUser();
   for (User user: users){
       System.out.println(user);
  }
   session.close();
}
```

5.测试，看控制台输出！

- 使用Log4j 输出日志
- 可以看到还生成了一个日志的文件 【需要修改file的日志级别】

## 7、分页

### 7.1Limit实现分页

**思考：为什么需要分页？**

在学习mybatis等持久层框架的时候，会经常对数据进行增删改查操作，使用最多的是对数据库进行查询操作，如果查询大量数据的时候，我们往往使用分页进行查询，也就是每次处理小部分数据，这样对数据库压力就在可控范围内。

**使用Limit实现分页**

```mysql
#语法
SELECT * FROM table LIMIT stratIndex，pageSize
#如果只给定一个参数，它表示返回最大的记录行数目：   
SELECT * FROM table LIMIT 5; //检索前 5 个记录行  

#换句话说，LIMIT n 等价于 LIMIT 0,n。 
```

1、修改Mapper文件

```xml
<select id="selectUser" parameterType="map" resultType="user">
  select * from user limit #{startIndex},#{pageSize}
</select>
```

3、在测试类中传入参数测试

- 推断：起始位置 =  （当前页面 - 1 ） * 页面大小

```java
//分页查询 , 两个参数startIndex , pageSize
@Test
public void testSelectUser() {
   SqlSession session = MybatisUtils.getSession();
   UserMapper mapper = session.getMapper(UserMapper.class);

   int currentPage = 1;  //第几页
   int pageSize = 2;  //每页显示几个
   Map<String,Integer> map = new HashMap<String,Integer>();
   map.put("startIndex",(currentPage-1)*pageSize);
   map.put("pageSize",pageSize);

   List<User> users = mapper.selectUser(map);

   for (User user: users){
       System.out.println(user);
  }

   session.close();
}
```

### 7.2RowBounds分页

我们除了使用Limit在SQL层面实现分页，也可以使用RowBounds在Java代码层面实现分页，当然此种方式作为了解即可。我们来看下如何实现的！

**步骤：**

1、mapper接口

```java
//选择全部用户RowBounds实现分页
List<User> getUserByRowBounds();
```

2、mapper文件

```xml
<select id="getUserByRowBounds" resultType="user">
select * from user
</select>
```

3、测试类

在这里，我们需要使用RowBounds类

```java
@Test
public void testUserByRowBounds() {
   SqlSession session = MybatisUtils.getSession();

   int currentPage = 2;  //第几页
   int pageSize = 2;  //每页显示几个
   RowBounds rowBounds = new RowBounds((currentPage-1)*pageSize,pageSize);

   //通过session.**方法进行传递rowBounds，[此种方式现在已经不推荐使用了]
   List<User> users = session.selectList("com.kuang.mapper.UserMapper.getUserByRowBounds", null, rowBounds);

   for (User user: users){
       System.out.println(user);
  }
   session.close();
}
```

### 7.3MyBatis分页插件

**PageHelper**

## 8、注解开发

### 8.1面向接口编程

- 大家之前都学过面向对象编程，也学习过接口，但在真正的开发中，很多时候我们会选择面向接口编程
- **根本原因 :  解耦 , 可拓展 , 提高复用 , 分层开发中 , 上层不用管具体的实现 , 大家都遵守共同的标准 , 使得开发变得容易 , 规范性更好**
- 在一个面向对象的系统中，系统的各种功能是由许许多多的不同对象协作完成的。在这种情况下，各个对象内部是如何实现自己的,对系统设计人员来讲就不那么重要了；
- 而各个对象之间的协作关系则成为系统设计的关键。小到不同类之间的通信，大到各模块之间的交互，在系统设计之初都是要着重考虑的，这也是系统设计的主要工作内容。面向接口编程就是指按照这种思想来编程。

**关于接口的理解**

- 接口从更深层次的理解，应是定义（规范，约束）与实现（名实分离的原则）的分离。

- 接口的本身反映了系统设计人员对系统的抽象理解。

- 接口应有两类：

- - 第一类是对一个个体的抽象，它可对应为一个抽象体(abstract class)；
  - 第二类是对一个个体某一方面的抽象，即形成一个抽象面（interface）；

- 一个体有可能有多个抽象面。抽象体与抽象面是有区别的。

**三个面向区别**

- 面向对象是指，我们考虑问题时，以对象为单位，考虑它的属性及方法 .
- 面向过程是指，我们考虑问题时，以一个具体的流程（事务过程）为单位，考虑它的实现 .
- 接口设计与非接口设计是针对复用技术而言的，与面向对象（过程）不是一个问题.更多的体现就是对系统整体的架构

### 8.2使用注解开发

- **mybatis最初配置信息是基于 XML ,映射语句(SQL)也是定义在 XML 中的。而到MyBatis 3提供了新的基于注解的配置。不幸的是，Java 注解的的表达力和灵活性十分有限。最强大的 MyBatis 映射并不能用注解来构建**

- sql 类型主要分成 :

- - @select ()
  - @update ()
  - @Insert ()
  - @delete ()



**注意：**利用注解开发就不需要mapper.xml映射文件了 .

1、我们在我们的接口中添加注解

```java
//查询全部用户
@Select("select id,name,pwd password from user")
public List<User> getAllUser();
```

2、在mybatis的核心配置文件中注入

```xml
<!--使用class绑定接口-->
<mappers>
   <mapper class="com.kuang.mapper.UserMapper"/>
</mappers>
```

3、我们去进行测试

```java
@Test
public void testGetAllUser() {
   SqlSession session = MybatisUtils.getSession();
   //本质上利用了jvm的动态代理机制
   UserMapper mapper = session.getMapper(UserMapper.class);

   List<User> users = mapper.getAllUser();
   for (User user : users){
       System.out.println(user);
  }

   session.close();
}
```

**本质上利用了jvm的动态代理机制**

**Mybatis详细的执行流程**

![image-20220512222917596](https://tva1.sinaimg.cn/large/e6c9d24egy1h25zq8bvvvj20j011saco.jpg)

### 关于@Param

@Param注解用于给方法参数起一个名字。以下是总结的使用原则：

- 在方法只接受一个参数的情况下，可以不使用@Param。
- 在方法接受多个参数的情况下，建议一定要使用@Param注解给参数命名。
- 如果参数是 JavaBean ， 则不能使用@Param。
- 不使用@Param注解时，参数只能有一个，并且是Javabean。

### \#与$的区别

- \#{} 的作用主要是替换预编译语句(PrepareStatement)中的占位符? 【推荐使用】

  ```
  INSERT INTO user (name) VALUES (#{name});
  INSERT INTO user (name) VALUES (?);
  ```

- ${} 的作用是直接进行字符串替换

  ```
  INSERT INTO user (name) VALUES ('${name}');
  INSERT INTO user (name) VALUES ('kuangshen');
  ```

**使用注解和配置文件协同开发，才是MyBatis的最佳实践！**

## 9、多对一处理

### 9.1创建数据库表

```mysql
CREATE TABLE teacher(
	`id` int PRIMARY kEY AUTO_INCREMENT,
	`name` VARCHAR(20) NOT NULL DEFAULT ''
	);
CREATE TABLE student(
	`id` int PRIMARY kEY AUTO_INCREMENT,
	`name` VARCHAR(20) NOT NULL DEFAULT '',
	tid int ,
	FOREIGN kEY (tid) REFERENCES teacher(`id`)
	);
	INSERT INTO teacher(`id`, `name`) VALUES (NULL, '陈老师');
	INSERT INTO teacher(`id`, `name`) VALUES (NULL, '李老师');
	INSERT INTO student VALUES (NULL, '小亮',1);
	INSERT INTO student VALUES (NULL, '小陈',2);
```

![image-20220514172120931](https://tva1.sinaimg.cn/large/e6c9d24egy1h2822hq2nnj20ua09k0tp.jpg)

### 9.2编写MyBatis配置信息

Mybatis-config.xml

实体类

Mapper接口、Mapper.xml

MyBatisUtil.java工具类（获取sqlSession）

```java
public class Student {
    private int id;
    private String name;
  	//学生关联一个老师
    private Teacher teacher;
		//无参构造、getter和setter方法
}
```

### 9.3查询所有学生及其老师信息

#### 9.3.1按照查询嵌套

```xml
 		<resultMap id="StudentTeacher" type="student">
        <result property="id" column="id"/>
        <result property="name" column="name"/>
      <!--association对象关联属性 property属性名 javaType属性类型 column在多的一方的表中的列名-->
      <!--collection集合
        <association property="teacher" column="tid" javaType="Teacher" select="getTeacher"/>
      <!--
   这里传递过来的id，只有一个属性的时候，下面可以写任何值
   association中column多参数配置：
       column="{key=value,key=value}"
       其实就是键值对的形式，key是传给下个sql的取值名称，value是片段一中sql查询的字段名。
   -->
    </resultMap>
    <select id="getAll" resultMap="StudentTeacher">
        select *from student;
    </select>
    <select id="getTeacher" resultType="teacher">
        select * from teacher where id=#{id};
    </select>
```

#### 9.3.2按查询结果嵌套

```xml
<!--
按查询结果嵌套处理
思路：
   1. 直接查询出结果，进行结果集的映射
-->
<select id="getStudents2" resultMap="StudentTeacher2" >
  select s.id sid, s.name sname , t.name tname
  from student s,teacher t
  where s.tid = t.id
</select>

<resultMap id="StudentTeacher2" type="student">
   <id property="id" column="sid"/>
   <result property="name" column="sname"/>
   <!--关联对象property 关联对象在Student实体类中的属性-->
   <association property="teacher" javaType="teacher">
       <result property="name" column="tname"/>
   </association>
</resultMap>
```

#### 9.3.3测试

```java
  @Test
    public void getAllStudent(){
        SqlSession sqlSession = MyBatisUtil.getSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        //List<Student> students = mapper.getAll();
        List<Student> students = mapper.getStudents2();
        if(students!=null) {
            for (Student student : students) {
                System.out.println("{" + student.getId() + "," + student.getName() + "," + student.getTeacher().getName()+"}");
            }
        }
        sqlSession.close();
```

### 9.4小结

按照查询进行嵌套处理就像**SQL中的子查询**

按照结果进行嵌套处理就像**SQL中的联表查询**

## 10、一对多处理

### 10.1 按查询嵌套

```xml
<select id="selectTeacher" resultType="teacher">
        select * from teacher where name=#{name};
    </select>
    <select id="getTeacher" resultMap="TeacherStudent">
        select * from teacher where id=#{id};
    </select>
    <resultMap id="TeacherStudent" type="teacher">
        <result property="id" column="id"/>
        <collection property="students" column="id" javaType="List" ofType="student" select="selectStudentByTid"/>
    </resultMap>
    <select id="selectStudentByTid" resultType="student">
        select *from student where tid=#{id};
    </select>
```

### 10.2按结果嵌套

```xml
<!--    按查询结果嵌套-->
    <select id="getTeacher1" resultMap="TeacherStudent1">
        select t.id tid,t.name tname,s.id sid,s.name sname
        from student s,teacher t where t.id=#{id} and t.id=s.tid
    </select>
    <resultMap id="TeacherStudent1" type="teacher">
        <result property="id" column="tid"/>
        <result property="name" column="tname"/>

        <collection property="students" ofType="student" >
            <result property="id" column="sid"/>
            <result property="name" column="sname"/>
        </collection>
    </resultMap>
```

### 10.3测试

```java
 @Test
    public void getTeacherStu(){
        SqlSession sqlSession = MyBatisUtil.getSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher1(2);
        //Teacher teacher= mapper.getTeacher(2);
        if(teacher!=null){
            System.out.println(teacher);
        }
        sqlSession.close();
    }
```

### 10.4小结

1、关联-association

2、集合-collection

3、所以association是用于一对一和多对一，而collection是用于一对多的关系

4、JavaType和ofType都是用来指定对象类型的

- JavaType是用来指定pojo中属性的类型
- ofType指定的是映射到list集合属性中pojo的类型。

**注意说明：**

1、保证SQL的可读性，尽量通俗易懂

2、根据实际要求，尽量编写性能更高的SQL语句

3、注意属性名和字段不一致的问题

4、注意一对多和多对一 中：字段和属性对应的问题

5、尽量使用Log4j，通过日志来查看自己的错误

