package org.net.perorin.predalien.slave;

import java.io.File;
import java.io.IOException;

import org.net.perorin.predalien.common.PredalienAbstractSender;
import org.net.perorin.predalien.common.PredalienDatum;
import org.net.perorin.predalien.common.PredalienUtil;

public class PredalienDatumSender extends PredalienAbstractSender {

	/**
	 * インスタンス作成禁止
	 */
	private PredalienDatumSender() {
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
			PredalienUtil.writeDatumOnFile(file, datum);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
