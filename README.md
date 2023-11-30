
```toc
```



---

## 运行环境^skipped
**硬件信息** 
客户端设备：
- 系统：Windows 10 专业版
- 处理器	AMD Ryzen 5 2600 Six-Core Processor  3.40 GHz
- 机带 RAM	16.0 GB

Hadoop集群：
- 系统：CentOS-7-1804
- 资源分配情况：内存4G、硬盘50G

**软件信息**
Hadoop集群：
- Java环境：jdk1.8.0_212
- Hadoop环境：hadoop-3.1.3

## Hadoop简介^skipped
<span style="background:#d4b106">一切从创建虚拟机开始！</span>
**课程地址**：【尚硅谷大数据Hadoop教程，hadoop3.x搭建到集群调优，百万播放】 https://www.bilibili.com/video/BV1Qp4y1n7EN/?p=38&share_source=copy_web&vd_source=3ddbcbe8933febf49d36238457a32fcc
**课程安排**：
- [x] 大数据概论
- [x] Hadoop 入门（搭建Hadoop集群）
- [x] Hadoop HDFS
- [ ] Hadoop MapReduce
- [ ] Hadoop Yarn
- [ ] Hadoop 生产调优手册
- [ ] 源码解析


# Hadoop 入门（搭建Hadoop集群）

## 1 模板虚拟机
- 主机名称：**hadoop100**
- IP地址：192.168.10.100
- 资源分配情况：内存4G、硬盘50G
- 系统：CentOS-7.5-x86-1804

**TODO**
- [x] 测试虚拟机联网情况
- [x] 安装 epel-release
- [x] 安装net-tool、vim、psmisc nc rsync lrzsz ntp libzstd openssl-static tree iotop git 【见[[尚硅谷 Hadoop 课件.pdf]]18页】
- [x] 关闭防火墙，关闭防火墙开机启动
- [ ] 创建atguigu用户，修改用户的密码 (123456)
- [ ] 配置atguigu具有root权限
- [x] 在/opt目录下创建 module、software 文件夹
	- [x] 。。。
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
- 系统：CentOS-7.5-x86-1804
- 主机名：hadoop102
- 静态IP：192.168.10.102
- 用户1：root   密码：123456
- 用户2：atguigu  密码：123456
- 内存：4G
- 磁盘：50G
- 上网方式：NAT   虚拟ip网段：10

### 3.2 hadoop103（Yarn的ResourceManager）
#### 3.2.1 虚拟机信息
- 系统：CentOS-7.5-x86-1804
- 主机名：hadoop103
- 静态IP：192.168.10.103
- 用户1：root   密码：123456
- 用户2：atguigu  密码：123456
- 内存：4G
- 磁盘：50G
- 上网方式：NAT   虚拟ip网段：10

其他自定义的Hadoop集群配置文件（`core-site.xml` 、`hdfs-site.xml` 、`yarn-site.xml` 、`mapred-site.xml`）同`hadoop102` 主机上的配置文件一样（见上）。

### 3.3 hadoop104（HDFS的SecondaryNameNode）
#### 3.3.1 虚拟机信息
- 系统：CentOS-7.5-x86-1804
- 主机名：hadoop104
- 静态IP：192.168.10.104
- 用户1：root   密码：123456
- 用户2：atguigu  密码：123456
- 内存：4G
- 磁盘：50G
- 上网方式：NAT   虚拟ip网段：10

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
### 3.1 准备Windows客户端的环境
- [x] 安装 hadoop-3.1.0
- [x] 配置 HADOOP_HOME 环境变量
- [x] 创建在Maven工程，导入相应的依赖坐标+日志添加
- [x] 编写代码

### 3.2 HDFS 的 API 案例实操
见`HdfsClilentDemo


## 4 HDFS 读写流程


## 5 NameNode（nn） 和SecondaryNameNode（2nn）


## 6 DataNode




---
# MapReduce

## 1 MapReduce 概述
- [x] WordCount实操

## 2 Hadoop 序列化
- [x] 序列化实操


## 3 MapReduce 框架原理
### 3.1 InputFormat：数据输入

- [x] CombineTextInputFormat 实操


### 3.2 Partition：自定义分区
通过自定义Partition类，可以让输出结果按照自定义的规则输出到不同文件中。例如，将统计结果按照手机归属地不同省份输出到不同的文件中。（电话号码开头136、137、138、139的放在4个单独的文件中）

- [x] Partition 分区实操

### 3.3 WritableComparable 排序

- [x] WritableComparable 排序案例实操（全排序）
需求：根据序列化案例产生的结果再次对总流量进行倒序排序。
期望输出数据：
![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311271245144.png)

在序列化的案例中我们的输出结果没有排序，是这样的：
```
13470253144		180	180	360
13509468723		7335	110349	117684
13560439638		918	4938	5856
13568436656		3597	25635	29232
13590439668		1116	954	2070
13630577991		6960	690	7650
13682846555		1938	2910	4848
13729199489		240	0	240
13736230513		2481	24681	27162
13768778790		120	120	240
13846544121		264	0	264
13956435636		132	1512	1644
13966251146		240	0	240
13975057813		11058	48243	59301
13992314666		3008	3720	6728
15043685818		3659	3538	7197
15910133277		3156	2936	6092
15959002129		1938	180	2118
18271575951		1527	2106	3633
18390173782		9531	2412	11943
84188413		4116	1432	5548


```

现在我们希望：以序列化案例中的输出结果作为输入，在输出的文件中按照总流量的倒序进行排序。

也就是说，`FlowBean` 对象之间必须可以比较。所以`FlowBean` 要实现`WritableComparable` 接口，重写`compareTo` 方法。

需求分析：
![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311271252343.png)

实验见`com.liyijiadou.hadoop.mapreduce.writable_comparable.FlowDriver#main`

实验结果：
```
13509468723		upFlow=7335	downFlow=110349	sumFlow=117684
13975057813		upFlow=11058	downFlow=48243	sumFlow=59301
13568436656		upFlow=3597	downFlow=25635	sumFlow=29232
13736230513		upFlow=2481	downFlow=24681	sumFlow=27162
18390173782		upFlow=9531	downFlow=2412	sumFlow=11943
13630577991		upFlow=6960	downFlow=690	sumFlow=7650
15043685818		upFlow=3659	downFlow=3538	sumFlow=7197
13992314666		upFlow=3008	downFlow=3720	sumFlow=6728
15910133277		upFlow=3156	downFlow=2936	sumFlow=6092
13560439638		upFlow=918	downFlow=4938	sumFlow=5856
84188413		upFlow=4116	downFlow=1432	sumFlow=5548
13682846555		upFlow=1938	downFlow=2910	sumFlow=4848
18271575951		upFlow=1527	downFlow=2106	sumFlow=3633
15959002129		upFlow=1938	downFlow=180	sumFlow=2118
13590439668		upFlow=1116	downFlow=954	sumFlow=2070
13956435636		upFlow=132	downFlow=1512	sumFlow=1644
13470253144		upFlow=180	downFlow=180	sumFlow=360
13846544121		upFlow=264	downFlow=0	sumFlow=264
13729199489		upFlow=240	downFlow=0	sumFlow=240
13768778790		upFlow=120	downFlow=120	sumFlow=240
13966251146		upFlow=240	downFlow=0	sumFlow=240

```

- [x] 二次排序
- [x] 分区内部排序

### 3.4 Combiner 合并
Combiner 是MP程序中除了 Mapper 和 Reducer 之外的一种组件。它是在每一个MapTask 所在的节点运行的。使用 Combiner 可以实现先对每一个 MapTask 的输出进行局部汇总，然后再输出到 Reducer去的效果。这样可以大幅减轻 Reducer 的接受请求负担。

我们用 WordCount 程序来举例。输入：
```
ruthless blend
compose mixture
ruthless blend
mixture
ruthless blend blend
```
如果我们没有使用 Combiner，在 MapReduce 任务结束后发给 Reducer 的将是：
```
(ruthless, 1)
(ruthless, 1)
(ruthless, 1)
(blend, 1)
(blend, 1)
(blend, 1)
(blend, 1)
(mixture, 1)
(mixture, 1)
(compose, 1)
```

但是如果我们使用了 Combiner，就可以在每一个MapReduce任务结束后进行局部聚合。MapReduce 任务结束后发给 Reducer 的将是：
```
(ruthless, 3)
(blend, 4)
(mixture, 2)
(compose, 1)
```


- [x] Combiner 实操

实验成功。在输出的目录中我们可以看到：
```java
C:\Java\jdk1.8.0_201\bin\java.exe "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2023.2.2\lib\idea_rt.jar=12167:C:\Program Files\JetBrains\IntelliJ IDEA 
...
		Combine input records=10
		Combine output records=4
		Spilled Records=4
		Failed Shuffles=0
...
Process finished with exit code 0

```

也就是说原本需要发送10条KV对，经过了combine之后只需要发送4条。

还有一个值得注意的点。
这是我们的WordCountCombiner：
```java
package com.liyijiadou.hadoop.mapreduce.combiner;  
  
import org.apache.hadoop.io.IntWritable;  
import org.apache.hadoop.io.Text;  
import org.apache.hadoop.mapreduce.Reducer;  
  
import java.io.IOException;  
  
/**  
 * @author liyijia  
 * @create 2023-11-2023/11/27  
 */public class WordCountCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {  
  
    private IntWritable outV = new IntWritable();  
  
    /**  
     * @param key 单词  
     * @param values 单词出现的个数，实际上都是1  
     * @param context 上下文  
     * @throws IOException  
     * @throws InterruptedException  
     */    @Override  
    protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context)  
            throws IOException, InterruptedException {  
       int sum = 0;  
        for (IntWritable value : values) {  
            sum += value.get();  
        }  
        outV.set(sum);  
        context.write(key, outV);  
    }  
}
```

这是 WordCountReducer :
```java
package com.liyijiadou.hadoop.mapreduce.combiner;  
  
  
import org.apache.hadoop.io.IntWritable;  
import org.apache.hadoop.io.Text;  
import org.apache.hadoop.mapreduce.Reducer;  
  
import java.io.IOException;  
  
/**  
 * @author liyijia  
 * @create 2023-11-2023/11/25  
 * Reducer源码：<KEYIN, VALUEIN, KEYOUT, VALUEOUT> {  
 * KEYIN：reduce 阶段输入的key类型  
 * VALUEIN：reduce阶段输入的value类型  
 * KEYOUT：reduce阶段输出的key类型  
 * VALUEOUT：reduce阶段输出的value类型  
 */  
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {  
  
    //    提高运行效率，不需要每次reduce都创建一个新对象  
    IntWritable outValue = new IntWritable();  
    /**  
     * reduce: 对每一种key调用一次  
     */  
    @Override  
    protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context)  
            throws IOException, InterruptedException {  
        int sum = 0;  
        //        要做累加  
        for (IntWritable value : values) {  
            sum += value.get();  
        }  
        //        写出  
        outValue.set(sum);  
        context.write(key, outValue);  
    }  
  
}
```

它们竟然是一模一样的！

难道我们完全不需要写 Combiner？

没错，真的，我们在`job.setCombinerClass()` 函数中用`job.setCombinerClass(WordCountCombiner.class);` 取代 `job.setCombinerClass(WordCountCombiner.class);`
完全可以实现一样的效果。

修改后的Driver函数：
```java
public class WordCountDriver {  
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {  
//        获取job  
        Configuration configuration = new Configuration();  
        Job job = Job.getInstance(configuration);  
//        设置jar包  
        job.setJarByClass(WordCountDriver.class);  
//        关联 M 和 R，可选：Combiner  
        job.setMapperClass(WordCountMapper.class);  
        job.setReducerClass(WordCountReducer.class);  
//        job.setCombinerClass(WordCountCombiner.class);  
        job.setCombinerClass(WordCountReducer.class);  
  
//        设置 map 输出的 kv 类型  
        job.setMapOutputKeyClass(Text.class);  
        job.setMapOutputValueClass(IntWritable.class);  
//        最终输出的 kv 类型  
        job.setOutputKeyClass(Text.class);  
        job.setOutputValueClass(IntWritable.class);  
  
//        设置输入输出路径  
        String inputPath = "D:\\source\\Learning-Hadoop\\playground\\mapred-learning\\08-combiner\\input";  
        String outputPath = "D:\\source\\Learning-Hadoop\\playground\\mapred-learning\\08-combiner\\output";  
        deleteDirectoryIfExists(outputPath);  
        FileInputFormat.setInputPaths(job, new Path(inputPath));  
        FileOutputFormat.setOutputPath(job, new Path(outputPath));  
  
//        提交 job        boolean result = job.waitForCompletion(true);  
        System.exit(result ? 0 : 1);  
    }  
}
```

![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311271601641.png)

结果也完全一样。

### 3.5 OutputFormat | 数据输出
MapReduce 任务完成后，Reducer 并没有直接把结果写出到文件中，而是交给你一个OutputFormat 对象。

默认的 OutputFormat 类型是 TextOutputFormat，他会把结果写出到文本文件中。
我们可以自定义一个 OutputFormat，让 MR 输出我们想要的文件。

- [x] 自定义 OutputFormat 案例实操


### 3.6 Join 应用
两张表需要关联就要使用 join。

- [x] Reduce Join 案例实操
- [x] Map Join 案例实操

怎么在Map阶段让order表能访问pd表呢？需要借助内存缓存。

具体方法：采用DistributedCache

1) 在 Mapper 的 setup 阶段将文件读取到缓存集合中
2) 在 Driver 驱动类中加载缓存


### 3.7 Hadoop 压缩
- [ ] 压缩实操

---
# Yarn
## 1 课程大纲
1. 理论
	1. Yarn 基础架构
	2. 工作机制
	3. MapReduce / HDFS / Yarn 之间如何配合工作
	4. 调度器和调度算法
		1. FIFO（生产环境下很少用）
		2. 容量调度器（Apache Hadoop 3.1.3 默认的）
		3. 公平调度器
	5. Yarn 在生产环境下需要配置哪些参数
	6. 命令行操作Yarn
2. 实践
	1. 生成环境下参数配置
	2. 容量调度器配置
	3. 公平调度器配置
	4. Yarn 的 tool 接口

## 2 Yarn常用命令
> 创建项目：`yarn init`

> 安装依赖包：`yarn` == `yarn install`

> 添加依赖包：`yarn add`
> 
> 查看全局安装列表： `yarn global list`
> 
> npm查看方式：`npm list -g --depth 0`
>

### 2.1 Yarn命令列表
| 命令                       | 慕课释义                       |
|--------------------------|----------------------------|
| yarn add                 | 添加依赖                       |
| yarn audit               | 对已安装的软件包执行漏洞审核             |
| yarn autoclean           | 从程序包依赖项中清除并删除不必要的文件        |
| yarn bin                 | 显示依赖bin文件夹的位置              |
| yarn cache               | 管理用户目录中的依赖缓存               |
| yarn check               | 验证当前项目中程序包依赖项              |
| yarn config              | 管理依赖配置文件                   |
| yarn create              | 创建Yarn工程                   |
| yarn dedupe              | 删除重复的依赖                    |
| yarn generate-lock-entry | 生成Yarn锁文件                  |
| yarn global              | 在全局安装依赖                    |
| yarn help                | 显示Yarn的帮助信息                |
| yarn import              | 迁移当前依赖的项目package-lock.json |
| yarn info                | 显示有关依赖的信息                  |
| yarn init                | 初始化工程并创建package.json文件     |
| yarn install             | 用于安装项目的所有依赖项               |
| yarn licenses            | 列出已安装依赖的许可证及源码url          |
| yarn link                | 链接依赖文件夹                    |
| yarn list                | 列出已安装的依赖                   |
| yarn login               | 存储您在 registry 上的用户名和 email |
| yarn logout              | 清除你在 registry 上用户名和 email  |
| yarn outdated            | 列出所有依赖项的版本信息               |
| yarn owner               | 展示依赖作者                     |
| yarn pack                | 创建依赖项的压缩gzip               |
| yarn policies            | 规定整个项目中执行Yarn的版本           |
| yarn publish             | 将依赖发布到npm注册表               |
| yarn remove              | 删除依赖                       |
| yarn run                 | 运行定义的程序脚本命令                |
| yarn tag                 | 在依赖上添加，删除或列出标签             |
| yarn team                | 管理组织中的团队，并更改团队成员身份         |
| yarn test                | 运行程序的test命令                |
| yarn upgrade             | 将指定依赖升级为最新版本               |
| yarn upgrade-interactive | 更新过期依赖的简便方法                |
| yarn version             | 展示依赖版本信息                   |
| yarn versions            | 展示所有依赖项版本信息                |
| yarn why                 | 显示有关为什么安装依赖的信息             |
| yarn workspace           | Yarn的工作区信息                 |
| yarn workspaces          | Yarn的所有工作区信息               |
>	https://m.imooc.com/wiki/yarnlesson-yarntext

## 3 Yarn 生成环境核心配置参数

- [x] Yarn 环境配置
```yml
<property>
    <!-- 选择调度器，例如容量调度器 -->
    <description>The class to use as the resource scheduler.</description>
    <name>yarn.resourcemanager.scheduler.class</name>
    <value>org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.CapacityScheduler</value>
</property>

<property>
    <!-- ResourceManager处理器调度器请求的线程数量，默认50 -->
    <!-- 如果提交的任务数量大于50，可以增加该值，但是不能超过 3台 * 4线程 = 12 线程（去除其他应用程序，实际上不能超过8） -->
    <description>Number of threads to handle scheduler interface.</description>
    <name>yarn.resourcemanager.scheduler.client.thread-count</name>
    <value>8</value>
</property>

<property>
    <!-- 是否让yarn自动检测硬件进行配置，默认false -->
    <!-- 如果该节点有很多其他应用程序，建议手动配置 -->
    <!-- 如果该节点没有其他应用程序，可以采用自动配置 -->
    <description>Enable auto-detection of node capabilities such as memory and CPU.</description>
    <name>yarn.nodemanager.resource.detect-hardware-capabilities</name>
    <value>false</value>
</property>

<property>
    <!-- 是否将虚拟核数当做cpu核数，默认值false，采用物理核数 -->
    <description>Flag to determine if logical processors(such as
        hyperthreads) should be counted as cores. Only applicable on Linux
        when yarn.nodemanager.resource.cpu-vcores is set to -1 and
        yarn.nodemanager.resource.detect-hardware-capabilities is true.
    </description>
    <name>yarn.nodemanager.resource.count-logical-processors-as-cores</name>
    <value>false</value>
</property>

<property>
    <!-- 虚拟核数和物理核数乘数，默认值1.0 -->
    <!-- 此处我们的服务器时4核4线程，即核数和线程数比值为1.0 -->
    <description>Multiplier to determine how to convert phyiscal cores to
        vcores. This value is used if yarn.nodemanager.resource.cpu-vcores
        is set to -1(which implies auto-calculate vcores) and
        yarn.nodemanager.resource.detect-hardware-capabilities is set to true. The
        number of vcores will be calculated as
        number of CPUs * multiplier.
    </description>
    <name>yarn.nodemanager.resource.pcores-vcores-multiplier</name>
    <value>1.0</value>
</property>

<property>
    <!-- NodeManager使用内存，默认设置的 -1，即不开启硬件检测时默认8G，开启的话自动计算 -->
    <!-- 这里我们服务器是4G，需要调整为4G -->
    <description>Amount of physical memory, in MB, that can be allocated 
        for containers. If set to -1 and
        yarn.nodemanager.resource.detect-hardware-capabilities is true, it is
        automatically calculated(in case of Windows and Linux).
        In other cases, the default is 8192MB.
    </description>
    <name>yarn.nodemanager.resource.memory-mb</name>
    <value>4096</value>
</property>

<property>
    <!-- NodeManager的CPU核数，默认值-1。即不开启硬件检测时默认8，开启的话自动计算-->
    <!-- 此处我们的服务器只有4核4线程 -->
    <description>Number of vcores that can be allocated
        for containers. This is used by the RM scheduler when allocating
        resources for containers. This is not used to limit the number of
        CPUs used by YARN containers. If it is set to -1 and
        yarn.nodemanager.resource.detect-hardware-capabilities is true, it is
        automatically determined from the hardware in case of Windows and Linux.
        In other cases, number of vcores is 8 by default.</description>
    <name>yarn.nodemanager.resource.cpu-vcores</name>
    <value>4</value>
</property>

<property>
    <!-- 容器最小内存，默认1G -->
    <description>The minimum allocation for every container request at the RM
        in MBs. Memory requests lower than this will be set to the value of this
        property. Additionally, a node manager that is configured to have less memory
        than this value will be shut down by the resource manager.</description>
    <name>yarn.scheduler.minimum-allocation-mb</name>
    <value>1024</value>
</property>

<property>
    <!-- 容器最大内存，默认8G -->
    <!-- 此处我们的服务器只有4G内存，根据前面分析，每台服务器要启动3个容器，所以容器最大内存可以修改为 2G -->
    <description>The maximum allocation for every container request at the RM
        in MBs. Memory requests higher than this will throw an
        InvalidResourceRequestException.</description>
    <name>yarn.scheduler.maximum-allocation-mb</name>
    <value>2048</value>
</property>

<property>
    <!-- 容器最小CPU核数，默认1个 -->
    <description>The minimum allocation for every container request at the RM
        in terms of virtual CPU cores. Requests lower than this will be set to the
        value of this property. Additionally, a node manager that is configured to
        have fewer virtual cores than this value will be shut down by the resource
        manager.</description>
    <name>yarn.scheduler.minimum-allocation-vcores</name>
    <value>1</value>
</property>

<property>
    <!-- 容器最大CPU核数，默认值4 -->
    <!-- 此处我们的服务器是4核，根据前面分析每台服务器要启动3个容器，所以容器最大CPU核数设置为2个 -->
    <description>The maximum allocation for every container request at the RM
        in terms of virtual CPU cores. Requests higher than this will throw an
        InvalidResourceRequestException.</description>
    <name>yarn.scheduler.maximum-allocation-vcores</name>
    <value>2</value>
</property>

<property>
    <!-- 虚拟内存检测，默认打开 -->
    <!-- 如果是 CentOS 7 + JDK 8，建议关闭该检测 -->
    <description>Whether virtual memory limits will be enforced for
        containers.</description>
    <name>yarn.nodemanager.vmem-check-enabled</name>
    <value>false</value>
</property>

<property>
	<!-- 虚拟内存和物理内存比例（用作虚拟内存检测的限制），默认值2.1 -->
    <description>Ratio between virtual memory to physical memory when
        setting memory limits for containers. Container allocations are
        expressed in terms of physical memory, and virtual memory usage
        is allowed to exceed this allocation by this ratio.
    </description>
    <name>yarn.nodemanager.vmem-pmem-ratio</name>
    <value>2.1</value>
</property>
```

可以在 [http://hadoop103:8088](http://hadoop103:8088) 中看到已经修改并应用成功：
![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311301134652.png)

在这里保存一个快照`Yarn-2`

### 3.1 多队列配置
在生产环境中不可能只使用一个队列。我们要怎么创建队列？
1）在生产环境怎么创建队列？  
（1）调度器默认就 1 个 default 队列，不能满足生产要求。  
（2）按照框架： hive /spark/ flink 每个框架的任务放入指定的队列（企业用的不是特别  
多） 。  
（3）按照业务模块：登录注册、购物车、下单、业务部门 1、业务部门 2。  
2）创建多队列的好处？  
（1）因为担心员工不小心，写递归死循环代码，把所有资源全部耗尽。  
（2）实现任务的降级使用，特殊时期保证重要的任务队列资源充足。
业务部门 1（重要） =》业务部门 2（比较重要） =》下单（一般） =》购物车（一般） =》  登录注册（次要） 。

#### 3.1.1 需求
1. default 队列占总内存的40%，最大资源量可占总资源 60%。
2. Hive 队列占总内存的 60%，最大资源容量可占总资源的 80%。
3.  配置优先级

配置前：只有一个default队列，占总资源的100%：
![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311301143821.png)

- [x] 配置多队列
```yaml
<!-- yarn.scheduler.capacity.root.queues前面的配置项保持默认即可  -->

<property>
    <!-- 为容量调度器root指定多队列，默认值default -->
    <!-- 配置为 default,hive，即增加一个hive队列 -->
    <name>yarn.scheduler.capacity.root.queues</name>
    <value>default,hive</value>
    <description>
        The queues at the this level (root is the root queue).
    </description>
</property>

<property>
    <!-- root调度器下的default队列的内存容量，默认100% -->
    <!-- 根据前面的需求，调整为 40% -->
    <name>yarn.scheduler.capacity.root.default.capacity</name>
    <value>40</value>
    <description>Default queue target capacity.</description>
</property>

<property>
    <!-- hive队列的内存容量，默认没有该队列，需要增加 -->
    <!-- 根据前面的需求，调整为 60% -->
    <name>yarn.scheduler.capacity.root.hive.capacity</name>
    <value>40</value>
    <description>Default queue target capacity.</description>
</property>

<property>
    <!-- default队列中，单个用户最多占用的资源比例，默认1（即可以占用完default队列的所有资源） -->
    <!-- 可以根据实际需求进行调整，防止某一个用户的死循环等操作将整个队列资源全部耗尽 -->
    <name>yarn.scheduler.capacity.root.default.user-limit-factor</name>
    <value>1</value>
    <description>
        Default queue user limit a percentage from 0.0 to 1.0.
    </description>
</property>

<property>
    <!-- hive队列中，单个用户最多占用的资源比例。默认没有该队列，需要自行添加 -->
    <name>yarn.scheduler.capacity.root.default.user-limit-factor</name>
    <value>1</value>
    <description>
        Default queue user limit a percentage from 0.0 to 1.0.
    </description>
</property>

<property>
    <!-- default队列，最大可以占用的资源容量，默认100% -->
    <!-- 根据前面的需求，调整为60%（default队列的资源容量为40%，所以最大可以再向其他队列借调20%） -->
    <name>yarn.scheduler.capacity.root.default.maximum-capacity</name>
    <value>60</value>
    <description>
        The maximum capacity of the default queue. 
    </description>
</property>
<property>
    <!-- hive队列，最大可以占用的资源容量，默认没有该队列，需要自行添加-->
    <name>yarn.scheduler.capacity.root.hive.maximum-capacity</name>
    <value>80</value>
    <description>
        The maximum capacity of the default queue. 
    </description>
</property>

<property>
    <!-- default队列的状态，默认值RUNNING启动，不需要修改 -->
    <name>yarn.scheduler.capacity.root.default.state</name>
    <value>RUNNING</value>
    <description>
        The state of the default queue. State can be one of RUNNING or STOPPED.
    </description>
</property>

<property>
    <!-- hive队列的状态，默认没有该项，需要自行添加 -->
    <name>yarn.scheduler.capacity.root.hive.state</name>
    <value>RUNNING</value>
    <description>
        The state of the default queue. State can be one of RUNNING or STOPPED.
    </description>
</property>

<property>
    <!-- default队列任务提交的acl权限，默认*（即所有用户都可以向该队列进行提交），不需要调整 -->
    <name>yarn.scheduler.capacity.root.default.acl_submit_applications</name>
    <value>*</value>
    <description>
        The ACL of who can submit jobs to the default queue.
    </description>
</property>

<property>
    <!-- hive队列任务提交的acl权限，默认没有该队列，需要自行添加 -->
    <name>yarn.scheduler.capacity.root.hive.acl_submit_applications</name>
    <value>*</value>
    <description>
        The ACL of who can submit jobs to the default queue.
    </description>
</property>

<property>
    <!-- default队列操作管理的acl权限，默认*（即所有用户都可以对队列任务进行kill等操作），不需要调整 -->
    <name>yarn.scheduler.capacity.root.default.acl_administer_queue</name>
    <value>*</value>
    <description>
        The ACL of who can administer jobs on the default queue.
    </description>
</property>

<property>
    <!-- hive队列操作管理的acl权限，默认没有该队列，需要自行添加 -->
    <name>yarn.scheduler.capacity.root.hive.acl_administer_queue</name>
    <value>*</value>
    <description>
        The ACL of who can administer jobs on the default queue.
    </description>
</property>

<property>
    <!-- default队列的提交任务优先级设置的acl权限，默认*（即所有用户都可以设置队列中的优先级），不需要调整 -->
    <name>yarn.scheduler.capacity.root.default.acl_application_max_priority</name>
    <value>*</value>
    <description>
        The ACL of who can submit applications with configured priority.
        For e.g, [user={name} group={name} max_priority={priority} default_priority={priority}]
    </description>
</property>

<property>
    <!-- hive队列的提交任务优先级设置的acl权限，默认没有该队列，需要自行添加 -->
    <name>yarn.scheduler.capacity.root.hive.acl_application_max_priority</name>
    <value>*</value>
    <description>
        The ACL of who can submit applications with configured priority.
        For e.g, [user={name} group={name} max_priority={priority} default_priority={priority}]
    </description>
</property>

<property>
    <!-- default队列的application能够指定的最大超时时间 -->
    <!-- 如果application指定了超时时间，则提交到该队列的application能够指定的最大超时时间不能超过该值 -->
    <!-- 任务的超时时间设置：yarn application -appId <app_id> -updateLifetime <Timeout>  -->
    <!-- 任务执行时间如果超过了指定的超时时间，将会被kill掉 -->
    <name>yarn.scheduler.capacity.root.default.maximum-application-lifetime
    </name>
    <value>-1</value>
    <description>
        Maximum lifetime of an application which is submitted to a queue
        in seconds. Any value less than or equal to zero will be considered as
        disabled.
        This will be a hard time limit for all applications in this
        queue. If positive value is configured then any application submitted
        to this queue will be killed after exceeds the configured lifetime.
        User can also specify lifetime per application basis in
        application submission context. But user lifetime will be
        overridden if it exceeds queue maximum lifetime. It is point-in-time
        configuration.
        Note : Configuring too low value will result in killing application
        sooner. This feature is applicable only for leaf queue.
    </description>
</property>

<property>
    <!-- 默认没有hive队列，需要自行添加 -->
    <name>yarn.scheduler.capacity.root.default.maximum-application-lifetime
    </name>
    <value>-1</value>
    <description>
        Maximum lifetime of an application which is submitted to a queue
        in seconds. Any value less than or equal to zero will be considered as
        disabled.
        This will be a hard time limit for all applications in this
        queue. If positive value is configured then any application submitted
        to this queue will be killed after exceeds the configured lifetime.
        User can also specify lifetime per application basis in
        application submission context. But user lifetime will be
        overridden if it exceeds queue maximum lifetime. It is point-in-time
        configuration.
        Note : Configuring too low value will result in killing application
        sooner. This feature is applicable only for leaf queue.
    </description>
</property>

<property>
    <!-- default队列，如果没有为application指定超时时间，则使用 default-application-lifetime 作为默认值 -->
    <name>yarn.scheduler.capacity.root.default.default-application-lifetime
    </name>
    <value>-1</value>
    <description>
        Default lifetime of an application which is submitted to a queue
        in seconds. Any value less than or equal to zero will be considered as
        disabled.
        If the user has not submitted application with lifetime value then this
        value will be taken. It is point-in-time configuration.
        Note : Default lifetime can't exceed maximum lifetime. This feature is
        applicable only for leaf queue.
    </description>
</property>

<property>
    <!-- 默认没有hive队列，需要自行添加 -->
    <name>yarn.scheduler.capacity.root.default.default-application-lifetime
    </name>
    <value>-1</value>
    <description>
        Default lifetime of an application which is submitted to a queue
        in seconds. Any value less than or equal to zero will be considered as
        disabled.
        If the user has not submitted application with lifetime value then this
        value will be taken. It is point-in-time configuration.
        Note : Default lifetime can't exceed maximum lifetime. This feature is
        applicable only for leaf queue.
    </description>
</property>

<!-- 后面的配置和容量调度器root没有关系，保持默认即可 -->
```

（此处已经提前备份了文件，备份文件是 ../doc/default-etc/capacity-scheduler.xml。

这里不知道为什么，就是刷新不了队列。所以就恢复了快照`Yarn-1`

重写`capacity-scheduler.xml` 文件，如下：
```xml
<!--
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. See accompanying LICENSE file.
-->
<configuration>

  <property>
    <name>yarn.scheduler.capacity.maximum-applications</name>
    <value>10000</value>
    <description>
      Maximum number of applications that can be pending and running.
    </description>
  </property>

  <property>
    <name>yarn.scheduler.capacity.maximum-am-resource-percent</name>
    <value>0.1</value>
    <description>
      Maximum percent of resources in the cluster which can be used to run 
      application masters i.e. controls number of concurrent running
      applications.
    </description>
  </property>

  <property>
    <name>yarn.scheduler.capacity.resource-calculator</name>
    <value>org.apache.hadoop.yarn.util.resource.DefaultResourceCalculator</value>
    <description>
      The ResourceCalculator implementation to be used to compare 
      Resources in the scheduler.
      The default i.e. DefaultResourceCalculator only uses Memory while
      DominantResourceCalculator uses dominant-resource to compare 
      multi-dimensional resources such as Memory, CPU etc.
    </description>
  </property>

<!-- 20231130-yarn.scheduler.capacity.root.queues前面的配置项保持默认即可  -->
<property>
  <!-- 为容量调度器root指定多队列，默认值default -->
  <!-- 配置为 default,hive，即增加一个hive队列 -->
  <name>yarn.scheduler.capacity.root.queues</name>
  <value>default,hive</value>
  <description>
      The queues at the this level (root is the root queue).
  </description>
</property>

<property>
  <!-- root调度器下的default队列的内存容量，默认100% -->
  <!-- 根据前面的需求，调整为 40% -->
  <name>yarn.scheduler.capacity.root.default.capacity</name>
  <value>40</value>
  <description>Default queue target capacity.</description>
</property>

<property>
  <!-- hive队列的内存容量，默认没有该队列，需要增加 -->
  <!-- 根据前面的需求，调整为 60% -->
  <name>yarn.scheduler.capacity.root.hive.capacity</name>
  <value>40</value>
  <description>Default queue target capacity.</description>
</property>

<property>
  <!-- default队列中，单个用户最多占用的资源比例，默认1（即可以占用完default队列的所有资源） -->
  <!-- 可以根据实际需求进行调整，防止某一个用户的死循环等操作将整个队列资源全部耗尽 -->
  <name>yarn.scheduler.capacity.root.default.user-limit-factor</name>
  <value>1</value>
  <description>
      Default queue user limit a percentage from 0.0 to 1.0.
  </description>
</property>

<property>
  <!-- hive队列中，单个用户最多占用的资源比例。默认没有该队列，需要自行添加 -->
  <name>yarn.scheduler.capacity.root.default.user-limit-factor</name>
  <value>1</value>
  <description>
      Default queue user limit a percentage from 0.0 to 1.0.
  </description>
</property>

<property>
  <!-- default队列，最大可以占用的资源容量，默认100% -->
  <!-- 根据前面的需求，调整为60%（default队列的资源容量为40%，所以最大可以再向其他队列借调20%） -->
  <name>yarn.scheduler.capacity.root.default.maximum-capacity</name>
  <value>60</value>
  <description>
      The maximum capacity of the default queue. 
  </description>
</property>
<property>
  <!-- hive队列，最大可以占用的资源容量，默认没有该队列，需要自行添加-->
  <name>yarn.scheduler.capacity.root.hive.maximum-capacity</name>
  <value>80</value>
  <description>
      The maximum capacity of the default queue. 
  </description>
</property>

<property>
  <!-- default队列的状态，默认值RUNNING启动，不需要修改 -->
  <name>yarn.scheduler.capacity.root.default.state</name>
  <value>RUNNING</value>
  <description>
      The state of the default queue. State can be one of RUNNING or STOPPED.
  </description>
</property>

<property>
  <!-- hive队列的状态，默认没有该项，需要自行添加 -->
  <name>yarn.scheduler.capacity.root.hive.state</name>
  <value>RUNNING</value>
  <description>
      The state of the default queue. State can be one of RUNNING or STOPPED.
  </description>
</property>

<property>
  <!-- default队列任务提交的acl权限，默认*（即所有用户都可以向该队列进行提交），不需要调整 -->
  <name>yarn.scheduler.capacity.root.default.acl_submit_applications</name>
  <value>*</value>
  <description>
      The ACL of who can submit jobs to the default queue.
  </description>
</property>

<property>
  <!-- hive队列任务提交的acl权限，默认没有该队列，需要自行添加 -->
  <name>yarn.scheduler.capacity.root.hive.acl_submit_applications</name>
  <value>*</value>
  <description>
      The ACL of who can submit jobs to the default queue.
  </description>
</property>

<property>
  <!-- default队列操作管理的acl权限，默认*（即所有用户都可以对队列任务进行kill等操作），不需要调整 -->
  <name>yarn.scheduler.capacity.root.default.acl_administer_queue</name>
  <value>*</value>
  <description>
      The ACL of who can administer jobs on the default queue.
  </description>
</property>

<property>
  <!-- hive队列操作管理的acl权限，默认没有该队列，需要自行添加 -->
  <name>yarn.scheduler.capacity.root.hive.acl_administer_queue</name>
  <value>*</value>
  <description>
      The ACL of who can administer jobs on the default queue.
  </description>
</property>

<property>
  <!-- default队列的提交任务优先级设置的acl权限，默认*（即所有用户都可以设置队列中的优先级），不需要调整 -->
  <name>yarn.scheduler.capacity.root.default.acl_application_max_priority</name>
  <value>*</value>
  <description>
      The ACL of who can submit applications with configured priority.
      For e.g, [user={name} group={name} max_priority={priority} default_priority={priority}]
  </description>
</property>

<property>
  <!-- hive队列的提交任务优先级设置的acl权限，默认没有该队列，需要自行添加 -->
  <name>yarn.scheduler.capacity.root.hive.acl_application_max_priority</name>
  <value>*</value>
  <description>
      The ACL of who can submit applications with configured priority.
      For e.g, [user={name} group={name} max_priority={priority} default_priority={priority}]
  </description>
</property>

<property>
  <!-- default队列的application能够指定的最大超时时间 -->
  <!-- 如果application指定了超时时间，则提交到该队列的application能够指定的最大超时时间不能超过该值 -->
  <!-- 任务的超时时间设置：yarn application -appId <app_id> -updateLifetime <Timeout>  -->
  <!-- 任务执行时间如果超过了指定的超时时间，将会被kill掉 -->
  <name>yarn.scheduler.capacity.root.default.maximum-application-lifetime
  </name>
  <value>-1</value>
  <description>
      Maximum lifetime of an application which is submitted to a queue
      in seconds. Any value less than or equal to zero will be considered as
      disabled.
      This will be a hard time limit for all applications in this
      queue. If positive value is configured then any application submitted
      to this queue will be killed after exceeds the configured lifetime.
      User can also specify lifetime per application basis in
      application submission context. But user lifetime will be
      overridden if it exceeds queue maximum lifetime. It is point-in-time
      configuration.
      Note : Configuring too low value will result in killing application
      sooner. This feature is applicable only for leaf queue.
  </description>
</property>

<property>
  <!-- 默认没有hive队列，需要自行添加 -->
  <name>yarn.scheduler.capacity.root.default.maximum-application-lifetime
  </name>
  <value>-1</value>
  <description>
      Maximum lifetime of an application which is submitted to a queue
      in seconds. Any value less than or equal to zero will be considered as
      disabled.
      This will be a hard time limit for all applications in this
      queue. If positive value is configured then any application submitted
      to this queue will be killed after exceeds the configured lifetime.
      User can also specify lifetime per application basis in
      application submission context. But user lifetime will be
      overridden if it exceeds queue maximum lifetime. It is point-in-time
      configuration.
      Note : Configuring too low value will result in killing application
      sooner. This feature is applicable only for leaf queue.
  </description>
</property>

<property>
  <!-- default队列，如果没有为application指定超时时间，则使用 default-application-lifetime 作为默认值 -->
  <name>yarn.scheduler.capacity.root.default.default-application-lifetime
  </name>
  <value>-1</value>
  <description>
      Default lifetime of an application which is submitted to a queue
      in seconds. Any value less than or equal to zero will be considered as
      disabled.
      If the user has not submitted application with lifetime value then this
      value will be taken. It is point-in-time configuration.
      Note : Default lifetime can't exceed maximum lifetime. This feature is
      applicable only for leaf queue.
  </description>
</property>

<property>
  <!-- 默认没有hive队列，需要自行添加 -->
  <name>yarn.scheduler.capacity.root.default.default-application-lifetime
  </name>
  <value>-1</value>
  <description>
      Default lifetime of an application which is submitted to a queue
      in seconds. Any value less than or equal to zero will be considered as
      disabled.
      If the user has not submitted application with lifetime value then this
      value will be taken. It is point-in-time configuration.
      Note : Default lifetime can't exceed maximum lifetime. This feature is
      applicable only for leaf queue.
  </description>
</property>
<!-- 后面的配置和容量调度器root没有关系，保持默认即可 -->

  <property>
    <name>yarn.scheduler.capacity.node-locality-delay</name>
    <value>40</value>
    <description>
      Number of missed scheduling opportunities after which the CapacityScheduler 
      attempts to schedule rack-local containers.
      When setting this parameter, the size of the cluster should be taken into account.
      We use 40 as the default value, which is approximately the number of nodes in one rack.
      Note, if this value is -1, the locality constraint in the container request
      will be ignored, which disables the delay scheduling.
    </description>
  </property>

  <property>
    <name>yarn.scheduler.capacity.rack-locality-additional-delay</name>
    <value>-1</value>
    <description>
      Number of additional missed scheduling opportunities over the node-locality-delay
      ones, after which the CapacityScheduler attempts to schedule off-switch containers,
      instead of rack-local ones.
      Example: with node-locality-delay=40 and rack-locality-delay=20, the scheduler will
      attempt rack-local assignments after 40 missed opportunities, and off-switch assignments
      after 40+20=60 missed opportunities.
      When setting this parameter, the size of the cluster should be taken into account.
      We use -1 as the default value, which disables this feature. In this case, the number
      of missed opportunities for assigning off-switch containers is calculated based on
      the number of containers and unique locations specified in the resource request,
      as well as the size of the cluster.
    </description>
  </property>

  <property>
    <name>yarn.scheduler.capacity.queue-mappings</name>
    <value></value>
    <description>
      A list of mappings that will be used to assign jobs to queues
      The syntax for this list is [u|g]:[name]:[queue_name][,next mapping]*
      Typically this list will be used to map users to queues,
      for example, u:%user:%user maps all users to queues with the same name
      as the user.
    </description>
  </property>

  <property>
    <name>yarn.scheduler.capacity.queue-mappings-override.enable</name>
    <value>false</value>
    <description>
      If a queue mapping is present, will it override the value specified
      by the user? This can be used by administrators to place jobs in queues
      that are different than the one specified by the user.
      The default is false.
    </description>
  </property>

  <property>
    <name>yarn.scheduler.capacity.per-node-heartbeat.maximum-offswitch-assignments</name>
    <value>1</value>
    <description>
      Controls the number of OFF_SWITCH assignments allowed
      during a node's heartbeat. Increasing this value can improve
      scheduling rate for OFF_SWITCH containers. Lower values reduce
      "clumping" of applications on particular nodes. The default is 1.
      Legal values are 1-MAX_INT. This config is refreshable.
    </description>
  </property>


  <property>
    <name>yarn.scheduler.capacity.application.fail-fast</name>
    <value>false</value>
    <description>
      Whether RM should fail during recovery if previous applications'
      queue is no longer valid.
    </description>
  </property>

</configuration>

```

当我执行刷新队列的时候，报错：
```shell
[atguigu@hadoop102 hadoop]$ yarn rmadmin -refreshQueues
2023-11-30 18:14:55,401 INFO client.RMProxy: Connecting to ResourceManager at hadoop103/192.168.10.103:8033
2023-11-30 18:14:56,956 INFO ipc.Client: Retrying connect to server: hadoop103/192.168.10.103:8033. Already tried 0 time(s); retry policy is RetryUpToMaximumCountWithFixedSleep(maxRetries=10, sleepTime=1000 MILLISECONDS)
2023-11-30 18:14:57,958 INFO ipc.Client: Retrying connect to server: hadoop103/192.168.10.103:8033. Already tried 1 time(s); retry policy is RetryUpToMaximumCountWithFixedSleep(maxRetries=10, sleepTime=1000 MILLISECONDS)
2023-11-30 18:14:58,961 INFO ipc.Client: Retrying connect to server: hadoop103/192.168.10.103:8033. Already tried 2 time(s); retry policy is RetryUpToMaximumCountWithFixedSleep(maxRetries=10, sleepTime=1000 MILLISECONDS)
...
```

我不知道为什么！？

经过网上查找答案后破案了：原来是我的 Resource Manager 没有被启动起来。

在 hadoop103 上运行 `start-all.sh` ，稍等片刻后就能看到 启动了。
于是再执行刷新队列的操作，这回成功了：
```shell
[atguigu@hadoop103 hadoop]$ yarn rmadmin -refreshQueues
2023-11-30 18:35:28,847 INFO client.RMProxy: Connecting to ResourceManager at hadoop103/192.168.10.103:8033

```

很奇怪。这个问题说明我的脚本 myhadoop.sh 无法启动 resource manager。说明有可能 yarn.xml 出了问题。

暂时不管这个bug吧，我们在hadoop103:8088/中可以看到两个队列已经配置好了：
![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311301340794.png)

测试一下任务是否会执行。我们指定一个任务提交到hive队列中试试：
```shell
# 指定提交到hive队列
# -D 运行时改变参数值
hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-3.2.3.jar wordcount -D mapreduce.job.queuename=hive /input /output
```

是的，可以观察到 hive 队列已经在执行了，实验成功。

![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202311301347894.png)

### 3.2 任务优先级配置
- [x] 任务优先级配置
容量调度器支持任务优先级的配置，在资源紧张时，优先级高的任务将优先获取资源。

默认情况下，Yarn将所有任务的优先级限制为0，若想使用任务的优先级功能，必须开放该限制。

修改`yarn-site.xml`，增加参数：
```xml
<property>
    <!-- 设置Yarn的任务优先级，默认值0 -->
    <!-- 设置5，表示我们可以有5个优先级：0/1/2/3/4/5，数字越大优先级越高 -->
    <name>yarn.cluster.max-application-priority</name>
    <value>5</value>
</property>
```

分发到集群其他节点。
重启Yarn集群（注意是在 hadoop103 上！）：
```shell
sbin/stop-yarn.sh
sbin/start-yarn.sh
```

当集群中资源不足出现排队时，可以通过调整任务的优先级达到优先执行的目的：
```shell
# 在任务启动时就指定任务的优先级
hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-3.1.3.jar wordcount -D mapreduce.job.priority=5 /input /output

# 也可以通过命令修改正在执行的任务的优先级
yarn application -appID <app_id> -updatePriority 5
```

### 3.3 公平调度器案例
- [ ] 公平调度器案例实操

## 4 Yarn的Tool接口

懒得写了，见这里：
https://www.yuque.com/tmfl/big_data/zhen6g

---



