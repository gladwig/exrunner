package edu.kit.aifb.exrunner.model.attribute;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Attribute {
	protected String m_name;
	
	public Attribute(String name) {
		m_name = name;
	}

	public String getName() {
		return m_name;
	}

	public abstract String toSQLDecl();
	public abstract void setSQLValue(PreparedStatement pst, int idx, Object o) throws SQLException;

	@Override
	public String toString() {
		return m_name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_name == null) ? 0 : m_name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attribute other = (Attribute)obj;
		if (m_name == null) {
			if (other.m_name != null)
				return false;
		}
		else if (!m_name.equals(other.m_name))
			return false;
		return true;
	}
	
	
}
