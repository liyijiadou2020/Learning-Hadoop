package com.liyijiadou.hadoop.mapreduce.map_join;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 需要合并两张表 订单数据表 t_order 和 t_product 的数据。
 * 其中订单数据表 t_order 的字段有：id pid amount
 * t_product 表的字段有：pid pname.
 * 输入：两张带有数据的表格
 * 输出：join之后的表，字段为 id pname amount
 *
 * @author: liyijia
 * @version: 0.0
 * @create time: 2023-11-2023/11/27
 * @description: ***********************************************************
 */
public class TableBean implements Writable {

    private String id; /* 订单id */
    private String pid; /* 商品id */
    private int amount; /* 商品数量 */
    private String pname; /* 商品名称 */
    private String flag; /* 判断来自order表还是pd表的字段 */

    public TableBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return id + '\t' + pname + '\t' + amount;
    }

    /**
     * 序列化方法
     *
     * @param dataOutput
     * @throws IOException
     */
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(id);
        dataOutput.writeUTF(pid);
        dataOutput.writeInt(amount);
        dataOutput.writeUTF(pname);
        dataOutput.writeUTF(flag);
    }

    /**
     * 反序列化方法
     *
     * @param dataInput
     * @throws IOException
     */
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readUTF();
        this.pid = dataInput.readUTF();
        this.amount = dataInput.readInt();
        this.pname = dataInput.readUTF();
        this.flag = dataInput.readUTF();
    }
}
