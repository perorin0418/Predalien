package org.net.perorin.predalien.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;

public class PredalienKeyCode extends JDialog {
	private JTable table;
	private DefaultTableModel model;
	public static Map<String, String> map = new LinkedHashMap<String, String>() {
		{
			put("英数字", "");
			put("48", "0");
			put("49", "1");
			put("50", "2");
			put("51", "3");
			put("52", "4");
			put("53", "5");
			put("54", "6");
			put("55", "7");
			put("56", "8");
			put("57", "9");
			put("65", "A");
			put("66", "B");
			put("67", "C");
			put("68", "D");
			put("69", "E");
			put("70", "F");
			put("71", "G");
			put("72", "H");
			put("73", "I");
			put("74", "J");
			put("75", "K");
			put("76", "L");
			put("77", "M");
			put("78", "N");
			put("79", "O");
			put("80", "P");
			put("81", "Q");
			put("82", "R");
			put("83", "S");
			put("84", "T");
			put("85", "U");
			put("86", "V");
			put("87", "W");
			put("88", "X");
			put("89", "Y");
			put("90", "Z");
			put("ファンクション", "");
			put("112", "F1");
			put("113", "F2");
			put("114", "F3");
			put("115", "F4");
			put("116", "F5");
			put("117", "F6");
			put("118", "F7");
			put("119", "F8");
			put("120", "F9");
			put("121", "F10");
			put("122", "F11");
			put("123", "F12");
			put("テンキー", "");
			put("96", "0");
			put("97", "1");
			put("98", "2");
			put("99", "3");
			put("100", "4");
			put("101", "5");
			put("102", "6");
			put("103", "7");
			put("104", "8");
			put("105", "9");
			put("106", "*");
			put("107", "+");
			put("109", "-");
			put("110", ".");
			put("111", "/");
			put("特殊", "");
			put("8", "BackSpace");
			put("9", "Tab");
			put("13", "Enter");
			put("16", "Shift");
			put("17", "Ctrl");
			put("18", "Alt");
			put("19", "PauseBreak");
			put("20", "CapsLock");
			put("27", "Esc");
			put("32", "Space");
			put("33", "PageUp");
			put("34", "PageDown");
			put("35", "End");
			put("36", "Home");
			put("37", "←");
			put("38", "↑");
			put("39", "→");
			put("40", "↓");
			put("45", "Insert");
			put("46", "Delete");
			put("144", "NumLock");
			put("145", "ScrollLock");
			put("186", "*、:");
			put("187", "+、;");
			put("188", "<、,");
			put("189", "=、-");
			put("190", ">、.");
			put("191", "?、/");
			put("192", "`、@");
			put("219", "{、[");
			put("220", "|、\\");
			put("221", "}、]");
			put("222", "~、^");
			put("226", "_、\\");
			put("91", "Windows");
			put("92", "Windows");
			put("93", "メニュー");

		}
	};

	public PredalienKeyCode(Point p) {
		setBounds(p.x, p.y, 200, 439);
		setTitle("キーコード一覧");
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.DARK_GRAY);
		scrollPane.setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.BLACK));
		scrollPane.getVerticalScrollBar().setUI(myScroolBar);
		scrollPane.getViewport().setBackground(Color.DARK_GRAY);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		String[] column_header = { "キー", "キーコード" };
		String[][] column_init = { { "", "" } };
		model = new DefaultTableModel(column_init, column_header);
		model.setRowCount(0);
		for (String key : map.keySet()) {
			model.addRow(new Object[] { map.get(key), key });
		}

		table = new JTable(model);
		table.setBackground(Color.DARK_GRAY);
		table.setForeground(new Color(0, 200, 0));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getColumn(column_header[0]).setPreferredWidth(80);
		table.getColumn(column_header[1]).setPreferredWidth(100);
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setBackground(Color.DARK_GRAY);
		table.getTableHeader().setForeground(new Color(0, 200, 0));
		table.getTableHeader().setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.DARK_GRAY));
		table.getTableHeader().setFont(new Font("ＭＳＰ ゴシック", Font.BOLD, 12));
		table.getTableHeader().setPreferredSize(new Dimension(100, 20));
		scrollPane.setViewportView(table);
	}

	private BasicScrollBarUI myScroolBar = new BasicScrollBarUI() {
		private final Color defaultColor = Color.DARK_GRAY;
		private final Color draggingColor = Color.LIGHT_GRAY;
		private final Color rolloverColor = Color.LIGHT_GRAY;
		private final Dimension d = new Dimension();

		@Override
		protected JButton createDecreaseButton(int orientation) {
			return new JButton() {
				@Override
				public Dimension getPreferredSize() {
					return d;
				}
			};
		}

		@Override
		protected JButton createIncreaseButton(int orientation) {
			return new JButton() {
				@Override
				public Dimension getPreferredSize() {
					return d;
				}
			};
		}

		@Override
		protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
			trackColor = Color.GRAY;
			super.paintTrack(g, c, trackBounds);
		}

		@Override
		protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
			Color color;
			JScrollBar sb = (JScrollBar) c;
			if (!sb.isEnabled() || r.width > r.height) {
				return;
			} else if (isDragging) {
				color = draggingColor;
			} else if (isThumbRollover()) {
				color = rolloverColor;
			} else {
				color = defaultColor;
			}
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setPaint(color);
			g2.fillRect(r.x, r.y, r.width - 1, r.height - 1);
			g2.setPaint(Color.BLACK);
			g2.drawRect(r.x, r.y, r.width - 1, r.height - 1);
			g2.dispose();
		}

		@Override
		protected void setThumbBounds(int x, int y, int width, int height) {
			super.setThumbBounds(x, y, width, height);
			scrollbar.repaint();
		}
	};

}
