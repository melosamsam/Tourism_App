package com.example.tourism_app

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        val database = Firebase.database.reference

        //Switching from register to login
        val switchToRegister = findViewById<Button>(R.id.registerButtonL)
        switchToRegister.setOnClickListener {
            // Start the new activity
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.loginButtonL)
        loginButton.setOnClickListener {
            val usernameEntered = findViewById<EditText>(R.id.usernameTextL).text.toString()
            val enteredPassword = findViewById<EditText>(R.id.passwordTextL).text.toString()
            val clientsRef = database.child("Client")
            clientsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var isUserFound = false
                    var pseudo = ""
                    var mail = ""
                    for (clientSnapshot in dataSnapshot.children) {
                        val mailFromDB = clientSnapshot.child("mail").getValue()
                        val passwordFromDB = clientSnapshot.child("passwd").getValue()
                        val usernameFromDB = clientSnapshot.child("pseudo").getValue()

                        val mailFromDBString = mailFromDB?.toString()
                        val passwordFromDBString = passwordFromDB?.toString()
                        val usernameFromDBString = usernameFromDB?.toString()

                        if ((usernameEntered == usernameFromDBString ||usernameEntered == mailFromDBString) && enteredPassword == passwordFromDBString) {
                            isUserFound = true
                            pseudo = usernameFromDB.toString()
                            mail = mailFromDB.toString()
                            break
                        }
                    }
                    if (isUserFound) {
                        Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Login, MainActivity::class.java)
                        val b = Bundle()
                        b.putString("pseudo",pseudo )
                        b.putString("mail",mail )
                        intent.putExtras(b)
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "Wrong password or username", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                    Toast.makeText(applicationContext, "ERROR DATA FETCH", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}