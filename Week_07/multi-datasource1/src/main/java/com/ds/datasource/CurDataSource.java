package com.ds.datasource;

import com.ds.util.DatasourceConst;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurDataSource {

	String value() default DatasourceConst.MASTER;

}
