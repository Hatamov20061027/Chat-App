package com.example.chatapp10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp10.databinding.ActivityVerificationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.ktx.Firebase

class VerificationActivity : AppCompatActivity() {
    lateinit var binding: ActivityVerificationBinding
    lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser!=null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.signupButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.continiueBtn.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (email.isNotEmpty()&&password.isNotEmpty()){

                logIn(email,password)

            }else{
                Toast.makeText(this, "Firstly enter your email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logIn(email: String, password: String) {
       mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
           if (it.isSuccessful){
               val intent = Intent(this,MainActivity::class.java)
               startActivity(intent)
               finish()
           }else{
               Toast.makeText(this, "Mistake or We don't have an account like this ", Toast.LENGTH_SHORT).show()
           }
       }
    }
}