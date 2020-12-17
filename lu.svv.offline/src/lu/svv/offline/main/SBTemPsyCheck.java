/**
 * Copyright by University of Luxembourg 2019-2020. 
 *   Developed by Chaima Boufaied, chaima.boufaied@uni.lu University of Luxembourg. 
 *   Developed by Claudio Menghi, claudio.menghi@uni.lu University of Luxembourg. 
 *   Developed by Domenico Bianculli, domenico.bianculli@uni.lu University of Luxembourg. 
 *   Developed by Lionel Briand, lionel.briand@uni.lu University of Luxembourg. 
 */
package lu.svv.offline.main;


import java.util.concurrent.Callable;

import org.eclipse.ocl.ParserException;

public class SBTemPsyCheck implements Callable<Boolean>{

	private final String propertyFile;
	private final String traceFile;
	
	private boolean result;
	
	private TraceCheck tc;
	
	/**
	 * Creates a new TemPsyCheck checker
	 * @param propertyFile the path of the property file to be checked
	 * @param traceFile the path of the property file to be checked
	 * @throws IllegalArgumentException if one of the parameter is null
	 */
	public SBTemPsyCheck(String propertyFile, String traceFile) {
		if(propertyFile==null) {
			throw new IllegalArgumentException("The property file cannot be null");
		}
		if(traceFile==null) {
			throw new IllegalArgumentException("The trace file cannot be null");
		}

		this.propertyFile=propertyFile;
		this.traceFile=traceFile;
		 tc= new TraceCheck();
		// Load OCL functions once
		tc.parseOCL(); // fixed
		
		// getting the parameters:
		tc.loadMonitor(propertyFile, traceFile); 
		System.out.println("Trace loaded!");
		
	}
	
	/**
	 * Runs the TemPsyCheck checker
	 * @return true if the property is satisfied, false otherwise
	 * @throws ParserException 
	 */
	public boolean check() throws ParserException {
		long startTime = System.currentTimeMillis();
		
		boolean res=tc.checkSingle();
//		// Reset the monitor instance for memory release
		tc.resetMonitor(); // fixed
		System.gc();
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.printf("%.2f ms\t", elapsedTime / 1.0);
		System.out.println();

		return res;
	}
	public Boolean call() {
		try {
			result=this.check();
			return result;
		} catch (ParserException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	
}
