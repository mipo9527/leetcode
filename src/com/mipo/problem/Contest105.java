package com.mipo.problem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;
import com.mipo.problem.Wrapper.*;

public class Contest105 {
    public String reverseOnlyLetters(String S) {
        char[] reverse = new char[S.length()];
        Arrays.fill(reverse,'\\');
        for(int i=0;i<S.length();i++){
            if(!Character.isLetter(S.charAt(i))){
                reverse[i] = S.charAt(i);
            }
        }
        int k = 0;
        for(int i=S.length()-1;i>=0;){
            if(!Character.isLetter(S.charAt(i))){
                i--;
                continue;
            }
            if(reverse[k]=='\\'){
                reverse[k++]=S.charAt(i);
                i--;
            }else{
                k++;
            }
        }
        return String.valueOf(reverse);
    }

    public int maxSubarraySumCircular(int[] A) {
        int max = -30001,lastI=0,lastJ = 0;
        int[] lastSum = new int[A.length];
        int k = 0;
        for(int i=0;i<A.length;i++){
            max = Math.max(max,A[i]);
            if(A[i]<=0)continue;
            if(lastJ>i)continue;
            lastSum[i] = A[i];
            for(int j=(i+1)%A.length;j!=(A.length+i)%A.length;j=(j+1)%A.length){
                k++;
                lastSum[j]=lastSum[(j-1+A.length)%A.length]+A[j];
                if(lastSum[j]>max){
                    lastJ = j;
                    lastI = i;
                    max = lastSum[j];
                }
                if(lastSum[j]<=0)break;
            }
        }
        System.out.println(k);
        return  max;
    }

    public static class CBTInserter {
        TreeNode root;
        TreeNode[] nodes = new TreeNode[11001];
        int k = 1;

        public CBTInserter(TreeNode root) {
            this.root = root;
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while(!queue.isEmpty()){
                TreeNode cnode = queue.poll();
                nodes[k++]=cnode;
                if(cnode.left!=null)queue.add(cnode.left);
                if(cnode.right!=null)queue.add(cnode.right);
            }
        }

        public int insert(int v) {
            TreeNode node = new TreeNode(v);
            nodes[k++] = node;
            int p = (k-1)/2;
            if(nodes[p].left==null){
                nodes[p].left = node;
            }else{
                nodes[p].right = node;
            }
            return nodes[p].val;
        }

        public TreeNode get_root() {
            return nodes[1];
        }
    }


    public static  void main(String args[]){
        Contest105 test = new Contest105();
       /* System.out.println(test.reverseOnlyLetters("Test1ng-Leet=code-Q!"));
        System.out.println(test.maxSubarraySumCircular(new int[]{1,-2,3,-2}));
        System.out.println(test.maxSubarraySumCircular(new int[]{5,-3,5}));
        System.out.println(test.maxSubarraySumCircular(new int[]{3,-1,2,-1}));
        System.out.println(test.maxSubarraySumCircular(new int[]{3,-2,2,-3}));
        System.out.println(test.maxSubarraySumCircular(new int[]{-2,-3,-1}));*/
        System.out.println(test.maxSubarraySumCircular(new int[]{-5,-2,5,6,-2,-7,0,2,8}));
        TreeNode treeNode = Wrapper.stringToTreeNode("[1,2,3,4,5,6]");
        CBTInserter inserter = new CBTInserter(treeNode);
        inserter.insert(7);
        inserter.insert(8);
        inserter.get_root();
    }
}
