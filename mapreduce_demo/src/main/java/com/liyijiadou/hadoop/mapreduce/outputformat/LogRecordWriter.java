package com.liyijiadou.hadoop.mapreduce.outputformat;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;


import java.io.IOException;
import java.security.Key;

import static utils.FileUtils.deleteDirectoryIfExists;


/**
 * 创建两条文件输出流
 *
 * @author: liyijia
 * @version: 0.0
 * @create time: 2023-11-2023/11/27
 * @description: *****************************
 */
public class LogRecordWriter extends RecordWriter<Text, NullWritable> {

    private final FSDataOutputStream atguiguOut;
    private final FSDataOutputStream otherOut;

    public LogRecordWriter(TaskAttemptContext taskAttemptContext) {
        //        创建两条流
        try {
            FileSystem fileSystem = FileSystem.get(taskAttemptContext.getConfiguration());
            String dir = "D:\\source\\Learning-Hadoop\\playground\\mapred-learning\\09-outputformat\\output";
            deleteDirectoryIfExists(dir);

            atguiguOut = fileSystem.create(new Path(dir + "\\atguigu.log"));
            otherOut = fileSystem.create(new Path(dir + "\\other.log"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 具体执行往外写
     *
     * @param text
     * @param nullWriter
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void write(Text text, NullWritable nullWriter) throws IOException, InterruptedException {
//        具体写
        String log = text.toString();
        if (log.contains("atguigu")) {
            atguiguOut.writeBytes(log + "\n");
        } else {
            otherOut.writeBytes(log + "\n");
        }

    }

    /**
     * 关闭流
     *
     * @param taskAttemptContext
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        atguiguOut.close();
        otherOut.close();
    }
}
