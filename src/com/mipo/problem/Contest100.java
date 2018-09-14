package com.mipo.problem;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mipo.problem.Wrapper.TreeNode;

/**
 * @Description Contest100
 * @Author yangsz
 * @Date 2018/9/5 17:23
 * @Version 1.0
 **/
public class Contest100 {


    /**
     * 解法：
     *  中序遍历：获取左侧调整树的头尾，接上中结点与右侧调整树，用左侧树的头以及右侧树的尾作为头尾返回，依次递归
     * @param root
     * @return
     */
    public TreeNode increasingBST(TreeNode root) {
        return inorder(root).root;
    }

    public static class Pair{
        TreeNode root;
        TreeNode leaf;

        public Pair(TreeNode root, TreeNode leaf) {
            this.root = root;
            this.leaf = leaf;
        }
    }

    public Pair inorder(TreeNode treeNode){
        Pair treeLeft = null;
        if(treeNode.left!=null){
            treeLeft = inorder(treeNode.left);
        }
        Pair treeRight = null;
        if(treeNode.right!=null){
            treeRight = inorder(treeNode.right);
        }
        treeNode.left = null;
        if(treeLeft!=null){
            treeLeft.leaf.right = treeNode;
            if(treeRight!=null){
                treeNode.right = treeRight.root;
                return new Pair(treeLeft.root,treeRight.leaf);
            }
            else{
                return new Pair(treeLeft.root,treeNode);
            }
        }else{
            if(treeRight!=null){
                treeNode.right = treeRight.root;
                return new Pair(treeNode,treeRight.leaf);
            }
            return new Pair(treeNode,treeNode);
        }
    }

    /**
     * 暴力方法 brute force
     * @param A
     * @return
     */
    public int subarrayBitwiseORs1(int[] A) {
    	Set<Integer> set = new HashSet<>();
        for(int i=0;i<A.length;i++){
            int s = A[i];
            set.add(s);
            for(int j=i+1;j<A.length;j++){
                s = s|A[j];
                set.add(s);
            }
        }
        return set.size();
    }

    /**
     * 详解：首先暴力方法，时间复杂度为O(n2) 大致计算量为50000*50000 = 25亿次 在leetcode里面计算上亿次绝大多数会超时
     * 可能想到的优化方法：
     *  1、贪心算法 很大降低暴力法的时间复杂度
     *  2、动态规划算法，跟数据的维度有很大的关系，如查表法则需根据数据维度建立记录表。一般用来将指数时间复杂度降为平方时间复杂度
     *  3、树结构：如二分搜索、深度遍历、广度遍历等，可将时间复杂度降为o(nlgn)或者降为o(n)，或者为数据建立特定的数据结构降低搜索时间
     * 解法：
     *  在这里需要将历史计算利用到，比如在j位置，如果之前的运算就已经得到某个结果，则如果在这次运算中如果得到了同样的结果，则计算没必要继续下去，因为前面的运算已经包含了本次计算后续相关计算内容。
     * @param A
     * @return
     */

    public int subarrayBitwiseORs(int[] A) {
    	Set<Integer> set = new HashSet<>();
    	int[] last = new int[A.length];
        for(int i=0;i<A.length;i++){
        	int s = A[i];
            set.add(s);
        	for(int j=i+1;j<A.length;j++){
        	    if(last[j]==(s|A[j]))break;
        		s = s|A[j];
                last[j]=s;
                set.add(s);
        	}
        }
        return set.size();
    }

    /**
     * 解法：
     *  目标：
     *  2、
     * @param S
     * @param K
     * @return
     */
    public String orderlyQueue(String S, int K) {
        char[] chs = S.toCharArray();
        boolean[] solved = new boolean[K];
        int[] sortK = new int[K];
        int[] index = new int[chs.length];
        List<IndexPair> list = new ArrayList<>();
        for(int i=0;i<chs.length;i++){
            list.add(new IndexPair(chs[i],i));
        }
        Collections.sort(list);
        List<Integer> indexes = list.stream().map(x->x.index).collect(Collectors.toList());
        for(int i=0;i<K;i++){
            sortK[i]=indexes.get(i);
        }
        for(int i=0;i<K;i++){


        }
        return null;
    }

    public class IndexPair implements Comparable<IndexPair>{
        char ch;
        int index;

        public IndexPair(char ch, int index) {
            this.ch = ch;
            this.index = index;
        }

        @Override
        public int compareTo(IndexPair o) {
            return ch-o.ch;
        }
    }

    
    public static void main(String args[]){
    	Contest100 test = new Contest100();
    	/*TreeNode treeNode = Wrapper.stringToTreeNode("[5,3,6,2,4,null,8,1,null,null,null,7,9]");
        TreeNode treenode = test.increasingBST(treeNode);
        System.out.println(treenode);*/
        System.out.println(test.subarrayBitwiseORs(new int[]{1,1,2}));
        System.out.println(test.subarrayBitwiseORs(new int[]{1,2,4}));
        System.out.println(test.subarrayBitwiseORs(new int[]{13,4,2}));
    }
}
