package com.piedaholic.algo.tree;

import com.piedaholic.algo.nodes.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeTraversal<T extends Comparable<T>> {

  protected class BreadthFirst {

    // TODO: Level Order Traversal
    /**
    procedure levelorder(node)
    queue ← empty queue
    queue.enqueue(node)
            while not queue.isEmpty()
    node ← queue.dequeue()
    visit(node)
        if node.left ≠ null
            queue.enqueue(node.left)
            if node.right ≠ null
            queue.enqueue(node.right)
     */
    public void traverse() {}
  }

  public TreeNode<T> toBST(TreeNode<T> root) {
    // In-order traversal of binary tree
    // Save the order to an array
    // Again In-order traversal of binary tree and update the node with the corresponding data in
    // array
    List<T> inOrder = new ArrayList<>();
    DepthFirstTraversal<T> traversal = new DepthFirstTraversal<>();
    traversal.inOrder(root, inOrder);
    Collections.sort(inOrder);
    int[] tracker = new int[1];
    traversal.inOrderUpdate(root, inOrder, tracker);
    return root;
  }
}
