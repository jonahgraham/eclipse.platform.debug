package org.eclipse.debug.internal.ui;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.SelectionProviderAction;

public abstract class InspectorAction extends SelectionProviderAction {

	public InspectorAction(ISelectionProvider provider, String label) {
		super(provider, label);
	}

	/**
	 * @see IAction
	 */
	public void run() {
		// get the Inspector
		IWorkbenchPage p= DebugUIPlugin.getActiveWorkbenchWindow().getActivePage();
		if (p == null) {
			return;
		}
		InspectorView view= (InspectorView) p.findView(IDebugUIConstants.ID_INSPECTOR_VIEW);
		if (view == null) {
			// open a new view
			try {
				view= (InspectorView) p.showView(IDebugUIConstants.ID_INSPECTOR_VIEW);
			} catch (PartInitException e) {
				DebugUIPlugin.logError(e);
				return;
			}
		}
	
		try {
			doAction(view);
		} catch (DebugException de) {
			DebugUIPlugin.logError(de);
		}
	}

	/**
	 * @see SelectionProviderAction
	 */
	public void selectionChanged(IStructuredSelection sel) {
		setEnabled(!sel.isEmpty());
	}

	protected abstract void doAction(InspectorView view) throws DebugException;
}

