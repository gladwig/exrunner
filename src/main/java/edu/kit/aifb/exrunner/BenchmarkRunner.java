package edu.kit.aifb.exrunner;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.kit.aifb.exrunner.model.ExperimentSystem;
import edu.kit.aifb.exrunner.model.ParameterSetProvider;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class BenchmarkRunner {

	private static final Logger log = LoggerFactory.getLogger(BenchmarkRunner.class);
	
	private static Object instantiate(String clazz) {
		try {
			Class<?> c = Class.forName(clazz);
			return c.newInstance();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		OptionParser op = new OptionParser();
		OptionSpec<String> systemParamOp = op.accepts("system-param", "system-directory").withRequiredArg().ofType(String.class);
		OptionSpec<String> systemClassOp = op.accepts("system-class", "BenchmarkSystem implementation class").withRequiredArg().ofType(String.class);
		OptionSpec<String> queryProviderParamOp = op.accepts("query-provider-param", "query directory").withRequiredArg().ofType(String.class);
		OptionSpec<String> queryProviderClassOp = op.accepts("query-provider-class", "QueryProvider implementation class").withRequiredArg().ofType(String.class);
		OptionSpec<String> dbFileOp = op.accepts("db", "database file").withRequiredArg().ofType(String.class);
		OptionSpec<String> prepareScriptOp = op.accepts("prepare-execute-script", "script to be run before each execute").withRequiredArg().ofType(String.class);

		OptionSpec<Integer> runsOp = op.accepts("benchmark-runs", "number of benchmark runs").withRequiredArg().ofType(Integer.class);
		
		OptionSet os = op.parse(args);

		if (!os.has(systemParamOp) || !os.has(queryProviderParamOp) || !os.has(systemClassOp) || !os.has(queryProviderClassOp)) {
			op.printHelpOn(System.out);
			return;
		}

		String systemClass = systemClassOp.value(os);
		String queryProviderClass = queryProviderClassOp.value(os);
		int benchRuns = os.has(runsOp) ? runsOp.value(os) : 1;
		File dbFile = new File(os.has(dbFileOp) ? dbFileOp.value(os) : "results.db");
		File prepareScript = os.has(prepareScriptOp) ? new File(prepareScriptOp.value(os)) : null;
		
		ParameterSetProvider qp = (ParameterSetProvider)instantiate(queryProviderClass);
		ExperimentSystem sys = (ExperimentSystem)instantiate(systemClass);
		
		ExperimentRun run = new ExperimentRun(sys, systemParamOp.value(os), qp, queryProviderParamOp.value(os), benchRuns, dbFile, prepareScript);
		run.run();
	}

}
