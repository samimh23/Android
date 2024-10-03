package com.example.myapplicationjetpackcolor

import android.icu.lang.UProperty.NAME
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplicationjetpackcolor.ui.theme.FAILED
import com.example.myapplicationjetpackcolor.ui.theme.MyApplicationColorTheme
import com.example.myapplicationjetpackcolor.ui.theme.RESULT
import com.example.myapplicationjetpackcolor.ui.theme.SUCCESS

class ResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val result = intent.getStringExtra(RESULT) ?: FAILED
        val name = intent.getStringExtra(NAME.toString()) ?: "Unknown"

        setContent {
            MyApplicationColorTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ResultScreen(result = result, name = name)
                }
            }
        }
    }
}

@Composable
fun ResultScreen(result: String, name: String) {
    val backgroundColor = if (result == SUCCESS) Color.Green else Color.Red
    val message = if (result == SUCCESS) "Congratulation $name! You succeeded!" else "Sorry $name! You failed!"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = if (result == SUCCESS) painterResource(id = R.drawable.ic_success) else painterResource(id = R.drawable.ic_failure),
            contentDescription = if (result == SUCCESS) "Success" else "Failure"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { /* Handle finish or return to main screen */ }) {
            Text("Quit")
        }
    }
}
