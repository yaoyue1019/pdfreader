package com.yaoyue.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

public class InvoiceUtil {
	public static final String SHOUJU = "PDF BOLETA DE VENTA ELECTR?NICA BPP1-5.pdf";
	public static final String FAPIAO = "PDF FACTURA ELECTR?NICA FPP1-3.pdf";

	public static void main(String[] args) {
		ReadPdf("PDF FACTURA ELECTR_NICA FPP1-31.pdf");
	}

	public static void ReadPdf(String path) {
		PdfDocument document = null;
		try {
			PdfReader reader = new PdfReader(new File(path));
			document = new PdfDocument(reader);
			for (int i = 1, len = document.getNumberOfPages(); i <= len; i++) {
				PdfPage page = document.getPage(i);
				String text = PdfTextExtractor.getTextFromPage(page);
				// log(text);
				Pattern patter = Pattern.compile(REG_SHOUJU);
				Matcher matcher = patter.matcher(text);
				if (matcher.find()) {
					log("get match count:%d",matcher.groupCount());
					for (int j = 1, gCount = matcher.groupCount(); j <= gCount; j++) {
						log(matcher.group(j));
					}
				}else {
					log("pattern not found");
				}
				log("----------------------------------------------");
				log(text);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	public static void log(String format, Object... objects) {
		log(String.format(format, objects));
	}

	public static void log(String text) {
		System.out.println(text);
	}

	public static final String REG_SHOUJU = "RUC (\\d{11})" + "\n"
			+ "FACTURA" + "\n"
			+ "ELECTRÓNICA" + "\n"
			+ "[\\s\\S]+"
			+ "CLIENTE FECHA EMISIÓN: (\\d{2}/\\d{2}/\\d{4})" + "\n"
			+ "(.*)FECHA DE VENC: (\\d{2}/\\d{2}/\\d{4})";
}
