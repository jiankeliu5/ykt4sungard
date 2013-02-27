package com.kingstargroup.action.bgAnalysis;

/**
 * Copyright (C), 2000-2005, Kingstar Co., Ltd. 
 * File name: LargeExceptAnalysisThread
 * Description: ��������ˮ����߳���
 * Modify History����Change Log��:  
 * �������ͣ��������޸ĵȣ�   ��������     ������    �������ݼ���
 * ����  				2005-10-19   ������    ��������ˮ����߳�
 * �޸�					2006-07-20   ������    �޸���־��¼��ʽ
 * <p>
 *
 * @author      ������
 * @version     1.0
 * @since       1.0
 */
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xml.utils.FastStringBuffer;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kingstargroup.SqlStatementDictionary;
import com.kingstargroup.commons.AnalyzeThreadInterface;
import com.kingstargroup.commons.HibernateSessionFactory;
import com.kingstargroup.form.MapAlertTable;
import com.kingstargroup.form.MapAnalysisPoint;
import com.kingstargroup.form.MapContactMonitorTable;
import com.kingstargroup.form.MapContactTable;
import com.kingstargroup.form.MapParamTable;
import com.kingstargroup.form.MapSerialexceptTable;
import com.kingstargroup.form.MapSerialexceptTableId;
import com.kingstargroup.form.RcvdtlCardResult;
import com.kingstargroup.form.TPosdtl;
import com.kingstargroup.utils.CommonSendMailUtil;
import com.kingstargroup.utils.CommonSendSMGUtil;
import com.kingstargroup.utils.DateUtil;
import com.kingstargroup.utils.GetApplicationResources;
import com.kingstargroup.utils.StringUtil;

public class LargeExceptAnalysisThread extends Thread implements
		AnalyzeThreadInterface {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(LargeExceptAnalysisThread.class);

	// flag of stop this thread
	private static boolean requestStop = false;

	// record analysis time
	private String recordTime = "";

	// default analysis is to analysis all from
	private String startTime = "20050101000000";

	// default analysis is to analysis to now
	private String endTime = DateUtil.getNow();
	
	private int accelateParam = 0;

	// default analysis cycle is one hour
	private double cycle = 1.0;

	// money limit
	private double limit = 100;

	// single money limit
	private double singleLimit = 19;

	// inteval param
	private int doubleCycle = 0;

	// alarm contact
	private String contactName = "";

	// record the record of analysis result
	private int mailRecord = 0;

	// whether to record
	private boolean recordOrNot = true;

	// whether to mail
	private boolean mailOrNot = false;

	// whether to SMG
	private boolean smgOrNot = false;

	// mail content' bufferString
	private FastStringBuffer buffer = null;

	// SMG content's Sting
	private String SmgMessage = "";

	// phone number list with delimeter ";"
	private String phoneNumber = "";

	// phone contact name list with delimeter ";"
	private String phoneName = "";

	// mail address list with delimeter ";"
	private String mailList = "";

	// mail contact name list with delimeter ";"
	private String mailName = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (!requestStop) {
			buffer = new FastStringBuffer(100);
			mailRecord = 0;
			anaylize();
		}
	}

	public void initParam() {
		if (logger.isInfoEnabled()) {
			logger.info("**************��������ˮ�����̳߳�ʼ��**************"); //$NON-NLS-1$
		}
		try {
			Session session = HibernateSessionFactory.currentSession();
			Query queryTime = session
					.createQuery("from MapAnalysisPoint point where point.analysisname=:analysisName");
			queryTime.setString("analysisName", "LARGE_EXCEPTION");
			Iterator iterTime = queryTime.iterate();
			if (iterTime.hasNext()) {
				MapAnalysisPoint mapAnalysisPoint = (MapAnalysisPoint) iterTime
						.next();
				this.startTime = mapAnalysisPoint.getAnalysispoint();
			}
			Query queryCycle = session
					.createQuery("from MapParamTable param where param.parameter=:paramRefer "
							+ "or param.parameter=:paramRefer1 or param.parameter=:paramRefer2");
			queryCycle.setString("paramRefer", "LargeAmountCycle");
			queryCycle.setString("paramRefer1", "LargeAmountLimit");
			queryCycle.setString("paramRefer2", "SingleAmountLimit");
			List listParam = queryCycle.list();
			Iterator iterParam = listParam.iterator();
			while (iterParam.hasNext()) {
				MapParamTable mapParamTable = (MapParamTable) iterParam.next();
				if ("LargeAmountCycle".equals(mapParamTable.getParameter())) {
					this.cycle = mapParamTable.getValue();
				}
				if ("LargeAmountLimit".equals(mapParamTable.getParameter())) {
					this.limit = mapParamTable.getValue();
				}
				if ("SingleAmountLimit".equals(mapParamTable.getParameter())) {
					this.singleLimit = mapParamTable.getValue();
				}

			}
			String s = String.valueOf(cycle * 3600);
			this.doubleCycle = Integer.parseInt(s.substring(0, s.indexOf('.')));
			if (!"20050101000000".equals(this.startTime)) {
				this.endTime = DateUtil.getNewTime(startTime, doubleCycle);
			} else {
				this.endTime = DateUtil.getNow();
				this.startTime = DateUtil
						.getNewTime(this.endTime, -doubleCycle);
			}

		} catch (HibernateException e) {
			logger.error("��������ˮ�����̳߳�ʼ��ʧ�ܣ�", e); //$NON-NLS-1$
		} finally {
			HibernateSessionFactory.closeSession();
		}
		if (logger.isInfoEnabled()) {
			logger.info("**************��������ˮ�����̳߳�ʼ���ɹ�**************"); //$NON-NLS-1$
		}
	}

	/**
	 * @param session
	 */
	private void getParameter(Session session) {
		phoneNumber = "";
		phoneName = "";
		mailList = "";
		contactName = "";
		recordOrNot = true;
		mailOrNot = false;
		smgOrNot = false;

		// monitor param
		Query queryOrNot = session
				.createQuery("from MapAlertTable alert where alert.parameter='LargeAmountRecord'");
		Iterator iterOrNot = queryOrNot.iterate();
		if (iterOrNot.hasNext()) {
			MapAlertTable alertTable = (MapAlertTable) iterOrNot.next();
			if (0==alertTable.getNote()) {
				recordOrNot = false;
			}
			if (1==alertTable.getMailalert()) {
				mailOrNot = true;
			}
			if (1==alertTable.getPhonealert()) {
				smgOrNot = true;
			}
		}
		Query queryCycle = session
				.createQuery("from MapParamTable as param where param.parameter=:paramRefer "
						+ "or param.parameter=:paramRefer1 or param.parameter=:paramRefer2");
		queryCycle.setString("paramRefer", "LargeAmountCycle");
		queryCycle.setString("paramRefer1", "LargeAmountLimit");
		queryCycle.setString("paramRefer2", "SingleAmountLimit");
		List listParam = queryCycle.list();
		Iterator iterParam = listParam.iterator();
		while (iterParam.hasNext()) {
			MapParamTable mapParamTable = (MapParamTable) iterParam.next();
			if ("LargeAmountCycle".equals(mapParamTable.getParameter())) {
				this.cycle = mapParamTable.getValue();
			}
			if ("LargeAmountLimit".equals(mapParamTable.getParameter())) {
				this.limit = mapParamTable.getValue();
			}
			if ("SingleAmountLimit".equals(mapParamTable.getParameter())) {
				this.singleLimit = mapParamTable.getValue();
			}

		}
		String s = String.valueOf(cycle * 3600);
		this.doubleCycle = Integer.parseInt(s.substring(0, s.indexOf('.')));

		// contact list
		Query queryContact = session
				.createQuery("from MapContactMonitorTable monitor where monitor.monitorno='LARGE_EXCEPTION'");
		List listContact = queryContact.list();
		Iterator iterContact = listContact.iterator();
		while (iterContact.hasNext()) {
			MapContactMonitorTable contactMonitor = (MapContactMonitorTable) iterContact
					.next();
			String nowDayTime = DateUtil.getNow("HHmmss");

			Query queryPhone = session
					.createQuery("from MapContactTable a where a.contactname=:contactName and a.starttime<=:starttime and a.endtime>=:endtime");
			queryPhone
					.setString("contactName", contactMonitor.getContactname());
			queryPhone.setString("starttime", nowDayTime);
			queryPhone.setString("endtime", nowDayTime);
			Iterator iterPhone = queryPhone.iterate();
			if (iterPhone.hasNext()) {
				MapContactTable contactTable = (MapContactTable) iterPhone
						.next();
				int dayofWeek = DateUtil.getDayofWeek();
				if ((1 == dayofWeek)
						&& ("1".equals(contactTable.getWeekdays().substring(6,
								7)))) {
					if (!"".equals(contactTable.getCellphone())) {
						phoneNumber += contactTable.getCellphone() + ";";
						phoneName += contactTable.getContactname() + ";";
					}
					if (!"".equals(contactTable.getEmail())) {
						mailList += contactTable.getEmail() + ";";
						mailName += contactMonitor.getContactname() + ";";
					}
					if (!"".equals(contactMonitor.getContactname())) {
						contactName += contactMonitor.getContactname() + ",";
					}
				} else if ("1".equals(contactTable.getWeekdays().substring(
						dayofWeek - 2, dayofWeek - 1))) {
					if (!"".equals(contactTable.getCellphone())) {
						phoneNumber += contactTable.getCellphone() + ";";
						phoneName += contactTable.getContactname() + ";";
					}
					if (!"".equals(contactTable.getEmail())) {
						mailList += contactTable.getEmail() + ";";
						mailName += contactMonitor.getContactname() + ";";
					}
					if (!"".equals(contactMonitor.getContactname())) {
						contactName += contactMonitor.getContactname() + ",";
					}
				}
			}
		}
		if (!"".equals(contactName)) {
			contactName = contactName
					.substring(0, contactName.lastIndexOf(","));
		}
	}

	public void anaylize() {
		if (logger.isInfoEnabled()) {
			logger.info("��������ˮ�����߳̿�ʼ��������"); //$NON-NLS-1$
		}
		Transaction tx = null;
		try{
		try {
			Session session = HibernateSessionFactory.currentSession();
			tx = session.beginTransaction();
			
			this.accelateParam = Integer
			.parseInt(BgMonitor.getIntance().accelateParam);
			endTime = DateUtil.getNewTime(endTime, accelateParam * 60);

			if (logger.isInfoEnabled()) {
				logger
						.info("��������ˮ�����߳̿�ʼ����ʱ��:" + DateUtil.getNow("yyyy/MM/dd/HH:mm:ss")); //$NON-NLS-1$
			}
			if (1 > DateUtil.checkTimeSequence(endTime, DateUtil.getNow())) {
				if (logger.isInfoEnabled()) {
					logger.info("��������ˮ�����̷߳���ʱ��Σ�" + startTime + " -- "
							+ endTime);
				}
			} else {
				endTime = DateUtil.getNow();
				if (logger.isInfoEnabled()) {
					logger.info("��������ˮ�����̷߳���ʱ��Σ�" + startTime + " -- "
							+ endTime);
				}
			}
			getParameter(session);
			// analysis time record
			recordTime = DateUtil.getNow();
			Query query = session.createQuery(SqlStatementDictionary.getIntance().getSQL("com.kingstargroup.action.bgAnalysis.LargeExceptAnalysisThread.bigAmountSerial"));

			query.setString("startTime", startTime);
			query.setString("endTime", endTime);
			query.setDouble("limit", limit);
			List list = query.list();
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				RcvdtlCardResult cardResult = (RcvdtlCardResult) iter.next();
				Query queryOne = session
						.createSQLQuery(SqlStatementDictionary.getIntance().getSQL("com.kingstargroup.action.bgAnalysis.LargeExceptAnalysisThread.getSerial"))
						.addEntity("posdtl", TPosdtl.class);
				queryOne
						.setLong("cardNo", cardResult.getCardNo());
				queryOne.setString("txDate", cardResult.getTxDate());
				Query queryCustomer = session
						.createSQLQuery(SqlStatementDictionary.getIntance().getSQL("com.kingstargroup.action.bgAnalysis.LargeExceptAnalysisThread.getCutName"))
						.addScalar("cutName", Hibernate.STRING);
				queryCustomer.setLong("cardno", cardResult.getCardNo());
				List listCust = queryCustomer.list();
				String cust_name = "";
				if (listCust.size() > 0) {
					cust_name = listCust.get(0).toString().trim();
				}
				List listOne = queryOne.list();
				Iterator iterOne = listOne.iterator();
				while (iterOne.hasNext()) {
					TPosdtl rcvdtl = (TPosdtl) iterOne.next();
					if (true == recordOrNot) {			
						MapSerialexceptTable seriExcept = new MapSerialexceptTable();
						MapSerialexceptTableId seriExptKey = new MapSerialexceptTableId();
						
						seriExptKey.setAnalysistime(recordTime);
						seriExptKey.setTransdate(rcvdtl.getId().getTransdate());
						seriExptKey.setDevphyid(rcvdtl.getId().getDevphyid());
						seriExptKey.setDevseqno(rcvdtl.getId().getDevseqno());
						seriExcept.setAccdate(rcvdtl.getAccdate());
						seriExcept.setAcctime(rcvdtl.getAcctime());

						seriExcept.setAmount(rcvdtl.getAmount());
						seriExcept.setBatchno(rcvdtl.getBatchno());
						seriExcept.setCardaftbal(rcvdtl.getCardaftbal());
						seriExcept.setCardbefbal(rcvdtl.getCardbefbal());
						seriExcept.setCardcnt(rcvdtl.getCardcnt());
						seriExcept.setCardno(rcvdtl.getCardno());
						seriExcept.setColdate(rcvdtl.getColdate());
						seriExcept.setColtime(rcvdtl.getColtime());

						seriExcept.setDelflag(rcvdtl.getDelflag());
						seriExcept.setErrcode(rcvdtl.getErrcode());
						seriExcept.setErrmsg(rcvdtl.getErrmsg());
						seriExcept.setId(seriExptKey);
						seriExcept.setManagefee(rcvdtl.getManagefee());
						seriExcept.setPurseno(rcvdtl.getPurseno());
						seriExcept.setShopid(rcvdtl.getShopid());
						seriExcept.setStatus(rcvdtl.getStatus());
						seriExcept.setSubsidyno(rcvdtl.getSubsidyno());
						seriExcept.setSysid(rcvdtl.getSysid());
						seriExcept.setTranscode(rcvdtl.getTranscode());
						seriExcept.setTransmark(rcvdtl.getTransmark());
						seriExcept.setTranstime(rcvdtl.getTranstime());

						seriExcept.setContactname(contactName);
						seriExcept.setAbnormaltype(Long.parseLong("3"));
						session.save(seriExcept);
					}
					mailRecord++;
					if (!"".equals(mailList)) {
						String device_name = "";
						device_name = DeviceNameDictionary.getInstance().get(
								rcvdtl.getId().getDevphyid()).trim();

						buffer.append(StringUtil.largeExceptStringGenerate(
								rcvdtl.getId().getDevphyid(), device_name,
								rcvdtl.getCardno(), cust_name, rcvdtl.getId().getDevseqno(),
								rcvdtl.getAmount(), rcvdtl.getId().getTransdate(), rcvdtl.getTranstime(),
								"LARGE_EXCEPTION.DAYTIME_ERR"));
					}
				}
			}

			// ���ʳ���

			recordTime = DateUtil.getNewTime(DateUtil.getNow(), 10);
			BgMonitor
					.monitorThreadStatusReport(
							"com.kingstargroup.action.bgAnalysis.LargeExceptAnalysisThread",
							"��������ˮ�����߳�", DateUtil
									.getNow("yyyy/MM/dd/HH:mm:ss"), String
									.valueOf(this.doubleCycle), "������...");
			Query querySingle = session.createSQLQuery(SqlStatementDictionary.getIntance().getSQL("com.kingstargroup.action.bgAnalysis.LargeExceptAnalysisThread.bigsingleAmountSerial"))
					.addEntity("posdtl", TPosdtl.class);

			querySingle.setString("startTime", startTime);
			querySingle.setString("endTime", endTime);
			querySingle.setDouble("singleLimit", singleLimit);
			List listSingle = querySingle.list();
			Iterator iterSingle = listSingle.iterator();
			while (iterSingle.hasNext()) {
				TPosdtl rcvdtl = (TPosdtl) iterSingle.next();
				if (true == recordOrNot) {
					MapSerialexceptTable seriExcept = new MapSerialexceptTable();
					MapSerialexceptTableId seriExptKey = new MapSerialexceptTableId();
					
					seriExptKey.setAnalysistime(recordTime);
					seriExptKey.setTransdate(rcvdtl.getId().getTransdate());
					seriExptKey.setDevphyid(rcvdtl.getId().getDevphyid());
					seriExptKey.setDevseqno(rcvdtl.getId().getDevseqno());
					seriExcept.setAccdate(rcvdtl.getAccdate());
					seriExcept.setAcctime(rcvdtl.getAcctime());

					seriExcept.setAmount(rcvdtl.getAmount());
					seriExcept.setBatchno(rcvdtl.getBatchno());
					seriExcept.setCardaftbal(rcvdtl.getCardaftbal());
					seriExcept.setCardbefbal(rcvdtl.getCardbefbal());
					seriExcept.setCardcnt(rcvdtl.getCardcnt());
					seriExcept.setCardno(rcvdtl.getCardno());
					seriExcept.setColdate(rcvdtl.getColdate());
					seriExcept.setColtime(rcvdtl.getColtime());

					seriExcept.setDelflag(rcvdtl.getDelflag());
					seriExcept.setErrcode(rcvdtl.getErrcode());
					seriExcept.setErrmsg(rcvdtl.getErrmsg());
					seriExcept.setId(seriExptKey);
					seriExcept.setManagefee(rcvdtl.getManagefee());
					seriExcept.setPurseno(rcvdtl.getPurseno());
					seriExcept.setShopid(rcvdtl.getShopid());
					seriExcept.setStatus(rcvdtl.getStatus());
					seriExcept.setSubsidyno(rcvdtl.getSubsidyno());
					seriExcept.setSysid(rcvdtl.getSysid());
					seriExcept.setTranscode(rcvdtl.getTranscode());
					seriExcept.setTransmark(rcvdtl.getTransmark());
					seriExcept.setTranstime(rcvdtl.getTranstime());

					seriExcept.setContactname(contactName);
					seriExcept.setAbnormaltype(Long.parseLong("15"));
					session.save(seriExcept);
				}
				mailRecord++;
				if (!"".equals(mailList)) {
					String device_name = "";
					device_name = DeviceNameDictionary.getInstance().get(
							rcvdtl.getId().getDevphyid()).trim();
					Query queryCustomer = session
							.createSQLQuery(SqlStatementDictionary.getIntance().getSQL("com.kingstargroup.action.bgAnalysis.LargeExceptAnalysisThread.getCutName"))
							.addScalar("cutName", Hibernate.STRING);
					queryCustomer.setLong("cardno", rcvdtl.getCardno());
					List listCust = queryCustomer.list();
					String cust_name = "";
					if (listCust.size() > 0) {
						cust_name = listCust.get(0).toString().trim();
					}
					buffer.append(StringUtil.largeExceptStringGenerate(
							rcvdtl.getId().getDevphyid(), device_name,
							rcvdtl.getCardno(), cust_name, rcvdtl.getId().getDevseqno(),
							rcvdtl.getAmount(), rcvdtl.getId().getTransdate(), rcvdtl.getTranstime(),
							"LARGE_EXCEPTION.ONETIME_ERR"));
				}

			}

			// record timepoint analysis
			Object tempAnalysis = session.get(MapAnalysisPoint.class,
					"LARGE_EXCEPTION");
			MapAnalysisPoint analysisPoint = null;
			if (tempAnalysis == null) {
				analysisPoint = new MapAnalysisPoint();
				analysisPoint.setAnalysisname("LARGE_EXCEPTION");
				analysisPoint.setAnalysispoint(endTime);
				session.save(analysisPoint);
			} else {
				analysisPoint = (MapAnalysisPoint) tempAnalysis;
				analysisPoint.setAnalysispoint(endTime);
				session.update(analysisPoint);
			}
			tx.commit();
		} catch (HibernateException e) {
			logger.error("��������ˮ�����̳߳��������쳣��", e); //$NON-NLS-1$
			e.printStackTrace();
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
			}
		} catch (Exception e) {
			logger.error("��������ˮ�����̳߳��������쳣��", e); //$NON-NLS-1$
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
			}
		} finally {
			HibernateSessionFactory.closeSession();
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			// mail
			sendMail();
			// SMG
			sendSMG();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("��������ˮ�����̳߳��������쳣��", e); //$NON-NLS-1$
		}

		// sleep thread follows the setting
		try {
			long sleepTime = Long.parseLong(String.valueOf(doubleCycle * 1000))
					- DateUtil.getIntervalTime(DateUtil.getNow(), recordTime);
			if (logger.isInfoEnabled()) {
				logger
						.info("��������ˮ�����߳̽�����������״̬!"
								+ " ���߿�ʼʱ��:" + DateUtil.getNow("yyyy/MM/dd/HH:mm:ss") + ", ����ʱ��:" + sleepTime + " ����"); //$NON-NLS-1$
			}
			this.startTime = this.endTime;
			this.endTime = DateUtil.getNewTime(this.endTime, doubleCycle);
			if (sleepTime <= 0) {
				sleep(600000);
			} else {
				sleep(sleepTime);
			}
		} catch (InterruptedException e) {
			logger.error("��������ˮ�����̳߳��������쳣�����ܱ�ǿ��ֹͣ�� ", e); //$NON-NLS-1$
		} catch (Exception e) {
			logger.error("��������ˮ�����̳߳��������쳣�����ܱ�ǿ��ֹͣ�� ", e); //$NON-NLS-1$
		}
	}

	public void terminate() {
		if (this.isAlive()) {
			logger.warn("��������ˮ�����߳�״̬�����󣬴�ʱ�߳�״̬����߳�!"); //$NON-NLS-1$
			requestStop = true;
		} else {
			logger.warn("��������ˮ�����߳�״̬�����󣬴�ʱ�߳�״̬�������߳�!"); //$NON-NLS-1$
			this.destroy();
		}
	}

	public boolean isTerminate() {
		return this.isAlive();
	}

	public void sendMail() {
		if ((true == mailOrNot) && (!"".equals(mailList))
				&& (0 != buffer.size())) {
			if (logger.isInfoEnabled()) {
				logger.info("��������ˮ�����̷߳����ʼ�����ϵ��:" + contactName);
			}
			String tmpStr = "";
			if (buffer.size() > 5000) {
				tmpStr = buffer.getString(0, 5000);
			}
			tmpStr = buffer.toString();

			String[] tempList = null;
			String[] nameList = null;
			tempList = mailList.split(";");
			nameList = mailName.split(";");
			for (int i = 0; i < tempList.length; i++) {
				if (!"".equals(tempList)) {
					CommonSendMailUtil.sendMail(GetApplicationResources
							.getInstance().getString(
									"com.kingstar.MonitorSys.SUBJECT_SERIAL"),
									nameList[i], tempList[i], StringUtil
									.largeExceptMailGenerate(nameList[i],
											DateUtil.reFormatTime(recordTime),
											mailRecord, tmpStr,
											"LARGE_EXCEPTION"));
				}
			}
		}
	}

	public void sendSMG() {
		if ((true == smgOrNot) && (!"".equals(phoneNumber)) && mailRecord > 0) {
			if (logger.isInfoEnabled()) {
				logger.info("��������ˮ�����̷߳��Ͷ��Ÿ���ϵ��:" + contactName);
			}
			String[] tempList = null;
			String[] nameList = null;
			tempList = phoneNumber.split(";");
			nameList = phoneName.split(";");
			// String tempPhone = phoneNumber.substring(0, phoneNumber
			// .lastIndexOf(";"));
			for (int i = 0; i < tempList.length; i++) {
				if (!"".equals(tempList[i])) {
					SmgMessage = StringUtil.wholeSMGGenerate(nameList[i],
							DateUtil.reFormatTime(recordTime), mailRecord,
							"LARGE_EXCEPTION");
					CommonSendSMGUtil.sendSMG(tempList[i], SmgMessage);
				}
			}
		}
	}

}