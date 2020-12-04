package com.ds.cfg;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.ds.datasource.DynamicDataSource;
import com.ds.util.DatasourceConst;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DynamicDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource primaryDataSource() {
        return DruidDataSourceBuilder.create().build();
    }


    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    public DataSource secondaryDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource primaryDataSource, DataSource secondaryDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>(5);
        targetDataSources.put(DatasourceConst.master.name(), primaryDataSource);
        targetDataSources.put(DatasourceConst.slave.name(), secondaryDataSource);
        return new DynamicDataSource(primaryDataSource, targetDataSources);
    }

}
