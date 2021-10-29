package com.bikcode.firebaseappinitialcourse

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bikcode.firebaseappinitialcourse.databinding.ActivityMainBinding
import com.bikcode.firebaseappinitialcourse.util.showToast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var dataReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        database = Firebase.database.reference
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()) {
                    val data = snapshot.getValue(String::class.java)
                    data?.let { setText("Firebase remote: $it") }
                } else {
                    setText("Route without data")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("An error has occurred")
            }
        }

        dataReference = database.child("initial_firebase").child("data")
        dataReference.addValueEventListener(listener)

        setListeners()

    }

    private fun setText(data: String) {
        _binding.tvData.text = data
    }

    private fun setListeners() {
        with(_binding) {
            btnSend.setOnClickListener {
                dataReference.setValue(tieData.text.toString()).addOnSuccessListener {
                    showToast("Send")
                }.addOnFailureListener {
                    showToast("Something happened")
                }.addOnCompleteListener {
                    showToast("Completed")
                }
            }

            btnSend.setOnLongClickListener {
                dataReference.removeValue()
                true
            }
        }
    }
}