package excel;

//import java.io.File;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Path("/get")
public class ExcelService {
	
	//private static final String FILE_PATH = "C:\\Users\\IBM_ADMIN\\Desktop\\meteor\\raw_data\\raw_report_sellers_mcq.xlsx";
	
	@GET
	@Produces("application/vnd.ms-excel")
	public Response getFile() {
		//File file = new File(FILE_PATH);
		XSSFWorkbook workbook = getDbWorkbook();
		StreamingOutput stream = new StreamingOutput() {
			@Override
			public void write(OutputStream os) throws IOException, WebApplicationException {
				workbook.write(os);
				workbook.close();
			}
		};
		ResponseBuilder response = Response.ok(stream);
		response.header("Content-Disposition", "attachment; filename=new-excel-file.xlsx");
		return response.build();
	}
	
	private XSSFWorkbook getDbWorkbook() {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Data");
		System.setProperty("db2.jcc.charsetDecoderEncoder", "3");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-data");
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT c from DataEntity c");
		List<DataEntity> list = q.getResultList();
		em.close();
		
		int rowCount = 0;
		int columnCount = 0;
		Row row = sheet.createRow(rowCount++);
		Cell cell_imts_header = row.createCell(columnCount++);
		cell_imts_header.setCellValue("IMTS");
		Cell cell_author_header = row.createCell(columnCount++);
		cell_author_header.setCellValue("Author");
		Cell cell_title_header = row.createCell(columnCount++);
		cell_title_header.setCellValue("Title");
		Cell cell_brand_header = row.createCell(columnCount++);
		cell_brand_header.setCellValue("Brand");
		Cell cell_primary_community_header = row.createCell(columnCount++);
		cell_primary_community_header.setCellValue("Primary Community");
		Cell cell_total_emails_sent_header = row.createCell(columnCount++);
		cell_total_emails_sent_header.setCellValue("Total Emails Sent");
		Cell cell_total_opens_header = row.createCell(columnCount++);
		cell_total_opens_header.setCellValue("Total Opens Header");
		Cell cell_total_ocr_header = row.createCell(columnCount++);
		cell_total_ocr_header.setCellValue("Total OCR");
		Cell cell_total_click_header = row.createCell(columnCount++);
		cell_total_click_header.setCellValue("Total Click");
		Cell cell_total_ccr_header = row.createCell(columnCount++);
		cell_total_ccr_header.setCellValue("Total CCR");
		
		for(DataEntity d: list) {
			row = sheet.createRow(rowCount++);
			columnCount = 0;
			Cell cell_imts = row.createCell(columnCount++);
			cell_imts.setCellValue(d.getImts());
			Cell cell_author = row.createCell(columnCount++);
			cell_author.setCellValue(d.getAuthor());
			Cell cell_title = row.createCell(columnCount++);
			cell_title.setCellValue(d.getTitle());
			Cell cell_brand = row.createCell(columnCount++);
			cell_brand.setCellValue(d.getBrand());
			Cell cell_primary_community = row.createCell(columnCount++);
			cell_primary_community.setCellValue(d.getPrimary_community());
			Cell cell_total_emails_sent = row.createCell(columnCount++);
			cell_total_emails_sent.setCellValue(d.getTotal_emails_sent());
			Cell cell_total_opens = row.createCell(columnCount++);
			cell_total_opens.setCellValue(d.getTotal_opens());
			Cell cell_total_ocr = row.createCell(columnCount++);
			cell_total_ocr.setCellValue(d.getTotal_ocr());
			Cell cell_total_click = row.createCell(columnCount++);
			cell_total_click.setCellValue(d.getTotal_click());
			Cell cell_total_ccr = row.createCell(columnCount++);
			cell_total_ccr.setCellValue(d.getTotal_ccr());
		}
		
		return workbook;
	}
	
	/*
	private XSSFWorkbook getWorkbook() {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Java Books");
		
		Object[][] bookData = {
				{"Head First Java", "Kathy Sierra", 79},
				{"Effective Java", "Joshua Bloch", 36},
				{"Clean Code", "Robert Martin", 42},
				{"Thinking in Java", "Bruce Eckel", 35}
		};
		
		int rowCount = 0;
		
		for(Object[] aBook: bookData) {
			Row row = sheet.createRow(++rowCount);
			int columnCount = 0;
			for(Object field : aBook) {
				Cell cell = row.createCell(++columnCount);
				if(field instanceof String) {
					cell.setCellValue((String)field);
				} else if(field instanceof Integer) {
					cell.setCellValue((Integer)field);
				}
			}
		}
		
		return workbook;
	}
	*/
}
