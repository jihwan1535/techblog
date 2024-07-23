package com.blog.tech.domain.common;

@FunctionalInterface
public interface TransactionCallback<T> {

    T execute();

}
