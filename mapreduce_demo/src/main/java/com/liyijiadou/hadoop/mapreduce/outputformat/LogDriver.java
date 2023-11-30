package com.liyijiadou.hadoop.mapreduce.outputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;
import utils.FileUtils.*;

import static utils.FileUtils.deleteDirectoryIfExists;


/**
 * @author liyijia
 * @create 2023-11-2023/11/27
 */
public class LogDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(LogDriver.class);
        job.setMapperClass(LogMapper.class);
        job.setReducerClass(LogReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //设置自定义的 outputformat
        job.setOutputFormatClass(LogOutputFormat.class);
        FileInputFormat.setInputPaths(job, new Path("D:\\source\\Learning-Hadoop\\playground\\mapred-learning\\09-outputformat\\input"));

//虽然我们自定义了 outputformat，但是因为我们的 outputformat 继承自fileoutputformat
//而 fileoutputformat 要输出一个_SUCCESS 文件，所以在这还得指定一个输出目录（这里的路径只会输出一个 _SUCCESS 文件
        String dir_succeed = "D:\\hadoop\\logoutput";
        deleteDirectoryIfExists(dir_succeed);
        FileOutputFormat.setOutputPath(job, new Path(dir_succeed));
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }
}
