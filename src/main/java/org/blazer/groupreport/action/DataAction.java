package org.blazer.groupreport.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.blazer.groupreport.model.CoreKpi;
import org.blazer.groupreport.model.FinanceKpi;
import org.blazer.groupreport.service.DataService;
import org.blazer.groupreport.service.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/data")
public class DataAction extends BaseAction {

	private static Logger logger = LoggerFactory.getLogger(DataAction.class);

	@Autowired
	DataService dataService;

	@Autowired
	DataService coreKpiService;

	@Autowired
	VersionService versionService;

	@ResponseBody
	@RequestMapping("/getmaxctime")
	public String getConfig(HttpServletRequest request, HttpServletResponse response) {
		return versionService.getMaxVersionTime();
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

}
