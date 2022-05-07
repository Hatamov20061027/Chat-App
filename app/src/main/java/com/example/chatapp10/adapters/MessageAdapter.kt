package com.example.chatapp10.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp10.databinding.ItemFromBinding
import com.example.chatapp10.databinding.ItemToBinding
import com.example.chatapp10.models.Message

class MessageAdapter (val list: List<Message>, var uid:String) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class FromVh(var itemRv: ItemFromBinding): RecyclerView.ViewHolder(itemRv.root){

        fun onBind(message: Message){
            itemRv.messageTv.text = message.message
//            itemRv.dateTv.text = message.date
        }
    }

    inner class ToVh(var itemRv: ItemToBinding): RecyclerView.ViewHolder(itemRv.root){
        fun onBind(message: Message) {
            itemRv.messageTv.text = message.message
//            itemRv.dateTv.text = message.date
//        }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1){
            return FromVh(ItemFromBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }else{
            return ToVh(ItemToBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == 1){
            val fromVh = holder as FromVh
            fromVh.onBind(list[position])
        }else{
            val toVh = holder as ToVh
            toVh.onBind(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].fromUid == uid){
            return 1
        }else{
            return 2
        }
    }

    override fun getItemCount(): Int = list.size
}