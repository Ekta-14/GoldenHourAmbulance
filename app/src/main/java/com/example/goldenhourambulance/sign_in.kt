package com.example.goldenhourambulance

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class sign_in : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        val button_signin=findViewById<Button>(R.id.btn_signin)


        //to go to sign up page
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

    fun passCheck(view: View) {

        var usermail=findViewById<EditText>(R.id.et_mail).text.toString()
        var userpassword=findViewById<EditText>(R.id.et_pass).text.toString()

//

        if(usermail.equals("ekta.2024it1166@kiet.edu") && userpassword.equals("AbhinavEkta"))
        {
            val intent=Intent(this,mapsActivity::class.java)
            startActivity(intent)
        }
        else if(usermail.equals("") || userpassword.equals(""))
           Toast.makeText(this,"Fill the details",Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this,"Email or password incorrect",Toast.LENGTH_LONG).show()
    }
}