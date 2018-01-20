package com.company.productname.pgtypes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.EnumType;


/**
 * Created by darrell-shofstall on 1/12/18.
 */
public class PostgreSQLEnumType extends EnumType {

    @Override
    public Object nullSafeGet(
            ResultSet resultSet, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
        assert (names.length == 1);
        if (resultSet.wasNull()) {
            return null;
        }
        String pgEnumName = resultSet.getString(names[0]);
        if (pgEnumName == null) {
            return null;
        }
        assert (!pgEnumName.contains("_"));
        String javaEnumName = PostgreSQLEnum.fromPgIdentifier(pgEnumName);
        return uncheckedValueOf(javaEnumName);
    }

    @SuppressWarnings("unchecked")
    Enum uncheckedValueOf(String javaEnumName) {
        return Enum.valueOf(returnedClass(), javaEnumName);
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
            return;
        }
        String v = ((PostgreSQLEnum) value).getPgName();
        st.setObject(index, v, Types.OTHER);
    }
}

