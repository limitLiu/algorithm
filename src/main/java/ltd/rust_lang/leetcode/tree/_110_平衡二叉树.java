package ltd.rust_lang.leetcode.tree;

import java.util.LinkedList;

public class _110_平衡二叉树 {
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        return Math.abs(height(root.left) - height(root.right)) <= 1 && isBalanced(root.left) && isBalanced(root.right);
    }

    private int height(TreeNode root) {
        var h = 0;
        if (root != null) {
            var queue = new LinkedList<TreeNode>();
            queue.offer(root);
            var levelSize = 1;
            while (!queue.isEmpty()) {
                var node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                if (--levelSize == 0) {
                    levelSize = queue.size();
                    h++;
                }
            }
        }
        return h;
    }
}
