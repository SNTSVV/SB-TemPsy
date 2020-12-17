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




public class SingleCheckMain {
	private static long startTime, elapsedTime;
	
	
	public static void singleMain(String[] args) throws IOException, ParserException {
		startTime = System.currentTimeMillis();
		TraceCheck tc = new TraceCheck();
		// Load OCL functions once
	    tc.parseOCL();
		String propertyFile =  String.valueOf(args[0]);
		String traceFile = String.valueOf(args[1]);
		String resultFile = String.valueOf(args[2]);
		
		SBTemPsyCheck checker = new SBTemPsyCheck(propertyFile, traceFile);
	
		startTime = System.currentTimeMillis();
		
		boolean result=checker.check();
		elapsedTime = System.currentTimeMillis() - startTime;
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile,true));
		writer.write(propertyFile + ',' + traceFile + ',' + result + ',' + elapsedTime / 1000.0 + '\n');
		writer.close();

		System.out.println("Result: "+result);
		System.out.printf("Time: %.2f\t", elapsedTime/1.0);
		
		
	}
}
		
		
	