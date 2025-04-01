package com.piedaholic.algo.tree;

import com.piedaholic.algo.nodes.TreeNode;
import org.junit.Test;

import java.util.List;

public class TestTreeTraversal {

  DepthFirstTraversal<Integer> traversal = new DepthFirstTraversal<>();

    @Test
    public void test() {
        Integer[] root = {1,2,3,4,5,null,8,null,null,6,7,9};
    }

    @Test
    public void testBinaryTreeToBST() {
        TreeNode<Integer> root = new TreeNode<>(10);

        root.left = new TreeNode<>(2);

        root.left.left = new TreeNode<>(8);
        root.left.right = new TreeNode<>(4);

        root.right = new TreeNode<>(7);

        traversal.toBST(root);
        traversal.inOrder(root);
    }

    @Test
    public void testPathSum() {
        TreeNode<Integer> root = new TreeNode<>(-2);
        root.left = null;
        root.right = new TreeNode<>(-3);
        BinaryTree<Integer> binaryTree = new BinaryTree<>();
        List<List<Integer>> sums = binaryTree.pathSum(root, -5);
        assert !sums.isEmpty();
    }
}
