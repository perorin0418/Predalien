package org.net.perorin.predalien.server;

import org.net.perorin.predalien.common.PredalienUtil;

public class PredalienServerExecuter {
	public static void main(String[] args) throws InterruptedException {
		PredalienUtil.cleanTmp();
		PredalienWindow.getInstance();
	}
}
