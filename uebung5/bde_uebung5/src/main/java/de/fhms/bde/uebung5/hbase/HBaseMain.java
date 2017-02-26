package de.fhms.bde.uebung5.hbase;

import java.io.IOException;

public class HBaseMain {



	public static void main(String[] args) {

		try {
			HBaseConnector connector = new HBaseConnector();
			connector.createPersonTable();

			//Insert 1000 persons at once
			connector.insertRandomPersons(1000);
			
			connector.getPersonTable();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
