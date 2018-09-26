package algorithm;

/**
 * @ClassName lintCode60
 * @Description TODO
 * @Date 2018/9/26 下午2:32
 **/
public class lintCode60 {
    /**
     * @param A:      an integer sorted array
     * @param target: an integer to be inserted
     * @return: An integer
     */
    public static int searchInsert(int[] A, int target) {
        int len = A.length;

        int start = 0;
        int end = len - 1;

        while (start <= end) {
            if (A[start] > target) {
                return start;
            }
            if (A[end] < target) {
                return end + 1;
            }
            // target is in the range
            int mid = (start + end) / 2;
            if (A[mid] == target) {
                return mid;
            }
            if (target > A[mid]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return start;
    }

    public static void main(String[] args) {
        int[] n = {1, 3, 5, 6};
        System.out.println(searchInsert(n, 5));
    }
}