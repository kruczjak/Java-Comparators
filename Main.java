package com.company;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            addAndSort();
        } catch (NoSuchComparatorException e) {
            e.printStackTrace();
        }
    }

    private static void addAndSort() throws NoSuchComparatorException {
        List<Person> people = new ArrayList<Person>();

        people.add(new Person("Jakub", "Kruczek", 20, "Kraków"));
        people.add(new Person("Jan", "Kowalski", 40, "Warszawa"));
        people.add(new Person("Anna", "Nowak", 21, "Rzeszów"));
        people.add(new Person("Wojciech", "Prawy", 25, "Lublin"));
        people.add(new Person("Damian", "Zły", 18, "Radom"));
        people.add(new Person("Daniel", "Zły", 30, "Radom"));
        people.add(new Person("Daniel", "Żre", 15, "Radom"));

        System.out.println("Nieposortowane: ");
        for(Person person : people) {
            System.out.println(person);
        }

        Collections.sort(people);

        System.out.println("\nPosortowane nazwisko i imie: ");
        for(Person person : people) {
            System.out.println(person);
        }

        Collections.sort(people, Person.getComparator(PersonComparator.AGE));

        System.out.println("\nPosortowane po wieku: ");
        for(Person person : people) {
            System.out.println(person);
        }

        Collections.sort(people, Person.getComparator(PersonComparator.CITY));

        System.out.println("\nPosortowane po miescie: ");
        for(Person person : people) {
            System.out.println(person);
        }

        try {
            Collections.sort(people, Person.getComparatorReflective("Surname"));
            System.out.println("\nPosortowane po nazwiskach przez reflective: ");
            for(Person person : people) {
                System.out.println(person);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        CompositeComparator compositeComparator = new CompositeComparator();
        compositeComparator.add(Person.getComparator(PersonComparator.CITY));
        compositeComparator.add(Person.getComparator(PersonComparator.AGE));
        compositeComparator.sort(people);
        System.out.println("\nPosortowane po mieście (najważniejsze) potem wieku: ");
        for(Person person : people) {
            System.out.println(person);
        }
    }
}
