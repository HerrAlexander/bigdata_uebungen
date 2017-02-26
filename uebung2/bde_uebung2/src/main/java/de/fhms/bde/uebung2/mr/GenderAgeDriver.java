package de.fhms.bde.uebung2.mr;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;



public class GenderAgeDriver {
	public static void main(String[] args) {
		try {	
			Configuration conf = new Configuration();
			String[] otherArgs;
		
			otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

		    if (otherArgs.length != 2) {
		      System.err.println("Usage: genderageaverage <in> <out>");
		      System.exit(2);
		    }
		    Job job = Job.getInstance(conf, "genderage_average");
		    job.setJarByClass(GenderAgeDriver.class);
		    job.setMapperClass(GenderAgeMapper.class);
		    job.setCombinerClass(GenderAgeReducer.class);
		    job.setReducerClass(GenderAgeReducer.class);
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(IntWritable.class);
		    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		    System.exit(job.waitForCompletion(true) ? 0 : 1);
	    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
