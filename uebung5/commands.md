# Übung 5 - Erweitertes HBase Beispiel

### Szenario

Mit Java soll die Personendatenbank in Hbase automatisch angelegt und mit Daten gefüllt werden. Die bisherige Testdatei wird dabei überflüssig.


### Build Prozess
Dieses Projekt wurde als **Maven** Projekte initialisiert. Zum kompilieren und builden der notwendigen Dateien muss Maven ausgeführt werden.

```
mvn clean install

```

Dieses Beispiel wurde für die **Cloudera Quickstart VM** entwickelt und dementsprechend sind in der *pom.xml* die Dependencies dafür eingetragen.


### Vorbereitung
Die kompilierte *.jar* Datei muss zunächst in die Cloudera VM kopiert werden. Dies kann über *WinSCP* oder die Konsole erfolgen.

```
scp -r -P 2222 root@localhost:/resource/uebung5-1.0.jar ~/uebung5
```

### Implementierung

In der Klasse *HBaseConnector* ist die eigentliche Logik zur HBase Schnittstelle implementiert. Neben der eigentlichen Verbindung zur HBase können folgende Funktionen durchgeführt werden:

* Tabelle mit Spaltendefinition erstellen
* Daten in der angelegten Tabelle speichern
* Tabelle scannen und Daten zurückgeben


### Ausführung
Das Programm kann über die Konsole gestartet werden.

```
java -jar uebung5-1.0.jar
```
Im ersten Teil wird die Tabelle *PersonDatabase* mit den entsprechenden Columns angelegt.

```
connector.createPersonTable();
```

Der *PersonGenerator* wurde in so weit angepasst, dass ein erzeugter Eintrag direkt in die Datenbank gespeichert werden kann. In diesem Beispiel werden 10000 Personen mit eigener ID in der Datenbank angelegt.

```
connector.insertRandomPersons(10000);
```

Im Anschluss wird ein Scan auf die HBase Table durchgeführt und die zuvor erstellten Daten ausgeben:
```
Found row : keyvalues={ff9f3db7-b260-495c-aad6-9621ce6dcaa1/persons:age/1488047749372/Put/vlen=2/seqid=0, ff9f3db7-b260-495c-aad6-9621ce6dcaa1/persons:gender/1488047749372/Put/vlen=4/seqid=0, ff9f3db7-b260-495c-aad6-9621ce6dcaa1/persons:height/1488047749372/Put/vlen=3/seqid=0}
gender: MALE
age: 54
height: 209
Found row : keyvalues={ffa3c859-1c89-477c-b0ed-a3ca1f2ae002/persons:age/1488047749362/Put/vlen=2/seqid=0, ffa3c859-1c89-477c-b0ed-a3ca1f2ae002/persons:gender/1488047749362/Put/vlen=4/seqid=0, ffa3c859-1c89-477c-b0ed-a3ca1f2ae002/persons:height/1488047749362/Put/vlen=3/seqid=0}
gender: MALE
age: 41
height: 174
Found row : keyvalues={ffb8a26e-9734-42c6-9fac-b0ae2552b12b/persons:age/1488047749575/Put/vlen=3/seqid=0, ffb8a26e-9734-42c6-9fac-b0ae2552b12b/persons:gender/1488047749575/Put/vlen=4/seqid=0, ffb8a26e-9734-42c6-9fac-b0ae2552b12b/persons:height/1488047749575/Put/vlen=3/seqid=0}
gender: MALE
age: 100
height: 182
Found row : keyvalues={ffd9c6e7-479d-4120-a0a4-4fc72b8dbab8/persons:age/1488047747830/Put/vlen=2/seqid=0, ffd9c6e7-479d-4120-a0a4-4fc72b8dbab8/persons:gender/1488047747830/Put/vlen=6/seqid=0, ffd9c6e7-479d-4120-a0a4-4fc72b8dbab8/persons:height/1488047747830/Put/vlen=3/seqid=0}
gender: FEMALE
age: 43
height: 164
Found row : keyvalues={fff3ba7e-b033-42af-9c78-90f131d8e5df/persons:age/1488047748812/Put/vlen=2/seqid=0, fff3ba7e-b033-42af-9c78-90f131d8e5df/persons:gender/1488047748812/Put/vlen=6/seqid=0, fff3ba7e-b033-42af-9c78-90f131d8e5df/persons:height/1488047748812/Put/vlen=3/seqid=0}
gender: FEMALE
age: 26
height: 168
...
```

 Analog dazu kann man die HBase Shell verwenden und die Daten gezielt abrufen.
 ```
 hbase shell
 hbase(main):001:0> get 'PersonDatabase', 'fff3ba7e-b033-42af-9c78-90f131d8e5df'
COLUMN                CELL                                                      
 persons:age          timestamp=1488047748812, value=26                         
 persons:gender       timestamp=1488047748812, value=FEMALE                     
 persons:height       timestamp=1488047748812, value=168                        
3 row(s) in 0.3800 seconds

 ```
