package ltd.rust_lang.leetcode.linked_list;

public class _206_反转链表 {
    public ListNode reverseList1(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = reverseList1(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode tail = null;
        ListNode tmp;
        while (head != null) {
            tmp = head;
            head = head.next;
            tmp.next = tail;
            tail = tmp;
        }
        return tail;
    }
}
