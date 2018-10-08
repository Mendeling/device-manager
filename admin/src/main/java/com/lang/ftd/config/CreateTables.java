package com.lang.ftd.config;

import common.tools.dbtools.scaffold.ScaffoldGen;

public class CreateTables {
	public static void main(String[] args) {
		ScaffoldGen generator1 = new ScaffoldGen("com/lang/ftd/manager", "AdminOpera","菜单管理", "t_admin_opera");
		ScaffoldGen generator2 = new ScaffoldGen("com/lang/ftd/manager", "AdminRole","角色管理", "t_admin_role");
		ScaffoldGen generator3 = new ScaffoldGen("com/lang/ftd/manager", "AdminRoleOpera","菜单权限管理", "t_admin_role_opera");
		ScaffoldGen generator4 = new ScaffoldGen("com/lang/ftd/manager", "AdminUserRole","用户角色管理", "t_admin_user_role");
		generator1.execute();
		generator2.execute();
		generator3.execute();
		generator4.execute();

	}
}
