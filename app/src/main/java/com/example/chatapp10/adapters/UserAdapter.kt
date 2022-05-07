package com.example.chatapp10.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp10.databinding.ItemUserBinding
import com.example.chatapp10.models.UserEx
import com.squareup.picasso.Picasso

class UserAdapter(val list: List<UserEx>, var onItemCLickListener: OnItemCLickListener) :
    RecyclerView.Adapter<UserAdapter.Vh>(){
    inner class Vh(var itemRv: ItemUserBinding): RecyclerView.ViewHolder(itemRv.root){

        fun onBind(userEx: UserEx, position: Int){
           itemRv.itemDisplayName.text = userEx.displayName
//            if (userEx.photoUrl!= null) {
//                Picasso.get().load(userEx.photoUrl).into(itemRv.itemImage)
//            }
            itemRv.root.setOnClickListener {
                onItemCLickListener.onCLick(userEx)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemCLickListener{
        fun onCLick(userEx: UserEx)
    }
}