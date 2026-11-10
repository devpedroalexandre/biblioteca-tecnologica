# Biblioteca Tecnológica — Levar o projeto para o NOTEBOOK

Passo a passo para reproduzir, no notebook, tudo o que funciona hoje no desktop. **Sem pendrive e sem cabo.** A transferência é feita pelo **OneDrive**, porque a pasta do projeto já está dentro dele.

> **Aviso honesto:** os passos do desktop foram executados e conferidos. Os passos do notebook **ainda não foram testados nele**, porque eu não tenho acesso a esse computador. Por isso, **faça tudo com antecedência e ensaie** antes da apresentação.

---

## 0. Visão geral (o que precisa existir no notebook)

| Peça | Para que serve | Observação |
|---|---|---|
| Projeto (pastas BACKEND e ANDROID) | O código | Vem pelo OneDrive |
| Java (JDK) 25 | Rodar o Backend **e** compilar o app | O desktop usa Java 25. O Gradle do app **exige Java 25** (ele baixa sozinho na 1ª sincronização) |
| IntelliJ IDEA | Abrir e rodar o Backend | A versão gratuita (Community) serve |
| Android Studio | Abrir e compilar o app | Traz o emulador |
| Emulador "Medium Phone" (Android 37) | O "celular" onde o app roda | Mesma configuração do desktop |
| `adb reverse` | Liga o emulador ao Backend (porta 8089) | Um comando, repetido a cada vez que o emulador reiniciar |

A ordem dos passos é: **(1) preparar no desktop → (2) baixar no notebook → (3) instalar as ferramentas → (4) rodar o Backend → (5) rodar o app → (6) ensaiar.**

> **Importante:** a primeira vez que o Maven e o Gradle rodam, eles **baixam bibliotecas da internet** (centenas de MB). Faça isso em casa, com Wi-Fi bom, **dias ou horas antes**, e não na hora da apresentação.

---

## 1. NO DESKTOP — preparar o envio

### 1.1 Confirmar que o OneDrive está sincronizando
1. Olhe no canto da tela, perto do relógio, o ícone da **nuvem do OneDrive**.
2. Ele deve estar **sem seta girando e sem alerta**. Passe o mouse: deve aparecer "Atualizado" (ou similar).
3. Se estiver pausado, clique no ícone e escolha **Retomar a sincronização**.

### 1.2 Pegar o arquivo ZIP pronto
Foi criado um ZIP **limpo** (sem as pastas pesadas de compilação), com este nome:

```
bibliothec-mobile_para-notebook.zip
```

Ele está dentro de:

```
OneDrive\Desktop\ADS\4º\Mobile\bibliothec-mobile\
```

Ele contém as pastas **BACKEND**, **ANDROID** e **DOCUMENTACAO** (com os PDFs).

### 1.3 Conferir que o envio terminou
1. Abra o navegador e entre em **https://onedrive.live.com** com a **mesma conta Microsoft** do desktop.
2. Procure o arquivo `bibliothec-mobile_para-notebook.zip`.
3. Se ele aparecer lá, o envio terminou. Se não aparecer, espere a sincronização acabar.

> **Plano reserva para enviar:** se o OneDrive der problema, mande o ZIP por **e-mail para você mesma** (ele é pequeno) ou suba no **Google Drive**.

---

## 2. NO NOTEBOOK — verificar se ele aguenta

Faça estas conferências **antes de instalar qualquer coisa**.

| Conferência | Como ver | O que precisa |
|---|---|---|
| Tipo do processador | Configurações → Sistema → Sobre → "Tipo de sistema" | Deve dizer **64 bits, x64**. (Se disser *ARM*, avise: o emulador precisa de outro tipo de imagem) |
| Memória | A mesma tela → "RAM instalada" | **8 GB no mínimo**, 16 GB é o ideal |
| Espaço livre | Configurações → Sistema → Armazenamento | **Pelo menos 20 GB livres** |
| Virtualização ligada | Ctrl+Shift+Esc → aba **Desempenho** → **CPU** → "Virtualização" | Deve estar **Habilitado** |
| Internet | Abrir um site | Wi-Fi funcionando (para os downloads) |

Se a **Virtualização** aparecer como *Desabilitado*, o emulador não vai funcionar bem. Nesse caso é preciso ligar a opção na BIOS do notebook (o nome costuma ser *Intel VT-x*, *SVM* ou *Virtualization Technology*). Isso depende do modelo do notebook.

---

## 3. NO NOTEBOOK — baixar o projeto

1. Abra o navegador e entre em **https://onedrive.live.com** com a **mesma conta Microsoft**.
2. Baixe o arquivo `bibliothec-mobile_para-notebook.zip`.
3. Crie uma pasta com **caminho curto, sem acento e sem espaço**: `C:\biblioteca`.
4. Clique com o botão direito no ZIP → **Extrair tudo...** → escolha `C:\biblioteca`.
5. No fim, você deve ter:

```
C:\biblioteca\
   BACKEND\
   ANDROID\
   DOCUMENTACAO\
```

> Por que um caminho curto? O caminho original do desktop tem `4º` (com acento) e espaços. Isso funciona no desktop, mas um caminho curto evita qualquer problema nas ferramentas.

---

## 4. NO NOTEBOOK — instalar as ferramentas

### 4.1 IntelliJ IDEA (para o Backend)
1. Baixe o **IntelliJ IDEA Community** em **https://www.jetbrains.com/idea/download** (a versão gratuita).
2. Instale com as opções padrão (Avançar, Avançar...).
3. Abra o IntelliJ.

### 4.2 Android Studio (para o app)
1. Baixe em **https://developer.android.com/studio**.
2. Instale com as opções padrão.
3. Na **primeira abertura**, aparece um assistente ("Setup Wizard"):
   - Escolha **Standard**.
   - Aceite as licenças (marque **Accept** em todas).
   - Deixe baixar o Android SDK (alguns minutos).
4. Use a versão **estável mais recente**. O projeto usa o *Android Gradle Plugin 9.4.1*. Se o Android Studio reclamar de versão antiga, **atualize o Android Studio** pelo menu *Help → Check for Updates*.

### 4.3 Componentes do Android (SDK Manager)
No Android Studio: **Tools → SDK Manager** (ou o ícone de engrenagem → *SDK Manager*).

**Aba "SDK Platforms"** — marque:

- **Android 37.0** (a mesma do desktop; o projeto compila com a API 37).

**Aba "SDK Tools"** — confirme que estão marcados:

- *Android SDK Build-Tools*
- *Android SDK Platform-Tools* (é o que contém o `adb`)
- *Android Emulator*

Clique em **Apply** e espere baixar.

### 4.4 Criar o emulador (celular de mentira)
No Android Studio: **Tools → Device Manager** → **Create Virtual Device** (o botão **+**).

1. Escolha **Phone** → **Medium Phone** → Next.
2. Escolha a imagem do sistema **API 37.0** com **x86_64** e **Google APIs**.
   - O emulador do desktop usa a imagem "Google APIs ... (16 KB Page Size)". Se essa opção aparecer, escolha a mesma. Se não aparecer, escolha a versão normal do API 37 (também deve funcionar, mas isso **não foi testado**).
   - Se pedir, clique em **Download** ao lado da imagem (é um download grande, de alguns GB) e espere acabar.
3. Nome: `Medium_Phone` → **Finish**.
4. Clique no botão **▶** (Play) ao lado do emulador para **ligá-lo** uma vez e confirmar que abre. Espere até a tela inicial do Android aparecer.

> Se o Windows reclamar do emulador, ligue o recurso **Plataforma do Hipervisor do Windows** em *Ativar ou desativar recursos do Windows*, reinicie o notebook e tente de novo.

### 4.5 Java 25
Você não precisa baixar o Java à mão:

- O **IntelliJ** baixa o Java 25 para o Backend (passo 5.2).
- O **Gradle** do app baixa o Java 25 sozinho na primeira sincronização (passo 6.1). Isso exige internet.

---

## 5. NO NOTEBOOK — rodar o BACKEND

### 5.1 Abrir o projeto
1. No IntelliJ: **File → Open**.
2. Escolha a pasta **`C:\biblioteca\BACKEND`** (a pasta que contém o `pom.xml`).
3. Se perguntar "Trust Project", clique em **Trust Project**.
4. Espere o canto inferior direito terminar de carregar (barra de progresso). Na **primeira vez**, o Maven baixa as bibliotecas.

### 5.2 Escolher o Java
1. **File → Project Structure → Project**.
2. Em **SDK**, abra a lista → **Download JDK...**.
3. Escolha a versão **25** (a mesma testada no desktop). O `pom.xml` aceita Java 17 ou superior, mas só a 25 foi testada.
4. Clique em **Download** e **OK**.

> No desktop o Backend foi testado com Java 25. Em outra versão (como a 21), o esperado é funcionar, mas isso **não foi testado**.

### 5.3 Rodar
1. No painel esquerdo, abra: `src/main/java/com/biblioteca/BibliotecaApplication.java`.
2. Clique no **triângulo verde** ao lado de `public static void main` → **Run 'BibliotecaApplication'**.
3. Na janela de baixo (Console), deve aparecer algo como `Started BibliotecaApplication`. O servidor está ligado na **porta 8089**.

### 5.4 Conferir
Abra o navegador do notebook e entre em:

```
http://localhost:8089/livros
```

Deve aparecer o texto JSON com os **4 livros**. Teste também:

```
http://localhost:8089/livros/1
http://localhost:8089/livros/99
```

O primeiro deve mostrar o livro 1. O segundo deve responder com status 404 (Not Found).

**Se o Backend estiver certo aqui, o Backend está pronto.** Deixe o IntelliJ rodando.

> Para **reiniciar o Backend** (voltar a ter só os 4 livros): clique no quadrado vermelho (**Stop**) e depois **Run** de novo.

---

## 6. NO NOTEBOOK — rodar o APP ANDROID

### 6.1 Abrir o projeto
1. No Android Studio: **File → Open**.
2. Escolha a pasta **`C:\biblioteca\ANDROID`**.
3. Clique em **Trust Project** se perguntar.
4. O Android Studio faz o **Gradle Sync** sozinho. **Espere terminar** (pode demorar). Na primeira vez ele baixa o **Gradle 9.6.0**, o **Java 25** (o projeto exige essa versão) e as bibliotecas. Por isso precisa de internet. Pode aparecer **"BUILD SUCCESSFUL"** no painel *Build*.
5. Ele cria sozinho o arquivo `local.properties` com o caminho do SDK do notebook. Nós não levamos o do desktop de propósito, porque o caminho é diferente.

### 6.2 Ligar o emulador
No **Device Manager**, clique no **▶** do `Medium_Phone` e **espere** a tela inicial do Android aparecer por completo.

### 6.3 Conectar o emulador ao Backend (ADB Reverse)

Este comando faz o `127.0.0.1:8089` do emulador chegar ao Backend do notebook.

Abra o **PowerShell** (Menu Iniciar → digite *PowerShell*) e rode:

```powershell
& "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe" devices
```

Deve aparecer uma linha com `emulator-5554    device`. Depois:

```powershell
& "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe" reverse tcp:8089 tcp:8089
& "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe" reverse --list
```

O último comando deve mostrar uma linha com `tcp:8089 tcp:8089`.

> O caminho acima é o **padrão do Android Studio**. Se você mudou o local do SDK durante a instalação, ajuste o caminho (no Android Studio: *SDK Manager → Android SDK Location*). No desktop o SDK está em `C:\Android`, mas isso não vale para o notebook.

> **Atenção:** o `adb reverse` **some** se o emulador for fechado ou reiniciado. Rode o comando de novo sempre que isso acontecer.

### 6.4 Rodar o app
1. Na barra de cima do Android Studio, confirme que o dispositivo selecionado é o **Medium_Phone**.
2. Clique no **triângulo verde (Run 'app')**.
3. O app é compilado e instalado no emulador, e deve abrir sozinho.
4. Se aparecer **tela preta** logo depois de instalar, **abra o app pelo ícone** dele no emulador.

### 6.5 O que você deve ver
- Título "Biblioteca Tecnológica", campo de busca, carrossel com os **4 livros** e o formulário de cadastro.
- Buscar o **ID 3** → mostra Dom Casmurro.
- Buscar o **ID 99** → mostra "Livro não encontrado.".
- Cadastrar **"O Pequeno Príncipe"** com a capa do Pequeno Príncipe → "Livro cadastrado com sucesso! ID: 5" e o livro entra no carrossel.

---

## 7. Ensaio final (faça pelo menos uma vez, em casa)

1. Feche tudo (IntelliJ, Android Studio, emulador).
2. Siga o **roteiro do dia** (próxima seção) do zero e veja se funciona.
3. **Reinicie o Backend** no fim, para o banco voltar a ter só os 4 livros.

---

## 8. Roteiro do DIA DA APRESENTAÇÃO

### Backend (hoje)
- [ ] 1. Ligar o notebook na tomada e fechar programas pesados.
- [ ] 2. Abrir o **IntelliJ** com a pasta **BACKEND**.
- [ ] 3. Rodar `BibliotecaApplication` e esperar `Started BibliotecaApplication`.
- [ ] 4. Abrir `http://localhost:8089/livros` no navegador para mostrar que responde.
- [ ] 5. Ter o **PDF de Backend** aberto para consultar.

### Mobile (amanhã)
Siga **nesta ordem**:

- [ ] 1. Ligar o **Backend** (IntelliJ → Run). Esperar `Started BibliotecaApplication`.
- [ ] 2. Ligar o **emulador** (Android Studio → Device Manager → ▶). Esperar a tela inicial.
- [ ] 3. Rodar o `adb reverse` (seção 6.3) e conferir com `reverse --list`.
- [ ] 4. Abrir o app (▶ Run, ou pelo ícone no emulador).
- [ ] 5. Conferir que os 4 livros aparecem.
- [ ] 6. **Reiniciar o Backend** antes de começar, se você já cadastrou algum livro, para o ID do Pequeno Príncipe ser o 5.
- [ ] 7. Ter o **PDF de Mobile** aberto.

> Depois do primeiro Run, o app **já fica instalado no emulador**. Se faltar internet no dia, você ainda consegue abrir o app pelo ícone, e ele conversa com o Backend pelo `127.0.0.1`, que **não precisa de internet**. O Android Studio pode reclamar de sincronização sem rede; é só ignorar.

---

## 9. Se der problema (tabela de socorro)

| Sintoma | Causa provável | O que fazer |
|---|---|---|
| App mostra "Erro de conexão: failed to connect to /127.0.0.1 (port 8089)" | Backend desligado, ou o `adb reverse` sumiu | Ligar o Backend e rodar o `adb reverse` de novo |
| "Port 8089 was already in use" ao ligar o Backend | Já existe um Backend rodando | Parar o antigo (quadrado vermelho no IntelliJ) e rodar de novo |
| Emulador não liga ou fica muito lento | Virtualização desligada, pouca memória | Ver a seção 2; fechar outros programas |
| Tela preta no emulador depois de instalar | Normal logo após reinstalar | Abrir o app pelo ícone |
| Gradle Sync falha | Sem internet (ele baixa o Java 25 e o Gradle), ou Android Studio antigo | Conferir o Wi-Fi; atualizar o Android Studio |
| Maven não baixa as bibliotecas | Sem internet | Conferir o Wi-Fi e clicar em *Reload All Maven Projects* |
| `adb devices` não mostra o emulador | O serviço do adb travou | `adb kill-server`, depois `adb start-server`, depois `adb devices` |
| Livro cadastrado antes sumiu | O banco é em memória | É o comportamento normal: ao reiniciar, voltam só os 4 livros |
| `adb` não é encontrado | O caminho do SDK é outro | Ajustar o caminho (seção 6.3) |

---

## 10. Plano B (se tudo falhar na hora)

Tenha **sempre** estes arquivos à mão, de preferência abertos no notebook e também no celular:

1. Os **3 PDFs** (explicação do Backend, explicação do Mobile e este passo a passo).
2. **Prints** da tela do app funcionando (lista, busca, 404, cadastro).
3. Se possível, um **vídeo curto** do app rodando no emulador.

Com isso, mesmo que o emulador falhe, você apresenta o código e explica o funcionamento.
