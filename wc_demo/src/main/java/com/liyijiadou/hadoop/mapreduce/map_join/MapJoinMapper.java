package com.liyijiadou.hadoop.mapreduce.map_join;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: liyijia
 * @version: 0.0
 * @create time: 2023-11-2023/11/27
 * @description: ***********************************************************
 */
public class MapJoinMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    private Text outK = new Text();
    private Map<String, String> pdMap = new HashMap<>();

    /**
     * 读取缓存文件。任务开始前先将 pd 数据存进 pdMap
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void setup(Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException {

//        通过缓存文件得到小表数据pd.txt
        URI[] cacheFiles = context.getCacheFiles();
        Path path = new Path(cacheFiles[0]);

//        获取文件系统对象，并开流
        FileSystem fileSystem = FileSystem.get(context.getConfiguration());
        FSDataInputStream fsDataInputStream = fileSystem.open(path);

//        通过包装流转换成reader，方便按行读取
        BufferedReader reader = new BufferedReader(new InputStreamReader(fsDataInputStream, "UTF-8"));

//        逐行读取，按行处理
        String line;
        while (StringUtils.isNotEmpty(line = reader.readLine())) {
//            切割一行
            String[] split = line.split(" ");
            pdMap.put(split[0], split[1]);
        }

//        关流
        IOUtils.closeStream(reader);
        IOUtils.closeStream(fsDataInputStream);
    }

    /**
     * 每一行都会执行一次 map() 方法
     * 所以在 map 中读取大表的数据。
     * 这里只要处理大表即可！大表放在单独的一个目录下！
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context)
            throws IOException, InterruptedException {
        String[] split = value.toString().split("\t");
        String pname = pdMap.get(split[1]);
        outK.set(split[0] + "\t" + pname + "\t" + split[2]);
//        写出
        context.write(outK, NullWritable.get());
    }
}
