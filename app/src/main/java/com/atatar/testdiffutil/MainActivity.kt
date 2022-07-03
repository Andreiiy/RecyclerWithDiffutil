package com.atatar.testdiffutil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



private var viewModel: MessagesViewModel? = null

class MainActivity : AppCompatActivity() {
    private var recyclerChatUsers: RecyclerView? = null
    private var adapterChats: ChatsListAdapter? = null

    private var buttonAdd: TextView? = null
    private var buttonRemove: TextView? = null
    private var buttonUpdate: TextView? = null

    companion object {
        var  constListChats: MutableLiveData<MutableList<Chat>> = MutableLiveData<MutableList<Chat>>()

    }
    private var list = mutableListOf<Chat>(
        Chat(1,1,"message 1",1,"Andrei1","https://image.shutterstock.com/image-photo/young-handsome-man-beard-wearing-260nw-1768126784.jpg",1,1),
        Chat(2,2,"message 2",1,"Andrei2","https://image.shutterstock.com/image-photo/young-handsome-man-beard-wearing-260nw-1768126784.jpg",2,2),
        Chat(3,3,"message 2",1,"Andrei3","https://image.shutterstock.com/image-photo/young-handsome-man-beard-wearing-260nw-1768126784.jpg",3,3),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MessagesViewModel::class.java)
        recyclerChatUsers = findViewById(R.id.list_chat_users)
        buttonAdd = findViewById(R.id.button_add)
        buttonRemove = findViewById(R.id.button_remove)
        buttonUpdate = findViewById(R.id.button_update)

        buttonAdd?.setOnClickListener{
            var newList = constListChats.value?.toMutableList()
            newList?.add(Chat(4,4,"message 2",1,"Andrei4","https://image.shutterstock.com/image-photo/young-handsome-man-beard-wearing-260nw-1768126784.jpg",4,4),)
            constListChats.postValue(newList?.toMutableList())
        }
        buttonRemove?.setOnClickListener{
            var newList = constListChats.value?.toMutableList()
            newList?.removeFirst()
            constListChats.postValue(newList?.toMutableList())
        }
        buttonUpdate?.setOnClickListener{
            var newList = constListChats.value?.toMutableList()
            newList?.first()?.username = "Andrei Updated"
            newList?.first()?.picture = "https://image.shutterstock.com/image-photo/close-head-shot-handsome-commercial-260nw-1584061174.jpg"
            constListChats.postValue(newList?.toMutableList())
        }




        recyclerChatUsers?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapterChats = ChatsListAdapter (context)
            adapter = adapterChats
        }

        adapterChats?.submitList( mutableListOf())




        viewModel?.listChats?.observe({ lifecycle }) { list ->
           adapterChats?.submitList(list.toMutableList())
        }





        constListChats.postValue(list.toMutableList())
    }



}