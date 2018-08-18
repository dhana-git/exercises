package com.mine.exercise.salesnotificationmsg.dto;

public class SaleMessage implements Message {

	private String type;
	private Integer value;
	private Integer occurrence = 1;
	private String adjustmentOp;

	public SaleMessage() {
	}

	public SaleMessage(String type, Integer value) {
		this.type = type;
		this.value = value;
	}

	public SaleMessage(String type, Integer value, Integer occurrence) {
		this(type, value);
		this.occurrence = occurrence;
	}

	public SaleMessage(String type, Integer value, String adjustmentOp) {
		this(type, value);
		this.adjustmentOp = adjustmentOp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getOccurrence() {
		return occurrence;
	}

	public void setOccurrence(Integer occurrence) {
		this.occurrence = occurrence;
	}

	public String getAdjustmentOp() {
		return adjustmentOp;
	}

	public void setAdjustmentOp(String adjustmentOp) {
		this.adjustmentOp = adjustmentOp;
	}

}
