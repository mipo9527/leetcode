package com.mipo.problem;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode Contest87
 * @author mipo
 *
 */
public class Contest87 {
	
	public static class Edge{
		private int v1;
		private int v2;
		public Edge(int v1, int v2) {
			super();
			this.v1 = Math.min(v1, v2);
			this.v2 = Math.max(v1, v2);
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + v1;
			result = prime * result + v2;
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Edge other = (Edge) obj;
			if((v1==other.v1&&v2==other.v2)||(v1==other.v2&&v2==other.v1))
				return true;
			return false;
		}

		@Override
		public String toString() {
			return "(" + v1 + ", " + v2 + ")";
		}
		
	}
	
	public static int shortestPathLength(int[][] graph) {
		HashSet<Edge> edges = new LinkedHashSet<Edge>();
		int index = 0;
		for(int i=0;i<graph.length;i++){
			for(int j=0;j<graph[i].length;j++){
				edges.add(new Edge(i,graph[i][j]));
			}
		}
		
		return 0;
    }
	

	public static void main(String args[]){
		shortestPathLength(new int[][]{{1,2,3},{0},{0},{0}});
		shortestPathLength(new int[][]{{1},{0,2,4},{1,3,4},{2},{1,2}});
		
	}

}
