package com.mipo.problem;

import com.mipo.problem.Problems.ListNode;
import com.mipo.problem.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class LeetcodeProblems {

    public List<String> letterCombinations(String digits) {
        permutations(0,digits,"");
        return res;
    }

    List<String> res = new ArrayList<>();
    void permutations(int i,String digits,String sb){
        if(i>=digits.length()){
            res.add(sb);
            return ;
        }
        char[] chars = getCands(digits.charAt(i)-'0');
        for(char ch:chars){
            permutations(i+1,digits,sb+ch);
        }
    }

    private char[] getCands(int i){
        switch (i){
            case 9:
                return new char[]{'w','x','y','z',};
            default:
                return new char[]{(char)((i-2)*3+'a'),(char)((i-2)*3+'b'),(char)((i-2)*3+'c')};
        }
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0;i<nums.length;i++){
            for(int j=i+1;j<nums.length;j++){
                int l = j+2,r = nums.length-1;
                while(l<r){
                    int sum = nums[i]+nums[j]+nums[l]+nums[r];
                    if(sum==target){
                        res.add(Arrays.asList(nums[i],nums[j],nums[l],nums[r]));
                    }else{
                        if(sum<target){
                            l++;
                        }else r--;
                    }
                }
            }
        }
        return res;
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for(char ch:s.toCharArray()){
            Character top = stack.isEmpty()?null:stack.peek();
            if(top!=null){
                boolean needPop = false;
                switch (ch){
                    case '}':
                        needPop =top.equals("{");
                        break;
                    case ']':
                        needPop =top.equals("[");
                        break;
                    case ')':
                        needPop =top.equals("(");
                        break;
                }
                if(needPop){stack.pop();continue;}
            }
            stack.push(ch);
        }
        return stack.isEmpty();
    }
    public List<String> generateParenthesis(int n) {
        pos(0,0,n,"");
        return ans;
    }

    List<String> ans = new ArrayList<>();

    void pos(int i,int j,int n,String res){
        if(i==n&&j==n)ans.add(res);
        if(i<n){
            pos(i+1,j,n,res+"(");
        }
        if(j<i){
            pos(i,j+1,n,res+")");
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        List<ListNode> nodes = Arrays.stream(lists).collect(Collectors.toList());
        ListNode head = null,cur = null;
        if(nodes.size()==0)return null;
        int min = Integer.MAX_VALUE;
        for(ListNode node:nodes){
            if(node.val<min){
                min = node.val;
                cur = head = node;
            }
        }
        cur = cur.next;
        while(nodes.size()>1){
            min = Integer.MAX_VALUE;
            ListNode minNode = null;
            for(ListNode node:nodes){
                if(node.val<min){
                    min = node.val;
                    minNode = node;
                }
            }
            cur.next = minNode;
        }
        return head;
    }

    public int minIncrementForUnique(int[] A) {
        int cnt=0;
        int[] rec = new int[80001];
        for(int i=0;i<A.length;i++){
            int len = work(A[i],rec);
            rec[A[i]]=len+1;
            rec[A[i]+len] = 1;
            cnt+=len;
        }
        return cnt;
    }

    public int work(int k,int[] rec){
        if(rec[k+rec[k]]==0){
            return rec[k];
        }
        int len = work(k+rec[k],rec);
        rec[k+rec[k]]=len+1;
        rec[k+rec[k]+len] = 1;
        return rec[k]+len;
    }

    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int j = 0;
        for(int i=0;i<pushed.length;i++){
            stack.push(pushed[i]);
            while(!stack.isEmpty()&&stack.peek()==popped[j]){
                j++;
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    public int removeStones(int[][] stones) {
        Map<Integer,Integer> rowMap = new HashMap<>();
        Map<Integer,Integer> colMap = new HashMap<>();
        Set<Integer> index = new HashSet<>();
        for(int i=0;i<stones.length;i++){
            rowMap.put(stones[i][0],rowMap.get(stones[i][0])==null?1:rowMap.get(stones[i][0])+1);
            colMap.put(stones[i][1],colMap.get(stones[i][1])==null?1:colMap.get(stones[i][1])+1);
            index.add(i);
        }
        int total = 0;
        while(true){
            int min = Integer.MAX_VALUE;
            int max = 0;
            int minIndex = -1;
            for(Integer i:index){
                max = Math.max(rowMap.get(stones[i][0])+colMap.get(stones[i][1]),max);
                if(min>rowMap.get(stones[i][0])+colMap.get(stones[i][1])){
                    min = rowMap.get(stones[i][0])+colMap.get(stones[i][1]);
                    minIndex=i;
                }
            }
            if(max==2)
                return total;
            else{
                if(min>2)total++;
                if(min==2)
                System.out.println(minIndex);
                index.remove(minIndex);
                rowMap.put(stones[minIndex][0],rowMap.get(stones[minIndex][0])-1);
                colMap.put(stones[minIndex][1],colMap.get(stones[minIndex][1])-1);
            }
        }
    }

    public static void main(String[] args) {
        LeetcodeProblems problems = new LeetcodeProblems();
       // System.out.println((problems.letterCombinations("23")));
        //System.out.println(problems.fourSum(new int[]{1, 0, -1, 0, -2, 2},0));
        //System.out.println(problems.generateParenthesis(3));
        //System.out.println(problems.minIncrementForUnique(new int[]{1,1,2,0}));
        /*System.out.println(problems.removeStones(Utils.to2DArray("[[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]")));
        System.out.println(problems.removeStones(Utils.to2DArray("[[0,0],[0,2],[1,1],[2,0],[2,2]]")));*/
        System.out.println(problems.removeStones(Utils.to2DArray("[[5,9],[9,0],[0,0],[7,0],[4,3],[8,5],[5,8],[1,1],[0,6],[7,5],[1,6],[1,9],[9,4],[2,8],[1,3],[4,2],[2,5],[4,1],[0,2],[6,5]]")));
    }

}
