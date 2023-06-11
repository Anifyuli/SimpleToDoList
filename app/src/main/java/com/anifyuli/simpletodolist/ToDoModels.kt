package com.anifyuli.simpletodolist

data class ToDoModels (
    val todo: List<Data>
) {
    data class Data(val id:String?, val did: String?) : java.io.Serializable
}