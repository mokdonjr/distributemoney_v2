package com.seungchan.distributemoney_v2.common.service;

import com.seungchan.distributemoney_v2.common.BaseBean;
import com.seungchan.distributemoney_v2.common.util.FileUtil;
import com.seungchan.distributemoney_v2.common.util.properties.PropertiesModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MaintenanceService extends BaseBean {

    @Value(value = "${spring.datasource.url}")
    private String databaseUrl;
    @Value(value = "${spring.datasource.username}")
    private String databaseUsername;
    @Value(value = "${spring.datasource.password}")
    private String databasePassword;


    @PostConstruct
    public void initMaintenanceService() {
        String gitCommitId = null;
        try {
            PropertiesModel gitPropertiesModel = new PropertiesModel(FileUtil.readFile(new ClassPathResource("git.properties")).toString());
            gitCommitId = String.valueOf(gitPropertiesModel.getParameter("git.commit.id.full"));
        } catch (Exception e) {
//            e.printStackTrace();
        }
        System.out.println("gitCommitId:" + gitCommitId);

        // DB 마이그레이션
//        Flyway flyway = Flyway.configure().dataSource(databaseUrl, databaseUsername, databasePassword).load();
//        flyway.migrate();
    }
}
