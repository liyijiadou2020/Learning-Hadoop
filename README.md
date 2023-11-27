

## Hadoop简介^skipped
<span style="background:#d4b106">一切从创建虚拟机开始！</span>
**课程地址**：【尚硅谷大数据Hadoop教程，hadoop3.x搭建到集群调优，百万播放】 https://www.bilibili.com/video/BV1Qp4y1n7EN/?p=38&share_source=copy_web&vd_source=3ddbcbe8933febf49d36238457a32fcc
**课程安排**：
- [x] 大数据概论
- [x] Hadoop 入门（搭建Hadoop集群）
- [ ] Hadoop HDFS
- [ ] Hadoop MapReduce
- [ ] Hadoop Yarn
- [ ] Hadoop 生产调优手册
- [ ] 源码解析


# Hadoop 入门（搭建Hadoop集群）

## 1 模板虚拟机
主机名称：**hadoop100**
IP地址：192.168.10.100
资源分配情况：内存4G、硬盘50G
系统：CentOS-7.5-x86-1804

**TODO**
- [x] 测试虚拟机联网情况
- [ ] 安装 epel-release
- [ ] 安装net-tool、vim、psmisc nc rsync lrzsz ntp libzstd openssl-static tree iotop git 【见[[尚硅谷 Hadoop 课件.pdf]]18页
- [x] 关闭防火墙，关闭防火墙开机启动
- [ ] 创建atguigu用户，修改用户的密码 (123456)
- [ ] 配置atguigu具有root权限
- [x] 在/opt目录下创建 module、software 文件夹
	- [ ] 。。。
- [x] 卸载虚拟机自带的JDK
- [x] 重启虚拟机


## 2 克隆虚拟机

### 2.1 总体流程
1. 准备3台客户机（关闭防火墙、静态IP、主机名称）
2. 安装JDK
3. 配置环境变量
4. 安装Hadoop
5. 配置环境变量
6. 配置集群
7. 单点启动
8. 配置ssh
9. 群起并测试集群

### 2.2 集群部署规划
![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311221506546.png)

### 2.3 配置文件说明
默认的配置文件：`core-default.xml`、`yarn-default.xml` 、`hdfs-default.xml` 、`mapred-default.xml`
自定义的5个人配置文件（hadoop 3.x）：`core-site.xml` 、`hdfs-site.xml` 、`yarn-site.xml` 、`mapred-site.xml`、`workers`

#### 2.3.1 core-site.xml
NameNode 的地址：hdfs://hadoop102:8020
hadoop数据存储目录：/opt/module/hadoop-3.2.3/data

#### 2.3.2 hdfs-site.xml
nn web端访问地址：hadoop102:9870
2nn web端访问地址：hadoop104:9868

#### 2.3.3 yarn-site.xml
指定 ResourceManager 的地址：hadoop103

#### 2.3.4 mapred-site.xml
指定 MapReduce 程序运行在 Yarn 上


## 3 集群主机情况
### 3.1 hadoop102（HDFS的NameNode）
#### 3.1.1 虚拟机信息
系统：CentOS-7.5-x86-1804
主机名：hadoop102
静态IP：192.168.10.102
用户1：root   密码：123456
用户2：atguigu  密码：123456
内存：4G
磁盘：50G
上网方式：NAT   虚拟ip网段：10

### 3.2 hadoop103（Yarn的ResourceManager）
#### 3.2.1 虚拟机信息
系统：CentOS-7.5-x86-1804
主机名：hadoop103
静态IP：192.168.10.103
用户1：root   密码：123456
用户2：atguigu  密码：123456
内存：4G
磁盘：50G
上网方式：NAT   虚拟ip网段：10

其他自定义的Hadoop集群配置文件（`core-site.xml` 、`hdfs-site.xml` 、`yarn-site.xml` 、`mapred-site.xml`）同`hadoop102` 主机上的配置文件一样（见上）。

### 3.3 hadoop104（HDFS的SecondaryNameNode）
#### 3.3.1 虚拟机信息
系统：CentOS-7.5-x86-1804
主机名：hadoop104
静态IP：192.168.10.104
用户1：root   密码：123456
用户2：atguigu  密码：123456
内存：4G
磁盘：50G
上网方式：NAT   虚拟ip网段：10

其他自定义的Hadoop集群配置文件（`core-site.xml` 、`hdfs-site.xml` 、`yarn-site.xml` 、`mapred-site.xml`）同`hadoop102` 主机上的配置文件一样（见上）。


## 4 常用的端口号
| 端口名称                   | Hadoop2.x | Hadoop3.x      |
|------------------------|-----------|----------------|
| NameNode内部通信端口         | 8020/9000 | 8020/9000/9820 |
| NameNode HTTP UI（面向用户） | 50070     | 9870           |
| MapReduce查看执行任务端口      | 8088      | 8088           |
| 历史服务器通信端口              | 19888     | 19888          |

## 5 自定义的几个常用的脚本

### 5.1 Hadoop 集群启动/停止脚本：myhadoop.sh start/stop

### 5.2 查看三台服务器Java进程的脚本：jpsall

### 5.3 分发文件到3台服务器的脚本：xsync



## 6 集群时间同步问题【略】
==如果是虚拟机环境，虚拟机环境可以连接外网，所以不需要配置时间同步。内网服务器才需要配置==

如果服务器在公网环境（能连接外网），可以不采用集群时间同步，因为服务器会定期和公网时间进行校准。

如果服务器在内网环境，必须要配置集群时间同步，否则时间久了会产生时间偏差，导致集群执行任务时间不同步。

时间服务器配置，例如以`hadoop102`作为时间服务器（必须使用`root`用户进行配置）：


## 7 运行中遇到的坑/错误
1. CentOS7 重启之后 ens33 消失（外部连接不上）
参考 https://blog.csdn.net/ling1998/article/details/123729100 得到了解决
```shell
systemctl stop NetworkManager
systemctl disable NetworkManager
systemctl start network.service
service network restart
```




---
# HDFS
## 1 HDFS 简介
分布式系统：把数据进行分区并存储到若干台设备上去，这样跨设备管理的文件系统就是分布式文件系统（ Distributed File System）。
HDFS：Hadoop Distributed File System，是**Hadoop的分布式文件系统**。 HDFS（Hadoop Distributed File System）是hadoop生态系统的一个重要组成部分，是hadoop中的的存储组件，在整个Hadoop中的地位非同一般，是最基础的一部分，因为它涉及到数据存储，MapReduce等计算模型都要依赖于存储在HDFS中的数据。HDFS是一个分布式文件系统，以流式数据访问模式存储超大文件，将数据分块存储到一个商业硬件集群内的不同机器上。
这里重点介绍其中涉及到的几个概念：
（1）**超大文件**。目前的hadoop集群能够存储几百TB甚至PB级的数据。
（2）**流式数据访问**。HDFS的访问模式是：**一次写入，多次读取**，更加关注的是读取整个数据集的整体时间。
（3）**商用硬件。**HDFS集群的设备不需要多么昂贵和特殊，只要是一些日常使用的普通硬件即可，正因为如此，hdfs节点故障的可能性还是很高的，所以**必须要有机制来处理这种单点故障**，保证数据的可靠。
（4）**不支持低时间延迟的数据访问**。hdfs关心的是高数据吞吐量，不适合那些要求低时间延迟数据访问的应用。
（5）**单用户写入，不支持任意修改。**hdfs的数据以读为主，只支持单个写入者，并且写操作总是以添加的形式在文末追加，不支持在任意位置进行修改。

### 1.1 数据块
默认一个块（block）的大小为128MB（HDFS的块这么大主要是为了最小化寻址开销），要在HDFS中存储的文件可以划分为多个分块，每个分块可以成为一个独立的存储单元。


### 1.2 NameNode 和 DataNode
DFS集群的节点分为两类：namenode和datanode，以管理节点-工作节点的模式运行，即一个namenode和多个datanode，理解这两类节点对理解HDFS工作机制非常重要。
NameNode 负责管理整个文件系统，维护着文件系统树，记录每个文件中各个块的所在数据节点的信息和整棵树内所有的文件和目录。DataNode 负责存储数据块，定检索数据块，定期向NameNode 发送所存储的块的列表。
![img.png](https://img2018.cnblogs.com/blog/1608161/201907/1608161-20190709150608993-1616801523.png)
NameNode 如此重要，可是它宕机了该怎么办？Hadoop 提供了两种机制：
1) 备份系统的元数据。比如，挂载一个网络文件系统（ NFS），在执行写操作的时候把系统信息同时写入NFS。
2) 运行一个NameNode的热备，在NameNode宕掉的时候顶替它上场。


### 1.3 块缓存
数据通常情况下都保存在磁盘，但是对于访问频繁的文件，其对应的数据块可能被显式的缓存到datanode的内存中，以堆外缓存的方式存在。


### 1.4 HDFS 的高可用性






## 2 HDFS 的 Shell 操作
### 2.1 基本语法
`hadoop fs 具体命令` 或者 `hdfs dfs 具体命令` 来操作，两者完全相同。
![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311241102779.png)
![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311241102781.png)

### 2.2 常用操作

#### 2.2.1 上传

- 从本地剪切
	`-moveFromLocal`
	
- 从本地拷贝
`-copyFromLocal`，或者`put` 。生产环境习惯用`put`

#### 2.2.2 下载
`copyToLocal`：从HDFS 拷贝到本地，也可以用`get` 。实际上`get` 更常用。

#### 2.2.3 HDFS直接操作
`-ls`：显示目录信息
`-cat`：显示文件内容
`-chgrp`  `-chmod`  `chown`  ：修改文件所属权限
`mkdir`：创建路径
`-cp`：从HDFS的一个路径拷贝到HDFS的另一个路径
`-mv`：移动文件
`-tail`：显示一个文件的末尾1kb的数据
`-rm`：删除文件或文件夹
`-rm -r`：递归删除目录及目录里的内容
`-du`：统计文件夹的大小信息
`-setrep`：设置HDFS中文件副本的数量

## 3 HDFS 的 API 操作
需要准备Windows客户端的环境。






