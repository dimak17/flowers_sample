package com.impltech.reports.invoice;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dima
 */
public class InvoiceData {

    /**
     *
     * @return
     */
	private MainItems setInfo() {

        MainItems invoice = new MainItems();
        BigDecimal pricePerBox1;
        BigDecimal pricePerBox2;
        BigDecimal pricePerBox3;

        BigDecimal pricePerStem1 = new BigDecimal(0.21);
        BigDecimal pricePerStem2 = new BigDecimal(0.22);
        BigDecimal pricePerStem3 = new BigDecimal(0.20);

        Integer quantityOfStems1 = 350;
        Integer quantityOfStems2 = 350;
        Integer quantityOfStems3 = 250;

        invoice.setInvoiceNumber("001-001-000017901");
        invoice.setInvoiceDate(Date.valueOf(LocalDate.of(2017, 02, 15)));
        invoice.setShipmentDate(Date.valueOf(LocalDate.of(2017, 02, 15)));

        invoice.setCompanyName("Gardaexport");
        invoice.setCompanyBrandName("Blooming Acres");
        invoice.setCoordinationName("San Luis De Mira");
        invoice.setCompanyCountry("Ecuador");
        invoice.setCompanyCity("Quito");
        invoice.setCompanyStreet("Calle 94 № 15-32 OFC 402");
        invoice.setCompanyPhoneNumber("593-6-3010777");
        invoice.setCompanyCelNumber("593-9-87895731");
        invoice.setCompanyEmail("gardaexport@gmail.com");
        invoice.setCompanyFax("N/A");

        invoice.setAWB("074-49004546");
        invoice.setDaughterAWB("GV5267F2845");
        invoice.setAgency("Panatlantic");
        invoice.setAirLine("KLM");

        invoice.setCustomerLabel("LAKMUS");
        invoice.setLabelCity("Kyiv");
        invoice.setLabelCountry("Ukraine");
        invoice.setLabelPhone("+380678560934");

        invoice.setCustomerName("MosFlor");
        invoice.setCustomerCountry("Russia");
        invoice.setCustomerCity("San Peterburgo");
        invoice.setCustomerStreet("Shturmanskaya-32");
        invoice.setCustomerPhone("7812704686");

        pricePerBox1 = pricePerStem1.multiply(new BigDecimal(quantityOfStems1));
        pricePerBox2 = pricePerStem2.multiply(new BigDecimal(quantityOfStems2));
        pricePerBox3 = pricePerStem3.multiply(new BigDecimal(quantityOfStems3));

		List<TableItems> items = new ArrayList<TableItems>();
		items.add(createItem(
                         1, "HB", "EXPLORER", 60, 14, 25, quantityOfStems1, pricePerStem1, pricePerBox1, new BigDecimal(0.00)));
        items.add(createItem(
                         1, "HB", "IGUANA", 60, 14, 25, quantityOfStems2, pricePerStem2, pricePerBox2, new BigDecimal(0.00)));
        items.add(createItem(
                         1, "HB", "MIX COLOR", 70, 10, 25, quantityOfStems3, pricePerStem3, pricePerBox3, new BigDecimal(0.00)));

        BigDecimal totalInvPrice = new BigDecimal(0);
        Integer totalStems = new Integer(0);
        Integer totalPieces = new Integer(0);


        for(TableItems item : items){
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

    /**
     *
     * @param boxQuantity
     * @param boxType
     * @param varietyTypes
     * @param stemsLength
     * @param bunchesPerBox
     * @param stemsPerBunch
     * @param stemsPerBox
     * @param pricePerStem
     * @param pricePerBox
     * @param boxCharge
     * @return
     */
	private TableItems createItem(Integer boxQuantity, String boxType,
         String varietyTypes, Integer stemsLength, Integer bunchesPerBox,
         Integer stemsPerBunch, Integer stemsPerBox,
         BigDecimal pricePerStem, BigDecimal pricePerBox, BigDecimal boxCharge) {

		TableItems item = new TableItems();

        item.setBoxQuantity(boxQuantity);
        item.setBoxTypes(boxType);
        item.setVarietyTypes(varietyTypes);
        item.setStemsLength(stemsLength);
        item.setBunchesPerBox(bunchesPerBox);
        item.setStemsPerBunch(stemsPerBunch);
        item.setStemsPerBox(stemsPerBox);
        item.setPricePerStem(pricePerStem);
        item.setPricePerBox(pricePerBox);
        item.setBoxCharge(boxCharge);

		return item;
	}

	public MainItems getInvoice() {
		return setInfo();
	}

	public JRDataSource createDataSource() {
		return new JRBeanCollectionDataSource(setInfo().getItems());
	}
}
