package org.example.aggregation;

import java.util.Set;

public class PersonAggregate {
    private String key;
    private Set<String> values;
    private int count;

    public PersonAggregate(String key, Set<String> values, int count) {
        this.key = key;
        this.values = values;
        this.count = count;
    }

    public PersonAggregate updatePerson(String key, String value) {
        var newCount = this.count + 1;
        values.add(value);
        return new PersonAggregate(key, values, newCount);
    }
}
