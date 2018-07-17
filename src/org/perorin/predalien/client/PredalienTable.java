package org.perorin.predalien.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;

public class PredalienTable extends JTable {

	public PredalienTable(PredalienModel model) {
		super(model);
		this.setBackground(Color.DARK_GRAY);
		this.setForeground(new Color(0, 200, 0));
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.getColumn(PredalienModel.COLUMN_HEADER[0]).setPreferredWidth(110);
		this.getColumn(PredalienModel.COLUMN_HEADER[1]).setPreferredWidth(80);
		this.getColumn(PredalienModel.COLUMN_HEADER[2]).setPreferredWidth(110);
		this.getColumn(PredalienModel.COLUMN_HEADER[3]).setPreferredWidth(110);
		this.getColumn(PredalienModel.COLUMN_HEADER[4]).setPreferredWidth(150);
		this.getColumn(PredalienModel.COLUMN_HEADER[5]).setPreferredWidth(150);
		this.getColumn(PredalienModel.COLUMN_HEADER[6]).setPreferredWidth(80);
		this.getColumn(PredalienModel.COLUMN_HEADER[7]).setPreferredWidth(100);
		this.getColumn(PredalienModel.COLUMN_HEADER[8]).setPreferredWidth(80);
		this.getColumn(PredalienModel.COLUMN_HEADER[9]).setPreferredWidth(80);
		this.getTableHeader().setBackground(Color.DARK_GRAY);
		this.getTableHeader().setForeground(new Color(0, 200, 0));
		this.getTableHeader().setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.DARK_GRAY));
		this.getTableHeader().setFont(new Font("ＭＳＰ ゴシック", Font.BOLD, 12));
		this.getTableHeader().setPreferredSize(new Dimension(100, 20));
	}

}
