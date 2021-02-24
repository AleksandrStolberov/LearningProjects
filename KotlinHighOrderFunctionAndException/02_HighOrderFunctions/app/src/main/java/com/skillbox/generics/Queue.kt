package com.skillbox.generics

class Queue<T>(list:MutableList<T>){
    var items = list


    fun enqueue(item: T){
        items.add(item)
    }

    fun dequeue(): T?{
        return if (items.isEmpty()){
            null
        } else {
            items.removeAt(0)
        }
    }

    fun filterItems(predicate: (T) -> Boolean): List<T> {
        return items.filter(predicate)
    }


}