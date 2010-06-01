package org.webfabric.rest.coercers;

public interface TypeCoercer {
    boolean canCoerce(Object value, Class result);
    Object coerce(Object value, Class result);
}
