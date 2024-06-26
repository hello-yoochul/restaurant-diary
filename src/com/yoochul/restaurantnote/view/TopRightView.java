package com.yoochul.restaurantnote.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.yoochul.restaurantnote.model.Restaurant;
import com.yoochul.restaurantnote.util.ColorManager;
import com.yoochul.restaurantnote.util.StringUtil;

public class TopRightView extends ViewPart {
	public static final String ID = "com.yoochul.restaurantnote.view.toprightview";
	private final int IMAGE_NUMBER_PER_LINE = 3;
	private Composite parentComposite;
	private Composite topComposite;
	private ScrolledComposite bottomScrolledComposite;
	private CLabel topLabel;
    
	@Override
	public void createPartControl(Composite parent) {
		parentComposite = new Composite(parent, SWT.NONE);
		parentComposite.setLayout(new FillLayout(SWT.VERTICAL));
        createTop(parentComposite);
        createBottom(parentComposite);
        addListenerForTableSelection();
	}

	private void createTop(Composite parent) {
		topComposite = new Composite(parent, SWT.BORDER);
		topComposite.setLayout(new GridLayout()); // 수평 방향으로 채워지도록 설정
		topComposite.setBackground(ColorManager.WHITE);
		
		topLabel = new CLabel(topComposite, SWT.CENTER | SWT.UNDERLINE_SINGLE);
		topLabel.setText("                                                                                                           ");
		topLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		topLabel.setForeground(ColorManager.BLUE);

	    topLabel.addMouseListener(new MouseListener() {
	        @Override
	        public void mouseDoubleClick(MouseEvent e) {
	            Program.launch(topLabel.getText());
	        }

	        @Override
	        public void mouseDown(MouseEvent e) {
	        }

	        @Override
	        public void mouseUp(MouseEvent e) {
	        }
	    });
	}
	
	private void createBottom(Composite parent) {
		bottomScrolledComposite = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	    bottomScrolledComposite.setLayout(new FillLayout());
	    bottomScrolledComposite.setBackground(ColorManager.WHITE);
	}
	
	private void addListenerForTableSelection() {
		getViewSite().getPage().addSelectionListener(new ISelectionListener() {
            @Override
            public void selectionChanged(IWorkbenchPart part, ISelection selection) {
                if (part instanceof TopLeftView) {
                	if (selection.isEmpty()) { // 테이블에 selection이 없어질때 UI 리셋
                		resetUI();
                    } else {
                    	Object obj = ((IStructuredSelection) selection).getFirstElement();
                    	if (obj instanceof Restaurant) {
                    		Restaurant selected = (Restaurant) obj;
                    		setMapUrlOnTop(selected);
                    		setImage(selected.getPicturesLocation());
                    	}
                    }
                }
            }
        });
	}
	
	private void resetUI() {
		topLabel.setText("                                                                                                           ");
		bottomScrolledComposite.setContent(null);
	}
	
	
	private void setMapUrlOnTop(Restaurant restaurant) {
		if (restaurant == null) return;
		topLabel.setText(restaurant.getMapUrl());
	}
	
	private void setImage(String imagePath){
		//System.out.println("@@@@@@@@@@@@@@@@@@ imagePath: " + imagePath);
		if (StringUtil.isBlank(imagePath)) return;
		
		 // 내부 Composite 생성
	    Composite composite = new Composite(bottomScrolledComposite, SWT.NONE);
	    composite.setBackground(ColorManager.WHITE);
	    
	    // GridLayout 설정
	    GridLayout layout = new GridLayout(IMAGE_NUMBER_PER_LINE, true);
        layout.marginWidth = layout.marginHeight = 10;
	    composite.setLayout(layout);
	    
	    Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		URL url = FileLocator.find(bundle, new Path(imagePath), null); //$NON-NLS-1$
		URL fileUrl = null;
		
		try {
			fileUrl = FileLocator.toFileURL(url);
			File directory = new File(fileUrl.getPath());
			File[] files = directory.listFiles();
			
			if (files != null) {
				for (File file : files) {
					if(file.isFile() && isImageFile(file.getName())){
						Image originalImage = getImage(imagePath + file.getName());
						if(originalImage == null) return;
						
		                int scaledWidth = 170;
		                int scaledHeight = 140;
		                Image scaledImage = new Image(parentComposite.getDisplay(), originalImage.getImageData().scaledTo(scaledWidth, scaledHeight));
		                originalImage.dispose(); // 이미지 리소스 반환
		                
		                Label label = new Label(composite, SWT.NONE);
		                label.setImage(scaledImage);
		                label.setBackground(ColorManager.WHITE);
		                
		                GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		                gridData.widthHint = 150; // 이미지의 폭
		                gridData.heightHint = 140; // 이미지의 높이
		                label.setLayoutData(gridData);
					}
				}
			}
			
		} catch (IOException e) {
			System.out.println("파일 위치를 찾을 수 없습니다.");
			e.printStackTrace();
		}
		
		/*System.out.println("!@#!@$#@$#@$ url.getPath():   " + url.getPath());	    

	    File directory = new File(fileUrl.getPath());

		final File[] files = directory.listFiles();

	    if (files != null) {
	        for (File file : files) {
	        	System.out.println("file: " + file.getName());
	            if (file.isFile() && isImageFile(file.getName())) {
	                // 이미지 로드
	            	Image originalImage = getImage(imagePath + file.getName());
	            	
//	                Image originalImage = new Image(parentComposite.getDisplay(), file.getAbsolutePath());
//	            	Image originalImage = ImageDescriptor.createFromURL(fileUrl)createImage();
	                
//	                // TODO: 이미지 크기 조정 composite 사이즈에 맞게 조정, resize할때 조정되게 
	                int scaledWidth = 150;
	                int scaledHeight = 130;
	                Image scaledImage = new Image(parentComposite.getDisplay(), originalImage.getImageData().scaledTo(scaledWidth, scaledHeight));
	                originalImage.dispose(); // 이미지 리소스 반환
	                
	                // 이미지를 보여주는 Label 생성
	                Label label = new Label(composite, SWT.NONE);
	                label.setImage(scaledImage);
	                
	                // Label의 GridData 설정
	                GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
	                gridData.widthHint = scaledWidth;
	                gridData.heightHint = scaledHeight;
	                label.setLayoutData(gridData);
	            }
	        }
	    }*/
	    bottomScrolledComposite.setContent(composite);
	    
	    // 스크롤 기능 활성화
	    bottomScrolledComposite.setExpandHorizontal(true);
	    bottomScrolledComposite.setExpandVertical(true);
	    bottomScrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	
	private Image getImage(String imagePath){
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		URL url = FileLocator.find(bundle, new Path(imagePath), null); //$NON-NLS-1$
		try {
			URL fileUrl = FileLocator.toFileURL(url);
			ImageDescriptor descriptor = ImageDescriptor.createFromURL(fileUrl);
			return descriptor.createImage();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static boolean isImageFile(String fileName) {
		String[] imageExtensions = { ".png", ".jpg", ".jpeg", ".gif", ".bmp" };
		for (String extension : imageExtensions) {
			if (fileName.toLowerCase().endsWith(extension)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setFocus() {
	}
}
