package com.example.chatapp10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.chatapp10.adapters.MessageAdapter
import com.example.chatapp10.databinding.ActivityMessegingBinding
import com.example.chatapp10.models.UserEx
import com.example.chatapp10.objects.ObjectUser
import com.example.firebaserealtimedatabase.Retrofit.ApiClient
import com.example.firebaserealtimedatabase.Retrofit.ApiService
import com.example.firebaserealtimedatabase.Retrofit.Model.Data
import com.example.firebaserealtimedatabase.Retrofit.Model.MyResponce
import com.example.firebaserealtimedatabase.Retrofit.Model.Sender
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.R
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import com.example.chatapp10.models.Message as Message

class MessegingActivity : AppCompatActivity() {
    lateinit var  binding:ActivityMessegingBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var referenceUser: DatabaseReference
    lateinit var messageAdapter:MessageAdapter
    lateinit var userId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMessegingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("messages")
        referenceUser = firebaseDatabase.getReference("users")

        userId = ObjectUser.userId.toString()
        val userName = ObjectUser.name
        binding.txtDisplayName.text = userName




        binding.imagePlus.setOnClickListener {
            val m = binding.edtMessage.text.toString().trim()

            if (m!=""){
                val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
                val date = simpleDateFormat.format(Date())

                val message = Message( m, date,firebaseAuth.currentUser?.uid, userId)
                val key = reference.push().key

                reference.child(key!!).setValue(message)
                binding.edtMessage.text.clear()
                Toast.makeText(this, "Sent $m to ${userName}", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Avval xabar yozing...", Toast.LENGTH_SHORT).show()
            }
        }

        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val fromUid = firebaseAuth.currentUser?.uid
                val toUid = userId
                val list = arrayListOf<Message>()
                val children = snapshot.children
                for (child in children) {
                    val value = child.getValue(Message::class.java)
                    if (value!=null && ((value.fromUid == fromUid && value.toUid == toUid) || (value.fromUid==toUid && value.toUid==fromUid))){
                        list.add(value!!)
                    }
                }
                messageAdapter = MessageAdapter(list, fromUid!!)
                binding.rvMessage.adapter = messageAdapter
                binding.rvMessage.scrollToPosition(list.size-1)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        referenceUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                for (child in children) {
                    val value = child.getValue(UserEx::class.java)
                    if (value!=null && value.uid == userId){

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}