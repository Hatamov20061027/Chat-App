package com.example.chatapp10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapp10.adapters.UserAdapter
import com.example.chatapp10.databinding.ActivityMainBinding
import com.example.chatapp10.models.UserEx
import com.example.chatapp10.objects.ObjectName
import com.example.chatapp10.objects.ObjectUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val TAG = "RealActivity"
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var referense: DatabaseReference //pathlar bilan ishlashga yordam beradi

    lateinit var userAdapter: UserAdapter
    var list = ArrayList<UserEx>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        firebaseDatabase = FirebaseDatabase.getInstance()
        referense = firebaseDatabase.getReference("users")

        val email = currentUser?.email
        val displayName = ObjectName.name
        val photoUri = currentUser?.photoUrl
        val uid = currentUser?.uid

        val user = UserEx(email, displayName, photoUri.toString(), uid)
        referense.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                val filterList = arrayListOf<UserEx>()
                val children = snapshot.children
                for (child in children) {
                    val value = child.getValue(UserEx::class.java)
                    if (value != null && uid != value.uid) {
                        list.add(value)
                    }
                    if (value != null && uid == value.uid) {
                        filterList.add(value)
                    }
                }
                if (filterList.isEmpty()) {
                    referense.child(uid!!).setValue(user)
                }

                userAdapter = UserAdapter(list, object : UserAdapter.OnItemCLickListener {
                    override fun onCLick(userEx: UserEx) {
                        val intent = Intent(this@MainActivity, MessegingActivity::class.java)
                        ObjectUser.name = userEx.displayName
                        ObjectUser.userId = userEx.uid

                        startActivity(intent)
                    }
                })

                binding.rvUser.adapter = userAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}