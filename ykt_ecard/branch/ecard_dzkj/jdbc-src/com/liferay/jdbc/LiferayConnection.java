/**
 * Copyright (c) 2000-2005 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="LiferayConnection.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @version $Revision: 1.6 $
 *
 */
public class LiferayConnection implements Connection {

	public LiferayConnection(Connection con, boolean db2) {
		_con = con;
		_db2 = db2;
	}

	public void clearWarnings() throws SQLException {
		_con.clearWarnings();
	}

	public void close() throws SQLException {
		_con.close();
	}

	public void commit() throws SQLException {
		_con.commit();
	}

	public Statement createStatement() throws SQLException {
		LiferayStatement ls = new LiferayStatement(_con.createStatement());

		return ls;
	}

	public Statement createStatement(int resultSetType,
									 int resultSetConcurrency)
		throws SQLException {

		LiferayStatement ls = new LiferayStatement(
			_con.createStatement(resultSetType, resultSetConcurrency));

		return ls;
	}

	public Statement createStatement(int resultSetType,
									 int resultSetConcurrency,
									 int resultSetHoldability)
		throws SQLException {

		LiferayStatement ls = new LiferayStatement(_con.createStatement(
			resultSetType, resultSetConcurrency, resultSetHoldability));

		return ls;
	}

	public boolean getAutoCommit() throws SQLException {
		return _con.getAutoCommit();
	}

	public String getCatalog() throws SQLException {
		return _con.getCatalog();
	}

	public int getHoldability() throws SQLException {
		return _con.getHoldability();
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return _con.getMetaData();
	}

	public int getTransactionIsolation() throws SQLException {
		return _con.getTransactionIsolation();
	}

	public Map getTypeMap() throws SQLException {
		return new HashMap();
	}

	public SQLWarning getWarnings() throws SQLException {
		return _con.getWarnings();
	}

	public boolean isClosed() throws SQLException {
		return _con.isClosed();
	}

	public boolean isReadOnly() throws SQLException {
		return _con.isReadOnly();
	}

	public String nativeSQL(String sql) throws SQLException {
		return _con.nativeSQL(sql);
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		LiferayCallableStatement lcs = new LiferayCallableStatement(
			_con.prepareCall(sql));

		return lcs;
	}

	public CallableStatement prepareCall(String sql, int resultSetType,
										 int resultSetConcurrency)
		throws SQLException {

		LiferayCallableStatement lcs = new LiferayCallableStatement(
			_con.prepareCall(sql, resultSetType, resultSetConcurrency));

		return lcs;
	}

	public CallableStatement prepareCall(String sql, int resultSetType,
										 int resultSetConcurrency,
										 int resultSetHoldability)
		throws SQLException {

		LiferayCallableStatement lcs = new LiferayCallableStatement(
			_con.prepareCall(
				sql, resultSetType, resultSetConcurrency,
				resultSetHoldability));

		return lcs;
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		LiferayPreparedStatement lps = new LiferayPreparedStatement(
			_con.prepareStatement(sql), _db2);

		return lps;
	}

	public PreparedStatement prepareStatement(String sql,
											  int autoGeneratedKeys)
		throws SQLException {

		LiferayPreparedStatement lps = new LiferayPreparedStatement(
			_con.prepareStatement(sql, autoGeneratedKeys), _db2);

		return lps;
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
		throws SQLException {

		LiferayPreparedStatement lps = new LiferayPreparedStatement(
			_con.prepareStatement(sql, columnIndexes), _db2);

		return lps;
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
											  int resultSetConcurrency)
		throws SQLException {

		LiferayPreparedStatement lps = new LiferayPreparedStatement(
			_con.prepareStatement(sql, resultSetType, resultSetConcurrency),
			_db2);

		return lps;
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
											  int resultSetConcurrency,
											  int resultSetHoldability)
		throws SQLException {

		LiferayPreparedStatement lps = new LiferayPreparedStatement(
			_con.prepareStatement(
				sql, resultSetType, resultSetConcurrency,
				resultSetHoldability),
			_db2);

		return lps;
	}

	public PreparedStatement prepareStatement(String sql,
											  String[] columnNames)
		throws SQLException {

		LiferayPreparedStatement lps = new LiferayPreparedStatement(
			_con.prepareStatement(sql, columnNames), _db2);

		return lps;
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		_con.releaseSavepoint(savepoint);
	}

	public void rollback() throws SQLException {
		_con.rollback();
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		_con.rollback(savepoint);
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		_con.setAutoCommit(autoCommit);
	}

	public void setCatalog(String catalog) throws SQLException {
		_con.setCatalog(catalog);
	}

	public void setHoldability(int holdability) throws SQLException {
		_con.setHoldability(holdability);
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		_con.setReadOnly(readOnly);
	}

	public Savepoint setSavepoint() throws SQLException {
		return _con.setSavepoint();
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		return _con.setSavepoint(name);
	}

	public void setTransactionIsolation(int level) throws SQLException {
		_con.setTransactionIsolation(level);
	}

	public void setTypeMap(Map map) throws SQLException {
		_con.setTypeMap(map);
	}

	private Connection _con;
	private boolean _db2;

}