package org.perorin.predalien.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.metal.MetalToggleButtonUI;

import org.perorin.predalien.client.PredalienDatum;
import org.perorin.predalien.client.PredalienEdit;
import org.perorin.predalien.client.PredalienModel;
import org.perorin.predalien.client.PredalienTable;

public class PredalienWindow extends JFrame {

	private static PredalienWindow instance;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private PredalienTable table;
	private PredalienModel model;
	public static boolean isRecord = false;

	/**
	 * シングルトン
	 * @return PredalienWindow
	 */
	public static PredalienWindow getInstance() {
		if (instance == null) {
			instance = new PredalienWindow();
		}
		return instance;
	}

	private PredalienWindow() {
		initialize();
		initFrame();
		initNorthPanel();
		initCenterPanel();
		initEastPanel();
		initSouthPanel();
		initTimer();

		this.setVisible(true);
	}

	private void initialize() {
	}

	private void initFrame() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		recordSetting();
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	private void initNorthPanel() {
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.DARK_GRAY);
		northPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
		contentPane.add(northPanel, BorderLayout.NORTH);

		final JToggleButton tglbtnRecord = new JToggleButton("記録");
		tglbtnRecord.setUI(myButtonUI);
		tglbtnRecord.setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.BLACK));
		tglbtnRecord.setBackground(Color.DARK_GRAY);
		tglbtnRecord.setForeground(new Color(0, 200, 0));
		tglbtnRecord.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 32));
		tglbtnRecord.setPreferredSize(new Dimension(90, 45));
		tglbtnRecord.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				isRecord = !isRecord;
				recordSetting();
			}
		});
		tglbtnRecord.setSelected(isRecord);
		northPanel.add(tglbtnRecord);

		final JToggleButton tglbtnPlay = new JToggleButton("再生");
		tglbtnPlay.setUI(myButtonUI);
		tglbtnPlay.setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.BLACK));
		tglbtnPlay.setBackground(Color.DARK_GRAY);
		tglbtnPlay.setForeground(new Color(0, 200, 0));
		tglbtnPlay.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 32));
		tglbtnPlay.setPreferredSize(new Dimension(90, 45));
		tglbtnPlay.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Not yet implemented...
			}
		});
		northPanel.add(tglbtnPlay);
	}

	private void initCenterPanel() {
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout(0, 0));
		centerPanel.setBackground(Color.DARK_GRAY);
		contentPane.add(centerPanel, BorderLayout.CENTER);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.BLACK));
		scrollPane.setBackground(Color.DARK_GRAY);
		scrollPane.getVerticalScrollBar().setUI(myScroolBar);
		scrollPane.getViewport().setBackground(Color.DARK_GRAY);
		centerPanel.add(scrollPane, BorderLayout.CENTER);

		model = new PredalienModel();
		table = new PredalienTable(model);
		scrollPane.setViewportView(table);
	}

	private void initEastPanel() {
		JPanel eastPanel = new JPanel();
		eastPanel.setBackground(Color.DARK_GRAY);
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		contentPane.add(eastPanel, BorderLayout.EAST);

		JButton btnUp = new JButton("上へ");
		btnUp.setUI(myButtonUI);
		btnUp.setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.BLACK));
		btnUp.setBackground(Color.DARK_GRAY);
		btnUp.setForeground(new Color(0, 200, 0));
		btnUp.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		eastPanel.add(btnUp);

		JButton btnDown = new JButton("下へ");
		btnDown.setUI(myButtonUI);
		btnDown.setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.BLACK));
		btnDown.setBackground(Color.DARK_GRAY);
		btnDown.setForeground(new Color(0, 200, 0));
		btnDown.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		eastPanel.add(btnDown);

		JButton btnIns = new JButton("追加");
		btnIns.setUI(myButtonUI);
		btnIns.setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.BLACK));
		btnIns.setBackground(Color.DARK_GRAY);
		btnIns.setForeground(new Color(0, 200, 0));
		btnIns.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		btnIns.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				PredalienEdit pe = new PredalienEdit(PredalienWindow.this.getLocation());
				pe.setModal(true);
				pe.setVisible(true);
			}
		});
		eastPanel.add(btnIns);

		JButton btnEdit = new JButton("編集");
		btnEdit.setUI(myButtonUI);
		btnEdit.setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.BLACK));
		btnEdit.setBackground(Color.DARK_GRAY);
		btnEdit.setForeground(new Color(0, 200, 0));
		btnEdit.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		btnEdit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				PredalienEdit pe = new PredalienEdit(PredalienWindow.this.getLocation(),
						new PredalienDatum((Vector) model.getDataVector().elementAt(table.getSelectedRow())));
				pe.setModal(true);
				pe.setVisible(true);
			}
		});
		eastPanel.add(btnEdit);

		JButton btnDel = new JButton("削除");
		btnDel.setUI(myButtonUI);
		btnDel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.BLACK));
		btnDel.setBackground(Color.DARK_GRAY);
		btnDel.setForeground(new Color(0, 200, 0));
		btnDel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		btnDel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				model.removeRow(table.getSelectedRow());
			}
		});
		eastPanel.add(btnDel);
	}

	private void initSouthPanel() {
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.DARK_GRAY);
		FlowLayout flowLayout = (FlowLayout) southPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		contentPane.add(southPanel, BorderLayout.SOUTH);

		JLabel lblMessage = new JLabel("");
		lblMessage.setForeground(new Color(0, 200, 0));
		lblMessage.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		southPanel.add(lblMessage);
	}

	private void initTimer() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				recordSetting();
			}
		};
		timer.schedule(task, 0, 1000);
	}

	public void addDatum(PredalienDatum pdm) {
		if (isRecord) {
			model.addRow(pdm.toArray());

			// スクロールバーが一番下にあるか判定
			BoundedRangeModel m = (BoundedRangeModel) scrollPane.getVerticalScrollBar().getModel();
			if (m.getValue() + m.getExtent() >= m.getMaximum()) {

				// スクロールを一番下にする
				scrollPane.getVerticalScrollBar().setValue(0);
				scrollPane.getViewport().scrollRectToVisible(new Rectangle(0, Integer.MAX_VALUE - 1, 1, 1));
			}
		}
	}

	private void recordSetting() {
		if (isRecord) {
			this.setIconImage(new ImageIcon(PredalienWindow.class.getResource("Alien-red-icon.png")).getImage());
			this.setTitle("[記録中] Predalien Window");
		} else {
			this.setIconImage(new ImageIcon(PredalienWindow.class.getResource("Alien-green-icon.png")).getImage());
			this.setTitle("[待機中] Predalien Window");
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
