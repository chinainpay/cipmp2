package com.chinainpay.apps.pool;

import java.rmi.RemoteException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;

import com.chinainpay.mp.app.pagelet.axis.P;
import com.cul.ws.imtxn.client.ImTxnServiceStub;
import com.cul.ws.imtxn.service.*;
import com.cul.ws.imtxn.service.bean.xsd.*;

/**
 * Servlet implementation class StartJksClass
 */
//@WebServlet(urlPatterns = { "/StartJksClass" }, loadOnStartup = 1)
public class CulwsServices extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Echo testTrade = new Echo();
	private static final String testString = "OK?";
	private static ImTxnServiceStub service = null;
	Thread initThread;

	static {
		testTrade.setArg(testString);
	}

	public CulwsServices() {
		super();
	}

	public void init() {
		try {
//			String realPath =P.getRequest().getContextPath();
//			P.log.d("项目路径"+realPath);
			P.log.d("发起银商接口请求");
			service = null;
			System.gc();
			System.setProperty("javax.net.ssl.keyStore", "D:/jks/WS1.jks");
			System.setProperty("javax.net.ssl.keyStorePassword", "loyalty");
			System.setProperty("javax.net.ssl.trustStore",
					"D:/jks/sichuanst.trust.jks");
			System.setProperty("javax.net.ssl.trustStorePassword",
					"hunanjicui&123");
			ConfigurationContext ACCX = ConfigurationContextFactory
					.createConfigurationContextFromFileSystem(
							"D:/jks/client_repo", null);

			service = new ImTxnServiceStub(ACCX, // 证书目录
					"https://180.168.71.178:6667/culws/services/imTxnService"); // 银商请求地址
			ServiceClient sc = service._getServiceClient();
			sc.engageModule("rampart");
			Options options = sc.getOptions();
			options.setUserName("ws1");// 银商用户名
			options.setPassword("loyalty");// 银商密码
			boolean r = testString.equals(service.echo(testTrade).get_return());
			String msg = r == true ? "成功" : "失败";
			P.log.d("银商接口请求"+msg);
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 充值
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param cardNoForm
	 * @param cardNoTo
	 * @param amount
	 * @param totalAmount
	 * @param expiredDate
	 * @param discount
	 * @param buyPerson
	 * @return
	 * @throws RemoteException
	 *             CodeMsg islv(IslvBean bean);
	 */
	public static CodeMsg islvBean(String merchantNo, String userId,
			String cardNoForm, String cardNoTo, String amount,
			String totalAmount, String expiredDate, String discount,
			String buyPerson) throws RemoteException {
		try {
			IslvBean bean = new IslvBean();
			bean.setAmount(amount);
			bean.setBuyPerson(buyPerson);
			bean.setCardNoFrom(cardNoForm);
			bean.setCardNoTo(cardNoTo);
			bean.setDiscount(discount);
			bean.setExpiredDate(expiredDate);
			bean.setMerchantNo(merchantNo);
			bean.setTotalAmount(totalAmount);
			bean.setUserId(userId);
			Islv islv = new Islv();
			islv.setBean(bean);
			return service.islv(islv).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	// String merchantNo：分店编号，15位，非空
	// String userId：操作员代码，20位，非空
	// String cardNoFrom：起始卡号，19位，非空
	// String cardNoTo：终止卡号，19位，非空
	// String isPager：是否分页：Y-分页，N-不分页
	// String pageNo：页码
	// String pageSize：每页大小
	/**
	 * 卡信息查询
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param cardNoForm
	 * @param cardNoTo
	 * @param isPager
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws RemoteException
	 */
	public static InfoData info(String merchantNo, String userId,
			String cardNoForm, String cardNoTo, String isPager, String pageNo,
			String pageSize) throws RemoteException {
		try {
			InfoBeanE bean = new InfoBeanE();
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setCardNoFrom(cardNoForm);
			bean.setCardNoTo(cardNoTo);
			bean.setIsPager(isPager);
			bean.setPageNo(pageNo);
			bean.setPageSize(pageSize);
			Info info = new Info();
			info.setBean(bean);
			return service.info(info).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	/**
	 * 交易明细查询
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param isPager
	 * @param pageNo
	 * @param pageSize
	 * @param DateFrom
	 * @param DateTo
	 * @return
	 * @throws RemoteException
	 */
	public static CtqyData ctqy(String merchantNo, String userId,
			String isPager, String pageNo, String pageSize, String dateFrom,
			String dateTo,String cardNo,String type) throws RemoteException {
		try {
			CtqyBean bean = new CtqyBean();
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setCardNo(cardNo);
			bean.setIsPager(isPager);
			bean.setPageNo(pageNo);
			bean.setPageSize(pageSize);
			bean.setDateFrom(dateFrom);
			bean.setDateTo(dateTo);
			bean.setQueryType(type);
			Ctqy ctqy = new Ctqy();
			ctqy.setBean(bean);
			return service.ctqy(ctqy).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	/**
	 * 卡密码修改
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param cardNo
	 * @param newPassword
	 * @param oldPassword
	 * @return
	 * @throws RemoteException
	 */
	public static CodeMsg updp(String merchantNo, String userId, String cardNo,
			String newPassword, String oldPassword) throws RemoteException {
		try {
			UpdpBean bean = new UpdpBean();
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setCardNo(cardNo);
			bean.setNewPassword(newPassword);
			bean.setOldPassword(oldPassword);
			Updp updp = new Updp();
			updp.setBean(bean);
			return service.updp(updp).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	/**
	 * 紧急扣款
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param cardNo
	 * @param txndate
	 * @param txntime
	 * @param terminal
	 * @param billNo
	 * @param isVerifyPassword
	 * @param password
	 * @param amount
	 * @return
	 * @throws RemoteException
	 */
	public static IdadBillData idadWithBill4BYJK(String merchantNo,
			String userId, String cardNo, String txndate, String txntime,
			String terminal, String billNo, String isVerifyPassword,
			String password, String amount) throws RemoteException {
		try {
			IdadBill4BYJKBean bean = new IdadBill4BYJKBean();
			bean.setAmount(amount);
			bean.setBillNo(billNo);
			bean.setCardNo(cardNo);
			bean.setIsVerifyPassword(isVerifyPassword);
			bean.setPassword(password);
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setTerminal(terminal);
			bean.setTxntime(txntime);
			bean.setTxndate(txndate);
			IdadWithBill4BYJK idadWithBill4BYJK = new IdadWithBill4BYJK();
			idadWithBill4BYJK.setBean(bean);
			return service.idadWithBill4BYJK(idadWithBill4BYJK).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	/**
	 * 4.7 退货
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param cardNo
	 * @param txnId
	 * @param amount
	 * @return
	 * @throws RemoteException
	 */
	public static CodeMsg iprf(String merchantNo, String userId, String cardNo,
			String txnId, String amount) throws RemoteException {
		try {
			IprfBean bean = new IprfBean();
			bean.setAmount(amount);
			bean.setCardNo(cardNo);
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setTxnId(txnId);
			Iprf iprf = new Iprf();
			iprf.setBean(bean);
			return service.iprf(iprf).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	/**
	 * 4.8 转账
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param cardNo
	 * @param cardNoTo
	 * @param cardNoFrom
	 * @param password
	 * @param amount
	 * @return
	 * @throws RemoteException
	 */
	public static CodeMsg itrf(String merchantNo, String userId,
			String cardNoTo, String cardNoFrom, String password, String amount)
			throws RemoteException {
		try {
			ItrfBean bean = new ItrfBean();
			bean.setAmount(amount);
			bean.setPassword(password);
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setCardNoTo(cardNoTo);
			bean.setCardNoFrom(cardNoFrom);
			Itrf itrf = new Itrf();
			itrf.setBean(bean);
			return service.itrf(itrf).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	/**
	 * 4.9 换卡
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param cardNo
	 * @param cardNoTo
	 * @param cardNoFrom
	 * @param password
	 * @return
	 * @throws RemoteException
	 */
	public static CodeMsg ictv(String merchantNo, String userId,
			String cardNoTo, String cardNoFrom, String password)
			throws RemoteException {
		try {
			IctvBean bean = new IctvBean();
			bean.setPassword(password);
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setCardNoTo(cardNoTo);
			bean.setCardNoFrom(cardNoFrom);
			Ictv ictv = new Ictv();
			ictv.setBean(bean);
			return service.ictv(ictv).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	/**
	 * 卡片收回
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param cardNo
	 * @param cardNoTo
	 * @param cardNoFrom
	 * @return
	 * @throws RemoteException
	 */
	public static CodeMsg irec(String merchantNo, String userId,
			String cardNoTo, String cardNoFrom) throws RemoteException {
		try {
			IrecBean bean = new IrecBean();
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setCardNoTo(cardNoTo);
			bean.setCardNoFrom(cardNoFrom);
			Irec irec = new Irec();
			irec.setBean(bean);
			return service.irec(irec).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}
	/**
	 * 4.11	卡状态操作（冻结解冻挂失解挂）
	 * @param merchantNo
	 * @param userId
	 * @param cardNoTo
	 * @param cardNoFrom
	 * @param type
	 * @return
	 * @throws RemoteException
	 */
	public static CodeMsg stat(String merchantNo, String userId,
			String cardNoTo, String cardNoFrom, String type)
			throws RemoteException {
		try {
			StatBean bean = new StatBean();
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setCardNoTo(cardNoTo);
			bean.setCardNoFrom(cardNoFrom);
			bean.setType(type);
			Stat stat = new Stat();
			stat.setBean(bean);
			return service.stat(stat).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	/**
	 * 4.12 卡密码重置
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param cardNo
	 * @param password
	 * @return
	 * @throws RemoteException
	 */
	public static CodeMsg rstp(String merchantNo, String userId, String cardNo,
			String password) throws RemoteException {
		try {
			RstpBean bean = new RstpBean();
			bean.setPassword(password);
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setCardNo(cardNo);
			Rstp rstp = new Rstp();
			rstp.setBean(bean);
			return service.rstp(rstp).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	/**
	 * 卡片延期
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param reqExpireDate
	 * @param cardNoFrom
	 * @param cardNoTo
	 * @return
	 * @throws RemoteException
	 */
	public static CodeMsg batchPostpone(String merchantNo, String userId,
			String reqExpireDate, String cardNoFrom, String cardNoTo)
			throws RemoteException {
		try {
			BatchPostponeBean bean = new BatchPostponeBean();
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setCardNoFrom(cardNoFrom);
			bean.setCardNoTo(cardNoTo);
			bean.setReqExpireDate(reqExpireDate);
			BatchPostpone batchPostpone = new BatchPostpone();
			batchPostpone.setBean(bean);
			return service.batchPostpone(batchPostpone).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	/**
	 * 4.14 卡密码尝试次数重置
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param cardNo
	 * @return
	 * @throws RemoteException
	 */
	public static CodeMsg rstpt(String merchantNo, String userId, String cardNo)
			throws RemoteException {
		try {
			RstptBean bean = new RstptBean();
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setCardNo(cardNo);
			Rstpt rstpt = new Rstpt();
			rstpt.setBean(bean);
			return service.rstpt(rstpt).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	/**
	 * 4.15 充值撤销
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param cardNo
	 * @param cardNoTo
	 * @param cardNoFrom
	 * @param totalAmount
	 * @param amount
	 * @param dMerchantNo
	 * @return
	 * @throws RemoteException
	 */
	public static CodeMsg idvv(String merchantNo, String userId,
			String cardNoTo, String cardNoFrom, String totalAmount,
			String amount, String dMerchantNo) throws RemoteException {
		try {
			IdvvBean bean = new IdvvBean();
			bean.setAmount(amount);
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setCardNoTo(cardNoTo);
			bean.setCardNoFrom(cardNoFrom);
			bean.setDMerchantNo(dMerchantNo);
			bean.setTotalAmount(totalAmount);
			Idvv idvv = new Idvv();
			idvv.setBean(bean);
			return service.idvv(idvv).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	/**
	 * 4.16 卡信息查询（参数控制是否验证密码）
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param cardNo
	 * @param isVerifyPassword
	 * @param cardNoFrom
	 * @param password
	 * @return
	 * @throws RemoteException
	 */
	public static IbaData infoByAll(String merchantNo, String userId,
			String cardNo, String isVerifyPassword, String cardNoFrom,
			String password) throws RemoteException {
		try {
			IbaBean bean = new IbaBean();
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setCardNo(cardNo);
			bean.setIsVerifyPassword(isVerifyPassword);
			bean.setPassword(password);
			InfoByAll infoByAll = new InfoByAll();
			infoByAll.setBean(bean);
			return service.infoByAll(infoByAll).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	/**
	 * 4.17 转账（校验密码）
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param cardNo
	 * @param cardNoFrom
	 * @param cardNoTo
	 * @param password
	 * @param amount
	 * @return
	 * @throws RemoteException
	 */
	public static CodeMsg itrfWithPin(String merchantNo, String userId,
			String cardNo, String cardNoFrom, String cardNoTo, String password,
			String amount) throws RemoteException {
		try {
			ItrfBean bean = new ItrfBean();
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setPassword(password);
			bean.setAmount(amount);
			bean.setCardNoFrom(cardNoFrom);
			bean.setCardNoTo(cardNoTo);
			ItrfWithPin itrfWithPin = new ItrfWithPin();
			itrfWithPin.setBean(bean);
			return service.itrfWithPin(itrfWithPin).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	/**
	 * 4.18 售卖（带流水号）
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param cardNo
	 * @param cardNoFrom
	 * @param cardNoTo
	 * @param expiredDate
	 * @param amount
	 * @param totalAmount
	 * @param discount
	 * @param buyPerson
	 * @param SerialNo
	 * @return
	 * @throws RemoteException
	 */
	public static ByCodeMsg islvBy(String merchantNo, String userId,
			String cardNo, String cardNoFrom, String cardNoTo,
			String expiredDate, String amount, String totalAmount,
			String discount, String buyPerson, String SerialNo)
			throws RemoteException {
		try {
			ByIslvBean bean = new ByIslvBean();
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setAmount(amount);
			bean.setCardNoFrom(cardNoFrom);
			bean.setCardNoTo(cardNoTo);
			bean.setSerialNo(SerialNo);
			bean.setBuyPerson(buyPerson);
			bean.setDiscount(discount);
			bean.setExpiredDate(expiredDate);
			bean.setTotalAmount(totalAmount);
			IslvBy islvBy = new IslvBy();
			islvBy.setBean(bean);
			return service.islvBy(islvBy).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	/**
	 * 4.19 售卖（校验流水号）
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param cardNo
	 * @param cardNoFrom
	 * @param cardNoTo
	 * @param expiredDate
	 * @param amount
	 * @param totalAmount
	 * @param discount
	 * @param buyPerson
	 * @param billNo
	 * @return
	 * @throws RemoteException
	 */
	public static ByCodeMsg islvByBill(String merchantNo, String userId,
			String cardNo, String cardNoFrom, String cardNoTo,
			String expiredDate, String amount, String totalAmount,
			String discount, String buyPerson, String billNo)
			throws RemoteException {
		try {
			ByIslvBillBean bean = new ByIslvBillBean();
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setAmount(amount);
			bean.setCardNoFrom(cardNoFrom);
			bean.setCardNoTo(cardNoTo);
			bean.setBuyPerson(buyPerson);
			bean.setDiscount(discount);
			bean.setExpiredDate(expiredDate);
			bean.setTotalAmount(totalAmount);
			bean.setBillNo(billNo);
			IslvByBill islvByBill = new IslvByBill();
			islvByBill.setBean(bean);
			return service.islvByBill(islvByBill).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

	/**
	 * 4.20 充值撤销（带流水号并严格校验）
	 * 
	 * @param merchantNo
	 * @param userId
	 * @param cardNo
	 * @param cardNoFrom
	 * @param cardNoTo
	 * @param amount
	 * @param totalAmount
	 * @param dMerchantNo
	 * @return
	 * @throws RemoteException
	 */
	public static ByCodeMsg idvvByBill(String merchantNo, String userId,
			String cardNo, String cardNoFrom, String cardNoTo, String amount,
			String totalAmount, String dMerchantNo) throws RemoteException {
		try {
			IdvvByBean bean = new IdvvByBean();
			bean.setMerchantNo(merchantNo);
			bean.setUserId(userId);
			bean.setAmount(amount);
			bean.setCardNoFrom(cardNoFrom);
			bean.setCardNoTo(cardNoTo);
			bean.setTotalAmount(totalAmount);
			bean.setDMerchantNo(dMerchantNo);
			IdvvByBill idvvByBill = new IdvvByBill();
			idvvByBill.setBean(bean);
			return service.idvvByBill(idvvByBill).get_return();
		} catch (RemoteException e) {
			throw e;
		}
	}

}
