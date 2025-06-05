package com.example.newbookshelf.data.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun EditText.textChange(): Flow<CharSequence?> = callbackFlow {
    val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            trySend(s)
        }
        override fun afterTextChanged(s: Editable?) = Unit
    }
    addTextChangedListener(watcher)
    awaitClose{ removeTextChangedListener(watcher)}
}