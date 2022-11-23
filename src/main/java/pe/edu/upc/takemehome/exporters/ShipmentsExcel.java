package pe.edu.upc.takemehome.exporters;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pe.edu.upc.takemehome.entities.Shipment;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShipmentsExcel {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Shipment> shipments;

    public ShipmentsExcel(List<Shipment> shipments){
        this.shipments = shipments;
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
        createCell(row,1,"State",style);
        createCell(row,2,"Payment",style);
        createCell(row,3,"Payment Date",style);
        createCell(row,4,"Arrival Date",style);
        createCell(row,5,"Order",style);
        createCell(row,6,"User",style);
        createCell(row,7,"Courier",style);
    }


    public  void writeDataLines(){
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(false);
        font.setFontHeight(12);
        style.setFont(font);
        for(Shipment shipment: shipments){
            Row row = sheet.createRow(rowCount);
            int colCount=0;
            createCell(row, colCount,shipment.getId().toString(),style);
            createCell(row,colCount+1,shipment.getState().toString(),style);
            createCell(row,colCount+2,shipment.getPayment().toString(),style);
            createCell(row,colCount+3,shipment.getPaymentDate().toString(),style);
            createCell(row,colCount+4,shipment.getArrivalDate().toString(),style);
            createCell(row,colCount+5,shipment.getOrder().getId().toString(),style);
            createCell(row,colCount+6,shipment.getOrder().getUser().getUsername(),style);
            createCell(row,colCount+7,shipment.getUser().getUsername(),style);
            rowCount++;

        }
    }

    public void writeFooterLine(){

    }

    public void export(HttpServletResponse response) throws IOException {
        sheet=workbook.createSheet("ReporteShipments");

        writeHeaderLine();
        writeDataLines();
        writeFooterLine();

        ServletOutputStream servletOutputStream = response.getOutputStream();
        workbook.write(servletOutputStream);
        workbook.close();
        servletOutputStream.close();
    }
}
