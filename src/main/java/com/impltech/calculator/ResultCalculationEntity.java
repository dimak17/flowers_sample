package com.impltech.calculator;

/**
 * @author alex
 */
public class ResultCalculationEntity {
	private int id_agronome_data;
	private int fb;
	private int hb;
	private int qb;
	private int banch;
	private int stamps;

	public int getId_agronome_data() {
		return id_agronome_data;
	}
	public void setId_agronome_data(int id_agronome_data) {
		this.id_agronome_data = id_agronome_data;
	}
	public int getFb() {
		return fb;
	}
	public void setFb(int fb) {
		this.fb = fb;
	}
	public int getHb() {
		return hb;
	}
	public void setHb(int hb) {
		this.hb = hb;
	}
	public int getQb() {
		return qb;
	}
	public void setQb(int qb) {
		this.qb = qb;
	}
	public int getBanch() {
		return banch;
	}
	public void setBanch(int banch) {
		this.banch = banch;
	}
	public int getStamps() {
		return stamps;
	}
	public void setStamps(int stamps) {
		this.stamps = stamps;
	}
	@Override
	public String toString() {
		return "ResultCalculationEntity [id_agronome_data=" + id_agronome_data + ", fb=" + fb + ", hb=" + hb + ", qb="
				+ qb + ", banch=" + banch + ", stamps=" + stamps + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + banch;
		result = prime * result + fb;
		result = prime * result + hb;
		result = prime * result + id_agronome_data;
		result = prime * result + qb;
		result = prime * result + stamps;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResultCalculationEntity other = (ResultCalculationEntity) obj;
		if (banch != other.banch)
			return false;
		if (fb != other.fb)
			return false;
		if (hb != other.hb)
			return false;
		if (id_agronome_data != other.id_agronome_data)
			return false;
		if (qb != other.qb)
			return false;
		if (stamps != other.stamps)
			return false;
		return true;
	}




}
