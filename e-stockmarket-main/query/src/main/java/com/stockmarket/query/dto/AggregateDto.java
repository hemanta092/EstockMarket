package com.stockmarket.query.dto;

public class AggregateDto 
{
	private String companyCode;
	private Double avgStockPrice;
	private Double minStockPrice;
	private Double maxStockPrice;
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public Double getAvgStockPrice() {
		return avgStockPrice;
	}
	public void setAvgStockPrice(Double avgStockPrice) {
		this.avgStockPrice = avgStockPrice;
	}
	public Double getMinStockPrice() {
		return minStockPrice;
	}
	public void setMinStockPrice(Double minStockPrice) {
		this.minStockPrice = minStockPrice;
	}
	public Double getMaxStockPrice() {
		return maxStockPrice;
	}
	public void setMaxStockPrice(Double maxStockPrice) {
		this.maxStockPrice = maxStockPrice;
	}
	

}
