package de.fhms.bde.uebung3.mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.hadoop.io.Text;

import org.apache.log4j.Logger;

public class PersonAverageMapper extends Mapper<Object, Text, Text, IntWritable> {

	private Text gender = new Text();
	private IntWritable height = new IntWritable();

	final static Logger logger = Logger.getLogger(PersonAverageMapper.class);
	
	@Override
	protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {

		 String[] strings = value.toString().split(Pattern.quote("\t"));
		 
		 String genderByAgeKey = strings[0] + "(" + strings[1] + ")";
		 //String genderByAgeKey = strings[0];
		 
		 gender.set(genderByAgeKey);
		
		 //Größe an 3. Stelle der generierten Example Datei
		 height.set(Integer.parseInt(strings[2])); 
		 context.write(gender, height);

	}
}
