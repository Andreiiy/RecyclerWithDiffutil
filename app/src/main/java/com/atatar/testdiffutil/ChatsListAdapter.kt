package com.atatar.testdiffutil

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ChatsListAdapter(val context : Context) :
    RecyclerView.Adapter<ChatsListAdapter.ItemViewHolder>() {

    var currentList: MutableList<Chat> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: MutableList<Chat>) {

        val diffResult = DiffUtil.calculateDiff(ItemsDiffCallback(currentList, data.toMutableList()))
        diffResult.dispatchUpdatesTo(this)
        currentList.clear()
        currentList = mutableListOf<Chat>().apply { addAll(data.toMutableList()) }
        currentList
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_chat_users, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position, currentList[position],context)

    }



    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val userImage = itemView.findViewById<ImageView?>(R.id.user_image)
        val textName = itemView.findViewById<TextView?>(R.id.text_name)
        val countMessages = itemView.findViewById<TextView?>(R.id.count_messages)
        val textTypeMessage = itemView.findViewById<TextView?>(R.id.text_type_message)



        @SuppressLint("ResourceAsColor")
        fun bind(position: Int, chat: Chat?, context:Context) {
            // userImage?.invalidate()

                textName?.let { it.text = chat?.username }

                    Glide.with(context)
                        .load(chat?.picture)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(280,250)
                        .fitCenter()// ALL works here too
                        .into(userImage!!)
                    // userImage?.glide(chat?.picture)


                if (chat?.unreceivedMessages ?: 0 > 0) {
                    countMessages?.isVisible = true
                    countMessages?.text = chat?.unreceivedMessages.toString()
                } else
                    countMessages?.isVisible = false


                textTypeMessage?.isVisible = true


            }




        }
    }



class ChatDiffUtil : DiffUtil.ItemCallback<Chat>(){
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem.typeMessage == newItem.typeMessage
    }

}



class ItemsDiffCallback(
    private val oldList: List<Chat>,
    private val newList: List<Chat>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.username == newItem.username

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.picture == newItem.picture && oldItem.username == newItem.username

    }

}