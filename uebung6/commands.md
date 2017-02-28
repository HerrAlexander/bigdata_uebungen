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
Der Worfklow kann über die Konsole gestartet werden.

```
oozie job -oozie http://quickstart.cloudera.com:11000/oozie -config job.properties -run
```

Unter der Webkonsole kann der Fortschritt betrachtet werden. Das Ergbnis ist dabei analog zu Übung 6.
