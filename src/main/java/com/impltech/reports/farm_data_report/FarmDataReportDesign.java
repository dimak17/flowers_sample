package com.impltech.reports.farm_data_report;

import com.impltech.domain.Company;
import com.impltech.domain.Market;
import com.impltech.domain.TypeOfFlower;
import com.impltech.reports.Templates;
import com.impltech.web.rest.util.ReportsUtils;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.component.*;
import net.sf.dynamicreports.report.constant.ComponentPositionType;
import net.sf.dynamicreports.report.constant.HorizontalImageAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;

/**
 * @author alex
 */
public class FarmDataReportDesign {

    public static ComponentBuilder<?, ?> titleReportsComponent;
    public static ComponentBuilder<?, ?> companyInfoComponents;
    public static ComponentBuilder<?,?> companyContactsComponents;
    public static ComponentBuilder<?,?> companyReport;

    public JasperReportBuilder build(LocalDate currentDate,  List<Market> markets, Company company, List<TypeOfFlower> typeOfFlowers) throws DRException, FileNotFoundException {

        JasperReportBuilder report = report();

        String typeOfFlowerString = "";
        for (int i = 0; i < typeOfFlowers.size(); i++){
            if (i < typeOfFlowers.size() - 1) {
                typeOfFlowerString.concat(" " + typeOfFlowers.get(i).getName() + ",");
            }else{
                typeOfFlowerString.concat(" " + typeOfFlowers.get(i).getName());
            }
        }

            titleReportsComponent =
                cmp.verticalList(

                    cmp.horizontalList(

                        cmp.image(ReportsUtils.getImagePath().toString())
                            .setFixedDimension(200, 150)
                            .setHorizontalImageAlignment(HorizontalImageAlignment.LEFT)
                            .setPositionType(ComponentPositionType.FIX_RELATIVE_TO_TOP),

                        cmp.text("\"" + company.getFarmName() + "\""
                            + "\npresentation letter")
                            .setFixedDimension(200, 150)
                            .setStyle(Templates.bold12CenteredStyle.setFontSize(10))
                            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                            .setPositionType(ComponentPositionType.FIX_RELATIVE_TO_BOTTOM),

                        cmp.text("Current date: ")
                            .setFixedDimension(80, 150)
                            .setStyle(Templates.bold12CenteredStyle.setFontSize(10))
                            .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)
                            .setPositionType(ComponentPositionType.FIX_RELATIVE_TO_BOTTOM),

                        cmp.text(currentDate.toString())
                            .setFixedDimension(70, 150)
                            .setStyle(Templates.bold12CenteredStyle.setFontSize(10))
                            .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)
                            .setPositionType(ComponentPositionType.FIX_RELATIVE_TO_BOTTOM)));

            companyInfoComponents =
                cmp.verticalList(

                    cmp.verticalGap(10),

                    cmp.text("Dear Sirs, ")
                        .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)
                        .setStyle(Templates.bold18CenteredStyle.setFontSize(12)),

                    cmp.text("We are flower growers from " + company.getCountry() + " and we are glad to introduce you our Farm " + company.getFarmName() +".")
                        .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)
                        .setStyle(Templates.bold18CenteredStyle.setFontSize(12)),

                    cmp.text("We produce and export " + typeOfFlowerString + ".")
                        .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)
                        .setStyle(Templates.bold18CenteredStyle.setFontSize(12)),

                    cmp.text("Our farm size is over " + company.getFarmSize() + "HA.")
                        .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)
                        .setStyle(Templates.bold18CenteredStyle.setFontSize(12)));

            HorizontalListBuilder info1 = cmp.horizontalList()
                .add(cmp.text("Our company works on such markets: ")
                    .setStyle(Templates.bold18CenteredStyle.setFontSize(10))
                    .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)
                    .setPositionType(ComponentPositionType.FIX_RELATIVE_TO_TOP));

            RectangleBuilder rectangleBackground = cmp.rectangle();

            VerticalListBuilder info2 = cmp.verticalList().setBackgroundComponent(rectangleBackground);

        for (int i = 0; i < markets.size(); i++){
            if (i < markets.size() - 1) {
                info2.add(cmp.text(markets.get(i).getName()+",")
                    .setStyle(Templates.bold18CenteredStyle.setFontSize(10))
                    .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT));
            }else{
                info2.add(cmp.text(markets.get(i).getName()+".")
                    .setStyle(Templates.bold18CenteredStyle.setFontSize(10))
                    .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT));
            }

        }

            if (markets.size() == 1){
                companyContactsComponents =
                    cmp.horizontalList(
                        cmp.text("Our company works on the " + markets.get(1).getName() + " market.")
                            .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)
                            .setStyle(Templates.bold18CenteredStyle.setFontSize(10)));
            }else{
                companyContactsComponents = cmp.verticalList()
                    .add(cmp.verticalList()
                        .add(info1, info2)
                        .add(cmp.verticalGap(15)));
            }

            HorizontalListBuilder contact1 = cmp.horizontalList()
                .add(cmp.text("General Email:")
                .setStyle(Templates.bold18CenteredStyle.setFontSize(10)),
                    cmp.text(
                        company.getGeneralEmailAddress()))
                            .setBackgroundComponent(rectangleBackground);

            HorizontalListBuilder contact2 = cmp.horizontalList()
                .add(cmp.text("General Office Phone:")
                        .setStyle(Templates.bold18CenteredStyle.setFontSize(10)),
                    cmp.text(company.getGeneralOfficePhone()))
                .setBackgroundComponent(rectangleBackground);

            HorizontalListBuilder contact3 = cmp.horizontalList()
                .add(cmp.text("Our address: ")
                        .setStyle(Templates.bold18CenteredStyle.setFontSize(10)),
                    cmp.text(company.getCountry() + ", " + company.getCity() + ", " + company.getAddress()))
                .setBackgroundComponent(rectangleBackground);

            HorizontalListBuilder contact4 = cmp.horizontalList()
                .add(cmp.text("You can also visit our website: ")
                        .setStyle(Templates.bold18CenteredStyle.setFontSize(10)),
                    cmp.text("https://www.google.com/"))
                .setBackgroundComponent(rectangleBackground);
            //TODO Change it to DB data

            VerticalListBuilder contacts = cmp.verticalList()
                .add(cmp.text("Please contact Us: ")
                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER))
                .add(cmp.verticalGap(20))
                .add(contact1, contact2, contact3, contact4);

            companyReport = cmp.horizontalList()
                .add(cmp.text("Best regards,")
                    .setStyle(Templates.bold12CenteredStyle.setFontSize(10))
                    .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT))
                .add(cmp.text(company.getFarmName())
                    .setStyle(Templates.bold12CenteredStyle.setFontSize(10))
                    .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT));

        report
            .setTemplate(Templates.reportTemplate)

            .title(
                Templates.createPresentationList(titleReportsComponent, companyInfoComponents, companyContactsComponents, contacts),
                cmp.horizontalList().setStyle(stl.style(10)).setGap(50).add(cmp.verticalGap(10)))
            .pageHeader(companyReport);

        return report;
    }





    public static void farmDataReportBuilder(String path, LocalDate currentDate,
                                             List<Market> markets, Company company, List<TypeOfFlower> typeOfFlowers) throws FileNotFoundException {
        FarmDataReportDesign design = new FarmDataReportDesign();
        try {
            JasperReportBuilder report = design.build(currentDate, markets, company, typeOfFlowers);
            report.toPdf(new FileOutputStream(path));
        } catch (DRException e) {
            e.printStackTrace();
        }
    }
}
