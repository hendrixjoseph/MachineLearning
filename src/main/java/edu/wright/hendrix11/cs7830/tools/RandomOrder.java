package edu.wright.hendrix11.cs7830.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Joe on 11/7/2016.
 */
public class RandomOrder {
    private List<Order> order = new ArrayList<>();

    public RandomOrder(int count) {
        for (int i = 0; i < count; i++) {
            order.add(new Order(i));
        }
        shuffle();
    }

    public void sort() {
        Collections.sort(order);
    }

    public void shuffle() {
        order.forEach(Order::redoSortValue);
        sort();
    }

    public IntStream indexStream() {
        return order.stream().mapToInt(Order::getIndex);
    }

    public int size() {
        return order.size();
    }

    public int get(int index) {
        return order.get(index).getIndex();
    }
}
