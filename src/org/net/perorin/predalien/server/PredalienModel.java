package org.net.perorin.predalien.server;

import javax.swing.table.DefaultTableModel;

public class PredalienModel extends DefaultTableModel {

	public static final String[] COLUMN_HEADER = { "ターゲット", "名前", "クラス名", "マウス情報", "相対マウス位置", "絶対マウス位置", "キー情報", "キーコード", "キー修飾", "待機時間" };
	private static final String[][] COLUMN_INIT = { { "", "", "", "", "", "", "", "", "", "" } };

	public PredalienModel() {
		super(COLUMN_INIT, COLUMN_HEADER);
		this.setRowCount(0);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

}
