package ru.otus.homework;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import ru.otus.homework.model.User;

public class TemplateManager {

	private static TemplateManager templateManager = new TemplateManager();
	
	private static final String RESOURCE_FOLDER_NAME = "/WEB-INF/templates";
	
	private final Configuration config;
	
	private static final String ADMIN_PAGE_TEMPLATE_NAME = "admin_page.ftl";
	private static final String ADMIN_PAGE_USER_NAME_MODEL_KEY = "name";
	
	private static final String USER_CREATE_TEMPLATE_NAME = "create_user.ftl";
	
	private static final String USERS_LIST_TEMPLATE_NAME = "users_list.ftl";
	private static final String USERS_LIST_USERS_MODEL_KEY = "users";
	
	private TemplateManager() {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
		cfg.setClassForTemplateLoading(this.getClass(), RESOURCE_FOLDER_NAME);
		config = cfg;
	}
	
	public static TemplateManager getInstance() {
		return templateManager;
	}
	
	public boolean launchAdminPageTemplate(String userName, Writer out) {
		Map<String, Object> model = new HashMap<>();
		model.put(ADMIN_PAGE_USER_NAME_MODEL_KEY, userName);
		return launchTemplate(ADMIN_PAGE_TEMPLATE_NAME, model, out);
	}
	
	public boolean launchUserCreateTemplate(Writer out) {
		return launchTemplate(USER_CREATE_TEMPLATE_NAME, null, out);
	}
	
	public boolean launchUsersListTemplate(List<User> usersList, Writer out) {
		Map<String, Object> model = new HashMap<>();
		model.put("users", usersList);
		model.put(USERS_LIST_USERS_MODEL_KEY, usersList);
		return launchTemplate(USERS_LIST_TEMPLATE_NAME, model, out);
	}
	
	private boolean launchTemplate(String templateName, Map<String, Object> model, Writer out) {
		boolean result = false;
		try {
			Template template = config.getTemplate(templateName);
			template.process(model, out);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
