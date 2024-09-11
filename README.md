# PROJETO MYFOOD

Aluno: Ricardo Vinicius de Almeida Fernandes

## Como Rodar o Projeto

Existem duas formas de rodar o código: usando a linha de comando ou através da IDE IntelliJ IDEA.

### 1. **Compilando e Executando pela Linha de Comando**

**1.1. Compilação**

Para compilar o projeto, execute o seguinte comando na linha de comando:

```bash
javac -cp "lib/*" -d bin src/*.java src/br/ufal/ic/p2/myfood/*.java src/br/ufal/ic/p2/myfood/**/*.java
```

**Explicação dos parâmetros:**
- `-cp "lib/*"`: Adiciona todos os arquivos `.jar` no diretório `lib` ao classpath.
- `-d bin`: Especifica o diretório onde os arquivos `.class` serão gerados.
- `src/*.java src/br/ufal/ic/p2/myfood/*.java src/br/ufal/ic/p2/myfood/**/*.java`: Compila todos os arquivos `.java` nos diretórios especificados.

**1.2. Execução**

Para executar o projeto, use o comando:

```bash
java -cp "bin:lib/*" Main
```

**Explicação dos parâmetros:**
- `-cp "bin:lib/*"`: Inclui o diretório `bin` e todos os arquivos `.jar` no diretório `lib` no classpath.
- `Main`: Nome da classe principal que contém o método `main`.

### 2. **Compilando e Executando no IntelliJ IDEA**

**2.1. Configuração de Compilação**

O projeto já possui uma configuração de compilação no IntelliJ IDEA. Para compilar:

1. Abra o IntelliJ IDEA.
2. Certifique-se de que o projeto está configurado corretamente com o JDK.
3. Vá para `Build` no menu superior.
4. Selecione `Build Project` ou pressione `Ctrl+F9` (Windows/Linux) ou `Cmd+F9` (Mac) para compilar o projeto.

**2.2. Configuração de Execução**

Para executar o projeto no IntelliJ IDEA:

1. Abra o IntelliJ IDEA.
2. Certifique-se de que a configuração de execução está definida para a classe `Main`.
3. Vá para `Run` no menu superior.
4. Selecione `Run 'Main'` ou pressione `Shift+F10` para executar a aplicação.

A IDE usará a configuração de Run já definida para executar a aplicação.
