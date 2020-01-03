package ltd.rust_lang.entities;

public class Person {
    private int age;
    private String name;

    public Person(int age, String name) {
        setAge(age);
        setName(name);
    }

    public Person() {
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{" +
                " age=" + this.getAge() +
                ", name='" + this.getName() + '\'' +
                '}';
    }
}
