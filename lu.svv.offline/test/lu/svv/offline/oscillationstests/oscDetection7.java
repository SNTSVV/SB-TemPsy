/**
 * Copyright by University of Luxembourg 2019-2020. 
 *   Developed by Chaima Boufaied, chaima.boufaied@uni.lu University of Luxembourg. 
 *   Developed by Claudio Menghi, claudio.menghi@uni.lu University of Luxembourg. 
 *   Developed by Domenico Bianculli, domenico.bianculli@uni.lu University of Luxembourg. 
 *   Developed by Lionel Briand, lionel.briand@uni.lu University of Luxembourg. 
 */
package lu.svv.offline.oscillationstests;

import static org.junit.Assert.*;

import org.eclipse.ocl.ParserException;
import org.junit.Test;

import lu.svv.offline.main.SBTemPsyCheck;

public class oscDetection7 {

	@Test
	public void test() throws ParserException {
		
		ClassLoader loader=getClass()
		.getClassLoader();
		String propertyFile = loader.getResource("oscillations/posc7.xmi") // here max amp=2 
				.getFile();
		String traceFile = loader.getResource("oscillations/osc7.csv")
				.getFile();
		SBTemPsyCheck checker=new SBTemPsyCheck(propertyFile, traceFile);
		
		assertFalse(checker.check());
	}

}