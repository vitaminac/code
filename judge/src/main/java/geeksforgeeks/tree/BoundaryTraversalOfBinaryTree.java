package geeksforgeeks.tree;


import java.util.*;

// https://www.geeksforgeeks.org/boundary-traversal-of-binary-tree/
// https://www.geeksforgeeks.org/problems/boundary-traversal-of-binary-tree/1
public class BoundaryTraversalOfBinaryTree {
    class Node {
        int data;
        Node left, right;

        public Node(int d) {
            data = d;
            left = right = null;
        }
    }

    class Solution {
        ArrayList<Integer> boundary(Node root) {
            final ArrayList<Integer> ans = new ArrayList<>();
            ans.add(root.data);
            if (root.left != null) traverseLeftBoundaries(root.left, ans);
            if (root.right != null) traverseRightBoundaries(root.right, ans);
            return ans;
        }

        private void traverseLeftBoundaries(final Node node, final List<Integer> ans) {
            if (node.left == null && node.right == null) {
                // leaf
                ans.add(node.data);
            } else {
                ans.add(node.data);
                if (node.left != null) {
                    traverseLeftBoundaries(node.left, ans);
                    if (node.right != null) traverseLeaves(node.right, ans);
                } else {
                    traverseLeftBoundaries(node.right, ans);
                }
            }
        }

        private void traverseRightBoundaries(final Node node, final List<Integer> ans) {
            if (node.left == null && node.right == null) {
                // leaf
                ans.add(node.data);
            } else {
                if (node.right != null) {
                    if (node.left != null) traverseLeaves(node.left, ans);
                    traverseRightBoundaries(node.right, ans);
                } else {
                    traverseRightBoundaries(node.left, ans);
                }
                ans.add(node.data);
            }
        }

        private void traverseLeaves(final Node node, final List<Integer> ans) {
            if (node.left == null && node.right == null) {
                ans.add(node.data);
            } else {
                if (node.left != null) traverseLeaves(node.left, ans);
                if (node.right != null) traverseLeaves(node.right, ans);
            }
        }
    }

    class IterativeSolution {
        ArrayList<Integer> boundary(Node root) {
            final ArrayList<Integer> ans = new ArrayList<>();
            // root
            ans.add(root.data);
            // traverse left-most
            if (root.left != null) {
                Node leftMost = root.left;
                while (leftMost.left != null || leftMost.right != null) {
                    ans.add(leftMost.data);
                    if (leftMost.left != null) {
                        leftMost = leftMost.left;
                    } else {
                        leftMost = leftMost.right;
                    }
                }
            }
            // traverse leaves
            final Deque<Node> stack = new LinkedList<>();
            if (root.right != null) stack.push(root.right);
            if (root.left != null) stack.push(root.left);
            while (!stack.isEmpty()) {
                Node current = stack.pop();
                if (current.left == null && current.right == null) {
                    ans.add(current.data);
                } else {
                    if (current.right != null) stack.push(current.right);
                    if (current.left != null) stack.push(current.left);
                }
            }
            // traverse right-most
            if (root.right != null) {
                Node rightMost = root.right;
                while (rightMost.left != null || rightMost.right != null) {
                    stack.push(rightMost);
                    if (rightMost.right != null) {
                        rightMost = rightMost.right;
                    } else {
                        rightMost = rightMost.left;
                    }
                }
            }
            while (!stack.isEmpty()) ans.add(stack.pop().data);
            // finally
            return ans;
        }
    }

    class IterativeOneRunSolution {
        ArrayList<Integer> boundary(Node root) {
            final ArrayList<Integer> ans = new ArrayList<>();
            final Deque<Node> stack = new LinkedList<>();
            // root
            ans.add(root.data);
            // traverse left-most
            if (root.left != null) {
                Node current = root.left;
                while (current.left != null || current.right != null) {
                    ans.add(current.data);
                    if (current.left != null) {
                        if (current.right != null) stack.push(current.right);
                        current = current.left;
                    } else {
                        current = current.right;
                    }
                }
                stack.push(current);
            }
            // traverse left subtree leaves
            while (!stack.isEmpty()) {
                Node current = stack.pop();
                if (current.left == null && current.right == null) {
                    ans.add(current.data);
                } else {
                    if (current.right != null) stack.push(current.right);
                    if (current.left != null) stack.push(current.left);
                }
            }
            // traverse right subtree leaves
            if (root.right != null) {
                Node lastRightMost = root.right;
                while (lastRightMost.left != null || lastRightMost.right != null) {
                    stack.push(lastRightMost);
                    if (lastRightMost.right != null) {
                        if (lastRightMost.left != null) stack.push(lastRightMost.left);
                        while (stack.peek() != lastRightMost) {
                            Node current = stack.pop();
                            if (current.left == null && current.right == null) {
                                ans.add(current.data);
                            } else {
                                if (current.right != null) stack.push(current.right);
                                if (current.left != null) stack.push(current.left);
                            }
                        }
                        lastRightMost = lastRightMost.right;
                    } else {
                        lastRightMost = lastRightMost.left;
                    }
                }
                // leaf
                ans.add(lastRightMost.data);
            }
            // traverse right-most
            while (!stack.isEmpty()) ans.add(stack.pop().data);
            // finally
            return ans;
        }
    }
}
