
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
- [ ] Map Join 案例实操

怎么在Map阶段让order表能访问pd表呢？需要借助内存缓存。

具体方法：采用DistributedCache

1) 在 Mapper 的 setup 阶段将文件读取到缓存集合中
2) 在 Driver 驱动类中加载缓存


