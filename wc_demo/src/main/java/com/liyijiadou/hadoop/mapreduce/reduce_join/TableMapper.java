package com.liyijiadou.hadoop.mapreduce.reduce_join;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;


import java.io.IOException;
import java.util.Set;

/**
 * @author: liyijia
 * @version: 0.0
 * @create time: 2023-11-2023/11/27
 * @description: ***********************************************************
 */
public class TableMapper extends Mapper<LongWritable, Text, Text, TableBean> {

    private String filename;
    private Text outK = new Text();
    private TableBean outV = new TableBean();

    /**
     * 初始化方法，因为有两个文件所以需要初始化
     * 一个文件会开启一次map task, 每次开启map task 都会来执行一次 setup() 方法。
     *
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void setup(Mapper<LongWritable, Text, Text, TableBean>.Context context) throws IOException, InterruptedException {
//        获取文件的名称
        FileSplit split = (FileSplit) context.getInputSplit();
        filename = split.getPath().getName(); // CTRL + ALT + F 升级成类变量

    }

    /**
     * 每一行都会执行一次 map() 方法
     *
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, TableBean>.Context context)
            throws IOException, InterruptedException {
//        获取一行
        String line = value.toString();

//        判断是哪张表（那个文件），然后获取感兴趣的字段
        if (filename.contains("order")) { // 处理的是订单表(order.txt)
            String[] split = line.split("\t");
//            封装KV
            outK.set(split[1]);
            outV.setId(split[0]);
            outV.setPid(split[1]);
            outV.setAmount(Integer.parseInt(split[2]));
            outV.setPname("");
            outV.setFlag("order");
        } else { // 处理的是商品表(pd.txt)
            String[] split = line.split(" ");
//            封装KV
            outK.set(split[0]);
            outV.setId("");
            outV.setPid(split[0]);
            outV.setAmount(0);
            outV.setPname(split[1]);
            outV.setFlag("pd");
        }
//        写出
        context.write(outK, outV);
    }
}
