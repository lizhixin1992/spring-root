package algorithm;

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

/**
 * @ClassName lintCode85
 * @Description TODO
 * @Date 2018/9/26 下午2:32
 **/
public class lintCode85 {
    /*
     * @param root: The root of the binary search tree.
     * @param node: insert this node into the binary search tree
     * @return: The root of the new binary search tree.
     */
    public TreeNode insertNode(TreeNode root, TreeNode node) {
        // write your code here

        //递归
//        if(root == null){
//            return node;
//        }
//        if(root.val > node.val){
//            root.left = insertNode(root.left, node);
//        }else {
//            root.right = insertNode(root.right, node);
//        }
//        return root;


        //非递归
        if (root == null) {
            return root;
        }

        TreeNode cur = root;
        TreeNode prev = null;
        while (cur != null) {
            prev = cur;
            cur = cur.val > node.val ? cur.left : cur.right;
        }

        if (prev.val > node.val) {
            prev.left = node;
        } else {
            prev.right = node;
        }
        return root;
    }

    public static void main(String[] args) {
        System.out.println(0 ^ 7);
    }
}