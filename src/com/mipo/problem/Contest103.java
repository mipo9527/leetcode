package com.mipo.problem;

import com.mipo.problem.utils.Utils;

import java.util.Arrays;


public class Contest103 {
    int[][] board;
    int min;
    int n;
    int[] depth;
    public int snakesAndLadders(int[][] board) {
        this.board = board;
        min = Integer.MAX_VALUE;
        n = board.length;
        depth = new int[n*n+1];
        Arrays.fill(depth,Integer.MAX_VALUE);
        dfs(1,0);
        return min==Integer.MAX_VALUE?-1:min;
    }

    public class Point{
        int col;
        int row;

        public Point(int row, int col) {
            this.col = col;
            this.row = row;
        }
    }

    public void dfs(int p,int d){
        if(p==n*n){
            min = Math.min(min,d);
            return;
        }
        for(int i=p+1;i<=p+6&&i<=n*n;i++){
            if(depth[i]<d+1)continue;
            else depth[i] = d+1;
            Point p1 = point(n,i);
            int k = board[p1.row][p1.col];
            if(k==-1){
                if(i==p+6||i==n*n){
                    dfs(i,d+1);
                }
            }else{
                while(k!=-1){
                    Point p2 = point(n,k);
                    if(board[p2.row][p2.col]!=-1){
                        if(k==board[p2.row][p2.col]){min = -1;return ;}
                        else k = board[p2.row][p2.col];
                    }else{
                        dfs(k,d+1);
                        break;
                    }
                }
            }
        }
    }

    private Point point(int n,int p){
        int r = n-(p-1)/n-1;
        int direct = (n-r)%2==0?-1:1;
        int c = (direct==1?-1:n)+(p-(n-r-1)*n)*direct;
        return new Point(r,c);
    }

    public static void main(String args[]){
        Contest103 test = new Contest103();
        System.out.println(test.snakesAndLadders(Utils.to2DArray("[\n" +
                "[-1,-1,-1,-1,-1,-1],\n" +
                "[-1,-1,-1,-1,-1,-1],\n" +
                "[-1,-1,-1,-1,-1,-1],\n" +
                "[-1,35,-1,-1,13,-1],\n" +
                "[-1,-1,-1,-1,-1,-1],\n" +
                "[-1,15,-1,-1,-1,-1]]")));
        System.out.println(test.snakesAndLadders(Utils.to2DArray("[[4,-1],[-1,3]]")));
        System.out.println(test.snakesAndLadders(Utils.to2DArray("[[-1,-1,-1],[-1,9,8],[-1,8,9]]")));
        System.out.println(test.snakesAndLadders(Utils.to2DArray("[[-1,7,-1],[-1,6,9],[-1,-1,2]]")));
        System.out.println(test.snakesAndLadders(Utils.to2DArray("[[1,1,-1],[1,1,1],[-1,1,1]]")));
        System.out.println(test.snakesAndLadders(Utils.to2DArray("[[-1,1,2,-1],[2,13,15,-1],[-1,10,-1,-1],[-1,6,2,8]]")));
    }


}
