//package algorithm;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @ClassName lintCode66
// * @Description TODO
// * @Date 2018/9/26 下午2:32
// **/
//
///**
// * Definition of TreeNode:
// * public class TreeNode {
// *     public int val;
// *     public TreeNode left, right;
// *     public TreeNode(int val) {
// *         this.val = val;
// *         this.left = this.right = null;
// *     }
// * }
// */
//
////使用栈实现非递归
//
//public class lintCode66 {
//    /**
//     * @param root: A Tree
//     * @return: Preorder in ArrayList which contains node values.
//     */
//    public List<Integer> preorderTraversal(TreeNode root) {
//        // write your code here
//        ArrayList<Integer> result = new ArrayList<Integer>();
//        traverse(root, result);
//        return result;
//    }
//
//    private void traverse(TreeNode root, ArrayList<Integer> result) {
//        if (root == null) {
//            return;
//        }
//
//        result.add(root.val);
//        traverse(root.left, result);
//        traverse(root.right, result);
//    }
//
//    public static void main(String[] args) {
//
//    }
//}