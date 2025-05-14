package com.example.blackjack.Components

import androidx.compose.foundation.Image
import com.example.blackjack.R
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.example.blackjack.Models.Jugador
import com.example.blackjack.Utils.obtenerRecursoCarta


@Composable
fun BotonPrincipal(
    nombre: String,
    colorTexto: Color,
    colorFondo: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = colorFondo,
            contentColor = colorTexto
        )
    ) {
        Text(text = nombre, fontSize = 15.sp)
    }
}

@Composable
fun BotonPrincipal2(
    nombre: String,
    colorTexto: Color,
    colorFondo: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = colorFondo,
            contentColor = colorTexto
        )
    ) {
        Text(text = nombre, fontSize = 20.sp)
    }
}



@Composable
fun DialogoInstrucciones(
    mostrar: Boolean,
    cerrarDialogo: () -> Unit
) {
    if (mostrar) {
        AlertDialog(
            onDismissRequest = cerrarDialogo,
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = cerrarDialogo) {
                        Text(
                            text = "Cerrar",
                            fontSize = 16.sp,
                            color = Color(0xFFFFD700)
                        )
                    }
                }
            },
            title = {
                Text(
                    text = "Instrucciones",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "El objetivo del Blackjack es obtener un valor de cartas lo más cercano posible a 21 sin pasarse.",
                        fontSize = 15.sp,
                        color = Color(0xFFE0E0E0)
                    )
                    Text(
                        text = "1. Cada jugador comienza con dos cartas.\n" +
                                "2. Puedes pedir más cartas (hit) o mantenerte (stand).\n" +
                                "3. Si superas 21, pierdes automáticamente.\n" +
                                "4. El crupier juega luego, y gana quien esté más cerca de 21 sin pasarse.",
                        fontSize = 14.sp,
                        color = Color(0xFFCCCCCC)
                    )
                }
            },
            shape = RoundedCornerShape(20.dp),
            containerColor = Color(0xFF1E293B),
            tonalElevation = 10.dp
        )
    }
}


@Composable
fun CartaView(nombre: String) {
    val imagen = when (nombre) {
        "carta_oculta" -> R.drawable.reverso
        else -> obtenerRecursoCarta(nombre)
    }

    Image(
        painter = painterResource(id = imagen),
        contentDescription = "Carta $nombre",
        modifier = Modifier
            .width(60.dp)
            .height(90.dp)
            .padding(4.dp)
    )
}

@Composable
fun JugadorStatsCard(
    puntos: Int,
    saldo: Int,
    apuesta: Int,
    resultado: String
) {
    Card(
        modifier = Modifier
            .padding(top = 4.dp)
            .widthIn(min = 280.dp)
            .heightIn(min = 35.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E293B) // Fondo oscuro igual al dialog
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .widthIn(min = 280.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Puntos: $puntos", fontSize = 11.sp, color = Color.White)
                Text("Saldo: \$$saldo", fontSize = 11.sp, color = Color(0xFFCCCCCC))
                Text("Apuesta: \$$apuesta", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFFFFD700))
            }

            if (resultado.isNotEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Resultado:", fontSize = 10.sp, color = Color(0xFFE0E0E0))
                    Text(resultado, fontSize = 10.sp, color = Color(0xFFE0E0E0))
                }
            }
        }
    }
}


@Composable
fun DealerStatsCard(
    puntos: Int?,
    banco: Int,
    mostrarPuntos: Boolean
) {
    Card(
        modifier = Modifier
            .padding(top = 4.dp)
            .widthIn(min = 280.dp)
            .heightIn(min = 35.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .widthIn(min = 280.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Puntos: ${if (mostrarPuntos) puntos else "?"}",
                fontSize = 11.sp,
                color = Color.White
            )
            Text(
                text = "Banco: \$$banco",
                fontSize = 11.sp,
                color = Color(0xFFFFD700),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun GanadorStatsCard(jugador: Jugador) {
    Card(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2D3748)),
        modifier = Modifier.padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Nombre: ${jugador.nombre}", color = Color.White)
            Divider(modifier = Modifier.padding(vertical = 4.dp), color = Color.Gray)
            Text("Puntos: ${jugador.puntos}", color = Color.White)
            Divider(modifier = Modifier.padding(vertical = 4.dp), color = Color.Gray)
            Text("Saldo: \$${jugador.saldo}", color = Color.White)
            Divider(modifier = Modifier.padding(vertical = 4.dp), color = Color.Gray)
            Text("Apuesta: \$${jugador.apuesta}", color = Color.White)
        }
    }
}


