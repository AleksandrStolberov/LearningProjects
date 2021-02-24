package com.skillbox.listsecond

interface ItemList {

    fun onItemCar(id: Long, model: String, maxSpeed: String, pictureLink: String, doorCount: String)

    fun onItemPlane(id: Long, model: String, maxSpeed: String, pictureLink: String, maxHeight: String)
}