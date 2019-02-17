package code.leetcode;

import java.util.Scanner;
import java.util.StringTokenizer;

public class MergeTwoSortedLists {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line1 = scanner.nextLine();
        String line2 = scanner.nextLine();

        StringTokenizer tk;
        ListNode current;

        tk = new StringTokenizer(line1);
        ListNode l1 = new ListNode(0);
        current = l1;
        while (tk.hasMoreTokens()) {
            current.next = new ListNode(Integer.parseInt(tk.nextToken()));
            current = current.next;
        }

        tk = new StringTokenizer(line2);
        ListNode l2 = new ListNode(0);
        current = l2;
        while (tk.hasMoreTokens()) {
            current.next = new ListNode(Integer.parseInt(tk.nextToken()));
            current = current.next;
        }

        ListNode result = new MergeTwoSortedLists().mergeTwoLists(l1.next, l2.next);
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}
