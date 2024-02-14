package com.yoochul.restaurantnote;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.yoochul.restaurantnote.action.AboutAction;
import com.yoochul.restaurantnote.action.ExitAction;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	
	private ExitAction exitAction;
	private AboutAction helpAction;

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
		exitAction = new ExitAction(window);
		register(exitAction);
		helpAction = new AboutAction(window);
		register(helpAction);
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenu = new MenuManager("&파일", "파일");
		fileMenu.add(exitAction);
		
		MenuManager helpMenu = new MenuManager("&도움말", "도움말");
		helpMenu.add(helpAction);
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
	}
    
}
