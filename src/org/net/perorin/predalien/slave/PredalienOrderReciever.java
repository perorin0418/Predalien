package org.net.perorin.predalien.slave;

import java.io.File;

import org.net.perorin.predalien.common.PredalienAbstractReciever;
import org.net.perorin.predalien.common.PredalienDatum;
import org.net.perorin.predalien.common.PredalienUtil;

public class PredalienOrderReciever extends PredalienAbstractReciever {

	private String target = "";


	@Override
	public String getPattern() {
		return "^[0-9]{16}-PredalienOrder.*$";
	}

	@Override
	public boolean recieve(File file) {
		PredalienDatum datum = PredalienUtil.convProperties2Datum(file);
		if (getTarget().equals(datum.getTarget())) {
			recieve(datum);
			return true;
		} else {
			return false;
		}
	}

	public void recieve(PredalienDatum datum) {
		// NOP
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
