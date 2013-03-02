/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.workflow.service.permission;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portlet.workflow.model.WorkflowInstance;
import com.liferay.portlet.workflow.model.WorkflowTask;

/**
 * <a href="WorkflowTaskPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WorkflowTaskPermission {

	public static void check(
			PermissionChecker permissionChecker, long taskId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, taskId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, WorkflowTask task,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, task, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long taskId, String actionId)
		throws PortalException, SystemException {

		return contains(permissionChecker, taskId, 0, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long taskId, long instanceId,
			String actionId)
		throws PortalException, SystemException {

		if (permissionChecker.hasPermission(
				0, WorkflowTask.class.getName(), taskId, actionId)) {

			return true;
		}
		else if (permissionChecker.hasPermission(
					0, WorkflowInstance.class.getName(), instanceId,
					actionId)) {

			return true;
		}

		return false;
	}

	public static boolean contains(
			PermissionChecker permissionChecker, WorkflowTask task,
			String actionId)
		throws PortalException, SystemException {

		WorkflowInstance instance = task.getInstance();

		return contains(
			permissionChecker, task.getTaskId(), instance.getInstanceId(),
			actionId);
	}

}