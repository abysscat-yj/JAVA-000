package com.ds.datasource.aop;


import com.ds.datasource.CurDataSource;
import com.ds.datasource.DynamicDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
public class DataSourceAspect {

    @Around(("@annotation(curDataSource)"))
    public Object around(ProceedingJoinPoint point, CurDataSource curDataSource) throws Throwable {
        DynamicDataSource.setDataSource(curDataSource.value());

        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
        }
    }
}
