/**
 * Copyright by University of Luxembourg 2019-2020. 
 *   Developed by Chaima Boufaied, chaima.boufaied@uni.lu University of Luxembourg. 
 *   Developed by Claudio Menghi, claudio.menghi@uni.lu University of Luxembourg. 
 *   Developed by Domenico Bianculli, domenico.bianculli@uni.lu University of Luxembourg. 
 *   Developed by Lionel Briand, lionel.briand@uni.lu University of Luxembourg. 
 */
package lu.svv.offline.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.ocl.ParserException;
public class ExecuteExperiments {

	
	
	
private static BufferedReader bufferedReader;


	//	Count number of entries in a log file
	public static Integer countEntries(String csv) throws IOException {
	bufferedReader = new BufferedReader(new FileReader(csv));
    String entry;
    int count = 0;
    while((entry = bufferedReader.readLine()) != null)
    {
        count++;
    }
	return count;
	}
	
	public static String getBaseName(String file) {
	String[] id= file.split("/");
	return id[2];
	}
	
	public static void main(String[] args) throws IOException, ParserException {
		long startTime, elapsedTime;

		// Warm up
		System.out.println("Considering the file: " + args[0]);
		FileReader inFile = new FileReader(args[0]);
		BufferedReader inStream = new BufferedReader(inFile);
		String inString;
		int round = 0;
		String traceFile = "";
		String propertyFile = "";
		System.out.println("Performing 1 check for warming up the Java VM");
		while ((inString = inStream.readLine()) != null || round < 1) {

			if (inString != null) {
				String[] elem = inString.split(" ");
				propertyFile = elem[0];
				traceFile = elem[1];
				//System.out.println("Property: " + propertyFile + " Trace: " + traceFile);
				//startTime = System.currentTimeMillis();
				SBTemPsyCheck checker = new SBTemPsyCheck(propertyFile, traceFile);
				checker.check();
				//elapsedTime = System.currentTimeMillis() - startTime;
				//System.out.println("Time: " + elapsedTime / 100.0 + "s");

			} else {
				//System.out.println("Property: " + propertyFile + " Trace: " + traceFile);
				//startTime = System.currentTimeMillis();
				//SBTemPsyCheck checker = new SBTemPsyCheck(propertyFile, traceFile);
				//checker.check();
				//elapsedTime = System.currentTimeMillis() - startTime;
				//System.out.println("Time: " + elapsedTime / 100.0 + "s");
			}
			round++;
		}
		inStream.close();

		// Running the experiments

				
		for (int i=1;i<=Integer.parseInt(args[1]); i++) { //args[1] is the number of runs
			inFile = new FileReader(args[0]);
			inStream = new BufferedReader(inFile);

			String outputFileName="./run_"+i+".txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
			writer.close();
	
			while ((inString = inStream.readLine()) != null) {
	
				String[] elem = inString.split(" ");
				propertyFile = elem[0];
				traceFile = elem[1];
	
				try {
					
					System.out.println("Property: " + propertyFile + " Trace: " + traceFile);
					startTime = System.currentTimeMillis();
	
					SBTemPsyCheck checker = new SBTemPsyCheck(propertyFile, traceFile);
	
					ExecutorService executor = Executors.newSingleThreadExecutor();
	
					List<SBTemPsyCheck> jobs = new ArrayList<>();
					jobs.add(checker);
					List<Future<Boolean>> results=executor.invokeAll(jobs, Long.parseLong(args[2]), TimeUnit.MINUTES);
		
					elapsedTime = System.currentTimeMillis() - startTime;
					
					System.out.println("Time: " + elapsedTime / 1000.0 + "s");
					Future<Boolean> result=results.get(0);
					writer = new BufferedWriter(new FileWriter(outputFileName,true));
					writer.write(propertyFile + ',' + traceFile +','+(!result.isCancelled() ? checker.getResult() : "-") + ',' + elapsedTime / 1000.0 + '\n');
					writer.close();
					executor.shutdownNow();
					
	
				} catch (InterruptedException e) {
					System.out.println("Time out");
					writer = new BufferedWriter(new FileWriter(outputFileName,true));
					writer.write(propertyFile + ',' + traceFile + ",-,-,\n");
					writer.close();
				}
	
			}
			inStream.close();
		
		}

		
		// Terminate JVM 
        System.exit(0); 


	}

}
