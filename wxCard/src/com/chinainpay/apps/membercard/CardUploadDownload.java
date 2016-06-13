package com.chinainpay.apps.membercard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import net.javabone.common.util.DaoUtil;
import net.javabone.common.util.RegexpUtils;
import net.javabone.common.util.TransactionDataSource;
import net.javabone.common.web.JsonUtil;

import com.chinainpay.mp.app.pagelet.Pagelet;
import com.chinainpay.mp.app.pagelet.axis.P;

public class CardUploadDownload implements Pagelet {

	private static final long serialVersionUID = 1L;
	private DataSource ds = P.ds("base");

	@Execute("请求制卡列表")
	@Pagelet.Purview()
	public void execute() throws SQLException{
		String sql="SELECT * FROM T_CIPMP_MAKECARD WHERE MER_CODE=? ";
		P.setAttribute("list", DaoUtil.list(ds, 0, sql, P.mp.getMerchant().getMerCode()));
	}
	
	@Execute("上传制卡文件")
	@Pagelet.Purview()
	public StringBuffer exportTxt(@RequestParam("makeCard_id") String makeCard_id,@RequestParam("card_pro") String card_pro,
			@RequestParam("file") String filePath) throws SQLException {
		Map<String, Object> rMap = new HashMap<String, Object>();	
		String msg="";
		TransactionDataSource tds = new TransactionDataSource(ds);		
		if(!RegexpUtils.isTXT(filePath.substring(0,filePath.lastIndexOf(".")))){
			msg ="上传文件不是TXT类型";
			rMap.put("errorMsg", msg);
			P.log.d("<script> parent.upload_callback("+JsonUtil.encoder(rMap)+")</script>");
			return new StringBuffer("<script> parent.upload_callback("+JsonUtil.encoder(rMap)+")</script>");
		}
		//在制卡表得到卡类型和卡数量
		String merCode = P.mp.getMerchant().getMerCode();
		String makeCardSql="SELECT VIRTUAL_CARD,CARD_AMT FROM T_CIPMP_MAKECARD WHERE MAKECARD_NO=?  AND MER_CODE=?";
		Map<String, Object> rowMaps=DaoUtil.row(tds, makeCardSql, makeCard_id,merCode);
		String cardType=rowMaps.get("VIRTUAL_CARD").toString();//卡类型 0:虚拟 1：实体
		Object CARD_AMT=rowMaps.get("CARD_AMT");//卡数量
		
		//查看是否上传过
		String validateCard= "SELECT COUNT(*) COUNT FROM T_CIPMP_CARD WHERE MER_CODE=? AND MAKECARD_NO=?";
		Map<String, Object> rowMap2=DaoUtil.row(tds, validateCard,merCode,makeCard_id);
		String COUNT=rowMap2.get("COUNT").toString();//卡表中的卡数量
		if(Integer.parseInt(COUNT)>0){
			P.log.d("==========已经上传过卡数据，商户号："+merCode+",制卡编号："+makeCard_id);
			msg ="已经上传过卡数据,制卡编号："+makeCard_id;
			rMap.put("errorMsg", msg);
			return new StringBuffer("<script> parent.upload_callback("+JsonUtil.encoder(rMap)+")</script>");
		}
		File file = new File(filePath);
		String encoding = "GBK";
		InputStreamReader read = null;
		String text[] = null;
		String CARDNOFROM="";
		String CARDNOTO="";
		try {
			read = new InputStreamReader(new FileInputStream(file), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			String sql = "INSERT INTO T_CIPMP_CARD(CARD_NUM,TWO_MAGNETIC,CARD_PWD,MER_CODE,MAKECARD_NO,CARD_TYPE,VIRTUAL_CARD,CARD_STATE) VALUES(?,?,?,?,?,?,?,?)";
			int i = 0;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				i = i + 1;
				System.out.println("第" + i + "张卡导入");
				text = lineTxt.trim().split(",");
				if (text.length != 4) {
					P.setAttribute("MSG", "文件格式错误，请校验后导入");
					break;
				}
				Map<String, Object> rowMap = DaoUtil
						.row(tds,
								"SElECT COUNT(*) AS COUNT FROM T_CIPMP_CARD WHERE CARD_NUM = ?",
								text[0]);
				if (Integer.parseInt(rowMap.get("COUNT").toString()) == 0) {
					String cardState=cardType.equals("0")?"1":"0";
					int a = DaoUtil.execute(tds, sql, text[0], text[1], text[2],merCode,makeCard_id,card_pro,cardType,cardState);
					if (a > 0) {
						if(i==1){
							CARDNOFROM=text[0];
							CARDNOTO=text[0];
						}else{							
							CARDNOFROM=text[0].compareTo(CARDNOFROM)<0? text[0]:CARDNOFROM;
							CARDNOTO=text[0].compareTo(CARDNOTO)>0? text[0]:CARDNOTO;
						}
					}
				} else {
					P.setAttribute("MSG", "数据库有存在的卡数据，请核对后再试");
					tds.transactionRollback();
					tds.close();
					msg ="数据库已存在卡号为"+text[0]+"的卡数据，请核对后再试";
					rMap.put("errorMsg", msg);
					return new StringBuffer("<script> parent.upload_callback("+JsonUtil.encoder(rMap)+")</script>");
				}
			}
			if(CARD_AMT instanceof Integer){
				if(i == Integer.parseInt(CARD_AMT.toString())){
					//提交数据
					//数据上传完成后，更改制卡表中的状态，并将初始卡号和结束卡号填充在制卡表中
					//根据实体卡和虚拟卡去更改卡状态    实体卡上传数据后为已上传  虚拟卡因不用入库操作，所以上传完卡数据直接更改状态为已销毁
					String cardSates=cardType.equals("0")?"3":"1";
					DaoUtil.execute(tds, "UPDATE T_CIPMP_MAKECARD SET CARD_STATES=?,CARDNOFROM=?,CARDNOTO=?,DAT_DATE= now() WHERE MER_CODE=? AND MAKECARD_NO=?"
							,cardSates,CARDNOFROM,CARDNOTO,P.mp.getMerchant().getMerCode(),makeCard_id);
					tds.transactionCommit();
					tds.close();
					msg ="上传成功！";
					rMap.put("errorMsg", msg);
					return new StringBuffer("<script> parent.upload_callback("+JsonUtil.encoder(rMap)+")</script>");
				}else{
					tds.transactionRollback();
					tds.close();
					msg ="上传失败，上传数量和制卡数量不匹配！";
					rMap.put("errorMsg", msg);
					return new StringBuffer("<script> parent.upload_callback("+JsonUtil.encoder(rMap)+")</script>");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tds.transactionRollback();
		} finally {
			try {
				read.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return null;
	}
	
	
	@Execute("下载制卡数据")
	@Pagelet.Purview()
	public void downLoadFile(@RequestParam("makeCard_NO") String makeCard_NO) throws Exception{
		//String merCode = P.mp.getMerchant().getMerCode();
		//String merCode = "01";
		System.out.println("进入下载方法");
        String fileurl="D:/下载卡数据.txt";
        try{
        	File file = new File(fileurl);
            if(createNewFile(makeCard_NO,file)){
            	DaoUtil.row(ds, "SELECT * FROM T_CIPMP_MAKECARD WHERE MAKECARD_NO = ?", makeCard_NO);
            }
        }catch(IOException e){
        	e.printStackTrace();
        	System.out.println("异常");     
        }
        P.setAttribute("MSG", "下载成功！下载路径为："+fileurl);
        execute();
    }
	
	
	private boolean createNewFile(String makeCard_NO,File file) throws Exception{
		FileOutputStream fos = new FileOutputStream(file);
		StringBuffer sb = new StringBuffer();
		System.out.println(makeCard_NO);
		String sql = "SELECT CARD_NUM,TWO_MAGNETIC,CARD_PWD,MAKECARD_NO FROM T_CIPMP_CARD WHERE MAKECARD_NO = ?";
		List<Map<String, Object>> list = DaoUtil.list(ds, 0, sql, makeCard_NO);
		System.out.println(list.size());
		if(list.size()>0){
			Map<String, Object> map = list.get(0);
			Set<String> set = map.keySet();
			for(String key : set){
				sb.append(key + "\r\t");
			}
			sb.append("\r\n");
		}
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Set<String> set = map.keySet();
			for(String key : set){
				sb.append(map.get(key) + "\r\t");
			}
			sb.append("\r\n");
		}
		fos.write(sb.toString().getBytes());
		fos.close();
		if(file.exists()){
			return true;
		}else{
			return false;
		}
	}
}
