package code.leetcodecn;

import java.util.ArrayDeque;
import java.util.Deque;

public class Interview06PrintLinkedListInReverseOrder {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public int[] reversePrint(ListNode head) {
        Deque<Integer> stack = new ArrayDeque<>();
        while (head != null) {
            stack.add(head.val);
            head = head.next;
        }
        int[] result = new int[stack.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = stack.removeLast();
        }
        return result;
    }
}
