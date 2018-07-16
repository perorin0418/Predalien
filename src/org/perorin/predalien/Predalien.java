package org.perorin.predalien;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * require jdk 1.5
 * @author perorin
 */
public class Predalien extends JPanel {

	/** 記録対象 */
	public String target = "";

	/** カーソルイメージ */
	private JLabel curImg = new JLabel(new ImageIcon(Predalien.class.getResource("Cursor-icon.png")));

	/** 親のコンテナ */
	private Container parentCnt = null;

	/** 初期描画時 */
	private boolean firstRendered = true;

	/** 親コンテナ内の子コンポーネント達の変化検知用 */
	private long childHash = 0L;

	/** タイマー */
	private Timer timer = null;

	/** ロガー */
	private Logger logger = Logger.getLogger(Predalien.class.getSimpleName());

	/** 記録管理用ウィンドウ */
	private PredalienWindow window = null;

	/** Delay計測用 */
	private long eventTime = 0L;

	/** 透明なカーソル */
	private Cursor transCur = Toolkit.getDefaultToolkit().createCustomCursor(
			new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
			new Point(), "");

	/** 親コンテナのカーソル */
	private Cursor parentCur = null;

	/** ロボット */
	private Robot robot = null;

	/**
	 * マウスアダプター
	 */
	private MouseAdapter mouseAdapter = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {

			// フォーカスが外れるとキーリスナーが動かないので
			curImg.requestFocusInWindow();

			if (!PredalienWindow.isRecord) {
				return;
			}

			// クリック位置からコンポーネントを取得
			Component c = currentComponentByPoint();

			String name = c.getName();
			String className = c.getClass().getSimpleName();
			String mouseInfo = MouseEvent.getMouseModifiersText(e.getModifiers());

			// マウス座標をコンポーネント内の座標系で取得
			int childx = parentCnt.getMousePosition().x - c.getX();
			int childy = parentCnt.getMousePosition().y - c.getY();

			String relaMousePos = "x=" + childx + ",y=" + childy;
			String absMousePos = "x=" + parentCnt.getMousePosition().x + ",y=" + parentCnt.getMousePosition().y;

			// 前回のイベント時の時間差
			String delay = measureDelay();

			StringBuffer sb = new StringBuffer();
			sb.append("Target: [" + target + "] ");
			sb.append("Name: [" + name + "] ");
			sb.append("Class: [" + className + "] ");
			sb.append("Info: [" + mouseInfo + "] ");
			sb.append("RelaPos: " + relaMousePos + "] ");
			sb.append("AbsPos: " + relaMousePos + "] ");
			sb.append("Delay: " + delay + "]");
			logger.info(sb.toString());

			window.addDatum(new PredalienDatum(target, name, className, delay).registMouse(mouseInfo, relaMousePos, absMousePos));
		}
	};

	private KeyAdapter keyAdapter = new KeyAdapter() {

		// 正直keyTypedのほうが好きだけど、pressedかreleasedじゃないとキーコードが拾えない
		@Override
		public void keyReleased(KeyEvent e) {

			if (!PredalienWindow.isRecord) {
				return;
			}

			// キー修飾があるなら一緒に表示
			String keyInfo = e.getModifiers() == 0 ? KeyEvent.getKeyText(e.getKeyCode())
					: KeyEvent.getKeyText(e.getKeyCode()) + "+" + KeyEvent.getKeyModifiersText(e.getModifiers());

			// キーコード
			String keyCode = String.valueOf(e.getKeyCode());

			// キー修飾（ShiftとかAltとかそういうの）
			String keyModifiers = String.valueOf(e.getModifiers());
			String delay = measureDelay();

			StringBuffer sb = new StringBuffer();
			sb.append("Target: [" + target + "] ");
			sb.append("Info: [" + keyInfo + "] ");
			sb.append("Code: " + keyCode + "] ");
			sb.append("Modifiers: " + keyModifiers + "] ");
			sb.append("Delay: " + delay + "]");
			logger.info(sb.toString());

			window.addDatum(new PredalienDatum(target, "", "", delay).registKey(keyInfo, keyCode, keyModifiers));

		}
	};

	/**
	 * コンストラクタ
	 */
	public Predalien() {
		super();
		this.setLayout(null);
		this.setPreferredSize(new Dimension(32, 32));
		this.add(curImg);

		// サイズを1x1にすることでpaintメソッドが動くようになる
		this.setBounds(0, 0, 1, 1);
		curImg.setBounds(0, 0, 32, 32);

		// 背景色を透明にする
		curImg.setBackground(new Color(0, 0, 0, 0));

		// これがないと透明にならない
		curImg.setOpaque(true);

		// これがないとキーリスナーが動かない
		curImg.setFocusable(true);
	}

	/**
	 * 赤カーソルの位置からコンポーネントを取得<br>
	 * TODO 赤カーソルから1pxずらした位置でコンポーネントを取得している
	 * @return コンポーネント
	 */
	private Component currentComponentByPoint() {

		// 赤カーソルの位置
		Point pp = parentCnt.getMousePosition();

		// 赤カーソルを取得しないように1pxずらす
		Point p = new Point(pp.x - 1, pp.y - 1);
		return parentCnt.getComponentAt(p);
	}

	/**
	 * マウスカーソルを透明にする
	 * @param c コンポーネント
	 * @param transparent
	 * <ul>
	 * <li>true - 透明やで
	 * <li>false - 透明じゃない
	 * </ul>
	 */
	private void transparentMouseCursor(Container c, boolean transparent) {
		if (transparent) {
			c.setCursor(transCur);
		} else {
			c.setCursor(parentCur);
		}
	}

	/**
	 * 赤カーソルにリスナーをセット
	 */
	private void registListner() {
		curImg.addMouseListener(mouseAdapter);
		curImg.addKeyListener(keyAdapter);
	}

	/**
	 * Container内のコンポーネントの一覧を取得する<br>
	 * Comboboxみたいないくつかのコンポーネントが合体してるやつもバラバラにコンポーネントを取得する（できるだけ）
	 * @param c Container
	 * @return コンポーネント一覧
	 */
	private Component[] listComponents(Container c) {

		// HashSetを使って重複回避
		HashSet<Component> ret = new HashSet<Component>();
		Component cmps[] = c.getComponents();
		for (Component cmp : cmps) {
			ret.add(cmp);

			// 座標からコンポーネント探す
			//TODO 正直、ほかのやり方があるかも・・・
			for (int y = 0; y < cmp.getHeight(); y++) {
				for (int x = 0; x < cmp.getWidth(); x++) {
					ret.add(cmp.getComponentAt(x, y));
				}
			}
		}
		return ret.toArray(new Component[ret.size()]);
	}

	/**
	 * Component内の子コンポーネントに変化がないかチェックするで
	 * @param c コンポーネント
	 * @return
	 * <ul>
	 * <li>true - 変化なし
	 * <li>false - 変化あり
	 * </ul>
	 */
	private boolean checkChildHash(Container c) {
		Component cmps[] = listComponents(c);
		long hash = 0L;
		for (Component con : cmps) {
			hash += con.hashCode();
		}
		if (hash == childHash) {
			return true;
		} else {
			logger.info("Detect changing");
			childHash = hash;
			return false;
		}
	}

	/**
	 * タイマーセット
	 */
	private void scheduleTimer() {
		timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {

				// カーソルの表示設定
				Predalien.this.setVisible(PredalienWindow.isRecord);
				transparentMouseCursor(parentCnt, PredalienWindow.isRecord);

				if (!checkChildHash(parentCnt)) {

					// 赤カーソル画像を最前面に
					parentCnt.setComponentZOrder(Predalien.this, 0);
				}

				Point p = parentCnt.getMousePosition();

				// 画面外の時はnullになる
				if (p != null && PredalienWindow.isRecord) {

					Predalien.this.setBounds(p.x, p.y, 32, 32);
				}
			}
		};
		timer.schedule(task, 0, 1);
	}

	/**
	 * 記録と記録の間の時間の計る
	 * @return イベント間の時間
	 */
	private String measureDelay() {
		long now = System.currentTimeMillis();
		if (!(eventTime == 0L)) {
			long diff = now - eventTime;
			eventTime = now;
			return diff + "ms";
		} else {
			eventTime = now;
			return "0ms";
		}
	}

	public void click(PredalienDatum datum) {

		// 待つ
		robot.delay(datum.getDelayAsInt());

		// コンポーネント取得
		Component cmp = this.searchComponent(datum);

		// コンポーネント座標
		Point p = cmp.getLocationOnScreen();

		// クリック位置
		Point clickPoint = PredalienUtil.convMousePosString2Point(datum.getRelaMousePos());

		// マウス移動
		robot.mouseMove(p.x + clickPoint.x, p.y + clickPoint.y);
	}

	public void key(PredalienDatum datum) {

	}

	/**
	 * コンポーネントを探す
	 * @param datum
	 * @return コンポーネント
	 */
	private Component searchComponent(PredalienDatum datum) {
		Component[] cs = listComponents(parentCnt);
		for (Component c : cs) {
			if (c.getClass().getSimpleName().equals(datum.getClassName()) && c.getName().equals(datum.getName())) {
				return c;
			}
		}
		return parentCnt.getComponentAt(PredalienUtil.convMousePosString2Point(datum.getAbsMousePos()));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (firstRendered) {

			// 親のコンテナ
			parentCnt = this.getParent();

			// 親のマウスカーソル取得
			parentCur = parentCnt.getCursor();

			// タイマーセット
			scheduleTimer();

			// リスナー登録
			registListner();

			// 記録対象
			target = parentCnt.getClass().getSimpleName() + ":" + parentCnt.getName();

			// 記録管理用ウィンドウ展開
			window = PredalienWindow.getInstance();

			// 自動操作用ロボット作成
			try {
				robot = new Robot();
			} catch (AWTException e) {
				e.printStackTrace();
			}

			firstRendered = false;
		}
	}

	@Override
	protected void finalize() throws Throwable {

		// 別に必要な処理じゃないけど気分的にタイマー止めとく
		if (timer != null) {
			timer.cancel();
		}
	}

}
