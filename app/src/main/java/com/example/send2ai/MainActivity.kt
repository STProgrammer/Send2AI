package com.example.send2ai

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged

class MainActivity : AppCompatActivity() {

    private lateinit var editTextInput: EditText
    private lateinit var textViewCopiedText: TextView
    private lateinit var buttonSettings: Button
    private lateinit var defaultTemplate: String
    private val sharedPrefFile = "com.send2ai.preferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        editTextInput = findViewById(R.id.editTextInput)
        textViewCopiedText = findViewById(R.id.textViewCopiedText)
        buttonSettings = findViewById(R.id.buttonSettings)
        defaultTemplate = getString(R.string.default_template)

        // Load the template from SharedPreferences
        val sharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val template = sharedPreferences.getString("templateText", defaultTemplate)
            ?: defaultTemplate

        // Add listener to EditText to handle text changes
        editTextInput.doAfterTextChanged { text ->
            val processedText = template.replace("{text}", text.toString())
            copyToClipboard(processedText)
            textViewCopiedText.text = processedText
        }

        // Button to open SettingsActivity
        buttonSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
    }

}