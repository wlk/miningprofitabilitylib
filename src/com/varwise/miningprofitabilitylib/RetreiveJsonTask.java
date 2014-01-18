package com.varwise.miningprofitabilitylib;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import android.os.AsyncTask;

public class RetreiveJsonTask extends AsyncTask<String, Void, String> {

    private MiningActivityBase ma;
    private String API_BASE = "http://www.coinchoose.com/api.php?base=LTC";
    
    public RetreiveJsonTask(MiningActivityBase ma){
    	this.ma = ma;
    }
    
    public void setAPIBase(String s){
    	this.API_BASE = s;
    }

    protected String doInBackground(String... urls) {
    	DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
		HttpGet httpget = new HttpGet(API_BASE);
		InputStream inputStream = null;
		String result = null;
		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			result = sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Exception squish) {
			}
		}
		return result;
	}
   
    @Override
    protected void onPostExecute(String result) {
          ma.onJsonReceived(result); 
    }


}
