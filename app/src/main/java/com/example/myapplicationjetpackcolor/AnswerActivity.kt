package com.example.myapplicationjetpackcolor

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import com.example.myapplicationjetpackcolor.ui.theme.MyApplicationColorTheme
import com.example.myapplicationjetpackcolor.ui.theme.RESULT
import com.example.myapplicationjetpackcolor.ui.theme.SUCCESS
import com.example.myapplicationjetpackcolor.ui.theme.FAILED

class AnswerActivity : ComponentActivity() {

    private var correctColor = "NONE"
    private var name = "NONE"
    private var color1 = "NONE"
    private var color2 = "NONE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve intent data
        correctColor = intent.getStringExtra(MIXED_COLOR) ?: "NONE"
        name = intent.getStringExtra(NAME) ?: "NONE"
        color1 = intent.getStringExtra(COLOR1) ?: "NONE"
        color2 = intent.getStringExtra(COLOR2) ?: "NONE"

        // Set Compose content
        setContent {
            MyApplicationColorTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AnswerScreen(
                        name = name,
                        color1 = color1,
                        color2 = color2,
                        correctColor = correctColor
                    )
                }
            }
        }
    }
}

@Composable
fun AnswerScreen(name: String, color1: String, color2: String, correctColor: String) {
    var selectedAnswer by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }
    val context = LocalContext.current // Get the current context

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "You chose $color1 and $color2",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Radio buttons for color choices
        RadioButtonGroup(
            selectedAnswer = selectedAnswer,
            onAnswerSelected = { selectedAnswer = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Submit button
        Button(onClick = {
            if (selectedAnswer.isEmpty()) {
                showSnackbar = true
            } else {
                val isCorrect = selectedAnswer == correctColor
                // Navigate to ResultActivity
                val intent = Intent(context, ResultActivity::class.java).apply {
                    putExtra(NAME, name)
                    putExtra(RESULT, if (isCorrect) SUCCESS else FAILED)
                }
                context.startActivity(intent) // Use the context to start the activity
            }
        }) {
            Text("Submit")
        }

        // Show snackbar if no answer selected
        if (showSnackbar) {
            Snackbar(
                modifier = Modifier.padding(8.dp),
                content = { Text("Please choose an answer!") }
            )
        }
    }
}

@Composable
fun RadioButtonGroup(selectedAnswer: String, onAnswerSelected: (String) -> Unit) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedAnswer == "Green",
                onClick = { onAnswerSelected("Green") }
            )
            Text(text = "Green")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedAnswer == "Orange",
                onClick = { onAnswerSelected("Orange") }
            )
            Text(text = "Orange")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedAnswer == "Purple",
                onClick = { onAnswerSelected("Purple") }
            )
            Text(text = "Purple")
        }
    }
}
