package com.zhytnik.converter.apachepoi.doc;

import com.zhytnik.converter.common.Converter;
import com.zhytnik.converter.common.PageObserver;
import com.zhytnik.converter.common.Type;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.zhytnik.converter.common.Type.DOCX;

/**
 * @author Alexey Zhytnik
 * @since 29.08.2016
 */
public class PoiDocxToPdfConverter implements Converter<InputStream, ByteArrayOutputStream>, PageObserver<InputStream> {

    @Override
    public ByteArrayOutputStream convert(InputStream document) throws Exception {
        try (XWPFDocument docx = new XWPFDocument(document)) {
            return convert(docx);
        }
    }

    private ByteArrayOutputStream convert(XWPFDocument document) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        PdfConverter.getInstance().convert(document, output, PdfOptions.create());
        return output;
    }

    @Override
    public int getPageCount(InputStream document) throws Exception {
        try (XWPFDocument docx = new XWPFDocument(document)) {
            return docx
                    .getProperties()
                    .getExtendedProperties()
                    .getUnderlyingProperties()
                    .getPages();
        }
    }

    @Override
    public boolean isSupported(Type type) {
        return type == DOCX;
    }

    @Override
    public String toString() {
        return "Apache POI docx to pdf converter";
    }
}
