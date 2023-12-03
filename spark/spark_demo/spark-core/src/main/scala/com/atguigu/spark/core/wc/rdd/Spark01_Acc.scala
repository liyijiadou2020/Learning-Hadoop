package com.atguigu.spark.core.wc.rdd

import org.apache.spark.util.LongAccumulator
import org.apache.spark.{ SparkConf, SparkContext }

/**
 *
 * @author: liyijia
 * @version: 0.0
 * @create time: 2023-12-2023/12/3
 * @description:
 * ***********************************************************
 */
object Spark01_Acc {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("Acc")
    val sc: SparkContext = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 2, 3 ,4))

    // 这样太复杂了，还要分区，还要reduce
    // val value: Any = rdd.reduce(_ + _)
    // println(value)

    // 这样就不需要分区了：
    // var sum = 0 // 这样是不对的！这个sum并不在Executor里，而是在Driver中！
    var sum1 : LongAccumulator = sc.longAccumulator("sum1") // 声明累加器：sum1经过Executor计算之后回传给Driver

    rdd.foreach(
      num => {
        // sum += num // 不对
        sum1.add(num) // 使用累加器，ok
      }
    )

    println(sum1.value) // 打印累加器
    sc.stop()

  }

}
