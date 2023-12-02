package com.atguigu.spark.core.wc

import org.apache.spark.{ SparkConf, SparkContext }
import org.apache.spark.rdd.RDD

/**
 *
 * @author: liyijia
 * @version: 0.0
 * @create time: 2023-12-2023/12/2
 * @description: local 模式下运行 Spark 的 WordCount 程序
 *               ***********************************************************
 */
object Spark02_WordCount {

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
    //    2. 将一行数据拆分成一个一个的单词（分词） "Hello, world" -> Hello world
    /**
     * Scala 语法：
     * .flatMap 是一个高阶方法，这在Scala集合以及Spark的RDD中都是通用的。
     * 它遍历集合（这里是 lines）中的每个元素，并对每个元素应用一个函数。这个函数需要返回一个集合（例如数组，列表等）。flatMap
     * 方法最终将所有这些集合"扁平化"为一个单一的集合。
     * _.split(" ") 是匿名函数的简写，其中 _ 代表 lines 中的每一个元素（在这个上下文中是代表一个字符串）
     */
    val words: RDD[String] = lines.flatMap(_.split(" "))

    /**
     * .map 方法：
     * .map 是一个高阶函数，这种函数在 Scala 的集合类型以及 Spark 的 RDD
     * 中通用。它会遍历集合中的每一个元素并应用一个函数，然后返回一个在每个输入元素上应用函数后得到结果的新集合。
     * 此函数不会更改原始集合，而是生成一个新集合。
     *
     * word => (word, 1)：
     * 这部分是一个匿名函数（也称为 lambda 表达式）。这个匿名函数接受一个参数 word（这是由集合 words 中每个元素来的），
     * 并返回一个包含此参数和数字 1 的元组 (word, 1)。在 Scala
     * 中，元组是用圆括号包围的元素列表，可以存储不同类型的元素。
     *
     * 当 .map 应用这个匿名函数到 words 的每一个元素时，它实际上是为每个单词创建一个配对（单词，1）。
     * 这种配对是在很多词频统计算法中的第一步，其中每个单词开始时都与数字 1 关联，代表它出现了一次。
     */
    val wordToOne = words.map(
      word => (word, 1)
    )

    //    3. 将数据根据单词进行分组
    /**
     * groupBy 函数：https://juejin.cn/post/7089846102286925838
     *
     */
    val wordGroup: RDD[(String, Iterable[(String, Int)])] = wordToOne.groupBy(
      t => t._1
    )

    //    4. 对分组后的数据进行转换 (hello, hello, hello), (world, world) --> (hello, 3) (world, 2)
    /**
     * Scala 语法：
     * 这段Scala代码是对一个叫做 `wordGroup` 的集合进行操作。让我们一步一步地分析它：
     *
     * 1. `val wordToCount`：
     * 这是一个使用 Scala 的 `val` 关键字声明的不可变变量。`val` 表示一旦赋值就不能重新赋值，这是 Scala 编程语言中的一个常见实践，有利于写出更易于维护的函数式代码。
     *
     * 2. `wordGroup`：
     * 假设 `wordGroup` 是一个已经存在的集合。集合中的元素是元组（Tuple），每个元组包含一个词（word）和对应的元组列表（list）。例如，它可能是一个 `List[(String, List[
     * (String, Int)])]` 类型的集合，其中每个元组表示一个单词和一个包含多个 (`String`, `Int`) 元组列表。这个 `Int` 可以理解为单词出现的次数。
     *
     * 3. `.map` 方法：
     * `map` 方法是一个接受一个函数作为参数，对集合的每个元素应用这个函数，并返回一个新集合的高阶函数。
     *
     * 4. `{ case (word, list) => ... }`：
     * 在 `map` 方法中，我们传递了一个部分函数，通过使用模式匹配来处理元组 `(word, list)`。`case (word, list)` 可以匹配 `wordGroup` 中的每个元素，其中 `word`
     * 是元组的第一部分，`list` 是元组的第二部分。
     *
     * 5. `list.reduce`：
     * 这是 `reduce` 方法的调用，它对 `list`（第二元素，一个由元组构成的列表）进行折叠操作。`reduce` 方法接受一个函数，并使用这个函数来将列表的元素合并成一个单一值。
     *
     * 6. `(t1, t2) => { (t1._1, t1._2 + t2._2) }`：
     * 这是一个匿名函数，它接受两个参数 `t1` 和 `t2`，这两个参数都是 `list` 中的元组。这个函数返回一个新的元组，其第一个元素是 `t1`
     * 的第一个元素（假定没有修改，因为所有元组的第一元素通常是相同的字符串），其第二个元素是 `t1` 和 `t2` 的第二个元素相加的结果。
     *
     * 将这段代码串起来，它可以实现以下计算流程：
     * - 对于一个组成了词和与之相关联的元组列表的集合 `wordGroup`，
     * - 使用 `map` 方法遍历每个元素（`word`, `list`），
     * - 对于每个元组，使用 `list` 中的元组（假设第一元素不变，第二元素为计数）进行折叠操作，
     * - 计算总计数，即将所有具有相同单词的计数合并起来，忽略单词本身（因为它们是相同的）。
     *
     * 最终结果 `wordToCount` 将是一个包含单词和它们总计数的新集合。例如，如果 `list` 中包含的元组表示单词和出现的次数，那么 `wordToCount` 将是每个单词及其总出现次数的列表。
     *
     */
    val wordToCount = wordGroup.map {

      case (word, list) => {
        list.reduce(
          (t1, t2) => {
            (t1._1, t1._2 + t2._2)
          }
        )
      }

    }

    //    5. 将转换结果采集到控制台打印出来
    val array: Array[(String, Int)] = wordToCount.collect()
    array.foreach(println)
    sparkContext.stop()

  }


}
