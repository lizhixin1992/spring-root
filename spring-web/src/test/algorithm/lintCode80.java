package algorithm;

/**
 * @ClassName lintCode80
 * @Description TODO
 * @Date 2018/9/26 下午2:32
 **/
public class lintCode80 {
    /**
     * @param nums: A list of integers
     * @return: An integer denotes the middle number of the array
     */
    public static int median(int[] nums) {
        // write your code here
        int temp = -1;
        for (int i = 1; i < nums.length; i++) {
            int n = 0;
            if (nums[i] < nums[i - 1]) {
                temp = nums[i];
                nums[i] = nums[i - 1];
                for (int j = i - 2; j >= 0; j--) {
                    if (nums[j] > temp) {
                        nums[j + 1] = nums[j];
                    } else {
                        n = j + 1;
                        break;
                    }
                }
                nums[n] = temp;
            }
        }

        int s = nums.length % 2;
        if (s == 0) {
            return nums[nums.length / 2 - 1];
        } else {
            return nums[nums.length / 2];
        }
    }

    public static void main(String[] args) {
        int[] nums = {10, 5, 6, 7, 1, 2, 3, 8, 9, 4};
        median(nums);
    }
}