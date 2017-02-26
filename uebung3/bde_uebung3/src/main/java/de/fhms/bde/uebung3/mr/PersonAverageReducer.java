package de.fhms.bde.uebung3.mr;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class PersonAverageReducer extends Reducer<Text, IntWritable, Text, DoubleWritable> {

//	private FloatWritable result = new FloatWritable();
//	float average = 0f;
//	float count = 0f;
//	int sum = 0;
		
	
	IntWritable one = new IntWritable(1);
	DoubleWritable average = new DoubleWritable();

	
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {
		Log log = LogFactory.getLog(PersonAverageReducer.class);
		
	    int sum = 0;
	    int count = 0;
	    for(IntWritable value : values) {
	        sum += value.get();
	        count++;
	    }
		    
	    log.info("Reducer: " + sum + " | " + count);
	    
	    average.set(sum / (double) count);
	    context.write(key, average);
	}

}
