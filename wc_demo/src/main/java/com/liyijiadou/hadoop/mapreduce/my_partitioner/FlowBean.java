package com.liyijiadou.hadoop.mapreduce.my_partitioner;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author liyijia
 * @create 2023-11-2023/11/26
 * @需求：统计每一个手机号耗费的总上行流量、下行流量、总流量。
 * @输入数据格式： 1    13736230513	192.196.100.1	www.atguigu.com	2481	24681	200
 * @期望输出数据格式: 13736230513 2481	24681	27162
 */


/**
 * FlowBean 类用来存放每个用户的上行流量、下行流量和总流量
 * 要实现 Writable 方法，才能支持 Map 传输给 Reduce
 * @设计步骤：
 *  1) 定义类实现Writable接口
 *  2) 重写序列化和反序列化函数
 *  3) 重写空参构造函数
 *  4) 重写 toString 方法用于打印输出
 */
public class FlowBean implements Writable {

    private long upFlow; // 上行流量
    private long downFlow; // 下行流量
    private long sumFlow; // 总流量

    //        空参构造
    public FlowBean() {
    }

    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }

    public void setSumFlow() {
        this.sumFlow = this.upFlow + this.downFlow;
    }

    /**
     * 序列化方法
     * @param dataOutput
     * @throws IOException
     */
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(upFlow);
        dataOutput.writeLong(downFlow);
        dataOutput.writeLong(sumFlow);
    }

    /**
     * @param dataInput
     * @throws IOException
     */
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.upFlow = dataInput.readLong();
        this.downFlow = dataInput.readLong();
        this.sumFlow = dataInput.readLong();
    }

    @Override
    public String toString() {
        return "\tupFlow=" + upFlow +
                "\tdownFlow=" + downFlow +
                "\tsumFlow=" + sumFlow;
    }
}
