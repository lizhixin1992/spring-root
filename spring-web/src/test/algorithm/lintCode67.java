package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @ClassName lintCode67
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

public class lintCode67 {
    /**
     * @param root: A Tree
     * @return: Preorder in ArrayList which contains node values.
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        // write your code here
        //递归版本解法
//        ArrayList<Integer> result = new ArrayList<Integer>();
//        traverse(root, result);
//        return result;


        //栈实现，非递归
        ArrayList<Integer> result = new ArrayList<Integer>();
        Stack<TreeNode> s = new Stack<TreeNode>();
        TreeNode p = root;
        while (p != null || !s.empty()) {
            while (p != null) {
                s.push(p);
                p = p.left;
            }
            p = s.pop();
            result.add(p.val);
            if (p.right != null) {
                p = p.right;
            } else {
                p = null;
            }
        }
        return result;
    }

    private void traverse(TreeNode root, ArrayList<Integer> result) {
        if (root == null) {
            return;
        }

        traverse(root.left, result);
        result.add(root.val);
        traverse(root.right, result);
    }

    public static void main(String[] args) {

    }
}