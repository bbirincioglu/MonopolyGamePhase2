package gui;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

public class ComponentBuilder {
	public static JLabel composeDefaultLabel(String text) {
		JLabel defaultLabel = new JLabel(text);
		defaultLabel.setFont(new Font("Sans serif", Font.BOLD, 12));
		defaultLabel.setHorizontalAlignment(JLabel.CENTER);
		return defaultLabel;
	}
	
	public static JTextField composeDefaultTextField(int width, int height, String text, ActionListener textFieldListener) {
		JTextField defaultTextField = new JTextField();
		defaultTextField.setText(text);
		defaultTextField.setFont(new Font("Sans serif", Font.BOLD, 12));
		
		if (width != 0 && height != 0) {
			defaultTextField.setPreferredSize(new Dimension(width, height));
		}
		
		if (textFieldListener != null) {
			defaultTextField.addActionListener(textFieldListener);
		}
		
		return defaultTextField;
	}
	
	public static JPanel composeDummyContainer(JComponent[] components, LayoutManager layout, Object[] constraints) {
		JPanel dummyContainer = new JPanel();
		
		if (layout != null) {
			dummyContainer.setLayout(layout);
		} else {
			dummyContainer.setLayout(new FlowLayout());
		}
		
		for (int i = 0; i < components.length; i++) {
			if (constraints != null) {
				dummyContainer.add(components[i], constraints[i]);
			} else {
				dummyContainer.add(components[i]);
			}
		}
		
		return dummyContainer;
	}
	
	public static JPanel composeDummyContainer(ArrayList<JComponent> components, LayoutManager layout, Object[] constraints) {
		JPanel dummyContainer = new JPanel();
		
		if (layout != null) {
			dummyContainer.setLayout(layout);
		} else {
			dummyContainer.setLayout(new FlowLayout());
		}
		
		for (int i = 0; i < components.size(); i++) {
			if (constraints != null) {
				dummyContainer.add(components.get(i), constraints[i]);
			} else {
				dummyContainer.add(components.get(i));
			}
		}
		
		return dummyContainer;
	}
	
	public static JButton composeDefaultButton(String text, int width, int height, ActionListener buttonListener, boolean isEnabled) {
		JButton defaultButton = new JButton(text);
		
		if (width != 0 && height != 0) {
			defaultButton.setPreferredSize(new Dimension(width, height));
		}
		
		defaultButton.setEnabled(isEnabled);
		defaultButton.addActionListener(buttonListener);
		return defaultButton;
	}
	
	public static JCheckBox composeDefaultCheckBox(String text, ActionListener checkBoxListener) {
		JCheckBox defaultCheckBox = new JCheckBox(text);
		defaultCheckBox.addActionListener(checkBoxListener);
		return defaultCheckBox;
	}
	
	public static JComboBox composeDefaultComboBox(int width, int height, String[] options, String selectedItem, ItemListener comboBoxListener) {
		JComboBox defaultComboBox = new JComboBox(options);
		
		if (selectedItem != null && !selectedItem.equals("")) {
			defaultComboBox.setSelectedItem(selectedItem);
		}
		
		defaultComboBox.setPreferredSize(new Dimension(width, height));
		
		if (comboBoxListener != null) {
			defaultComboBox.addItemListener(comboBoxListener);
		}
		
		return defaultComboBox;
	}
	
	public static SteppedComboBox composeDefaultSteppedComboBox(int width, int height, int popUpWidth, String[] options, String selectedItem, ItemListener comboBoxListener) {
		SteppedComboBox defaultSteppedComboBox = new SteppedComboBox(options);
		
		if (selectedItem != null && !selectedItem.equals("")) {
			defaultSteppedComboBox.setSelectedItem(selectedItem);
		}
		
		defaultSteppedComboBox.setPopupWidth(popUpWidth);
		defaultSteppedComboBox.setPreferredSize(new Dimension(width, height));
		
		if (comboBoxListener != null) {
			defaultSteppedComboBox.addItemListener(comboBoxListener);
		}
		
		return defaultSteppedComboBox;
	}
	
	public static JScrollBar composeScrollBar(int orientation, int width, int height, int maximum, int minimum, int unitIncrement, AdjustmentListener scrollBarListener) {
		JScrollBar defaultScrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
		defaultScrollBar.setPreferredSize(new Dimension(width, height));
		defaultScrollBar.setMaximum(maximum);
		defaultScrollBar.setMinimum(minimum);
		defaultScrollBar.setUnitIncrement(unitIncrement);
		defaultScrollBar.addAdjustmentListener(scrollBarListener);
		return defaultScrollBar;
	}
	
	public static JDialog composeDefaultDialog(JPanel contentPanel) {
		JDialog dialog = new JDialog();
		dialog.setResizable(false);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setContentPane(contentPanel);
		dialog.pack();
		return dialog;
	}
}