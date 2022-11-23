package pe.edu.upc.takemehome.exporters;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pe.edu.upc.takemehome.entities.Order;
import pe.edu.upc.takemehome.entities.Shipment;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OrdersExcel {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Order> orders;

    public OrdersExcel(List<Order> orders) {
        this.orders = orders;
        workbook = new XSSFWorkbook();
    }
    public void createCell (Row row, int column, Object value, CellStyle style){
        sheet.autoSizeColumn(column);
        Cell cell = row.createCell(column);
        if(value instanceof Integer){
            cell.setCellValue((Integer)value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double)value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean)value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long)value);
        }else{
            cell.setCellValue((String)value);
        }
        cell.setCellStyle(style);
    }

    public void writeHeaderLine(){
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);
        createCell(row,0,"Id",style);
        createCell(row,1,"Limit Date",style);
        createCell(row,2,"Destination Country",style);
        createCell(row,3,"Destination City",style);
        createCell(row,4,"Destination Address",style);
        createCell(row,5,"Origin Country",style);
        createCell(row,6,"Origin City",style);
        createCell(row,7,"Name Product",style);
        createCell(row,8,"Category Product",style);
        createCell(row,9,"Price Product",style);
        createCell(row,10,"Url Product",style);
        createCell(row,11,"Dimensions Product",style);
        createCell(row,12,"User",style);
    }

    public  void writeDataLines() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(false);
        font.setFontHeight(12);
        style.setFont(font);
        for (Order order : orders) {
            Row row = sheet.createRow(rowCount);
            int colCount = 0;
            createCell(row, colCount, order.getId().toString(), style);
            createCell(row, colCount + 1, order.getLimitDate().toString(), style);
            createCell(row, colCount + 2, order.getDestinationCountry(), style);
            createCell(row, colCount + 3, order.getDestinationCity(), style);
            createCell(row, colCount + 4, order.getDestinationAddress(), style);
            createCell(row, colCount + 5, order.getOriginCountry(), style);
            createCell(row, colCount + 6, order.getOriginCity(), style);
            createCell(row, colCount + 7, order.getNameProduct(), style);
            createCell(row, colCount + 8, order.getCategoryProduct(), style);
            createCell(row, colCount + 9, order.getPriceProduct(), style);
            createCell(row, colCount + 10, order.getUrlProduct(), style);
            createCell(row, colCount + 11, order.getDimensionsProduct(), style);
            createCell(row, colCount + 12, order.getUser().getUsername(), style);
            rowCount++;
        }
    }

    public void writeFooterLine(){

    }
    public void export(HttpServletResponse response) throws IOException {
        sheet=workbook.createSheet("ReporteOrders");

        writeHeaderLine();
        writeDataLines();
        writeFooterLine();

        ServletOutputStream servletOutputStream = response.getOutputStream();
        workbook.write(servletOutputStream);
        workbook.close();
        servletOutputStream.close();
    }

}