# Übung 1 - Erweiterte HDFS Beispiele

### Erstellen von Verzeichnissen und Dateien

Erstellt neuen Ordner 'uebung1' und kopiert die lokale Datei 'pg14591.txt' nach HDFS.

```
[root@sandbox ~]# hdfs dfs -mkdir uebung1
[root@sandbox ~]# hdfs dfs -copyFromLocal ~/pg14591.txt uebung1/pg14591.txt
[root@sandbox ~]# hdfs dfs -ls uebung1
Found 1 items
-rw-r--r--   1 root hdfs     249321 2016-12-26 13:19 uebung1/pg14591.txt

```

Überprüfung des Ordners im Dateisystem.
```
[root@sandbox ~]# hdfs fsck uebung1
Connecting to namenode via http://sandbox.hortonworks.com:50070/fsck?ugi=root&path=%2Fuser%2Froot%2Fuebung1
FSCK started by root (auth:SIMPLE) from /172.17.0.2 for path /user/root/uebung1 at Mon Dec 26 13:22:57 UTC 2016
.Status: HEALTHY
 Total size:	249321 B
 Total dirs:	1
 Total files:	1
 Total symlinks:		0
 Total blocks (validated):	1 (avg. block size 249321 B)
 Minimally replicated blocks:	1 (100.0 %)
 Over-replicated blocks:	0 (0.0 %)
 Under-replicated blocks:	0 (0.0 %)
 Mis-replicated blocks:		0 (0.0 %)
 Default replication factor:	1
 Average block replication:	1.0
 Corrupt blocks:		0
 Missing replicas:		0 (0.0 %)
 Number of data-nodes:		1
 Number of racks:		1
FSCK ended at Mon Dec 26 13:22:57 UTC 2016 in 30 milliseconds


The filesystem under path '/user/root/uebung1' is HEALTHY
```
### Dateien duplizieren und mergen

Erzeugt eine Kopie der Textdatei.
```
[root@sandbox ~]# hdfs dfs -cp uebung1/pg14591.txt uebung1/pg14591_2.txt
[root@sandbox ~]# hdfs dfs -ls uebung1
Found 2 items
-rw-r--r--   1 root hdfs     249321 2016-12-26 13:32 uebung1/pg14591.txt
-rw-r--r--   1 root hdfs     249321 2016-12-26 13:41 uebung1/pg14591_2.txt
```

Fügt beide Textdateien aus dem HDFS zu einer großen Datei in das lokale Verzeichnis zusammen. Diese Datei kann dann wieder in HDFS verteilt werden.
```
[root@sandbox ~]# hdfs dfs -getmerge uebung1/pg14591.txt uebung1/pg14591_2.txt pg14591_merge.txt
[root@sandbox ~]# hdfs dfs -put pg14591_merge.txt uebung1/pg14591_merge.txt
[root@sandbox ~]# hdfs dfs -ls uebung1
Found 3 items
-rw-r--r--   1 root hdfs     249321 2016-12-26 13:32 uebung1/pg14591.txt
-rw-r--r--   1 root hdfs     249321 2016-12-26 13:41 uebung1/pg14591_2.txt
-rw-r--r--   1 root hdfs     498642 2016-12-26 14:46 uebung1/pg14591_merge.txt
```

### Sicherung durch Snapshots

Snapshots können mit dem Superuser hdfs erstellt werden.

```
[root@sandbox ~]# su hdfs
[hdfs@sandbox root]$ hdfs dfsadmin -allowSnapshot /user/root/uebung1
Allowing snaphot on /user/root/uebung1 succeeded
[hdfs@sandbox root]$ hdfs dfs -createSnapshot /user/root/uebung1
Created snapshot /user/root/uebung1/.snapshot/s20161226-152123.620
```

Nach Erstellung kann der Zustand angesehen werden.
```
[hdfs@sandbox root]$ hdfs dfs -ls /user/root/uebung1/.snapshot/s20161226-152123.620
Found 3 items
-rw-r--r--   1 root hdfs     249321 2016-12-26 13:32 /user/root/uebung1/.snapshot/s20161226-152123.620/pg14591.txt
-rw-r--r--   1 root hdfs     249321 2016-12-26 13:58 /user/root/uebung1/.snapshot/s20161226-152123.620/pg14591_2.txt
-rw-r--r--   1 root hdfs     498642 2016-12-26 14:05 /user/root/uebung1/.snapshot/s20161226-152123.620/pg14591_merge.txt
```

### Dateien auslesen und Verzeichnis löschen

Kopiert die Datei 'pg14591.txt' aus HDFS wieder ins lokal Dateisystem.

```
[root@sandbox ~]# hdfs dfs -copyToLocal uebung1/pg14591.txt ~/pg14591.txt
```

Löschen des Ordners inklusive des gesamten Inhalts (rekursiv).
```
[root@sandbox ~]# hdfs dfs -rm -r uebung1
16/12/26 13:29:45 INFO fs.TrashPolicyDefault: Moved: 'hdfs://sandbox.hortonworks.com:8020/user/root/uebung1' to trash at: hdfs://sandbox.hortonworks.com:8020/user/root/.Trash/Current/user/root/uebung1
```
