package com.quantchi.tianji.service.search.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 
 * 
 * <p>Title:AppConfig.java</p>
 * <p>Company: quant-chi</p>
 * <p>Description:</p>
 * @author:maxj
 * @date: 2018年5月3日
 *
 *
 */
@Configuration("appConfig")
public class AppConfig {

    @Value("${index}")
    private String esIndexCompany;
    @Value("${type}")
    private String esTypeCompany;

    @Value("${event.index}")
    private String esIndexEvent;

    @Value("${event.type}")
    private String esTypeEvent;

    public String getEsIndexEvent() {
        return esIndexEvent;
    }

    public void setEsIndexEvent(String esIndexEvent) {
        this.esIndexEvent = esIndexEvent;
    }

    public String getEsTypeEvent() {
        return esTypeEvent;
    }

    public void setEsTypeEvent(String esTypeEvent) {
        this.esTypeEvent = esTypeEvent;
    }

    public String getEsIndexCompany() {
        return esIndexCompany;
    }

    public void setEsIndexCompany(String esIndexCompany) {
        this.esIndexCompany = esIndexCompany;
    }

    public String getEsTypeCompany() {
        return esTypeCompany;
    }

    public void setEsTypeCompany(String esTypeCompany) {
        this.esTypeCompany = esTypeCompany;
    }
}
