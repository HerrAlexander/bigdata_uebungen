package de.fhms.bde.uebung7.spark;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import scala.Tuple2;

public class JavaWordCount {
	
	
	public static void wordCountJava(String filename) {
		// Define a configuration to use to interact with Spark
		SparkConf conf = new SparkConf().setMaster("local").setAppName("Work Count App");

		// Create a Java version of the Spark Context from the configuration
		JavaSparkContext sc = new JavaSparkContext(conf);

		// Load the input data, which is a text file read from the command line
		JavaRDD<String> input = sc.textFile(filename);

		// Java 8 with lambdas: split the input string into words
		JavaRDD<String> words = input.flatMap(s -> Arrays.asList(s.split(" ")));

		// Java 8 with lambdas: transform the collection of words into pairs
		// (word and 1) and then count them
		JavaPairRDD<String, Integer> counts = words.mapToPair(t -> new Tuple2(t, 1))
				.reduceByKey((x, y) -> (int) x + (int) y);

		// Save the word count back out to a text file, causing evaluation.
		counts.saveAsTextFile("output");
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: WordCount <file>");
			System.exit(0);
		}

		wordCountJava(args[0]);
	}

}