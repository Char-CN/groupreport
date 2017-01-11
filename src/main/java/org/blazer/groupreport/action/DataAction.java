package org.blazer.groupreport.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.blazer.groupreport.filter.PermissionsFilter;
import org.blazer.groupreport.model.CoreKpi;
import org.blazer.groupreport.model.FinanceKpi;
import org.blazer.groupreport.service.DataService;
import org.blazer.groupreport.service.VersionService;
import org.blazer.groupreport.util.CMDUtil;
import org.blazer.groupreport.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller(value="dataAction")
@RequestMapping("/data")
public class DataAction extends BaseAction {

	private static Logger logger = LoggerFactory.getLogger(DataAction.class);

	@Autowired
	DataService dataService;

	@Autowired
	DataService coreKpiService;

	@Autowired
	VersionService versionService;

	@Value("#{systemProperties.phantomjs_run}")
	public String phantomjs_run;

	@Value("#{systemProperties.phantomjs_bin}")
	public String phantomjs_bin;

	@Value("#{systemProperties.http_path}")
	public String http_path;

	@Value("#{systemProperties.files_path}")
	public String files_path;

	@ResponseBody
	@RequestMapping("/getmaxctime")
	public String getConfig(HttpServletRequest request, HttpServletResponse response) {
		return versionService.getMaxVersionCreateTime(getParamMap(request));
	}

	@ResponseBody
	@RequestMapping("/findcorekpi")
	public List<CoreKpi> findCoreKpi(HttpServletRequest request, HttpServletResponse response) {
		return coreKpiService.findCoreKpiByMonth(getParamMap(request));
	}

	@ResponseBody
	@RequestMapping("/updatecorekpi")
	public boolean updateCoreKpi(HttpServletRequest request, HttpServletResponse response) {
		try {
			coreKpiService.updateCoreKpi(getParamMap(request));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	@ResponseBody
	@RequestMapping("/findfinancekpi")
	public List<FinanceKpi> findFinanceKpi(HttpServletRequest request, HttpServletResponse response) {
		return coreKpiService.findFinanceKpiByMonth(getParamMap(request));
	}

	@ResponseBody
	@RequestMapping("/updatefinancekpi")
	public boolean updateFinanceKpi(HttpServletRequest request, HttpServletResponse response) {
		try {
			coreKpiService.updateFinanceKpi(getParamMap(request));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	@RequestMapping("/downloadpdf")
	public void downloadPDF(HttpServletRequest request, HttpServletResponse response) {
		// 生成pdf
		String sessiondId = PermissionsFilter.getSessionId(request);
		String now = DateUtil.now();
		String fileName = now + "_" + sessiondId + ".pdf";
		String cmd = phantomjs_run;
		cmd += " " + phantomjs_bin;
		cmd += " " + http_path + "?" + PermissionsFilter.COOKIE_KEY + "=" + sessiondId;
		cmd += " " + files_path + File.separator + fileName;
		logger.info("run cmd : " + cmd);
		int exit = CMDUtil.run(cmd);
		logger.info("exit : " + exit);
		// 下载pdf
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + now + ".pdf");
		try {
			InputStream inputStream = new FileInputStream(new File(files_path + File.separator + fileName));
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			// 这里主要关闭。
			os.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
