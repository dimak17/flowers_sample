package com.impltech.reports;

import com.impltech.config.*;
import com.impltech.reports.balance.BalanceDesign;
import com.impltech.reports.balance.second_balance.SecondBalanceDesign;
import com.impltech.reports.dispo.DispoDesign;
import com.impltech.reports.dispo.second_dispo.SecondDispoDesign;
import com.impltech.reports.farm_data_report.FarmDataReportDesign;
import com.impltech.reports.invoice.InvoiceDesign;
import com.impltech.reports.invoice.second_template.SecondInvoiceDesign;
import com.impltech.reports.price_list.PriceListReport;
import com.impltech.reports.price_list.second_price_list.SecondPriceList;
import com.impltech.reports.price_list.second_price_list.ThirdPriceList;
import com.impltech.reports.tiquet.TiquetDesign;
import com.impltech.reports.variety_list.VarietyListDesign;
import com.impltech.service.AuditEventService;
import com.impltech.service.MarketService;
import com.impltech.web.rest.util.ReportsUtils;
import com.impltech.reports.variety_list.second_variety_list.SecondVarietlyDesign;
import io.github.jhipster.config.JHipsterProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.spring4.SpringTemplateEngine;
import java.io.FileNotFoundException;


/**
 * Created by dima
 */
@RunWith(SpringRunner.class)
@EnableJpaRepositories("com.impltech.repository.*")
@ComponentScan(basePackageClasses = {FarmDataReportDesign.class, MarketService.class},
    excludeFilters = {@ComponentScan.Filter(classes = {AuditEventService.class}, type = FilterType.ASSIGNABLE_TYPE)})
@EntityScan("com.softhill.domain*")
@TestPropertySource(value = "classpath:config/application-dev.yml")
@ContextConfiguration(classes = {
    ApplicationProperties.class,
    BCryptPasswordEncoder.class,
    SpringTemplateEngine.class,
    JavaMailSenderImpl.class,
    JpaRepositoriesAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class,
    LiquibaseProperties.class,
    DataSourceAutoConfiguration.class,
    JHipsterProperties.class,
    DatabaseConfiguration.class,
    AsyncConfiguration.class,
})
public class ReportsTest {


    @Test
    public void balanceReportsTest() throws FileNotFoundException {

        String path = ReportsUtils.getProjectPath().toString() + ReportsTestUtils.getPathToTestReportsFolder() + ReportsTestUtils.getBalancePathName();
        BalanceDesign.balanceReportBuilder(path);
    }

    @Test
    public void dispoReportsTest() throws FileNotFoundException {
        String path = ReportsUtils.getProjectPath().toString() + ReportsTestUtils.getPathToTestReportsFolder() + ReportsTestUtils.getDispoPathName();
        DispoDesign.dispoReportBuilder(path);
    }

    @Test
    public void invoiceReportsTest() throws FileNotFoundException {
        String path = ReportsUtils.getProjectPath().toString() + ReportsTestUtils.getPathToTestReportsFolder() + ReportsTestUtils.getInvoicePathName();
        InvoiceDesign.invoiceReportBuilder(path);
    }

    @Test
    public void tiquetReportsTest() throws FileNotFoundException {
        String path = ReportsUtils.getProjectPath().toString() + ReportsTestUtils.getPathToTestReportsFolder() + ReportsTestUtils.getTiquetPathName();
        TiquetDesign.tiquetReportBuilder(path);
    }

    @Test
    public void varietyListTest() throws FileNotFoundException {
        String path = ReportsUtils.getProjectPath().toString() + ReportsTestUtils.getPathToTestReportsFolder() + ReportsTestUtils.getVarietyListPathName();
        VarietyListDesign.varietyListReportBuilder(path);
    }

    @Test
    public void secondInvoiceTest() throws FileNotFoundException {

        String path = ReportsUtils.getProjectPath().toString() + ReportsTestUtils.getPathToTestReportsFolder() + ReportsTestUtils.getSecondInvoicePathName();
        SecondInvoiceDesign.invoiceReportBuilder(path);
    }

    @Test
    public void secondBalanceTest() throws FileNotFoundException {

        String path = ReportsUtils.getProjectPath().toString() + ReportsTestUtils.getPathToTestReportsFolder() + ReportsTestUtils.getSecondBalancePathName();
        SecondBalanceDesign.balanceReportBuilder(path);
    }


    @Test
    public void secondPriceList() throws FileNotFoundException {
        String path = ReportsUtils.getProjectPath().toString() + ReportsTestUtils.getPathToTestReportsFolder() + ReportsTestUtils.getSecondPriceListPathName();
        SecondPriceList.secondPriceListReportBuilder(path);

    }


    @Test
    public void thirdPriceList() throws FileNotFoundException {
        String path = ReportsUtils.getProjectPath().toString() + ReportsTestUtils.getPathToTestReportsFolder() + ReportsTestUtils.getThirdPriceListPathName();
        ThirdPriceList.thirdPriceListReportBuilder(path);
    }

    @Test
    public void fourthPriceListPdf() throws FileNotFoundException {
        String path = ReportsUtils.getProjectPath().toString() + ReportsTestUtils.getPathToTestReportsFolder() + ReportsTestUtils.getFourthPriceListPathName();
        PriceListReport.priceListReportPdfBuilder(path);
    }

    @Test
    public void secondDispoReportBuilder() throws FileNotFoundException {
        String path = ReportsUtils.getProjectPath().toString() + ReportsTestUtils.getPathToTestReportsFolder() + ReportsTestUtils.getSecondDispoPathName();
        SecondDispoDesign.secondDispoReportBuilder(path);
    }

    @Test
    public void secondVarietlyList() throws FileNotFoundException {
        String path = ReportsUtils.getProjectPath().toString() + ReportsTestUtils.getPathToTestReportsFolder() + ReportsTestUtils.getSecondVarietyListPathName();
        SecondVarietlyDesign.secondVarietyListReportBuilder(path);
    }

}
