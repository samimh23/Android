package com.example.myapplicationjetpackcolor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.example.myapplicationjetpackcolor.ui.theme.FAILED
import com.example.myapplicationjetpackcolor.ui.theme.MyApplicationColorTheme
import com.example.myapplicationjetpackcolor.ui.theme.RESULT
import com.example.myapplicationjetpackcolor.ui.theme.SUCCESS

class AnswerActivity : ComponentActivity() {
    private var name = "NONE"
    private var color1 = "NONE"
    private var color2 = "NONE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the colors and name from the intent
        name = intent.getStringExtra(NAME) ?: "NONE"
        color1 = intent.getStringExtra(COLOR1) ?: "NONE"
        color2 = intent.getStringExtra(COLOR2) ?: "NONE"

        // Log the received colors for debugging
        Log.d("AnswerActivity", "Received colors: color1=$color1, color2=$color2")

        // Determine the correct color based on the selected colors
        val correctColor = determineCorrectColor(color1, color2)

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

    // Function to determine the correct color based on selected colors
    private fun determineCorrectColor(color1: String, color2: String): String {
        Log.d("AnswerActivity", "Determining correct color for: $color1 and $color2")
        return when {
            (color1.equals("RED", ignoreCase = true) && color2.equals("YELLOW", ignoreCase = true)) ||
                    (color1.equals("YELLOW", ignoreCase = true) && color2.equals("RED", ignoreCase = true)) -> "Orange"
            // Add more conditions for other color combinations here
            else -> "NONE" // Default if no combination matches
        }
    }

}

@Composable
fun AnswerScreen(name: String, color1: String, color2: String, correctColor: String) {
    var selectedAnswer by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }
    val context = LocalContext.current

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

        // Radio button group for selecting the answer
        RadioButtonGroup(
            selectedAnswer = selectedAnswer,
            onAnswerSelected = { selectedAnswer = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            if (selectedAnswer.isEmpty()) {
                showSnackbar = true
            } else {
                // Check if the selected answer matches the correct color
                Log.d("AnswerActivity", "Selected answer: $selectedAnswer, Correct color: $correctColor")
                val isCorrect = selectedAnswer == correctColor
                val intent = Intent(context, ResultActivity::class.java).apply {
                    putExtra(NAME, name)
                    putExtra(RESULT, if (isCorrect) SUCCESS else FAILED)
                }
                context.startActivity(intent)
            }
        }) {
            Text("Submit")
        }

        if (showSnackbar) {
            Snackbar(
                modifier = Modifier.padding(8.dp),
                content = { Text("Please select an answer.") }
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
