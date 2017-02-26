package de.fhms.bde.uebung5.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import de.fhms.bde.uebung5.data.GeneratorMain;

public class HBaseConnector {

	private static final String HBASE_MASTER = "hbase.master";
	private static final String HBASE_MASTER_HOST = "quickstart.cloudera:60010";

	private static final String HBASE_PERSON_TABLE = "PersonDatabase";
	private static final String HBASE_PERSON_COLUMN = "persons";
	
	private static Connection createConnection() throws IOException {
		Configuration config = HBaseConfiguration.create();
		config.set(HBASE_MASTER, HBASE_MASTER_HOST);

		return ConnectionFactory.createConnection(config);
	}

	@SuppressWarnings("deprecation")
	public void createPersonTable() throws IOException {
		Connection con = createConnection();

		HBaseAdmin admin = new HBaseAdmin(con);

		if (admin.tableExists(HBASE_PERSON_TABLE)) 
		{
			System.out.println("Table already exists!!");
			return;
		}
		// Instantiating table descriptor class
		HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(HBASE_PERSON_TABLE));

		// Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor(HBASE_PERSON_COLUMN));

		// Execute the table through admin
		admin.createTable(tableDescriptor);
		System.out.println("Table " + HBASE_PERSON_TABLE + " created");
		admin.close();
		con.close();
	}

	public void insertRandomPersons(int size) throws IOException {
		Connection con = createConnection();
		for (int i = 0; i <= size; i++) {
			String[] person = GeneratorMain.generateEntry();
			savePersonInHbase(person, con);
		}
		con.close();
	}

	public void getPersonTable() {

		Scan scan = new Scan();
		scan.addColumn(Bytes.toBytes(HBASE_PERSON_COLUMN), Bytes.toBytes("gender"));
		scan.addColumn(Bytes.toBytes(HBASE_PERSON_COLUMN), Bytes.toBytes("age"));
		scan.addColumn(Bytes.toBytes(HBASE_PERSON_COLUMN), Bytes.toBytes("height"));
		
		try {
			Connection con = createConnection();

			Table table = con.getTable(TableName.valueOf(HBASE_PERSON_TABLE));
			ResultScanner scanner = table.getScanner(scan);
			for (Result result : scanner) {
				System.out.println("Found row : " + result);

				byte[] valueGender = result.getValue(Bytes.toBytes(HBASE_PERSON_COLUMN), Bytes.toBytes("gender"));
				String gender = Bytes.toString(valueGender);
				System.out.println("gender: " + gender);

				byte[] valueAge = result.getValue(Bytes.toBytes(HBASE_PERSON_COLUMN), Bytes.toBytes("age"));
				String age = Bytes.toString(valueAge);
				System.out.println("age: " + age);

				byte[] valueHeight = result.getValue(Bytes.toBytes(HBASE_PERSON_COLUMN), Bytes.toBytes("height"));
				String height = Bytes.toString(valueHeight);
				System.out.println("height: " + height);
			}

			scanner.close();
		} catch (IOException e) {
			System.out.println("Couldn't create table object");
			e.printStackTrace();
		}
	}

	private void savePersonInHbase(String[] person, Connection con) throws IOException {
		UUID uuid = UUID.randomUUID(); // generate pseudo-unique row key

		Table table = con.getTable(TableName.valueOf(HBASE_PERSON_TABLE));
		
		Put put = new Put(Bytes.toBytes(uuid.toString())); // row key

		put.addColumn(Bytes.toBytes(HBASE_PERSON_COLUMN), Bytes.toBytes("gender"), Bytes.toBytes(person[0]));
		put.addColumn(Bytes.toBytes(HBASE_PERSON_COLUMN), Bytes.toBytes("age"), Bytes.toBytes(person[1]));
		put.addColumn(Bytes.toBytes(HBASE_PERSON_COLUMN), Bytes.toBytes("height"), Bytes.toBytes(person[2]));

		
		table.put(put);
	}

	public static void deleteTableContent(String tableName) throws IOException {
		Connection con = createConnection();
		Scan scan = new Scan();
		Table table = con.getTable(TableName.valueOf(tableName));
		scan.addFamily(Bytes.toBytes(HBASE_PERSON_COLUMN));
		ResultScanner scanner = table.getScanner(scan);
		List<Delete> listOfBatchDeletes = new ArrayList<Delete>();
		for (Result result : scanner) {
			Delete delete = new Delete(result.getRow());
			listOfBatchDeletes.add(delete);
		}
		table.delete(listOfBatchDeletes);
		con.close();
	}

}