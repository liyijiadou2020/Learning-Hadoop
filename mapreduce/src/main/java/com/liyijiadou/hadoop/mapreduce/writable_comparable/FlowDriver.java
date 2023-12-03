package com.liyijiadou.hadoop.mapreduce.writable_comparable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

import static utils.FileUtils.deleteDirectoryIfExists;

/**
 * @author liyijia
 * @create 2023-11-2023/11/26
 * @status 成功运行
 * 运行的命令行参数：D:\playground\mapred-learning\02-writable\input D:\playground\mapred-learning\02-writable\output
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
        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(Text.class);
//        最终输出的 kv 类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);
//        设置输入输出路径
        String inputPath = "D:\\source\\Learning-Hadoop\\playground\\mapred-learning\\06-writable_comparable\\input";
        String outputPath = "D:\\source\\Learning-Hadoop\\playground\\mapred-learning\\06-writable_comparable\\output";
        deleteDirectoryIfExists(outputPath);
        FileInputFormat.setInputPaths(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));
//        提交 job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }


}
