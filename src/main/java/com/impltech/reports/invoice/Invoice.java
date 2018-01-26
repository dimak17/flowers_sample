package com.impltech.reports.invoice;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author dima
 */
public class Invoice {

	private Integer id;
	private BigDecimal shipping;
	private Double tax;
	private MainItems billTo;
	private MainItems shipTo;
	private List<TableItems> items;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getShipping() {
		return shipping;
	}

	public void setShipping(BigDecimal shipping) {
		this.shipping = shipping;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public MainItems getBillTo() {
		return billTo;
	}

	public void setBillTo(MainItems billTo) {
		this.billTo = billTo;
	}

	public MainItems getShipTo() {
		return shipTo;
	}

	public void setShipTo(MainItems shipTo) {
		this.shipTo = shipTo;
	}

	public List<TableItems> getItems() {
		return items;
	}

	public void setItems(List<TableItems> items) {
		this.items = items;
	}
}
