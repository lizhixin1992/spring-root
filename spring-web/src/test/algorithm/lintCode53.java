package algorithm;

/**
 * @ClassName lintCode53
 * @Description TODO
 * @Date 2018/9/26 下午2:32
 **/
public class lintCode53 {
    /*
     * @param s: A string
     * @return: A string
     */
    public static String reverseWords(String s) {
        // write your code here
        String[] a = s.split(" ");
        String n = "";
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
            if(!a[i].isEmpty()){
                n = a[i] + " " + n;
            }
        }
        return n;
    }

    public static void main(String[] args) {
        System.out.println(reverseWords(" the     sky is     blue    "));
    }
}