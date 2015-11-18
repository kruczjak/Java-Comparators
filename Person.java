package com.company;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * Created by kruczjak on 13.04.14.
 */
public class Person implements Comparable<Person>, Externalizable {
    private String name;
    private String surname;
    private int age;
    private String city;

    public Person(String name, String surname, int age, String city) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.city = city;
    }

    @Override
    public int compareTo(Person person) {
        int compareName = surname.compareTo(person.surname);

        if (compareName==0)
            return name.compareTo(person.name);
        else    {
            return compareName;
        }
    }

    public static Comparator<Person> getComparator(PersonComparator comparator) throws NoSuchComparatorException {
        switch (comparator) {
            case SURNAME:
                return new ComparatorSurname();
            case AGE:
                return new ComparatorAge();
            case CITY:
                return new ComparatorCity();
            default:
                throw new NoSuchComparatorException();
        }
    }

    @Override
    public String toString() {
        return surname + ' ' + name + ' ' + age + ' ' + city;
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeObject(name);
        objectOutput.writeObject(surname);
        objectOutput.write(age);
        objectOutput.writeObject(city);
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        this.name = (String) objectInput.readObject();
        this.surname = (String) objectInput.readObject();
        this.age = objectInput.read();
        this.city = (String) objectInput.readObject();
    }

    private static class ComparatorCity implements Comparator<Person> {

        @Override
        public int compare(Person person, Person person2) {
            return person.city.compareTo(person2.city);
        }
    }

    private static class ComparatorAge implements Comparator<Person>   {

        @Override
        public int compare(Person person, Person person2) {
            return person.age - person2.age;
        }
    }

    private static class ComparatorSurname implements Comparator<Person>   {

        @Override
        public int compare(Person person, Person person2) {
            return person.compareTo(person2);
        }
    }

    public static Comparator<Person> getCity() {
        return new ComparatorCity();
    }
    public static Comparator<Person> getAge() {
        return new ComparatorAge();
    }
    public static Comparator<Person> getSurname() {
        return new ComparatorSurname();
    }

    public static Comparator<Person> getComparatorReflective(String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = Person.class.getMethod("get"+name);
        return (Comparator<Person>) m.invoke(null);
    }
}
