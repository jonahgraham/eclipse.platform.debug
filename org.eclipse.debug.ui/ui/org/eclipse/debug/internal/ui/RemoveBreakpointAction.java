package org.eclipse.debug.internal.ui;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */

import java.util.Iterator;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.IDebugStatusConstants;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.SelectionProviderAction;
import org.eclipse.ui.help.WorkbenchHelp;

public class RemoveBreakpointAction extends SelectionProviderAction {

	public RemoveBreakpointAction(ISelectionProvider provider) {
		super(provider, DebugUIMessages.getString("RemoveBreakpointAction.&Remove_1")); //$NON-NLS-1$
		setEnabled(!getStructuredSelection().isEmpty());
		setToolTipText(DebugUIMessages.getString("RemoveBreakpointAction.Remove_Selected_Breakpoints_2")); //$NON-NLS-1$
		setHoverImageDescriptor(DebugPluginImages.getImageDescriptor(IDebugUIConstants.IMG_LCL_REMOVE));
		setDisabledImageDescriptor(DebugPluginImages.getImageDescriptor(IInternalDebugUIConstants.IMG_DLCL_REMOVE));
		setImageDescriptor(DebugPluginImages.getImageDescriptor(IInternalDebugUIConstants.IMG_ELCL_REMOVE));
		WorkbenchHelp.setHelp(
			this,
			new Object[] { IDebugHelpContextIds.REMOVE_ACTION });
	}

	/**
	 * @see IAction
	 */
	public void run() {
		IStructuredSelection selection= getStructuredSelection();
		if (selection.isEmpty()) {
			return;
		}
		IStructuredSelection es= (IStructuredSelection)selection;
		final Iterator itr= es.iterator();
		final MultiStatus ms = new MultiStatus(DebugUIPlugin.getDefault().getDescriptor().getUniqueIdentifier(),
			IDebugStatusConstants.REQUEST_FAILED, DebugUIMessages.getString("RemoveBreakpointAction.Breakpoint(s)_removal_failed_3"), null); //$NON-NLS-1$
 
		IWorkspaceRunnable runnable= new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) {
				while (itr.hasNext()) {
					try {
						IBreakpointManager breakpointManager = DebugPlugin.getDefault().getBreakpointManager();
						IBreakpoint breakpoint= (IBreakpoint)itr.next();
						breakpointManager.removeBreakpoint(breakpoint, true);
					} catch (CoreException ce) {
						ms.merge(ce.getStatus());
					}
				}
			}
		};
		try {
			ResourcesPlugin.getWorkspace().run(runnable, null);
		} catch (CoreException ce) {
			ms.merge(ce.getStatus());
		}
		if (!ms.isOK()) {
			DebugUIPlugin.errorDialog(DebugUIPlugin.getActiveWorkbenchWindow().getShell(), DebugUIMessages.getString("RemoveBreakpointAction.Removing_a_breakpoint_4"),DebugUIMessages.getString("RemoveBreakpointAction.Exceptions_occurred_attempting_to_remove_a_breakpoint._5") , ms); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * @see SelectionProviderAction
	 */
	public void selectionChanged(IStructuredSelection sel) {
		setEnabled(!sel.isEmpty());
	}
}

