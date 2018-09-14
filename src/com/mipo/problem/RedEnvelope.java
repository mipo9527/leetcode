package com.mipo.problem;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class RedEnvelope {
	
	private Random random = new Random();
	
	private double amount;
	private int peoples;
	private double percent;
	
	public RedEnvelope(double amount, int peoples, int percent) {
		super();
		//Assert.assertTrue(100*(1.0/peoples)<=percent);
		this.amount = amount;
		this.peoples = peoples;
		this.percent = percent/100.0;
	}
	
	private double[] probability(){
		List<Integer> res = new LinkedList<Integer>();
		for(int i=0;i<peoples-1;i++){
			res.add(random.nextInt(100));
		}
		res.add(100);
		res.add(0);
		Collections.sort(res);
		Integer[] arr = res.toArray(new Integer[res.size()]);
		double[] ret =new double[peoples];
		int k =0;
		for(int i=arr.length-2;i>=0;i--){
			ret[k++] = (arr[i+1]-arr[i])/100.0;
		}
		return ret;
	}
	
	public double[] allocate(){
		double aperc = amount*percent;
		double[] res = new double[peoples];
		double[] prob = probability();
		for(int i=0;i<peoples;i++){
			res[i] = prob[i]*aperc;
		}
		double remain =amount- Arrays.stream(res).sum();
		while(remain>0.1){
			for(int i=0;i<peoples;i++){
				if(res[i]+remain/(peoples-i)>aperc){
					remain = remain -aperc + res[i];
					res[i] = aperc;
				}else{
					res[i] += remain/(peoples-i);
					remain = remain - remain/(peoples-i);
				}
			}
		}
		for(int i=0;i<peoples;i++){
			if(res[i]>=aperc)continue;
			if(res[i]+remain-aperc<0&&res[i]+remain-aperc<0.0001){
				res[i] += remain;
				remain = 0;
			}else{
				remain = remain -aperc + res[i];
				res[i] = aperc;
			}
		}
		return res;
	}
	
	public static void check(){
		RedEnvelope re = new RedEnvelope(100,10,90);
		for(int i=0;i<1000000;i++){
			double[] ret = re.allocate();
			System.out.println(Arrays.toString(ret));
			//System.out.println(Arrays.stream(ret).sum()+":"+Arrays.stream(ret).min().getAsDouble()+":"+Arrays.stream(ret).max().getAsDouble());
			//Assert.assertTrue(Arrays.stream(ret).min().getAsDouble()>0);
			//Assert.assertTrue(re.amount-Arrays.stream(ret).sum()<0.0001);
			//Assert.assertTrue((Arrays.stream(ret).max().getAsDouble()/100.0)<=re.percent);;
		}
	}
	
	
	public static void main(String args[]){
		check();
		RedEnvelope re = new RedEnvelope(100,10,49);
		System.out.println(Arrays.stream(re.allocate()).sum());
		System.out.println(Arrays.toString(re.allocate()));
	}
	
	
}
