package ltd.rust_lang.leetcode.linked_list;

public class _237_删除链表中的节点 {

    public void deleteNode(ListNode node) {
        ListNode next = node.next;
        node.value = next.value;
        node.next = next.next;
    }
}
