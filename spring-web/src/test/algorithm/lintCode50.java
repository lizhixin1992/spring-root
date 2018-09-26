package algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName lintCode50
 * @Description TODO
 * @Date 2018/9/26 下午2:32
 **/
public class lintCode50 {
    /*
     * @param nums: Given an integers array A
     * @return: A long long array B and B[i]= A[0] * ... * A[i-1] * A[i+1] * ... * A[n-1]
     */
    public static List<Long> productExcludeItself(List<Integer> nums) {
        List<Long> n = new ArrayList<>();
        for (int i = 0; i < nums.size(); i++) {
            n.add(i,left(nums, i) * right(nums,i));
        }
        return n;
    }

    public static Long left(List<Integer> nums,Integer i){
        Long a = 1L;
        for (int j = 0; j < i; j++) {
            a = a * nums.get(j);
        }
        return a;
    }

    public static Long right(List<Integer> nums,Integer i){
        Long a = 1L;
        for (int j = i + 1; j < nums.size(); j++) {
            a = a * nums.get(j);
        }
        return a;
    }

    public static void main(String[] args) {
        List<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);
        a.add(5);
        a.add(6);
        List<Long> m = productExcludeItself(a);
        for (Long aLong : m) {
            System.out.println(aLong);
        }
    }
}