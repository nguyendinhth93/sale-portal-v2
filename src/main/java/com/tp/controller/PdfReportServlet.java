package com.tp.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tp.dto.PdfDTO;

@WebServlet("/viewFile")
public class PdfReportServlet extends HttpServlet {

    public static final String PDF_DATA = "reportBytes";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PdfDTO pdfDTO = (PdfDTO) request.getSession().getAttribute(PDF_DATA);
        byte[] content = pdfDTO.getPdfBytes();
        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        response.setContentLength(content.length);
        response.setHeader("Content-Disposition", "inline;filename="+(pdfDTO.getFileName()));
        ByteArrayInputStream in = new ByteArrayInputStream(content);
        int len;
        byte[] buffer = new byte[1024];
        while ((len = in.read(buffer)) > 0) {
            response.getOutputStream().write(buffer, 0, len);
        }
        in.close();
        if(pdfDTO.getExcelBytes()==null||pdfDTO.isRemoveAble()){
            request.getSession().setAttribute(PDF_DATA, null);
        }
        else{
            pdfDTO.setRemoveAble(true);
            request.getSession().setAttribute(PDF_DATA, pdfDTO);
        }
    }

}