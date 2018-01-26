package com.impltech.reports.tiquet;

import net.sf.jasperreports.engine.JRDataSource;

/**
 * @author dima
 */
public class TiquetData {

    Tiquet customer = new Tiquet();

	private TiquetDataUtils shippingLabel;

	public TiquetData() {
		shippingLabel = createShippingLabel();
	}

    /**
     *
     * @return
     */
	private TiquetDataUtils createShippingLabel() {

		TiquetDataUtils shippingLabel = new TiquetDataUtils();

		shippingLabel.setData(createTiquetData("Lucy Roses", 2, "ROSE", "EC05-AGCF-01-0869",
            "145-0463-7953", "369149", "ASANA",
            "Pacific Cargo", "Russia", "Mix Color", 70, 12, 300));
		shippingLabel.setBoxId(customer.getLabelBoxId());

		return shippingLabel;
	}

    /**
     *
     * @param farmName
     * @param labelBoxId
     * @param flowerType
     * @param agroQualityNumber
     * @param AWB
     * @param HAWB
     * @param label
     * @param agency
     * @param country
     * @param variety
     * @param length
     * @param bunches
     * @param stems
     * @return
     */
	private Tiquet createTiquetData(String farmName, Integer labelBoxId, String flowerType, String agroQualityNumber, String AWB, String HAWB, String label,
                                    String agency, String country, String variety, Integer length, Integer bunches, Integer stems) {
		customer.setFarmName(farmName);
        customer.setLabelBoxId(labelBoxId);
		customer.setFlowerType(flowerType);
		customer.setAgroQuialityNumber(agroQualityNumber);
		customer.setAWB(AWB);
        customer.setHAWB(HAWB);
        customer.setLabel(label);
        customer.setAgency(agency);
        customer.setCountry(country);
        customer.setVariety(variety);
        customer.setLength(length);
        customer.setBunch(bunches);
        customer.setStems(stems);

		return customer;
	}


    /**
     *
     * @return
     */
	public TiquetDataUtils getShippingLabel() {
		return shippingLabel;
	}

    /**
     *
     * @return
     */
	public JRDataSource createDataSource() {
		return null;
	}
}
