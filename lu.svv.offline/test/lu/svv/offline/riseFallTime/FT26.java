/**
 * Copyright by University of Luxembourg 2019-2020. 
 *   Developed by Chaima Boufaied, chaima.boufaied@uni.lu University of Luxembourg. 
 *   Developed by Claudio Menghi, claudio.menghi@uni.lu University of Luxembourg. 
 *   Developed by Domenico Bianculli, domenico.bianculli@uni.lu University of Luxembourg. 
 *   Developed by Lionel Briand, lionel.briand@uni.lu University of Luxembourg. 
 */
package lu.svv.offline.riseFallTime;

import static org.junit.Assert.*;

import org.eclipse.ocl.ParserException;
import org.junit.Ignore;
import org.junit.Test;

import lu.svv.offline.main.SBTemPsyCheck;

public class FT26 {

	@Ignore
	@Test
	public void test() throws ParserException {
		
		
		ClassLoader loader=getClass()
		.getClassLoader();
		String propertyFile = loader.getResource("rTFT/np5.xmi") // here max amp=2 
				.getFile();
		String traceFile = loader.getResource("rTFT/np5trace2.csv")
				.getFile();
		SBTemPsyCheck checker=new SBTemPsyCheck(propertyFile, traceFile);
		
		assertTrue(checker.check()); //fixed
	}

}
