package com.impltech.web.rest.util;

import com.impltech.domain.TypeOfFlower;
import com.impltech.reports.balance.BalanceDesign;
import com.impltech.reports.dispo.DispoDesign;
import com.impltech.reports.invoice.InvoiceDesign;
import com.impltech.reports.price_list.PriceListReport;
import com.impltech.reports.tiquet.TiquetData;
import com.impltech.reports.tiquet.TiquetDesign;
import com.impltech.reports.variety_list.VarietyListDesign;
import com.impltech.security.SecurityUtils;
import com.impltech.service.dto.DefaultPriceListDTO;
import com.impltech.service.dto.PriceListDTO;
import com.impltech.service.dto.SeasonPriceListDTO;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Created by dima
 */
public class ReportsUtils {

    private DispoDesign dispoDesign = new DispoDesign();
    private BalanceDesign balanceDesign = new BalanceDesign();
    private InvoiceDesign invoiceDesign = new InvoiceDesign();
    private PriceListReport priceListReport = new PriceListReport();
    private TiquetDesign tiquetDesign = new TiquetDesign();
    private TiquetData tiquetData = new TiquetData();
    private VarietyListDesign varietyListDesign = new VarietyListDesign();

    private final String dispoFileName = dispoDesign.getDataInvoice().getInvoice().getCompanyName() + " Actual Dispo in HB " + LocalDate.now() + ".pdf";
    private final String balanceFileName = balanceDesign.getDataInvoice().getInvoice().getCustomerName() + "-Balance.pdf";
    private final String priceListFileName = "Actual ";
    private final String tiquetFileName = tiquetData.getShippingLabel().getData().getFarmName() + "-" + tiquetData.getShippingLabel().getData().getLabel() + " tiquet" + ".pdf";
    private final String varietyListFileName = varietyListDesign.getDataInvoice().getInvoice().getCompanyName() + " Actual Variety List" + ".pdf";
    private final String invoiceFileName = invoiceDesign.data.getInvoice().getInvoiceNumber() + ".pdf";

    private final static Path projectPath = Paths.get(".").toAbsolutePath().getParent();
    private final Path pathToReportsFolder = Paths.get("/src/main/resources/reports/");

    private final Path dispoReportPath = Paths.get(projectPath.resolve(pathToReportsFolder) + dispoFileName);
    private final Path balanceReportPath = Paths.get(projectPath.resolve(pathToReportsFolder) + balanceFileName);
    private final Path invoiceReportPath = Paths.get(projectPath.resolve(pathToReportsFolder) + invoiceFileName);
    private final Path priceListReportPath = Paths.get(projectPath.resolve(pathToReportsFolder) + priceListFileName);
    private final Path tiquetReportPath = Paths.get(projectPath.resolve(pathToReportsFolder) + tiquetFileName);
    private final Path varietyListReportPath = Paths.get(projectPath.resolve(pathToReportsFolder) + varietyListFileName);

    private final static Path logoPath = Paths.get(projectPath + "/src/main/resources/farms/miraflowers/companies/logo/default.jpg");

    public static Path getProjectPath() {
        return projectPath;
    }

    public static Path getImagePath() {
        File[] defaultList = ImageUtil.getLogoDir().listFiles();
        return defaultList[0].toPath();
    }
    public static Path getLogoPath() { return logoPath; }


    public DispoDesign getDispoDesign() {
        return dispoDesign;
    }

    public String getDispoFileName() {
        return dispoFileName;
    }

    public Path getDispoReportPath() {
        return dispoReportPath;
    }


    public BalanceDesign getBalanceDesign() {
        return balanceDesign;
    }

    public Path getBalanceReportPath() {
        return balanceReportPath;
    }

    public String getBalanceFileName() {
        return balanceFileName;
    }


    public InvoiceDesign getInvoiceDesign() {
        return invoiceDesign;
    }

    public Path getInvoiceReportPath() {
        return invoiceReportPath;
    }

    public String getInvoiceFileName() {
        return invoiceFileName;
    }


    public PriceListReport getPriceListReport() {
        return priceListReport;
    }

    public Path getPriceListReportPath() {
        return priceListReportPath;
    }

    public String getPriceListFileName(List<PriceListDTO> pricelist, String priceListType){

        String priceListInfo = SecurityUtils.getCurrentCompanyUser().getCompany().getFarmName();

        if (pricelist.get(0) instanceof SeasonPriceListDTO){
            SeasonPriceListDTO seasonPriceListDTO = (SeasonPriceListDTO) pricelist.get(0);
            priceListInfo += " " + seasonPriceListDTO.getMarketSeason().getSeason().getSeasonName()
               + " " + seasonPriceListDTO.getMarketSeason().getMarket().getName();
        } else {
            priceListInfo += " " + ((DefaultPriceListDTO)pricelist.get(0)).getMarket().getName();
        }
        priceListInfo += " " + pricelist.get(0).getShippingPolicy().getShortName();

        List<TypeOfFlower> types = pricelist.stream().map((priceList) -> (
            priceList.getVariety().getTypeOfFlower())).distinct().collect(Collectors.toList());

        if(types.size() != 1){
            priceListInfo += " all flowers";
        } else {
            priceListInfo += " " + pricelist.get(0).getVariety().getTypeOfFlower().getName();
        }
        return priceListFileName + priceListInfo + " " + priceListType.toLowerCase() + " price list";
    }


    public TiquetDesign getTiquetDesign() {
        return tiquetDesign;
    }

    public Path getTiquetReportPath() {
        return tiquetReportPath;
    }

    public String getTiquetFileName() {
        return tiquetFileName;
    }


    public VarietyListDesign getVarietyListDesign() {
        return varietyListDesign;
    }

    public Path getVarietyListReportPath() {
        return varietyListReportPath;
    }

    public String getVarietyListFileName() {
        return varietyListFileName;
    }

}
