package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

fun validatePhone(phone_num: String): Boolean {
    if (phone_num.substring(0, 2) != "05" || phone_num[2] == '7')
        return false
    return phone_num.length == 10
}

fun pushPhone(phone_num: String) {
    val database =
        Firebase.database("https://smartcart-7d3de-default-rtdb.europe-west1.firebasedatabase.app/")
    val phoneList = database.getReference("phone_list")
    val query = phoneList.orderByValue().equalTo(phone_num)
    query.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (!snapshot.exists()) {
                phoneList.push().setValue(phone_num)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            // Do nothing
        }
    })
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onButtonClick(view: View) {
        val phone_num = findViewById<EditText>(R.id.editTextNumber).text.toString()
        if (validatePhone(phone_num))
            pushPhone(phone_num)
        else {
            //PHONE NOT VALID POP UP
        }
        startActivity(Intent(this, this::class.java))
    }
}