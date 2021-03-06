package com.dbgeneration.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbgeneration.dao.ReadStructureDatabaseDAO;
import com.dbgeneration.dto.DatabaseProperties;
import com.dbgeneration.utils.StringHandleUtils;

public class SelectStatementAnalysisImpl {

	private static final Logger logger = Logger.getLogger(SelectStatementAnalysisImpl.class);

	/**
	 * 
	 * @author ntmduyen
	 * @datetime Jul 31, 2020 - 11:25:43 PM
	 * @return
	 */
	public DatabaseProperties analysisSqlStatement() {
		logger.debug("analysisSqlStatement() start.");
		logger.debug("analysisSqlStatement() end.");
		return null;
	}

	/**
	 * 
	 * @author ntmduyen
	 * @datetime Jul 31, 2020 - 11:25:48 PM
	 * @return
	 */
	public static DatabaseProperties getSQLStatement() {
		logger.debug("getMapTableAndAlias() start");

		DatabaseProperties dbProperties = new DatabaseProperties();

		String sql = ReadStructureDatabaseDAO.getFormattedSQLString();

		List<String> lstSubStatement = new ArrayList<>();
		lstSubStatement.addAll(returnSubJoinStatement("JOIN", sql));
		lstSubStatement.addAll(returnSubJoinStatement("FROM", sql));

		Map<Object, Object> mapTableAlias = new HashMap<>();

		for (String statement : lstSubStatement) {
			String tableName = statement.substring(4, statement.indexOf(" ", 6));

			String aliasName = statement.substring(StringHandleUtils.getIndexOfFindString(statement, " ", 1),
					StringHandleUtils.getIndexOfFindString(statement, " ", 2)).trim();

			logger.info("Table alias: " + tableName + ", " + aliasName);
			mapTableAlias.put(tableName, aliasName);
		}
		logger.debug("getMapTableAndAlias() end");

		dbProperties.setTableAlias(mapTableAlias);
		return dbProperties;
	}

	/**
	 * 
	 * @author ntmduyen
	 * @datetime Jul 31, 2020 - 11:25:51 PM
	 * @param typeOfJoin
	 * @param sql
	 * @return
	 */
	private static List<String> returnSubJoinStatement(String typeOfJoin, String sql) {
		logger.debug("returnSubJoinStatement() start.");
		List<String> lstSubStatement = new ArrayList<>();

		int pointerStart = 0;
		int pointerFrom = 0;
		int pointer = 0;

		boolean stop = false;
		while (!stop) {
			pointerStart = sql.indexOf(typeOfJoin, pointer);
			pointerFrom = sql.indexOf("\r\n", pointerStart);
			pointer = sql.indexOf(typeOfJoin, pointerStart);
			pointer++;
			if (pointer == -1 || pointerStart == -1) {
				break;
			}

			String subStatement = sql.substring(pointerStart, pointerFrom).replace(" AS ", " ");
			lstSubStatement.add(subStatement);
			if ("FROM".equals(typeOfJoin)) {
				stop = true;
			}
		}

		logger.debug("returnSubJoinStatement() end.");
		return lstSubStatement;
	}
}
