
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


---

## 4 Spark Core





## 5 Spark 内核 & 源码







## 6 Spark SQL






## 7 Spark Streaming








