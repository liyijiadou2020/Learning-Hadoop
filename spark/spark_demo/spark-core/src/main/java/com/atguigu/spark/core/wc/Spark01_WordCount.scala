package com.atguigu.spark.core.wc

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

    //    TODO 建立连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)

    //    TODO 配置

    //    TODO 关闭连接
    sc.stop()



  }

}
