package com.yoochul.restaurantnote.composite;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.yoochul.restaurantnote.db.DB;
import com.yoochul.restaurantnote.model.Restaurant;

public class RestaurantTableViewer extends Composite {
	private final DB database = DB.getInstance();
	
	private static final String[] columnNames = { "No.", "Name", "Type", "Address", "Note" };
	
	private static final int[] columnWidths = { 50, 100, 100, 200, 400 };
	
	private TableViewer tableViewer;
	
	public RestaurantTableViewer(Composite parent, int style) {
		super(parent, style);
		fillComposite(parent);
	}

	private void fillComposite(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
        Table table = tableViewer.getTable();
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        createTableColumns(table);     
        
        tableViewer.setContentProvider(new RestaurantTableContentProvider());
        tableViewer.setLabelProvider(new RestaurantTableLabelProvider());
        tableViewer.setInput(database.getData());
        
        tableViewer.setComparator(new ViewerComparator() {
            public int compare(Viewer viewer, Object e1, Object e2) {
            	Restaurant res1 = (Restaurant) e1;
                Restaurant res2 = (Restaurant) e2;
                Long id1 = res1.getId();
                Long id2 = res2.getId();
                return id1.compareTo(id2);
            };
        });
	}

	private void createTableColumns(Table table) {
		for (int i = 0; i < columnNames.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(columnNames[i]);
			column.setWidth(columnWidths[i]);
		}
	}
}
