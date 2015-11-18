package com.company;

import java.util.*;

/**
 * Created by kruczjak on 28.04.14.
 */
public class CompositeComparator {
    private List<Comparator<Person>> comparators = new LinkedList<Comparator<Person>>();

    public void add(Comparator<Person> comparator)  {
        comparators.add(0,comparator);
    }

    public void sort(List<Person> people) {
        for (Comparator<Person> comparator : comparators)   {
            Collections.sort(people, comparator);
        }
    }
}
