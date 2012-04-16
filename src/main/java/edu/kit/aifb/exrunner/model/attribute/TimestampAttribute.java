package edu.kit.aifb.exrunner.model.attribute;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class TimestampAttribute extends Attribute {

	public TimestampAttribute(String name) {
		super(name);
	}

	@Override
	public String toSQLDecl() {
		return m_name + " int";
	}

	@Override
	public void setSQLValue(PreparedStatement pst, int idx, Object o) throws SQLException {
		if (o instanceof Date)
			pst.setInt(idx, (int)(((Date)o).getTime() / 1000));
		if (o instanceof Integer)
			pst.setInt(idx, (Integer)o);
	}

}
