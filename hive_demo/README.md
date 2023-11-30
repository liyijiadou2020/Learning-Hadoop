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
> ODBS：Open Database Connection，开放式数据库连接。JDBC：Java Database Connection，Java数据库连接。
> ODBC和JDBC都是API。区别在于JDBC是面向 Java 数据库连接的API，它是JDK的一部分。JDBC是建立在ODBC的基础上的。
> 
> ![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311301735943.png)
> 
> JDBC是任何Java应用程序和不同数据库之间的标准接口。JDBC的功能是帮助基于Java的应用程序访问不同类型的数据库。JDBC提供了查询数据库的方法，它也可用于更新数据库。JDBC提供JDBC驱动程序，将请求从客户端的Java应用程序转换为数据库理解的语言。
> ODBC是开放式数据库连接。与JDBC一样，ODBC也是一个API，充当客户端应用程序和服务器端数据库之间的接口。
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

里面有一个默认的数据库`dafualt`
```shell
hive> show databases;

OK
default
Time taken: 0.61 seconds, Fetched: 1 row(s)
```

最小化部署有个很大的缺点：同一时间只允许一个客户端访问（derby数据库的特点）。这显然不能满足生产需求。那么在生产环境下是如何部署Hive的呢？

### 2.3 MySQL 安装
#### 2.3.1 MySQL 安装
- [x] 下载MySQL jar安装包和驱动jar包，解压
- [x] 卸载系统自带的 mariadb
	sudo rpm -qa | grep mariadb | xargs sudo  rpm -e --nodeps
- [x] 安装 MySQL 依赖，注意需要安装顺序安装！
	- [x] common
	- [x] libs
	- [x] libs-compat
	- [x] client
	- [x] server
sudo rpm -ivh mysql-community-common-5.7.28-1.el7.x86_64.rpm
sudo rpm -ivh mysql-community-libs-5.7.28-1.el7.x86_64.rpm
sudo rpm -ivh mysql-community-libs-compat-5.7.28-1.el7.x86_64.rpm
sudo rpm -ivh mysql-community-client-5.7.28-1.el7.x86_64.rpm
sudo rpm -ivh mysql-community-server-5.7.28-1.el7.x86_64.rpm
- [x] 启动MySQL服务
	启动成功：
	![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311301856880.png)

#### 2.3.2 MySQL 配置
主要是配置root用户 + 密码。在任何主机上都能登录MySQL数据库。

查看MySQL的日志，找到root用户的密码：sudo cat /var/log/mysqld.log | grepp password

找到了我的初始密码：HtgzwdpwN6.x

用刚刚查到的密码来登录：mysql -uroot -p'HtgzwdpwN6.x'

- [x] 设置复杂密码
`set password=password("000000");

由于密码过于简单会报错，我们的应对方法是降低密码验证安全等级：

`set global validate_password_policy=0; 

`set global validate_password_length=4;

然后再重新设置密码就行了。

- [x] 进入 MySQL 库，修改 user 表， 把 Host 表内容修改为%，刷新

### 2.4 配置 Hive 元数据存储到 MySQL 
- [x] 新建 Hive 元数据库 `metastore`
- [x] 将 MySQL 的 JDBC 驱动拷贝到 Hive 的 lib 目录下
- [x] 在$HIVE_HOME/conf 目录下新建 hive-site.xml 文件
- [x] 验证元数据是否配置成功

### 2.5 Hive 服务部署
#### 2.5.1 hiveserver2 服务
Hive 的 hiveserver2 服务的作用是提供 jdbc/odbc 接口，为用户提供远程访问 Hive 数据的功能。

![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311302048297.png)

这里需要解决一个问题：

访问 Hadoop 集群的用户是谁？

这就取决于是否开启模拟用户的功能了（默认是开启的）。

未开启用户模拟功能：

![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311302050501.png)

开启用户模拟：

![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311302051370.png)

生产环境下建议开启用户模拟。

**hiveserver2 部署**

hivesever2 的模拟用户功能，依赖于 Hadoop 提供的 proxy user（代理用户功能），只有  Hadoop 中的代理用户才能模拟其他用户的身份访问 Hadoop 集群。因此，需要将  hiveserver2 的启动用户设置为 Hadoop 的代理用户。

- [x] 启动hiveserver2
后台运行，输出全部丢入黑洞：`nohup bin/hive --service hiveserver2 1>/dev/null 2>&1 &`
- [x] 启动 beeline 客户端（命令行窗口连接数据库）
- [ ] 图形化界面：DataGrip
	- [x] 下载 DataGrip
	- [x] 连接 hadoop102 的数据库

#### 2.5.2 metastore 服务
我们已经说过了，Hive 的 metastore 服务作用是为 Hive CLI 或者 Hiveserver2 提供<font color="#c0504d">元数据访问接口</font>。我们在执行 Hive SQL 的时候本质就是执行MapReduce 程序。既然是 MR 程序那就有输入和输出路径。输入输出路径怎么映射到表格呢？这些映射关系就存在 metastore 中了。

metastore 有两种运行模式：
- 嵌入式模式，我们在这之前一直都是用这种模式。这种模式下，每个连接都内嵌一个metastore，客户端使用这个内嵌的metastore来访问数据库。
![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311302146116.png)

- 独立服务模式，生产中最常用的模式。每个客户端必须要拿到用户元数据库的读取权限才能访问元数据库。
![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311302147936.png)


**metastore 配置**
1. 嵌入式模式配置
2. 独立服务模式（重点）
- [x] 在数据库所在的主机（我们的情况是hadoop102）配置好 hive-site.xml, 保证有连接元数据所需要的参数（jdbc连接所需的URL, Driver, username, password）
- [x] 保证每一个客户端都配置了 metastore 服务的地址（hive-site.xml)
- [x] hadoop102 启动 metastore 服务
- [x] hadoop103 去 访问hadoop102 的 metastore 服务（直接启动Hive CLI，执行Hive SQL语句即可）


### 2.6 Hive 使用技巧






