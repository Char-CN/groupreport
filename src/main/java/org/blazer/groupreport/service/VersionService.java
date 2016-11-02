package org.blazer.groupreport.service;

import java.util.List;
import java.util.Map;

import org.blazer.groupreport.util.IntegerUtil;
import org.blazer.groupreport.util.SqlUtil;
import org.blazer.groupreport.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service(value = "versionService")
public class VersionService {

	private static Logger logger = LoggerFactory.getLogger(VersionService.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public Integer getMaxVersionKey() {
		String sql = "select max(version_key) as version_key from rp_version";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if (list == null || list.size() == 0) {
			logger.error("not found table [rp_version] max [version_key]...");
			return 0;
		}
		Integer maxKey = IntegerUtil.getInt0(list.get(0).get("version_key"));
		if (maxKey == 0) {
			logger.error("found table max [version_key] == 0");
		}
		return maxKey;
	}

	public Integer getMaxVersionKeyByMonth(Integer month) {
		String sql = "select max(version_key) as version_key from rp_core_kpi where time_key=?";
		logger.debug(SqlUtil.Show(sql, month));
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, month);
		if (list == null || list.size() == 0) {
			logger.error("not found table [rp_version] max [version_key]...");
			return 0;
		}
		Integer maxKey = IntegerUtil.getInt0(list.get(0).get("version_key"));
		if (maxKey == 0) {
			logger.error("found table max [version_key] == 0");
		}
		return maxKey;
	}

	public String getMaxVersionCreateTime() {
		String sql = "select max(ctime) as ctime from rp_version";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if (list == null || list.size() == 0) {
			logger.error("not found table [rp_version] max [version_key]...");
			return null;
		}
		String maxCTime = StringUtil.getStrEmpty(list.get(0).get("ctime"));
		if (maxCTime == null) {
			logger.error("found table max [ctime] == null");
			return maxCTime;
		}
		return maxCTime.substring(0, "2012-01-01 11:11:11".length());
	}

}
