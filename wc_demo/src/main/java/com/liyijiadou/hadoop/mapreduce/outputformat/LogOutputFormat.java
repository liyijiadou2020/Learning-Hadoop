package com.liyijiadou.hadoop.mapreduce.outputformat;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 使用自定义OutputFormat类，将结果输出到两个文件中。
 @author: liyijia
 @version: 0.0
 @create time: 2023-11-2023/11/27
 @description:
 创建两条文件输出流：atguiguOut 和 otherOut。如果输入数据包含atguigu，输出到atguiguOut流；如果不包含atguigu，输出到otherOut流
 ************************************************************
 */
public class LogOutputFormat extends FileOutputFormat<Text, NullWritable> {

    /**
     * 往外写，需要返回一个 RecordWriter
     * @param taskAttemptContext
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public RecordWriter<Text, NullWritable> getRecordWriter(TaskAttemptContext taskAttemptContext)
            throws IOException, InterruptedException {
        LogRecordWriter logRecordWriter = new LogRecordWriter(taskAttemptContext);
        return logRecordWriter;
    }
}
