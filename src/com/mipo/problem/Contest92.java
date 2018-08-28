package com.mipo.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Contest92 {
	public static int[][] transpose(int[][] A) {
        int[][] T = new int[A[0].length][A.length];
        for(int i=0;i<A.length;i++){
        	for(int j=0;j<A[i].length;j++){
        		T[j][i] = A[i][j];
        	}
        }
        return T;
    }
	
	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}
	
	int[] depth;
	TreeNode[] parent;
	TreeNode[] nodes;
	
	public TreeNode subtreeWithAllDeepest(TreeNode root) {
		depth = new int[500];
		parent = new TreeNode[500];
		nodes = new TreeNode[500];
		DFS(root,1);
		int max = Arrays.stream(depth).max().getAsInt();
		Set<TreeNode> parents = new HashSet<TreeNode>();
		for(int i=0;i<depth.length;i++){
			if(depth[i]==max)parents.add(nodes[i]);
		}
		while(parents.size()>1){
			Set<TreeNode> cparents = new HashSet<TreeNode>();
			for(TreeNode node:parents){
				cparents.add(parent[node.val]);
			}
			parents = cparents;
		}
		return parents.iterator().next();
	}
	
	public void DFS(TreeNode root,int k){
		nodes[root.val] = root;
		depth[root.val] = k;
		if(root.left!=null){
			parent[root.left.val] = root;
			DFS(root.left,k+1);
		}
		if(root.right!=null){
			parent[root.right.val] = root;
			DFS(root.right,k+1);
		}
	}
	
	int max = 100000000;
    Byte[] bytes = new Byte[2*max+1];
    
    public void getPrimeTable(){
    	long t1 = System.currentTimeMillis();
    	bytes[1] = 0;
    	bytes[2] = 1;
    	int i=2;
    	while(i<=2*max){
    		for(int j=2;j<=2*max/i;j++){
    			bytes[j*i]=0;
    		}
    		boolean found = false;
    		for(int j=i+1;j<=2*max;j++){
    			if(bytes[j]==null){
    				bytes[j] = 1;
    				i = j;
    				found = true;
    				break;
    			}
    		}
    		if(!found)break;
    	}
    	System.out.println(System.currentTimeMillis() - t1);
    }
    
	
	public int primePalindrome(int N) {
		getPrimeTable();
		for(int i=N;i<2*max;i++){
			if(bytes[i]!=null&&bytes[i]==1){
				if(isPalindrome(i))return i;
			}
		}
        return 0;
    }
	
	public void palindromes(){
		getPrimeTable();
		long t1 = System.currentTimeMillis();
		List<Integer> list = new ArrayList<>();
		for(int i=1;i<2*max+1;i++){
			if(bytes[i]!=null&&bytes[i]==1&&isPalindrome(i))list.add(i);
		}
		System.out.println(list);
		System.out.println(System.currentTimeMillis() - t1);
	}
	
	public boolean isPalindrome(int i){
		char[] str = String.valueOf(i).toCharArray();
		int s = 0;
		int e = str.length-1;
		while(s<=e){
			if(str[s++]==str[e--])continue;
			else return false;
		}
		return true;
	}
	
	
	
	int[] keyHolds = new int[6];
	int keySum = 0;
	int minLength = Integer.MAX_VALUE;
	
	public int shortestPathAllKeys(String[] grid) {
		char[][] cgrid = new char[31][31];
        for(int i=0;i<cgrid.length;i++)
        	Arrays.fill(cgrid[i],'#');
        int begin_i = 1,bengin_j = 1;
        for(int i=0;i<grid.length;i++){
        	char[] ch = grid[i].toCharArray();
        	for(int j=0;j<ch.length;j++){
        		cgrid[i+1][j+1] = ch[j];
        		if(ch[j]=='@'){
        			begin_i = i+1;
        			bengin_j = j+1;
        		}
        		if(isKey(ch[j]))keySum++;
        	}
        }
        DFS(cgrid,begin_i,bengin_j,0,0);
        if(minLength==Integer.MAX_VALUE)return -1;
        return minLength;
    }
	
	public void DFS(char[][] cgrid,int x,int y,int k,int keyLen){
		if(k>minLength)return;
		char ch = cgrid[x][y];
		if(ch=='@'){
			cgrid[x][y] = '.';
		}else{
			if(legal(ch)){
				if(isKey(ch)){
					cgrid[x][y] = '.';
					keyLen++;
					if(keyLen==keySum){
						minLength = k<minLength?k:minLength;
						return ;
					}
					keyHolds[ch-'a']++;
				}
				if(isLock(ch)){
					if(keyHolds[ch-'A']>0){
						cgrid[x][y] = '.';
						keyHolds[ch-'A']--;
					}else{
						return ;
					}
				}
			}else{
				return ;
			}
		}
		if(legal(cgrid[x][y+1]))
			DFS(cgrid,x,y+1,k+1,keyLen);
		if(legal(cgrid[x+1][y]))
			DFS(cgrid,x+1,y,k+1,keyLen);
		if(legal(cgrid[x-1][y]))
			DFS(cgrid,x-1,y,k+1,keyLen);
		if(legal(cgrid[x][y-1]))
			DFS(cgrid,x,y-1,k+1,keyLen);
	}
	
	
	public boolean isKey(char ch){
		return (ch>='a'&&ch<='f');
	}
	
	public boolean isLock(char ch){
		return (ch>='A'&&ch<='F');
	}
	
	public boolean legal(char ch){
		return ch!='#';
	}
	
	
	

	public static void main(String args[]){
		Contest92 c92 = new Contest92();
		//int[][] T = transpose(new int[][]{{1,2,3},{4,5,6},{7,8,9}});
		/*for(int i=0;i<T.length;i++)
		System.out.println(Arrays.toString(T[i]));
		TreeNode node = c92.subtreeWithAllDeepest( c92.new TreeNode(1));
		System.out.println(node);*/
		//System.out.println(c92.primePalindrome(13));
		//c92.palindromes();
		System.out.println(c92.shortestPathAllKeys(new String[]{"@..aA","..B#.","....b"}));;
		System.out.println(c92.shortestPathAllKeys(new String[]{"@..aA","..B#.","....b"}));;
	}
}
