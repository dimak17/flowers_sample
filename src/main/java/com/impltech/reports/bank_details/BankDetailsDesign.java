package com.impltech.reports.bank_details;

import com.impltech.web.rest.util.ReportsUtils;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.RectangleBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalImageAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static com.impltech.reports.Templates.footerComponent;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

public class BankDetailsDesign {

    public static ComponentBuilder<?, ?> titleBankDetailsComponent;
    public static ComponentBuilder<?, ?> titleLogoBankDetailsComponent;
    public static ComponentBuilder<?, ?> titleDateBankDetailsComponent;
    public static ComponentBuilder<?, ?> bodyBankDetailsComponent;

    private String companyName;
    private String date;

    public BankDetailsDesign(String companyName, String date) {
        this.companyName = companyName;
        this.date = date;
    }

    public BankDetailsDesign() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public static void bankDetailsReportBuilder(String companyName, String date, String path, String bankDetails) throws FileNotFoundException {
        BankDetailsDesign designBD = new BankDetailsDesign(companyName, date);
        try {
            JasperReportBuilder report = designBD.build(bankDetails);

            report.toPdf(new FileOutputStream(path));
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

    public JasperReportBuilder build(String bankDetails) throws DRException {
        JasperReportBuilder report = report();

        StyleBuilder rectangleStyle = stl.style().setRadius(5);
        RectangleBuilder roundRectangleStyle = cmp.roundRectangle().setStyle(rectangleStyle);

        titleLogoBankDetailsComponent =

            cmp.verticalList(
                cmp.image(ReportsUtils.getImagePath().toString())
                    .setHorizontalImageAlignment(HorizontalImageAlignment.LEFT)
                    .setFixedWidth(150).setFixedHeight(150));

        titleBankDetailsComponent =

            cmp.verticalList(

                cmp.text(getCompanyName() + " Bank detils")
                    .setStyle(TemplatesBankDetails.boldCenteredStyle));

        titleDateBankDetailsComponent =

            cmp.verticalList(
                cmp.text("Date: " + getDate())
                    .setStyle(TemplatesBankDetails.dateRightTopStyle));

        bodyBankDetailsComponent =
            cmp.verticalList(
                cmp.text(bankDetails)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER))
                .setStyle(TemplatesBankDetails.bodyStyle)
                .setBackgroundComponent(roundRectangleStyle);

        HorizontalListBuilder titleBD = cmp.horizontalList()
            .add(titleLogoBankDetailsComponent, titleBankDetailsComponent);

        VerticalListBuilder titleBD1 = cmp.verticalList()
            .add(titleDateBankDetailsComponent);

        HorizontalListBuilder bodyBD = cmp.horizontalList()
            .add(bodyBankDetailsComponent);

        return report
            .title(TemplatesBankDetails.createBDTitleComponent(titleBD, titleBD1))
            .pageHeader(TemplatesBankDetails.createBOdyComponent(bodyBD))
            .pageFooter(footerComponent);
    }
}
