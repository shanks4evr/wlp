package excel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CCDB.ARTICLE_STATS_REPORTING")
public class DataEntity {
	@Id
	@Column(name="IMTS")
	private String imts;
	@Column(name="PRIMARY_COMMUNITY")
	private String primary_community;
	@Column(name="TOTAL_EMAILS_SENT")
	private String total_emails_sent;
	@Column(name="AUTHOR")
	private String author;
	@Column(name="BRAND")
	private String brand;
	@Column(name="TITLE")
	private String title;
	@Column(name="TOTAL_OPENS")
	private String total_opens;
	@Column(name="TOTAL_OCR")
	private String total_ocr;
	@Column(name="TOTAL_CLICK")
	private String total_click;
	@Column(name="TOTAL_CCR")
	private String total_ccr;
	public String getImts() {
		return imts;
	}
	public void setImts(String imts) {
		this.imts = imts;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTotal_opens() {
		return total_opens;
	}
	public void setTotal_opens(String total_opens) {
		this.total_opens = total_opens;
	}
	public String getTotal_ocr() {
		return total_ocr;
	}
	public void setTotal_ocr(String total_ocr) {
		this.total_ocr = total_ocr;
	}
	public String getTotal_click() {
		return total_click;
	}
	public void setTotal_click(String total_click) {
		this.total_click = total_click;
	}
	public String getTotal_ccr() {
		return total_ccr;
	}
	public void setTotal_ccr(String total_ccr) {
		this.total_ccr = total_ccr;
	}
	public String getPrimary_community() {
		return primary_community;
	}
	public void setPrimary_community(String primary_community) {
		this.primary_community = primary_community;
	}
	public String getTotal_emails_sent() {
		return total_emails_sent;
	}
	public void setTotal_emails_sent(String total_emails_sent) {
		this.total_emails_sent = total_emails_sent;
	}
}
