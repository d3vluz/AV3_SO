# Relatório do Simulador de Sistema de Arquivos com Journaling

## Metodologia

O simulador de sistema de arquivos foi desenvolvido em **Java**, utilizando uma estrutura em estilo **MVC (Model-View-Controller)** para organizar o código. O sistema é capaz de receber comandos de um shell (linha de comando) e realizar operações como copiar, apagar, renomear, criar e listar arquivos e diretórios. Cada operação é registrada utilizando um mecanismo de **Journaling** para garantir a integridade dos dados e possibilitar auditoria.

As funcionalidades foram desenvolvidas para serem chamadas por métodos Java que, quando executados, mostram o resultado na tela, permitindo uma simulação interativa semelhante ao comportamento de um sistema operacional.

---

## Parte 1: Introdução ao Sistema de Arquivos com Journaling

### Descrição do Sistema de Arquivos

Um **sistema de arquivos** é um componente fundamental de qualquer sistema operacional, pois ele organiza e fornece uma maneira de armazenar e recuperar dados em um dispositivo de armazenamento, como um disco rígido. Ele permite que os dados sejam acessados de maneira eficiente e segura, e organiza os dados em estruturas hierárquicas de **arquivos** e **diretórios**.

O sistema de arquivos simulado neste projeto inclui operações básicas que são comumente encontradas em sistemas operacionais reais, como a criação, renomeação, cópia e exclusão de arquivos e diretórios. Além disso, a operação de **listar conteúdo** permite que o usuário navegue pelas pastas, verificando o conteúdo de um diretório específico.

### Journaling

**Journaling** é uma técnica utilizada por sistemas de arquivos modernos para aumentar a confiabilidade dos dados armazenados. Ele funciona registrando as operações a serem realizadas antes de realmente alterá-las no sistema de arquivos principal. Este registro é mantido em um arquivo de **log** ou **journal**, o que ajuda a garantir que o sistema possa se recuperar adequadamente em caso de falhas, como quedas de energia ou erros críticos.

Existem diversos tipos de journaling, entre os quais:

1. **Write-Ahead Logging**: Registra as alterações a serem feitas antes de aplicá-las, garantindo que as mudanças possam ser repetidas ou revertidas em caso de falha.
2. **Log-Structured Journaling**: Utiliza um log para manter registros de todas as operações realizadas no sistema de arquivos. Isso ajuda a otimizar a escrita, especialmente em sistemas com alta taxa de modificações.

No simulador, implementamos um journaling simples utilizando a abordagem **Write-Ahead Logging**. Todas as operações de manipulação de arquivos e diretórios são registradas em um arquivo de log antes de serem aplicadas, permitindo que as mudanças sejam verificadas e rastreadas.

---

## Parte 2: Arquitetura do Simulador

### Estrutura de Dados

O sistema de arquivos é implementado utilizando **classes Java** para representar as principais entidades envolvidas. A arquitetura foi desenvolvida em três componentes principais seguindo a arquitetura MVC:

1. **Model**: Representa a lógica do sistema de arquivos.
   - A classe `FileSystem` contém os métodos para gerenciar arquivos e diretórios.
   - A classe `Journal` mantém o registro de todas as operações, garantindo a integridade e a possibilidade de auditoria das ações realizadas.

2. **View**: Responsável pela interface com o usuário.
   - A classe `ShellView` fornece um shell interativo que recebe comandos do usuário.

3. **Controller**: Coordena a interação entre o usuário e o modelo.
   - A classe `FileSystemController` interpreta os comandos fornecidos no shell e aciona os métodos apropriados no `FileSystem`.

### Journaling

O **journaling** é implementado pela classe `Journal`, que registra cada operação realizada no sistema de arquivos em um arquivo de log (`file_system_journal.log`). Cada operação importante, como **criação, cópia, renomeação e exclusão de arquivos ou diretórios**, é registrada antes de ser executada.

**Estrutura do Log**:
- O log contém linhas de texto que indicam a operação realizada, o nome do arquivo ou diretório afetado, e a data e hora da execução.
- Por exemplo: "Tue Mar 15 10:00:00 - Copiando arquivo: /path/source.txt para /path/destination.txt"


**Operações Registradas**:
- **Criação** (`mkdir`), **Renomeação** (`rename`), **Exclusão** (`delete`), **Cópia** (`copy`), entre outras operações, são registradas no log antes da execução, garantindo que cada modificação seja documentada.

---

## Parte 3: Implementação em Java

### Classe `FileSystem`

**Descrição**  
A classe `FileSystem` implementa o simulador do sistema de arquivos, incluindo métodos que permitem manipular arquivos e diretórios. Ela mantém uma instância de `Journal` para registrar todas as operações, permitindo recuperação e auditoria dos dados.

**Principais Métodos**:
- `copyFile(String sourcePath, String destinationPath)` - Copia um arquivo de uma localização para outra.
- `deleteFile(String filePath)` - Apaga um arquivo específico.
- `renameFile(String oldName, String newName)` - Renomeia um arquivo ou diretório.
- `createDirectory(String dirPath)` - Cria um novo diretório.
- `deleteDirectory(String dirPath)` - Remove um diretório, caso esteja vazio.
- `listDirectory(String dirPath)` - Lista o conteúdo de um diretório.

Cada método faz um registro da operação no `Journal`, garantindo que a ação seja documentada antes de ser executada.

### Classe `Journal`

**Descrição**  
A classe `Journal` gerencia o log de operações realizadas no sistema de arquivos. Ela escreve cada operação em um arquivo de log (`file_system_journal.log`) e é utilizada pela classe `FileSystem` para garantir que cada ação seja registrada adequadamente.

**Função Principal**:
- `logOperation(String operation)` - Registra a operação recebida no log, incluindo a data e hora.

### Classe `ShellView`

**Descrição**  
A classe `ShellView` oferece uma interface de terminal ao usuário, onde os comandos podem ser inseridos e executados. Ela é responsável por receber a entrada do usuário, exibindo o resultado das operações quando necessário.

**Funções Principais**:
- `getShellInput()` - Coleta um comando do usuário.
- `showOutput(String message)` - Exibe uma mensagem no terminal.

### Classe `FileSystemController`

**Descrição**  
A classe `FileSystemController` conecta o shell (`ShellView`) com as operações do sistema de arquivos (`FileSystem`). Ela interpreta os comandos fornecidos pelo usuário e aciona o método apropriado no sistema de arquivos.

**Função Principal**:
- `startShell()` - Inicia o shell e fica em um loop contínuo para receber comandos até que o usuário opte por sair (`exit`).
- `handleCommand(String command)` - Recebe e interpreta os comandos do usuário, repassando para a `FileSystem` executar a operação correspondente.

**Comandos Suportados**:
- `copy <sourcePath> <destinationPath>` - Copiar um arquivo.
- `delete <filePath>` - Apagar um arquivo.
- `rename <oldName> <newName>` - Renomear um arquivo ou diretório.
- `mkdir <dirPath>` - Criar um diretório.
- `rmdir <dirPath>` - Remover um diretório.
- `list <dirPath>` - Listar o conteúdo de um diretório.
- `exit` - Sair do shell.

### Classe `Main`

**Descrição**  
A classe `Main` é o ponto de entrada da aplicação. Ela inicializa as dependências necessárias (como `Journal`, `FileSystem`, `ShellView`, `FileSystemController`) e executa o método `startShell()` para começar a interação.

**Função Principal**:
- `main(String[] args)` - Inicializa as classes e inicia o shell.

---

## Fluxo de Execução

1. **Inicialização**  
 O método `main()` cria instâncias das classes `Journal`, `FileSystem`, `ShellView` e `FileSystemController`, e então inicia o shell interativo.

2. **Shell Interativo**  
 O usuário insere comandos no shell. O `FileSystemController` interpreta esses comandos e executa as operações usando a classe `FileSystem`.

3. **Journaling**  
 Antes de qualquer operação ser executada no sistema de arquivos, a operação é registrada no `Journal`. Isso fornece uma maneira segura de auditar e potencialmente reverter alterações.
