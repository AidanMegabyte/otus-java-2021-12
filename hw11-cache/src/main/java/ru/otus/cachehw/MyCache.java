package ru.otus.cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K, V> cache = new WeakHashMap<>();

    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notifyListeners(key, value, HwAction.PUT.name());
    }

    @Override
    public void remove(K key) {
        var value = cache.get(key);
        cache.remove(key);
        notifyListeners(key, value, HwAction.REMOVE.name());
    }

    @Override
    public V get(K key) {
        var result = cache.get(key);
        notifyListeners(key, result, HwAction.GET.name());
        return result;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(K key, V value, String action) {
        listeners.forEach(listener -> listener.notify(key, value, action));
    }
}
