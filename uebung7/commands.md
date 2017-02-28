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
Das WordCount Programm kann über die Konsole gestartet werden.

```
spark-submit --class de.fhms.bde.uebung7.JavaWordCount \
--master local --deploy-mode client --executor-memory 1g \
--name wordcount --conf "spark.app.id=wordcount" \
uebung7-1.0.jar hdfs://quickstart.cloudera:8020/uebung7/person.txt
```
