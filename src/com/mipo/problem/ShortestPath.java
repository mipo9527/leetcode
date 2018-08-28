/*package com.mipo.problem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ShortestPath {
	int[] keyHolds = new int[6];
	int keySum = 0;
	int minStepLength = Integer.MAX_VALUE;
	int cstep = 0;
	int keyLen = 0;
	char[][] cgrid = new char[31][31];
	Queue<Stage> stage = new LinkedList<>();
	
	public int shortestPathAllKeys(String[] grid) {
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
        if(minStepLength==Integer.MAX_VALUE)return -1;
        return minStepLength;
    }
	
	public class Stage{
		int x;
		int y;
		char a;
		int keySum;
		int op;
		int step;
		int[] keyHolds;
		public Stage(int x, int y, char a, int keySum, int op, int step,
				int[] keyHolds) {
			super();
			this.x = x;
			this.y = y;
			this.a = a;
			this.keySum = keySum;
			this.op = op;
			this.step = step;
			this.keyHolds = Arrays.copyOf(keyHolds, 6);
		}
		
	}
	
	public boolean DFS(int x,int y){
		if(cstep>minStepLength)return false;
		char ch = cgrid[x][y];
		if(ch=='@'){cgrid[x][y] = '.';}
		if(legal(ch)){
			if(isKey(ch)){
				stage.add(new Stage(x,y,ch,keyLen,1,cstep,keyHolds));
				cgrid[x][y] = '.';
				keyLen++;
				keyHolds[ch-'a']++;
				if(keyLen==keySum){
					minStepLength = cstep<minStepLength?cstep:minStepLength;
					return true;
				}
				
			}
			if(isLock(ch)){
				if(keyHolds[ch-'A']>0){
					stage.add(new Stage(x,y,ch,keyLen,-1,cstep,keyHolds));
					cgrid[x][y] = '.';
					keyHolds[ch-'A']--;
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
		cstep++;
		boolean[] find = new boolean[4];
		if(legal(cgrid[x][y+1]))
			find[0] = DFS(x,y+1);
		if(legal(cgrid[x+1][y]))
			find[1] = DFS(x+1,y);
		if(legal(cgrid[x-1][y]))
			find[2] = DFS(x-1,y);
		if(legal(cgrid[x][y-1]))
			find[3] = DFS(x,y-1);
		if(find[0]==false&&find[1]==false&&find[2]==false&&find[3]==false){
			
		}
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
}
*/