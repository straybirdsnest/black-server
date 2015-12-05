package org.team10424102.config.jpa;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.AbstractStandardBasicType;
import org.hibernate.type.TypeResolver;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Look here for more info on design.
 * http://community.jboss.org/wiki/Java5EnumUserType
 * modify to use AbstractStandardBasicType instead.
 * @author Chun ping Wang.
 *
 *
 * ,
 *
 */
public class GenericEnumUserType implements UserType, ParameterizedType {
    private static final String DEFAULT_IDENTIFIER_METHOD_NAME = "name";
    private static final String DEFAULT_VALUE_OF_METHOD_NAME = "valueOf";

    //@SuppressWarnings("rawtypes")
    private Class<? extends Enum> enumClass;
    private Class<?> identifierType;
    private Method identifierMethod;
    private Method valueOfMethod;
    private AbstractStandardBasicType<? extends Object> type;
    private int[] sqlTypes;

    @SuppressWarnings({ "unchecked"})
    public void setParameterValues(Properties parameters) {
        String enumClassName = parameters.getProperty("enumClass");
        try {
            enumClass = Class.forName(enumClassName).asSubclass(Enum.class);
        } catch (ClassNotFoundException cfne) {
            throw new HibernateException("Enum class not found", cfne);
        }

        String identifierMethodName = parameters.getProperty("identifierMethod", DEFAULT_IDENTIFIER_METHOD_NAME);

        try {
            identifierMethod = enumClass.getMethod(identifierMethodName, new Class[0]);
            identifierType = identifierMethod.getReturnType();
        } catch (Exception e) {
            throw new HibernateException("Failed to obtain identifier method", e);
        }

        type = (AbstractSingleColumnStandardBasicType<? extends Object>) new TypeResolver().heuristicType(identifierType.getName(), parameters);

        if (type == null)
            throw new HibernateException("Unsupported identifier type " + identifierType.getName());

        sqlTypes = new int[] { ((AbstractSingleColumnStandardBasicType<?>)type).sqlType() };

        String valueOfMethodName = parameters.getProperty("valueOfMethod", DEFAULT_VALUE_OF_METHOD_NAME);

        try {
            valueOfMethod = enumClass.getMethod(valueOfMethodName, new Class[] { identifierType });
        } catch (Exception e) {
            throw new HibernateException("Failed to obtain valueOf method", e);
        }
    }

    @SuppressWarnings("rawtypes")
    public Class<? extends Enum> returnedClass() {
        return enumClass;
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        Object identifier = type.get(resultSet, strings[0], sessionImplementor);
        if (resultSet.wasNull()) {
            return null;
        }

        try {
            return valueOfMethod.invoke(enumClass, new Object[] { identifier });
        } catch (Exception e) {
            throw new HibernateException("Exception while invoking valueOf method '" + valueOfMethod.getName() + "' of " +
                "enumeration class '" + enumClass + "'", e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        try {
            if (o == null) {
                preparedStatement.setNull(i, ((AbstractSingleColumnStandardBasicType<?>) type).sqlType());
            } else {
                Object identifier = identifierMethod.invoke(o, new Object[0]);
                type.nullSafeSet(preparedStatement, identifier, i, null);
            }
        } catch (Exception e) {
            throw new HibernateException("Exception while invoking identifierMethod '" + identifierMethod.getName() + "' of " +
                "enumeration class '" + enumClass + "'", e);
        }
    }

    public int[] sqlTypes() {
        return sqlTypes;
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y;
    }

    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    public boolean isMutable() {
        return false;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}