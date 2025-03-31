package com.example.soccer.repository;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
* manages collections of any T type
* */
public class Repository<T> implements Iterable<T>{

    private final List<T> list;

    public Repository(){
        list = new ArrayList<>();
    }

    public void addItem(T item){
        list.add(item);
    }

    public void removeItem(T item){
        list.remove(item);
    }

    public T retrieve(int index) { //https://github.com/JapskuaLUT/CT60A2412_Week_11_Example/blob/master/app/src/main/java/com/example/week11example1/container/InventoryContainer.java
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return null;
    }

    public List<T> getAllItems(){
        return new ArrayList<>(list);
    }

    public int size(){
        return list.size();
    }

    /*
    * filters items based on a predicate (a lambda function)
    * */
    public Repository<T> filter(Predicate<T> predicate){
        Repository<T> filteredRepository = new Repository<>();

        // Using stream and lambda to filter items
        List<T> filteredItems = list.stream()
                .filter(predicate)
                .collect(Collectors.toList());

        // Add filtered items to the new container
        filteredItems.forEach(filteredRepository::addItem);

        return filteredRepository;
    }

    //sort list
    public void processList(Consumer<T> processor){
        list.forEach(processor);
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    public RepositoryIterator getCustomIterator(){
        return new RepositoryIterator();
    }

    public class RepositoryIterator implements Iterator<T>{
        private int currentIndex = list.size() - 1;

        @Override
        public boolean hasNext() {
            return currentIndex >= 0;
        }

        @Override
        public T next() {
            return list.get(currentIndex--);
        }
    }
}
