package com.mipo.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Contest96 {

	public int projectionArea(int[][] grid) {
		int area = 0;
		int[] xzv = new int[51];
		int[] yzv = new int[51];
		for (int x = 0; x < grid.length; x++) {
			int xy = 0;
			for (int y = 0; y < grid[x].length; y++) {
				if (grid[x][y] > 0) {
					xy++;
					xzv[x] = Math.max(xzv[x], grid[x][y]);
					yzv[y] = Math.max(yzv[y], grid[x][y]);
				}
			}
			area += xy;
		}
		area += Arrays.stream(xzv).sum();
		area += Arrays.stream(yzv).sum();
		return area;
	}

	/*public int numRescueBoats(int[] people, int limit) {
		Arrays.sort(people);
		int[] mapCnt = new int[limit+1];
		for(int i=0;i<people.length;i++){
			mapCnt[people[i]]++;
		}
		
		
	}*/
	
	public String decodeAtIndex(String S, int K) {
		char[] ch = S.toCharArray();
		Object[] seq = new Object[100];
		StringBuffer sb = new StringBuffer();
		int k = 0;
		for(int i=0;i<ch.length;i++){
			if(ch[i]>='a'&&ch[i]<='z'){
				sb.append(ch[i]);
			}else{
				if(sb.length()>0){
					seq[k++] = sb.toString();
					sb = new StringBuffer();
				}
				seq[k++]=ch[i];
			}
		}
		if(sb.length()>0){
			seq[k++] = sb.toString();
		}
		long clen = 0;
		boolean forward = true;
		for(int i=0;i<k;){
			Object t = seq[i];
			if(t instanceof String){
				int tlen = ((String) t).length();
				if(forward){
					if(clen+tlen>=K){
						return ((String) t).charAt((int) (K-clen-1))+"";
					}
					clen = clen + tlen;
					i++;
				}else{
					if(clen-tlen>=K){
						clen = clen - tlen;
						i--;
					}else{
						return ((String) t).charAt((int) (K-clen+tlen-1))+"";
					}
				}
			}else{
				if(forward){
					if(clen*Integer.valueOf(t.toString())>=K){
						K = (int) (K%clen==0?clen:K%clen);
						i--;
						forward = false;
					}else{
						clen = clen*Integer.valueOf(t.toString());
						i++;
					}
				}else{
					clen = clen/Integer.valueOf(t.toString());
					K = (int) (K%clen==0?clen:K%clen);
					i--;
				}
			}
		}
		return "";
    }
	

	public static void main(String args[]) {
		Contest96 test = new Contest96();
		/*System.out.println(test.projectionArea(new int[][] { { 1, 1, 1 },
				{ 1, 0, 1 }, { 1, 1, 1 } }));
		System.out.println(test.projectionArea(new int[][] { { 2, 2, 2 },
				{ 2, 1, 2 }, { 2, 2, 2 } }));*/
		/*System.out.println(test.numRescueBoats(new int[]{1,2}, 3));
		System.out.println(test.numRescueBoats(new int[]{3,2,2,1}, 3));
		System.out.println(test.numRescueBoats(new int[]{3,5,3,4}, 5));*/
		//System.out.println(test.numRescueBoats(new int[]{5,1,4,2}, 6));
		System.out.println(test.decodeAtIndex("leet2code3", 10));
		System.out.println(test.decodeAtIndex("ha22", 5));
		System.out.println(test.decodeAtIndex("a2345678999999999999999", 1));
	}

}
