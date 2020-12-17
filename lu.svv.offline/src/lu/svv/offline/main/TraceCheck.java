/**
 * Copyright by University of Luxembourg 2019-2020. 
 *   Developed by Chaima Boufaied, chaima.boufaied@uni.lu University of Luxembourg. 
 *   Developed by Claudio Menghi, claudio.menghi@uni.lu University of Luxembourg. 
 *   Developed by Domenico Bianculli, domenico.bianculli@uni.lu University of Luxembourg. 
 *   Developed by Lionel Briand, lionel.briand@uni.lu University of Luxembourg. 
 */
package lu.svv.offline.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import lu.svv.offline.check.CheckPackage;
import lu.svv.offline.check.Monitor;
import lu.svv.offline.check.impl.CheckFactoryImpl;
import lu.svv.offline.sBTemPsy.PropertiesBlock;
import lu.svv.offline.sBTemPsy.SBTemPsyPackage;
import lu.svv.offline.trace.Trace;
import lu.svv.offline.trace.TracePackage;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.OCLInput;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.helper.OCLHelper;

public class TraceCheck {
	private Monitor monitor;
	public static final String oclOperationsFile = "lib/sb-tempsy-check.ocl";
	private OCL<EPackage, EClassifier, ?, ?, ?, ?, ?, ?, ?, Constraint, ?, ?> ocl;
	private OCLHelper<EClassifier, ?, ?, Constraint> oclHelper;
	private ResourceLoader tempsyLoader;
	private ResourceLoader traceLoader;
	public static final String CheckProperty = "let exp:sBTemPsy::AbstractProperty = self.properties.properties->at(1), subtrace:OrderedSet(trace::TraceElement) = getInputTraceElements(self.trace) in checkProperty(subtrace, exp)";
	
	// load TemPsy properties (XMI) and trace instances (CSV)
	public void loadMonitor(String tempsyFilePath, String traceFilePath) {
		tempsyLoader = XmiTemPsyLoader.init();
		PropertiesBlock properties = (PropertiesBlock) tempsyLoader.load(tempsyFilePath);

		traceLoader = CsvTraceLoader.init();
		Trace trace = (Trace) traceLoader.load(traceFilePath);
		
		if (properties != null && trace != null) {
			Monitor monitor = new CheckFactoryImpl().createMonitor();
			monitor.setProperties(properties);
			monitor.setTrace(trace);
			this.monitor = monitor;
		}
	}
	
	public void resetMonitor() {
		this.monitor = null;
	}
	
	public void parseOCL() {
		// Copied from org.eclipse.ocl.ecore.tests.DocumentationExamples.java
		EPackage.Registry registry = new EPackageRegistryImpl();
		registry.put(CheckPackage.eNS_URI, CheckPackage.eINSTANCE);
		registry.put(SBTemPsyPackage.eNS_URI, SBTemPsyPackage.eINSTANCE);
		registry.put(TracePackage.eNS_URI, TracePackage.eINSTANCE);
		EcoreEnvironmentFactory environmentFactory = new EcoreEnvironmentFactory(registry);
		ocl = OCL.newInstance(environmentFactory);

		// get an OCL text file via some hypothetical API
		InputStream in = null;
		try {
			in = new FileInputStream(oclOperationsFile);
			in.skip(193);
			ocl.parse(new OCLInput(in));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		    try {
		    	in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		oclHelper = ocl.createOCLHelper();
		oclHelper.setContext(CheckPackage.Literals.MONITOR);
	}

	public boolean checkSingle() throws ParserException
	{
//		return ocl.check(this.monitor, oclHelper.createInvariant(ConstraintFactory.init().createInvariant(monitor.getProperties().getProperties().get(0))));
		return ocl.check(this.monitor, oclHelper.createInvariant(CheckProperty));
	}

//	public void applyScopeSingle() throws ParserException
//	{
//		ocl.evaluate(this.monitor, oclHelper.createQuery(ConstraintFactory.init().createScopeQuery(monitor.getProperties().getProperties().get(0))));
//	}
	
//	public void check() throws ParserException
//	{
//		Map<String, String> constraintStringMap = ConstraintFactory.init().createInvariants(this.monitor);
//		Map<String, Constraint> constraintMap = new HashMap<String, Constraint>();
//		Iterator<Entry<String, String>> it1 = constraintStringMap.entrySet().iterator();
//		while(it1.hasNext()) {
//			Map.Entry<String, String> pairs = (Entry<String, String>)it1.next();
//			constraintMap.put(pairs.getKey(), oclHelper.createInvariant(pairs.getValue()));
//		}
//		Iterator<Entry<String, Constraint>> it2 = constraintMap.entrySet().iterator();
//		while(it2.hasNext()) {
//			Entry<String, Constraint> pairs = (Entry<String, Constraint>)it2.next();
//			ocl.check(this.monitor, pairs.getValue());
//		}
//	}
}
