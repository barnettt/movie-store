package com.iyasoft.movie.function;

@FunctionalInterface
public interface InvokeServiceFunction<X, U, V, R> {

    R invokeOnService(X template, U url, V clazz);

}
