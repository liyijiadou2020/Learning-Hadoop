package com.liyijiadou.hadoop.mapreduce.reduce_join;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * @author: liyijia
 * @version: 0.0
 * @create time: 2023-11-2023/11/27
 * @description: ***********************************************************
 */
public class TableReducer extends Reducer<Text, TableBean, TableBean, NullWritable> {

    /**
     * 创建一个orders集合，然后对orders中的每一个TableBean对象填充“空”字段。
     * “合并” 操作是在Reduce 阶段完成的。所以Reduce 端的处理压力太大。Map节点的运算负载则很低。
     * 解决方案：Map端实现数据合并
     * @param key     pid
     * @param values  TableBean类型
     * @param context 上下文
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<TableBean> values, Reducer<Text, TableBean, TableBean, NullWritable>.Context context)
            throws IOException, InterruptedException {

        ArrayList<TableBean> orderBeans = new ArrayList<>();
        TableBean pdBean = new TableBean();

        for (TableBean tableBean : values) {
            if ("order".equals(tableBean.getFlag())) { // 订单表
                TableBean tmpOrderBean = new TableBean(); // 创建一个临时 MapJoinBean 来接收 value

                try {
                    BeanUtils.copyProperties(tmpOrderBean, tableBean);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
//                将临时 MapJoinBean 对象添加到集合 orderBeans
                orderBeans.add(tmpOrderBean);

            } else { // 商品表

                try {
                    BeanUtils.copyProperties(pdBean, tableBean);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }

        }

//        遍历集合 orderBeans，替换每个orderBean 的 pid 为 pname，然后写出
        for (TableBean orderBean : orderBeans) {
            orderBean.setPname(pdBean.getPname());
            context.write(orderBean, NullWritable.get()); // 写出修改后的 orderBean 对象
        }


    }
}
