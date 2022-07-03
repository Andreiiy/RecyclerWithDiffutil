package com.atatar.testdiffutil

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MessagesViewModel() : ViewModel() {

    var listChats: MutableLiveData<MutableList<Chat>> = MainActivity.constListChats

}