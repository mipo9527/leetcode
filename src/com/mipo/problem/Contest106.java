package com.mipo.problem;

import com.mipo.problem.utils.Utils;

import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

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
                    F[i][j][1]= (F[i-1][j-A[i]][0]+F[i-1][j][1])% 1000000007;
                else{
                    F[i][j][1] = F[i-1][j][1]% 1000000007;
                }
            }
        }
        for(int j=0;j<=target;j++){
            for(int i=2;i<A.length;i++){
                if(j-A[i]>=0){
                    F[i][j][2] = (F[i-1][j-A[i]][1]+F[i-1][j][2] )% 1000000007;
                }else{
                    F[i][j][2] = F[i-1][j][2] % 1000000007;
                }

            }
        }
        return F[A.length-1][target][2]% 1000000007;
    }

    public int minMalwareSpread1(int[][] graph, int[] initial) {
        int n = graph.length;
        for(int f:initial){
            int cnt = 0;
            for(int j=0;j<n;j++){
                if(j==f)continue;
                if(graph[f][j]==1)cnt++;
            }
            if(cnt==1){
                return f;
            }
        }
        return Arrays.stream(initial).min().getAsInt();
    }

    public int minMalwareSpread(int[][] graph, int[] initial) {
        int n = graph.length;
        int min = Integer.MAX_VALUE;
        for(int f:initial){
            int cnt = 0;
            for(int j=0;j<n;j++){
                if(j==f)continue;
                if(graph[f][j]==1)cnt++;
            }
            if(cnt>1){
                return f;
            }
            if(cnt==1){
                boolean found = false;
                if(min!=Integer.MAX_VALUE){
                    for(int j=0;j<n;j++){
                        if(graph[min][j]==1&&graph[f][j]==1){
                            found = true;
                            break;
                        }
                    }
                }
                if(found)min = Integer.MAX_VALUE;
                else min = Math.min(min,f);
            }
        }
        return min==Integer.MAX_VALUE?Arrays.stream(initial).min().getAsInt():min;
    }

    public static void main(String[] args) {
        Contest106 test = new Contest106();
        /*System.out.println(test.minAddToMakeValid("())"));;
        System.out.println(test.minAddToMakeValid("((("));
        System.out.println(test.minAddToMakeValid("()"));
        System.out.println( test.minAddToMakeValid("()))(("));*/
        /*System.out.println(test.threeSumMulti(new int[]{1,1,2,2,3,3,4,4,5,5},8));
        System.out.println(test.threeSumMulti(new int[]{1,1,2,2,2,2},5));*/
        //System.out.println(test.threeSumMulti(new int[]{0,0,0,0,0,0,0},0));
        /*System.out.println(test.minMalwareSpread(Utils.to2DArray("[[1,0,0,0,0,0,0,0,0],[0,1,0,0,0,0,0,0,0],[0,0,1,0,1,0,1,0,0],[0,0,0,1,0,0,0,0,0],[0,0,1,0,1,0,0,0,0],[0,0,0,0,0,1,0,0,0],[0,0,1,0,0,0,1,0,0],[0,0,0,0,0,0,0,1,0],[0,0,0,0,0,0,0,0,1]]")
                ,new int[]{6,0,4}));*/
        System.out.println(test.minMalwareSpread(Utils.to2DArray("[[1,1,1,0],[1,1,0,0],[1,0,1,0],[0,0,0,1]]")
                ,new int[]{3,2}));
    }
}
