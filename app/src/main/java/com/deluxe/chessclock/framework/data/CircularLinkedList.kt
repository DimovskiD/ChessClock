package com.deluxe.chessclock.framework.data

import java.util.*

class CircularLinkedList<T>(lst : List<T>) : LinkedList<T>(lst) {

    override fun pop(): T {
        val result = super.pop()
        add(result)
        return result
    }
}