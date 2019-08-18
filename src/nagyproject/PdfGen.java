package nagyproject;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.Pfm2afm;
import java.io.FileOutputStream;
import java.util.concurrent.Phaser;
import javafx.collections.ObservableList;

public class PdfGen {

    public void pdfGeneration(String fajlnev,ObservableList<Person> data) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fajlnev + ".pdf"));
            document.open();
            Image image1 = Image.getInstance(getClass().getResource("/kiskutya.png"));
            image1.scaleToFit(250, 180);
            image1.setAbsolutePosition(260f, 680f);
            document.add(image1);
            //T�bl�zat
            document.add(new Paragraph("\n\n\n\n\n\n\n"));
            float [] columnwidths = {3,3,4};
            PdfPTable table = new PdfPTable(columnwidths);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(new Phrase("KontaktLista"));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(3);
            table.addCell(cell);
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Név");
            table.addCell("Mobilszám");
            table.addCell("Email");
            table.setHeaderRows(1);
            table.getDefaultCell().setBackgroundColor(BaseColor.GRAY);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
             for (int i = 1; i <= data.size(); i++) {
                Person actualPerson = data.get(i - 1);
               
                table.addCell(actualPerson.getName());
                table.addCell(actualPerson.getPhonenumber());
                table.addCell(actualPerson.getEmail());
             }
            document.add(table);

         // hozza adatam p�r sor majd 
         //   document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n" + text, FontFactory.getFont("betutipus",BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
            Chunk signature = new Chunk("\n\n hozzáadatam");
            Paragraph base = new Paragraph(signature);
            
            document.add(base);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }



}
