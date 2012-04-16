package edu.kit.aifb.exrunner.model.attribute;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoubleAttribute extends Attribute {

	public DoubleAttribute(String name) {
		super(name);
	}

	@Override
	public String toSQLDecl() {
		return m_name + " real";
	}

	@Override
	public void setSQLValue(PreparedStatement pst, int idx, Object o) throws SQLException {
		pst.setDouble(idx, (Double)o);
	}

}
