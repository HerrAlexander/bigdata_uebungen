# Übung 6 - Automatisierter Workflow mit Oozie
### Szenario

Die bisherigen Ergbnisse aus den vorherigen Übungen sollen über Oozie in einem Workflow automatisiert ausgeführt werden.

Zunächst soll dabei das HBase Beispiel aus Übung 5 in einem Prozess eingebunden werden.


### Vorbereitung
Zunächst muss der *oozie* Ordner mit dem eigentlichen Workflow in die Cloudera VM kopiert werden. Die kompilierten *.jar* Dateien, die später ausgeführt werden, müssen im Anschluss in den *ooziee/lib* kopiert werden. Dies kann über *WinSCP* oder die Konsole erfolgen.

```
scp -r -P 2222 root@localhost:/oozie ~/oozie
scp -r -P 2222 root@localhost:/resource/uebung5-1.0.jar ~/oozie/lib
```

### Ausführung
Der oozie Ordner muss zunächst ins HDFS kopiert werden.
```
hdfs dfs -put oozie /user/cloudera/oozie
```

Danach kann der Worfklow über die Konsole gestartet werden.

```
oozie job -oozie http://localhost.com:11000/oozie -config job.properties -run
```

Zunächst konnte job.properties nicht eingelesen werden, diese muss von der CDH gelesen werden.

```
java.io.IOException: configuration is not specified
```

Der angepasste Befehl sieht wie folgt aus (abhängig von den Speicherort der Config-Datei):
```
[cloudera@quickstart uebung]$ oozie job -oozie http://localhost:11000/oozie -config oozie/job.properties -run
```

Unter der Webkonsole kann der Fortschritt betrachtet werden. Nach kurzer Zeit ist der Workflow erfolgreich abgeschlossen. Das Ergbnis ist dabei analog zu Übung 6.
