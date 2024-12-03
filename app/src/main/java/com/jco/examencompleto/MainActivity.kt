package com.jco.examencompleto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jco.examencompleto.ui.theme.ExamenCompletoTheme

class MainActivity : ComponentActivity() {
    val background5 = Color(0xFF9B9594)
    val alumno5 = Color(0xFFDD2C00)


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamenCompletoTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            colors = topAppBarColors(
                                containerColor = background5,
                                titleContentColor = alumno5,
                            ),
                            title = {
                                Text("Conversación Janey")
                            }
                        )
                    },

                    ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding),
                    ) {
                        FichaAlumno("Janey", R.drawable.avatar4, Color.LightGray)
                        Boton()
                        MiSpacer(10.dp)


                    }
                }
            }
        }
    }
}


data class Message(val author: String, val body: String)

@Composable
fun FichaAlumno(nombreAlumno: String, imagenAlumno: Int, colorBG: Color) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .background(colorBG)
            .fillMaxWidth()
            .border(
                border = BorderStroke(width = 2.dp, color = Color.Black),
                shape = RectangleShape
            )
    )
    {
        Image(
            painterResource(imagenAlumno),
            contentDescription = "Foto perfil",
            modifier = Modifier
                .padding(10.dp)
                .size(60.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)

        )
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(text = "Alumno: $nombreAlumno", fontSize = 26.sp)
            Text(text = "Soy un alumno")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun Boton() {
    val colorBoton = Color.Magenta
    val listaColores = listOf(Color.Green, Color.Blue, Color.Red, Color.Cyan)
    var colorActualizado by remember { mutableStateOf(colorBoton) }
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Button(onClick = {
            val random = (0..3).random()
            val newColor = listaColores[random]
            colorActualizado = newColor

        }, colors = ButtonDefaults.buttonColors(colorActualizado)) {

            Text(text = "Cambiar color")


        }
    }
    Conversation(SampleData.conversationSample, colorActualizado)
}

@Composable
fun MessageCard(
    msg: Message,
    imagenProfesor: Int,
    colorDesplegado: Color,
    colorSinDesplegar: Color
) {


    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painterResource(imagenProfesor),
            contentDescription = "Foto perfil",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            if (isExpanded) colorDesplegado else colorSinDesplegar,
        )

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>, color: Color) {
    val imagenProfesor = R.drawable.profesor
    val colorSinDesplegar = Color.LightGray

    LazyColumn {
        items(messages) { message ->
            MessageCard(message, imagenProfesor, color, colorSinDesplegar)
        }
    }
}

@Composable
fun MiSpacer(size: Dp = 5.dp) {
    Spacer(modifier = Modifier.width(size))
}
