package edu.wright.hendrix11.cs7830.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Joe on 11/7/2016.
 */
public class Ordering {
    private List<Order> order = new ArrayList<>();

    public Ordering(int count) {
        for (int i = 0; i < count; i++) {
            order.add(new Order(i));
        }
    }

    public void sort() {
        Collections.sort(order);
    }

    public void shuffle() {
        order.forEach(Order::redoSortValue);
        sort();
    }

    public int size() {
        return order.size();
    }

    public int getIndex(int index) {
        return order.get(index).getIndex();
    }
}
