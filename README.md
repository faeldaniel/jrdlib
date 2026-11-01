# jrdlib

Biblioteca de auxílio à programação em Java: utilitários para Swing, datas,
formatação de textos e números, validações (CPF, CNPJ, chave de NF-e),
conexões JDBC, envio de e-mail, consulta de CEP, impressão e identificação
de hardware.

Este repositório contém apenas o código-fonte, na pasta `src/`.

## Requisitos

- Java 8 ou superior

## Dependências

| Biblioteca | Versão | Usada por |
|---|---|---|
| dom4j | 2.2.0 | `BuscaCep`, `ChecagensRemota` |
| JSON-java (org.json) | 20260814 | `BuscaCep` |
| Jakarta Mail | 2.0.1 | `EmailAux` |
| Jakarta Activation API | 2.0.1 | `EmailAux` |
| Joda-Time | 2.9.7 | `Datas` |
| SwingX | 1.7.6 | `SwingsAuxilios` |
| BalloonTip | 1.2.4.1 | `BalloonToolTip` |

Os drivers JDBC usados por `Conexao` (PostgreSQL, Firebird e Access) não são
necessários para compilar: a aplicação que usar a biblioteca deve incluí-los no
classpath.

## Como usar

Adicione a pasta `src/` como pasta de código-fonte do seu projeto (ou copie os
pacotes `com.jrdlib` e `com.splashs` para ele) e coloque as dependências acima
no classpath. Os fontes estão em UTF-8; ao compilar com `javac`, use
`-encoding UTF-8`.

## Conteúdo

### `com.jrdlib`

| Classe | Descrição |
|---|---|
| `Audio` | Reprodução de arquivos de áudio |
| `BalloonToolTip` | Dicas e mensagens de erro em balão sobre componentes Swing |
| `BuscaCep` | Consulta de endereço pelo CEP via [OpenCEP](https://opencep.com/) |
| `CellRendererAlinhamentoCentro`, `CellRendererAlinhamentoDireita` | Alinhamento de colunas de `JTable` |
| `CellRendererParaValoresDecimais3Casas`, `CellRendererParaValoresMonetario` | Exibição de valores decimais e monetários em `JTable` |
| `ChecagensRemota` | Verificação remota de versão e atualizações via XML |
| `Compactacao` | Compactação de um ou vários arquivos em ZIP |
| `Conexao` | Conexões JDBC (PostgreSQL, Firebird e Access) e execução de SQL |
| `Cryptografia` | Hash MD5 com senha/salt |
| `Datas` | Conversão, formatação e cálculos com datas e horas |
| `EmailAux` | Envio de e-mails via SMTP, com suporte a STARTTLS |
| `FuncoesRede` | Endereço IP, MAC e nome da máquina |
| `GeradorLogs` | Gravação de logs em arquivo |
| `ImageForPanel` | `JPanel` que desenha uma imagem de fundo |
| `Impressoes` | Impressão na impressora padrão, por nome da impressora ou nas portas paralela e USB |
| `ManipulacaoArquivos` | Cópia de arquivos, criação e exclusão de pastas |
| `RowSorters` | `RowSorter` padrão para `JTable` |
| `SerialUtils` | Identificadores de hardware (placa-mãe, CPU e disco) no Windows, Linux e macOS |
| `Sistema` | Dados globais da aplicação (empresa, usuário, operador e versão) |
| `SituacoesPadroes` | Textos e cores padrão para situações (existe, não existe, incorreto, em branco) |
| `SwingsAuxilios` | Utilitários Swing: `JInternalFrame`, `JSpinner` monetário, `JFileChooser` em português, máscara de CPF/CNPJ e `JXErrorPane` |
| `Tratamentos` | Formatação e limpeza de textos e números (CPF/CNPJ, CEP, telefone, valores monetários) |
| `TratamentosImagens` | Ícones em `JLabel` e gravação de imagens |
| `Validacoes` | Validação de CPF, CNPJ e chave de NF-e |
| `ValoresPorExtenso` | Valores por extenso |

### `com.jrdlib.filders`

Filtros de digitação (`PlainDocument`) e máscaras para campos de texto:
`upCase`, `upCaseAlgunsCaracteres`, `Numeros`, `NumerosMonetario` e `Maskaras`.

### `com.splashs`

Componentes para telas de abertura (*splash screens*): `BackGroundPanel`,
`ScreenPanel` e `ShapelyImagePane`.
