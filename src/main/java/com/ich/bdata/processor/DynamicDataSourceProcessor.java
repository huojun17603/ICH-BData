package com.ich.bdata.processor;

import com.alibaba.druid.pool.DruidDataSource;
import com.ich.bdata.dao.BlogMapper;
import com.ich.bdata.util.DynamicDataSource;
import com.ich.bdata.util.DynamicDataSourceHolder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;

public class DynamicDataSourceProcessor implements ApplicationListener<ContextRefreshedEvent> {



    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        DruidDataSource source = new DruidDataSource();
        DruidDataSource source2 = new DruidDataSource();
        DruidDataSource source3 = new DruidDataSource();
        source.setUrl("jdbc:mysql://192.168.1.200:3306/kprd?characterEncoding=utf-8");
        source.setUsername("root");
        source.setPassword("p@ssw0rd");
        source.setDriverClassName("com.mysql.jdbc.Driver");

        source2.setUrl("jdbc:mysql://120.76.201.182:3306/kprd?characterEncoding=utf-8");
        source2.setUsername("root");
        source2.setPassword("kprdp@ssw0rd!@#");
        source2.setDriverClassName("com.mysql.jdbc.Driver");

        source3.setUrl("jdbc:mysql://219.153.15.93:3306/kprd?characterEncoding=utf-8");
        source3.setUsername("kprd");
        source3.setPassword("kprdp@ssw0rd#@!");
        source3.setDriverClassName("com.mysql.jdbc.Driver");

        Map<Object,Object> targetMap = new LinkedHashMap<>();
        targetMap.put("source",source);
        targetMap.put("source2",source2);
        targetMap.put("source3",source3);
        DynamicDataSource dynamics = new DynamicDataSource();
        dynamics.setTargetDataSources(targetMap);
        dynamics.setDefaultTargetDataSource(source);

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamics);
        bean.setConfigLocation(new ClassPathResource("classpath:mybatis/SqlMapConfig.xml"));


    }

    public static void main(String[] args) throws IOException {
        /*DruidDataSource source = new DruidDataSource();
        DruidDataSource source2 = new DruidDataSource();
        DruidDataSource source3 = new DruidDataSource();
        source.setUrl("jdbc:mysql://192.168.1.200:3306/kprd?characterEncoding=utf-8");
        source.setUsername("root");
        source.setPassword("p@ssw0rd");
        source.setDriverClassName("com.mysql.jdbc.Driver");

        source2.setUrl("jdbc:mysql://120.76.201.182:3306/kprd?characterEncoding=utf-8");
        source2.setUsername("root");
        source2.setPassword("kprdp@ssw0rd!@#");
        source2.setDriverClassName("com.mysql.jdbc.Driver");

        source3.setUrl("jdbc:mysql://219.153.15.93:3306/kprd?characterEncoding=utf-8");
        source3.setUsername("kprd");
        source3.setPassword("kprdp@ssw0rd#@!");
        source3.setDriverClassName("com.mysql.jdbc.Driver");

        Map<Object,Object> targetMap = new LinkedHashMap<>();
        targetMap.put("source",source);
        targetMap.put("source2",source2);
        targetMap.put("source3",source3);
        DynamicDataSource dynamics = new DynamicDataSource();
        dynamics.setTargetDataSources(targetMap);
        dynamics.setDefaultTargetDataSource(source);

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamics);
        bean.setConfigLocation(new ClassPathResource("mybatis/SqlMapConfig.xml"));*/
        try {
            String resource = "mybatis/SqlMapConfig.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

            SqlSessionFactory sessionFactory = builder.build(reader,"development3");
            SqlSession session = sessionFactory.openSession();
            BlogMapper blogMapper = session.getMapper(BlogMapper.class);
            Map<String,Object> result = blogMapper.selectById("0b68882e2cc2489a831cb6e41c1d83fd");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
