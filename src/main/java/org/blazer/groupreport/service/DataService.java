package org.blazer.groupreport.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.blazer.groupreport.model.CoreKpi;
import org.blazer.groupreport.model.FinanceKpi;
import org.blazer.groupreport.util.BigDecimalUtil;
import org.blazer.groupreport.util.IntegerUtil;
import org.blazer.groupreport.util.SqlUtil;
import org.blazer.groupreport.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service(value = "dataService")
public class DataService {

	private static Logger logger = LoggerFactory.getLogger(DataService.class);

	@Value("#{systemProperties.total_key}")
	private String total_key;

	@Value("#{systemProperties.month_key}")
	private String month_key;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	VersionService versionService;

	public List<CoreKpi> findCoreKpiByMonth(HashMap<String, String> params) {
		logger.debug(params.toString());
		Integer yyyyMM = IntegerUtil.getInt0(params.get("yyyyMM"));
		Integer versionKey = versionService.getMaxVersionKeyByMonth(yyyyMM);
		Integer yyyy = IntegerUtil.getInt0(params.get("yyyy"));
		String sql = "select rt.time_name, rck.period_key, rck.time_key, rck.version_key,"
				+ " rck.register_num,"
				+ " rck.fixed_time_money,"
				+ " rck.fund_money,"
				+ " rck.current_money,"
				+ " rck.coupon_money"
				+ " from (select * from rp_time where time_key=?) rt "
				+ " left join (select period_key,time_key,version_key,register_num,fixed_time_money,fund_money,current_money,coupon_money "
				+ " from rp_core_kpi where version_key=? and period_key=? and time_key=?) rck on rt.time_key=rck.time_key";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, yyyyMM, versionKey, month_key, yyyyMM);
		logger.debug(SqlUtil.Show(sql, yyyyMM, versionKey, month_key, yyyyMM));
		logger.debug("list size : " + list.size());
		List<CoreKpi> rst = new ArrayList<CoreKpi>();
		for (Map<String, Object> map : list) {
			CoreKpi ck = new CoreKpi();
			ck.setTime_name(StringUtil.getStrEmpty(map.get("time_name")).replace("-", "年") + "月");
			ck.setPeriod_key(IntegerUtil.getInt0(map.get("period_key")));
			ck.setTime_key(IntegerUtil.getInt0(map.get("time_key")));
			ck.setVersion_key(IntegerUtil.getInt0(map.get("version_key")));
			ck.setRegister_num(IntegerUtil.getInt0(map.get("register_num")));
			ck.setFixed_time_money(BigDecimalUtil.getBigDecimal0(map.get("fixed_time_money")));
			ck.setFund_money(BigDecimalUtil.getBigDecimal0(map.get("fund_money")));
			ck.setCurrent_money(BigDecimalUtil.getBigDecimal0(map.get("current_money")));
			ck.setCoupon_money(BigDecimalUtil.getBigDecimal0(map.get("coupon_money")));
			// 计算
			BigDecimal bd = new BigDecimal(0);
			bd = bd.add(ck.getFixed_time_money());
			bd = bd.add(ck.getFund_money());
			bd = bd.add(ck.getCurrent_money());
			bd = bd.add(ck.getCoupon_money());
			ck.setTotal_money(bd);
			rst.add(ck);
		}
		List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql, yyyy, versionKey, total_key, yyyy);
		logger.debug("list2 size : " + list2.size());
		for (Map<String, Object> map : list2) {
			CoreKpi ck = new CoreKpi();
			ck.setTime_name(StringUtil.getStrEmpty(map.get("time_name")) + "年累计");
			ck.setPeriod_key(IntegerUtil.getInt0(map.get("period_key")));
			ck.setTime_key(IntegerUtil.getInt0(map.get("time_key")));
			ck.setVersion_key(IntegerUtil.getInt0(map.get("version_key")));
			ck.setRegister_num(IntegerUtil.getInt0(map.get("register_num")));
			ck.setFixed_time_money(BigDecimalUtil.getBigDecimal0(map.get("fixed_time_money")));
			ck.setFund_money(BigDecimalUtil.getBigDecimal0(map.get("fund_money")));
			ck.setCurrent_money(BigDecimalUtil.getBigDecimal0(map.get("current_money")));
			ck.setCoupon_money(BigDecimalUtil.getBigDecimal0(map.get("coupon_money")));
			// 计算
			BigDecimal bd = new BigDecimal(0);
			bd = bd.add(ck.getFixed_time_money());
			bd = bd.add(ck.getFund_money());
			bd = bd.add(ck.getCurrent_money());
			bd = bd.add(ck.getCoupon_money());
			ck.setTotal_money(bd);
			rst.add(ck);
		}
		return rst;
	}

	public void updateCoreKpi(HashMap<String, String> params) {
		logger.debug(params.toString());
		Integer period_key = IntegerUtil.getInt0(params.get("period_key"));
		Integer time_key = IntegerUtil.getInt0(params.get("time_key"));
		Integer version_key = IntegerUtil.getInt0(params.get("version_key"));
		String sql = "update rp_core_kpi set fixed_time_money=?, fund_money=?, current_money=?, coupon_money=? where version_key=? and period_key=? and time_key=?";
		BigDecimal fixed_time_money = BigDecimalUtil.getBigDecimal0(params.get("fixed_time_money"));
		BigDecimal fund_money = BigDecimalUtil.getBigDecimal0(params.get("fund_money"));
		BigDecimal current_money = BigDecimalUtil.getBigDecimal0(params.get("current_money"));
		BigDecimal coupon_money = BigDecimalUtil.getBigDecimal0(params.get("coupon_money"));
		logger.info("做了更新rp_core_kpi操作：" + SqlUtil.Show(sql, fixed_time_money, fund_money, current_money, coupon_money, version_key, period_key, time_key));
		jdbcTemplate.update(sql, fixed_time_money, fund_money, current_money, coupon_money, version_key, period_key, time_key);
	}

	public List<FinanceKpi> findFinanceKpiByMonth(HashMap<String, String> params) {
		logger.debug(params.toString());
		Integer yyyyMM = IntegerUtil.getInt0(params.get("yyyyMM"));
		Integer versionKey = versionService.getMaxVersionKeyByMonth(yyyyMM);
		String sql = "select rt.time_name, rfk.period_key, rfk.time_key, rfk.version_key, "
				+ " rfk.product_type, rfk.time_limit, rfk.product_name, rfk.transaction_money"
				+ " from (select * from rp_time where time_key=?) rt "
				+ " left join (select period_key,time_key,version_key,product_type,time_limit,product_name,transaction_money,sort "
				+ " from rp_finance_kpi where version_key=? and period_key=? and time_key=?) rfk on rt.time_key=rfk.time_key"
				+ " order by rfk.sort, rfk.time_limit";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, yyyyMM, versionKey, month_key, yyyyMM);
		logger.debug("list size : " + list.size());
		List<FinanceKpi> rst = new ArrayList<FinanceKpi>();

		Integer period_key = 0;
		Integer time_key = 0;
		Integer version_key = 0;
		String product_type = "";
		String time_limit = "";
		BigDecimal heji_transaction_money = new BigDecimal(0);
		BigDecimal xiaoji_transaction_money = new BigDecimal(0);
		BigDecimal total_transaction_money = new BigDecimal(0);

		// 将已排序的数据根据 产品类型、产品期限 两个维度进行小计计算
		for (Map<String, Object> map : list) {
			FinanceKpi fk = new FinanceKpi();
			fk.setPeriod_key(IntegerUtil.getInt0(map.get("period_key")));
			fk.setTime_key(IntegerUtil.getInt0(map.get("time_key")));
			fk.setVersion_key(IntegerUtil.getInt0(map.get("version_key")));
			fk.setProduct_type(StringUtil.getStrEmpty(map.get("product_type")));
			fk.setTime_limit(StringUtil.getStrEmpty(map.get("time_limit")));
			fk.setProduct_name(StringUtil.getStrEmpty(map.get("product_name")));
			fk.setTransaction_money(BigDecimalUtil.getBigDecimal0(map.get("transaction_money")));
			total_transaction_money = total_transaction_money.add(fk.getTransaction_money());
			// 计算
			// 小计产品类型和产品期限，条件是期限不为空
			if (!"".equals(fk.getTime_limit())) {
				System.out.println(fk);
				if (!"".equals(time_limit) && !time_limit.equals(fk.getTime_limit())) {
					FinanceKpi xiaoji = new FinanceKpi();
					xiaoji.setPeriod_key(period_key);
					xiaoji.setTime_key(time_key);
					xiaoji.setVersion_key(version_key);
					xiaoji.setProduct_type(product_type);
					xiaoji.setTime_limit(time_limit);
					xiaoji.setProduct_name("小计");
					xiaoji.setTransaction_money(xiaoji_transaction_money);
					rst.add(xiaoji);
					period_key = 0;
					time_key = 0;
					version_key = 0;
					product_type = "";
					time_limit = "";
					xiaoji_transaction_money = new BigDecimal(fk.getTransaction_money().toString());
				} else {
					xiaoji_transaction_money = xiaoji_transaction_money.add(fk.getTransaction_money());
				}
			} else if(!"".equals(time_limit) && !time_limit.equals(fk.getTime_limit())) {
				FinanceKpi xiaoji = new FinanceKpi();
				xiaoji.setPeriod_key(period_key);
				xiaoji.setTime_key(time_key);
				xiaoji.setVersion_key(version_key);
				xiaoji.setProduct_type(product_type);
				xiaoji.setTime_limit(time_limit);
				xiaoji.setProduct_name("小计");
				xiaoji.setTransaction_money(xiaoji_transaction_money);
				rst.add(xiaoji);
			}
			// 合计产品类型
			if (!"".equals(product_type) && !product_type.equals(fk.getProduct_type())) {
				FinanceKpi heji = new FinanceKpi();
				heji.setPeriod_key(period_key);
				heji.setTime_key(time_key);
				heji.setVersion_key(version_key);
				heji.setProduct_type(product_type);
				heji.setTime_limit("");
				heji.setProduct_name("合计");
				heji.setTransaction_money(heji_transaction_money);
				rst.add(heji);
				period_key = 0;
				time_key = 0;
				version_key = 0;
				product_type = "";
				time_limit = "";
				heji_transaction_money = new BigDecimal(fk.getTransaction_money().toString());
			} else {
				heji_transaction_money = heji_transaction_money.add(fk.getTransaction_money());
			}
			rst.add(fk);
			period_key = fk.getPeriod_key();
			time_key = fk.getTime_key();
			version_key = fk.getVersion_key();
			product_type = fk.getProduct_type();
			time_limit = fk.getTime_limit();
		}
		if (!"".equals(time_limit)) {
			if (!"".equals(product_type)) {
				FinanceKpi xiaoji = new FinanceKpi();
				xiaoji.setPeriod_key(period_key);
				xiaoji.setTime_key(time_key);
				xiaoji.setVersion_key(version_key);
				xiaoji.setProduct_type(product_type);
				xiaoji.setTime_limit(time_limit);
				xiaoji.setProduct_name("小计");
				xiaoji.setTransaction_money(xiaoji_transaction_money);
				rst.add(xiaoji);
			}
		}
		// 合计
		FinanceKpi heji = new FinanceKpi();
		heji.setPeriod_key(period_key);
		heji.setTime_key(time_key);
		heji.setVersion_key(version_key);
		heji.setProduct_type(product_type);
		heji.setTime_limit("");
		heji.setProduct_name("合计");
		heji.setTransaction_money(heji_transaction_money);
		rst.add(heji);
		// 总计
		FinanceKpi total = new FinanceKpi();
		total.setPeriod_key(period_key);
		total.setTime_key(time_key);
		total.setVersion_key(version_key);
		total.setProduct_type("总交易金额");
		total.setTime_limit("");
		total.setProduct_name("");
		total.setTransaction_money(total_transaction_money);
		rst.add(total);
		return rst;
	}

	public void updateFinanceKpi(HashMap<String, String> params) {
		logger.debug(params.toString());
		Integer period_key = IntegerUtil.getInt0(params.get("period_key"));
		Integer time_key = IntegerUtil.getInt0(params.get("time_key"));
		Integer version_key = IntegerUtil.getInt0(params.get("version_key"));
		String product_type = StringUtil.getStrEmpty(params.get("product_type"));
		String time_limit = StringUtil.getStrEmpty(params.get("time_limit"));
		String product_name = StringUtil.getStrEmpty(params.get("product_name"));
		String sql = "update rp_finance_kpi set transaction_money=? where version_key=? and period_key=? and time_key=? and product_type=? and time_limit=? and product_name=?";
		BigDecimal transaction_money = BigDecimalUtil.getBigDecimal0(params.get("transaction_money"));
		logger.info("做了更新rp_finance_kpi操作：" + SqlUtil.Show(sql, transaction_money, version_key, period_key, time_key, product_type, time_limit, product_name));
		jdbcTemplate.update(sql, transaction_money, version_key, period_key, time_key, product_type, time_limit, product_name);
	}

}
