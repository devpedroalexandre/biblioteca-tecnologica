package com.example.android

data class Livro(
    val id: Long,
    val titulo: String,
    val disponivel: Boolean,
    val capa: String,
)

data class LivroRequest(
    val titulo: String,
    val disponivel: Boolean,
    val capa: String,
)

fun capaParaImagem(capa: String): Int = when (capa) {
    "aneis" -> R.drawable.aneis
    "harry" -> R.drawable.harry
    "dom" -> R.drawable.dom
    "orwel" -> R.drawable.orwel
    "principe" -> R.drawable.principe
    else -> R.drawable.dom
}

val codigosDeCapa = listOf("aneis", "harry", "dom", "orwel", "principe")
