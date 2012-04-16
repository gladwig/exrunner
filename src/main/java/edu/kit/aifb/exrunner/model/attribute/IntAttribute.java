package edu.kit.aifb.exrunner.model.attribute;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class IntAttribute extends Attribute {

	public IntAttribute(String name) {
		super(name);
	}

	@Override
	public String toSQLDecl() {
		return m_name + " int";
	}

	@Override
	public void setSQLValue(PreparedStatement pst, int idx, Object o) throws SQLException {
		if (o instanceof Long)
			pst.setLong(idx, (Long)o);
		else if (o instanceof Double)
			pst.setLong(idx, ((Double)o).longValue());
		else
			pst.setInt(idx, (Integer)o);
	}

}
