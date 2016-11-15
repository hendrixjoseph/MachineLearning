package edu.wright.hendrix11.cs7830.tools;

/**
 * Created by Joe on 11/7/2016.
 */
public class Order implements Comparable<Order> {
    private final int index;
    private double sortValue;

    public Order(int index) {
        this.index = index;
        sortValue = Math.random();
    }

    public int getIndex() {
        return index;
    }

    public void redoSortValue() {
        sortValue = Math.random();
    }

    @Override
    public int compareTo(Order o) {
        return Double.compare(sortValue, o.sortValue);
    }
}
