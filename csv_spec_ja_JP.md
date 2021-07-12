#  カスタム機能ドキュメント

このドキュメントは、 のカスタム機能が提供する、
Flow、コマンド、設定定義についての説明及び出力するメッセージの定義について説明する。

- Contents
  - [Information](#Information)
  - [Description](#Description)
  - [Flow List](#Flow-List)
  - [Command List](#Command-List)
  - [Configuration List](#Configuration-List)
  - [Message List](#Message-List)

## Information

本カスタム機能の基本情報は以下の通り。

CSVを扱う機能を提供します。

- Name : `csv`
- Custom Package : `com.epion_t3.csv`

## Description
CSVを扱う機能を提供します。

## Flow List

本カスタム機能が提供するFlowの一覧及び詳細。

|Name|Summary|
|:---|:---|


## Command List

本カスタム機能が提供するコマンドの一覧及び詳細。

|Name|Summary|Assert|Evidence|
|:---|:---|:---|:---|
|[AssertCsvData](#AssertCsvData)|CSVの値を確認します。  |X||

------

### AssertCsvData
CSVの値を確認します。
#### Command Type
- Assert : __Yes__
- Evidence : No

#### Functions
- CSVファイル同士を比較し、値の確認を行います。
- 結果値のCSVファイルは、エビデンスとして取得済である必要があります。
- ファイルフォーマットの指定及び無視カラムインデックスの指定である程度柔軟なアサーションが行えます。
- ファイルフォーマットの指定次第でTSVにも対応が可能となっています。

#### Structure
```yaml
commands : 
  - id: コマンドのID
    command: 「AssertCSVData」固定
    summary: コマンドの概要（任意）
    description: コマンドの詳細（任意）
    fileFormatConfigRef: ファイルフォーマット定義の参照ID # (1)
    expectedDataSetPath: 期待値CSV/TSVの相対パス # (2)
    actualFlowId: 結果値を取得したFlowのFlowIDを指定 # (3)
    ignores: 対象のCSV/TSV内でアサートの対象外としたいカラムのIndex指定を行います。
例：
  ignores:
    - index: 1（headerNameかindexのいずれかが必須）
      headerName: ヘッダ名（現状は、「FileFormatConfiguration」の「firstRecordIsHeader」がTrueの場合のみ利用可能。）

    ignoresConfigPath: 対象のCSV/TSV内でアサートの対象外としたいカラムの設定を記載した設定ファイルのパスを指定します。
設定ファイルは、YAML or JSON で指定します。
例：[
  {
    "index" : "インデックスの数値を指定。headerNameかindexのいずれかが必須",
    "headerName" : "ヘッダ名を指定。現状は、「FileFormatConfiguration」の「firstRecordIsHeader」がTrueの場合のみ利用可能。"
  }
]


```

1. ファイルフォーマットの設定を行っている &#96;Configuration&#96; の参照IDを指定します。
1. 期待値を定義したCSV/TSVファイルへの相対パス。基底ディレクトリはパス解決モードで指定された方式に従います。
1. 結果値を取得したFlowIDを指定。

## Configuration List

本カスタム機能が提供する設定定義の一覧及び詳細。

|Name|Summary|
|:---|:---|
|[FileFormatConfiguration](#FileFormatConfiguration)|CSV/TSVファイルのフォーマットを定義するための設定定義です。  |

------

### FileFormatConfiguration
CSV/TSVファイルのフォーマットを定義するための設定定義です。
#### Description
- CSV/TSVファイルのフォーマットを定義するための設定定義です。
- 本設定定義は、「Commons CSV」の「CSVFormat」をベースとしています。

#### Structure
```yaml
commands : 
  - configuration: 「FileFormatConfiguration」固定
    id: 設定のID
    summary: 設定の概要（任意）
    description: 設定の詳細（任意）
    delimiter: 要素のデリミタ文字を指定します。省略した場合、カンマ（,）が選択されます。
    escape: エスケープに使用する文字を指定します。
    ignoreEmptyLines: 空行をSKIPするかを真偽値で指定します。true の場合SKIPします。
    quote: 要素の囲い文字を指定します。
    recordSeparator: レコード区切り文字を指定します。改行コードの指定となりますが、「CRLF」もしくは「LF」のいずれかを指定して下さい。
    trim: 要素の前後の空白を除去するかを真偽値で指定します。true の場合Trim処理を行います。デフォルトでは falseと扱います。
    firstRecordIsHeader: 1レコード目がヘッダを表すかどうかを真偽値で指定します。 trueの場合、ヘッダ行を比較SKIPします。デフォルトでは falseと扱います。

```


## Message List

本カスタム機能が出力するメッセージの一覧及び内容。

|MessageID|MessageContents|
|:---|:---|
|com.epion_t3.csv.err.0008|比較無視カラムの設定ファイルが見つかりません. 比較無視カラム設定ファイル : {0}|
|com.epion_t3.csv.err.0007|比較無視カラムの設定ファイルは「yaml」「yml」「json」のいずれかで指定して下さい. 比較無視カラム設定ファイル : {0}|
|com.epion_t3.csv.err.0009|比較無視カラムの設定ファイルを正しく読み込めませんでした. 比較無視カラム設定ファイル : {0}|
|com.epion_t3.csv.err.0002|期待値ファイルのCSV/TSV読み込みに失敗しました. Path: {0}|
|com.epion_t3.csv.err.0001|期待値ファイルが見つかりません. Path: {0}|
|com.epion_t3.csv.err.0004|結果値を取得したFlowIDは必須です.|
|com.epion_t3.csv.err.0003|期待値ファイルのパスは必須です.|
|com.epion_t3.csv.err.0006|無視カラムの指定には、ヘッダ名称かインデックスの指定のいずれかが必須です.|
|com.epion_t3.csv.err.0005|指定されたエンコーディングは不正です. エンコーディング : {0}|
