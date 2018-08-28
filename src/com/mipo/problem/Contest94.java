package com.mipo.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Contest94 {
	public static int binaryGap(int N) {
        char[] ch = Integer.toBinaryString(N).toCharArray();
        int len = ch.length;
        int max = 0;
        int i = 0;
        boolean foundTowOne = false;
        while(i<len){
        	while(i+1<len&&ch[i+1]=='1'){
        		i++;
        		foundTowOne = true;
        	}
        	if(i==len-1)break;
        	int cnt = 1;
        	while(i+1<len&&ch[i+1]=='0'){
        		i++;
        		cnt++;
        	}
        	if(i==len-1)break;
        	i++;
        	max = max<cnt?cnt:max;
        }
        if(foundTowOne)max = max<1?1:max;
        return max;
    }
	
	public static boolean reorderedPowerOf2(int N) {
		char[] nums = String.valueOf(N).toCharArray();
		Arrays.sort(nums);
		String num = String.valueOf(nums);
		List<String> p2 = new ArrayList<>();
		for(int i=0;i<=31;i++){
			char[] ch = String.valueOf((int)Math.pow(2, i)).toCharArray();
			Arrays.sort(ch);
			p2.add(String.valueOf(ch));
		}
        for(String str:p2){
        	if(num.equals(str))return true;
        }
        return false;
    }
	
	class IndVal implements Comparable<IndVal>{
		int ind;
		int val;
		public IndVal(int ind, int val) {
			super();
			this.ind = ind;
			this.val = val;
		}
		

		@Override
		public int compareTo(IndVal o) {
			return val-o.val;
		}

		@Override
		public String toString() {
			return "IndVal [ind=" + ind + ", val=" + val + "]";
		}
		
	}
	
	public int[] advantageCount(int[] A, int[] B) {
		int n = A.length;
		int[] res = new int[n];
		Arrays.fill(res,-1);
		List<IndVal> cand = new ArrayList<IndVal>();
		for(int i=0;i<n;i++){
			cand.add(new IndVal(i,A[i]));
		}
		Collections.sort(cand);
		for(int i=0;i<n;i++){
			for(IndVal indVal:cand){
				if(indVal.val>B[i]){
					res[i]=A[indVal.ind];
					cand.remove(indVal);
					break;
				}
			}
		}
		for(int i=0;i<res.length;i++){
			if(res[i]==-1){
				IndVal indVal = cand.iterator().next();
				res[i]=indVal.val;
				cand.remove(indVal);
			}
		}
		return res;
	}
	
	
	
	public static void main(String args[]){
		Contest94 c94 = new Contest94();
		/*System.out.println(binaryGap(8));
		System.out.println(binaryGap(1));
		System.out.println(binaryGap(22));
		System.out.println(binaryGap(6));*/
		/*System.out.println(reorderedPowerOf2(1));
		System.out.println(reorderedPowerOf2(24));
		System.out.println(reorderedPowerOf2(46));*/
		System.out.println(Arrays.toString(c94.advantageCount(new int[]{2,0,4,1,2},new int[]{1,3,0,0,2})));
		System.out.println(Arrays.toString(c94.advantageCount(new int[]{12,24,8,32},new int[]{13,25,32,11})));
	}
}
