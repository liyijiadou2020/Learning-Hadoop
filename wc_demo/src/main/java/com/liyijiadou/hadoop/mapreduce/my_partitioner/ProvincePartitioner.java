package com.liyijiadou.hadoop.mapreduce.my_partitioner;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.io.Text;

/**
 * @author liyijia
 * @create 2023-11-2023/11/27
 */
public class ProvincePartitioner extends Partitioner<Text, FlowBean> {
    /**
     * 自定义的分区类
     * 需求：将统计结果按照手机归属地不同省份输出到不同文件中（分区）
     * 期望输出：手机号136、137、138、139开头的分别放在一个单独的4个文件中，其他开头的放到一个文件中
     */

    private final static String numberPrefix1 = "136";
    private final static String numberPrefix2 = "137";
    private final static String numberPrefix3 = "138";
    private final static String numberPrefix4 = "139";

    /**
     * @param text 手机号
     * @param flowBean 流量类
     * @param i 分区数量
     * @return 分区号
     */
    @Override
    public int getPartition(Text text, FlowBean flowBean, int i) {

        String phoneNumber = text.toString();
        String substring = phoneNumber.substring(0, 3);
//        定义一个分区号变量partition, 根据partition设置分区号
        int partition;
        switch (substring) {
            case numberPrefix1:
                partition = 0;
                break;
            case numberPrefix2:
                partition=1;
                break;
            case numberPrefix3:
                partition=2;
                break;
            case numberPrefix4:
                partition=3;
                break;
            default:
                partition=4;
        }
        return partition;
    }
}
