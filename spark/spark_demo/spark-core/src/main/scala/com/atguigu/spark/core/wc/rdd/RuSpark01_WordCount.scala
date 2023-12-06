package com.atguigu.spark.core.wc.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{ SparkConf, SparkContext }

/**
 *
 * @author: liyijia
 * @version: 0.0
 * @create time: 2023-12-2023/12/6
 * @description:
 * ***********************************************************
 */
object RuSpark01_WordCount {
  def main(args: Array[String]): Unit = {
    /** STEPS
     * 1. Установление соединения с платформой Spark
     * 2. Выполнять хозяйственные операции
     * 3. тесная связь
     */
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sparkContext = new SparkContext(sparkConf)
    //    1. Читать файл построчно
    val lines: RDD[String] = sparkContext.textFile(path = "datas")
    // выравнивание данных
    //    2. Разделение строки данных на слова одно за другим (сегментация слов) "Hello, world" -> Hello world
    val words: RDD[String] = lines.flatMap(_.split(" "))
    //    3. Transformation
    val wordToOne = words.map(
      word => (word, 1)
    )
    //    4. Группировать данные по словам
    val wordToCount: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _)
    //    5. Собрать результаты конвертации в консоль и распечатайте их.
    val array: Array[(String, Int)] = wordToCount.collect()
    array.foreach(println)
    sparkContext.stop()
  }

}
