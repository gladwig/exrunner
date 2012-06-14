package edu.kit.aifb.exrunner;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.kit.aifb.exrunner.model.ParameterSet;
import edu.kit.aifb.exrunner.model.ExperimentSystem;
import edu.kit.aifb.exrunner.model.ParameterSetProvider;
import edu.kit.aifb.exrunner.model.attribute.Attribute;
import edu.kit.aifb.exrunner.model.attribute.IntAttribute;
import edu.kit.aifb.exrunner.model.attribute.StringAttribute;
import edu.kit.aifb.exrunner.model.attribute.TimestampAttribute;

public class ExperimentRun {

	private ExperimentSystem m_system;
	private ParameterSetProvider m_paramSetProvider;
	private int m_benchRuns;
	private File m_dbFile;
	private File m_executePrepareScript;
	private String m_runid;
	
	private static final Logger log = LoggerFactory.getLogger(ExperimentRun.class);

	public ExperimentRun(ExperimentSystem system, ParameterSetProvider queryProvider, int benchRuns, File dbFile, File executePrepareScript) {
		this(system, null, queryProvider, benchRuns, dbFile, executePrepareScript);
	}
	
	public ExperimentRun(ExperimentSystem system, String runid, ParameterSetProvider queryProvider, int benchRuns, File dbFile, File executePrepareScript) {
		m_system = system;
		m_paramSetProvider = queryProvider;
		m_benchRuns = benchRuns;
		m_dbFile = dbFile;
		m_executePrepareScript = executePrepareScript;
		m_runid = runid;
		
		if (m_runid == null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
			m_runid = format.format(new Date());
		}
	}
	
	public void run() throws ClassNotFoundException, SQLException, IOException {
		Attribute runidAttr = new StringAttribute("runid");
		Attribute nrAttr = new IntAttribute("nr");
		Attribute sysnameAttr = new StringAttribute("system_name");
		List<Attribute> runAttributes = Lists.newArrayList(runidAttr, nrAttr, sysnameAttr);
		
		List<Attribute> attributes = Lists.newArrayList();
		attributes.addAll(runAttributes);
		attributes.addAll(m_system.getSystemAttributes());
		attributes.addAll(m_paramSetProvider.getAttributes());
		attributes.addAll(m_system.getExecutionAttributes());

		List<Attribute> keyAttributes = Lists.newArrayList();
		keyAttributes.addAll(runAttributes);
		keyAttributes.addAll(m_system.getSystemAttributes());
		keyAttributes.addAll(m_paramSetProvider.getAttributes());

		log.info("attributes: " + attributes);
		
		Map<Attribute,Object> systemValues = m_system.open();

		List<ParameterSet> paramSets = m_paramSetProvider.getParameterSets();

		ResultDatabase db = new ResultDatabase(m_dbFile, m_system.getName(), attributes, keyAttributes);
		
		for (int run = 0; run < m_benchRuns; run++) {
			Collections.shuffle(paramSets);
			
			Map<Attribute,Object> runValues = Maps.newHashMap();
			runValues.put(runidAttr, m_runid);
			runValues.put(nrAttr, run);
			runValues.put(sysnameAttr, m_system.getName());
			
			for (ParameterSet paramSet : paramSets) {
				if (m_executePrepareScript != null && m_executePrepareScript.exists()) {
					Process p = Runtime.getRuntime().exec(m_executePrepareScript.getAbsolutePath());
					try {
						p.waitFor();
					}
					catch (InterruptedException e) {
						log.error("exception while executing " + m_executePrepareScript, e);
					}
				}
				
				long time = System.currentTimeMillis();
				Map<Attribute,Object> executionValues = m_system.execute(paramSet);
				time = System.currentTimeMillis() - time;

				Map<Attribute,Object> queryValues = paramSet.getValues();
				
				db.recordResult(runValues, systemValues, queryValues, executionValues);
				
				log.info("paramSet=" + paramSet + " executionValues=" + executionValues);
			}
		}

		db.close();
	}
}
