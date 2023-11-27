package com.liyijiadou.hadoop.mapreduce.patitioner_compare;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author liyijia
 * @create 2023-11-2023/11/26
 */
public class FlowReducer extends Reducer<FlowBean, Text, Text, FlowBean> {

    private FlowBean outV = new FlowBean();
    private Text outK = new Text();

    /**
     * @param key     FlowBean对象
     * @param values  手机号码
     * @param context 上下文
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(FlowBean key, Iterable<Text> values, Reducer<FlowBean, Text, Text, FlowBean>.Context context)
            throws IOException, InterruptedException {
        //        由于总流量相同的记录都会进入到同一个reduce函数中，
        //        需要遍历集合，循环写出，避免总流量相同的不同号码累加
        for (Text value : values) {
        //            注意！这里调换了KV的位置，反向写出了
            context.write(value, key);
        }

    }
}
