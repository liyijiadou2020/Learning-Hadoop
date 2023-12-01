package com.atguigu.spark.core.wc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author: liyijia
 * @version: 0.0
 * @create time: 2023-12-2023/12/1
 * @description:
 * ***********************************************************
 */
object Spark01_WordCount {

  def main(args: Array[String]): Unit = {
    /** 步骤
     * 1. 建立和Spark 框架的连接
     * 2. 执行业务操作
     * 3. 关闭连接
     */

    //    Application

    //    Spark 框架

    //    TODO 配置，建立连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)

    //    TODO 业务操作
    //    1. 按行读取文件
    val lines : RDD[String] = sc.textFile(path = "datas")

    // 扁平化
    //    2. 将一行数据拆分成一个一个的单词（分词） "Hello, world" -> Hello world
    val words: RDD[String] = lines.flatMap(_.split(" "))

    //    3. 将数据根据单词进行分组
    val wordGroup: RDD[(String, Iterable[String])] = words.groupBy(word => word)

    //    4. 对分组后的数据进行转换 (hello, hello, hello), (world, world) --> (hello, 3) (world, 2)
    val wordToCount = wordGroup.map {
      case (word, list) => {
        (word, list.size)
      }
    }

    //    5. 将转换结果采集到控制台打印出来
    val array: Array[(String, Int)] = wordToCount.collect()
    array.foreach(println)
    
    //    TODO 关闭连接
    sc.stop()


  }

}
