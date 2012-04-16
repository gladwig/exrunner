package edu.kit.aifb.exrunner.model;

import java.util.Map;

import edu.kit.aifb.exrunner.model.attribute.Attribute;

public class GenericQuery extends ParameterSet {

	public GenericQuery(Map<Attribute,Object> values) {
		m_attrValues.putAll(values);
	}
}
