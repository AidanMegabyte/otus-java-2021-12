package homework;


import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    // NavigableMap - отсортированная мапа с возможностью поиска одних элементов относительно других.
    // Сортировка производится по ключу, порядок задается компаратором.
    // При помощи ключа и компаратора также производится поиск элементов относительно указанного.
    private final NavigableMap<Customer, String> map =
            new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return copyEntry(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return copyEntry(map.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> copyEntry(Map.Entry<Customer, String> entry) {
        if (entry == null) {
            return null;
        }
        // Создаем копию, чтобы избежать возможных модификаций
        Customer key = new Customer(entry.getKey());
        String value = entry.getValue();
        return Map.entry(key, value);
    }
}
