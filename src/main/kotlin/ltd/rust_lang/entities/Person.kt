package ltd.rust_lang.entities

class Person(private var age: Int? = 0, var name: String? = "") {
    override fun toString(): String {
        return "Person{" +
                " age=" + age +
                ", name='" + name + '\'' +
                '}'
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other.javaClass != javaClass) return false

        val person = other as Person
        return person.age === age && if (person.name == null) name == null else person.name == name
    }

    override fun hashCode(): Int {
        var hashCode = age.hashCode()
        hashCode = hashCode * 31 + if (name != null) name.hashCode() else 0
        return hashCode
    }
}
