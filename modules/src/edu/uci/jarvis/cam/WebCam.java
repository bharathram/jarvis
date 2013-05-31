package edu.uci.jarvis.cam;

import java.io.File;
import java.io.IOException;

public class WebCam {
	private static String cmd1 = "mplayer -vo png:prefix=";
	private static String cmd2 = ":outdir=webCamOut -frames 1 tv://";

	public String capture() throws IOException {
		Runtime rt = Runtime.getRuntime();

		Process proc;
		try {
			String prefix = "" + System.currentTimeMillis();
			String cmd = cmd1 + prefix + cmd2;
			proc = rt.exec(cmd);
			proc.waitFor();
			File file = new File("webCamOut");
			if (file.isDirectory()) {
				String[] list = file.list();
				for (String string : list) {
					if (string.startsWith(prefix)) {
						return string;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new IOException();

	}

}