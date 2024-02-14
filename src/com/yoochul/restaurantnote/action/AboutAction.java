package com.yoochul.restaurantnote.action;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;

import com.yoochul.restaurantnote.dialog.AboutDialog;

public class AboutAction extends Action {
	private final static String ID = "com.yoochul.restaurantnote.action.aboutaction";
	
	private final IWorkbenchWindow window;

	public AboutAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setText("이 프로그램에 대하여");
	}

	@Override
	public void run() {
		new AboutDialog(Display.getCurrent().getActiveShell()).open();
	}

}
