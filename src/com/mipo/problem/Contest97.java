package com.mipo.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.gson.Gson;

public class Contest97 {
	
	public int reachableNodes(int[][] edges, int M, int N) {
		Set<Integer> found = new HashSet<>();
		Set<Integer> cand = new HashSet<>();
		cand.addAll(IntStream.range(1, N).boxed().collect(Collectors.toList()));
		int[][] adj = new int[N][N];
		int[] path = new int[N];
		for(int i=0;i<N;i++)
			Arrays.fill(adj[i], -1);
		for(int[] edge:edges){
			adj[edge[0]][edge[1]] = adj[edge[1]][edge[0]] = edge[2]+1;
		}
		int[] dist = new int[N];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[0] = 0;
		found.add(0);
		while(found.size()!=N){
			int min = Integer.MAX_VALUE;
			int k = 0;
			for(Integer i:found){
				for(Integer j:cand){
					if(adj[i][j]!=-1){
						if(adj[i][j]+ dist[i]<dist[j]){
							dist[j] = adj[i][j]+dist[i];
							path[j] = i;
						}
						if(dist[j]<min){
							min = dist[j];
							k = j;
						}
					}
				}
			}
			found.add(k);
			cand.remove(k);
		}
		return 0;
    }
	
	 public String[] uncommonFromSentences(String A, String B) {
		 StringTokenizer tokenizer = new StringTokenizer(A+" "+B);
		 Map<String,Integer> cnt = new HashMap<>();
		 while(tokenizer.hasMoreTokens()) {
            String s = tokenizer.nextToken();
            cnt.put(s, Optional.ofNullable(cnt.get(s)).orElse(0)+1);
        }
		List<String> a = new ArrayList<String>();
		for(String key:cnt.keySet()){
			if(cnt.get(key)<=1)
				a.add(key);
		}
	    return a.toArray(new String[a.size()]);
    }
	
	public int[][] spiralMatrixIII(int R, int C, int r0, int c0) {
	   int[][] res = new int[R*C][2];
	   int k = 1, x =r0,y=c0,cnt = 0,d = 0;
	   res[cnt++] = new int[]{r0,c0};
	   int[][] direct = {{0,1},{1,0},{0,-1},{-1,0}};
	   while(cnt<R*C){
		   int i=0;
		   while(i<8*k&&cnt<R*C){
			   int nx = x + direct[d][0];
			   int ny = y + direct[d][1];
			   if(nx<=r0+k&&nx>=r0-k&&ny<=c0+k&&ny>=c0-k){
				   x = nx;
				   y = ny;
				   if(nx<R&&nx>=0&&ny<C&&ny>=0)
					   res[cnt++] = new int[]{x,y};
				   i++;
			   }else{
				   d = (d+1)%4;
			   }
		   }
		   k++;
	   }
	   return res;
	}
	
	int[][] adjs;
	int[] label;
	int N;
	Map<Integer,List<Integer>> adj;
	
	public boolean possibleBipartition(int N, int[][] dislikes) {
        adjs = new int[N+1][N+1];
        adj = new HashMap<Integer,List<Integer>>();
        for(int[] d:dislikes){
        	adjs[d[0]][d[1]]=1;
        	List<Integer> list = adj.get(d[0]);
        	if(list==null){
        		list = new ArrayList<Integer>();
        		adj.put(d[0],list);
        	}
        	list.add(d[1]);
        }
        this.N = N;
        label = new int[N+1];
        label[1] = 1;
        return DFS(1,1);
    }
	int sum = 0;
	boolean BFS(int p,int k){
		Queue<Integer> queue = new LinkedList<>();
		List<Integer> list = adj.get(p);
		if(list!=null)
		for(int i:list){
			sum++;
			if(adjs[p][i]==1){
				if(label[i]!=0&&label[i]%2==(k+1)%2){
					queue.offer(i);
				}else if(label[i]==0){
					label[i] = k+1;
					queue.offer(i);
				} else {
					return false;
				}
			}
		}
		boolean res = true;
		for(Integer np:queue){
			res = res&BFS(np,k+1);
		}
		return res;
	}
	
	boolean DFS(int p,int k){
		List<Integer> list = adj.get(p);
		boolean res = true;
		if(list!=null)
		for(int i:list){
			if(adjs[p][i]==1){
				if(label[i]!=0&&label[i]%2==(k+1)%2){
				}else if(label[i]==0){
					label[i] = k+1;
					res = res&DFS(i,k+1);
				} else {
					return false;
				}
			}
		}
		return res;
	}
	
	int max = 0;
	
	public int superEggDrop(int K, int N) {
		max = 0;
		binsearch(1,N,K,0);
		return max;
	}
	
	public void binsearch(int left,int right,int remain,int depth){
		if(remain==0)return;
		if(left==right){
			max = Math.max(depth+1, max);
			return ;
		}
		if(remain==1){
			max = Math.max(depth+right-left+1, max);
			return;
		}
		int middle = (left+right)/2;
		if(middle-1>=left){
			binsearch(left,middle-1,remain-1,depth+1);
		}
		if(middle+1<=right){
			binsearch(middle+1,right,remain,depth+1);
		}
	}
	 
	public static void main(String args[]){
		Contest97 test = new Contest97();
		/*System.out.println(test.reachableNodes(new int[][]{ {0,1,10},{0,2,1},{1,2,2}}, 6, 3));
		System.out.println(test.reachableNodes(new int[][]{ {0,1,4},{1,2,6},{0,2,8},{1,3,1}}, 10, 4));
		System.out.println(test.reachableNodes(new int[][]{ {1,2,4},{1,4,5},{1,3,1},{2,3,4},{3,4,5}}, 10, 4));
		System.out.println(test.reachableNodes(new int[][]{{0,4,4},{0,3,3},{0,2,5},{3,4,3},{2,4,3}}, 16, 5));
		System.out.println(test.reachableNodes(new int[][]{{0,2,3},{0,4,4},{2,3,8},{1,3,5},{0,3,9},{3,4,6},{0,1,5},{2,4,6},{1,2,3},{1,4,1}},8,5));
		System.out.println(test.reachableNodes(new int[][]{{1,3,23},{3,5,19},{3,6,17},{1,5,14},{6,7,20},{1,4,10},{1,6,0},{3,4,20},{1,7,4},{0,4,10},{0,7,9},{2,3,3},{3,7,9},{5,7,4},{4,5,16},{0,1,16},{2,6,0},{4,7,11},{2,5,14},{5,6,22},{4,6,12},{0,6,2},{0,2,1},{2,4,22},{2,7,20}}
		,19,8));*/
		/*System.out.println(test.reachableNodes(new int[][]{{4,21,114},{18,25,139},{3,22,244},{22,26,193},{18,22,98},{1,24,6},{17,18,42},{8,25,151},{5,28,265},{2,22,138},{9,20,126},{0,8,152},{22,28,39},{8,27,241},{11,29,147},{6,23,22},{24,26,274},{21,27,20},{15,18,8},{1,19,0},{0,25,164},{1,22,97},{15,21,19},{13,16,13},{18,28,141},{14,20,21},{14,26,60},{10,13,223},{11,20,93},{5,8,8},{11,14,288},{7,28,280},{5,23,191},{17,19,228},{12,17,278},{7,16,103},{9,17,188},{24,29,293},{20,29,18},{13,25,259},{19,22,136},{21,26,276},{6,21,113},{23,25,12},{18,27,155},{24,25,279},{7,24,165},{22,23,72},{2,8,204},{5,6,166},{16,19,166},{3,9,71},{19,28,66},{9,12,3},{5,16,291},{20,26,226},{16,21,271},{4,15,136},{16,27,71},{9,21,142},{11,23,293},{8,22,262},{25,27,219},{13,27,204},{16,23,129},{2,6,172},{24,27,228},{5,25,72},{17,24,20},{2,25,221},{19,23,145},{16,20,199},{14,21,86},{23,24,213},{17,20,260},{18,29,181},{6,14,1},{6,9,245},{8,19,67},{16,26,140},{9,25,26},{26,28,119},{10,12,268},{9,23,149},{21,25,214},{2,28,135},{10,17,149},{14,24,82},{15,26,203},{6,28,60},{2,24,272},{6,19,253},{0,27,76},{3,28,154},{5,18,287},{3,12,256},{18,21,31},{23,26,122},{8,17,113},{14,28,264},{7,23,289},{12,28,232},{0,17,193},{9,14,79},{8,20,79},{6,8,134},{6,15,123},{3,11,212},{0,1,125},{4,9,266},{2,7,30},{18,20,44},{14,25,229},{0,2,265},{14,23,159},{5,10,167},{3,25,174},{10,23,243},{4,11,253},{12,26,95},{11,16,120},{7,9,218},{15,24,208},{14,22,158},{7,10,90},{3,17,209},{2,12,232},{10,20,204},{1,29,275},{26,27,9},{6,7,74},{7,22,60},{15,17,62},{19,24,12},{23,28,194},{19,21,176},{6,18,55},{5,9,165},{20,28,84},{8,23,240},{16,17,208},{4,28,148},{13,23,207},{13,15,265},{14,17,181},{12,15,108},{6,17,53},{6,12,144},{1,3,2},{2,10,80},{5,20,158},{19,20,164},{13,20,53},{10,16,118},{25,26,142},{1,7,255},{9,29,61},{6,13,110},{18,26,276},{27,28,109},{15,25,164},{17,27,156},{21,29,275},{10,18,284},{12,21,85},{16,28,181},{0,11,222},{0,9,14},{5,29,226},{1,18,117},{12,27,195},{14,18,118},{12,14,57},{17,28,197},{9,22,17},{4,8,171},{0,10,158},{10,29,6},{25,29,202},{14,16,149},{9,16,74},{15,23,161},{19,27,196},{6,22,186},{20,25,213},{3,10,66},{3,27,275},{15,29,149},{16,25,83},{4,26,179},{14,19,26},{20,23,5},{10,26,76},{20,24,255},{7,29,31},{2,23,160},{17,21,224},{13,22,173},{13,19,69},{3,18,147},{7,21,124},{12,29,35},{26,29,106},{10,21,298},{9,24,14},{9,10,46},{18,23,256},{3,16,257},{23,29,32},{17,26,254},{13,28,260},{14,27,145},{0,7,52},{9,11,149},{21,24,7},{15,28,231},{3,23,58},{10,28,99},{3,4,177},{5,26,196},{0,6,71},{13,14,115},{12,25,177},{3,14,80},{16,24,258},{7,19,157},{13,26,195},{8,12,257},{6,16,24},{5,17,249},{0,18,79},{20,21,62},{5,7,205},{0,5,129},{11,12,225},{15,22,27},{5,22,188},{2,21,144},{25,28,223},{9,15,7},{18,24,21},{1,20,196},{10,22,299},{4,14,33},{8,26,75},{19,25,15},{4,20,245},{0,13,32},{22,29,215},{23,27,113},{1,23,160},{1,6,112},{2,4,62},{8,18,255},{24,28,20},{11,22,113},{2,11,236},{21,28,151},{2,20,156},{3,29,33},{22,24,63},{4,6,220},{0,12,94},{22,27,222},{11,13,180},{7,15,209},{21,22,90},{11,27,125},{4,10,256},{5,14,57},{28,29,22},{12,24,241},{7,26,259},{12,23,53},{2,17,245},{2,27,69},{6,24,238},{8,10,207},{9,26,139},{15,19,118},{9,28,261},{7,8,64},{4,24,176},{14,29,56},{4,25,280},{14,15,30},{5,13,282},{17,25,269},{8,11,291},{4,29,217},{5,15,284},{11,21,4},{11,15,109},{9,13,158},{8,9,170},{11,18,8},{5,24,261},{12,20,41},{16,29,41},{27,29,22}}
		,172,30));*/
		//System.out.println(Arrays.toString(test.uncommonFromSentences("this apple is sweet", "this apple is sour")));
		//System.out.println(gson.toJson(test.spiralMatrixIII(1,4,0,0)));
		//System.out.println(gson.toJson(test.spiralMatrixIII(5,6,1,4)));
		//System.out.println(test.possibleBipartition(4,Utils.to2DArray("[[1,2],[1,3],[2,4]]")));
		/*System.out.println(test.possibleBipartition(5,Utils.to2DArray("[[1,2],[2,3],[3,4],[4,5],[1,5]]")));
		System.out.println(test.possibleBipartition(3,Utils.to2DArray("[[1,2],[1,3],[2,3]]")));
		System.out.println(test.possibleBipartition(4,Utils.to2DArray(" [[1,2],[1,3],[2,4]]")));*/
		/*System.out.println(test.superEggDrop(2, 1));
		System.out.println(test.superEggDrop(1, 2));
		System.out.println(test.superEggDrop(2, 6));
		System.out.println(test.superEggDrop(3, 14));*/
		System.out.println(test.superEggDrop(2, 9));
	}
	static Gson gson = new Gson();
	
	
}
