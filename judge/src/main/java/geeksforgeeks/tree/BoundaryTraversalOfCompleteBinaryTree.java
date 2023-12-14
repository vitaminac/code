package geeksforgeeks.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// https://www.geeksforgeeks.org/iterative-boundary-traversal-of-complete-binary-tree/
public class BoundaryTraversalOfCompleteBinaryTree {
    class Solution {
        ArrayList<Integer> boundary(BoundaryTraversalOfBinaryTree.Node root) {
            final List<Integer> leftBoundaries = new ArrayList<>();
            final List<Integer> leaves = new ArrayList<>();
            final List<Integer> rightBoundaries = new ArrayList<>();
            List<BoundaryTraversalOfBinaryTree.Node> nextLevel = new ArrayList<>();
            nextLevel.add(root);
            while (!nextLevel.isEmpty()) {
                List<BoundaryTraversalOfBinaryTree.Node> currentLevel = nextLevel;
                nextLevel = new LinkedList<>();
                for (int i = 0; i < currentLevel.size(); i++) {
                    BoundaryTraversalOfBinaryTree.Node node = currentLevel.get(i);
                    // the first one as left boundary
                    if (i == 0) leftBoundaries.add(node.data);
                        // the right one as right boundary
                    else if (i == currentLevel.size() - 1) rightBoundaries.add(node.data);
                        // leaf
                    else if (node.left == null && node.right == null) leaves.add(node.data);
                    if (node.left != null) nextLevel.add(node.left);
                    if (node.right != null) nextLevel.add(node.right);
                }
            }
            final ArrayList<Integer> ans = new ArrayList<>();
            ans.addAll(leftBoundaries);
            ans.addAll(leaves);
            Collections.reverse(rightBoundaries);
            ans.addAll(rightBoundaries);
            return ans;
        }
    }
}
