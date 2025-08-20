package com.example.re

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class FirebaseUIActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var otherOptionsButton: Button
    private lateinit var otherOptionsContainer: LinearLayout
    private lateinit var metaIcon: ImageView
    private lateinit var twitterIcon: ImageView
    private lateinit var googleIcon: ImageView

    companion object {
        private const val RC_SIGN_IN = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_uiactivity)
        auth = FirebaseAuth.getInstance()

        otherOptionsButton = findViewById(R.id.other_options_button)
        otherOptionsContainer = findViewById(R.id.other_options_container)
        metaIcon = findViewById(R.id.meta_icon)
        twitterIcon = findViewById(R.id.twitter_icon)
        googleIcon = findViewById(R.id.google_icon)

        otherOptionsButton.setOnClickListener {
            otherOptionsContainer.visibility = if (otherOptionsContainer.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        metaIcon.setOnClickListener {
            signInWithProvider(AuthUI.IdpConfig.FacebookBuilder().build())
        }

        twitterIcon.setOnClickListener {
            signInWithProvider(AuthUI.IdpConfig.TwitterBuilder().build())
        }

        googleIcon.setOnClickListener {
            signInWithProvider(
                AuthUI.IdpConfig.GoogleBuilder()
                    .setScopes(listOf("email"))
                    .build()
            )
        }

        findViewById<Button>(R.id.login_button).setOnClickListener {
            performLogin()
        }

        findViewById<Button>(R.id.create_account_button).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun signInWithProvider(provider: AuthUI.IdpConfig) {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(listOf(provider))
                .build(),
            RC_SIGN_IN
        )
    }

    private fun performLogin() {
        val email = findViewById<EditText>(R.id.email_field).text.toString()
        val password = findViewById<EditText>(R.id.password_field).text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) redirectToMain() else Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) redirectToMain() else Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun redirectToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}