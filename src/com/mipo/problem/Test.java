package com.mipo.problem;

public class Test {
	
	public static String plus(String num1,String num2){
		char[] ch1 = num1.length()<=num2.length()?num1.toCharArray():num2.toCharArray();
		char[] ch2 = num1.length()>num2.length()?num1.toCharArray():num2.toCharArray();
		int[] res = new int[ch2.length+1];
		int p = 0;
		int k = 0;
		for(int index = 1;index<=ch1.length;index++){
			res[k++] = (ch1[ch1.length-index]-'0'+ch2[ch2.length-index]-'0'+p)%10;
			p = (ch1[ch1.length-index]-'0'+ch2[ch2.length-index]-'0'+p)/10;
		}
		for(;k<ch2.length;k++){
			res[k] = (ch2[k]-'0'+p)%10;
			p = (ch2[k]-'0'+p)/10;
		}
		if(p>0){
			res[k] = p;
		}else{
			k--;
		}
		String ret = "";
		for(;k>=0;k--){
			ret = ret + res[k];
		}
		return ret;
	}
	
	public static void main(String args[]){
		//System.out.println(plus("1212","999999"));
		Integer a = new Integer(1);
		Integer b = a;
		Integer c = new Integer(3);
		a = c;
		System.out.println(b);
	}

}
