# ３年前に授業で作成したreversi_programの備忘録

人vsCPUでオセロをすることができるプログラム

コメントアウト部分を編集することで人vs人や、CPUvsCPUでの対戦も可能

playerは自分のターンになったら、以下の手順で入力を行う
[自分の駒を置きたい行番号]
[Enter]
[自分の駒を置きたい列番号]
[Enter]

その場所に駒を置くことができない場合は、もう一度入力を求められる。


# 実行方法
$ javac GameMain.java
$ java GameMain


