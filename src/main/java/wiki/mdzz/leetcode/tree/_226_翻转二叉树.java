package wiki.mdzz.leetcode.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class _226_翻转二叉树 {

    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        var node = root;
        var stack = new Stack<TreeNode>();
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                TreeNode t = node.left;
                node.left = node.right;
                node.right = t;
                node = node.left;
            }
            if (!stack.isEmpty()) {
                node = stack.pop();
                node = node.right;
            }
        }
        return root;
    }

    public TreeNode invertTree1(TreeNode root) {
        if (root == null) return null;
        TreeNode t = root.left;
        root.left = root.right;
        root.right = t;
        invertTree1(root.left);
        invertTree(root.right);
        return root;
    }

    public TreeNode invertTree2(TreeNode root) {
        if (root == null) return null;
        invertTree2(root.left);
        TreeNode t = root.left;
        root.left = root.right;
        root.right = t;
        invertTree2(root.left);
        return root;
    }

    public TreeNode invertTree3(TreeNode root) {
        if (root == null) return null;
        invertTree3(root.left);
        invertTree3(root.left);
        TreeNode t = root.left;
        root.left = root.right;
        root.right = t;
        return root;
    }

    public TreeNode invertTree4(TreeNode root) {
        if (root == null) return null;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            var node = queue.poll();
            var t = node.left;
            node.left = node.right;
            node.right = t;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return root;
    }
}
