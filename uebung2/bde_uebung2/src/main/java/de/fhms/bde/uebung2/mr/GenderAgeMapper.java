package de.fhms.bde.uebung2.mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Pattern;


import org.apache.hadoop.io.Text;


public class GenderAgeMapper extends Mapper<Object, Text, Text, IntWritable> {

	private Text genderByAge = new Text();
	private final static IntWritable one = new IntWritable(1);


	@Override
	protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		
		String[] strings = value.toString().split(Pattern.quote("\t"));
		//[0] = Gender; [1] = Age
		String genderByAgeKey = strings[0] + "(" + strings[1] + ")";
		genderByAge.set(genderByAgeKey);
		context.write(genderByAge, one);
	}

}
