# Übung 7 - Apache Spark

Um weitergehende Kenntnisse zu erlangen, wurde Apache Spark näher betrachtet und angewendet. Das Beispiel mit der Persondatenbank soll dabei beibehalten werden, im ersten Schritt soll allerdings das WordCount Beispiel verwendet werden

### Build Prozess
Das Projekt kann wie bisher mit Maven kompiliert werden.

```
mvn clean install

```

Dieses Beispiel wurde für die **Cloudera Quickstart VM** entwickelt und dementsprechend sind in der *pom.xml* die Dependencies dafür eingetragen.

Für Apache Spark mussten zusätzlich weitere Abhängigkeiten eingetragen werden.


### Vorbereitung
Die kompilierte *.jar* Datei muss zunächst in die Cloudera VM kopiert werden. Dies kann über *WinSCP* oder die Konsole erfolgen.

```
scp -r -P 2222 root@localhost:/target/uebung7-1.0.jar ~/uebung7
```


### Implementierung

Das Apache Spark - WordCount Programm wurde mit diesem Tutorial realisiert:
http://www.javaworld.com/article/2972863/big-data/open-source-java-projects-apache-spark.html

### Ausführung
Das WordCount Programm kann über die Konsole gestartet werden. Als Eingabe wird das Buch *pg27827.txt* angegeben, welches zuvor in das HDFS kopiert worden ist.

```
spark-submit --class de.fhms.bde.uebung7.spark.JavaWordCount --master local --deploy-mode client --executor-memory 1g --name wordcount --conf "spark.app.id=wordcount" uebung7-1.0.jar hdfs://quickstart.cloudera:8020/user/cloudera/pg27827.txt
```

Die Variante mit funktionaler Programmierung ist nicht unterstützt, da Java 8 benötigt wird, die auf der CDH standardmäßig nicht installiert ist:
```
Exception in thread "main" java.lang.UnsupportedClassVersionError: de/fhms/bde/uebung7/spark/JavaWordCount : Unsupported major.minor version 52.0
```

Nach Veränderung der Build-Version auf 1.7 kann die *.jar* ausgeführt werden und das Ergebnis betrachtet.

```
hdfs dfs -cat /user/cloudera/output/part-00000 | sort -g -k2 -r | less

(Zola,,1)
(Zodiac,,1)
(zenana,1)
(youthful,1)
(youth;,1)
(youth,1)
(youth,,1)
(YOURSELF.,1)
(your,16)
(younger,8)
(YOUNGER,1)
(young,30)
(Young,1)
(young;,1)
(young,,1)
(you,64)
(YOU,6)
(you.,4)
(you,,4)
(You,12)

```
