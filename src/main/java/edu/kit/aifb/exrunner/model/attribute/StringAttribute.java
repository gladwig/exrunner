package edu.kit.aifb.exrunner.model.attribute;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StringAttribute extends Attribute {

	public StringAttribute(String name) {
		super(name);
	}

	@Override
	public String toSQLDecl() {
		return m_name + " text";
	}

	@Override
	public void setSQLValue(PreparedStatement pst, int idx, Object o) throws SQLException {
		pst.setString(idx, (String)o);
	}

}
