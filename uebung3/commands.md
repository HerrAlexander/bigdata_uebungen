# Übung 3 - Erweitertes MapReduce Beispiel

### Szenario

Als Datengrundlage wird die triviale *Personendatenbank* aus Übung 2 verwendet mit folgenden Daten verwendet:

```
GENDER     AGE      HEIGHT
MALE	   12	   175
MALE	   20	   207
...
```

Im erweiterteten Beispiel soll die Durchschnittsgröße berechnet werden. Dabei kann als Key sowohl das Geschlecht genommen werden, als auch in Verbindung mit dem Alter. Mit einer realen Datenbank könnte man so die Veränderung der Größe eines Menschen im Laufe seines Lebens analysieren.  

### Build Prozess
Dieses Projekt wurde als **Maven** Projekte initialisiert. Zum kompilieren und builden der notwendigen Dateien muss Maven ausgeführt werden.

```
mvn clean install

```

Dieses Beispiel wurde für die **Cloudera Quickstart VM** entwickelt und dementsprechend sind in der *pom.xml* die Dependencies dafür eingetragen.


### Vorbereitung
Die kompilierte *.jar* Datei und die *person.txt* muss zunächst in die Cloudera VM kopiert werden. Dies kann über *WinSCP* oder die Konsole erfolgen.

```
scp -r -P 2222 root@localhost:/resource/person.txt ~/uebung3
scp -r -P 2222 root@localhost:/target/uebung3-1.0.jar ~/uebung3
```

Im Anschluss wird die generierte *person.txt* ins HDFS File System kopiert. Dazu können die Befehle aus Übung 1 verwendet werden.

```
hdfs dfs -mkdir uebung3
hdfs dfs -put person.txt uebung3
hdfs dfs -ls uebung3

```

### Implementierung
In der Anfangsphase wurde versucht, dass Projekt mit einer Combiner-Klasse zu erweitern, die zwischen dem Mapper und Reducer die Daten weiter aufbereitet. So sollte der Combiner bereits die Summierung der Werte durchführen, während der Reducer lediglich für die Berechnung des Mittelwertes zuständig war.

Wie sich herausstellte, war dies für eine einfache Durchschnittsberechnung nicht notwendig und konnte im Reducer direkt implementiert werden.

```
int sum = 0;
int count = 0;
for(IntWritable value : values) {
    sum += value.get();
    count++;
}
average.set(sum / (double) count);
```

### Ausführung
Nun kann die *uebung3-1.0.jar* unter Angabe der Job-Definition ausgeführt werden und das Ergebnis betrachtet werden. Als Datenquelle wird die *person.txt* angegeben.
```
yarn jar uebung3-1.0.jar de.fhms.bde.uebung3.mr.PersonAverageDriver uebung3/person.txt uebung3/out

```

Das reduzierte Ergebnis auf das Geschlecht sieht wie folgt aus:
```
[cloudera@quickstart uebung]$ hdfs dfs -cat uebung3/out/part-r-00000
FEMALE	164.71630499701374
MALE	184.69226225634966
```

Das reduzierte Ergebnis auf das Geschlecht in Verbindung mit dem Alter sieht wie folgt aus:
```
MALE(40)	183.92307692307693
MALE(41)	188.6734693877551
MALE(42)	181.75
MALE(43)	187.22916666666666
MALE(44)	185.57142857142858
MALE(45)	187.29787234042553
MALE(46)	184.28571428571428
MALE(47)	185.82758620689654
MALE(48)	181.66666666666666
MALE(49)	184.88095238095238
MALE(50)	184.825
MALE(51)	181.7058823529412
MALE(52)	180.16981132075472
```

Das Ergebnis ist auf den trivialen *PersonGenerator.java* zurückzuführen. Ausgehend aus den Basiswerten (Frau: 140; Mann: 160) und einer Addition von maximal 50, kann man feststellen, dass die Werte gleichverteilt sind und im Mittel (*Gesetz der großen Zahlen*) 165cm bzw 185cm betragen.
