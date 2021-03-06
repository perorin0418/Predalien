package org.net.perorin.predalien.test;

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

import org.net.perorin.predalien.slave.Predalien;

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

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(93, 127, 262, 112);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(28, 20, 206, 82);
		panel_1.add(panel_2);

		JButton btnNewButton_1 = new JButton("New button");
		panel_2.add(btnNewButton_1);

		panel.add(new Predalien());

	}
}
