package com.piedaholic.algo.tree;

import com.piedaholic.algo.nodes.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DepthFirstTraversal<T extends Comparable<T>> extends TreeTraversal<T> {
    public void preOrder(TreeNode<T> root) {
        if (root == null) {
            return;
        }

        System.out.println(root.val);
        preOrder(root.left);
        preOrder(root.right);
    }

    /**
    TODO : procedure iterativePreorder(node)
        if node = null
                return
        stack ← empty stack
        stack.push(node)
        while not stack.isEmpty()
            node ← stack.pop()
            visit(node)
            // right child is pushed first so that left is processed first
            if node.right ≠ null
                stack.push(node.right)
                if node.left ≠ null
                stack.push(node.left)
     */
    public void iterativePreOrder() {}

    /**
     TODO: procedure iterativePostorder(node)
         if node = null
         return
         stack ← empty stack
         lastNodeVisited ← null
         while not stack.isEmpty() or node ≠ null
         if node ≠ null
             stack.push(node)
             node ← node.left
         else
             peekNode ← stack.peek()
             // if right child exists and traversing node
             // from left child, then move right
             if peekNode.right ≠ null and lastNodeVisited ≠ peekNode.right
                 node ← peekNode.right
             else
                 visit(peekNode)
                 lastNodeVisited ← stack.pop()
     */
    public void iterativePostOrder() {}

    /**
    TODO: procedure iterativeInorder(node)
        if node = null
                return
        stack ← empty stack
        while not stack.isEmpty() or node ≠ null
                if node ≠ null
                    stack.push(node)
                    node ← node.left
                else
                    node ← stack.pop()
                    visit(node)
                    node ← node.right
     */
    public void iterativeInOrder() {}

    /**
    TODO: procedure search(bst, key)
        // returns a (node, stack)
        node ← bst.root
        stack ← empty stack
        while node ≠ null
                stack.push(node)
                if key = node.key
                return (node, stack)
                if key < node.key
        node ← node.left
            else
        node ← node.right
        return (null, empty stack)
     */
    public void search() {}

    /**
    The function inorderNext returns an in-order-neighbor of node,
     either the in-order-successor (for dir=1) or the in-order-predecessor (for dir=0),
     and the updated stack, so that the binary search tree may be sequentially in-order-traversed
     and searched in the given direction dir further on.

    TODO: procedure inorderNext(node, dir, stack)
        newnode ← node.child[dir]
                if newnode ≠ null
                do
        node ← newnode
                stack.push(node)
        newnode ← node.child[1-dir]
        until newnode = null
            return (node, stack)
                // node does not have a dir-child:
                do
                if stack.isEmpty()
                return (null, empty stack)
        oldnode ← node
        node ← stack.pop()   // parent of oldnode
        until oldnode ≠ node.child[dir]
                // now oldnode = node.child[1-dir],
                // i.e. node = ancestor (and predecessor/successor) of original node
                return (node, stack)
    */
    public void inOrderNext() {}

    public void morris(TreeNode<T> root) {
            TreeNode<T> curr, prev;

            if (root == null)
                return;

            curr = root;
            while (curr != null) {
                if (curr.left == null) {
                    System.out.print(curr.val + " ");
                    curr = curr.right;
                }
                else {
                    /* Find the previous (prev) of curr */
                    prev = curr.left;
                    while (prev.right != null && prev.right != curr)
                        prev = prev.right;

                    /* Make curr as right child of its prev */
                    if (prev.right == null) {
                        prev.right = curr;
                        curr = curr.left;
                    }

                    /* fix the right child of prev*/

                    else {
                        prev.right = null;
                        System.out.print(curr.val + " ");
                        curr = curr.right;
                    }
                }
            }
        }

    public void morris(TreeNode<T> root, List<TreeNode<T>> order) {
        TreeNode<T> curr, prev;

        if (root == null)
            return;

        curr = root;
        while (curr != null) {
            if (curr.left == null) {
                order.add(curr);
                curr = curr.right;
            }
            else {
                /* Find the previous (prev) of curr */
                prev = curr.left;
                while (prev.right != null && prev.right != curr)
                    prev = prev.right;

                /* Make curr as right child of its prev */
                if (prev.right == null) {
                    prev.right = curr;
                    curr = curr.left;
                }

                /* fix the right child of prev*/

                else {
                    prev.right = null;
                    order.add(curr);
                    curr = curr.right;
                }
            }
        }
    }

    public void inOrder(TreeNode<T> root) {
        if (root == null) {
            return;
        }

        inOrder(root.left);
        System.out.println(root.val);
        inOrder(root.right);
    }

    public void inOrder(TreeNode<T> root, List<T> order) {
        if (root == null) {
            return;
        }

        inOrder(root.left, order);
        order.add(root.val);
        inOrder(root.right, order);
    }

    public void inOrderUpdate(TreeNode<T> root, List<T> order, int[] arr) {
        if (root == null) {
            return;
        }

        inOrderUpdate(root.left, order, arr);
        root.val = order.get(arr[0]);
        arr[0]++;
        inOrderUpdate(root.right, order, arr);
    }

    public void postOrder(TreeNode<T> root, int mode) {
        if (mode == 1) {
            postOrderRecursive(root);
        } else {
            postOrderIterative(root);
        }
    }

    public void postOrderRecursive(TreeNode<T> root) {
        if (root == null) {
            return;
        }

        inOrder(root.left);
        inOrder(root.right);
        System.out.println(root.val);
    }

    public void postOrderIterative(TreeNode<T> root) {
        Stack<Node> stack = new Stack<>();
        stack.push(new Node(root, false));
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node.treeNode == null) continue;

            System.out.println(node.treeNode.val);
            if (!node.visited) {
                if (node.treeNode.left == null && node.treeNode.right == null) {
                    System.out.println(node.treeNode.val);
                } else {
                    stack.push(new Node(root, true));
                    stack.push(new Node(root.right, false));
                    stack.push(new Node(root.left, false));
                }
            } else {
                System.out.println(node.treeNode.val);
            }
        }
    }

    public List<T> postOrderIterative_(TreeNode<T> root) {
        List<T> result = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(new Node(root, false));
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node.treeNode == null) continue;

            System.out.println(node.treeNode.val);
            if (!node.visited) {
                if (node.treeNode.left == null && node.treeNode.right == null) {
                    result.add(node.treeNode.val);
                } else {
                    stack.push(new Node(root, true));
                    stack.push(new Node(root.right, false));
                    stack.push(new Node(root.left, false));
                }
            } else {
                result.add(node.treeNode.val);
            }
        }
        return result;
    }

    private class Node {
        TreeNode<T> treeNode;
        boolean visited;

        public Node(TreeNode<T> treeNode, boolean visited) {
            this.treeNode = treeNode;
            this.visited = visited;
        }
    }
}
