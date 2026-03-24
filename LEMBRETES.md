# 📚 Lembretes de Programação

## 📁 Arquivos Interessantes

| Arquivo           | Descrição                       |
| ----------------- | ------------------------------- |
| `~/.bash_history` | 📜 Lista de comandos executados |
| `~/.vimrc`        | ⚙️ Configurações do VIM         |
| `~/.profile`      | 👤 Configurações do seu Usuário |

## 🎮 Principais Comandos no VIM

### ✏️ Modo de Inserção

| Comando | Descrição                |
| ------- | ------------------------ |
| `i`     | Abre o modo de inserção  |
| `ESC`   | Fecha o modo de inserção |

### 💾 Salvando e Saindo

| Comando | Descrição             |
| ------- | --------------------- |
| `:w`    | Salvar                |
| `:q`    | Sair                  |
| `:q!`   | Forçar saída          |
| `:wq!`  | Salvar e forçar saída |

### 🔍 Busca e Substituição

| Comando                        | Descrição                            |
| ------------------------------ | ------------------------------------ |
| `? PALAVRA`                    | Procurar ocorrências de PALAVRA      |
| `:%s/STRING_OLD/STRING_NEW/gc` | Substituir STRING_OLD por STRING_NEW |

### ✂️ Operações com Linhas e Palavras

| Comando    | Descrição               |
| ---------- | ----------------------- |
| `yy`       | Copiar linha            |
| `NUMEROyy` | Copiar NUMERO linhas    |
| `dd`       | Excluir linha           |
| `NUMEROdd` | Excluir NUMERO linhas   |
| `dw`       | Excluir palavra         |
| `NUMEROdw` | Excluir NUMERO palavras |
| `p`        | Colar                   |

### 📋 Múltiplos Arquivos

| Comando       | Descrição                       |
| ------------- | ------------------------------- |
| `:sp ARQUIVO` | Abre o arquivo ARQUIVO          |
| `ww`          | Alternar entre arquivos abertos |
| `CTRL+V`      | Abre o modo de visualização     |

## 🐧 Comandos Linux/Shell

`man` • `exit` • `cd` • `ls` • `ls -l -t -h` • `rm` • `cp` • `mkdir` • `clear` • `grep` • `grep "PALAVRA" ARQUIVO` • `diff` • `diff ARQUIVO1 ARQUIVO2` • `ssh` • `sftp` • `wget` • `chmod` • `javac` • `java` • `tar -zcvf arquivo.tar.gz pasta` • `tar -zxvf arquivo.tar.gz`

## ☕ Java - Compilação e Execução

```bash
javac Programa.java                              # Compilar
java Programa                                    # Executar
java Programa < entrada.in                       # Com entrada padrão
java Programa > saida.in                         # Com saída padrão
java Programa < entrada.in > saida.in            # Com entrada e saída
```

## 🔧 C++ - Compilação e Execução

```bash
g++ fonte.cc -o objeto                           # Compilar
./objeto                                         # Executar
./objeto < entrada.in                            # Com entrada padrão
./objeto > saida.in                              # Com saída padrão
./objeto < entrada.in > saida.in                 # Com entrada e saída
```

## 🐛 GDB - Depurador

```bash
gdb objeto                                       # Iniciar GDB
run                                              # Executar programa
bt                                               # Acessar pilha de chamadas
```
