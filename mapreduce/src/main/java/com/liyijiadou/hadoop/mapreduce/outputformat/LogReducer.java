package com.liyijiadou.hadoop.mapreduce.outputformat;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


/**
 * @author liyijia
 * @create 2023-11-2023/11/27
 */
public class LogReducer extends Reducer<Text, NullWritable, Text, NullWritable> {

    /**
     * @param key 一行文本
     * @param values 无
     * @param context 上下文
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Reducer<Text, NullWritable, Text, NullWritable>.Context context)
            throws IOException, InterruptedException {
//       防止有相同的数据会丢数据，需要迭代写出
        for (NullWritable value : values) {
            context.write(key, NullWritable.get());
        }
    }
}
