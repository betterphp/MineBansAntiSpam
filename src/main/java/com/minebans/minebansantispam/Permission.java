package com.minebans.minebansantispam;

import org.bukkit.permissions.PermissionDefault;

import uk.co.jacekk.bukkit.baseplugin.permissions.PluginPermission;

public class Permission {
	
	public static final PluginPermission EXEMPT_ALL	= new PluginPermission("minebans.antispam.exempt", PermissionDefault.OP, "Players will this permission will be allowed to spam.");
	
}
