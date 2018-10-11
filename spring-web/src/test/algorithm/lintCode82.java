package algorithm;

/**
 * @ClassName lintCode82
 * @Description TODO
 * @Date 2018/9/26 下午2:32
 **/
public class lintCode82 {
    /**
     * @param A: An integer array
     * @return: An integer
     */
    public int singleNumber(int[] A) {
        if (A == null || A.length == 0) {
            return -1;
        }
        int rst = 0;
        for (int i = 0; i < A.length; i++) {
            rst ^= A[i];
        }
        return rst;
    }

    public static void main(String[] args) {
        System.out.println(0^7);
    }
}