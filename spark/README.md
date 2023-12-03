
# 课程信息
- 标题：尚硅谷大数据Spark教程从入门到精通
- URL： https://www.bilibili.com/video/BV11A411L7CK/?p=4&share_source=copy_web&vd_source=3ddbcbe8933febf49d36238457a32fcc
- 目录：见spark_demo/doc/目录.md

## 1 Spark 框架
### 1.1 Spark 概述

Spark 和 Hadoop 经常被用来作比较。他们有什么区别呢?

- Hadoop
	- Java写的
	-  HDFS 是 Hadoop 分布式文件系统，处于 Hadoop 生态圈的最下层
	- MapReduce 是一种编程模型，是 Hadoop 的核心
	- HBase 是基于 HDFS 的数据库
- Spark
	- 由 Scala 语言开发的大数据分析引擎
	- Spark Core 中提供了 Spark 最基础与最核心的功能
	- Spark SQL 是Spark 用来操作结构化数据的组件
	- Spark Streaming 是 Spark 平台上针对实时数据进行流式计算的组件，提供了丰富的API

Spark 一直被认为是 Hadoop 框架的升级版。

### 1.2 Spark 的核心模块



## 2 Spark快速上手
- [x] 创建Maven 项目
- [x] 配置 Scala JDK
- [x] 测试 Scale 环境 （hello spark）
- [ ] WordCount 案例

#### 2.1.1 步骤
1. 建立和Spark 框架的连接
2. 执行业务操作
3. 关闭连接
```scala
  
def main(args: Array[String]): Unit = {  
  /** 步骤  
   * 1. 建立和Spark 框架的连接  
   * 2. 执行业务操作  
   * 3. 关闭连接  
   */  
  val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")  
  val sparkContext = new SparkContext(sparkConf)  
  
  //    1. 按行读取文件  
  val lines: RDD[String] = sparkContext.textFile(path = "datas")  
  
  // 扁平化  
  //    2. 将一行数据拆分成一个一个的单词（分词） "Hello, world" -> Hello world  val words: RDD[String] = lines.flatMap(_.split(" "))  
  
  val wordToOne = words.map(  
    word => (word, 1)  
  )  
  
  //    3. 将数据根据单词进行分组  
  // Spark 框架提供了更多的功能：可以将分组和聚合使用一个方法实现 - reduceByKey() 。  
  val wordToCount: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _)  
  
  //    4. 将转换结果采集到控制台打印出来  
  val array: Array[(String, Int)] = wordToCount.collect()  
  array.foreach(println)  
  sparkContext.stop()  
}
```

对其中的Scala语法的解释：
这个 Scala 程序使用 Apache Spark 来执行一个经典的“Word Count”任务，即计算给定文本数据中每个单词出现的次数。让我们逐步分析程序的每一部分：
1. `def main(args: Array[String]): Unit = { ... }`: 这定义了一个 `main` 函数，是 Scala 程序的入口点。`args` 是传递给程序的命令行参数数组。`main` 函数没有返回值，这由返回类型 `Unit` 指示，类似于 Java 的 `void`. 
2. `val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")`: 创建 Spark 配置对象 `sparkConf`，并配置它运行在本地模式下，应用程序名称设置为 `"WordCount"`。
3. `val sparkContext = new SparkContext(sparkConf)`: 使用 `sparkConf` 配置，初始化 Spark 上下文 `sparkContext`。这是 Spark 的核心，用于驱动各种 Spark 操作。 
4. `val lines: RDD[String] = sparkContext.textFile(path = "datas")`: 通过 `sparkContext` 的 `textFile` 方法读取文件，文件路径是 `"datas"`。读取的结果存储在一个 `RDD[String]`（字符串类型的 Resilient Distributed Dataset，即弹性分布式数据集）中，每个元素是文件的一行。
5. `val words: RDD[String] = lines.flatMap(_.split(" "))`: 对于 `lines` RDD 中的每行，应用 `flatMap` 操作。这里使用了 `_` 来代表 `lines` 中的每一个元素（每一行），对其应用 `split` 方法进行分词，以空格为分隔符。`flatMap` 将所有行的单词合并到一个 RDD 中。 
6. `val wordToOne = words.map(word => (word, 1))`: 将 `words` 的每个单词映射成一个元组，元组的第一个元素是单词，第二个元素是数字 `1`。这准备了数据进行接下来的计数操作。 
7. `val wordToCount: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _)`: 使用 `reduceByKey` 方法对 `wordToOne` RDD 中的元素按键（这里的键是单词）进行归约。每对元组将根据单词（键）合并，数字（值）相加，得到每个单词的总出现次数。 
8. `val array: Array[(String, Int)] = wordToCount.collect()`: 使用 `collect` 方法，将 `wordToCount` RDD 中的所有元素收集到 driver 程序的内存中，存储在一个数组中，每个数组元素都是一个 `(String, Int)` 对。 
9. `array.foreach(println)`: 遍历 `array` 中的每一个元素并打印。`foreach` 是一个高阶函数，这里它执行 `println` 方法来输出每个元素。 
10. `sparkContext.stop()`: 停止（关闭）`sparkContext`。在 Spark 程序中，一旦不需要进行进一步操作，应当关闭 SparkContext 来释放资源。

整个程序是一个完整的 Spark 应用程序，展示了从文件读取数据、处理数据、执行变换操作（拆分、映射、归约），并最终将结果打印到控制台的过程。这是进行大数据处理时典型的工作流程示例。

## 3 Spark 运行环境
Spark 作为数据处理框架和计算引擎，被设计在所有常见的集群环境中运行：
- Local 模式
- Standalone 模式
- Yarn 模式
- K8S & Mesos 模式
- Windows 模式

![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202312022121768.png)


部署模式对比：
![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202312022119389.png)


**端口号**：
- Spark 查看当前 Spark-shell 运行任务情况端口号： 4040（计算）  
- Spark Master 内部通信服务端口号： 7077  
- Standalone 模式下， Spark Master Web 端口号： 8080（资源）  
- Spark 历史服务器端口号： 18080  
- Hadoop YARN 任务运行情况查看端口号： 8088

### 3.1 Local 模式（本地环境）
- [x] hadoop102 上安装Spark
- [x] 测试 Spark 本地模式

`bin/spark-submit --class org.apache.spark.examples.SparkPi --master local[2] ./examples/jars/spark-examples_2.12-3.0.0.jar 10`

### 3.2 Standalone 模式（独立部署模式）
local 模式只是演示联系使用，真实工作中还要有集群模式。这里我们看看只使用 Spark 自身节点运行的集群模式，也就是所谓的Standalone模式（独立部署模式）。
独立部署模式体现了经典的master-slave模式。
集群规划：

|   |   |   |   |
|---|---|---|---|
| |hadoop102|hadoop103|hadoop104|
|Spark|Worker Master|Worker|Worker|

- [x] 启动集群，提交任务
`bin/spark-submit --class org.apache.spark.examples.SparkPi --master spark://hadoop102:7077 ./examples/jars/spark-examples_2.12-3.0.0.jar 10`

- [x] 配置历史服务器

- [ ] 高可用配置（HA) (使用Zookeeper)

### 3.3 Yarn 模式（工作中比较多）

- [x] 在 hadoop102 上部署

### 3.4 K8s 和 Mesos 模式
Mesos 是 Apache 下的开源分布式资源管理框架。它在 Twitter 上广泛应用。但在国内，依然是传统的 Hadoop 框架比较流行。

容器化部署是业界很流行的一项技术。容器管理工具中最为流行的就是k8s。而Spark也支持了K8S的部署模式。

### 3.5 Windows 模式

- [x] 在 Windows 环境下部署 spark

提示：退出要用小写英文字母`y`

![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202312022340943.png)


## 4 Spark 核心编程
Spark 计算框架封装了三大数据结构，用于支持高并发高吞吐的数据处理：
1. RDD
2. 累加器
3. 广播变量

### 4.1 RDD （Resilient Distributed Dataset）
RDD 是 Spark 中最基本的数据处理单位。它是一个抽象类。

一个 RDD 中封装了一个数据的处理逻辑。例如我们在`Spark03` 案例中使用到的 textFile（读取文件）、flatMap（扁平化）、map（转换）等都是一个RDD的具体实现类的方法。

复杂的业务需要很多计算步骤，一个RDD只需要专注于一个简单的步骤，再把这些RDD串联组合在一起就可以完成复杂的业务逻辑了！


#### 4.1.1 RDD 的特点
因此RDD有以下的特点：
1. 有弹性：容错、计算出错可以重试、可以根据需要进行分片
2. 分布式：数据存储在不同节点上
3. 数据集：只封装计算逻辑，不保存数据
4. 抽象：需要子类来实现具体逻辑
5. 不可变：一旦计算逻辑固定了就不能改变了
6. 可以根据需要分区并进行并行计算。每一个分区的计算逻辑是一样的。

#### 4.1.2 RDD 的核心属性
在官方文档中，RDD 有5个核心属性：
1) 分区列表（Array[Partition]）：只会执行一次，对数据进行分区
2) 分区计算函数：使用这个函数来对每一个分区进行计算
3) RDD 之间的依赖关系：当需要将多个计算模型进行组合的时候，就要将多个RDD建立依赖关系
4) 分区器（可选）：规定如果划分数据
5) 首选位置（可选）：可以根据计算节点的资源情况进行选择 - 计算在哪个节点进行能达到性能最优？

#### 4.1.3 RDD 的工作原理
RDD 在整个工作流中主要负责将逻辑封装，将计算任务转化成一个个Task，然后把这些Task发送给 Executor 节点执行计算。

#### 4.1.4 基础编程
##### 4.1.4.1 创建RDD


##### 4.1.4.2 RDD 并行度与分区
默认情况下， Spark 可以将一个作业切分多个任务后，发送给 Executor 节点并行计算，而能够并行计算的任务数量我们称之为**并行度**。这个数量可以在构建 RDD 时指定。 记住，这里的并行执行的任务数量，并不是指的切分任务的数量，不要混淆了。

##### 4.1.4.3 RDD 算子
RDD 方法分两大类：转换和行动。

- 转化：把一个旧的 RDD 变成一个新的 RDD。比如下图所示的 flatMap, map 等

![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202312031235682.png)

- 行动：触发任务调度、作业执行。比如collect等

把 **RDD 的方法**称为 **RDD 算子**。

###### 4.1.4.3.1 转化算子
RDD 处理每种数据方法不同，因此算子的类型也不同。整体上RDD 转化算子包括 Value 类型、双 Value 类型 和 K-V 类型。


- **Value 类型**
	- map（用得最多的）
		- 函数签名：def map[U: ClassTag](f: T => U): RDD[U]
		- 将处理的数据逐条进行映射转换，这里的转换可以是类型的转换，也可以是值的转换。
	- mapPartitions
		- 将待处理的数据以分区为单位发送到计算节点进行处理，这里的处理是指可以进行任意的处 理，哪怕是过滤数据。
	- mapPartitionsWithIndex
		- 将待处理的数据以分区为单位发送到计算节点进行处理，这里的处理是指可以进行任意的处理，哪怕是过滤数据，在处理时同时可以获取当前分区索引
	- flatMap
		- 将处理的数据进行扁平化后再进行映射处理，所以算子也称之为扁平映射
	- glom
		- 将同一个分区的数据直接转换为相同类型的内存数组进行处理，分区不变
	- groupBy
		- 将数据根据指定的规则进行分组, 分区默认不变，但是数据会被打乱重新组合，我们将这样  的操作称之为 shuffle。极限情况下，数据可能被分在同一个分区中一个组的数据在一个分区中，但是并不是说一个分区中只有一个组
	- filter
		- 将数据根据指定的规则进行筛选过滤，**符合规则的数据保留，不符合规则的数据丢弃**。当数据进行筛选过滤后，分区不变，但是分区内的数据可能不均衡，生产环境下，可能会出现**数据倾斜**。
	- sample
		- 根据指定的规则从数据集中抽取数据
	- distinct
		- 将数据集中重复的数据去重
	- coalesce
		- 根据数据量缩减分区，用于大数据集过滤后，提高小数据集的执行效率。当 spark 程序中，存在过多的小任务的时候，可以通过 coalesce 方法，收缩合并分区，减少分区的个数，减小任务调度成本
	- repartition
		- 该操作内部其实执行的是 coalesce 操作，参数 shuffle 的默认值为 true。无论是将分区数多的 RDD 转换为分区数少的 RDD，还是将分区数少的 RDD 转换为分区数多的 RDD， repartition操作都可以完成，因为无论如何都会经 shuffle 过程。
	- sortBy
		- 该操作用于排序数据。在排序之前，可以将数据通过 f 函数进行处理，之后按照 f 函数处理的结果进行排序，默认为升序排列。排序后新产生的 RDD 的分区数与原 RDD 的分区数一致。 中间存在 shuffle 的过程


- **双 Value 类型**
	- intersection
		- 对源 RDD 和参数 RDD 求交集后返回一个新的 RDD
	- union
		- 对源 RDD 和参数 RDD 求并集后返回一个新的 RDD
	- subtract
		- 以一个 RDD 元素为主， 去除两个 RDD 中重复元素，将其他元素保留下来。求差集
	- zip
		- 将两个 RDD 中的元素，以键值对的形式进行合并。其中，键值对中的 Key 为第 1 个 RDD中的元素， Value 为第 2 个 RDD 中的相同位置的元素。
交、并、差 都要求 要求数据类型一致！但是zip无所谓。


- **Key - Value 类型**
	- partitionBy：将数据按照指定的Partitioner 重新进行分区。Spark 默认的分区器是 HashPatitioner。
	- reduceByKey：可以将数据按照相同的key对value进行聚合
	- groupByKey：根据key对value进行分组
	- aggregateByKey：将数据根据不同规则进行**分区内**计算和**分区间**计算
		- reduceByKey：分区内和分区间的计算规则相同，而aggregateByKey则是不同的。
	- foldByKey：当分区内计算规则和分区间计算规则相同时， aggregateByKey 就可以简化为 foldByKey
	- combineByKey：最通用的对 key-value 型 rdd 进行聚集操作的聚集函数（aggregation function）。类似于aggregate()， combineByKey()允许用户返回值的类型与输入不一致。
	- sortByKey：在一个(K,V)的 RDD 上调用， K 必须实现 Ordered 接口(特质)，返回一个按照 key 进行排序的
	- join：在类型为(K,V)和(K,W)的 RDD 上调用，返回一个相同 key 对应的所有元素连接在一起的(K,(V,W))的 RDD
	- leftOuterJoin：类似于 SQL 语句的左外连接
	- cogroup：在类型为(K,V)和(K,W)的 RDD 上调用，返回一个。。。



- [ ] 案例实操：agent.log（时间戳，省份，城市，用户，广告，中间字段使用空格分隔），统计出每一个省份每个广告被点击数量排行的 Top3


###### 4.1.4.3.2 行动算子
行动算子触发作业（Job）的执行。底层代码调用的是环境对象的runJob()
1) reduce
2) collect
3) count
4) first
5) take
6) takeOrdered
7) aggregate
8) fold
9) countByKey
10) save
11) foreach

#### 4.1.5 RDD 序列化
执行计算的时候，算子外的代码在Driver端执行，算子内的代码都是在Executor端执行。那就会导致算子内的代码经常要用到算子外的代码，这样就形成了闭包的效果。如果使用的算子外数据无法序列化，就意味着无法传值给Executor端执行，就会发生错误。

所以在执行计算任务前，检测闭包内的对象是否可以进行序列化，这个操作叫做**闭包检测**。

> 问题：什么叫做“闭包”？
> 
> ChatGPT回答：
> 在编程中，**闭包（Closure）是一种函数和与其相关的引用环境的组合**。它允许一个函数记住并访问它声明时的词法作用域，即使该函数在其声明的作用域之外执行。 
> 闭包的概念在函数式编程中尤为重要，尽管它在各种编程语言中都有出现。闭包使得函数可以访问那些非局部变量：一个函数不仅可以访问在其内部定义的变量，还可以访问定义函数时有效的任何变量。 在分布式计算框架Apache Spark中，闭包的概念尤为关键，因为： 
> - **算子外的代码在Driver端执行：** Driver端是控制节点，负责分发任务和管理应用程序流程。 
> - **算子内的代码在Executor端执行：** Executor端是分布式节点，负责处理数据和执行计算任务。 由于Spark的工作节点（Executors）通常运行在不同的机器上，所以当算子内部的函数需要访问算子外部定义的数据时，这些数据需要被序列化并传递到每个Executor。如果数据不能被序列化，那么它就无法被传送给Executor端，导致任务失败。 
>因此，闭包检测在Spark中非常重要，它能确保： 
> - 需要在Executor端使用的变量能被成功序列化。 
> - 没有意外地捕获大的数据结构，导致任务间不必要的数据传输。
> - 确保传递到Executor端的数据是程序逻辑必须的，并尽量维持其轻量级。 
> 
> 在Spark提交任务执行之前，Spark框架会自动进行闭包检测，以确保可以在网络中传输闭包内的所有变量。如果检测到不能序列化的变量，Spark会抛出异常，通知用户必须解决这些序列化问题才能继续执行。

#### 4.1.6 RDD 依赖关系
##### 4.1.6.1 血缘关系
RDD 的 Lineage 会记录 RDD 的元数据信息的转换行为，如果该 RDD 部分分区数据丢失，可以根据血缘关系重新运算和恢复数据分区。

##### 4.1.6.2 依赖关系
一个 RDD 的生成需要另一个RDD的完成，这就是依赖。

##### 4.1.6.3 窄依赖
窄依赖就是父亲RDD 的分区最多被儿子RDD的一个分区使用。就好像是独生子女

##### 4.1.6.4 宽依赖
宽依赖表示同一个父（上游） RDD 的 Partition 被多个子（下游） RDD 的 Partition 依赖，会引起 Shuffle，总结：宽依赖我们形象的比喻为多生。

##### 4.1.6.5 RDD 阶段划分
DAG（有向无环图）记录了RDD的转化过程和任务阶段。




#### 4.1.7 RDD 持久化
RDD 可以通过Cache 或者 Persist 方法将计算结果缓存。

也可以设置检查点，将中间结果写入磁盘。

3) 缓存和检查点区别  
1） Cache 缓存只是将数据保存起来，不切断血缘依赖。 Checkpoint 检查点切断血缘依赖。  

2） Cache 缓存的数据通常存储在磁盘、内存等地方，可靠性低。 Checkpoint 的数据通常存储在 HDFS 等容错、高可用的文件系统，可靠性高。  

3）建议对 checkpoint()的 RDD 使用 Cache 缓存，这样 checkpoint 的 job 只需从 Cache 缓存中读取数据即可，否则需要再从头计算一次 RDD。

#### 4.1.8 RDD 分区器
Spark 目前只支持3种分区：Hash分区、Range分区和用户自定义分区。Hash 分区是默认的。分区器直接决定了RDD 中分区的个数、每天数据经过Shuffle 之后进入哪个分区，进而决定了Reduce 的个数。

只有K-V类型的RDD才有分区器。

每个 RDD 的分区 ID 范围： 0 ~ (numPartitions - 1)，决定这个值是属于那个分区的。
1) Hash 分区：对于给定的 key，计算其 hashCode,并除以分区个数取余
2) Range 分区：将一定范围内的数据映射到一个分区中，尽量保证每个分区数据均匀，而  
且分区间有序

#### 4.1.9 RDD 文件读取与保存
文件格式：text、csv、sequence、Object文件。

文件系统：本地文件系统、HDFS、HBASE、数据库。

> 对象文件是将对象序列化后保存的文件，采用 Java 的序列化机制。 可以通过 objectFile[T: ClassTag](path)函数接收一个路径， 读取对象文件， 返回对应的 RDD， 也可以通过调用  
saveAsObjectFile()实现对对象文件的输出。因为是序列化所以要指定类型。





---

## 5 Spark Core






## 6 Spark 内核 & 源码







## 7 Spark SQL






## 8 Spark Streaming








