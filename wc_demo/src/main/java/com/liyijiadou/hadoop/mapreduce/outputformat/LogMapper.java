package com.liyijiadou.hadoop.mapreduce.outputformat;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
/**********************************
 * @author liyijia
 * @create 2023-11-2023/11/27
 * 输入：
 * http://www.baidu.com
 * http://www.google.com
 * http://cn.bing.com
 * http://www.atguigu.com
 * http://www.sohu.com
 * http://www.sina.com
 * http://www.sin2a.com
 * http://www.sin2desa.com
 * http://www.sindsafa.com
 * 输出：
 * http://www.atguigu.com 在一个单独的文件 auguigu.log 中
 * 其他内容在另一个文件 other.log 中
 *********************************
 */

public class LogMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    /**
     * 需求：过滤输入的log日志， 包含atguigu的网站输出到e:/atguigu.log， 不包含atguigu的网站输出到e:/other.log
     * @param key 一行数据
     * @param value 不需要value输出
     * @param context 上下文
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context)
            throws IOException, InterruptedException {

//        直接写出, 不做任何处理
        context.write(value, NullWritable.get());
    }
}
