package org.net.perorin.predalien.client;

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
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.net.perorin.predalien.common.PredalienDatum;
import org.net.perorin.predalien.common.PredalienUtil;

/**
 * require jdk 1.5
 *
 * @author perorin
 */
public class Predalien extends JPanel {

	/** 記録対象 */
	public String target = "";

	/** カーソルイメージ */
	private JLabel curImg = new JLabel(new ImageIcon(Predalien.class.getResource("PredalienCursor.png")));

	/** 親のコンテナ */
	private Container parentCnt = null;

	/** 初期描画時 */
	private boolean firstRendered = true;

	/** 親コンテナ内の子コンポーネント達の変化検知用 */
	private long childHash = 0L;

	/** ロガー */
	private Logger logger = Logger.getLogger(Predalien.class.getSimpleName());

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

	/** 自動操作再生の通信受信用 */
	private PredalienOrderReciever reciever = new PredalienOrderReciever() {

		@Override
		public void recieve(PredalienDatum datum) {

			// マウス操作かキー操作か判定
			if (!datum.getMouseInfo().equals("")) {
				click(datum);
			} else {
				key(datum);
			}
		};
	};

	/**
	 * マウスアダプター
	 */
	private MouseAdapter mouseAdapter = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {

			// フォーカスが外れるとキーリスナーが動かないので
			curImg.requestFocusInWindow();

			if (!PredalienUtil.isRecording()) {
				return;
			}

			// クリック位置からコンポーネントを取得
			Component c = currentComponentByPoint();

			String name = c.getName();
			String className = c.getClass().getSimpleName();
			String mouseInfo = MouseEvent.getMouseModifiersText(e.getModifiers());

			// マウス座標をコンポーネント内の座標系で取得
			Point childPoint = getLocationFrom(c, parentCnt);

			String relaMousePos = "x=" + childPoint.x + ",y=" + childPoint.y;
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

			PredalienDatumSender.send(new PredalienDatum(target, name, className, delay).registMouse(mouseInfo,
					relaMousePos, absMousePos));
		}
	};

	private KeyAdapter keyAdapter = new KeyAdapter() {

		// 正直keyTypedのほうが好きだけど、pressedかreleasedじゃないとキーコードが拾えない
		@Override
		public void keyReleased(KeyEvent e) {

			if (!PredalienUtil.isRecording()) {
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

			PredalienDatumSender.send(new PredalienDatum(target, "", "", delay).registKey(keyInfo, keyCode,
					keyModifiers));

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
	 *
	 * @return コンポーネント
	 */
	private Component currentComponentByPoint() {

		// 赤カーソルの位置
		Point pp = parentCnt.getMousePosition();

		// 赤カーソルを取得しないように1pxずらす
		Point p = new Point(pp.x - 1, pp.y - 1);
		return getComponentAtDeep(parentCnt, p);
	}

	/**
	 * cmpの位置をfromを基本とした座標系で取得する
	 * @param cmp
	 * @param from
	 * @return
	 */
	private Point getLocationFrom(Component cmp, Component from) {
		List<Component> list = getParentsTo(cmp, from);
		int retx = 0;
		int rety = 0;
		for (Component c : list) {
			retx += c.getX();
			rety += c.getY();
		}
		return new Point(retx, rety);
	}

	/**
	 * cmpの親をさかのぼっていき、toになるまでの一覧を取得する
	 * @param cmp
	 * @param to
	 * @return 親一覧
	 */
	private List<Component> getParentsTo(Component cmp, Component to) {
		List<Component> ret = new ArrayList<Component>();
		Component c = cmp;
		while (true) {
			if (c.equals(to)) {
				break;
			} else {
				ret.add(c);
			}
			c = c.getParent();
		}
		return ret;
	}

	/**
	 * {@link Component#getComponentAt(Point)}のDeep版。
	 *
	 * @param cmp
	 *            対象コンポーネント
	 * @param p
	 *            対象座標
	 * @return コンポーネント
	 */
	private Component getComponentAtDeep(Component cmp, Point p) {
		Component c = cmp.getComponentAt(p);
		if (cmp.equals(c)) {
			return c;
		} else {
			return getComponentAtDeep(c, new Point(p.x - c.getX(), p.y - c.getY()));
		}
	}

	/**
	 * マウスカーソルを透明にする
	 *
	 * @param c
	 *            コンポーネント
	 * @param transparent
	 *            <ul>
	 *            <li>true - 透明やで
	 *            <li>false - 透明じゃない
	 *            </ul>
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
	 *
	 * @param c
	 *            Container
	 * @return コンポーネント一覧
	 */
	private Component[] listComponents(Container c) {

		// HashSetを使って重複回避
		HashSet<Component> ret = new HashSet<Component>();
		Component cmps[] = c.getComponents();
		for (Component cmp : cmps) {
			ret.add(cmp);

			// 座標からコンポーネント探す
			// TODO 正直、ほかのやり方があるかも・・・
			for (int y = 0; y < cmp.getHeight(); y++) {
				for (int x = 0; x < cmp.getWidth(); x++) {
					ret.add(getComponentAtDeep(cmp, new Point(x, y)));
				}
			}
		}
		return ret.toArray(new Component[ret.size()]);
	}

	/**
	 * Component内の子コンポーネントに変化がないかチェックするで
	 *
	 * @param c
	 *            コンポーネント
	 * @return
	 *         <ul>
	 *         <li>true - 変化なし
	 *         <li>false - 変化あり
	 *         </ul>
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
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {

				// 赤カーソル画像を最前面に
				parentCnt.setComponentZOrder(Predalien.this, 0);

				Point p = parentCnt.getMousePosition();

				// 画面外の時はnullになる
				if (p != null) {

					Predalien.this.setBounds(p.x, p.y, 32, 32);
				}
			}
		};
		timer.schedule(task, 0, 10);

		Timer timerRec = new Timer();
		TimerTask taskRec = new TimerTask() {

			@Override
			public void run() {

				// カーソルの表示設定
				boolean recording = PredalienUtil.isRecording();
				Predalien.this.setVisible(recording);
				transparentMouseCursor(parentCnt, recording);

			}
		};
		timerRec.schedule(taskRec, 0, 100);
	}

	/**
	 * 記録と記録の間の時間の計る
	 *
	 * @return イベント間の時間
	 */
	private String measureDelay() {
		long now = System.currentTimeMillis();
		if (!(eventTime == 0L)) {
			long diff = now - eventTime;
			eventTime = now;

			// robot.delayは最大値60000まで
			if (diff > 60000) {
				diff = 60000;
			}
			return diff + "ms";
		} else {
			eventTime = now;
			return "0ms";
		}
	}

	/**
	 * ルートのコンポーネントを取得する。<br>
	 * Appletだとたぶんappletviewerが取れる？
	 *
	 * @param cmp
	 *            コンポーネント
	 * @return ルートコンポーネント
	 */
	private Component getRootComponent(Component cmp) {
		Component c = cmp.getParent();
		if (c == null) {
			return cmp;
		} else {
			return getRootComponent(c);
		}
	}

	/**
	 * 指定したコンポーネントを含むウィンドウのスクショを撮る
	 *
	 * @param cmp
	 *            コンポーネント
	 */
	private void screenshot(Component cmp) {
		Component root = getRootComponent(cmp);
		BufferedImage bi = robot.createScreenCapture(root.getBounds());
		//		try {
		//			// TODO 保存先の決定方法
		//			ImageIO.write(bi, "PNG", new File(""));
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
	}

	/**
	 * マウス自動操作
	 *
	 * @param datum
	 *            データム
	 */
	public void click(PredalienDatum datum) {

		// 待つ
		robot.delay(datum.getDelayAsInt());

		// コンポーネント取得
		Component cmp = this.searchComponent(datum);

		// スクショ
		screenshot(cmp);
		
		// コンポーネント座標
		Point p = cmp.getLocationOnScreen();

		// クリック位置
		Point clickPoint = PredalienUtil.convMousePosString2Point(datum.getRelaMousePos());

		// マウス移動
		robot.mouseMove(p.x + clickPoint.x, p.y + clickPoint.y);

		// キー修飾プレス
		if (datum.getMouseInfo().contains("Shift")) {
			robot.keyPress(KeyEvent.VK_SHIFT);
		}
		if (datum.getMouseInfo().contains("Ctrl")) {
			robot.keyPress(KeyEvent.VK_CONTROL);
		}
		if (datum.getMouseInfo().contains("Alt")) {
			robot.keyPress(KeyEvent.VK_ALT);
		}

		// マウスクリック
		if (datum.getMouseInfo().contains("Button1")) {
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.delay(1000);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		} else if (datum.getMouseInfo().contains("Button2")) {
			robot.mousePress(InputEvent.BUTTON2_MASK);
			robot.delay(1000);
			robot.mouseRelease(InputEvent.BUTTON2_MASK);
		} else if (datum.getMouseInfo().contains("Button3")) {
			robot.mousePress(InputEvent.BUTTON3_MASK);
			robot.delay(1000);
			robot.mouseRelease(InputEvent.BUTTON3_MASK);
		}

		// キー修飾リリース
		if (datum.getMouseInfo().contains("Shift")) {
			robot.keyRelease(KeyEvent.VK_SHIFT);
		}
		if (datum.getMouseInfo().contains("Ctrl")) {
			robot.keyRelease(KeyEvent.VK_CONTROL);
		}
		if (datum.getMouseInfo().contains("Alt")) {
			robot.keyRelease(KeyEvent.VK_ALT);
		}
	}

	public void key(PredalienDatum datum) {

	}

	/**
	 * コンポーネントを探す
	 *
	 * @param datum
	 * @return コンポーネント
	 */
	private Component searchComponent(PredalienDatum datum) {
		Component[] cs = listComponents(parentCnt);
		for (Component c : cs) {
			if (datum.getClassName().equals(c.getClass().getSimpleName()) && datum.getName().equals(c.getName())) {
				return c;
			}
		}
		return getComponentAtDeep(parentCnt, PredalienUtil.convMousePosString2Point(datum.getAbsMousePos()));
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

			// 自動操作用ロボット作成
			try {
				robot = new Robot();
			} catch (AWTException e) {
				e.printStackTrace();
			}

			// ターゲットを設定
			reciever.setTarget(target);

			// 受信機起動
			reciever.run();

			firstRendered = false;
		}
	}

}
