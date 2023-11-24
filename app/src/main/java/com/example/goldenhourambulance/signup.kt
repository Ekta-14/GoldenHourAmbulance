package com.example.goldenhourambulance

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.method.LinkMovementMethod.*
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val textView: TextView = findViewById(R.id.already_hav)
        setClickableText(textView, R.string.already_hav, "Sign in")
    }

    private fun setClickableText(textView: TextView, textResId: Int, clickableText: String) {
        val fullText = getString(textResId)

        val spannableString = SpannableString(fullText)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                // Handle the click action here (e.g., navigate to the sign-in screen)
                handleSignInClick()
               //Toast.makeText(this@signup,"clicked",Toast.LENGTH_LONG).show()
            }
        }
        spannableString.setSpan(clickableSpan,26,33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text=spannableString
        textView.movementMethod=LinkMovementMethod.getInstance()
    }
    private fun handleSignInClick() {
        val intent = Intent(this, sign_in::class.java)
        startActivity(intent)
    }
}