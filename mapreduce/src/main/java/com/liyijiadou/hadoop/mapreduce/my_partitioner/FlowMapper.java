package com.liyijiadou.hadoop.mapreduce.my_partitioner;

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
public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
    /**
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */

    private Text outK = new Text();
    private FlowBean outV = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, FlowBean>.Context context)
            throws IOException, InterruptedException {
//        1、获取一行
        String line = value.toString();
//        2、进行切割
        String[] split = line.split("\t");
//        3、抓取想要的数据: 手机号和上下行流量。这里注意，上下行流量是从后往前取的（技巧！）
        String phoneNumber = split[1];
        String up = split[split.length - 3];
        String down = split[split.length - 2];
//        4、封装
        outK.set(phoneNumber);
        outV.setUpFlow(Long.parseLong(up));
        outV.setDownFlow(Long.parseLong(down));
        outV.setSumFlow();
//        5、写出
        context.write(outK, outV);
    }
}
