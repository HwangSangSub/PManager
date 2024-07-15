package kr.sshsoft.vo;

import java.util.Date;

public class ProductVO {
	// 필드
	private String pdNo; // pd_no :상품번호
	private String pdName; // pd_name :상품명
	private String pdPrice; // pd_price :상품가격
	private String memo; // memo :상품설명
	private String pdCnt; // pd_cnt :재고수량
	private String modDate; // mod_date :재고변경일
	private Date creationDate; // :상품 등록일

	private String total; // total
	private String maxPrice; // max_price
	private String minPrice; // min_price
	private String avgPrice; // avg_price
	private String pdTotalCnt; // pd_total_cnt

	private String piNo; // pi_no
	private String piType; // pi_type
	private String piCnt; // pi_cnt

	private String piTotCnt; // pi_tot_cnt
	private String inCnt; // in_cnt
	private String outCnt; // out_cnt
	private String pdInven; // pd_inven

	// 값을 보기 위해서
	@Override
	public String toString() {
		return "ProductVO [pdNo=" + pdNo + ", pdName=" + pdName + ", pdPrice=" + pdPrice + ", memo=" + memo + ", pdCnt="
				+ pdCnt + ", modDate=" + modDate + ", creationDate=" + creationDate + "]";
	}

	// 간략하게 보여주기 위함
	public String briefShow() {
		return "\t" + pdNo + "\t " + pdName + "  \t\t " + pdPrice + "원     \t\t " + pdCnt + "개";
	}

	// 간략하게 보여주기 위함(piType == "IN" ? "입고" : "출고")
	public String briefPutShow() {
		return "\t" + pdName + "   \t\t " + (piType.equals("IN") ? "입고" : "출고") + " \t\t\t " + piCnt + "개 \t\t"
				+ creationDate;
	}

	public String getPdNo() {
		return pdNo;
	}

	public void setPdNo(String pdNo) {
		this.pdNo = pdNo;
	}

	public String getPdName() {
		return pdName;
	}

	public void setPdName(String pdName) {
		this.pdName = pdName;
	}

	public String getPdPrice() {
		return pdPrice;
	}

	public void setPdPrice(String pdPrice) {
		this.pdPrice = pdPrice;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPdCnt() {
		return pdCnt;
	}

	public void setPdCnt(String pdCnt) {
		this.pdCnt = pdCnt;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public String getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(String avgPrice) {
		this.avgPrice = avgPrice;
	}

	public String getPdTotalCnt() {
		return pdTotalCnt;
	}

	public void setPdTotalCnt(String pdTotalCnt) {
		this.pdTotalCnt = pdTotalCnt;
	}

	public String getPiNo() {
		return piNo;
	}

	public void setPiNo(String piNo) {
		this.piNo = piNo;
	}

	public String getPiType() {
		return piType;
	}

	public void setPiType(String piType) {
		this.piType = piType;
	}

	public String getPiCnt() {
		return piCnt;
	}

	public void setPiCnt(String piCnt) {
		this.piCnt = piCnt;
	}

	public String getPiTotCnt() {
		return piTotCnt;
	}

	public void setPiTotCnt(String piTotCnt) {
		this.piTotCnt = piTotCnt;
	}

	public String getInCnt() {
		return inCnt;
	}

	public void setInCnt(String inCnt) {
		this.inCnt = inCnt;
	}

	public String getOutCnt() {
		return outCnt;
	}

	public void setOutCnt(String outCnt) {
		this.outCnt = outCnt;
	}

	public String getPdInven() {
		return pdInven;
	}

	public void setPdInven(String pdInven) {
		this.pdInven = pdInven;
	}
}
