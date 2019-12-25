package com.oracle.osacs;

import com.oracle.cep.api.event.*;
import com.alibaba.fastjson.JSON;
import com.oracle.bean.Cell;
import com.oracle.bean.JsonRootBean;
import com.oracle.bean.Row;
import com.oracle.cep.api.annotations.OsaStage;
import com.oracle.cep.api.stage.EventProcessor;
import com.oracle.cep.api.stage.ProcessorContext;

import com.oracle.utl.RestUtil;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("serial")
@OsaStage(name = "md5", description = "Create an md5 hex from a string", inputSpec = "input, job_id:string, job_title:string, min_salary:string, max_salary:string", outputSpec = "output, job_id:string, job_title:string, min_salary:string, max_salary:string,job_title_column_name:string")
public class CustomStage implements EventProcessor {

	EventFactory eventFactory;
	EventSpec outputSpec;
	 //RestUtil restUtil;
	@Override
	public void init(ProcessorContext ctx, Map<String, String> config) {
		eventFactory = ctx.getEventFactory();
		OsaStage meta = CustomStage.class.getAnnotation(OsaStage.class);
		String spec = meta.outputSpec();
		outputSpec = TupleEventSpec.fromAnnotation(spec);
	//	restUtil=new RestUtil();
	}

	@Override
	public void close() {
	}

	@Override
	public Event processEvent(Event event) {

		//前のステップからパラメーターjob_id、job_title、max_salary、min_salaryを取得する
		
		String job_id = (String) event.getAttr("job_id").getObjectValue();
		String job_title = (String)event.getAttr("job_title").getObjectValue();
		String max_salary= (String)event.getAttr("max_salary").getObjectValue();
		String min_salary= (String)event.getAttr("min_salary").getObjectValue();
//		
//		//HbaseのrestAPIに渡すJSONパラメーターを構築する
//		List<Cell> cellList=new ArrayList<Cell>();
//		Row row =new Row();
//		//HbaseのrowKeyを設定する(job_idのBase64形式)
//		row.setKey(Base64.getEncoder().encodeToString(job_id.getBytes()));
//		
//		Cell cell_JOB_ID =new Cell();
//		cell_JOB_ID.set$(Base64.getEncoder().encodeToString(job_id.getBytes()));
//		cell_JOB_ID.setColumn(Base64.getEncoder().encodeToString("info:JOB_ID".getBytes()));
//
//		Cell cell_JOB_TITLE =new Cell();
//	
//		cell_JOB_TITLE.set$(Base64.getEncoder().encodeToString(job_title.getBytes()));
//		cell_JOB_TITLE.setColumn(Base64.getEncoder().encodeToString("info:JOB_TITLE".getBytes()));
//		
//		Cell cell_MAX_SALARY =new Cell();
//		cell_MAX_SALARY.set$(Base64.getEncoder().encodeToString(max_salary.getBytes()));
//		cell_MAX_SALARY.setColumn(Base64.getEncoder().encodeToString("info:MAX_SALARY".getBytes()));
//	
//		
//		Cell cell_MIN_SALARY =new Cell();
//		//cell_MIN_SALARY.set$(JOB_ID);
//		cell_MIN_SALARY.set$(Base64.getEncoder().encodeToString(min_salary.getBytes()));
//		cell_MIN_SALARY.setColumn(Base64.getEncoder().encodeToString("info:MIN_SALARY".getBytes()));
//		
//		cellList.add(cell_JOB_ID);
//		cellList.add(cell_JOB_TITLE);
//		cellList.add(cell_MAX_SALARY);
//		cellList.add(cell_MIN_SALARY);
//		row.setCell(cellList);
//		List<Row> rowList=new ArrayList<Row>();
//		rowList.add(row);
//		JsonRootBean jsonRootBean = new JsonRootBean();
//		jsonRootBean.setRow(rowList);
//		
//		//TypeUtils.compatibleWithJavaBean =true;
//		
//		//javaBeanからJSONに変換する
//		String json= JSON.toJSONString(jsonRootBean);
//		//ここまで、パラメーターの構築は終わりました
//		
//		//rest apiのURLを設定する
//         String url="http://140.238.36.9:9081/HR_JOBS/JOB_ID";
//	     HttpPut httpPut = new HttpPut(url);   
//	     //http headerを設定する
//	     httpPut.setHeader("Accept","text/json;charset=UTF-8");
//	     httpPut.setHeader("Content-Type", "application/json;charset=UTF-8");
//           
//	     
//	     StringEntity se = new StringEntity(json,"utf-8");
//	     se.setContentType("text/json");
//	     se.setContentEncoding("UTF-8");
//	        
//	     httpPut.setEntity(se);
//	        
//         DefaultHttpClient client = new DefaultHttpClient(
//                 new PoolingClientConnectionManager());
//         String status_code="0";
//	      try {
//	    	 //rest　リクエストを投げで、レスポンスを取得する
//			CloseableHttpResponse response2 = client.execute(httpPut);
//			  status_code=response2.getStatusLine().getStatusCode()+"";
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
        //出力変数を構築する。
		Map<String, Object> values = new HashMap<String, Object>();
		

	    values.put("job_id", Base64.getEncoder().encodeToString(job_id.getBytes()));
		values.put("job_title",  Base64.getEncoder().encodeToString(job_title.getBytes()));
		values.put("min_salary", Base64.getEncoder().encodeToString(min_salary.getBytes()));
		values.put("max_salary", Base64.getEncoder().encodeToString(max_salary.getBytes()));
		
		values.put("job_title_column_name", "aW5mbzpKT0JfVElUTEU=");
		
		
		Event outputEvent = eventFactory.createEvent(outputSpec, values, event.getTime());
		return outputEvent;
	}
}