package com.skillbox.generics

fun main() {
val listEven = genericList<Number>(listOf(2,3,4,2.1f,2.2f,5,3.1f,6))
    println("Четные числа: $listEven")

    val queue = Queue<Number>(mutableListOf(1,2,3,4,6,8))
    queue.enqueue(5)
    println(queue.dequeue())
    println(queue.items)

    val filter = queue.filterItems { it.toInt()% 2 == 0 }
    println(filter)

}

fun <T: Number> genericList(list: List<T>): List<T>{
    println(list.filterIsInstance<Float>())
    println(list.filterIsInstance<Int>())
    return list.filter { it.toInt()% 2 == 0 }
}
