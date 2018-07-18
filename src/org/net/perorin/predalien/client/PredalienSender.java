package org.net.perorin.predalien.client;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class PredalienSender {

	/**
	 * インスタンス作成禁止
	 */
	private PredalienSender() {
	}

	/**
	 * データ送信
	 * @param datum
	 */
	public static void send(PredalienDatum datum) {
		PredalienUtil.createTempDir();
		try {
			File file = File.createTempFile("Predalien" + File.separator +
					String.format("%016d", System.currentTimeMillis()) + "-PredalienDatum", ".tmp");
			PrintWriter fw = new PrintWriter(file);
			fw.println("Target = " + datum.getTarget());
			fw.println("Name = " + datum.getName());
			fw.println("ClassName = " + datum.getClassName());
			fw.println("MouseInfo = " + datum.getMouseInfo());
			fw.println("RelaMousePos = " + datum.getRelaMousePos());
			fw.println("AbsMousePos = " + datum.getAbsMousePos());
			fw.println("KeyInfo = " + datum.getKeyInfo());
			fw.println("KeyCode = " + datum.getKeyCode());
			fw.println("KeyModifiers = " + datum.getKeyModifiers());
			fw.println("Delay = " + datum.getDelay());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
