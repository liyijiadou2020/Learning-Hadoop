package com.liyijiadou.hadoop.mapreduce.patitioner_compare;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author liyijia
 * @create 2023-11-2023/11/26
 * input_key：偏移量，LongWritable类型
 * input_value：一行的内容，Text类型
 * output_key：手机号，Text类型
 * output_value：流量对象，FlowBean类型
 */
public class FlowMapper extends Mapper<LongWritable, Text, FlowBean, Text> {

    private FlowBean outK = new FlowBean();
    private Text outV = new Text();

    /**
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, FlowBean, Text>.Context context)
            throws IOException, InterruptedException {
//        获取一行
        String line = value.toString();
//        切割
        String[] split = line.split("\t");
//        封装
        outV.set(split[0]);
        outK.setUpFlow(Long.parseLong(split[1]));
        outK.setDownFlow(Long.parseLong(split[2]));
        outK.setSumFlow();
//        写出
        context.write(outK, outV);

    }

}
