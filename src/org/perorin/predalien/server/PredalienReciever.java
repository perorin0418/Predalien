package org.perorin.predalien.server;

import java.io.File;
import java.io.FileFilter;
import java.util.Timer;
import java.util.TimerTask;

import org.perorin.predalien.client.PredalienDatum;
import org.perorin.predalien.client.PredalienUtil;

public class PredalienReciever {

	String tmpFilePath = PredalienUtil.getTempFilePath() + File.separator + "Predalien";
	String tmpPattern = "^[0-9]{16}-PredalienDatum.tmp$";

	public PredalienReciever() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
			}
		}, 0, 100);
	}

	/**
	 * これオーバーライドしてやりたい処理をしてくんろ
	 * @param datum
	 */
	public void recieve(PredalienDatum datum) {
		// NOP
	}

	FileFilter filter = new FileFilter() {

		public boolean accept(File pathname) {
			return pathname.getName().matches(tmpPattern);
		}
	};
}
