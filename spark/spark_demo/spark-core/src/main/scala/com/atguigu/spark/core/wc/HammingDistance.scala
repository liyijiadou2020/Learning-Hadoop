package com.atguigu.spark.core.wc

/**
 *
 * @author: Ли Ицзя
 * @version: 1.0
 * @create time: 2023-12-2023/12/4
 * @description: Определить Расстояние Хэмминга
 * ***********************************************************
 */
object HammingDistance {
  def hammingDistance(s1: String, s2: String): Int = {
    // Проверяем, что строки одинаковой длины
    if (s1.length != s2.length) {
      throw new IllegalArgumentException("Strings must be of equal length")
    }

    // Вычисляем расстояние Хэмминга
    s1.zip(s2).count { case (c1, c2) => c1 != c2 }
  }

  def main(args: Array[String]): Unit = {
    // Примеры использования
    println(hammingDistance("1011101", "1001001")) // Выводит 2
    println(hammingDistance("toned", "roses")) // Выводит 3
  }
}
