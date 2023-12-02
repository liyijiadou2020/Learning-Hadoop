package com.atguigu.spark.core.wc

/**
 *
 * @author: liyijia
 * @version: 0.0
 * @create time: 2023-12-2023/12/1
 * @description: 测试Scala环境。
 *               ***********************************************************
 */
object HelloScala {
  /**
   * main 方法： 从外部可以直接调用执行的方法
   * def 方法名(参数名称: 参数类型): 返回值类型 = { 方法体 }
   * Array[String]：Array类型，其中[String]表示范型（类似于Java中的 Box<String>）
   * Unit - 空返回值类型，类似于Java中的void
   *
   * @param args
   */
  def main(args: Array[String]): Unit = {
    // var sum = ""
    // for (ch <- "Hello") sum += ch
    // println(sum)

    val scores = scala.collection.mutable.SortedMap(
      "Alice" -> 10,
      "Fred" -> 7,
      "Bob" -> 3
    )
    println(scores) // 哈希表：使用键的哈希码来遍历

    val months = scala.collection.mutable.LinkedHashMap(
      "Feb" -> 2,
      "Mar" -> 3,
      "Apr" -> 4
    )
    println(months)





  }

}
