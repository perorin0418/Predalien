package org.perorin.predalien.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class ViewerTest extends JApplet {
	private JTextField textField;

	/**
	 * Create the applet.
	 */
	public ViewerTest() {

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setName("parent");
		getContentPane().add(panel, BorderLayout.CENTER);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "ほげほげ", "警告", JOptionPane.WARNING_MESSAGE);
			}
		});
		btnNewButton.setBounds(12, 10, 91, 21);
		btnNewButton.setName("btn");
		panel.add(btnNewButton);

		textField = new JTextField();
		textField.setBounds(120, 11, 96, 19);
		textField.setName("fld");
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(228, 14, 50, 13);
		lblNewLabel.setName("lbl");
		panel.add(lblNewLabel);

		JSpinner spinner = new JSpinner();
		spinner.setBounds(22, 41, 81, 20);
		spinner.setName("spin");
		panel.add(spinner);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(130, 41, 86, 19);
		comboBox.setName("combo");
		panel.add(comboBox);

		panel.add(new Predalien());

	}
}
