package com.example.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaBiblioteca()
        }
    }
}

@Composable
fun CardLivro(livro: Livro) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = capaParaImagem(livro.capa)),
                contentDescription = livro.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = livro.titulo,
                style = MaterialTheme.typography.titleSmall,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (livro.disponivel) "Disponível" else "Indisponível",
                style = MaterialTheme.typography.bodySmall,
                color = if (livro.disponivel) Color(0xFF2E7D32) else Color(0xFFC62828),
            )
        }
    }
}

@Composable
fun CarrosselDeLivros(listaDeLivros: List<Livro>) {
    LazyRow(
        modifier = Modifier.padding(16.dp),
    ) {
        items(listaDeLivros) { livroAtual ->
            CardLivro(livro = livroAtual)
        }
    }
}

@Composable
fun BuscaLivro(onBuscar: (String) -> Unit) {
    var id by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("ID do livro") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { onBuscar(id) }) {
            Text("Buscar")
        }
    }
}

@Composable
fun FormularioCadastro(onCadastrar: (LivroRequest) -> Unit) {
    var titulo by remember { mutableStateOf("") }
    var disponivel by remember { mutableStateOf(true) }
    var capa by remember { mutableStateOf(codigosDeCapa.first()) }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text("Cadastrar livro", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = disponivel,
                onCheckedChange = { disponivel = it },
            )
            Text("Disponível")
        }

        Text("Escolha a capa", style = MaterialTheme.typography.labelLarge)
        LazyRow {
            items(codigosDeCapa) { codigo ->
                val selecionada = codigo == capa
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { capa = codigo },
                    border = if (selecionada) {
                        BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
                    } else {
                        null
                    },
                ) {
                    Image(
                        painter = painterResource(id = capaParaImagem(codigo)),
                        contentDescription = codigo,
                        modifier = Modifier.size(width = 60.dp, height = 80.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onCadastrar(LivroRequest(titulo.trim(), disponivel, capa)) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Cadastrar")
        }
    }
}

@Composable
fun TelaBiblioteca() {
    var livros by remember { mutableStateOf<List<Livro>>(emptyList()) }
    var livroEncontrado by remember { mutableStateOf<Livro?>(null) }
    var mensagemBusca by remember { mutableStateOf("") }
    var mensagemCadastro by remember { mutableStateOf("") }
    val escopo = rememberCoroutineScope()

    suspend fun carregarLivros() {
        try {
            livros = ApiClient.livroApi.listarLivros()
        } catch (e: Exception) {
            mensagemBusca = "Erro ao carregar livros: ${e.message}"
        }
    }

    LaunchedEffect(Unit) { carregarLivros() }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(
            text = "Biblioteca Tecnológica",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp),
        )

        BuscaLivro(onBuscar = { texto ->
            val id = texto.toLongOrNull()
            if (id == null) {
                livroEncontrado = null
                mensagemBusca = "Digite um número de ID."
            } else {
                escopo.launch {
                    try {
                        livroEncontrado = ApiClient.livroApi.buscarLivro(id)
                        mensagemBusca = ""
                    } catch (e: retrofit2.HttpException) {
                        livroEncontrado = null
                        mensagemBusca = "Livro não encontrado."
                    } catch (e: Exception) {
                        livroEncontrado = null
                        mensagemBusca = "Erro de conexão: ${e.message}"
                    }
                }
            }
        })

        livroEncontrado?.let { CardLivro(livro = it) }

        if (mensagemBusca.isNotEmpty()) {
            Text(
                text = mensagemBusca,
                modifier = Modifier.padding(16.dp),
            )
        }

        Text(
            text = "Todos os livros",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
        )
        CarrosselDeLivros(livros)

        FormularioCadastro(onCadastrar = { request ->
            escopo.launch {
                try {
                    val novo = ApiClient.livroApi.cadastrarLivro(request)
                    mensagemCadastro = "Livro cadastrado com sucesso! ID: ${novo.id}"
                    carregarLivros()
                } catch (e: retrofit2.HttpException) {
                    mensagemCadastro = "Dados inválidos. Confira o título (obrigatório, até 100 caracteres)."
                } catch (e: Exception) {
                    mensagemCadastro = "Erro de conexão: ${e.message}"
                }
            }
        })

        if (mensagemCadastro.isNotEmpty()) {
            Text(
                text = mensagemCadastro,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCarrosselDeLivros() {
    CarrosselDeLivros(
        listOf(
            Livro(1, "O Senhor dos Anéis", true, "aneis"),
            Livro(2, "Harry Potter", false, "harry"),
            Livro(3, "Dom Casmurro", true, "dom"),
            Livro(4, "1984", false, "orwel"),
        )
    )
}
