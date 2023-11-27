package com.liyijiadou.hadoop.mapreduce.map_join;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static utils.FileUtils.deleteDirectoryIfExists;

/**
 * @author: liyijia
 * @version: 0.0
 * @create time: 2023-11-2023/11/27
 * @description: ***********************************************************
 */
public class MapJoinDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
        //        1 获取job
        Job job = Job.getInstance(new Configuration());

        //        2 设置jar包
        job.setJarByClass(MapJoinDriver.class);

        //        3 关联 M 和 R （这里没有用到Reducer）
        job.setMapperClass(MapJoinMapper.class);

        //        4 & 5 设置 M 和 R 输出的 kv 类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class); // 注意，注意！！输出的是 NullWritable！
        job.setOutputValueClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

//        【加载缓存数据】
        job.addCacheFile(new URI("file:///D:/hadoop/pd.txt"));
//        根本不需要使用 Reduce 任务，因此设置 reduce task 的数量为0
        job.setNumReduceTasks(0);

        //        6 设置输入输出路径
        String inputPath = "D:\\source\\Learning-Hadoop\\playground\\mapred-learning\\11-map_join\\input";
        String outputPath = "D:\\source\\Learning-Hadoop\\playground\\mapred-learning\\11-map_join\\output";
        deleteDirectoryIfExists(outputPath);
        FileInputFormat.setInputPaths(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        //        7 提交 job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);

    }
}
