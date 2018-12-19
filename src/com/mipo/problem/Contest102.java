package com.mipo.problem;

import java.util.*;
import java.util.stream.Collectors;

public class Contest102 {

    public static int totalFruit(int[] tree) {
        int max=0;
        for(int i=0;i<tree.length;){
            int cnt = 1,second=-1,firstSecond = i+1,first = tree[i];
            for(int j=i+1;j<tree.length;j++){
                if(tree[j]==first||tree[j]==second){
                    cnt++;
                    continue;
                }
                if(second==-1){
                    second=tree[j];
                    firstSecond = j;
                    cnt++;
                    continue;
                }else{
                    if(tree[j]!=second)break;
                }
            }
            i=firstSecond;
            max = Math.max(cnt,max);
        }
        return max;
    }

    /**
     * Given an array of integers A, find the sum of min(B), where B ranges over every (contiguous) subarray of A.
     *
     * Since the answer may be large, return the answer modulo 10^9 + 7.
     * @param A
     * @return
     */
    public int sumSubarrayMins1(int[] A) {
        int[] minLen = new int[A.length];
        int  sum = 0;
        for(int i=0;i<A.length;i++){
            int min = A[i];
            int last = i;
            int total = minLen[i]*A[i];
            for(int j=i+minLen[i];j<A.length;j++){
                if(A[j]<min){
                    min=A[j];
                    minLen[last] = j-last;
                    last = j;
                }
                total+=min;
            }
            sum = (sum+total)%1000000007;
        }
        return sum%1000000007;
    }

    public int sumSubarrayMins2(int[] A) {
        int[] minLen = new int[A.length];
        int[] sumMin = new int[A.length];
        int  sum = 0;
        for(int i=0;i<A.length;i++){
            int min = A[i];
            int last = i;
            int total = 0;
            for(int j=i;j<A.length;j++){
                if(A[j]<min){
                    if(minLen[j]!=0){
                        minLen[last] = j-last;
                        total += sumMin[i-1]-minLen[i-1]*A[i-1];
                        break;
                    }
                    min=A[j];
                    minLen[last] = j-last;
                    last = j;
                }
                if(minLen[j]!=0){
                    total += sumMin[i-1]-minLen[i-1]*A[i-1];
                    break;
                }
                total+=min;
            }
            if(minLen[last]==0)minLen[last] = A.length-last;
            sumMin[i] = total;
        }
        for(int i=0;i<A.length;i++){
            sum=(sum+sumMin[i]%1000000007)%1000000007;
        }
        return sum%1000000007;
    }

    public int sumSubarrayMins(int[] A) {
          int res = 0, M = 1000000007, l = A.length, k = 0;
          int[] idx = new int[l + 1];
          int[] val = new int[l + 1];
          idx[0] = -1;
          for (int i = 0; i < l; i++) {
              int n = A[i];
              while (k > 0 && A[idx[k]] >= n) k--;
              int m = (n * (i - idx[k]) + val[k]) % M;
              res = (res + m) % M;
              idx[++k] = i;
              val[k] = m;
          }
          return res;
      }

    /**
     * Let's say a positive integer is a superpalindrome if it is a palindrome, and it is also the square of a palindrome.
     *
     * Now, given two positive integers L and R (represented as strings), return the number of superpalindromes in the inclusive range [L, R].
     */

    public int superpalindromesInRange(String L, String R) {
        Long left = Long.valueOf(L);
        Long right = Long.valueOf(R);
        int cnt=0;
        for(Long res:superPals){
            if(res>=left&&res<=right){
                cnt++;
            }
        }
        return cnt;
    }
    static Set<Long> superPals = new TreeSet<>();
    static List<Long> pals = new ArrayList<>();

    static{
        getAllSuperPals();
    }

    public static void getAllSuperPals(){
        for(int i=1;i<=9;i++){
            palindromes(1,i,new int[i]);
        }
        for(Long pal:pals){
            if(isPalindromes(pal*pal)){
                superPals.add(pal*pal);
            }
            long spal = (long)Math.sqrt(pal);
            if(spal*spal==pal&&pals.contains(spal)){
                superPals.add(pal);
            }
        }
    }

    private static boolean isPalindromes(Long a){
        char[] s = String.valueOf(a).toCharArray();
        for(int i=0;i<s.length/2;i++){
            if(s[i]==s[s.length-i-1])continue;
            else return false;
        }
        return true;
    }


    public static void palindromes(int k,int n,int[] record){
        if(k>(n+1)/2){
            Long val = Long.valueOf(String.join("",Arrays.stream(record).mapToObj(x->String.valueOf(x)).collect(Collectors.toList())));
            pals.add(val);
            return;
        }
        for(int i=(k==1?1:0);i<=9;i++){
            record[k-1]=i;
            record[n-k]=i;
            palindromes(k+1,n,record);
        }
    }

    public static void main(String args[]){
        Contest102 test = new Contest102();
        //System.out.println(test.sumSubarrayMins(new int[]{1,2,4}));
        /*System.out.println(test.sumSubarrayMins(new int[]{3,1,2,4}));
        System.out.println(test.sumSubarrayMins(new int[]{51,29}));
        System.out.println(test.sumSubarrayMins(new int[]{48,87,27}));
        System.out.println(test.sumSubarrayMins(new int[]{1,2,3}));
        System.out.println(test.sumSubarrayMins(new int[]{97,61,59,45}));*/
        System.out.println(test.superpalindromesInRange("1","213"));;
    }
}
