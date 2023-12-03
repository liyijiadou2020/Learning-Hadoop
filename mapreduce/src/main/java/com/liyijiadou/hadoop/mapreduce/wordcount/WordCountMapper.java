package com.liyijiadou.hadoop.mapreduce.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author liyijia
 * @create 2023-11-2023/11/25
 *
 * Mapper类型的源码：public class Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT> {
 * KEYIN：map阶段输入的key类型。我们这里是偏移量，long类型
 * VALUEIN：map阶段输入的value类型。我们是 Text 类型（一行？）。
 * KEYOUT：map阶段输出的key类型，这里是 一个单词，Text
 * VALUEOUT：map阶段输出的value类型， 这里是 1，一个IntWritable。
 *
 *
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    /*单词*/
    private Text k = new Text();
    /*出现的次数*/
    private IntWritable v = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //        获取一行
        String line = value.toString();
        //        分割
        String[] words = line.split(" ");
        //        输出
        for (String word : words) {
//            封装 outKey
            k.set(word);
//            写出
            context.write(k, v);
        }
    }

}
