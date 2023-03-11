package com.mahmoudhabib.firebaserealtimeactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.mahmoudhabib.firebaserealtimeactivity.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val database = Firebase.database
    var messageId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myRef = database.reference

        binding.btnSend.setOnClickListener {

            val newMessageText = binding.etMessage.text.toString()
            val newMessageObject = hashMapOf(
                "id" to messageId,
                "content" to newMessageText,
                "time" to System.currentTimeMillis()
            )

            if (newMessageText.isNotEmpty()) {
                myRef.child("messages").child("$messageId").setValue(newMessageObject)
                messageId++
            }

        }

        myRef.child("messages").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue<List<Message>>()?.let {
                    val recyclerView = binding.rvAllMessages
                    recyclerView.adapter = MessagesAdapter(this@MainActivity, it)
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    messageId = it.last().id+1
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Failed to load messages", Toast.LENGTH_SHORT)
                    .show()
            }

        })


    }
}