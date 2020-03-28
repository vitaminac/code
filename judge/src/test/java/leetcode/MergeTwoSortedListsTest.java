package leetcode;

import java.util.Scanner;
import java.util.StringTokenizer;

public class MergeTwoSortedListsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line1 = scanner.nextLine();
        String line2 = scanner.nextLine();

        StringTokenizer tk;
        MergeTwoSortedLists.ListNode current;

        tk = new StringTokenizer(line1);
        MergeTwoSortedLists.ListNode l1 = new MergeTwoSortedLists.ListNode(0);
        current = l1;
        while (tk.hasMoreTokens()) {
            current.next = new MergeTwoSortedLists.ListNode(Integer.parseInt(tk.nextToken()));
            current = current.next;
        }

        tk = new StringTokenizer(line2);
        MergeTwoSortedLists.ListNode l2 = new MergeTwoSortedLists.ListNode(0);
        current = l2;
        while (tk.hasMoreTokens()) {
            current.next = new MergeTwoSortedLists.ListNode(Integer.parseInt(tk.nextToken()));
            current = current.next;
        }

        MergeTwoSortedLists.ListNode result = new MergeTwoSortedLists().mergeTwoLists(l1.next, l2.next);
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}