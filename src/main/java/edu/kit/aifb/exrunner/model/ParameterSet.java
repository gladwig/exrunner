package edu.kit.aifb.exrunner.model;

import java.util.Map;

import com.google.common.collect.Maps;

import edu.kit.aifb.exrunner.model.attribute.Attribute;

public class ParameterSet {
	protected Map<Attribute,Object> m_attrValues;
	
	public ParameterSet() {
		m_attrValues = Maps.newHashMap();
	}
	
	public ParameterSet(Map<Attribute,Object> attrValues) {
		m_attrValues = attrValues;
	}
	
	public Map<Attribute,Object> getValues() {
		return m_attrValues;
	}
	
	public void setValue(Attribute attr, Object value) {
		m_attrValues.put(attr, value);
	}
	
	public Object getValue(Attribute attr) {
		return m_attrValues.get(attr);
	}
	
	@Override
	public String toString() {
		return m_attrValues.toString();
	}
}
