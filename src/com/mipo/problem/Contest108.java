package com.mipo.problem;

import com.mipo.problem.utils.Utils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import com.mipo.problem.Wrapper.TreeNode;

public class Contest108 {
    public int numSubarraysWithSum(int[] A, int S) {
        int[] sum = new int[A.length+1];
        int[] index = new int[30001];
        for(int i=0;i<A.length;i++){
            sum[i+1]=sum[i]+A[i];
            index[sum[i+1]]=i+1;
        }
        int total = 0;
        for(int i=A.length;i>=0;i--){
            if(sum[i]-S<0)break;
            for(int j=Math.min(index[sum[i]-S]+1,i);j>0;j--){
                if(sum[j]-A[j-1]==sum[i]-S)total++;
            }
        }
        return total;
    }

    public int minFallingPathSum(int[][] A) {
        int[][] rec = new int[A.length][A.length];
        rec[0] = A[0];
        for(int i=1;i<A.length;i++){
            for(int j=0;j<A.length;j++){
                rec[i][j] = Math.min(Math.min(j-1>=0?rec[i-1][j-1]:10001,
                        rec[i-1][j]),j+1<A.length?rec[i-1][j+1]:10001)+A[i][j];
            }
        }
        return Arrays.stream(rec[A.length-1]).min().getAsInt();
    }

    public int[] beautifulArray(int N) {
        return null;
    }

    public int knightDialer(int N) {
        long[][] rec = new long[N][10];
        rec[0] = new long[]{1,1,1,1,1,1,1,1,1,1};
        for(int i=1;i<N;i++){
            rec[i][0] = (rec[i-1][4]+rec[i-1][6])%1000000007l;
            rec[i][1] = (rec[i-1][6]+rec[i-1][8])%1000000007l;
            rec[i][2] = (rec[i-1][9]+rec[i-1][7])%1000000007l;
            rec[i][3] = (rec[i-1][8]+rec[i-1][4])%1000000007l;
            rec[i][4] = (rec[i-1][3]+rec[i-1][9]+rec[i-1][0])%1000000007l;
            rec[i][6] = (rec[i-1][1]+rec[i-1][7]+rec[i-1][0])%1000000007l;
            rec[i][7] = (rec[i-1][2]+rec[i-1][6])%1000000007l;
            rec[i][8] = (rec[i-1][1]+rec[i-1][3])%1000000007l;
            rec[i][9] = (rec[i-1][2]+rec[i-1][4])%1000000007l;
        }
        long sum = 0;
        for(int i=0;i<10;i++){
            sum= (sum + rec[N-1][i])%1000000007l;
        }
        return (int)sum;
    }

    boolean[][] visited ;
    int[][] direct = new int[][]{{-1,0},{0,1},{1,0},{0,-1}};

    static class Point{
        int col;
        int row;

        public Point(int row, int col) {
            this.col = col;
            this.row = row;
        }
    }


    public int shortestBridge(int[][] A) {
        visited = new boolean[A.length][A.length];
        List<Point>[] islands = new List[2];
        islands[0] = new ArrayList<>();
        islands[1] = new ArrayList<>();
        int k=0;
        for(int i=0;i<A.length;i++){
            for(int j=0;j<A.length;j++){
                if(k==2)break;
                if(A[i][j]==1&&!visited[i][j]){
                    DFS(A,islands[k++],i,j);
                }else{
                    visited[i][j]=true;
                }
            }
        }
        int min = Integer.MAX_VALUE;
        for(Point p1:islands[0]){
            for(Point p2:islands[1]){
                min = Math.min(Math.abs(p1.row-p2.row)+Math.abs(p1.col-p2.col)-1,min);
            }
        }
        return min;
    }

    public void DFS(int[][] A, List<Point> island, int i, int j){
        island.add(new Point(i,j));
        visited[i][j]=true;
        for(int[] d:direct){
            if(i+d[0]<A.length&&i+d[0]>=0&&j+d[1]<A.length&&j+d[1]>=0){
                if(!visited[i+d[0]][j+d[1]]&&A[i+d[0]][j+d[1]]==1){
                    DFS(A,island,i+d[0],j+d[1]);
                }
            }
        }
    }
    Map<String,Integer> dict = new HashMap<>();
    public int romanToInt(String s) {
        dict.put("I",1);
        dict.put("V",5);
        dict.put("X",10);
        dict.put("L",50);
        dict.put("C",100);
        dict.put("D",500);
        dict.put("M",1000);
        dict.put("IV",4);
        dict.put("IX",9);
        dict.put("XL",40);
        dict.put("XC",90);
        dict.put("CD",400);
        dict.put("CM",900);
        int res = 0;
        Integer tmp;
        int i=0;
        char[] chars = s.toCharArray();
        while(i<chars.length){
            if(i+1<chars.length){
                res += ((tmp=dict.get(chars[i]+""+chars[i+1]))==null?dict.get(chars[i]+""):tmp);
                if(tmp!=null)i=i+2;
                else i++;
            }else{
                res+=dict.get(chars[i]+"");
                i++;
            }
        }
        return res;
    }

    public int lengthOfLongestSubstring(String s) {
        int max = 0;
        List<Character> set = new ArrayList<>();
        for(char ch:s.toCharArray()){
            if(set.contains(ch)){
                set = set.subList(set.indexOf(ch),set.size());
                max = Math.max(max,set.size());
            }
            set.add(ch);
        }
        return Math.max(max,set.size());
    }

    public String longestPalindrome(String s) {
        char[] chs = s.toCharArray();
        int n = chs.length;
        return null;
    }

    public String convert(String s, int numRows) {
        if(numRows==1||numRows>s.length())return s;
        StringBuffer sb = new StringBuffer();
        int n=s.length(),k = n/(2*(numRows-1)),pos1,pos2;
        for(int j=0;j<numRows;j++){
            for(int i=0;i<=k;i++){
                pos1 = i*2*(numRows-1)+j;
                // 插入Z字两端的字母
                if(pos1<n)sb.append(s.charAt(pos1));
                // 插入Z字中间的字母
                if(j!=0&&j!=numRows-1){
                    pos2 = (i+1)*2*(numRows-1)-j;
                    if(pos2<n)sb.append(s.charAt(pos2));
                }
            }
        }
        return sb.toString();
    }

    public boolean isMatch(String s, String p) {
        Pattern pattern = Pattern.compile(p);
        return pattern.matcher(s).matches();
    }

    static Map<Integer,String> dict1 = new HashMap<>();
    static{
        dict1.put(1,"I");
        dict1.put(5,"V");
        dict1.put(10,"X");
        dict1.put(50,"L");
        dict1.put(100,"C");
        dict1.put(500,"D");
        dict1.put(1000,"M");
        dict1.put(4,"IV");
        dict1.put(9,"IX");
        dict1.put(40,"XL");
        dict1.put(90,"XC");
        dict1.put(400,"CD");
        dict1.put(900,"CM");
    }
    public String intToRoman(int num) {
        StringBuffer sb = new StringBuffer();
        for(int i=num/1000;i>0;i--) {
            sb.append("M");
        }
        for(int j=100;j>=1;j=j/10) {
            for(int i= num%(j*10)/j;i>0;i--) {
                if(i==9||i==4){sb.append(dict1.get(i*j));break;}
                if(i>5){sb.append(dict1.get(5*j));i=i-5;}
                sb.append(dict1.get(j));
            }
        }
        return sb.toString();
    }

    public class Triple{
        int[] triple;

        public Triple(int[] a) {
            Arrays.sort(a);
            this.triple = a;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(triple);
        }

        @Override
        public boolean equals(Object obj) {
            Triple t = (Triple)obj;
            return  triple[0]==t.triple[0]&&triple[1]==t.triple[1]&&triple[2]==t.triple[2];
        }
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Map<Integer,Integer> indexMap = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            indexMap.put(nums[i],i);
        }

        Set<Triple> set = new HashSet<>();
        for(int i=0;i<nums.length;i++){
            for(int j=i+1;j<nums.length;j++){
                if(indexMap.containsKey(0-nums[i]-nums[j])&&indexMap.get(0-nums[i]-nums[j])>j){
                    set.add(new Triple( new int[]{nums[i],nums[j],0-nums[i]-nums[j]}));
                }
            }
        }
        return set.stream().map(x->Arrays.stream(x.triple).boxed().collect(Collectors.toList())).collect(Collectors.toList());
    }

    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int min = target;
        int margin = Integer.MAX_VALUE;
        for(int i=0;i<nums.length;i++){
            for(int j=i+1;j<nums.length;j++){
                for(int k=j+1;k<nums.length;k++){
                    if(nums[i]+nums[j]+nums[k]==target)
                        System.out.println(i+","+j+","+k);
                }
            }
        }
        for(int i=0;i<nums.length;i++){
            if(margin==0)return min;
            int l = i+1,r = nums.length-1;
            int tempMargin = Integer.MAX_VALUE;
            while(l<r){
                int s = nums[i]+nums[l]+nums[r];
                int m = Math.abs(s-target);
                if(m>tempMargin)break;
                else tempMargin = m;
                if(m<margin){
                    margin = m;
                    min = s;
                }
                if(s-target<0)l++;
                else r--;
            }
        }
        return min;
    }

    public int bagOfTokensScore(int[] tokens, int P) {
        Arrays.sort(tokens);
        int points=0,s=0,l=0,r=tokens.length-1;
        while(l<=r){
            if(tokens[l]<=P){
                P -= tokens[l++];
                s++;
                points = s;
            }else{
                if(s>0){
                    P += tokens[r--];
                    s--;
                    continue;
                }
                break;
            }
        }
        return points;
    }

   /* public int minDeletionSize(String[] A) {
        int res = 0, n = A.length, m = A[0].length(), i, j;
        boolean[] sorted = new boolean[n - 1];
        for (j = 0; j < m; ++j) {
            for (i = 0; i < n - 1; ++i) {
                if (!sorted[i] && A[i].charAt(j) > A[i + 1].charAt(j)) {
                    res++;
                    break;
                }
            }
            if (i < n - 1) continue;
            for (i = 0; i < n - 1; ++i)
                if (A[i].charAt(j) < A[i + 1].charAt(j))
                    sorted[i] = true;
        }
        return res;
    }*/

    public int tallestBillboard(int[] rods) {
        int s =(1<<rods.length)-1,max = 0;
        int[] sum = new int[s+1];
        List<Integer>[] rec = new List[5001];
        for(int j=1;j<=rods.length;j++){
            for(int i=1<<(j-1);i<1<<j;i++){
                sum[i] = rods[j-1]+sum[i-(1<<j-1)];
                if(rec[sum[i]]==null)rec[sum[i]]=new ArrayList<>();
                rec[sum[i]].add(i);
            }
        }
        for(int i=sum[s]/2;i>=0;i--){
            if(rec[i]!=null){
                for(int n:rec[i]){
                    for(int m:rec[i]){
                        if((n^m)==n+m){
                            return i;
                        }
                    }
                }
            }
        }
        return max;
    }

    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if(root1==null&&root2==null)return true;
        if(root1!=null&&root2!=null){
            int leftValue1 = root1.left==null?-1:root1.left.val;
            int rightValue1 = root1.right==null?-1:root1.right.val;
            int leftValue2 = root2.left==null?-1:root2.left.val;
            int rightValue2 = root2.right==null?-1:root2.right.val;
            if(leftValue1==leftValue2&&rightValue1==rightValue2){
                return flipEquiv(root1.left,root2.left)&&flipEquiv(root1.right,root2.right);
            }
            if(leftValue1==rightValue2&&leftValue2==rightValue1){
                TreeNode l = root1.left;
                root1.left = root1.right;
                root1.right = l;
                return flipEquiv(root1.left,root2.left)&&flipEquiv(root1.right,root2.right);
            }
        }
        return false;
    }

    public int[] deckRevealedIncreasing(int[] deck) {
        Arrays.sort(deck);
        int i,n = deck.length;
        LinkedList<Integer> stack = new LinkedList<>();
        stack.add(deck[n-1]);
        for(i=n-2;i>=0;i--){
            int last = stack.removeFirst();
            stack.add(last);
            stack.add(deck[i]);
        }
        int[] reorder = new int[n];
        for(i=0;i<n;i++){
            reorder[i]=stack.removeLast();
        }
        return reorder;
    }
    Map<Integer,Integer> f = new HashMap<>();
    public int largestComponentSize(int[] A) {
        Arrays.sort(A);
        for(int i=0;i<A.length;i++){
            f.put(A[i],-1);
        }
        for(int i=0;i<A.length;i++){
            for(int j=i+1;j<A.length;j++){
                if(find(A[i])==find(A[j]))continue;
                if(gcd(A[j],A[i])>1){
                    union(A[i],A[j]);
                }
            }
        }
        int min = 0;
        for(Integer key:f.keySet()){
            if(f.get(key)<0){
                min = Math.min(min,f.get(key));
            }
        }
        return -min;
    }
    public void union(int x,int y){
        x = find(x);
        y = find(y);
        if (x != y) {
            f.put(y,f.get(x)+f.get(y));
            f.put(x, y);
        }
    }
    public int find(int x){
        if(f.get(x)<0)return x;
        f.put(x, find(f.get(x)));
        return f.get(x);
    }

    int gcd(int a,int b){
        while(b>0){
            int k = a%b;
            a = b;
            b = k;
        }
        return a;
    }

    public int[] prisonAfterNDays(int[] cells, int N) {
        List<int[]> recur = new ArrayList<>();
        for(int i=0;i<14;i++){
            int[] cells1= new int[8];
            for(int j=1;j<7;j++){
                cells1[j] = cells[j-1]==cells[j+1]?1:0;
            }
            recur.add(cells1);
            cells=cells1;
        }
        return recur.get(N%14==0?13:N%14-1);
    }
    public int minDeletionSize(String[] A) {
        int r = A.length,c = A[0].length();
        int[][] mLen = new int[r][c];
        for(int i=0;i<r;i++){
            char[] chars = A[i].toCharArray();
            for(int j=0;j<c;j++){
                for(int k=j+1;k<c;k++){
                    if(chars[j]>chars[k])mLen[i][j]++;
                }
            }
        }


        return 0;
    }

    // 1、暴力法 ；列举words的所有组合方式
    // 2、找到所有word的index，然后根据词连接+词间距判断
    // 3、动态规划？dp[n][i] = 1 解决重复字符串的问题
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        if(words.length==0)return res;
        int l=s.length(),n = words.length,m = words[0].length();
        Map<String,Integer> map = new HashMap<>();
        for(int i=0;i<n;i++){
            map.put(words[i],map.get(words[i])==null?1:map.get(words[i])+1);
        }
        for(int i=0;i<=l-m;i++){
            Map<String,Integer> cmap = new HashMap<>();
            for(int j = 0;j<n;j++){
                if(i+j*m>=l)break;
                String word = s.substring(i+j*m,i+j*m+m);
                cmap.put(word,cmap.get(word)==null?1:cmap.get(word)+1);
            }
            if(cnt==t&&j==n){
                boolean match = true;
                for(String word:map.keySet()){
                    if(!(match=map.get(word).equals(cmap.get(word))))break;
                }
                if(match)res.add(i);
            }
        }
        return res;
    }

    public static void main(String args[]){
        Contest108 test = new Contest108();
        /*System.out.println(test.numSubarraysWithSum(new int[]{1,0,1,0,1,0},2));
        System.out.println(test.numSubarraysWithSum(new int[]{1,0,1,0,1},2));
        System.out.println(test.numSubarraysWithSum(new int[]{0,0,0,0,0},0));
        System.out.println(test.numSubarraysWithSum(new int[]{0,0,0,0,0,0,1,0,0,0},0));*/
        //System.out.println(test.minFallingPathSum(Utils.to2DArray("[[-80,-13,22],[83,94,-5],[73,-48,61]]")));
        /*System.out.println(test.knightDialer(2));
        System.out.println(test.knightDialer(3));
        System.out.println(test.knightDialer(161));*/
        /*System.out.println(test.shortestBridge(Utils.to2DArray("[[0,1],[1,0]]")));
        System.out.println(test.shortestBridge(Utils.to2DArray("[[0,1,0],[0,0,0],[0,0,1]]")));
        System.out.println(test.shortestBridge(Utils.to2DArray("[[1,1,1,1,1],[1,0,0,0,1],[1,0,1,0,1],[1,0,0,0,1],[1,1,1,1,1]]")));*/
        //System.out.println(test.longestPalindrome("abacab"));
/*        System.out.println(test.convert("AB",1));
        System.out.println(test.convert("A",2));
        System.out.println(test.convert("PAYPALISHIRING",3));
        System.out.println(test.convert("PAYPALISHIRING",4));*/
       /* System.out.println(test.convert("ibgkxinzlgbjntwrvtlbmstfemisdnslpavokkovqphekfxiaijmaeugqcbtrvggvdxfnlcdajjnqgvqpedrizaabbtswbbteyatlcwnoiaeovvdbaxlzxlcygwwhzpnzpgkrfahnambyjhzlelkbwobfkxmkmcjbiwupwccwqguznwmrhyufmvkyszigbuhlsdbofdmxjjyyfkroiltuievcffigzrtrvuhcaucldakkldyvprszxnecsmkugendavhapjmukyexdcsutmutzyvumiosmbxmwfpnodhadnxgpsblevegvbahlqcxrzmqebfvgpvjcvpupqfvnlbiodsatuznvldcoixuxudcpvwelbcbodjejdecxgpttuviduecokeefaksdottsfabigfgmxbgryqusuziefojibcnpvjhcjklpstcpuiguydouewzjlkrmmhbkqlmzxzopssgmcnicswxwtwheibqvithyevzevptnicckpknjhmhakogspypzrwxyuycpoxewzgiqtxzcjgetxkmm",
                343));*/
        //System.out.println(test.threeSum(new int[]{ -1, 0, 1, 2, -1, -4}));
        //System.out.println(test.threeSumClosest(new int[]{-55,-24,-18,-11,-7,-3,4,5,6,9,11,23,33},0));
       /* System.out.println(test.bagOfTokensScore(new int[]{100},50));
        System.out.println(test.bagOfTokensScore(new int[]{100,200},150));
        System.out.println(test.bagOfTokensScore(new int[]{100,200,300,400},200));*/
/*
        System.out.println(test.minDeletionSize(new String[]{"ca","bb","ac"}));
        long t1 = System.currentTimeMillis();
        System.out.println(test.tallestBillboard(new int[]{33,30,41,34,37,29,26,31,42,33,38,27,33,31,35,900,900,900,900,900}));
        System.out.println(System.currentTimeMillis()-t1);
*/
        /*long t1 = System.currentTimeMillis();
        System.out.println(test.largestComponentSize(new int[]{8192,4,8198,7,9,10,13,15,8208,8209,18,8212,8196,8219,8220,8221,33,8226,8227,36,37,8230,39,49,8243,8244,1374,8247,8458,8253,62,8257,66,8259,77,8205,80,81,8275,8281,8283,92,8285,94,97,8293,102,104,108,109,8302,111,8305,8306,116,118,20,122,8317,8320,129,8325,135,137,8334,8335,8338,147,8340,149,8217,152,153,154,8347,156,158,8351,8353,163,8356,165,8359,170,8363,174,29,8369,8370,179,182,183,8376,188,189,192,8389,8394,203,8397,210,35,8409,8411,220,8415,8417,8418,8419,8420,38,8425,235,8428,237,238,239,241,8434,246,248,8443,8445,8448,257,260,261,264,8457,266,8461,270,8463,8464,8465,8467,280,282,8476,8478,288,8481,8484,8485,8241,8491,8494,304,1416,8498,3829,8505,314,8509,8511,8512,322,9611,324,8517,326,329,8525,8526,8528,8529,8530,341,8537,347,349,8542,352,8546,8547,8549,358,8553,367,8560,369,371,372,8565,374,8569,385,386,8580,390,393,8587,396,8590,403,407,408,8603,412,414,8607,416,418,422,424,426,8621,431,432,8626,436,440,442,8639,450,8645,8646,8648,462,467,469,8665,8666,475,476,8669,8670,8671,480,8673,8675,484,486,487,8680,489,490,494,8687,498,8693,508,8704,513,515,8716,525,8722,531,8201,8725,8726,537,538,8731,8734,8737,8740,8742,554,556,8750,560,8753,8202,563,564,8758,567,8761,572,8766,579,580,8773,582,8775,8778,8780,8782,600,601,605,608,8802,8803,612,613,614,8807,8808,8810,566,620,8818,627,8820,629,8823,8825,1471,8829,8830,8833,642,643,645,650,8843,653,8846,8847,8851,660,9842,8854,663,664,665,667,8862,673,674,8867,8869,678,679,680,681,8874,114,686,687,8880,8881,691,692,8885,8886,699,700,8893,8896,706,707,8901,712,8910,720,724,8917,726,8919,8921,8924,733,737,8932,741,743,745,749,750,8947,8948,8951,760,761,763,764,765,766,767,770,8965,8967,8968,8970,8971,9225,8976,785,8978,788,791,794,8990,8991,8992,804,805,811,9004,9006,9007,27,818,9012,821,9017,9019,9020,831,9026,9027,9032,844,9038,9041,9043,855,9049,9050,859,862,9701,9056,9059,868,9065,9067,9068,146,879,9072,9073,9075,886,9080,148,890,892,898,9091,905,9098,151,908,9101,9103,916,928,935,9129,940,9138,9141,950,9147,9151,960,9154,9156,9157,9158,9159,969,973,974,982,9175,986,987,991,992,996,9189,999,1001,9197,9201,1012,9207,1018,9211,8362,9215,1026,9219,9221,1033,1037,1041,9234,1043,9236,9237,9238,1048,1050,1051,1052,9245,9247,1056,9250,9254,9255,1065,1068,1069,1074,9267,1076,1077,9270,1080,9274,1083,1084,1086,1091,9284,9285,9290,1100,1101,1102,9295,9297,1108,1109,9302,9308,9310,9311,9313,1126,9319,1128,9323,9326,8381,9328,1137,1139,9334,1143,1144,1145,1147,1148,1149,8384,9346,9347,1156,1157,1158,9354,9356,9359,9364,1174,1180,9375,1185,1186,1191,9384,1194,9387,1203,1205,9399,1208,9404,9407,1217,9410,1219,1222,1225,1226,9421,1230,9423,1232,9425,9428,1239,9436,1573,9443,1253,9446,1258,1259,1262,1266,1267,9462,1272,9465,9466,1276,9472,1284,1286,9482,9483,9485,9486,9487,1301,1304,9498,9500,1309,9511,9512,1324,9522,1332,9525,1335,9529,9532,1342,9538,1347,1348,1350,1354,9549,9784,227,9557,1367,9560,1370,9563,9566,1375,9572,9573,1382,9576,9579,1391,9585,9586,9589,1401,1406,9603,9604,9608,236,1419,1420,9615,9617,9618,1427,9620,1429,1431,9625,1434,1435,1436,1445,242,9647,1460,9656,9657,9659,9663,9667,1476,9669,9670,1479,9674,9675,1484,1491,9684,9688,9694,9695,9697,9698,1509,9702,9704,9705,9709,9710,1520,1521,1525,9720,1530,1533,1536,9729,1538,9734,1543,1544,9737,9743,9744,9746,1555,1558,1560,9756,9764,9765,1575,9769,1580,9774,9778,9780,1591,1592,9785,1597,1598,9791,9796,1605,9799,9801,1610,1612,9805,1614,1616,9809,5731,1622,1626,1628,1631,1632,9825,9827,9835,9836,1649,1650,9843,9844,9846,1656,9849,9852,1664,9858,1669,9863,9864,9866,1675,1678,1679,1680,9873,9874,1687,9880,1691,1693,9887,1696,1701,9894,9895,9897,1709,9909,9914,9915,9916,9922,9923,9924,9925,9927,1736,9929,9930,1739,9934,9939,1749,9942,1752,9945,1754,1756,9950,9951,1761,5755,9957,9958,9960,1770,9964,9968,1777,9973,9974,1785,9980,1790,1794,9988,1798,9994,9996,9998,9999,1812,1814,1825,1831,1833,1834,1835,306,1838,1672,1845,1846,1853,1856,1859,1860,1862,1866,1872,1878,1881,1883,881,1888,1889,1890,1899,1900,1902,1913,1914,1919,1920,1921,1922,1930,1688,1943,1945,1950,1956,1963,1964,1968,1969,1973,1974,1982,1986,1990,1991,1994,1997,1998,2008,2015,2018,2020,2023,338,2033,2037,2042,2044,2046,2050,2055,2063,2072,2074,2078,2081,2083,2085,2090,2100,350,2103,2111,2112,616,2122,8254,2125,2127,2128,2136,2139,2147,2148,2149,2152,2156,2157,2169,2170,2173,2175,2177,2183,2184,2186,2187,2189,2192,1731,2199,2202,8559,1733,2208,2215,2217,2218,1735,2222,2224,2225,2226,2228,2233,2234,2241,2245,2255,2257,2267,2271,2272,2278,2279,2284,2289,2292,2293,2300,2301,2302,2304,2308,8578,9944,2322,2325,2326,2328,2332,2333,2336,2344,9948,2346,2347,2350,2352,2354,2356,1758,2360,2361,2364,2370,2375,2378,2382,2385,2387,2389,2390,2392,2398,2402,1766,2410,2412,2418,2419,2421,2423,2426,2428,2432,2439,2448,2450,2452,2457,2461,2463,411,2473,2480,2487,2493,2494,2495,2499,2505,2507,2512,2519,2526,2527,2532,2537,2538,2542,2544,9982,2564,2566,2567,2569,2571,2572,2575,2577,2578,2579,2586,2588,2590,2591,2592,8624,2602,2603,2607,2608,2609,2610,2614,2615,2616,2617,2622,2624,2625,2628,2634,2637,2641,8634,2654,2660,2663,2670,2675,2679,2689,2690,2693,2695,2696,2697,2698,2705,2706,2714,2720,2721,2727,2729,2732,2735,2736,2743,2746,2748,2751,2752,2753,2756,2757,2762,2767,2769,2770,8654,2774,2775,2781,2783,2788,2789,2791,2792,2800,2803,2807,2809,2810,2816,2818,2820,2825,2827,2828,2831,2833,2834,2835,2848,2853,2861,2863,2864,2866,2876,2877,2878,481,2891,2892,2898,2902,2903,2905,2910,2911,2915,2916,2918,2922,2926,2929,8681,2937,2940,2941,2946,2950,2952,2954,2957,2958,2959,2966,2967,2974,2976,2982,2990,2991,2993,2996,2997,2998,3004,3005,3006,3009,3919,3014,3015,3020,3021,3026,3029,3036,3046,3048,8700,3051,3052,3053,3055,3056,3057,3060,3061,3062,3068,3069,3071,3077,3081,3083,3088,3090,8707,3096,3098,3103,3105,3107,3111,3113,3118,3120,3122,3123,3126,3135,3137,3138,3139,3140,3145,9652,3148,3156,3157,3164,3166,3167,8289,3177,3180,3181,3182,3189,3190,3192,3194,3196,3203,3207,3210,3211,3213,9023,3216,3221,3222,3228,3236,3244,3927,3253,3256,3262,3265,545,3273,3275,3276,3279,3282,3283,3286,3291,3299,3300,3302,3305,3309,3310,3311,3315,3316,3317,3320,3324,3342,3343,3345,3352,3355,3361,3362,3365,3369,3374,3379,3380,3381,8756,3386,3388,3396,3397,3398,113,3401,3402,8759,3407,3413,569,3421,3424,3433,3444,3456,3461,3463,3464,3466,3467,3471,3474,3478,3480,3481,3482,581,3488,3490,8774,3494,3495,3496,3512,3520,3521,3527,3528,3529,3530,3534,8301,3540,3542,3547,3548,3550,3557,3562,3571,3572,3574,3576,3579,3585,3588,3591,3612,133,3618,3619,3623,3628,3630,3633,3637,3642,3644,3645,3647,3652,3657,3669,3680,3682,3693,3694,3697,3700,3707,3711,3715,3723,3725,3726,3731,3739,3740,3741,626,3758,3761,3762,3765,3768,628,3774,8821,3784,3785,3788,3791,3792,3793,3796,633,3800,3802,3806,3808,3809,3810,3812,3813,3814,1218,3821,638,3834,3836,3840,3841,3844,3847,3848,3851,3854,3857,3860,3869,3872,3875,3876,3881,3887,3890,8842,3904,3906,3910,3915,8845,3922,3925,3385,3938,3939,3950,3953,3956,3959,3965,3968,3973,3975,3976,3979,2029,3984,3995,3996,4000,4002,4010,4012,4014,4017,4027,4029,4036,4041,4043,4047,4055,4056,4057,4059,4062,4064,4065,4066,4069,8871,4078,4080,4084,4085,4089,4090,4093,4094,4095,4099,4100,4108,4110,4112,4126,4128,4129,4131,4135,4139,4143,4144,4155,4164,4165,8595,4174,4176,4180,4194,4200,4204,4207,4212,4220,4224,4234,4237,4243,4248,4249,4250,4258,4260,4267,4271,4272,4273,4275,4277,4280,4287,4290,4292,4294,4299,4300,4303,4304,4307,4312,4322,4326,4327,4331,4338,4352,4354,4355,4358,4359,4362,4366,4367,4369,4370,4374,4376,4377,4378,4384,4386,4388,4389,4396,1393,4400,4402,4410,4413,4419,4423,4434,4437,740,4447,4450,4452,4454,4457,4459,4460,4465,4466,4472,4474,4482,4484,4488,4489,4490,4497,4499,4500,4505,4510,4511,4522,4529,4530,4531,4537,4538,4541,4542,4550,4555,4557,4558,4559,4562,4579,4582,4583,4585,4589,4591,4593,4595,4597,4604,4605,4608,4609,4613,4616,4617,4619,4631,4638,4644,4646,4647,4648,4651,4657,4663,4667,4668,4673,4675,4676,4684,4686,4690,4691,4699,4701,4702,4711,4714,4717,4718,4720,4721,4722,4724,4726,4731,4735,4742,4743,4747,4750,4763,4766,4769,4770,4775,4776,4778,4790,4791,4792,4794,4795,4799,4800,4801,4805,4808,4814,4815,4816,4817,4818,4823,4830,4833,4834,4838,4842,4849,4852,4854,4855,4856,4857,6271,4864,9003,4869,4870,4873,4879,4885,4887,4895,4900,4908,9010,4910,4911,4913,4921,4925,4928,4930,4938,4941,4943,4945,4948,4950,4956,4961,4963,4970,4971,4972,4975,4978,9713,4987,4993,4994,4995,4998,5000,5001,5010,5012,5015,5016,5030,5034,5048,5050,5051,5055,5057,5065,5071,5074,5077,5078,5080,5081,5083,5085,5086,5087,5090,5092,5094,5096,5101,5103,5105,5109,5113,5116,5117,5119,5123,5124,5130,5134,5135,5138,5144,5158,5165,5166,5167,5170,5171,5174,5187,5188,5189,5190,5203,5207,9060,5211,5213,5214,5215,5217,5220,5224,5226,5237,5241,5242,5244,5255,5260,5267,5270,5271,5274,5275,5276,5280,5281,5284,5287,5288,5294,5297,5303,5307,5310,5311,5316,5318,177,5323,5329,5333,5334,889,5338,5349,5352,9084,5362,5371,5372,5375,5378,9583,5391,5398,5404,5405,5409,5411,5417,5419,5421,5427,5438,5446,5447,909,5461,5466,911,5469,5472,5477,5480,5481,5483,9730,5495,5498,5501,5503,5507,5509,5512,5517,5523,5531,5538,5539,5541,5542,5547,5550,5553,5561,5566,5572,5578,5580,5582,5587,5588,5593,5594,5597,5599,5602,5603,5611,5618,5620,5621,1674,5634,5635,5636,5638,5640,5647,5650,5657,5658,5661,5667,5669,5676,5678,5687,5689,5710,5711,5720,5724,5725,5727,955,5735,5738,5740,5741,5745,5747,5752,5753,959,5759,5763,5766,5772,5773,5784,5787,5790,5791,5800,5803,5804,5805,5813,5815,5816,5821,5824,5826,5835,5839,5843,5845,5853,5857,5858,5877,5881,5889,5891,5892,5894,5900,5901,5908,5910,5916,5918,5919,5928,5929,5931,5932,5941,5945,5947,5949,5957,5960,5961,5964,5965,5973,5975,5976,5980,5981,6000,6003,8929,6012,6014,6019,6031,6032,6035,6036,6041,6044,6048,6056,6058,6063,6077,6080,6088,6089,6093,6097,6098,6101,6102,6103,6104,6109,6117,6121,6123,6131,6132,6133,477,6144,6148,6151,6152,6157,6161,6162,6166,6167,6168,6172,6174,6177,6179,6180,6181,6188,6189,6190,6194,6199,6202,6210,6213,6214,6218,6219,6220,6228,6234,6236,2405,6240,6244,6256,6258,6264,6267,6269,1045,6278,6289,6292,6297,6298,6299,6300,6302,9243,6308,6311,6312,6318,6319,6322,6326,6327,6342,6354,6357,6360,6362,6363,6370,6371,6378,6384,6389,6400,6401,6402,6405,6408,6409,6411,6419,6430,6438,6439,6442,6444,6448,1075,6454,6455,6461,6463,6466,6469,6470,6480,6483,6487,1082,6496,6509,6511,6513,6514,9278,6524,6526,6527,6528,6534,6535,6538,6541,6542,6543,6547,6553,1582,6557,1093,6565,6566,6567,6572,6574,6578,6580,6582,6584,6586,6591,6597,9294,6617,6619,6620,6623,6625,6636,6639,6641,6648,6654,6656,6657,6663,6667,6668,6671,6675,6680,6684,6692,6694,6695,6696,6697,6703,6706,6708,6715,6718,6720,6723,6724,6731,6732,1588,6736,6740,6749,6752,6757,6770,6776,6778,6782,6787,6788,6793,6800,6803,1134,9327,6816,6820,6824,6827,6830,6842,6845,6846,6850,6856,9336,6868,6875,3775,6878,6881,6882,229,6884,6885,6891,6893,6894,6895,775,6897,6900,6901,6911,6913,6914,1153,6921,6922,6927,6928,6929,6933,6934,6935,6939,6940,6945,6946,6947,6949,6953,6956,6964,6967,6971,6976,6980,6988,6991,6996,6997,7001,7003,7008,7010,7014,7016,7020,7022,7025,7027,7029,7030,7035,7038,7047,7050,7057,7068,7073,7079,7087,7088,7091,7093,7101,7105,7106,7107,7110,7113,7115,7119,7120,7127,7131,7133,7139,7140,7145,7148,7150,7151,7159,7161,7163,7164,7167,7177,7178,7179,7188,7189,7193,7195,7197,7198,7202,7203,7204,7207,7210,7217,7218,7220,7222,7224,7229,7232,7235,7236,7237,7245,7247,7252,7253,7254,7258,7261,7270,7272,7276,7277,7282,7289,7290,1215,7294,7301,7302,7303,7305,7307,7309,7310,8427,7327,7329,7333,7334,7335,7337,7338,7339,7344,7346,7348,7352,9418,7359,7363,7368,7369,7371,1229,7378,7381,7393,7395,7404,7405,7408,7410,7411,1236,7423,7429,7431,7435,7438,7443,7448,7453,7463,7473,7484,7487,7489,7490,7492,7495,7497,7500,7507,7509,7510,7513,7514,7520,7521,7522,7524,7527,7532,7540,7543,7544,7547,7548,7553,7556,7557,7558,7563,7565,7574,7575,7576,7577,7581,7582,7586,7588,7589,7595,7597,7599,7600,7607,7609,7615,7616,7619,7622,7624,7626,7635,7638,7641,7643,7644,7649,7653,7658,7668,7670,7671,7673,7681,7683,7687,7689,7691,7692,256,7695,7698,7700,7701,7706,7711,7726,2653,530,7736,7740,7747,7750,7753,7757,7762,7763,7770,7775,7778,7780,7781,7785,7786,7788,7793,7798,7801,7806,7808,7809,7811,7812,7817,7818,7827,7829,7830,7834,7840,7844,7846,7847,7850,7851,7853,7856,7862,7864,7865,7866,7869,7872,7873,7883,7886,7889,7904,7907,7908,7924,7928,7931,7934,7935,7938,7939,7943,7951,7953,7955,7957,7959,7963,7966,7970,7972,7975,7978,7980,7981,7983,7984,53,7989,7992,7995,7999,8002,8015,8019,8021,8023,8027,8029,8030,8032,8034,8035,8038,8045,8048,8055,8065,8068,8069,8074,8079,8080,8085,1633,8090,8093,8094,8103,8108,8109,8111,8112,8116,8118,8120,9546,8129,8147,8148,8150,8154,8164,8168,8175,8176,8178,8179,8186,8191}));
        System.out.println(System.currentTimeMillis()-t1);*/
        System.out.println(test.minDeletionSize(new String[]{"babca","bbazb"}));
        System.out.println(test.findSubstring("barfoothefoobarman",new String[]{"foo","bar"}));
    }
}
