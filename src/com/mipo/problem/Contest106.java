package com.mipo.problem;

import java.util.Stack;

/**
 * @Description Contest106
 * @Author yangsz
 * @Date 2018/10/19 18:48
 * @Version 1.0
 **/
public class Contest106 {

    public int minAddToMakeValid(String S) {
        Stack<Character> stack = new Stack<>();
        for(char ch:S.toCharArray()){
            if(stack.isEmpty()){
                stack.push(ch);
                continue;
            }
            char bracket = stack.peek();
            if(bracket=='('&&ch==')')stack.pop();
            else stack.push(ch);
        }
        return stack.size();
    }

    public int threeSumMulti(int[] A, int target) {
        int[][][] F = new int[A.length][target+1][3];
        for(int j=0;j<=target;j++){
            for(int i=0;i<A.length;i++){
                if(A[i]==j){
                    if(i>0)
                        F[i][j][0] = F[i-1][j][0]+1;
                    else
                        F[i][j][0] = 1;
                }else{
                    if(i>0)
                        F[i][j][0] = F[i-1][j][0]% (10^9 + 7);
                }
            }
        }
        for(int j=0;j<=target;j++){
            for(int i=1;i<A.length;i++){
                if(j-A[i]>=0)
                    F[i][j][1]= (F[i-1][j-A[i]][0]+F[i-1][j][1])% (10^9 + 7);
                else{
                    F[i][j][1] = F[i-1][j][1]% (10^9 + 7);
                }
            }
        }
        for(int j=0;j<=target;j++){
            for(int i=2;i<A.length;i++){
                if(j-A[i]>=0){
                    F[i][j][2] = (F[i-1][j-A[i]][1]+F[i-1][j][2] )% (10^9 + 7);
                }else{
                    F[i][j][2] = F[i-1][j][2] % (10^9 + 7);
                }

            }
        }
        return F[A.length-1][target][2]% (10^9 + 7);
    }

    public static void main(String[] args) {
        Contest106 test = new Contest106();
        /*System.out.println(test.minAddToMakeValid("())"));;
        System.out.println(test.minAddToMakeValid("((("));
        System.out.println(test.minAddToMakeValid("()"));
        System.out.println( test.minAddToMakeValid("()))(("));*/
        System.out.println(test.threeSumMulti(new int[]{1,1,2,2,3,3,4,4,5,5},8));
        System.out.println(test.threeSumMulti(new int[]{1,1,2,2,2,2},5));
        System.out.println(test.threeSumMulti(new int[]{0,0,0,0,0,0,0},0));
    }
}
