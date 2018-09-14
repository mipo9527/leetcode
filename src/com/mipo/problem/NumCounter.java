package com.mipo.problem;

/**
 * @Description TODO
 * @Author yangsz
 * @Date 2018/9/14 15:48
 * @Version 1.0
 **/

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 字符串中的数字计算器实现
 */
public class NumCounter {

    private int num;
    private int sum;
    // 是否当前是个完整的数字
    private boolean isWholeNum;

    public NumCounter(int num, int sum, boolean isWholeNum) {
        this.num = num;
        this.sum = sum;
        this.isWholeNum = isWholeNum;
    }

    public NumCounter accumulate(Character c){
        if (Character.isDigit(c)){
            return isWholeNum ? new NumCounter(Integer.parseInt("" + c), sum + num, false) : new NumCounter(Integer.parseInt("" + num + c), sum, false);
        }else {
            return new NumCounter(0, sum + num, true);
        }
    }

    public NumCounter combine(NumCounter numCounter){
        return new NumCounter(numCounter.num, this.getSum() + numCounter.getSum(), numCounter.isWholeNum);
    }

    public int getSum() {
        return sum + num;
    }

    public static void main(String[] args) {
        String arr = "12%3 21sdas s34d dfsdz45   R3 jo34 sjkf8 3$1P 213ikflsd fdg55 kfd";
        Stream<Character> stream = IntStream.range(0, arr.length()).mapToObj(arr::charAt);
        System.out.println("ordered total: " + countNum(stream));
    }

    private static int countNum(Stream<Character> stream){
        NumCounter numCounter = stream.reduce(new NumCounter(0, 0, false), NumCounter::accumulate, NumCounter::combine);
        return numCounter.getSum();
    }
}
