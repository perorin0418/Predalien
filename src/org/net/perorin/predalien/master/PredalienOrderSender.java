package org.net.perorin.predalien.master;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.net.perorin.predalien.common.PredalienAbstractSender;
import org.net.perorin.predalien.common.PredalienDatum;
import org.net.perorin.predalien.common.PredalienUtil;

public class PredalienOrderSender extends PredalienAbstractSender {

	/** ロガー */
	private static Logger logger = Logger.getLogger(PredalienOrderSender.class.getSimpleName());

	/**
	 * インスタンス作成禁止
	 */
	private PredalienOrderSender() {
	}

	/**
	 * データ送信
	 *
	 * @param datum
	 */
	public static File send(PredalienDatum datum) {
		PredalienUtil.createTempDir();
		try {
			File file = File.createTempFile(
					String.format("%016d", System.currentTimeMillis()) + "-PredalienOrder",
					".tmp",
					new File(PredalienUtil.getTempFilePath() + File.separator + "Predalien"));
			PredalienUtil.writeDatumOnFile(file, datum);
			logger.info("Send:" + datum.toString());
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * データ送信
	 *
	 * @param datum データ
	 * @param wait データが処理(削除)されるまで待機する
	 * @return 送信したデータファイル（waitがtrueなら返却されるファイルはすでにこの世に存在していない）
	 */
	public static File send(PredalienDatum datum, boolean wait) {
		File file = send(datum);
		if (wait) {
			for (int times = 0; times < PredalienPropertis.getPropertiesAsInteger("predalien.order.sender.wait.times"); times++) {
				if (!file.exists()) {
					break;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}

}
