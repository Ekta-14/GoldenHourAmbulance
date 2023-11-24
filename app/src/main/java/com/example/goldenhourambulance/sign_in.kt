package com.example.goldenhourambulance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast

class sign_in : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        //to set specific part of @dont_hav text to clickable
        val textView: TextView = findViewById(R.id.dont_hav)
        setClickableText(textView, R.string.dont_hav, "Sign up")
    }

    private fun setClickableText(textView: TextView, textResId: Int, clickableText: String) {
        val fullText = getString(textResId)

        val spannableString = SpannableString(fullText)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                // Handle the click action here (e.g., navigate to the sign-in screen)
                handleSignInClick()
               // Toast.makeText(this@sign_in,"clicked",Toast.LENGTH_LONG).show()
            }
        }
        spannableString.setSpan(clickableSpan,23,30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text=spannableString
        textView.movementMethod= LinkMovementMethod.getInstance()
    }
    private fun handleSignInClick() {
        val intent = Intent(this, signup::class.java)
        startActivity(intent)
    }
}