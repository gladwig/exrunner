package edu.kit.aifb.exrunner.model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import edu.kit.aifb.exrunner.model.attribute.Attribute;


public abstract class ExperimentSystem {

	protected List<Attribute> m_systemAttributes;
	protected List<Attribute> m_executionAttributes;
	protected Map<Attribute,Object> m_attrValues;
	
	private String m_name;
	
	protected ExperimentSystem(String name) {
		m_name = name;
		m_systemAttributes = Lists.newArrayList();
		m_executionAttributes = Lists.newArrayList();
	}

	public String getName() {
		return m_name;
	}

	public List<Attribute> getSystemAttributes() {
		return m_systemAttributes;
	}
	
	public List<Attribute> getExecutionAttributes() {
		return m_executionAttributes;
	}
	
	protected Attribute registerSystemAttribute(Attribute attr) {
		m_systemAttributes.add(attr);
		return attr;
	}

	protected Attribute registerExecutionAttribute(Attribute attr) {
		m_executionAttributes.add(attr);
		return attr;
	}

	protected void registerExecutionAttributes(Attribute... attrs) {
		for (Attribute attr : attrs)
			m_executionAttributes.add(attr);
	}

	public abstract Map<Attribute,Object> open(String systemParam) throws IOException;
	public abstract void close() throws IOException;
	
	public abstract Map<Attribute,Object> execute(ParameterSet paramSet) throws IOException;
}
