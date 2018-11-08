package com.iyasoft.movie.function;

@FunctionalInterface
public interface InvokeServiceFunction<R, X, U, V> {

    R invokeOnService(X template, U url, V clazz);

}
