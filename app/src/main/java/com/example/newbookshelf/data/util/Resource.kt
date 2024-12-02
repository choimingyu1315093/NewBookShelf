package com.example.newbookshelf.data.util

//이건 뭐 암기할 거 없이 그대로 사용하면 된다.
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
){
    class Success<T>(data: T): Resource<T>(data)
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
}