package com.mipo.problem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.ToIntFunction;

public class Contest91 {
	
	 public static boolean lemonadeChange(int[] bills) {
		int[] hold = new int[3];
		for(int i=0;i<bills.length;i++){
			switch(bills[i]){
			case 5:
				hold[0]++;
				continue;
			case 10:
				if(hold[0]>0)
					hold[0]--;
				else 
					return false;
				hold[1]++;
				continue;
			case 20:
				boolean minus10 = false;
				if(hold[1]>0){
					hold[1]--;
					minus10 = true;
				}
				if(minus10){
					if(hold[0]>0)
						hold[0]--;
					else
						return false;
				}else{
					if(hold[0]>3)
						hold[0]=hold[0]-3;
					else
						return false;
				}
				hold[2]++;
			}
		}
		return true;
	 }
	 public class TreeNode {
		 int val;
		 TreeNode left;
		 TreeNode right;
		 TreeNode(int x) { val = x; }
	}
	 
	 public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
		 Set<Integer> res  = new HashSet<Integer>();
		 TreeNode[] parents = new TreeNode[1000];
		 parents[root.val] = null;
		 DFS(root,parents);
		 res.addAll(forward(target,K));
		 res.addAll(backward(parents,target,K));
		 List<Integer> ret = new ArrayList<Integer>();
		 ret.addAll(res);
		 return ret;
	 }
	 
	 public void DFS(TreeNode root,TreeNode[] nodes){
		 if(root.left!=null){
			 nodes[root.left.val] = root;
			 DFS(root.left,nodes);
		 }
		 if(root.right!=null){
			 nodes[root.right.val] = root;
			 DFS(root.right,nodes);
		 }
	 }
	 
	 public Set<Integer> backward(TreeNode[] parents,TreeNode target,int K){
		 Set<Integer> res = new HashSet<Integer>();
		 if(K==0){
			 res.add(target.val);
			 return res;
		 }
		 if(parents[target.val]!=null){
			 res.addAll(backward(parents,parents[target.val],K-1));
			 if(K-1>0){
				 if(parents[target.val].left!=null&&parents[target.val].left.equals(target)){
					 if(parents[target.val].right!=null)
						 res.addAll(forward(parents[target.val].right,K-2));
				 }
				 if(parents[target.val].right!=null&&parents[target.val].right.equals(target)){
					 if(parents[target.val].left!=null)
						 res.addAll(forward(parents[target.val].left,K-2));
				 }
			 }
		 }
		 return res;
	 }
	 
	 public Set<Integer> forward(TreeNode target, int K){
		 Set<Integer> res = new HashSet<Integer>();
		 if(K==0){
			 res.add(target.val);
			 return res;
		 }
		 if(target.left!=null){
			 res.addAll(forward(target.left,K-1));
		 }
		 if(target.right!=null){
			 res.addAll(forward(target.right,K-1));
		 }
		 return res;
	 }
	 
	 public static int matrixScore(int[][] A) {
	     for(int i=0;i<A.length;i++){
	    	 if(A[i][0]==0){
	    		toggleRow(A,i);
	    	 }
	     }
	     for(int i=1;i<A[0].length;i++){
	    	 int cnt = 0;
	    	 for(int j=0;j<A.length;j++){
	    		 if(A[j][i]==0)cnt++;
	    	 }
	    	 if(cnt>A.length/2){
	    		 toggleCol(A,i);
	    	 }
	     }
	     int sum=0;
	     for(int i=0;i<A.length;i++){
	    	sum+=Integer.valueOf(tostr(A[i]),2);
	     }
	     
	     return sum;
	 }
	 
	 public static String tostr(int[] a){
		 String res ="";
		 for(int i=0;i<a.length;i++){
			 res+=a[i];
		 }
		 return res;
	 }
	 
	 public static void toggleRow(int[][] A,int row){
		 for(int i=0;i<A[row].length;i++){
			 A[row][i] = A[row][i] ^ 1;
		 }
	 }
	 
	 public static void toggleCol(int[][] A,int col){
		 for(int i=0;i<A.length;i++){
			 A[i][col] = A[i][col] ^ 1;
		 }
	 }
	 
	 public static int shortestSubarray1(int[] A, int K) {
		if(A.length==0)return -1;
        int[] lastCsum = new int[A.length];
        for(int i=0;i<A.length;i++){
        	int[] csum = new int[A.length-i];
        	for(int j=0;j<A.length-i;j++){
        		if(A[j]<0)continue;
        		csum[j] = lastCsum[j]+A[j+i];
        		if(csum[j]>=K){
        			System.out.println(j+","+(i+1)+","+csum[j]);
        			return i+1;
        		}
        	}
        	lastCsum = csum;
        }
        return -1;
	}
	 
	 public static int shortestSubarray2(int[] A,int K){
		 int i=0;
		 int min = 50001;
		 while(i<A.length){
			 boolean step = false;
			 int[] lastCsum = new int[A.length-i+1];
			 for(int j=1;j<=A.length-i;j++){
				 lastCsum[j] = lastCsum[j-1]+A[j+i-1];
				 if(lastCsum[j]>=K){
					 min = j<min?j:min;
					 int cmin = j;
					 for(int k=1;k<=j-1;k++){
						 if(lastCsum[j]-lastCsum[k]>=K){
							 min = j-k<min?j-k:min;
							 cmin = j-k;
						 }
					 }
					 i = i+cmin+1;
					 step = true;
					 break;
				 }
				 if(lastCsum[j]<=0){
					 i = i+j;
					 step = true;
					 break;
				 }
			 }
			 if(!step)break;
		 }
		 if(min==50001)return -1;
		 return min;
	 }
	 
	 
	 public static int shortestSubarray3(int[] A, int K) {
			if(A.length==0)return -1;
			int n = 0;
			int[] nextStart = new int[A.length];
			for(int i=0;i<A.length;){
				if(A[i]<0)nextStart[n++]=i;
			}
	        int[] lastCsum = new int[A.length];
	        for(int i=0;i<A.length;i++){
	        	int[] csum = new int[A.length-i];
	        	for(int j=0;j<n;j++){
	        		if(nextStart[j]<A.length-i){
	        			csum[nextStart[j]] = lastCsum[nextStart[j]]+A[nextStart[j]+i];
	            		if(csum[nextStart[j]]>=K)return i+1;
	        		}
	        	}
	        	lastCsum = csum;
	        }
	        return -1;
		}
	 
	 public static int shortestSubarray(int[] A, int K) {
	        if(A.length==0)return -1;
			int i=0;
			int min = 50001;
			 while(i<A.length){
				 boolean step = false;
				 if(i==5273){
					@SuppressWarnings("unused")
					int a=1 ;
				 };
				 int[] lastCsum = new int[A.length-i+1];
				 int next = 1;
				 for(int j=1;j<=A.length-i;j++){
					 lastCsum[j] = lastCsum[j-1]+A[j+i-1];
					 if(lastCsum[j]<lastCsum[j])
					 if(lastCsum[j]>=K){ 
						 step = true;
						 min = j<min?j:min;i++;break;
					 }
					 if(lastCsum[j]<=0){
						 i = i+j;
						 step = true;
						 break;
					 }
				 }
				 if(!step)break;
			 }
			 if(min!=50001)return min;
			 return -1;
	    }

		
		
		public static void main(String args[]) throws IOException{
			
			//System.out.println(lemonadeChange(new int[]{5,5,10}));
			 //System.out.println(lemonadeChange(new int[]{5,5,5,10,20}));
			 //System.out.println(lemonadeChange(new int[]{5,5,10,10,20}));
			 /*System.out.println(1^1);
			 System.out.println(0^1);
			 System.out.println(0b1111 + 0b1001 + 0b1111);
			 int[][] A =new int[][]{{0,0,1,1},{1,0,1,0},{1,1,0,0}};
			 System.out.println(matrixScore(new int[][]{{0,0,1,1},{1,0,1,0},{1,1,0,0}}));
		*/
			System.out.println(shortestSubarray(new int[]{1},1));
			System.out.println(shortestSubarray(new int[]{1,3},4));
		
			BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\Mipo\\Desktop\\data.txt")));
			
			do{
				String line = br.readLine();
				int[] datas  = Arrays.stream(line.split(",")).mapToInt(new ToIntFunction<String>() {
					@Override
					public int applyAsInt(String value) {
						return Integer.valueOf(value);
					}
				}).toArray();
				Long t = System.currentTimeMillis();
				System.out.println(datas.length);
				System.out.println(shortestSubarray(datas,377684406));
				System.out.println(System.currentTimeMillis()-t);
			}while(br.read()!=-1);
			
			
			System.out.println(shortestSubarray(new int[]{2,-1,2},3));
			System.out.println(shortestSubarray(new int[]{17,85,93,-45,-21}, 150));

	 
	
		}

}
