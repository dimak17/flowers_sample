package com.impltech.reports.invoice.second_template;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex
 */
public class SecondInvoiceData {

    private SecondMainItems setInfo2() {
        SecondMainItems invoice = new SecondMainItems();

        //Company
        invoice.setInvoiceNumber("123456789102");
        invoice.setInvoiceDate(Date.valueOf(LocalDate.of(2017, 02, 02)));
        invoice.setShipmentDate(Date.valueOf(LocalDate.of(2017, 04, 05)));
        invoice.setFullName("PICO ACOSTA LUCIA ISABEL");
        invoice.setCompanyName("LUCY ROSES");
        invoice.setCompanyAddress("LA FLORIDA VIA A TOTORAS SN CEVALLOS");
        invoice.setCompanyBrandName("All flowers");
        invoice.setCompanyEmail("Lucyroses@gmail.com");
        invoice.setCompanyOfficePhone("593-6-3010777");
        invoice.setCompanyMobilePhone("593-9-87895731");

        //Bill Customer
        invoice.setCustomerName("TITANIK");
        invoice.setCustomerCompanyName("FN FUSION B.V.");
        invoice.setCustomerCountry("Ecuador");
        invoice.setCustomerCity("Quito");
        invoice.setCustomerStreet("P. F. von Sieboldlaan 1, 143 KG Aalsmeer");
        invoice.setCustomerPhone("6051578301");
        invoice.setCustomerID("123654987");
        invoice.setCustomerContactPersonName("Sedmundo Petrarka");

        //Ship Customer

        invoice.setCustomerLabel("LAKMUS");
        invoice.setLabelCountry("UKRAINE");
        invoice.setLabelCity("Kiev");
        invoice.setLabelStreet("Pushkina 20");
        invoice.setLabelPhone("380935779965");
        invoice.setLabelEmail("mamba@gmail.com");
        invoice.setLabelID("321546987");
        invoice.setLabelContactPersonName("Sinyavskiy Andrey");

        invoice.setCargoAgency("Panatlantic");
        invoice.setAirline("KLM");
        invoice.setaWB("074-49004546");
        invoice.sethAWB("GV5267F2845");

        //Season
        invoice.setSeason("Summer");
        invoice.setStartSeason(Date.valueOf(LocalDate.of(2017, 5, 01)));
        invoice.setEndSeason(Date.valueOf(LocalDate.of(2017, 9, 01)));

        invoice.setTypeOrder("Open Market");
        invoice.setMarket("Russian market");

        BigDecimal pricePerBox1;
        BigDecimal pricePerBox2;
        BigDecimal pricePerBox3;

        BigDecimal pricePerStem1 = new BigDecimal(0.21);
        BigDecimal pricePerStem2 = new BigDecimal(0.22);
        BigDecimal pricePerStem3 = new BigDecimal(0.20);

        Integer quantityOfStems1 = 350;
        Integer quantityOfStems2 = 350;
        Integer quantityOfStems3 = 250;

        pricePerBox1 = pricePerStem1.multiply(new BigDecimal(quantityOfStems1));
        pricePerBox2 = pricePerStem2.multiply(new BigDecimal(quantityOfStems2));
        pricePerBox3 = pricePerStem3.multiply(new BigDecimal(quantityOfStems3));

        List<SecondTableItems> items = new ArrayList<>();
        items.add(createTableItems(1, "HB", "EXPLORER", 70,
            14, 25, quantityOfStems1, pricePerStem1, pricePerBox1, new BigDecimal(0.00)));
        items.add(createTableItems(1, "HB", "IGUANA", 60,
            14, 25, quantityOfStems2, pricePerStem2, pricePerBox2, new BigDecimal(0.00)));
        items.add(createTableItems(1, "HB", "IGUANA", 80,
            10, 25, quantityOfStems3, pricePerStem3, pricePerBox3, new BigDecimal(0.00)));
        items.add(createTableItems(1, "HB", "LOLALA", 80,
            10, 25, quantityOfStems3, pricePerStem3, pricePerBox3, new BigDecimal(0.00)));

        BigDecimal totalInvPrice = new BigDecimal(0);
        Integer totalStems = new Integer(0);
        Integer totalPieces = new Integer(0);

        for (SecondTableItems item : items) {
            totalInvPrice = totalInvPrice.add(item.getPricePerBox());
            totalStems += item.getStemsPerBox();
            totalPieces += item.getBoxQuantity();
        }

        Float totalFB = new Float(totalPieces / 2.0);

        invoice.setTotalInvoicePrice(totalInvPrice);
        invoice.setTotalStems(totalStems);
        invoice.setTotalFB(totalFB);
        invoice.setItems(items);
        return invoice;
    }

    private SecondTableItems createTableItems
        (Integer boxQuantity, String boxTypes, String varietyTypes, Integer stemsPerBunch,
         Integer stemsLength, Integer bunchesPerBox, Integer stemsPerBox, BigDecimal pricePerStem,
         BigDecimal pricePerBox, BigDecimal boxCharge) {
        SecondTableItems item = new SecondTableItems();
        item.setBoxQuantity(boxQuantity);
        item.setBoxTypes(boxTypes);
        item.setVarietyTypes(varietyTypes);
        item.setStemsPerBunch(stemsPerBunch);
        item.setStemsLength(stemsLength);
        item.setBunchesPerBox(bunchesPerBox);
        item.setStemsPerBox(stemsPerBox);
        item.setPricePerStem(pricePerStem);
        item.setPricePerBox(pricePerBox);
        item.setBoxCharge(boxCharge);
        return item;
    }

    public SecondMainItems getSecondInvoice() {
        return setInfo2();
    }

    public JRDataSource createDataSource() {
        return new JRBeanCollectionDataSource(setInfo2().getItems());
    }

    public String checkCompanyItems(String item) {
        if (item == null || item.equals("")) {
            return "";
        }
        return "\n" + item + "\n";
    }

    public String checkCompanyItems(String item, String returnText) {
        if (item == null || item.equals("")) {
            return "";
        }
        return returnText + "\n";
    }

    public String checkCustomerItems(String item) {
        if (item == null || item.equals("")) {
            return "";
        }
        return "\n " + item;
    }

    public String checkCustomerItems(String item, String returnText) {
        if (item == null || item.equals("")) {
            return "";
        }
        return returnText;
    }
}
