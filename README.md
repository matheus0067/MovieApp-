# 🎬 MovieApp - Kotlin + Jetpack Compose

## 📌 Descrição
O **MovieApp** é um aplicativo nativo Android desenvolvido em **Kotlin** com **Jetpack Compose** que permite aos usuários **buscar filmes, favoritar e assistir trailers**.

---

## 🚀 Tecnologias Utilizadas
- **Kotlin** + **Jetpack Compose**
- **Arquitetura MVVM + Repository Pattern**
- **Coroutines + Flow** para operações assíncronas
- **Retrofit** para consumo da API TMDB
- **Room** para persistência dos filmes favoritos
- **ExoPlayer** para reprodução de trailers
- **Hilt (Dagger)** para injeção de dependência
- **Paging 3** para paginação de dados
- **MockK e JUnit** para testes automatizados
- **CI/CD com Fastlane e GitHub Actions**

---

## 📱 Funcionalidades
✅ **Busca automática de filmes** conforme o usuário digita  
✅ **Lista de favoritos persistida no banco de dados (Room)**  
✅ **Tela de detalhes do filme** com sinopse, imagens e trailer  
✅ **Player customizado para trailers (ExoPlayer)**  
✅ **Suporte a diferentes tamanhos de tela (responsividade)**  
✅ **Testes automatizados (JUnit, MockK, UI Test - Espresso/Compose)**  

---## 🔧 Como Rodar o Projeto

### ✅ **Pré-requisitos**
- Android Studio **Giraffe | 2023.3.1** ou superior  
- Conta no **TMDB** para obter a API Key  
- Configurar o arquivo `local.properties` com:  
```properties
TMDB_API_KEY="sua_chave_aqui"
