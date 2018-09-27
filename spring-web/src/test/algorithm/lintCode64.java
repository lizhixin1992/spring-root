package algorithm;

/**
 * @ClassName lintCode64
 * @Description TODO
 * @Date 2018/9/26 下午2:32
 **/
public class lintCode64 {
    /*
     * @param A: sorted integer array A which has m elements, but size of A is m+n
     * @param m: An integer
     * @param B: sorted integer array B which has n elements
     * @param n: An integer
     * @return: nothing
     */
    public static void mergeSortedArray(int[] A, int m, int[] B, int n) {
        // write your code here
        int i = m-1, j = n-1, index = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (A[i] > B[j]) {
                A[index--] = A[i--];
            } else {
                A[index--] = B[j--];
            }
        }
        while (i >= 0) {
            A[index--] = A[i--];
        }
        while (j >= 0) {
            A[index--] = B[j--];
        }
    }

    public static void main(String[] args) {
        int[] A = new int[5];
        A[0] = 2;
        A[1] = 5;
        A[2] = 9;
        int[] B = {4, 7};
        mergeSortedArray(A, 3, B, 2);
    }
}