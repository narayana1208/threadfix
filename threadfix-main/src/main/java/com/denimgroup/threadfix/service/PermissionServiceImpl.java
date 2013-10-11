package com.denimgroup.threadfix.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.denimgroup.threadfix.data.entities.Application;
import com.denimgroup.threadfix.data.entities.Organization;
import com.denimgroup.threadfix.data.entities.Permission;
import com.denimgroup.threadfix.data.entities.RemoteProviderType;
import com.denimgroup.threadfix.data.entities.Waf;
import com.denimgroup.threadfix.plugin.permissions.PermissionServiceDelegateFactory;

@Service
public class PermissionServiceImpl implements PermissionService {
	
	PermissionService delegate = null;
	
	public PermissionServiceImpl() {
		delegate = PermissionServiceDelegateFactory.getDelegate();
	}

	@Override
	public boolean isAuthorized(Permission permission, Integer orgId,
			Integer appId) {
		return delegate.isAuthorized(permission, orgId, appId);
	}

	@Override
	public void addPermissions(Model model, Integer orgId, Integer appId,
			Permission... permissions) {
		delegate.addPermissions(model, orgId, appId, permissions);
	}

	@Override
	public void addPermissions(ModelAndView modelAndView, Integer orgId,
			Integer appId, Permission... permissions) {
		delegate.addPermissions(modelAndView, orgId, appId, permissions);
	}

	@Override
	public boolean canSeeRules(Waf waf) {
		return delegate.canSeeRules(waf);
	}

	@Override
	public Set<Integer> getAuthenticatedAppIds() {
		return delegate.getAuthenticatedAppIds();
	}

	@Override
	public Set<Integer> getAuthenticatedTeamIds() {
		return delegate.getAuthenticatedTeamIds();
	}

	@Override
	public List<Application> filterApps(Organization organization) {
		return delegate.filterApps(organization);
	}

	@Override
	public void filterApps(List<RemoteProviderType> providers) {
		delegate.filterApps(providers);
	}
	
	@Override
	public boolean isEnterprise() {
		return delegate.isEnterprise();
	}
}
