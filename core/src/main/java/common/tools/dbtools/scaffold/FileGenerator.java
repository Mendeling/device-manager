package common.tools.dbtools.scaffold;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

public class FileGenerator {
	
	private static Logger logger = LoggerFactory.getLogger(FileGenerator.class);
	private final static String TEMPLATE_PATH = "common/tools/dbtools/scaffold/template/";
	private final static String SRC_PATH = "src/main/java/" ;
	private final static String TEST_PATH = "src/test/java/";
	protected String pkgPath;
	protected String clzName;
	protected String template;
	protected String suffix;
	private final Map<String, String> mapping;

	public FileGenerator(String pkgPath, String clzName, String template, Map<String, String> mapping) {
		this(pkgPath, clzName, template, mapping, "java");
	}

	public FileGenerator(String pkgPath, String clzName, String template, Map<String, String> mapping,
			String fileSuffix) {
		this.pkgPath = pkgPath;
		this.clzName = clzName;
		this.template = template;
		this.mapping = mapping;
		this.suffix = fileSuffix;
	}

	public String getTargetFilePath() {
		String result = pkgPath.replace(ScaffoldUtil.DOT, "/");
		result = result + "/";
		result = result + clzName + ScaffoldUtil.DOT + suffix;
		if (clzName.endsWith("Test")) {// 生成测试代码
			result = TEST_PATH + result;
		} else {
			result = SRC_PATH + result;// 生成正式代码
		}
		return result;
	}

	public void execute() {
		execute(true);
	}

	public void execute(boolean debug) {
		String tmplFile = TEMPLATE_PATH + template;
		InputStream templateInputStream = getClass().getClassLoader().getResourceAsStream(tmplFile);
		
		if (templateInputStream == null) {
			logger.debug("[WARN] " + tmplFile + " not exists.");
			return;
		}

		File f = new File(getTargetFilePath());
		if (f.exists()&&!debug) {
			logger.debug(f.getAbsoluteFile() + " aleardy exists.");
			return;
		}
		try {
			logger.debug(f.getAbsoluteFile() + " created !");
			if (debug) {
				writeContentWithTemplate(System.out, templateInputStream, mapping);
			} else {
				createFileWithDirs(f);
				writeContentWithTemplate(new PrintStream(f), templateInputStream, mapping);
			}
		} catch (IOException e) {
			logger.debug(f.getAbsoluteFile() + " create failed.");
			e.printStackTrace();
		}
	}

	private void writeContentWithTemplate(PrintStream ps, InputStream templateInputStream, Map<String, String> mapping)
			throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(templateInputStream));
		String line = null;
		while ((line = br.readLine()) != null) {
			line = replaceWithMapping(line, mapping);
			ps.println(line);
		}
		br.close();
	}

	private String replaceWithMapping(String srcLine, Map<String, String> mapping) {
		final String TAG_BEGI = "${";
		final String TAG_END = "}";
		String result = srcLine;
		for (String key : mapping.keySet()) {// 查询txt中的变量位置
			String value = mapping.get(key);
			result = StringUtils.replace(result, TAG_BEGI + key + TAG_END, value);
		}
		result = StringUtils.replace(result, TAG_BEGI + "pkgPath" + TAG_END, pkgPath);// 替换txt中的位置
		return result;
	}

	public boolean createFileWithDirs(File f) throws IOException {
		File parentDir = f.getParentFile();
		boolean parentCreated = false;
		if (!parentDir.exists()) {
			parentCreated = parentDir.mkdirs();
		}
		if (parentCreated) {
			return f.createNewFile();
		}
		return false;
	}

}
