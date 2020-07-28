package io.spring.initializr.generator.spring.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FileTemplateUtil {

	public static void copy(InputStream in, OutputStream out, String packageName) throws IOException {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			bw = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

			// 替换
			String line = null;

			// 以行为单位进行遍历
			while ((line = br.readLine()) != null) {
				// 替换每一行中符合被替换字符条件的字符串
				if (line.contains("com.croot.demo")) {
					line = line.replaceAll("com.croot.demo", packageName);
				}
				if (line.contains("bs_demo_server")) {
					line = line.replaceAll("demo", packageName);
				}

				bw.write(line + "\n");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (br != null) {
				br.close();
			}
			if (bw != null) {
				bw.close();
			}
		}

	}

}
