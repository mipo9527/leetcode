package com.mipo.problem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class Contest88 {
	/**
	 * We have a string S of lowercase letters, and an integer array shifts.

Call the shift of a letter, the next letter in the alphabet, (wrapping around so that 'z' becomes 'a'). 

For example, shift('a') = 'b', shift('t') = 'u', and shift('z') = 'a'.

Now for each shifts[i] = x, we want to shift the first i+1 letters of S, x times.

Return the final string after all such shifts to S are applied.
	 * @param S
	 * @param shifts
	 * @return
	 */
	public static String shiftingLetters(String S, int[] shifts) {
		char[] sarr = S.toCharArray();
		char[] dict = {'a','b','c','d','e','f','g','h','i','j',
			    'k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		for(int i=shifts.length-1;i>0;i--){
			shifts[i-1] = (shifts[i-1] + shifts[i])%26;
		}
		for(int i=0;i<shifts.length;i++){
			sarr[i] = dict[(sarr[i]-'a' + shifts[i]%26)%26];
		}
        return new String(sarr);
    }
	/**
	 * In a row of seats, 1 represents a person sitting in that seat, and 0 represents that the seat is empty. 

		There is at least one empty seat, and at least one person sitting.
		
		Alex wants to sit in the seat such that the distance between him and the closest person to him is maximized. 
		
		Return that maximum distance to closest person.
	 * @param seats
	 * @return
	 */
	 public static int maxDistToClosest(int[] seats) {
		int maxZeros = 1;
		int i=0;
		int curZeros = 0;
		while(i<seats.length){
			boolean beginPerson = false;
			boolean endPerson = false;
			curZeros = 0;
			while(i<seats.length&&seats[i]==1){
				i++;
				beginPerson = true;
			}
			while(i<seats.length&&seats[i]==0){
				curZeros++;
				i++;
			}
			if(i<seats.length&&seats[i]==1){
				endPerson=true;
			}
			if(beginPerson&&endPerson){
				maxZeros = (curZeros+1)/2>maxZeros?(curZeros+1)/2:maxZeros;
			}else{
				maxZeros = curZeros>maxZeros?curZeros:maxZeros;
			}
		}
		return maxZeros;
	}
	 /**
	  * In a group of N people (labelled 0, 1, 2, ..., N-1), each person has different amounts of money, and different levels of quietness.

For convenience, we'll call the person with label x, simply "person x".

We'll say that richer[i] = [x, y] if person x definitely has more money than person y.  Note that richer may only be a subset of valid observations.

Also, we'll say quiet[x] = q if person x has quietness q.

Now, return answer, where answer[x] = y if y is the least quiet person (that is, the person y with the smallest value of quiet[y]), among all people who definitely have equal to or more money than person x.
	  * @param richer
	  * @param quiet
	  * @return
	  */	
	 public static class Person implements Comparable<Person>{
		int label;
		int quiet;
		Set<Person> richerList =  new TreeSet<Person>();
		
		public Person(int label,int quiet) {
			super();
			this.label = label;
			this.quiet = quiet;
		}

		public int compareTo(Person arg0) {
			return quiet-arg0.quiet;
		}
		
		public int answer(){
			if(richerList.isEmpty()){
				return label;
			}else{
				return richerList.iterator().next().label;
			}
		}

		/*@Override
		public String toString() {
			return "[label=" + label + "]";
		}*/
		
	 }
	 
	 public static int[] loudAndRich(int[][] richer, int[] quiet) {
		Person[] persons = new Person[quiet.length];
		for(int i=0;i<quiet.length;i++){
			persons[i] = new Person(i,quiet[i]);
		}
		for(int j=0;j<richer.length;j++){
			persons[richer[j][1]].richerList.add(persons[richer[j][0]]);
		}
		for(int i=0;i<quiet.length;i++){
			Queue<Person> queue = new LinkedList<Person>(persons[i].richerList);
			while(!queue.isEmpty()){
				Person person = queue.poll();
				persons[i].richerList.addAll(person.richerList);
				queue.addAll(person.richerList);
			}
		}
		
		for(int i=0;i<quiet.length;i++){
			persons[i].richerList.add(persons[i]);
		}
		
		int[] answer = new int[quiet.length];
		for(int i=0;i<quiet.length;i++){
			answer[i]=persons[i].answer();
		}
		return answer;
	 }
	 
	/* public static int[] loudAndRich(int[][] richer, int[] quiet) {
	        int n = quiet.length;
	        int[] ans = new int[n];
	        for(int i=0;i<n;i++){
	            ans[i] = i;
	        }
	        
	        int[] in = new int[n];
	        for(int i=0;i<richer.length;i++){
	            in[richer[i][1]]++;
	        }
	        
	        boolean[] visited = new boolean[n];
	        while(true){
	            boolean find = false;
	            for(int i=0; i< n;i++){
	                if(!visited[i] && in[i]==0){
	                    for(int j=0;j<richer.length;j++){
	                        if(richer[j][0] == i){
	                            int next = richer[j][1];
	                            in[next]--;
	                            if(quiet[ans[next]] > quiet[ans[i]] || quiet[ans[next]] == quiet[ans[i]] && ans[next] > ans[i]){
	                                ans[next] = ans[i];
	                            }
	                        }
	                    }
	                    visited[i] = true;
	                    find = true;
	                }
	            }
	            if(!find) break;
	        }
	        return ans;
	    }*/
	public static void main(String args[]){
		//System.out.println(maxDistToClosest(new int[]{1,0,0,0,1,0,1}));
		System.out.println(Arrays.toString(loudAndRich(new int[][]{{1,0},{2,1},{3,1},{3,7},{4,3},{5,3},{6,3}},new int[]{3,2,5,4,6,1,7,0})));
	}
	
}
