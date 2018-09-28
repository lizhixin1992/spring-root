package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @ClassName lintCode66
 * @Description TODO
 * @Date 2018/9/26 下午2:32
 **/

/**
 * Definition of TreeNode:
 * public class TreeNode {
 * public int val;
 * public TreeNode left, right;
 * public TreeNode(int val) {
 * this.val = val;
 * this.left = this.right = null;
 * }
 * }
 */

//使用栈实现非递归

public class lintCode66 {
    /**
     * @param root: A Tree
     * @return: Preorder in ArrayList which contains node values.
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        // write your code here
        //递归版本解法
        ArrayList<Integer> result = new ArrayList<Integer>();
        traverse(root, result);
        return result;

//        //非递归，使用栈解法
//        List<Integer> res = new ArrayList<>();
//        Stack<TreeNode> s = new Stack<TreeNode>();
//        s.add(root);
//
//        while (!s.isEmpty()) {
//            TreeNode node = s.pop();
//            if (node.right != null) {
//                s.push(node.right);
//            }
//            if (node.left != null) {
//                s.push(node.left);
//            }
//            res.add(node.val);
//        }
//        return res;
    }

    private void traverse(TreeNode root, ArrayList<Integer> result) {
        if (root == null) {
            return;
        }

        result.add(root.val);
        traverse(root.left, result);
        traverse(root.right, result);
    }

    public static void main(String[] args) {

    }
}