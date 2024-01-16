package com.example.send2ai

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SettingsActivity : AppCompatActivity() {

    private lateinit var editTextTemplate: EditText
    private lateinit var textViewStatus: TextView
    private lateinit var defaultTemplate: String
    private val sharedPrefFile = "com.send2ai.preferences"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        defaultTemplate = getString(R.string.default_template)

        editTextTemplate = findViewById(R.id.editTextTemplate)
        textViewStatus = findViewById(R.id.textViewStatus)
        val buttonSave: Button = findViewById(R.id.buttonSave)

        loadTemplateText()

        buttonSave.setOnClickListener {
            saveTemplateText()
        }


    }

    private fun loadTemplateText() {
        val sharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val templateText = sharedPreferences.getString("templateText", defaultTemplate)
        editTextTemplate.setText(templateText)
    }

    private fun saveTemplateText() {
        val inputText = editTextTemplate.text.toString()

        if (inputText.isBlank()) {
            // Handle empty input
            saveTextToPreferences(defaultTemplate)
            textViewStatus.text =
                getString(R.string.empty_input_so_the_default_is_just, defaultTemplate)
        } else {
            // Save valid input
            saveTextToPreferences(inputText)
            textViewStatus.text = getString(R.string.template_saved_successfully)
        }
    }

    private fun saveTextToPreferences(text: String) {
        val sharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("templateText", text)
            apply()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }



}
