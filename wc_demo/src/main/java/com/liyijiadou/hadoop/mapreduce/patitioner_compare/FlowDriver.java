package com.liyijiadou.hadoop.mapreduce.patitioner_compare;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

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
//        关联 M 和 R，关联 P
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);
        job.setPartitionerClass(ProvincePartitioner.class);
//        设置 ReduceTask 的个数
        job.setNumReduceTasks(5);

//        设置 map 输出的 kv 类型
        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(Text.class);
//        最终输出的 kv 类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);
//        设置输入输出路径
        String inputPath = "D:\\source\\Learning-Hadoop\\playground\\mapred-learning\\07-patitioner_compare\\input";
        String outputPath = "D:\\source\\Learning-Hadoop\\playground\\mapred-learning\\07-patitioner_compare\\output";
        deleteDirectoryIfExists(outputPath);
        FileInputFormat.setInputPaths(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));
//        提交 job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }


    /**
     * 使用 Files.walk 方法来遍历目录，并删除每个文件和子目录，最后删除目录本身。
     * @param dir
     * @throws IOException
     */
    public static void deleteDirectoryIfExists(String dirName) throws IOException {
        java.nio.file.Path dir = Paths.get(dirName);
        // Check if the directory exists
        if (Files.exists(dir)) {
            // Walk the directory
            try (Stream<java.nio.file.Path> walk = Files.walk(dir)) {
                // Sort in reverse order so the directory entries get deleted before the directory itself
                walk.sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                // Delete each entry
                                Files.delete(path);
                            } catch (IOException e) {
                                // Handle the potential IOException
                                System.err.printf("Unable to delete this path : %s%n%s", path, e);
                            }
                        });
            } catch (IOException e) {
                // Handle the potential IOException from walking the directory
                throw e;
            }
        }
    }
}
