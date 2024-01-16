package com.example.send2ai

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TextProcessActivity : AppCompatActivity() {

    private val sharedPrefFile = "com.send2ai.preferences"
    private lateinit var defaultTemplate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the selected text from the intent
        val selectedText = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()

        defaultTemplate = getString(R.string.default_template)

        // Retrieve the template text from SharedPreferences
        val sharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val template = sharedPreferences.getString("templateText", defaultTemplate) ?: defaultTemplate

        // Replace the placeholder with the selected text
        val processedText = template.replace("{text}", selectedText)

        // Copy the processed text to the clipboard
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", processedText)
        clipboard.setPrimaryClip(clip)

        // Optional: Provide feedback to the user or close the activity
        Toast.makeText(this, "Text processed and copied to clipboard", Toast.LENGTH_SHORT).show()

        // Finish the activity
        finish()
    }

}



