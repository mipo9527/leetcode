package com.mipo.problem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 红包金额派发算法
 *
 */
public class MoneyAllocateService {

    /**
     * 最大循环次数
     */
    private static final int MAX_TRY_TIMES = 100000;

    /**
     * 最大红包占总金额的系数
     */
    private static final double MAX_FACTOR = 0.2;

    /**
     * 单个红包的最小红包金额
     */
    private static final int MIN_MONEY = 1;


    public static void main(String[] args) {
        MoneyAllocateService allocate = new MoneyAllocateService();
        for (int i = 0; i < MAX_TRY_TIMES; i++) {
            //总金额，单位元
            BigDecimal totalAmount=new BigDecimal(100);
            //拆分个数
            int num=10;

            //金额列表
            List<BigDecimal> redMoneyList = allocate.getRedMoneyList(totalAmount, num);

            //对红包结果进行二次校验,测试使用，方便排查问题
            allocate.checkPacketList(redMoneyList,totalAmount);

            //输出红包列表
            System.out.println(redMoneyList);
        }
    }


    /**
     * 红包拆分方法
     *
     * @param money 红包总金额，单位为元
     * @param num  需要拆分的红包个数
     * @return 返回红包列表
     */
    public List<BigDecimal> getRedMoneyList(BigDecimal money, int num) {
        // 转换成以分为单位
        int moneyFen = money.multiply(new BigDecimal(100)).intValue();
        // 获取最大红包
        int maxMoney = getMaxMoney(moneyFen);

        List<BigDecimal> list = new ArrayList<>();

        //进行分配红包
        for (int i = 0; i < num; i++) {
            int amountFen = generateMoney(moneyFen, MIN_MONEY, maxMoney, num - i);
            BigDecimal amountYuan = toYuan(amountFen);
            list.add(amountYuan);
            moneyFen -= amountFen;
        }
        return list;
    }


    //**************************************************//

    /**
     * 获取单个红包金额
     * @param totalMoney 红包总额
     * @param minMoney 最小红包金额
     * @param maxMoney 最大红包金额
     * @param num    红包数量
     * @return 随机红包金额
     */
    private int generateMoney(int totalMoney, int minMoney, int maxMoney, int num) {
        //最后一个，则直接进行派发
        if (num == 1) {
            return totalMoney;
        }
        //相等则直接派发
        if (minMoney == maxMoney) {
            return minMoney;
        }
        // 当前最大金额
        int max = maxMoney > totalMoney ? totalMoney : maxMoney;
        // 随机金额
        int randomMoney = getRandomMoney(max,minMoney);
        // 剩余总金额
        int moneyRemain = totalMoney - randomMoney;
        // 校验剩余金额是否合法
        boolean qualify = isQualify(moneyRemain, num - 1);

        if (qualify) {
            return randomMoney;
        }
        //若不符合条件，则继续递归计算
        else {
            //剩余每个红包的平均值
            double avg = moneyRemain / (num - 1);
            //若平均值比1小，则在min和random之间寻找
            if (avg < MIN_MONEY) {
                return generateMoney(totalMoney, minMoney, randomMoney, num);
                //若大于最大值，则在random和max之间寻找
            } else if (avg > maxMoney) {
                return generateMoney(totalMoney, randomMoney, maxMoney, num);
            }
        }
        return randomMoney;
    }

    /**
     * 获取在指定区间的金额
     * @param maxMoney 最大金额,单位分
     * @param minMoney 最小金额,单位分
     * @return 随机金额值，单位分
     */
    private int getRandomMoney(int maxMoney,int minMoney){
        return ((int) (Math.random() * (maxMoney - minMoney)) + minMoney);
    }

    /**
     * 校验红包是否符合条件
     * @param money 待校验的红包金额
     * @param num 待分配的红包个数
     * @return true 符合条件;false 不符合条件
     */
    private boolean isQualify(int money, int num) {
        // 获取最大红包
        int maxMoney = getMaxMoney(money);
        double avg = money / num;
        // 判断是否小于最小金额
        if (avg < MIN_MONEY) {
            return false;
        }
        //判断是否大于允许的最大红包金额
        if(avg>maxMoney){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 分转换为元方法
     *
     * @param amountFen 单位为分
     * @return 返回元
     */
    private BigDecimal toYuan(long amountFen) {
        BigDecimal result = new BigDecimal(amountFen).
                divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
        return result;
    }

    /**
     * 获取最大红包金额
     *
     * @param money 红包总额，单位分
     * @return 最大红包金额，单位分
     */
    private int getMaxMoney(int money) {
        return (int) (money * MAX_FACTOR);
    }


    /**
     * 红包校验函数
     * @param list 产生的红包列表
     * @param totalAmount 总金额
     */
    private void checkPacketList(List<BigDecimal> list, BigDecimal totalAmount){
        BigDecimal result=new BigDecimal(0);
        BigDecimal max=totalAmount.multiply(new BigDecimal(MAX_FACTOR));
        BigDecimal min = new BigDecimal(MIN_MONEY);
        for (BigDecimal temp:list){
            //1.最小值校验
            if(temp.multiply(new BigDecimal(100)).compareTo(min)<0){
                throw new RuntimeException(String.format("temp %s is smaller then min %s",temp,min));
            }
            //2.最大值校验
            if(temp.compareTo(max) > 0){
                throw new RuntimeException(String.format("temp %s is bigger then max %s",temp,max));
            }
            result= result.add(temp);
        }
        //3.总和校验
        if(result.compareTo(totalAmount)!=0){
            throw new RuntimeException(String.format("result %s is not equal to total %s",result,totalAmount));
        }
    }


}