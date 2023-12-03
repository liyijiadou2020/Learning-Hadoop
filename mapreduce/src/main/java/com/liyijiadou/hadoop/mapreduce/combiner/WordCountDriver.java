package com.liyijiadou.hadoop.mapreduce.combiner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

import static utils.FileUtils.deleteDirectoryIfExists;

/**
 * @author liyijia
 * @create 2023-11-2023/11/26
 * @status 成功运行
 * 固定的套路，7步走：
 * 1. 获取 job 对象
 * 2. 关联 Driver 程序的 jar
 * 3. 关联 Mapper 和 Reducer 的 jar
 * 4. 设置 Mapper 输出的 kv 类型
 * 5. 设置最终输出的 kv 类型
 * 6. 设置输入输出路径
 * 7. 提交 job
 *
 * @ 结果：运行成功。
 *
 */
public class WordCountDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
//        获取job
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
//        设置jar包
        job.setJarByClass(WordCountDriver.class);
//        关联 M 和 R，可选：Combiner
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
//        job.setCombinerClass(WordCountCombiner.class);
        job.setCombinerClass(WordCountReducer.class);

//        设置 map 输出的 kv 类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
//        最终输出的 kv 类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

//        设置输入输出路径
        String inputPath = "D:\\source\\Learning-Hadoop\\playground\\mapred-learning\\08-combiner\\input";
        String outputPath = "D:\\source\\Learning-Hadoop\\playground\\mapred-learning\\08-combiner\\output";
        deleteDirectoryIfExists(outputPath);
        FileInputFormat.setInputPaths(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

//        提交 job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}