package org.eclipse.debug.internal.ui;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.ISuspendResume;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;

public class SuspendActionDelegate extends ControlActionDelegate {

	/**
	 * @see ControlActionDelegate
	 */
	protected void doAction(Object element) throws DebugException {
		if (element instanceof ISuspendResume) {
			 ((ISuspendResume) element).suspend();
		}
	}
	
	/**
	 * @see ControlActionDelegate
	 */
	public boolean isEnabledFor(Object element) {
		return element instanceof ISuspendResume && ((ISuspendResume)element).canSuspend();
	}

	/**
	 * @see ControlActionDelegate
	 */
	public boolean getEnableStateForSelection(IStructuredSelection selection) {	 		
		if (selection.size() == 1) {
			return isEnabledFor(selection.getFirstElement());
		} else {
			 return false;
		}
	}
	
	protected String getHelpContextId() {
		return IDebugHelpContextIds.SUSPEND_ACTION;
	}

	/**
	 * @see ControlActionDelegate
	 */
	protected void setActionImages(IAction action) {		
		action.setHoverImageDescriptor(DebugPluginImages.getImageDescriptor(IDebugUIConstants.IMG_LCL_SUSPEND));
		action.setDisabledImageDescriptor(DebugPluginImages.getImageDescriptor(IInternalDebugUIConstants.IMG_DLCL_SUSPEND));
		action.setImageDescriptor(DebugPluginImages.getImageDescriptor(IInternalDebugUIConstants.IMG_ELCL_SUSPEND));
	}
	/*
	 * @see ControlActionDelegate#getStatusMessage()
	 */
	protected String getStatusMessage() {
		return DebugUIMessages.getString("SuspendActionDelegate.Suspend_failed_1"); //$NON-NLS-1$
	}

	/*
	 * @see ControlActionDelegate#getErrorDialogMessage()
	 */
	protected String getErrorDialogMessage() {
		return DebugUIMessages.getString("SuspendActionDelegate.Exceptions_occurred_attempting_to_suspend._2"); //$NON-NLS-1$
	}

	/*
	 * @see ControlActionDelegate#getErrorDialogTitle()
	 */
	protected String getErrorDialogTitle() {
		return getToolTipText();
	}
	/*
	 * @see ControlActionDelegate#getToolTipText()
	 */
	protected String getToolTipText() {
		return DebugUIMessages.getString("SuspendActionDelegate.Suspend_3"); //$NON-NLS-1$
	}

	/*
	 * @see ControlActionDelegate#getText()
	 */
	protected String getText() {
		return DebugUIMessages.getString("SuspendActionDelegate.&Suspend_4"); //$NON-NLS-1$
	}
}