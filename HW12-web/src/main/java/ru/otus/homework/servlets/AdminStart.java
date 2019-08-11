package ru.otus.homework.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.*;
import java.util.*;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AdminStart extends HttpServlet {

	private static final String TEMPLATE_NAME = "admin_page.ftl";
	
	private static final String USER_NAME_MODEL_KEY = "name";
	
	private static final long serialVersionUID = 1L;
	
	private Configuration config;
	
	public AdminStart(Configuration config) {
		this.config = config;
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws MalformedTemplateNameException, ParseException, IOException {

		Map<String, Object> root = new HashMap<>();
		root.put(USER_NAME_MODEL_KEY, request.getUserPrincipal().getName());
		
		Template temp = config.getTemplate(TEMPLATE_NAME);
		
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = response.getWriter();
		
		try {
			temp.process(root, printWriter);
		} catch (TemplateException e) {
			e.printStackTrace();
		}
    }
}
