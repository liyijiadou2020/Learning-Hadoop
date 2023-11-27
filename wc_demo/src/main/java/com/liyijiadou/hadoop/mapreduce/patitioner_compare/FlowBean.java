package com.liyijiadou.hadoop.mapreduce.patitioner_compare;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author liyijia
 * @create 2023-11-2023/11/26
 * @ 需求：根据序列化案例产生的结果再次对总流量进行倒序排序。
 * @输入数据格式： 13470253144        upFlow=180	downFlow=180	sumFlow=360
 * 13509468723		upFlow=7335	downFlow=110349	sumFlow=117684
 * 13560439638		upFlow=918	downFlow=4938	sumFlow=5856
 * 13568436656		upFlow=3597	downFlow=25635	sumFlow=29232
 * @期望输出数据格式: 13509468723        upFlow=7335	downFlow=110349	sumFlow=117684
 * 13568436656		upFlow=3597	downFlow=25635	sumFlow=29232
 * 13560439638		upFlow=918	downFlow=4938	sumFlow=5856
 * 13470253144        upFlow=180	downFlow=180	sumFlow=360
 */


/**
 * FlowBean 类用来存放每个用户的上行流量、下行流量和总流量
 * 原本要实现 Writable 方法，才能支持 Map 传输给 Reduce。
 * 现在要实现 WritableComparable 接口，这样才能支持 MapReduce 对 FlowBean 对象做排序操作。
 *
 */
public class FlowBean implements WritableComparable<FlowBean> {

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
        return upFlow +
                "\t" + downFlow +
                "\t" + sumFlow;
    }

//    @Override
//    public int compareTo(FlowBean o) {
////        总流量的倒序排序
//        if (this.sumFlow > o.sumFlow) {return -1;}
//        else if (this.sumFlow < o.sumFlow) {return 1;}
//        else return 0;
//    }

//    进行二次排序：如果总流量相同则按照上行流量排序
    @Override
    public int compareTo(FlowBean o) {
    //        总流量的倒序排序
        if (this.sumFlow > o.sumFlow) {return -1;}
        else if (this.sumFlow < o.sumFlow) {return 1;}
        else{
            if (this.upFlow > o.upFlow) {return -1;}
            else if (this.upFlow < o.upFlow) {return 1;}
        }
        return 0;
    }

}
