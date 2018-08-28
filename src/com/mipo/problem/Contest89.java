package com.mipo.problem;

import java.util.Set;
import java.util.TreeSet;

public class Contest89 {
    
	public int peakIndexInMountainArray(int[] A) {
        int i=0;
        while(A[i+1]>A[i])i++;
        return i;
    }
	
	public static class Car implements Comparable<Car>{
		int position;
		int speed;
		double time;
		public Car(int position, int speed,double time) {
			super();
			this.position = position;
			this.speed = speed;
			this.time = time;
		}
		public int compareTo(Car car) {
			return this.position-car.position;
		}
		public boolean canCatchUp(Car car){
			double t = (this.position-car.position)/((car.speed-this.speed)*1.0);
			if(t>=0&&t<=car.time&&t<=this.time)return true;
			return false;
		}
		
	}
	
	public static int carFleet(int target, int[] position, int[] speed) {
		int n = position.length;
		if(n<=0)return 0;
		Set<Car> cars = new TreeSet<Car>();
		for(int i=0;i<n;i++){
			cars.add(new Car(position[i], speed[i],(target-position[i])/(speed[i]*1.0)));
		}
		Car[] carArr = cars.toArray(new Car[n]);
		int i = n-2,fleet = 1,fleatHead=n-1,fleatTail=n-1;
		while(i>=0){
			boolean canCatchUp = false;
			for(int j=fleatTail;j<=fleatHead;j++){
				if(carArr[i].canCatchUp(carArr[j])){
					fleatTail = i;
					i--;
					canCatchUp = true;
					break;
				}
			}
			if(!canCatchUp){
				fleatHead = i;
				fleatTail = i;
				fleet++;
				i--;
			}
		}
		return fleet;
    }
	
	public static class Seat implements Comparable<Seat>{
		int pos;
		int nxtdist;
		public Seat(int pos, int nxtdist) {
			super();
			this.pos = pos;
			this.nxtdist = nxtdist;
		}
		public int compareTo(Seat seat) {
			return this.pos-seat.pos;
		}
	}
	
	public static class ExamRoom {
		Set<Seat>  satin = new TreeSet<Seat>();
		int N;

	    public ExamRoom(int N) {
	    	this.N = N;
	    }
	    
	    public int seat() {
	    	if(satin.isEmpty()){
	    		satin.add(new Seat(0,N-1));
	    		return 0;
	    	}
	    	Seat fseat = satin.iterator().next();
	    	Seat cseat = null;
	    	Seat lseat = null;
	    	int predist = 0,lastdist = 0;
	    	int maxDis = 0;
	    	for(Seat seat:satin){
	    		if((seat.nxtdist+1)/2>maxDis){
	    			cseat = seat;
	    			maxDis = (cseat.nxtdist+1)/2;
	    		}
	    		lseat = seat;
	    	}
	    	if(fseat.pos!=0){
	    		predist = fseat.pos;
	    	}
	    	if(lseat.pos!=N-1){
	    		lastdist = lseat.nxtdist;
	    	}
	    	if(lastdist>predist){
	    		if(lseat.nxtdist>maxDis){
	    			lseat.nxtdist = lseat.nxtdist - 1;
	    			satin.add(new Seat(N-1, 0));
		    		return N-1;
	    		}
	    	}else{
	    		if(predist>=maxDis){
	    			satin.add(new Seat(0, fseat.pos-1));
	    			return 0;
	    		}
	    	}
	    	int pos = cseat.pos+(cseat.nxtdist+1)/2;
	    	satin.add(new Seat(pos,cseat.nxtdist-(cseat.nxtdist+1)/2));
	    	cseat.nxtdist = pos - cseat.pos - 1;
			return pos;
	    }
	    
	    public void leave(int p) {
	    	Seat lseat = null;
	    	Seat cseat = null;
	    	for(Seat seat:satin){
	    		if(seat.pos==p){
	    			cseat = seat;
	    			break;
	    		}
	    		lseat = seat;
	    	}
	    	if(lseat!=null){
	    		lseat.nxtdist = lseat.nxtdist+cseat.nxtdist+1;
	    	}
	    	satin.remove(cseat);
	    }
	}
	
	
	public static void main(String args[]){
		//System.out.println(carFleet(12, new int[]{10,8,0,5,3}, new int[]{2,4,1,1,3}));
		ExamRoom er = new ExamRoom(8);
		seat(3,er);
		er.leave(0);
		er.leave(7);
		seat(7,er);
	}
	
	public static void seat(int n,ExamRoom er){
		for(int i=0;i<n;i++){
			er.seat();
		}
	}
}
