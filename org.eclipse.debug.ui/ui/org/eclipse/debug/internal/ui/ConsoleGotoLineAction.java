package org.eclipse.debug.internal.ui;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;

public class ConsoleGotoLineAction extends ConsoleViewerAction {

	/**
	 * Validates whether the text found in the input field of the
	 * dialog forms a valid line number, i.e. one to which can be 
	 * jumped.
	 */
	class NumberValidator implements IInputValidator {

		public String isValid(String input) {
			try {
				int i= Integer.parseInt(input);
				if (i <= 0 || fLastLine < i)
					return DebugUIMessages.getString("ConsoleGotoLineAction.Line_number_out_of_range_1"); //$NON-NLS-1$

			} catch (NumberFormatException x) {
				return DebugUIMessages.getString("ConsoleGotoLineAction.Not_a_number_2"); //$NON-NLS-1$
			}

			return ""; //$NON-NLS-1$
		}
	};

	protected int fLastLine;
	protected ConsoleViewer fConsoleViewer;
	
	/**
	 * Constructs a goto line action for the console using the provided resource bundle
	 */
	public ConsoleGotoLineAction(ConsoleViewer viewer) {
		super(viewer, -1);
		fConsoleViewer= viewer;
		setText(DebugUIMessages.getString("ConsoleGotoLineAction.Go_to_&Line...@Ctrl+L_4")); //$NON-NLS-1$
		setToolTipText(DebugUIMessages.getString("ConsoleGotoLineAction.Go_to_Line_5")); //$NON-NLS-1$
		setDescription(DebugUIMessages.getString("ConsoleGotoLineAction.Go_to_Line_6"));		 //$NON-NLS-1$
	}
	
	/**
	 * @see ConsoleViewerAction#update()
	 */
	public void update() {
	}

	/**
	 * Jumps to the line.
	 */
	protected void gotoLine(int line) {

		IDocument document= fConsoleViewer.getDocument();
		try {
			int start= document.getLineOffset(line);
			int length= document.getLineLength(line);
			fConsoleViewer.getTextWidget().setSelection(start, start + length);
			fConsoleViewer.revealRange(start, length);
		} catch (BadLocationException x) {
			DebugUIPlugin.logError(x);
		}
	}

	/**
	 * @see Action#run()
	 */
	public void run() {
		try {
			Point selection= fConsoleViewer.getTextWidget().getSelection();
			IDocument document= fConsoleViewer.getDocument();
			fLastLine= document.getLineOfOffset(document.getLength()) + 1;
			int startLine= selection == null ? 1 : fConsoleViewer.getTextWidget().getLineAtOffset(selection.x) + 1;
			String title= DebugUIMessages.getString("ConsoleGotoLineAction.Go_to_Line_7"); //$NON-NLS-1$
			String message= DebugUIMessages.getString("ConsoleGotoLineAction.Enter_line_number__8"); //$NON-NLS-1$
			String value= Integer.toString(startLine);
			Shell activeShell= DebugUIPlugin.getActiveWorkbenchWindow().getShell();
			InputDialog d= new InputDialog(activeShell, title, message, value, new NumberValidator());
			d.open();

			try {
				int line= Integer.parseInt(d.getValue());
				gotoLine(line - 1);
			} catch (NumberFormatException x) {
				DebugUIPlugin.logError(x);
			}
		} catch (BadLocationException x) {
			DebugUIPlugin.logError(x);
			return;
		}
	}
}

