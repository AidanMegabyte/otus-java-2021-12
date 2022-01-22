package homework;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    // Deque - двусторонняя очередь.
    // Добавление элементов производится в начало.
    // Удаление элементов производится тоже с начала.
    // По-хорошему, нужен стек, но класс Stack считается проблемным и устаревшим,
    // и вместо него рекомендуют использовать реализации Deque.
    private final Deque<Customer> deque = new ArrayDeque<>();

    public void add(Customer customer) {
        deque.addFirst(customer);
    }

    public Customer take() {
        return deque.pollFirst();
    }
}
