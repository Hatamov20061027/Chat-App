package com.example.chatapp10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp10.databinding.ActivitySignUpBinding
import com.example.chatapp10.models.UserEx
import com.example.chatapp10.objects.ObjectName
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    lateinit var binding:ActivitySignUpBinding
    lateinit var mAuth: FirebaseAuth
    lateinit var referense: DatabaseReference
    lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        firebaseDatabase = FirebaseDatabase.getInstance()
        referense = firebaseDatabase.getReference("users")

        binding.signupButton.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val name = binding.edtName.text.toString().trim()
            if (email.isNotEmpty()&&password.isNotEmpty()&&name.isNotEmpty()){

                signUp(email,password,name)

            }else{
                Toast.makeText(this, "Firstly enter your email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUp(email: String, password: String, name: String) {

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this@SignUpActivity){
                if (it.isSuccessful){
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    ObjectName.name = name
                    finish()
                }else{
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }

    }
    

}