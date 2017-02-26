# Übung 2 - Eigenes MapReduce Beispiel

### Szenario

Als fortführendes Beispiel wurde eine (triviale) Personen Datenbank ausgewählt. Diese wird durch die Klasse **GeneratorMain** aus dem Package **de.fhms.bde.uebung2.data** mit randomisierte Werte erstellt und als Textdatei exportiert.

Das Geschlecht wird zufällig ausgewählt und sollte gleich verteilt (50%/50%) sein. Das Alter wird in dem Intervall 10-100 generiert. Bei der Größe wird von einer Basisgröße ausgegangen und mit einem Zufallswert summiert. Die Basisgröße unterscheidet sich dabei je nach Mann oder Frau.

```
GENDER     AGE      HEIGHT
MALE	   12	   175
MALE	   20	   207
MALE	   63	   163
FEMALE	 99	   148
MALE	   25	   169
FEMALE	 73	   154
MALE	   90	   176
FEMALE	 71	   178
MALE	   46	   189
MALE	   38	   208
MALE	   56	   202
MALE	   37	   161
MALE	   94	   197
MALE	   76	   174

```

### Build Prozess
Dieses Projekt wurde als **Maven** Projekte initialisiert. Zum kompilieren und builden der notwendigen Dateien muss Maven ausgeführt werden.

```
mvn clean install

```

Dieses Beispiel wurde für die **Cloudera Quickstart VM** entwickelt und dementsprechend sind in der *pom.xml* die Dependencies dafür eingetragen.

### VM Anpassung

Um für späteres Logging in Hadoop auf der CDH zu aktivieren muss noch folgende Eigenschaft in */etc/hadoop/conf/yarn-site.xml* eingetragen werden:
```
<property>
         <name>yarn.log.server.url</name>
         <value>http://quicktstart.cloudera:19888/jobhistory/logs</value>
</property>
```


### Vorbereitung
Die kompilierte *.jar* Datei und die *person.txt* muss zunächst in die Cloudera VM kopiert werden. Dies kann über *WinSCP* oder die Konsole erfolgen.

```
scp -r -P 2222 root@localhost:/resource/person.txt ~/uebung2
```

Im Anschluss wird die generierte *person.txt* ins HDFS File System kopiert. Dazu können die Befehle aus Übung 1 verwendet werden.

```
hdfs dfs -mkdir uebung2
hdfs dfs -put person.txt uebung2
hdfs dfs -ls uebung2

```

### Ausführung
Nun kann die *uebung2-1.0.jar* unter Angabe der Job-Definition ausgeführt werden und das Ergebnis betrachtet werden. Als Datenquelle wird die *person.txt* angegeben.
```
yarn jar uebung2-1.0.jar de.fhms.bde.uebung2.mr.GenderAgeDriver uebung2/person.txt uebung2/out

```
Output Beispiel nach Häufigkeit:
```
hdfs dfs -cat uebung2/out/part-r-00000 | sort -g -k2 -r | less
```

Demnach sind 71 Personen Weiblich und 20 Jahre alt:
```
FEMALE(20)      71
MALE(54)        67
MALE(28)        67
FEMALE(74)      66
MALE(26)        65
FEMALE(40)      65
MALE(37)        64
FEMALE(58)      64
MALE(31)        63
FEMALE(35)      63
MALE(65)        62
MALE(63)        62
...
```
In der Job Anzeige kann man die durchschnittliche Zeit betrachten, was bei der geringen Datenmenge von 10000 Einträgen lediglich 22 Sekunden betrug.
```
Job Name: 	genderage_count
User Name: 	cloudera
Queue: 	root.cloudera
State: 	SUCCEEDED
Uberized: 	false
Submitted: 	Sun Jan 08 04:08:37 PST 2017
Started: 	Sun Jan 08 04:08:47 PST 2017
Finished: 	Sun Jan 08 04:09:10 PST 2017
Elapsed: 	22sec
Diagnostics: 	
Average Map Time 	8sec
Average Shuffle Time 	7sec
Average Merge Time 	0sec
Average Reduce Time 	1sec
```
