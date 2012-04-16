package edu.kit.aifb.exrunner.model;

import java.util.List;

import com.google.common.collect.Lists;

import edu.kit.aifb.exrunner.model.attribute.Attribute;

public abstract class ParameterSetProvider {
	
	protected List<Attribute> m_queryAttributes;
	
	public ParameterSetProvider() {
		m_queryAttributes = Lists.newArrayList();
	}
	
	protected void registerAttributes(Attribute... attrs) {
		for (Attribute attr : attrs)
			m_queryAttributes.add(attr);
	}
	
	public List<Attribute> getAttributes() {
		return m_queryAttributes;
	}
	
	public abstract List<ParameterSet> getParameterSets();
}
