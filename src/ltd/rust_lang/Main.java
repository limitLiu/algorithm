package ltd.rust_lang;

import ltd.rust_lang.entities.Person;
import ltd.rust_lang.utils.*;

public class Main {

    public static void main(String[] args) {
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
}
