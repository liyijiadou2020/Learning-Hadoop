> 参考资料：[https://blog.csdn.net/YG15165/article/details/132529562](https://blog.csdn.net/YG15165/article/details/132529562 "https://blog.csdn.net/YG15165/article/details/132529562")  
> [https://blog.csdn.net/qq_50954361/article/details/129578360](https://blog.csdn.net/qq_50954361/article/details/129578360 "https://blog.csdn.net/qq_50954361/article/details/129578360")  
> [https://www.bilibili.com/read/cv26421201/](https://www.bilibili.com/read/cv26421201/ "https://www.bilibili.com/read/cv26421201/")  




---

## 1 Hive 入门
### 1.1 什么是 Hive
#### 1.1.1 Hive 简介
Hive 是一个数据仓库工具。它可以<font color="#c0504d">将结构化的数据文件映射成一张表</font>，并提供<font color="#c0504d">类 SQL 查询功能</font>。它可以大大简化我们提取数据的方式。
例如：我们在 MapReduce 框架下要求文本的词频，需要写 Mapper、Reducer、Driver 三个类。但是在 Hive 中只需要一行代码就行了：
`select count(*) from test group by id;`

#### 1.1.2 Hive 的本质
本质就是一个 Hadoop 客户端，它<font color="#c0504d">将 HQL 转化成 MapReduce 程序。</font>
它和 HDFS、MapReduce、Yarn 是深度绑定的：
1) Hive 中每张表的数据都存储在 HDFS 
2) Hive 的底层是 MapReduce
3) Hive 运行在Yarn 上

### 1.2 Hive 架构原理
Metastore：提供元数据信息的<font color="#c0504d">访问接口</font>。元数据常常保存在 MySQL 数据库中。

HiveServer2：功能有2个 - (1) 提供 JDBC/ODBC(见下) 的访问接口 (2) 用户认证功能

Hive 的架构原理如图所示：
![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311301741709.png)



> 什么是JDBC 和 ODBC？
> 
> ODBS：Open Database Connection，开放式数据库连接。JDBC：Java Database Connection，Java数据库连接。
> 
> ODBC和JDBC都是API。区别在于JDBC是面向 Java 数据库连接的API，它是JDK的一部分。JDBC是建立在ODBC的基础上的。
> ![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311301735943.png)
> JDBC是任何Java应用程序和不同数据库之间的标准接口。JDBC的功能是帮助基于Java的应用程序访问不同类型的数据库。JDBC提供了查询数据库的方法，它也可用于更新数据库。JDBC提供JDBC驱动程序，将请求从客户端的Java应用程序转换为数据库理解的语言。
> 
> ODBC是开放式数据库连接。与JDBC一样，ODBC也是一个API，充当客户端应用程序和服务器端数据库之间的接口。
> 
> ODBC是最广泛使用的，并且可以理解许多不同的编程语言。ODBC的最大优点是能以统一的方式处理所有的数据库，但它的代码很复杂，难以理解。
> 
> ![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311301737419.png)

## 2 Hive 安装部署
### 2.1 安装（最小化部署模式）
- [x] 把 apache-hive-3.1.3-bin.tar.gz 上传到 Linux 的/opt/software 目录下
- [x] 解压
- [x] 更名成`hive`
- [x] 添加环境变量，source一下
- [x] 初始化元数据库
![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311301825046.png)

### 2.2 启动、使用 Hive
启动：在 `/opt/module/hive` 目录下 执行 `/bin/hive` 就可以进入 hive 程序了。
![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311301827315.png)

里面有一个默认的数据库`dafualt
```shell
hive> show databases;

OK
default
Time taken: 0.61 seconds, Fetched: 1 row(s)
```

最小化部署有个很大的缺点：同一时间只允许一个客户端访问（derby数据库的特点）。这显然不能满足生产需求。那么在生产环境下是如何部署Hive的呢？



