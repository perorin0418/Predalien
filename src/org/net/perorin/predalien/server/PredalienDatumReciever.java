package org.net.perorin.predalien.server;

import java.io.File;

import org.net.perorin.predalien.common.PredalienAbstractReciever;
import org.net.perorin.predalien.common.PredalienDatum;
import org.net.perorin.predalien.common.PredalienUtil;

public class PredalienDatumReciever extends PredalienAbstractReciever {

	@Override
	public String getPattern() {
		return "^[0-9]{16}-PredalienDatum.*$";
	}

	@Override
	public boolean recieve(File file) {
		PredalienDatum datum = PredalienUtil.convProperties2Datum(file);
		recieve(datum);
		return true;
	}

	/**
	 * これオーバーライドしてやりたい処理をしてくんろ
	 * @param datum
	 */
	public void recieve(PredalienDatum datum) {
		// NOP
	}

}
