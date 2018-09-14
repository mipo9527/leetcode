package com.mipo.problem.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Contest101 {

    /**
     * 解题方法：
     *  easy
     */
    static class RLEIterator {
        int[] A ;
        int[] acc;
        int index;
        public RLEIterator(int[] A) {
            this.A = A;
            acc = new int[A.length/2];
            for(int i=0;i<A.length;i=i+2){
                acc[i/2]=A[i];
            }
        }

        public int next(int n) {
            while(index<acc.length){
                if(acc[index]>n){
                    acc[index]=acc[index]-n;
                    return A[index*2+1];
                }else if(acc[index]==n){
                    return A[index++*2+1];
                }else{
                    return next(n-acc[index++]);
                }
            }
            return -1;
        }
    }

   static class StockSpanner {
        List<Integer> history = new ArrayList<>();
        List<Integer> last = new ArrayList<>();
        int index = 0;

        public StockSpanner() {

        }

        public int next(int price) {
            history.add(price);
            int cnt = 1;
            for(int i=index-1;i>=0;){
                if(history.get(i)<=price){
                    cnt=cnt+last.get(i);
                    i = i - last.get(i);
                }
                else break;
            }
            last.add(cnt);
            index++;
            return cnt;
        }
    }

    /**
     * 解决方法： 按10进制位进行处理，从最高位开始处理直到最低位《N
     *
     * @param D
     * @param N
     * @return
     */
    public int atMostNGivenDigitSet(String[] D, int N) {
        int k = (int)Math.floor(Math.log10(N));
        int adj =(int) Math.pow(10,k);
        int sum = 0;
        for(int i=1;i<=k;i++){
            sum+=Math.pow(D.length,i);
        }
        return sum+loop(D, N, k, adj);
    }

    private int loop(String[] D,int N,int k,int adj){
        int sum = 0;
        for(String d:D){
            int id = Integer.parseInt(d);
            if((id+1)*adj<=N){
                if(k==0){
                    sum+=1;
                }else
                    sum+=Math.pow(D.length,k);
            }else{
                if(id*adj<N){
                    sum+=loop(D,N-id*adj,k-1,adj/10);
                }else{
                    if(id*adj==N&&k==0)sum+=1;  // 避免出现相等含0的情况
                }
                break;
            }
        }
        return sum;
    }


    public static void main(String args[]){
        Contest101 test = new Contest101();
       /* RLEIterator rleIterator = new RLEIterator(new int[]{3,8,0,9,2,5});
        System.out.println(rleIterator.next(2));
        System.out.println(rleIterator.next(1));
        System.out.println(rleIterator.next(1));
        System.out.println(rleIterator.next(2));*/
      /*  StockSpanner stockSpanner = new StockSpanner();
        System.out.println(stockSpanner.next(100));
        System.out.println(stockSpanner.next(80));
        System.out.println(stockSpanner.next(60));
        System.out.println(stockSpanner.next(70));
        System.out.println(stockSpanner.next(60));
        System.out.println(stockSpanner.next(75));
        System.out.println(stockSpanner.next(85));*/
        System.out.println(test.atMostNGivenDigitSet(new String[]{"1","3","5","7"},100));
        System.out.println(test.atMostNGivenDigitSet(new String[]{"1","4","9"},1000000000));
        System.out.println(test.atMostNGivenDigitSet(new String[]{"3","4","5","6"},64));
        System.out.println(test.atMostNGivenDigitSet(new String[]{"3","4","8"},4));
        System.out.println(test.atMostNGivenDigitSet(new String[]{"4","7","9"},412001178));
        System.out.println(test.atMostNGivenDigitSet(new String[]{"1","2","3","6","7","8"},211));
    }

}
