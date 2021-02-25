package com.alibaba.druid.support.opds.udf;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.aliyun.odps.udf.UDF;

import java.util.List;

public class SqlSyntaxCheck extends UDF {
    public Boolean evaluate(String sql) {
        return evaluate(sql, null, false);
    }

    public Boolean evaluate(String sql, String dbTypeName) {
        return evaluate(sql, dbTypeName, false);
    }

    public Boolean evaluate(String sql, String dbTypeName, boolean throwError) {
        if (sql == null || sql.length() == 0) {
            return null;
        }

        DbType dbType = dbTypeName == null ? null : DbType.valueOf(dbTypeName);

        try {
            SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, dbType);
            List<SQLStatement> statementList = parser.parseStatementList();
            return true;
        } catch (ParserException ex) {
            if (throwError) {
                throw new IllegalArgumentException("error sql : \n" + sql, ex);
            }

            return false;
        }
    }
}
