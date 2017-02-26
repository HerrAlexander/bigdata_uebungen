package de.fhms.bde.uebung3.mr;

import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

public class PersonAverageCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {

	private IntWritable result = new IntWritable();

	@Override
	protected void reduce(final Text key, final Iterable<IntWritable> values, final Context context)
			throws IOException, InterruptedException {

		Log log = LogFactory.getLog(PersonAverageCombiner.class);

		int sum = 0;
		for (IntWritable val : values) {
			sum += val.get();
		}

		result.set(sum);
		context.write(key, result);

	};
}
