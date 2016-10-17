package org.blazer.groupreport.util;

public class CMDUtil {

	public static void main(String[] args) {
		int i = run("/Users/hyy/Work/phantomjs-2.1.1-macosx/bin/phantomjs /Users/hyy/Work/workspace/group-report/doc/html2pdf.js http://bigdata.blazer.org:8050/print.html?MYSESSIONID=e3b0369aa35ccf47f73e10815b83df5239a78bbfbb3daaae /Users/hyy/Work/phantomjs-2.1.1-macosx/test.pdf");
		System.out.println(i);
	}

	public static int run(String shellString) {
		try {
			Process process = Runtime.getRuntime().exec(shellString);
			int exitValue = process.waitFor();
			return exitValue;
		} catch (Throwable e) {
			System.err.println("execute fail ... " + e.getMessage());
		}
		return -1;
	}

}
