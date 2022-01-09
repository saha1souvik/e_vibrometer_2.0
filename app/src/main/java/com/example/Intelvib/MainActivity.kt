package com.example.Intelvib

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.os.StrictMode



class MainActivity : AppCompatActivity() {

    private lateinit var signIn: Button
    private lateinit var register: Button
    private lateinit var userName: EditText
    private lateinit var password:EditText

    private val validmailid = "admin"
    private val validpassword = "Password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        userName = findViewById(R.id.userName)
        password = findViewById(R.id.password)
        signIn = findViewById(R.id.signIn)
        register = findViewById(R.id.createAccount)
        signIn.setOnClickListener {
            val mailid = userName.text.toString()
            val passentry = password.text.toString()
           if ((mailid == validmailid) && (passentry == validpassword)) {
                val intent = Intent(this@MainActivity, EquipmentType::class.java)
                startActivity(intent);
            } else {
                Toast.makeText(this@MainActivity, "Incorrect Credentials", Toast.LENGTH_LONG).show()
            }


        }
    }


}