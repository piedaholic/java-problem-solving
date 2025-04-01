package com.piedaholic.algo.tree;

import com.piedaholic.algo.nodes.ListNode;
import com.piedaholic.algo.nodes.Pair;
import com.piedaholic.algo.nodes.TreeNode;
import java.util.*;

public class BinaryTree<T> {

  TreeNode<T> root;
  TreeNode<T> prev = null;
  TreeNode<Integer> firstElement = null;
  TreeNode<Integer> secondElement = null;
  // The reason for this initialization is to avoid null pointer exception in the first comparison
  // when prevElement has not been initialized
  TreeNode<Integer> prevElement = new TreeNode<>(Integer.MIN_VALUE);

  public static void main(String[] args) {
    BinaryTree<Integer> binaryTree = new BinaryTree<>();
    List<TreeNode<Integer>> result = binaryTree.generateTrees(3);
    assert !result.isEmpty();
  }

  /**
   * Given the root of a binary tree, flatten the tree into a "linked list":
   *
   * <p>The "linked list" should use the same TreeNode class where the right child pointer points to
   * the next node in the list and the left child pointer is always null.
   *
   * <p>The "linked list" should be in the same order as a pre-order traversal of the binary tree.
   */
  public void flattenToLinkedList() {
    flatten(this.root);
  }

  public void flattenToLinkedList(TreeNode<T> root) {
    flatten(root);
  }

  public void flatten(TreeNode<T> root) {
    if (root == null) return;
    flatten(root.right);
    flatten(root.left);
    root.right = prev;
    root.left = null;
    prev = root;
  }

  /**
   * @param n an integer
   * @return all the structurally unique binary search trees, which has exactly n nodes of unique
   *     values from 1 to n. Return the answer in any order.
   */
  public List<TreeNode<Integer>> generateTrees(int n) {
    // Map<Pair<Integer, Integer>, List<TreeNode<Integer>>> memo = new HashMap<>();
    // return allPossibleBST(1, n, memo);
    return allPossibleBST(1, n);
  }

  private List<TreeNode<Integer>> allPossibleBST(int start, int end) {
    List<TreeNode<Integer>> result = new ArrayList<>();

    if (start > end) {
      result.add(null);
      return result;
    }

    for (int i = start; i <= end; i++) {
      List<TreeNode<Integer>> lefts = allPossibleBST(start, i - 1);
      List<TreeNode<Integer>> rights = allPossibleBST(i + 1, end);

      for (TreeNode<Integer> left : lefts) {
        for (TreeNode<Integer> right : rights) {
          TreeNode<Integer> root = new TreeNode<>(i);
          root.left = left;
          root.right = right;
          result.add(root);
        }
      }
    }

    return result;
  }

  /** Map Implementation */
  public List<TreeNode<Integer>> allPossibleBST(
      int start, int end, Map<Pair<Integer, Integer>, List<TreeNode<Integer>>> memo) {
    List<TreeNode<Integer>> res = new ArrayList<>();
    if (start > end) {
      res.add(null);
      return res;
    }
    if (memo.containsKey(new Pair<>(start, end))) {
      return memo.get(new Pair<>(start, end));
    }
    // Iterate through all values from start to end to construct left and right subtree recursively.
    for (int i = start; i <= end; ++i) {
      List<TreeNode<Integer>> leftSubTrees = allPossibleBST(start, i - 1, memo);
      List<TreeNode<Integer>> rightSubTrees = allPossibleBST(i + 1, end, memo);

      // Loop through all left and right subtrees and connect them to ith root.
      for (TreeNode<Integer> left : leftSubTrees) {
        for (TreeNode<Integer> right : rightSubTrees) {
          TreeNode<Integer> root = new TreeNode<>(i, left, right);
          res.add(root);
        }
      }
    }
    memo.put(new Pair<>(start, end), res);
    return res;
  }

  /**
   * @param n an integer
   * @return the number of structurally unique binary search trees which has exactly n nodes of
   *     unique values from 1 to n.
   */
  private int dpBstCount(int n) {
    int[] result = new int[n + 1];
    result[0] = result[1] = 1;
    for (int i = 2; i <= n; i++) {
      result[i] = 0;
      for (int j = 1; j <= i; j++) {
        result[i] = result[i] + result[j - 1] * result[i - j];
      }
    }
    return result[n];
  }

  /**
   * Construct a BST from a sorted list.
   *
   * @param head of a singly linked list where elements are sorted in ascending order
   * @return root of a height-balanced binary search tree
   */
  public TreeNode<T> sortedListToBST(ListNode<T> head) {
    if (head == null) return null;

    return constructBst(head, null);
  }

  public TreeNode<T> constructBst(ListNode<T> head, ListNode<T> tail) {
    if (head == tail) return null;

    ListNode<T> slow = head;
    ListNode<T> fast = head;

    while (fast != tail && fast.next != tail) {
      fast = fast.next.next;
      slow = slow.next;
    }

    TreeNode<T> root = new TreeNode<>(slow.val);
    root.left = constructBst(head, slow);
    root.right = constructBst(slow.next, tail);

    return root;
  }

  /**
   * @param root the root of a binary tree
   * @param targetSum an integer
   * @return
   *     <p>All root-to-leaf paths where the sum of the node values in the path equals {@code
   *     targetSum}. Each path should be returned as a list of the node values, not node references.
   *     A root-to-leaf path is a path starting from the root and ending at any leaf node. A leaf is
   *     a node with no children.
   */
  public List<List<Integer>> pathSum(TreeNode<Integer> root, int targetSum) {
    List<List<Integer>> result = new ArrayList<>();
    pathSum(root, result, new ArrayList<>(), targetSum);
    return result;
  }

  private void pathSum(
      TreeNode<Integer> root, List<List<Integer>> result, List<Integer> order, int targetSum) {
    if (root == null) return;

    int residue = targetSum - root.val;

    if (residue == 0 && root.left == null && root.right == null) {
      List<Integer> tracker = new ArrayList<>(order);
      tracker.add(root.val);
      result.add(tracker);
    } else {
      List<Integer> tracker = new ArrayList<>(order);
      tracker.add(root.val);
      pathSum(root.left, result, new ArrayList<>(tracker), residue);
      pathSum(root.right, result, new ArrayList<>(tracker), residue);
    }
  }

  /**
   * A valid BST is defined as follows:
   *
   * <ul>
   *   The left subtree of a node contains only nodes with keys less than the node's key.
   * </ul>
   *
   * <ul>
   *   The right subtree of a node contains only nodes with keys greater than the node's key.
   * </ul>
   *
   * <ul>
   *   Both the left and right subtrees must also be binary search trees.
   * </ul>
   *
   * @param root the root of a binary tree
   * @param mode the algorithm to be used
   * @return determine if it is a valid binary search tree (BST)
   */
  public boolean isValidBST(TreeNode<Integer> root, Integer mode) {
    switch (mode) {
      case 0 -> {
        return isValidBst(root);
      }
      case 1 -> {
        return isValidBst(root, null, null);
      }
      case 2 -> {
        DepthFirstTraversal<Integer> traversal = new DepthFirstTraversal<>();
        List<Integer> list = new ArrayList<>();
        traversal.inOrder(root, list);
        return isSortedList(list);
      }
    }
    return false;
  }

  private boolean isValidBst(TreeNode<Integer> root) {
    if (root == null) return true;
    Stack<TreeNode<Integer>> stack = new Stack<>();
    TreeNode<Integer> pre = null;

    while (root != null || !stack.isEmpty()) {
      while (root != null) {
        stack.push(root);
        root = root.left;
      }

      root = stack.pop();
      if (pre != null && pre.val >= root.val) return false;
      pre = root;
      root = root.right;
    }
    return true;
  }

  private boolean isValidBst(TreeNode<Integer> node, Integer min, Integer max) {
    if (node == null) return true;
    if (min != null && node.val <= min) return false;
    if (max != null && node.val >= max) return false;
    return isValidBst(node.left, min, node.val) && isValidBst(node.right, node.val, max);
  }

  private boolean isSortedList(List<Integer> list) {
    for (int i = 0; i < list.size() - 1; i++) {
      if (list.get(i) >= list.get(i + 1)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Given two integer arrays with preorder and inorder traversal of a binary tree, construct the
   * binary tree.
   *
   * @param preorder is the preorder traversal of a binary tree
   * @param inorder is the inorder traversal of the same tree
   * @return the binary tree.
   */
  public TreeNode<Integer> buildTreePreIn(int[] preorder, int[] inorder) {
    return buildTreePreIn(0, 0, inorder.length - 1, preorder, inorder);
  }

  public TreeNode<Integer> buildTreePreIn(
      int preStart, int inStart, int inEnd, int[] preorder, int[] inorder) {
    if (preStart > preorder.length - 1 || inStart > inEnd) {
      return null;
    }
    TreeNode<Integer> root = new TreeNode<>(preorder[preStart]);
    int inIndex = 0; // Index of current root in inorder
    for (int i = inStart; i <= inEnd; i++) {
      if (inorder[i] == root.val) {
        inIndex = i;
      }
    }
    root.left = buildTreePreIn(preStart + 1, inStart, inIndex - 1, preorder, inorder);
    root.right =
        buildTreePreIn(preStart + inIndex - inStart + 1, inIndex + 1, inEnd, preorder, inorder);
    return root;
  }

  /**
   * Given two integer arrays {@code inorder} and {@code postorder} where {@code inorder} is the
   * inorder traversal of a binary tree and {@code postorder} is the postorder traversal of the same
   * tree, construct and return the binary tree.
   */
  public TreeNode<Integer> buildTreePostIn(int[] inorder, int[] postorder) {
    if (inorder == null || postorder == null || inorder.length != postorder.length) return null;
    HashMap<Integer, Integer> hm = new HashMap<>();
    for (int i = 0; i < inorder.length; ++i) hm.put(inorder[i], i);
    return buildTreePostIn(0, inorder.length - 1, postorder, 0, postorder.length - 1, hm);
  }

  private TreeNode<Integer> buildTreePostIn(
      int is, int ie, int[] postorder, int ps, int pe, HashMap<Integer, Integer> hm) {
    if (ps > pe || is > ie) return null;
    TreeNode<Integer> root = new TreeNode<>(postorder[pe]);
    int ri = hm.get(postorder[pe]);
    TreeNode<Integer> leftChild = buildTreePostIn(is, ri - 1, postorder, ps, ps + ri - is - 1, hm);
    TreeNode<Integer> rightChild = buildTreePostIn(ri + 1, ie, postorder, ps + ri - is, pe - 1, hm);
    root.left = leftChild;
    root.right = rightChild;
    return root;
  }

  /**
   * @param root of a binary search tree (BST), where the values of exactly two nodes of the tree
   *     were swapped by mistake.
   *     <p>Recover the tree without changing its structure.
   */
  public void recoverTree(TreeNode<Integer> root) {

    // In order traversal to find the two elements
    traverse(root);

    // Swap the values of the two nodes
    int temp = firstElement.val;
    firstElement.val = secondElement.val;
    secondElement.val = temp;
  }

  private void traverse(TreeNode<Integer> root) {

    if (root == null) return;

    traverse(root.left);

    // Start of "do some business",
    // If first element has not been found, assign it to prevElement (refer to 6 in the example
    // above)
    if (firstElement == null && prevElement.val >= root.val) {
      firstElement = prevElement;
    }

    // If first element is found, assign the second element to the root (refer to 2 in the example
    // above)
    if (firstElement != null && prevElement.val >= root.val) {
      secondElement = root;
    }
    prevElement = root;

    // End of "do some business"

    traverse(root.right);
  }

  /**
   * Given the {@code root} of a binary tree, return the bottom-up level order traversal of its
   * nodes' values. (i.e., from left to right, level by level from leaf to root).
   */
  public List<List<Integer>> levelOrderBottom(TreeNode<Integer> root) {
    Queue<TreeNode<Integer>> queue = new LinkedList<>();
    List<List<Integer>> wrapList = new LinkedList<>();

    if (root == null) return wrapList;

    queue.offer(root);
    while (!queue.isEmpty()) {
      int levelNum = queue.size();
      List<Integer> subList = new LinkedList<>();
      for (int i = 0; i < levelNum; i++) {
        if (queue.peek().left != null) queue.offer(queue.peek().left);
        if (queue.peek().right != null) queue.offer(queue.peek().right);
        subList.add(queue.poll().val);
      }
      wrapList.add(0, subList);
    }
    return wrapList;
  }

  /** BFS Implementation of bottom-up level order traversal. */
  public List<List<Integer>> levelOrderBottom_(TreeNode<Integer> root) {
    List<List<Integer>> wrapList = new LinkedList<>();
    levelMaker(wrapList, root, 0);
    return wrapList;
  }

  public void levelMaker(List<List<Integer>> list, TreeNode<Integer> root, int level) {
    if (root == null) return;
    if (level >= list.size()) {
      list.add(0, new LinkedList<>());
    }
    levelMaker(list, root.left, level + 1);
    levelMaker(list, root.right, level + 1);
    list.get(list.size() - level - 1).add(root.val);
  }

  public void recoverTree_(TreeNode<Integer> root) {
    List<TreeNode<Integer>> eNodes = new LinkedList<>(); // error nodes
    if (root == null) return;
    TreeNode<Integer> current = root;
    TreeNode<Integer> pre;
    TreeNode<Integer> previous = null;
    while (current != null) {

      if (current.left == null) {

        if (previous != null && previous.val > current.val) {
          eNodes.add(previous);
          eNodes.add(current);
        }
        previous = current;
        current = current.right;

      } else {
        pre = current.left;
        while (pre.right != null && pre.right.val != current.val) {
          pre = pre.right;
        }

        if (pre.right == null) {
          pre.right = current;
          current = current.left;
        } else {
          if (previous != null && previous.val > current.val) {
            eNodes.add(previous);
            eNodes.add(current);
          }

          pre.right = null;
          previous = current;
          current = current.right;
        }
      }
    }

    // this is redundant check
    // if(eNodes.size() == 0) return;

    if (eNodes.size() == 2) {
      pre = eNodes.get(0);
      current = eNodes.get(1);
    } else { // this case where eNodes.size()==4
      pre = eNodes.get(0);
      current = eNodes.get(3);
    }

    int temp = pre.val;
    pre.val = current.val;
    current.val = temp;
  }
}
