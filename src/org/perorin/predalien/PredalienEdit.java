package org.perorin.predalien;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.metal.MetalToggleButtonUI;

public class PredalienEdit extends JDialog {

	private JPanel pnlMouse;
	private JPanel pnlKey;
	private JTextField fldName;
	private JTextField fldClassName;
	private JRadioButton rdoMouse;
	private JRadioButton rdoKey;
	private JTextField fldDelay;
	private JRadioButton rdoMouseInfoLeft;
	private JRadioButton rdoMouseInfoCenter;
	private JRadioButton rdoMouseInfoRight;
	private JCheckBox chkMouseInfoShift;
	private JCheckBox chkMouseInfoCtrl;
	private JCheckBox chkMouseInfoAlt;
	private JTextField fldRelaMousePosX;
	private JTextField fldRelaMousePosY;
	private JTextField fldAbsMousePosX;
	private JTextField fldAbsMousePosY;
	private JTextField fldKeyInfo;
	private JTextField fldKeyCode;
	private JCheckBox chkKeyModifiersShift;
	private JCheckBox chkKeyModifiersCtrl;
	private JCheckBox chkKeyModifiersAlt;

	public static void main(String[] args) {
		try {
			//			PredalienEdit dialog = new PredalienEdit(new Point(100, 100));
			PredalienEdit dialog = new PredalienEdit();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PredalienEdit() {
		setBounds(100, 100, 382, 396);
		setTitle("[その他] Predalien Edit");
		initDialog();
		initCommon();
		initMouse();
		initKey();
		pnlMouse.setVisible(true);
		pnlKey.setVisible(false);
	}

	public PredalienEdit(Point parentPosition) {
		super();
		setBounds(parentPosition.x, parentPosition.y, 382, 396);
		setTitle("[追加] Predalien Edit");
		initDialog();
		initCommon();
		initMouse();
		initKey();
		rdoMouse.setSelected(true);
		pnlMouse.setVisible(true);
		pnlKey.setVisible(false);
	}

	public PredalienEdit(Point parentPosition, PredalienDatum data) {
		super();
		setBounds(parentPosition.x, parentPosition.y, 382, 396);
		setTitle("[編集] Predalien Edit");
		initDialog();
		initCommon();
		initMouse();
		initKey();
		setDatum(data);
	}

	private void initDialog() {
		setResizable(false);
		getContentPane().setBackground(Color.DARK_GRAY);
		getContentPane().setLayout(null);
	}

	private void initCommon() {
		JLabel lblName = new JLabel("コンポーネント名");
		lblName.setBackground(Color.DARK_GRAY);
		lblName.setForeground(new Color(0, 200, 0));
		lblName.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		lblName.setBounds(12, 10, 209, 13);
		getContentPane().add(lblName);

		fldName = new JTextField();
		fldName.setBounds(12, 25, 356, 19);
		fldName.setBackground(Color.GRAY);
		fldName.setCaretColor(Color.LIGHT_GRAY);
		fldName.setForeground(Color.GREEN);
		fldName.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.DARK_GRAY));
		getContentPane().add(fldName);

		JLabel lblClassName = new JLabel("クラス名");
		lblClassName.setForeground(new Color(0, 200, 0));
		lblClassName.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		lblClassName.setBackground(Color.DARK_GRAY);
		lblClassName.setBounds(12, 54, 209, 13);
		getContentPane().add(lblClassName);

		fldClassName = new JTextField();
		fldClassName.setForeground(Color.GREEN);
		fldClassName.setCaretColor(Color.LIGHT_GRAY);
		fldClassName.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.DARK_GRAY));
		fldClassName.setBackground(Color.GRAY);
		fldClassName.setBounds(12, 69, 356, 19);
		getContentPane().add(fldClassName);

		ButtonGroup btnGrpMouseKey = new ButtonGroup();

		rdoMouse = new JRadioButton("マウス系");
		rdoMouse.setForeground(new Color(0, 200, 0));
		rdoMouse.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		rdoMouse.setBackground(Color.DARK_GRAY);
		rdoMouse.setBounds(8, 96, 75, 21);
		rdoMouse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				pnlMouse.setVisible(true);
				pnlKey.setVisible(false);
			}
		});
		getContentPane().add(rdoMouse);
		btnGrpMouseKey.add(rdoMouse);

		rdoKey = new JRadioButton("キー系");
		rdoKey.setForeground(new Color(0, 200, 0));
		rdoKey.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		rdoKey.setBackground(Color.DARK_GRAY);
		rdoKey.setBounds(87, 96, 75, 21);
		rdoKey.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				pnlMouse.setVisible(false);
				pnlKey.setVisible(true);
			}
		});
		getContentPane().add(rdoKey);
		btnGrpMouseKey.add(rdoKey);

		JLabel lblDelay = new JLabel("待機時間");
		lblDelay.setForeground(new Color(0, 200, 0));
		lblDelay.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		lblDelay.setBackground(Color.DARK_GRAY);
		lblDelay.setBounds(12, 287, 209, 13);
		getContentPane().add(lblDelay);

		fldDelay = new JTextField();
		fldDelay.setForeground(Color.GREEN);
		fldDelay.setCaretColor(Color.LIGHT_GRAY);
		fldDelay.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.DARK_GRAY));
		fldDelay.setBackground(Color.GRAY);
		fldDelay.setBounds(12, 303, 356, 19);
		getContentPane().add(fldDelay);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setUI(myButtonUI);
		btnCancel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.BLACK));
		btnCancel.setBackground(Color.DARK_GRAY);
		btnCancel.setForeground(new Color(0, 200, 0));
		btnCancel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		btnCancel.setBounds(278, 332, 91, 25);
		btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				PredalienEdit.this.dispose();
			}
		});
		getContentPane().add(btnCancel);

		JButton btnOk = new JButton("OK");
		btnOk.setUI(myButtonUI);
		btnOk.setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.BLACK));
		btnOk.setBackground(Color.DARK_GRAY);
		btnOk.setForeground(new Color(0, 200, 0));
		btnOk.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		btnOk.setBounds(173, 332, 91, 25);
		btnOk.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			}
		});
		getContentPane().add(btnOk);
	}

	private void initMouse() {
		ButtonGroup btnGrpMouseInfo = new ButtonGroup();

		pnlMouse = new JPanel();
		pnlMouse.setBackground(Color.DARK_GRAY);
		pnlMouse.setBounds(0, 123, 369, 154);
		pnlMouse.setLayout(null);
		getContentPane().add(pnlMouse);

		JLabel lblMouseInfo = new JLabel("マウス情報");
		lblMouseInfo.setBounds(12, 5, 209, 13);
		pnlMouse.add(lblMouseInfo);
		lblMouseInfo.setForeground(new Color(0, 200, 0));
		lblMouseInfo.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		lblMouseInfo.setBackground(Color.DARK_GRAY);

		rdoMouseInfoLeft = new JRadioButton("左クリック");
		rdoMouseInfoLeft.setBounds(8, 21, 75, 21);
		rdoMouseInfoLeft.setBackground(Color.DARK_GRAY);
		rdoMouseInfoLeft.setForeground(new Color(0, 200, 0));
		rdoMouseInfoLeft.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		pnlMouse.add(rdoMouseInfoLeft);
		btnGrpMouseInfo.add(rdoMouseInfoLeft);

		rdoMouseInfoCenter = new JRadioButton("中クリック");
		rdoMouseInfoCenter.setBounds(84, 21, 75, 21);
		rdoMouseInfoCenter.setForeground(new Color(0, 200, 0));
		rdoMouseInfoCenter.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		rdoMouseInfoCenter.setBackground(Color.DARK_GRAY);
		pnlMouse.add(rdoMouseInfoCenter);
		btnGrpMouseInfo.add(rdoMouseInfoCenter);

		rdoMouseInfoRight = new JRadioButton("右クリック");
		rdoMouseInfoRight.setBounds(160, 21, 75, 21);
		rdoMouseInfoRight.setForeground(new Color(0, 200, 0));
		rdoMouseInfoRight.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		rdoMouseInfoRight.setBackground(Color.DARK_GRAY);
		pnlMouse.add(rdoMouseInfoRight);
		btnGrpMouseInfo.add(rdoMouseInfoRight);

		chkMouseInfoShift = new JCheckBox("Shift");
		chkMouseInfoShift.setForeground(new Color(0, 200, 0));
		chkMouseInfoShift.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		chkMouseInfoShift.setBackground(Color.DARK_GRAY);
		chkMouseInfoShift.setBounds(8, 42, 75, 21);
		pnlMouse.add(chkMouseInfoShift);

		chkMouseInfoCtrl = new JCheckBox("Ctrl");
		chkMouseInfoCtrl.setForeground(new Color(0, 200, 0));
		chkMouseInfoCtrl.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		chkMouseInfoCtrl.setBackground(Color.DARK_GRAY);
		chkMouseInfoCtrl.setBounds(84, 42, 75, 21);
		pnlMouse.add(chkMouseInfoCtrl);

		chkMouseInfoAlt = new JCheckBox("Alt");
		chkMouseInfoAlt.setForeground(new Color(0, 200, 0));
		chkMouseInfoAlt.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		chkMouseInfoAlt.setBackground(Color.DARK_GRAY);
		chkMouseInfoAlt.setBounds(160, 42, 75, 21);
		pnlMouse.add(chkMouseInfoAlt);

		JLabel lblRelaMousePos = new JLabel("相対マウス位置");
		lblRelaMousePos.setForeground(new Color(0, 200, 0));
		lblRelaMousePos.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		lblRelaMousePos.setBackground(Color.DARK_GRAY);
		lblRelaMousePos.setBounds(12, 69, 209, 13);
		pnlMouse.add(lblRelaMousePos);

		JLabel lblRelaMousePosX = new JLabel("X座標");
		lblRelaMousePosX.setForeground(new Color(0, 200, 0));
		lblRelaMousePosX.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		lblRelaMousePosX.setBackground(Color.DARK_GRAY);
		lblRelaMousePosX.setBounds(12, 87, 31, 13);
		pnlMouse.add(lblRelaMousePosX);

		fldRelaMousePosX = new JTextField();
		fldRelaMousePosX.setForeground(Color.GREEN);
		fldRelaMousePosX.setCaretColor(Color.LIGHT_GRAY);
		fldRelaMousePosX.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.DARK_GRAY));
		fldRelaMousePosX.setBackground(Color.GRAY);
		fldRelaMousePosX.setBounds(45, 84, 75, 19);
		pnlMouse.add(fldRelaMousePosX);

		JLabel lblRelaMousePosY = new JLabel("Y座標");
		lblRelaMousePosY.setForeground(new Color(0, 200, 0));
		lblRelaMousePosY.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		lblRelaMousePosY.setBackground(Color.DARK_GRAY);
		lblRelaMousePosY.setBounds(128, 87, 31, 13);
		pnlMouse.add(lblRelaMousePosY);

		fldRelaMousePosY = new JTextField();
		fldRelaMousePosY.setForeground(Color.GREEN);
		fldRelaMousePosY.setCaretColor(Color.LIGHT_GRAY);
		fldRelaMousePosY.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.DARK_GRAY));
		fldRelaMousePosY.setBackground(Color.GRAY);
		fldRelaMousePosY.setBounds(160, 84, 75, 19);
		pnlMouse.add(fldRelaMousePosY);

		JLabel lblAbsMousePos = new JLabel("絶対マウス位置");
		lblAbsMousePos.setForeground(new Color(0, 200, 0));
		lblAbsMousePos.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		lblAbsMousePos.setBackground(Color.DARK_GRAY);
		lblAbsMousePos.setBounds(12, 110, 209, 13);
		pnlMouse.add(lblAbsMousePos);

		JLabel lblAbsMousePosX = new JLabel("X座標");
		lblAbsMousePosX.setForeground(new Color(0, 200, 0));
		lblAbsMousePosX.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		lblAbsMousePosX.setBackground(Color.DARK_GRAY);
		lblAbsMousePosX.setBounds(12, 128, 31, 13);
		pnlMouse.add(lblAbsMousePosX);

		fldAbsMousePosX = new JTextField();
		fldAbsMousePosX.setForeground(Color.GREEN);
		fldAbsMousePosX.setCaretColor(Color.LIGHT_GRAY);
		fldAbsMousePosX.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.DARK_GRAY));
		fldAbsMousePosX.setBackground(Color.GRAY);
		fldAbsMousePosX.setBounds(45, 125, 75, 19);
		pnlMouse.add(fldAbsMousePosX);

		JLabel lblAbsMousePosY = new JLabel("Y座標");
		lblAbsMousePosY.setForeground(new Color(0, 200, 0));
		lblAbsMousePosY.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		lblAbsMousePosY.setBackground(Color.DARK_GRAY);
		lblAbsMousePosY.setBounds(128, 128, 31, 13);
		pnlMouse.add(lblAbsMousePosY);

		fldAbsMousePosY = new JTextField();
		fldAbsMousePosY.setForeground(Color.GREEN);
		fldAbsMousePosY.setCaretColor(Color.LIGHT_GRAY);
		fldAbsMousePosY.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.DARK_GRAY));
		fldAbsMousePosY.setBackground(Color.GRAY);
		fldAbsMousePosY.setBounds(160, 125, 75, 19);
		pnlMouse.add(fldAbsMousePosY);
	}

	private void initKey() {
		pnlKey = new JPanel();
		pnlKey.setBackground(Color.DARK_GRAY);
		pnlKey.setBounds(0, 123, 368, 134);
		pnlKey.setLayout(null);
		getContentPane().add(pnlKey);

		JLabel lblKeyInfo = new JLabel("キー情報");
		lblKeyInfo.setBounds(12, 5, 209, 13);
		lblKeyInfo.setForeground(new Color(0, 200, 0));
		lblKeyInfo.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		lblKeyInfo.setBackground(Color.DARK_GRAY);
		pnlKey.add(lblKeyInfo);

		fldKeyInfo = new JTextField();
		fldKeyInfo.setEditable(false);
		fldKeyInfo.setForeground(Color.GREEN);
		fldKeyInfo.setCaretColor(Color.LIGHT_GRAY);
		fldKeyInfo.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.DARK_GRAY));
		fldKeyInfo.setBackground(Color.GRAY);
		fldKeyInfo.setBounds(12, 21, 356, 19);
		pnlKey.add(fldKeyInfo);

		JLabel lblKeyCode = new JLabel("キーコード");
		lblKeyCode.setForeground(new Color(0, 200, 0));
		lblKeyCode.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		lblKeyCode.setBackground(Color.DARK_GRAY);
		lblKeyCode.setBounds(12, 48, 209, 13);
		pnlKey.add(lblKeyCode);

		fldKeyCode = new JTextField();
		fldKeyCode.setForeground(Color.GREEN);
		fldKeyCode.setCaretColor(Color.LIGHT_GRAY);
		fldKeyCode.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.DARK_GRAY));
		fldKeyCode.setBackground(Color.GRAY);
		fldKeyCode.setBounds(12, 62, 356, 19);
		pnlKey.add(fldKeyCode);

		JLabel lblKeyModifiers = new JLabel("キー修飾");
		lblKeyModifiers.setForeground(new Color(0, 200, 0));
		lblKeyModifiers.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		lblKeyModifiers.setBackground(Color.DARK_GRAY);
		lblKeyModifiers.setBounds(12, 89, 209, 13);
		pnlKey.add(lblKeyModifiers);

		chkKeyModifiersShift = new JCheckBox("Shift");
		chkKeyModifiersShift.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		chkKeyModifiersShift.setForeground(new Color(0, 200, 0));
		chkKeyModifiersShift.setBackground(Color.DARK_GRAY);
		chkKeyModifiersShift.setBounds(12, 105, 74, 21);
		pnlKey.add(chkKeyModifiersShift);

		chkKeyModifiersCtrl = new JCheckBox("Ctrl");
		chkKeyModifiersCtrl.setForeground(new Color(0, 200, 0));
		chkKeyModifiersCtrl.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		chkKeyModifiersCtrl.setBackground(Color.DARK_GRAY);
		chkKeyModifiersCtrl.setBounds(90, 105, 74, 21);
		pnlKey.add(chkKeyModifiersCtrl);

		chkKeyModifiersAlt = new JCheckBox("Alt");
		chkKeyModifiersAlt.setForeground(new Color(0, 200, 0));
		chkKeyModifiersAlt.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		chkKeyModifiersAlt.setBackground(Color.DARK_GRAY);
		chkKeyModifiersAlt.setBounds(168, 105, 74, 21);
		pnlKey.add(chkKeyModifiersAlt);
	}

	private void setDatum(PredalienDatum datum) {
		String name = datum.getName();
		String className = datum.getClassName();
		String mouseInfo = datum.getMouseInfo();
		String relaMousePos = datum.getRelaMousePos();
		String absMousePos = datum.getAbsMousePos();
		String keyInfo = datum.getKeyInfo();
		String keyCode = datum.getKeyCode();
		String keyModifiers = datum.getKeyModifiers();
		String delay = datum.getDelay();

		// コンポーネント名
		fldName.setText(name);

		// クラス名
		fldClassName.setText(className);

		// マウス情報
		if (mouseInfo.contains("Button1")) {
			rdoMouseInfoLeft.setSelected(true);
		} else if (mouseInfo.contains("Button2")) {
			rdoMouseInfoCenter.setSelected(true);
		} else if (mouseInfo.contains("Button3")) {
			rdoMouseInfoRight.setSelected(true);
		}
		if (mouseInfo.contains("Shift")) {
			chkMouseInfoShift.setSelected(true);
		}
		if (mouseInfo.contains("Ctrl")) {
			chkMouseInfoCtrl.setSelected(true);
		}
		if (mouseInfo.contains("Alt")) {
			chkMouseInfoAlt.setSelected(true);
		}

		// 相対マウス位置
		Point relaMousePosPoint = PredalienUtil.convMousePosString2Point(relaMousePos);
		fldRelaMousePosX.setText(String.valueOf(relaMousePosPoint.x));
		fldRelaMousePosY.setText(String.valueOf(relaMousePosPoint.y));

		// 絶対マウス位置
		Point absMousePosPoint = PredalienUtil.convMousePosString2Point(absMousePos);
		fldAbsMousePosX.setText(String.valueOf(absMousePosPoint.x));
		fldAbsMousePosY.setText(String.valueOf(absMousePosPoint.y));

		// キー情報
		fldKeyInfo.setText(keyInfo);

		// キーコード
		fldKeyCode.setText(keyCode);

		// キー修飾（ちなみに10進の4はMETA、要はWindowsキーとかCommandキーのこと。
		// 10進の16は左クリックで、10進の32はAltGraph。AltGraphキーなんて見たことないゾ・・・）
		// Shift（10進で1）
		if ((Integer.parseInt(keyModifiers) & 1 << 0) != 0) {
			chkKeyModifiersShift.setSelected(true);
		}
		// Ctrl（10進で2）
		if ((Integer.parseInt(keyModifiers) & 1 << 1) != 0) {
			chkKeyModifiersCtrl.setSelected(true);
		}
		// Alt（10進で8）
		if ((Integer.parseInt(keyModifiers) & 1 << 3) != 0) {
			chkKeyModifiersAlt.setSelected(true);
		}

		// 待機時間
		fldDelay.setText(delay);

		// マウスとキーの切り替え
		if("".equals(mouseInfo)){
			rdoKey.setSelected(true);
			pnlMouse.setVisible(false);
			pnlKey.setVisible(true);
		}else{
			rdoMouse.setSelected(true);
			pnlMouse.setVisible(true);
			pnlKey.setVisible(false);
		}
	}

	private MetalToggleButtonUI myButtonUI = new MetalToggleButtonUI() {

		@Override
		protected Color getSelectColor() {
			return Color.BLACK;
		}

		@Override
		protected Color getFocusColor() {
			return new Color(0, 255, 0);
		}
	};
}
