import java.rmi.UnexpectedException;
import java.util.*;

public class Solution {

    /*
     * 1. the sum of two int numbers
     */
    //1.1
    public int[] twoSum(int[] nums, int target) {
        int length = nums.length;
        for(int i = 0;i<length;i++){
            int difference = target - nums[i];
                for(int j = i+1;j<length;j++){
                    if(difference==nums[j]){
                        int[] result = new int[]{i,j};
                        return result;
                    } else{
                        continue;
                    }
                }
        }
        return null;
    }

    /*
     * 2.add two numbers
     */
    //2.1
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode listNode = l1;
        l1.val += l2.val;
        while(l1.next!=null||l2.next!=null||l1.val>=10){
            if(l1.val>=10){
                l1.val -= 10;
                if(l1.next!=null){
                    l1 = l1.next;
                    l1.val += 1;
                    if(l2.next!=null){
                        l2 = l2.next;
                        l1.val += l2.val;
                    }
                } else {
                    l1.next =new ListNode(1);
                    l1 = l1.next;
                    if(l2.next!=null){
                        l2 = l2.next;
                        l1.val += l2.val;
                    } else{
                        return listNode;
                    }
                }
            } else {
                if(l1.next!=null){
                    l1 = l1.next;
                    if(l2.next!=null){
                        l2 = l2.next;
                        l1.val += l2.val;
                    } else{
                        return listNode;
                    }
                } else {
                    if(l2.next!=null){
                        l2 = l2.next;
                        l1.next = l2;
                        return listNode;
                    }
                }
            }
        }
        return listNode;
    }

    /*
     * 3.find the longest substring(No duplicate characters)
     */

    //3.1 time and space cost are too heavy
     /**public int lengthOfLongestSubstring(String s) {
        int length = 0;
        List<Character> list = new LinkedList<Character>();
        for(int i=0; i<s.length(); i++){
            Character c = s.charAt(i);
            int leftIndex = list.indexOf(c);
            if(leftIndex==-1){
                list.add(c);
                length = Math.max(length,list.size());
            } else {
                list.add(c);
                list = list.subList(Math.min(leftIndex+1,list.size()-1),list.size());
                length = Math.max(length,list.size());
            }
        }
        return length;
        *
        * //a little quickly but still high time and space cost.
        int length = 0;
        String subStr = "";
        for(int i=0; i<s.length(); i++){
            Character c = s.charAt(i);
            int leftIndex = subStr.indexOf(c); //indexOf contains for-curculation
            subStr = (leftIndex==-1)?
                    subStr += c
                    :subStr.substring(leftIndex+1) + c;
            length = Math.max(length,subStr.length());
        }
        return length;
    }*/

    //3.2
    public int lengthOfLongestSubstring(String s){
        int rightIndex = 0;
        int leftIndex = 0;
        int length = 0;
        Set<Character> characterSet = new HashSet<>();
        while(s.length()>0 && rightIndex<s.length()){
            Character character = s.charAt(rightIndex);
            while(characterSet.contains(character)){
                characterSet.remove(s.charAt(leftIndex));
                leftIndex++;
            }
            characterSet.add(character);
            rightIndex++;
            length = Math.max(length,rightIndex-leftIndex);
        }
        return length;
    }

    /*
     * 4. find median
     */

    // 4.1 pass the test but time complexity is O(m+n)
    /*public double findMedianSortedArrays(int[] nums1, int[] nums2) throws UnexpectedException {
        int l1 = nums1.length;
        int l2 = nums2.length;
        int l = l1 + l2;
        int[] nums = new int[l];
        int i=0,j =0,count=0;
        while(i<l1 || j<l2){
            int num1 = (i<l1)? nums1[i]:nums2[j]+1;
            int num2 = (j<l2)? nums2[j]:nums1[i]+1;
            if(num1<num2){
                nums[count] = num1;
                i++;
            } else {
                nums[count] = num2;
                j++;
            }
            count++;
        }
        if(l==0){
            throw new UnexpectedException("Can not calculate median cause both arrays are empty.");
        }
        double median =nums[l/2] + nums[(l-1)/2];
        median /= 2;
        return median;
    }
*/
    //4.2 find the kth smallest number binary-search TC = O(log(m+n))
    public double findMedianSortedArrays(int[] nums1, int[] nums2) throws UnexpectedException {
        int l1 = nums1.length;
        int l2 = nums2.length;
        int l = l1 + l2;
        double median;
        if(l1==0&&l2==0){
            throw new UnexpectedException("Both arrays are empty.");
        }
        if(l1==0){
            median = nums2[l2/2] + nums2[(l2-1)/2];
            return median/2;
        }
        if(l2==0){
            median = nums1[l1/2] + nums1[(l1-1)/2];
            return median/2;
        }
        if(l1==1&&l2==1){
            median = nums1[0] + nums2[0];
            return median/2;
        }
        // initialize
        int medianIndex = (l+1)/2;
        int range = 1;
        int index1 = 0;
        int index2 = 0;
        int num1 = nums1[0];
        int num2 = nums2[0];
        while( range> 0){
            if(num1<num2){
                range = (index1 + range + 1 > l1) ? l1-1-index1 : range;
                index1 += range;
                if(range==0){
                    median = (l%2==0) ?
                            nums2[index2+medianIndex-2] + nums2[index2+medianIndex-1]
                            :nums2[index2+medianIndex-2] + nums2[index2+medianIndex-2];
                    return median/2;
                }
                medianIndex -= range;
                range = medianIndex/2;
                num1 = (index1+range>l1||index1+range<1)? nums1[l1-1]:nums1[index1+range-1];
                num2 = (index2+range>l2||index2+range<1)? nums2[l2-1]:nums2[index2+range-1];
            } else {
                range = (index2 + range + 1 > l2) ? l2-1-index2 : range;
                index2 += range;
                if(range==0){
                    median = (l%2==0) ?
                            nums1[index1+medianIndex-2] + nums1[index1+medianIndex-1]
                            :nums1[index1+medianIndex-2] + nums1[index1+medianIndex-2];
                    return median/2;
                }
                medianIndex -= range;
                range = medianIndex/2;
                num1 = (index1+range>l1||index1+range<1)? nums1[l1-1]:nums1[index1+range-1];
                num2 = (index2+range>l2||index2+range<1)? nums2[l2-1]:nums2[index2+range-1];
            }
        }
        if(index1>l1-1){
            if(l%2==0){
                median = nums2[index2] + nums2[index2+1];
            } else{
                median = nums2[index2] + nums2[index2];
            }
            return median/2;
        } else if(index2>l2-1){
            if(l%2==0){
                median = nums1[index1] + nums1[index1+1];
            } else{
                median = nums1[index1] + nums1[index1];
            }
            return median/2;
        } else {
            num1 = nums1[index1];
            num2 = nums2[index2];
            if(num1<num2){
                if(l%2==0){
                    if(index1<l1-1){
                        num2 = Math.min(num2, nums1[index1+1]);
                    }
                    median = num1+num2;
                    return median/2;
                } else {
                    median = num1 + num1;
                    return median/2;
                }
            } else {
                if(l%2==0){
                    if(index2<l2-1){
                        num1 = Math.min(num1, nums2[index2+1]);
                    }
                    median = num1+num2;
                    return median/2;
                } else {
                    median = num2 + num2;
                    return median/2;
                }
            }
        }
    }

    /*
     * 5. find the longest palindrome string from given string
     */

    //5.1direct resolution: TC is 0(n^3)
    /*public String longestPalindrome(String s) throws UnexpectedException{
        int l = s.length();
        int sl = l;
        if(l==0){
            throw new UnexpectedException("String is empty.");
        }
        for(;sl>0;sl--){
            for(int i = 0;i+sl<=l;i++){
                String subStr = s.substring(i,i+sl);
                if(this.isPalindrome(subStr)){
                    return subStr;
                }
            }
        }
        return "";
    }
    private boolean isPalindrome(String s){
        boolean result = true;
        for(int i=0,j=s.length()-1;i<j;i++,j--)
            if(s.charAt(i)!=s.charAt(j)){
                return false;
            }
        return result;
    }*/

    //5.2 center expansion TC is O(n^2)
    /*public String longestPalindrome(String s) throws UnexpectedException{
        int l = s.length();
        if(l==0){
            return "";
        }
        String result = s.substring(0,1);
        for(int i= 0;i<l;i++){
            String s1 = this.expansion(i,i,s);
            String s2 = this.expansion(i,i+1,s);
            if(s1.length()>result.length()){
                result = s1;
            }
            if(s2.length()>result.length()){
                result = s2;
            }
        }
        return result;
    }
    private String expansion(int left,int right,String s){
        int l = s.length();
        while(left>-1&&right<l){
            if(s.charAt(left) != s.charAt(right)){
                break;
            }
            left--;
            right++;
        }
        String exStr = (left+1>right-1)? "":s.substring(left+1,right);
        return exStr;
    }*/

    // 5.2 java8 version
    @FunctionalInterface
    interface Expansion{
        String expansion(int left,int right,String s);
    }
    public String longestPalindrome(String s) throws UnexpectedException{
        int l = s.length();
        if(l==0){
            return "";
        }
        String result = s.substring(0,1);
        Expansion exp = (left,right,str)->{
            int len = str.length();
            while(left>-1&&right<len){
                if(str.charAt(left) != str.charAt(right)){
                    break;
                }
                left--;
                right++;
            }
            String exStr = (left+1>right-1)? "":str.substring(left+1,right);
            return exStr;
        };
        for(int i= 0;i<l;i++){
            String s1 = exp.expansion(i,i,s);
            String s2 = exp.expansion(i,i+1,s);
            if(s1.length()>result.length()){
                result = s1;
            }
            if(s2.length()>result.length()){
                result = s2;
            }
        }
        return result;
    }

    //5.3 dynamic programming : TC is O(n^2)
    /*public String longestPalindrome(String s) throws UnexpectedException{
        if(s==null){
            throw new UnexpectedException("String is null");
        }
        int length = s.length();
        if(length==0){
            return "";
        }
        int maxLength = 1;
        int left = 0;
        boolean[][] dp = new boolean[length][length];
        for(int i = 0;i<length;i++){
            dp[i][i] = true;
        }
        //pay attention to the assignment order for dp[i][j]
        for(int i=length-1; i>-1; i--){
            for(int j=i+1; j<length; j++){
                if(s.charAt(i) != s.charAt(j)){
                    dp[i][j] = false;
                } else {
                    if(j-i<2){
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i+1][j-1];
                    }
                }
                if(dp[i][j] && j-i+1>maxLength){
                    maxLength = j-i+1;
                    left = i;
                }
            }
        }
        return s.substring(left,left+maxLength);
    }*/

    /*
     * 6. convert String to zigzag
     */

    //6.1 TC=O(n^2)
    public String convert(String s, int numRows) throws UnexpectedException{
        if(s==null){
            throw new UnexpectedException("String is null");
        }
        int l = s.length();
        if(numRows<2||numRows>l-1){
            return s;
        }
        String zigS = "";
        for(int i=0;i<numRows;i++){
            int count = 1;
            int index = i;
            int k1 = 2*(numRows-i-1);
            int k2 = 2*i;
            while(index<l){
                zigS += s.charAt(index);
                if(k1==0){
                    index += k2;
                } else if(k2==0){
                    index += k1;
                } else {
                    if(count%2==0){
                        index += k2;
                        count++;
                    } else {
                        index += k1;
                        count++;
                    }
                }
            }
        }
        return zigS;
    }

    //7. reverse int

    //7.1.1 int --> String --> int
    /*
        public int reverse(int x) {
            int INT_MAX = (int)(Math.pow(2,31)-1);
            int INT_MIN = (int)(-Math.pow(2,31));
            String s = String.valueOf(x);
            String rs = "";
            if(s.charAt(0)=='-'){
                rs += '-';
                s = s.substring(1);
            }
            // rs += s.reverse()  s should be StringBuilder if reverse used.
            for(int i=s.length()-1; i>-1; i--){
                rs += s.charAt(i);
            }
            long rx = Long.parseLong(rs);
            System.out.println("s:" + s);
            System.out.println("rs:"+ rs);
            System.out.println("rx:"+ rx);
            if(rx>INT_MAX||rx<INT_MIN){
                return 0;
            } else{
                return (int)rx;
            }
        }
    */

    //7.1.2 StringBuilder
    /*
        public int reverse(int x){
            int INT_MAX = (int)(Math.pow(2,31)-1);
            int INT_MIN = (int)(-Math.pow(2,31));
            StringBuilder sb = new StringBuilder();
            sb.append(x);
            StringBuilder rs = new StringBuilder();
            if(x<0){
                rs.append('-');
                sb.delete(0,1);
            }
            rs.append(sb.reverse());
            long rx = Long.parseLong(String.valueOf(rs));
            if(rx<INT_MIN||rx>INT_MAX){
                return 0;
            } else{
                return (int)rx;
            }
        }
    */

    //7.2 mathematical method
    public int reverse(int x){
        int INT_MAX = 2147483647;
        int INT_MIN = -2147483648;
        double rx =0;
        while(x!=0){
            rx = rx*10 + x%10;
            x /= 10;
        }
        if(rx>INT_MAX||rx<INT_MIN){
            return 0;
        } else{
            return (int)rx;
        }
    }


}
