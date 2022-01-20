package homework;

import java.util.Stack;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    // Стек - "первым пришел, последним ушел".
    // Добавление элементов производится в конец.
    // Удаление элементов производится тоже с конца.
    private final Stack<Customer> stack = new Stack<>();

    public void add(Customer customer) {
        stack.push(customer);
    }

    public Customer take() {
        // Во избежание исключения проверяем, что стек не пустой
        return stack.empty() ? null : stack.pop();
    }
}
