package window.startup;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.EventObject;
import java.util.Hashtable;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

public class RowEditor implements TableCellEditor {
	
	private Hashtable<Integer, TableCellEditor> editors;
	private TableCellEditor editor, defaultEditor;
	private JTable table;
	
	public RowEditor(JTable table) {
		this.table = table;
		editors = new Hashtable<>();
		defaultEditor = new DefaultCellEditor(new JTextField());
	}
	
	public void addEditor(int row, Object value) {
		if(value instanceof Boolean) {
			JCheckBox checkBox = new JCheckBox();
			checkBox.setSelected((Boolean) value);
			
			addCompListener(checkBox);
			setEditorAt(row, new DefaultCellEditor(checkBox));
			return;
		}
		
		if(value instanceof String) {
			JTextField textField = new JTextField();
			textField.setText((String) value);

			addCompListener(textField);
			setEditorAt(row, new DefaultCellEditor(textField));
			return;
		}
		
		if(value instanceof Number) {
			NumberFormat format = NumberFormat.getNumberInstance();
			format.setMaximumFractionDigits(3);
			
			JFormattedTextField numberField = new JFormattedTextField(format);
			numberField.setValue((Number) value);
			
			addCompListener(numberField);
			setEditorAt(row, new DefaultCellEditor(numberField));
			return;
		}
	}
	
	private JComponent addCompListener(JComponent component) {
		component.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				final JComponent c = (JComponent) e.getSource();
				
				SwingUtilities.invokeLater(() -> {
					c.requestFocus();
					System.out.println(c);
					
					if(c instanceof JComboBox) {
						System.out.println("a");
					}
				});
			}
		});
		
		return component;
	}
	
	public void setEditorAt(int row, TableCellEditor editor) { 
		editors.put(row, editor);
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return editor.getTableCellEditorComponent(table, value, isSelected, row, column);
	}
	
	public Object getCellEditorValue() { return editor.getCellEditorValue(); }
	public boolean stopCellEditing() { return editor.stopCellEditing(); }
	public void cancelCellEditing() { editor.cancelCellEditing();  }
	
	public boolean isCellEditable(EventObject anEvent) {
		selectEditor((MouseEvent) anEvent);
		return editor.isCellEditable(anEvent);
	}
	
	public void addCellEditorListener(CellEditorListener l) { editor.addCellEditorListener(l); }
	public void removeCellEditorListener(CellEditorListener l) { editor.removeCellEditorListener(l); }
	
	public boolean shouldSelectCell(EventObject anEvent) {
		selectEditor((MouseEvent) anEvent);
		return editor.shouldSelectCell(anEvent);
	}
	
	protected void selectEditor(MouseEvent e) {
		int row = 0;
		if(e == null) row = table.getSelectionModel().getAnchorSelectionIndex();
		else row = table.rowAtPoint(e.getPoint());
		
		editor = (TableCellEditor) editors.get(row);
		if(editor == null) {
			editor = defaultEditor;
		}
	}
}
