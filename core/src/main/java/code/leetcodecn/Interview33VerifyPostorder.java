package code.leetcodecn;

public class Interview33VerifyPostorder {
    private boolean verifyPostorder(int start, int end, int[] postorder) {
        if (start >= end) return true;
        int root = postorder[end];
        int right = start;
        while (right < end && postorder[right] < root) right += 1;
        for (int i = right; i < end; i++) if (postorder[i] < root) return false;
        return this.verifyPostorder(start, right - 1, postorder) && this.verifyPostorder(right, end - 1, postorder);
    }

    public boolean verifyPostorder(int[] postorder) {
        return verifyPostorder(0, postorder.length - 1, postorder);
    }
}
