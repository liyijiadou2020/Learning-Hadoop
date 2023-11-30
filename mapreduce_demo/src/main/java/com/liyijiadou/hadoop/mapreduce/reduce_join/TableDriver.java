package com.liyijiadou.hadoop.mapreduce.reduce_join;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

import static utils.FileUtils.deleteDirectoryIfExists;

/**
 * @author: liyijia
 * @version: 0.0
 * @create time: 2023-11-2023/11/27
 * @description: ***********************************************************
 */
public class TableDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        //        获取job
        Job job = Job.getInstance(new Configuration());

        //        设置jar包
        job.setJarByClass(TableDriver.class);

        //        关联 M 和 R
        job.setMapperClass(TableMapper.class);
        job.setReducerClass(TableReducer.class);

        //        设置 M 和 R 输出的 kv 类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(TableBean.class);
        job.setOutputValueClass(TableBean.class);
        job.setOutputValueClass(NullWritable.class);
//        设置 ReduceTask 的个数
//        job.setNumReduceTasks(2);
        //        设置输入输出路径
        String inputPath = "D:\\source\\Learning-Hadoop\\playground\\mapred-learning\\10-join_table\\input";
        String outputPath = "D:\\source\\Learning-Hadoop\\playground\\mapred-learning\\10-join_table\\output";
        deleteDirectoryIfExists(outputPath);
        FileInputFormat.setInputPaths(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        //        提交 job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);


    }
}
