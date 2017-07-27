package window.startup;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.EventObject;

import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import game.GameServer;
import game.ServerProperties;
import game.ServerPropertiesIO;

public class PropertiesPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private ServerProperties properties;
	
	public PropertiesPanel(GameServer server) {
		this.properties = server.getProprties();
		setLayout(new BorderLayout(0, 0));
		
		Component verticalStrut_1 = Box.createVerticalStrut(5);
		add(verticalStrut_1, BorderLayout.SOUTH);
		
		Component horizontalStrut = Box.createHorizontalStrut(5);
		add(horizontalStrut, BorderLayout.WEST);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		add(horizontalStrut_1, BorderLayout.EAST);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable() {
			private static final long serialVersionUID = -7010919770837329686L;

			public TableCellRenderer getCellRenderer(int row, int column) {
	        	Object value = getValueAt(row, column);
	        	
	            if(value instanceof Number)
	                return getNumberRender((Number) value);
	            return super.getDefaultRenderer(value.getClass());
	        }

	        public TableCellEditor getCellEditor(int row, int column) {
	        	if(column == 1) return new DefaultCellEditor(new JTextField()) {
					private static final long serialVersionUID = 1L;
					public boolean isCellEditable(EventObject e) { return false; }
	        		public boolean shouldSelectCell(EventObject e) { return false; }
	        	};
	        	
	        	Object value = getValueAt(row, column);

	        	if(value instanceof Number)
	                return getNumberEditor((Number) value);
	            return super.getDefaultEditor(value.getClass());
	        }
	        
	        public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
	            Component component = super.prepareRenderer(renderer, row, col);
	            component.setEnabled(col != 1);
	            return component;
	        }
		};
		
		table.setFillsViewportHeight(true);
		table.setRowHeight(table.getRowHeight() + 10);
		table.setRowMargin(5);
		
		scrollPane.setViewportView(table);
		table.setBorder(new EmptyBorder(2, 2, 2, 2));
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		table.setModel(makeModel());
		
		Component verticalStrut = Box.createVerticalStrut(5);
		add(verticalStrut, BorderLayout.NORTH);

		table.getModel().addTableModelListener(e -> {
			int property = e.getFirstRow();
			try {
				Object tableValue = table.getModel().getValueAt(property, 2);
				Method setter = ServerPropertiesIO.getSetter(ServerProperties.class.getDeclaredFields()[property]);
				Class<?> parmClass = Class.forName(ServerPropertiesIO.getClassType(setter.getParameters()[0].getType()));
				Constructor<?> objectConstructor = parmClass.getConstructor(String.class);
				setter.invoke(properties, objectConstructor.newInstance(tableValue + ""));
				
				server.propertiesUpdated();
			} catch(Exception e1) { e1.printStackTrace(); }
		});
	}
	
	private JFormattedTextField getTextField(Number value) {
		boolean decimals = value instanceof Integer || value instanceof Long;
		
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(!decimals ? 3 : 0);
		format.setMinimumFractionDigits(!decimals ? 2 : 0);
		
		JFormattedTextField numberField = new JFormattedTextField(format);
		numberField.setHorizontalAlignment(JTextField.CENTER);
		numberField.setValue(value);
		
		try { numberField.commitEdit(); } catch(ParseException e) {}
		return numberField;
	}
	
	private TableCellRenderer getNumberRender(Number value) {
		return (t, v, s, f, r, c) -> getTextField(value);
	}
	
	private TableCellEditor getNumberEditor(Number value) {
		JFormattedTextField numberField = getTextField(value);
		
		return new DefaultCellEditor(numberField) {
			private static final long serialVersionUID = 1L;

			public Object getCellEditorValue() {
				JFormattedTextField textField = (JFormattedTextField) getComponent();
				try { textField.commitEdit(); } catch(ParseException e) {}
				
				return textField.getValue();
			}
		}; 
	}
	
	public TableModel makeModel() {
		Field[] fields = ServerProperties.class.getDeclaredFields();
		Object[][] data = new Object[fields.length][3];
		
		try {
			int index = 0;
			for(Field field : fields) {
				Object value = ServerPropertiesIO.getGetter(field).invoke(properties);
				
				data[index][0] = field.getName();
				data[index][1] = value;
				data[index][2] = value;
				
				index ++;
			}
		} catch(IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return new DefaultTableModel(data,
			new String[] {
				"Property", "Defualt Vaule", "Vaule"
			}
		);
	}
}
