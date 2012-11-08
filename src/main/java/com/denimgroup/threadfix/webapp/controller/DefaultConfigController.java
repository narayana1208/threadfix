package com.denimgroup.threadfix.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.denimgroup.threadfix.data.entities.Role;
import com.denimgroup.threadfix.service.DefaultConfigService;
import com.denimgroup.threadfix.service.RoleService;
import com.denimgroup.threadfix.service.SanitizedLogger;
import com.denimgroup.threadfix.webapp.viewmodels.DefaultsConfigModel;

@Controller
@RequestMapping("/configuration/defaults")
public class DefaultConfigController {
	
	protected final SanitizedLogger log = new SanitizedLogger(DefaultConfigController.class);

	private RoleService roleService = null;
	private DefaultConfigService defaultConfigService = null;
	
	@Autowired
	public DefaultConfigController(DefaultConfigService defaultConfigService,
			RoleService roleService) {
		this.roleService = roleService;
		this.defaultConfigService = defaultConfigService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setAllowedFields(new String [] { "defaultRoleId", "globalGroupEnabled" });
	}
	
	@ModelAttribute
	public List<Role> populateRoles() {
		return roleService.loadAll();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model) {
		model.addAttribute("model", defaultConfigService.loadCurrentConfiguration());
		return "config/defaults";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processForm(@ModelAttribute DefaultsConfigModel configModel) {
		
		defaultConfigService.saveConfiguration(configModel);
		
		return "redirect:/configuration";
	}
	
}