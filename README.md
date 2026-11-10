# Biblioteca Tecnológica

Projeto acadêmico (ADS, 4º período) com duas partes que conversam entre si:

| Pasta | O que é | Tecnologias |
|---|---|---|
| `BACKEND` | API que lista, busca e cadastra livros | Java, Spring Boot, Maven, JPA, H2, Bean Validation |
| `ANDROID` | Aplicativo que mostra os livros e cadastra novos | Kotlin, Jetpack Compose, Retrofit, Coroutines |
| `DOCUMENTACAO` | Explicação do código (PDF e Markdown) e passo a passo para rodar em outro computador | |

## A API (porta 8089)

| Verbo | Endereço | Resultado |
|---|---|---|
| GET | `/livros` | 200, lista de livros |
| GET | `/livros/{id}` | 200, ou 404 se não existir |
| POST | `/livros` | 201 criado, ou 400 se os dados forem inválidos |

O banco é H2 em memória: ao reiniciar o Backend, voltam só os 4 livros iniciais.

## Como rodar

1. **Backend:** abra a pasta `BACKEND` no IntelliJ e rode `BibliotecaApplication`. Teste em `http://localhost:8089/livros`.
2. **Emulador:** ligue um emulador Android e execute `adb reverse tcp:8089 tcp:8089`. O app acessa o Backend em `http://127.0.0.1:8089/`.
3. **App:** abra a pasta `ANDROID` no Android Studio e rode o módulo `app`.

O passo a passo completo, com instalação das ferramentas, está em `DOCUMENTACAO/3-PASSO-A-PASSO-NOTEBOOK.pdf`.
