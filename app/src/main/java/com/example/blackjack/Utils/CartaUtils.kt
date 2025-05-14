package com.example.blackjack.Utils
import com.example.blackjack.R

fun obtenerRecursoCarta(nombre: String): Int {
    return when (nombre) {

        "a_picas" -> R.drawable.a_picas
        "c2_picas" -> R.drawable.c2_picas
        "c3_picas" -> R.drawable.c3_picas
        "c4_picas" -> R.drawable.c4_picas
        "c5_picas" -> R.drawable.c5_picas
        "c6_picas" -> R.drawable.c6_picas
        "c7_picas" -> R.drawable.c7_picas
        "c8_picas" -> R.drawable.c8_picas
        "c9_picas" -> R.drawable.c9_picas
        "c10_picas" -> R.drawable.c10_picas
        "j_picas" -> R.drawable.j_picas
        "q_picas" -> R.drawable.q_picas
        "k_picas" -> R.drawable.k_picas


        "a_corazones" -> R.drawable.a_corazones
        "c2_corazones" -> R.drawable.c2_corazones
        "c3_corazones" -> R.drawable.c3_corazones
        "c4_corazones" -> R.drawable.c4_corazones
        "c5_corazones" -> R.drawable.c5_corazones
        "c6_corazones" -> R.drawable.c6_corazones
        "c7_corazones" -> R.drawable.c7_corazones
        "c8_corazones" -> R.drawable.c8_corazones
        "c9_corazones" -> R.drawable.c9_corazones
        "c10_corazones" -> R.drawable.c10_corazones
        "j_corazones" -> R.drawable.j_corazones
        "q_corazones" -> R.drawable.q_corazones
        "k_corazones" -> R.drawable.k_corazones


        "a_diamantes" -> R.drawable.a_diamantes
        "c2_diamantes" -> R.drawable.c2_diamantes
        "c3_diamantes" -> R.drawable.c3_diamantes
        "c4_diamantes" -> R.drawable.c4_diamantes
        "c5_diamantes" -> R.drawable.c5_diamantes
        "c6_diamantes" -> R.drawable.c6_diamantes
        "c7_diamantes" -> R.drawable.c7_diamantes
        "c8_diamantes" -> R.drawable.c8_diamantes
        "c9_diamantes" -> R.drawable.c9_diamantes
        "c10_diamantes" -> R.drawable.c10_diamantes
        "j_diamantes" -> R.drawable.j_diamantes
        "q_diamantes" -> R.drawable.q_diamantes
        "k_diamantes" -> R.drawable.k_diamantes


        "a_trebol" -> R.drawable.a_trebol
        "c2_trebol" -> R.drawable.c2_trebol
        "c3_trebol" -> R.drawable.c3_trebol
        "c4_trebol" -> R.drawable.c4_trebol
        "c5_trebol" -> R.drawable.c5_trebol
        "c6_trebol" -> R.drawable.c6_trebol
        "c7_trebol" -> R.drawable.c7_trebol
        "c8_trebol" -> R.drawable.c8_trebol
        "c9_trebol" -> R.drawable.c9_trebol
        "c10_trebol" -> R.drawable.c10_trebol
        "j_trebol" -> R.drawable.j_trebol
        "q_trebol" -> R.drawable.q_trebol
        "k_trebol" -> R.drawable.k_trebol

        else -> R.drawable.reverso
    }
}
