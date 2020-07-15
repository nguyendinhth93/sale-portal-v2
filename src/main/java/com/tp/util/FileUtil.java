package com.tp.util;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;
import org.omnifaces.util.Faces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.faces.context.FacesContext;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dungha7 on 10/24/2016.
 */
public class FileUtil extends FileUtils {
    private static final String template_path   = "template_path";
    private static final String report_out_path = "report_out_path";


    public FileUtil() {
    }

    /**
     * @param bean all params  need to export file with jxls
     * @throws Exception
     */
    public static void exportFile(FileExportBean bean) throws Exception {
        bean.validate();
        String template = bean.getTemplatePath() + bean.getTemplateName();
        String fileExport = bean.getExportFileName();
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(template, bean.getBean(), fileExport);
    }

    /**
     * @return
     * @desc get template path default
     */
    public static String getTemplatePath() {
//        String templatePath = bundle.getString(template_path);
//        return Faces.getRealPath(templatePath) + File.separator;
        return Faces.getRealPath("/resources/templates/") + File.separator;
    }

    /**
     * @return
     * @desc get output path default when export report
     */
    public static final String getOutputPath() {
//        String reportoutPath = bundle.getString(report_out_path);
//        return Faces.getRealPath(reportoutPath) + File.separator;
        return Faces.getRealPath("/resources/report_out/") + File.separator;

    }

    /**
     * @param fileName
     * @return streamed content of file (fileName) in template path
     * @throws Exception
     */
    public static StreamedContent getTemplateFileContent(String fileName) throws Exception {
        File file = new File(new StringBuilder(FileUtil.getTemplatePath()).append(fileName).toString());
        InputStream is = new FileInputStream(file);
        return new DefaultStreamedContent(is, FacesContext.getCurrentInstance().getExternalContext().getMimeType(fileName), fileName);

    }

    /**
     * @param subFolder
     * @param fileName
     * @return streamed content of file (fileName) in template path
     * @throws Exception
     */
    public static StreamedContent getTemplateFileContent(String subFolder, String fileName) throws Exception {
        File file = new File(new StringBuilder(FileUtil.getTemplatePath()).append(subFolder).append(fileName).toString());
        InputStream is = new FileInputStream(file);
        return new DefaultStreamedContent(is, FacesContext.getCurrentInstance().getExternalContext().getMimeType(fileName), fileName);
    }


    /**
     * @param file
     * @return streamed content of file
     * @throws Exception
     */
    public static StreamedContent getFileContent(File file) throws Exception {
        InputStream is = new FileInputStream(file);
        return new DefaultStreamedContent(is, FacesContext.getCurrentInstance().getExternalContext().getMimeType(file.getName()), file.getName());
    }

    /**
     * @param content
     * @return streamed content of file
     * @throws Exception
     */
    public static StreamedContent getStreamedContent(byte[] content, String fileName) throws Exception {
        InputStream is = new ByteArrayInputStream(content);
        return new DefaultStreamedContent(is, FacesContext.getCurrentInstance().getExternalContext().getMimeType(fileName), fileName);
    }

    /**
     * @param bean
     * @return export file not save to harddisk
     * @throws Exception
     * @author SinhHV
     */
    public static Workbook exportWorkBook(FileExportBean bean) throws Exception {
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(bean.getTemplatePath() + bean.getTemplateName()));
        XLSTransformer transformer = new XLSTransformer();
        return transformer.transformXLS(is, bean.getBean());
    }

    /**
     * @param bean
     * @return export file not save to harddisk
     * @throws Exception
     * @author SinhHV
     */
    public static StreamedContent exportToStreamed(FileExportBean bean) throws Exception {
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(bean.getTemplatePath() + bean.getTemplateName()));
        XLSTransformer transformer = new XLSTransformer();
        Workbook workbook = transformer.transformXLS(is, bean.getBean());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return new DefaultStreamedContent(inputStream, FacesContext.getCurrentInstance().getExternalContext().getMimeType(bean.getOutName()), bean.getOutName());
    }


    public static byte[] encodeUTF8(String string) {
        return string.getBytes(Charset.forName("UTF-8"));
    }


    public static String decodeUTF8(byte[] bytes) {
        return new String(bytes, Charset.forName("UTF-8"));
    }

    /**
     * ham xuat file text
     *
     * @param lsData
     * @param outName
     * @return
     * @throws Exception
     * @author thanhnt77
     */
    public static StreamedContent exportToStreamedText(List<String> lsData, String outName) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(outputStream);
        for (String element : lsData) {
            out.write(encodeUTF8(element));
        }
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return new DefaultStreamedContent(inputStream, FacesContext.getCurrentInstance().getExternalContext().getMimeType(outName), outName);
    }

    public static byte[] mergePdfContent(List<byte[]> listPdfContent) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int fileIndex = 0;
        Document document = null;
        PdfCopy writer = null;
        PdfReader reader;
        try {
            ArrayList masterBookMarkList = new ArrayList();
            for (fileIndex = 0; fileIndex < listPdfContent.size(); fileIndex++) {

                InputStream is = new ByteArrayInputStream(listPdfContent.get(fileIndex));

                reader = new PdfReader(is);

                int totalPages = reader.getNumberOfPages();

                if (fileIndex == 0) {

                    document = new Document(reader.getPageSizeWithRotation(1));

                    writer = new PdfCopy(document, outputStream);
                    document.open();
                }
                PdfImportedPage page;
                if (writer != null) {
                    for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
                        page = writer.getImportedPage(reader, currentPage);
                        writer.addPage(page);
                    }
                }

            }
            if (!masterBookMarkList.isEmpty() && writer != null) {
                writer.setOutlines(masterBookMarkList);

            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }

        return outputStream.toByteArray();
    }

}
