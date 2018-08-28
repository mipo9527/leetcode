package com.mipo.problem;

import java.util.Arrays;

import javax.management.RuntimeErrorException;

import org.junit.Test;

/**
 * LeetCode Solutions
 * @author mipo
 *
 */
public class Problems {
	public static class ListNode {
	     int val;
	     ListNode next;
	     ListNode(int x) { val = x; }
	}
	 
	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		int p = (l1.val+l2.val)/10;
		ListNode root = new ListNode((l1.val+l2.val)%10);
		ListNode f = l1;
		ListNode s = l2;
		ListNode c = root;
		while(true){
			if(f.next!=null&&s.next!=null){
				c.next = new ListNode((f.next.val+s.next.val+p)%10);
				c = c.next;
				p = (f.next.val+s.next.val+p)/10;
				f = f.next;
				s = s.next;
				continue;
			}
			if(f.next!=null){
				c.next = new ListNode((f.next.val+p)%10);
				c = c.next;
				p = (f.next.val+p)/10;
				f = f.next;
				continue;
			}else
			if(s.next!=null){
				c.next = new ListNode((s.next.val+p)%10);
				c = c.next;
				p = (s.next.val+p)/10;
				s = s.next;
				continue;
			}
			if(f.next==null&&s.next==null){
				if(p!=0)c.next = new ListNode(p);
				break;
			}
		}
		return root;
    }
	
	@Test
	public void test1(){
		ListNode l1 = new ListNode(9);
		l1.next = new ListNode(8);
		//l1.next.next = new ListNode(1);
		ListNode l2 = new ListNode(1);
		
		ListNode res = addTwoNumbers(l1,l2);
		while(res!=null){
			System.out.println(res.val);
			res= res.next;
		}
	}
	
	/**
	 * There are two sorted arrays nums1 and nums2 of size m and n respectively.

		Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
		
		Example 1:
		nums1 = [1, 3]
		nums2 = [2]
		
		The median is 2.0
		
	 * @param nums1
	 * @param nums2
	 * @return
	 */
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		boolean isOdd = (nums1.length+nums2.length)%2>0;
		int mid = (nums1.length+nums2.length+1)/2;
		if(nums2.length<nums1.length){
			int[] tmp = nums1;nums1 = nums2;nums2 = tmp;
		}
		int begin = 0 ,end = nums1.length;
		while(begin<=end){
			int split1 = (begin+end)/2;
			int split2 = mid - split1;
			if(split1<end&&nums1[split1]<nums2[split2-1]){
				begin++;
				continue;
			}
			if(split1>begin&&nums2[split2]<nums1[split1-1]){
				end--;
				continue;
			}
			int maxLeft = 0;
            if (split1 == 0) { maxLeft = nums2[split2-1]; }
            else if (split2 == 0) { maxLeft = nums1[split1-1]; }
            else { maxLeft = Math.max(nums1[split1-1], nums2[split2-1]); }
            if (isOdd) { return maxLeft; }

            int minRight = 0;
            if (split1 == nums1.length) { minRight = nums2[split2]; }
            else if (split2 == nums2.length) { minRight = nums1[split1]; }
            else { minRight = Math.min(nums2[split2], nums1[split1]); }
            return (maxLeft + minRight) / 2.0;
		}
		return 0;
    }
	
	@Test
	public void test2(){
		System.out.println(findMedianSortedArrays(new int[]{5}, new int[]{1,2,3,4,6}));
		System.out.println(findMedianSortedArrays(new int[]{2}, new int[]{1,3,4,5,6}));
		System.out.println(findMedianSortedArrays(new int[]{1}, new int[]{2,3,4,5,6}));
		System.out.println(findMedianSortedArrays(new int[]{1,3}, new int[]{2}));
		System.out.println(findMedianSortedArrays(new int[]{4}, new int[]{1,2,3,5}));
		System.out.println(findMedianSortedArrays(new int[]{1,4}, new int[]{2,3}));
		System.out.println(findMedianSortedArrays(new int[]{4}, new int[]{1,2,3}));
		System.out.println(findMedianSortedArrays(new int[]{2}, new int[]{1,3,4}));
		System.out.println(findMedianSortedArrays(new int[]{1, 3}, new int[]{2,4,5}));
		System.out.println(findMedianSortedArrays(new int[]{1, 2}, new int[]{3,4,4,4}));
	}
	
	
	public static void main(String args[]){
		ListNode l1 = new ListNode(9);
		l1.next = new ListNode(8);
		//l1.next.next = new ListNode(1);
		ListNode l2 = new ListNode(1);
		
		ListNode res = addTwoNumbers(l1,l2);
		while(res!=null){
			System.out.println(res.val);
			res= res.next;
		}
	}
	 
}

