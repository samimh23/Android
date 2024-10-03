package com.example.myapplicationjetpackcolor

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.example.myapplicationjetpackcolor.ui.theme.MyApplicationColorTheme

const val RED = "RED"
const val BLUE = "BLUE"
const val YELLOW = "YELLOW"
const val PURPLE = "PURPLE"
const val GREEN = "GREEN"
const val ORANGE = "ORANGE"

const val NAME = "NAME"
const val MIXED_COLOR = "COLOR"
const val COLOR1 = "COLOR 1"
const val COLOR2 = "COLOR 2"

class QuestionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationColorTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    QuestionScreen()
                }
            }
        }
    }
}

@Composable
fun QuestionScreen() {
    var name by remember { mutableStateOf("") }
    var colorBlue by remember { mutableStateOf(false) }
    var colorRed by remember { mutableStateOf(false) }
    var colorYellow by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("esmek mon ami") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Checkbox(checked = colorBlue, onCheckedChange = { colorBlue = it })
            Text("Blue")
        }
        Row {
            Checkbox(checked = colorRed, onCheckedChange = { colorRed = it })
            Text("Red")
        }
        Row {
            Checkbox(checked = colorYellow, onCheckedChange = { colorYellow = it })
            Text("Yellow")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val selectedColors = listOf(colorBlue, colorRed, colorYellow).count { it }
            if (name.isEmpty()) {
                errorMessage = "esmk oiesm bouk"
            } else if (selectedColors != 2) {
                errorMessage = "lezem tekhtar zouz alwen!"
            } else {
                val mixedColor: String
                val color1: String
                val color2: String

                if (colorBlue && colorRed) {
                    mixedColor = PURPLE
                    color1 = BLUE
                    color2 = RED
                } else if (colorBlue && colorYellow) {
                    mixedColor = GREEN
                    color1 = BLUE
                    color2 = YELLOW
                } else if (colorRed && colorYellow) {
                    mixedColor = ORANGE
                    color1 = RED
                    color2 = YELLOW
                } else {
                    errorMessage = "ekhatr zouz alwen "
                    return@Button
                }

                val intent = Intent(context, AnswerActivity::class.java).apply {
                    putExtra(NAME, name)
                    putExtra(MIXED_COLOR, mixedColor)
                    putExtra(COLOR1, color1)
                    putExtra(COLOR2, color2)
                }

                context.startActivity(intent)
            }
        }) {
            Text("Mix")
        }
    }
}
