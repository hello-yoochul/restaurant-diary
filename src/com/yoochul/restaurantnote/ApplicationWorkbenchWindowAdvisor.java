package com.yoochul.restaurantnote;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {
	private static final int WINDOW_WIDTH = 1400;
	private static final int WINDOW_HEIGHT = 900;

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        // TODO: 앱 실행시 창 중앙에 배치하기 
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(WINDOW_WIDTH, WINDOW_HEIGHT));
//        configurer.setInitialSize(new Point(Display.getDefault().getBounds().width, Display.getDefault().getBounds().height));
        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(false);
    }
}
