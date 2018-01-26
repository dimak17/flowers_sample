package com.impltech.calculator;

import java.sql.Date;

/**
 * @author alex
 */
public class PostHarvestManager {
	private int id_sort;
	private int id_agronome;
	private int count_of_banch;
	private int count_of_remaider;
	private int id_sales_market;
	private String note;
	private String force_majeure;
	private Date data;

	public int getId_sort() {
		return id_sort;
	}
	public void setId_sort(int id_sort) {
		this.id_sort = id_sort;
	}
	public int getId_agronome() {
		return id_agronome;
	}
	public void setId_agronome(int id_agronome) {
		this.id_agronome = id_agronome;
	}
	public int getId_sales_market() {
		return id_sales_market;
	}
	public void setId_sales_market(int id_sales_market) {
		this.id_sales_market = id_sales_market;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getForce_majeure() {
		return force_majeure;
	}
	public void setForce_majeure(String force_majeure) {
		this.force_majeure = force_majeure;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public int getCount_of_banch() {
		return count_of_banch;
	}
	public void setCount_of_banch(int count_of_banch) {
		this.count_of_banch = count_of_banch;
	}
	public int getCount_of_remaider() {
		return count_of_remaider;
	}
	public void setCount_of_remaider(int count_of_remaider) {
		this.count_of_remaider = count_of_remaider;
	}
}
