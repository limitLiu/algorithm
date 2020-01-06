package ltd.rust_lang.leetcode.linked_list;

public class _203_移除链表元素 {
  public ListNode removeElements(ListNode head, int val) {
    ListNode temp = new ListNode(0);
    temp.next = head;
    ListNode current = temp;
    while (temp != null) {
      if (temp.next != null && temp.next.val == val) {
        temp.next = temp.next.next;
      } else {
        temp = temp.next;
      }
    }
    return current.next;
  }
}
