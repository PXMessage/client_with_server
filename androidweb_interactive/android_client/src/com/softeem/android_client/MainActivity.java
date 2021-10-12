package com.softeem.android_client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.softeem.bean.LoginInfo;
import com.softeem.http.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText et_name,et_pass;
	private Button btn_login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		et_name = (EditText)findViewById(R.id.tx_name);
		et_pass = (EditText)findViewById(R.id.tx_pass);
		btn_login = (Button)findViewById(R.id.btnlogin);
		
		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				String username = et_name.getText().toString();
				String password = et_pass.getText().toString();
				try {
					//get«Î«Û
					//String msg = HttpUtil.getRequest("http://10.0.2.2:8080/android_server/LoginServlet?username="+username+"&password="+password);
					
					List<NameValuePair> list = new ArrayList<NameValuePair>();
					list.add(HttpUtil.getNameValuePair("username", username));
					list.add(HttpUtil.getNameValuePair("password", password));
					//post«Î«Û
					String msg = HttpUtil.postRequest("http://10.0.2.2:8080/android_server/LoginServlet", list);
					LoginInfo loginInfo = getObject(msg);
					System.out.println(msg+"====="+loginInfo);
					if(loginInfo.isSuccess()){
						Intent i = new Intent(MainActivity.this,IndexActivity.class);
						startActivity(i);
						finish();
					}else{
						Toast.makeText(MainActivity.this, loginInfo.getMsg(), Toast.LENGTH_SHORT).show();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	public LoginInfo getObject(String jsonString){
		LoginInfo loginInfo = new LoginInfo();
		try {
			JSONObject jsonObj = new JSONObject(jsonString);
			loginInfo.setCode(jsonObj.getInt("code"));
			loginInfo.setMsg(jsonObj.getString("msg"));
			loginInfo.setSuccess(jsonObj.getBoolean("success"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return loginInfo;
	}

}
