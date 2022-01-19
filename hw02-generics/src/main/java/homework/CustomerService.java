package homework;


import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final NavigableMap<Customer, String> map =
            new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        if (map.isEmpty()) {
            return null;
        }
        // Создаем копию, чтобы избежать возможных модификаций
        Customer smallestKey = new Customer(map.firstKey());
        String smallestValue = map.get(smallestKey);
        return Map.entry(smallestKey, smallestValue);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> higherEntry = map.higherEntry(customer);
        if (higherEntry == null) {
            return null;
        }
        // Создаем копию, чтобы избежать возможных модификаций
        Customer higherKey = new Customer(higherEntry.getKey());
        String higherValue = higherEntry.getValue();
        return Map.entry(higherKey, higherValue);
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
