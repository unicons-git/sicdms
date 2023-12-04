/**
 *
 */
package com.g3way.sicims.util.handler;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.StringTypeHandler;


/**
 * @author dykim
 *
 */
public class EmptyStringTypeHandler extends StringTypeHandler {

	  @Override
	  public String getResult(ResultSet rs, String columnName) throws SQLException {
	    return unnulledString(super.getResult(rs, columnName));
	  }

	  @Override
	  public String getResult(ResultSet rs, int columnIndex) throws SQLException {
	    return unnulledString(super.getResult(rs, columnIndex));
	  }

	  @Override
	  public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
	    return unnulledString(super.getResult(cs, columnIndex));
	  }

	  private String unnulledString(String value) {
	    return StringUtils.defaultString(value, "");
	  }

}
