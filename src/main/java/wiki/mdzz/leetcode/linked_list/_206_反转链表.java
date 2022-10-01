package wiki.mdzz.leetcode.linked_list;

public class _206_反转链表 {
    public ListNode reverseList1(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = reverseList1(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public ListNode reverseList2(ListNode head) {
        ListNode current = head;
        ListNode previous = null;
        while (current != null) {
            ListNode tmp = new ListNode(current.value);
            tmp.next = previous;
            previous = tmp;
            current = current.next;
        }
        return previous;
    }

    public ListNode reverseList3(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = null;
        ListNode current;
        while (head != null) {
            current = head;
            head = head.next;
            current.next = newHead;
            newHead = current;
        }
        return newHead;
    }
}
