package com.liyijiadou.hadoop.mapreduce.wordcount;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author liyijia
 * @create 2023-11-2023/11/25
 * Reducer源码：<KEYIN, VALUEIN, KEYOUT, VALUEOUT> {
 * KEYIN：reduce 阶段输入的key类型
 * VALUEIN：reduce阶段输入的value类型
 * KEYOUT：reduce阶段输出的key类型
 * VALUEOUT：reduce阶段输出的value类型
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    //    提高运行效率，不需要每次reduce都创建一个新对象
    IntWritable outValue = new IntWritable();
    /**
     * reduce: 对每一种key调用一次
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context)
            throws IOException, InterruptedException {
        int sum = 0;
        //        要做累加
        for (IntWritable value : values) {
            sum += value.get();
        }
        //        写出
        outValue.set(sum);
        context.write(key, outValue);
    }

}