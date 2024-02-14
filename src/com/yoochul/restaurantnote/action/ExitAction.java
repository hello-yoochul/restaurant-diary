package com.yoochul.restaurantnote.action;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;

import com.yoochul.restaurantnote.dialog.ExitDialog;

public class ExitAction extends Action {
	private final static String ID = "com.yoochul.restaurantnote.action.exitaction";
	
	private final IWorkbenchWindow window;

	public ExitAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setText("종료");
	}

	@Override
	public void run() {
		new ExitDialog(Display.getCurrent().getActiveShell()).open();
	}
}
