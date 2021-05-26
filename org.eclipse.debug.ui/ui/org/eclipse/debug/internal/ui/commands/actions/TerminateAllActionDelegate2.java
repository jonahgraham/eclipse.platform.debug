/*******************************************************************************
 * Copyright (c) 2021 Kichwa Coders Canada Inc. and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.debug.internal.ui.commands.actions;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchesListener2;
import org.eclipse.debug.core.commands.ITerminateHandler;
import org.eclipse.debug.internal.ui.actions.AbstractRemoveAllActionDelegate;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;

public class TerminateAllActionDelegate2 extends AbstractRemoveAllActionDelegate implements ILaunchesListener2 {

	private DebugCommandService fUpdateService;

	@Override
	protected boolean isEnabled() {
		ILaunch[] launches = DebugPlugin.getDefault().getLaunchManager().getLaunches();
		if (launches != null) {
			for (ILaunch launch : launches) {
				if (!launch.isTerminated()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected void initialize() {
		throw new IllegalStateException("initialize should not used because we need a window to initialize."); //$NON-NLS-1$
	}

	@Override
	public void init(IViewPart part) {
		fUpdateService = DebugCommandService.getService(part.getSite().getWorkbenchWindow());
		update();
	}

	@Override
	public void init(IWorkbenchWindow window) {
		fUpdateService = DebugCommandService.getService(window);
		update();
	}

	@Override
	public void dispose() {
		super.dispose();
		DebugPlugin.getDefault().getLaunchManager().removeLaunchListener(this);
	}

	@Override
	public void launchesAdded(ILaunch[] launches) {
		update();
	}

	@Override
	public void launchesChanged(ILaunch[] launches) {
	}

	@Override
	public void launchesRemoved(ILaunch[] launches) {
		update();

	}

	@Override
	public void launchesTerminated(ILaunch[] launches) {
		update();
	}

	@Override
	public void run(IAction action) {
		ILaunch[] launches = DebugPlugin.getDefault().getLaunchManager().getLaunches();
		fUpdateService.executeCommand(ITerminateHandler.class, launches, request -> {
			// nothing to do after terminating
		});
	}

}
