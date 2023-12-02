package com.atguigu.spark.core.wc

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 *
 * @author: liyijia
 * @version: 0.0
 * @create time: 2023-12-2023/12/2
 * @description: local 模式下运行 Spark 的 WordCount 程序
 * ***********************************************************
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
    val words: RDD[String] = lines.flatMap(_.split(" "))

    val wordToOne = words.map(
      word => (word, 1)
    )

    //    3. 将数据根据单词进行分组
    val wordGroup: RDD[(String, Iterable[(String, Int)])] = wordToOne.groupBy(
      t => t._1
    )

    //    4. 对分组后的数据进行转换 (hello, hello, hello), (world, world) --> (hello, 3) (world, 2)
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
