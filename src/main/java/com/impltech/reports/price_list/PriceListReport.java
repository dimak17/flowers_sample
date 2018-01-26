package com.impltech.reports.price_list;

import com.impltech.domain.TypeOfFlower;
import com.impltech.reports.Templates;
import com.impltech.security.SecurityUtils;
import com.impltech.service.dto.DefaultPriceListDTO;
import com.impltech.service.dto.PriceListDTO;
import com.impltech.service.dto.SeasonPriceListDTO;
import com.impltech.web.rest.util.ReportsUtils;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsExporterBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.constant.HorizontalImageAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.SplitType;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import org.apache.commons.lang.WordUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.impltech.reports.Templates.bold12CenteredStyle;
import static com.impltech.reports.Templates.titleStyle;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

public class PriceListReport<T extends PriceListDTO> {

    private List<T> priceLists;
    private String priceListType;

    public void setPriceLists(List<T> priceLists, String priceListType) {
        this.priceLists = priceLists;
        this.priceListType = priceListType;
    }

    public JasperReportBuilder build() throws FileNotFoundException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> itemColumn = col.column("Variety", "variety", type.stringType()).setFixedWidth(80);
        TextColumnBuilder<String> fortyColumn = col.column("40", "forty", type.stringType());
        TextColumnBuilder<String> fiftyColumn = col.column("50", "fifty", type.stringType());
        TextColumnBuilder<String> sixtyColumn = col.column("60", "sixty", type.stringType());
        TextColumnBuilder<String> seventyColumn = col.column("70", "seventy", type.stringType());
        TextColumnBuilder<String> eightyColumn = col.column("80", "eighty", type.stringType());
        TextColumnBuilder<String> ninetyColumn = col.column("90", "ninety", type.stringType());
        TextColumnBuilder<String> hundredColumn = col.column("100", "hundred", type.stringType());
        TextColumnBuilder<String> hundredTenColumn = col.column("110", "hundred_ten", type.stringType());
        TextColumnBuilder<String> hundredTwentyColumn = col.column("120", "hundred_twenty", type.stringType());
        TextColumnBuilder<String> hundredThirtyColumn = col.column("130", "hundred_thirty", type.stringType());
        TextColumnBuilder<String> hundredFortyColumn = col.column("140", "hundred_forty", type.stringType());
        TextColumnBuilder<String> hundredFiftyColumn = col.column("150", "hundred_fifty", type.stringType());
        TextColumnBuilder<String> hundredSixtyColumn = col.column("160", "hundred_sixty", type.stringType());
        TextColumnBuilder<String> hundredSeventyColumn = col.column("170", "hundred_seventy", type.stringType());
        TextColumnBuilder<String> hundredEightyColumn = col.column("180", "hundred_eighty", type.stringType());
        TextColumnBuilder<String> hundredNinetyColumn = col.column("190", "hundred_ninety", type.stringType());
        TextColumnBuilder<String> twoHundredColumn = col.column("200", "two_hundred", type.stringType());
        TextColumnBuilder<String> twoHundredTenColumn = col.column("210", "two_hundred_ten", type.stringType());

        report
            .setTemplate(Templates.priceListTemplate)

            .title(
                cmp.horizontalList(
                    cmp.image(ReportsUtils.getImagePath().toString())
                        .setFixedDimension(200, 150)
                        .setHorizontalImageAlignment(HorizontalImageAlignment.CENTER),
                    cmp.verticalList(cmp.text(SecurityUtils.getCurrentCompanyUser().getCompany().getFarmName())
                            .setStyle(titleStyle.setVerticalTextAlignment(VerticalTextAlignment.MIDDLE))
                            .setHeight(10),
                        cmp.horizontalList(
                            cmp.text(getPriceListTitleColumns())
                                .setStyle(stl.style(titleStyle).setFontSize(12))
                                .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                            cmp.text(getPriceListInfo())
                                .setStyle(stl.style(titleStyle).setFontSize(12))
                                .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)))));

        List<TypeOfFlower> types = priceLists.stream().map((priceLists) -> (
            priceLists.getVariety().getTypeOfFlower())).distinct().collect(Collectors.toList());

        for (TypeOfFlower type : types) {
            JasperReportBuilder subReport = report();

            subReport
                .setTemplate(Templates.priceListTemplate)
                .addTitle((types.size() != 1 ? cmp.text(type.getName()) : cmp.text(""))
                    .setStyle(stl.style(bold12CenteredStyle.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER))).setHeight(20))
                .columns(itemColumn, fortyColumn, fiftyColumn, sixtyColumn, seventyColumn, eightyColumn, ninetyColumn, hundredColumn,
                    hundredTenColumn, hundredTwentyColumn, hundredThirtyColumn, hundredFortyColumn, hundredFiftyColumn, hundredSixtyColumn,
                    hundredSeventyColumn, hundredEightyColumn, hundredNinetyColumn, twoHundredColumn, twoHundredTenColumn)
                .sortBy(asc(itemColumn))
                .setDetailSplitType(SplitType.PREVENT)
                .setDataSource(createDataSource(type));

            report
                .detail(cmp.subreport(subReport));
        }

        report
            .setDataSource(createReportDataSource())
            .pageFooter(Templates.footerComponent);

        return report;
    }

    private String checkZeroPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) == 0) {
            return "-";
        }
        return String.valueOf("$" + price.setScale(2));
    }

    private String getPriceListTitleColumns() {
        return ((priceLists.get(0) instanceof SeasonPriceListDTO) ? "Season: \n Date: " : " Type of price list: ") +
            "\n Market: \n Shipping policy: \n Type of flowers: ";
    }

    private String getPriceListInfo() {
        String priceListInfo = priceListType.equals("LOW") || priceListType.equals("HIGH") ? WordUtils.capitalizeFully(priceListType) + " market" : ""
            + (priceListType.equals("DEFAULT") ? WordUtils.capitalizeFully(priceListType) : "");
        if (priceLists.get(0) instanceof SeasonPriceListDTO) {
            SeasonPriceListDTO seasonPriceListDTO = (SeasonPriceListDTO) priceLists.get(0);
            priceListInfo += seasonPriceListDTO.getMarketSeason().getSeason().getSeasonName()
                + "\n" + seasonPriceListDTO.getMarketSeason().getSeason().getStartDate()
                + " " + seasonPriceListDTO.getMarketSeason().getSeason().getEndDate()
                + "\n" + ((SeasonPriceListDTO) priceLists.get(0)).getMarketSeason().getMarket().getName();
        } else if (priceLists.get(0) instanceof DefaultPriceListDTO) {
            priceListInfo += "\n" + ((DefaultPriceListDTO) priceLists.get(0)).getMarket().getName();
        }
        priceListInfo += "\n" + priceLists.get(0).getShippingPolicy().getShortName();
        if (priceLists.stream().allMatch(x -> x.getVariety().getTypeOfFlower().equals(priceLists.get(0).getVariety().getTypeOfFlower()))) {
            priceListInfo += "\n" + priceLists.get(0).getVariety().getTypeOfFlower().getName();
            return priceListInfo;
        }
        return priceListInfo + "\nAll";
    }

    private JRDataSource createReportDataSource() {
        DRDataSource dataSource = new DRDataSource("");
        dataSource.add("");
        return dataSource;
    }

    /**
     * @return datasource.
     */
    private JRDataSource createDataSource(TypeOfFlower type) {
        DRDataSource dataSource = new DRDataSource("variety", "forty", "fifty", "sixty", "seventy", "eighty", "ninety",
            "hundred", "hundred_ten", "hundred_twenty", "hundred_thirty", "hundred_forty", "hundred_fifty", "hundred_sixty",
            "hundred_seventy", "hundred_eighty", "hundred_ninety", "two_hundred", "two_hundred_ten");

        priceLists.stream().filter(priceLists -> priceLists.getVariety().getTypeOfFlower().getId().equals(type.getId()))
            .forEach(variety -> dataSource.add(
                variety.getVariety().getName(),
                checkZeroPrice(variety.get_40()),
                checkZeroPrice(variety.get_50()),
                checkZeroPrice(variety.get_60()),
                checkZeroPrice(variety.get_70()),
                checkZeroPrice(variety.get_80()),
                checkZeroPrice(variety.get_90()),
                checkZeroPrice(variety.get_100()),
                checkZeroPrice(variety.get_110()),
                checkZeroPrice(variety.get_120()),
                checkZeroPrice(variety.get_130()),
                checkZeroPrice(variety.get_140()),
                checkZeroPrice(variety.get_150()),
                checkZeroPrice(variety.get_160()),
                checkZeroPrice(variety.get_170()),
                checkZeroPrice(variety.get_180()),
                checkZeroPrice(variety.get_190()),
                checkZeroPrice(variety.get_200()),
                checkZeroPrice(variety.get_210())
            ));
        return dataSource;
    }

    /**
     * @throws FileNotFoundException
     */
    public static void priceListReportPdfBuilder(String path) throws FileNotFoundException {
        try {
            new PriceListReport().build().toPdf(new FileOutputStream(path));
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws FileNotFoundException
     */
    public void priceListReportXlsBuilder(String path) throws FileNotFoundException {
        JasperXlsExporterBuilder xlsExporter = export.xlsExporter(path)
            .setDetectCellType(true)
            .setIgnorePageMargins(true)
            .setWhitePageBackground(false)
            .setRemoveEmptySpaceBetweenColumns(true);
        try {
            this.build().toXls(xlsExporter);
        } catch (DRException e) {
            e.printStackTrace();
        }
    }
}
