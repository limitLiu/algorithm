package ltd.rust_lang;

import ltd.rust_lang.entities.Person;
import ltd.rust_lang.utils.queue.CircleDeque;
import ltd.rust_lang.utils.stack.IStack;
import ltd.rust_lang.utils.stack.Stack;
import ltd.rust_lang.utils.list.*;
import ltd.rust_lang.utils.queue.CircleQueue;
import ltd.rust_lang.utils.queue.Deque;

public class Main {

  public static void main(String[] args) {
    testQueue();
  }

  private static void testQueue() {
    Deque<Integer> deque = new Deque<>();
    deque.enQueueFront(11);
    deque.enQueueFront(22);
    deque.enQueueRear(33);
    deque.enQueueRear(44);
    while (!deque.isEmpty()) {
      System.out.println(deque.deQueueFront());
    }

    System.out.println("-----");
    CircleQueue<Integer> circleQueue = new CircleQueue<>();
    for (int i = 0; i < 10; ++i) {
      circleQueue.enQueue(i);
    }
    for (int i = 0; i < 5; ++i) {
      circleQueue.deQueue();
    }
    for (int i = 15; i < 24; ++i) {
      circleQueue.enQueue(i);
    }
    System.out.println(circleQueue);

    System.out.println("-----");
    var circleDeque = new CircleDeque<>();
    for (var i = 0; i < 10; ++i) {
      circleDeque.enQueueFront(i);
      circleDeque.enQueueRear(i + 100);
    }
    System.out.println(circleDeque);
  }

  static void testList() {
    MyList<Integer> array = new DynamicArray<>();
    array.add(11);
    array.add(12);
    array.insert(0, 2);
    array.set(1, 22);
    System.out.println(array);

    MyList<Person> persons = new DynamicArray<>();
    persons.add(new Person(11, "test"));
    System.out.println(persons);

    MyList<Integer> linked = new SinglyLinkList<>();
    linked.add(222);
    linked.add(111);
    linked.insert(0, 11);
    linked.remove(1);
    linked.set(1, 33);
    System.out.println(linked);

    MyList<Integer> linked2 = new SinglyLinkList2<>();
    linked2.add(1);
    linked2.add(2);
    linked2.add(3);
    linked2.insert(0, 11);
    linked2.remove(0);
    linked2.set(1, 33);
    linked2.set(0, 44);
    System.out.println(linked2);

    MyList<Integer> circleSinglyList = new CircleSinglyLinkList<>();
    circleSinglyList.add(11);
    circleSinglyList.add(22);
    circleSinglyList.add(33);
    circleSinglyList.add(44);
    circleSinglyList.insert(0, 55);
    System.out.println(circleSinglyList);
    CircleDoublyLinkList<Integer> circleDoublyLinkList = new CircleDoublyLinkList<>();
    for (int i = 0; i < 8; ++i) {
      circleDoublyLinkList.add(i);
    }
    circleDoublyLinkList.reset();
    while (!circleDoublyLinkList.isEmpty()) {
      circleDoublyLinkList.next();
      circleDoublyLinkList.next();
      circleDoublyLinkList.remove();
    }
  }

  static void testStack() {
    IStack<Integer> stack = new Stack<>();
    stack.push(11);
    stack.push(12);

    while (!stack.isEmpty()) {
      System.out.println(stack.pop());
    }
  }

}
