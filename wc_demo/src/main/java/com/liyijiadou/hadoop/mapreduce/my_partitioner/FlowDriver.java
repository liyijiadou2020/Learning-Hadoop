package com.liyijiadou.hadoop.mapreduce.my_partitioner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author liyijia
 * @create 2023-11-2023/11/26
 * @status 成功运行
 */
public class FlowDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
//        获取job
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
//        设置jar包
        job.setJarByClass(FlowDriver.class);
//        关联 M 和 R
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);
//        设置 map 输出的 kv 类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
//        最终输出的 kv 类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

//        指定自定义的分区器
        job.setPartitionerClass(ProvincePartitioner.class);
//        同时指定相应数量的ReduceTask
        job.setNumReduceTasks(5);

//        设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path("D:\\playground\\mapred-learning\\05-partitioner\\input"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\playground\\mapred-learning\\05-partitioner\\output"));
//        提交 job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
