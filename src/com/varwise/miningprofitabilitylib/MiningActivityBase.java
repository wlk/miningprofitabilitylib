package com.varwise.miningprofitabilitylib;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;

public abstract class MiningActivityBase extends Activity implements OnClickListener {
	public static final String TAG = "MainActivity";
	public static String ABOUT_MESSAGE = "LTC Mining Profitability \n\nBased on coinchoose.com API";
	public static String ERROR_MESSAGE = "There was an error contacting with remote servie. Please check your network connection or try again later.";
	protected TableLayout coinTable;
	protected ArrayList<Coin> coins;
	protected boolean sortDesc = true;
	protected String sortColumn;
	protected int clickCount = 0;
	protected boolean sortColumnTMP = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void executeGetCoinProfitabilityTask(String apiUrl){
		RetreiveJsonTask getCoinProfitabilityTask = new RetreiveJsonTask(this);
		getCoinProfitabilityTask.setAPIBase(apiUrl);
		getCoinProfitabilityTask.execute();
	}

	public void onJsonReceived(String json) {
		removePleaseWait();
		if (json == null || json.equals("")) {
		} else {
			coins = getCoinsArray(json);
			showTable();
		}
	}

	protected abstract void removePleaseWait();

	private void addHeader() {
		CoinHeader header = new CoinHeader();
		coinTable.addView(header.toTableRow(this, this, sortColumn, sortDesc));
	}

	private void addCoins() {
		for (Coin c : coins) {
			coinTable.addView(c.toTableRow(this, this));
		}
	}

	public void showTable() {
		coinTable.removeAllViews();
		addHeader();
		sortCoins();
		addCoins();
	}

	private void sortCoins() {
		if (sortColumnTMP) {
			Collections.sort(coins, new Coin.ProfitabilityComparator());
		} else {
			Collections.sort(coins, new Coin.NameComparator());
		}
	}

	private ArrayList<Coin> getCoinsArray(String json) {
		ArrayList<Coin> coins = new ArrayList<Coin>();
		JSONArray coinchooseJson;
		try {
			coinchooseJson = new JSONArray(json);
			for (int i = 0; i < coinchooseJson.length(); ++i) {
				coins.add(new Coin((JSONObject) coinchooseJson.get(i)));
			}
		} catch (JSONException e) {
			showDialog("Error", ERROR_MESSAGE);
			e.printStackTrace();
		}
		return coins;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {	
		return true;
	}

	public void showDialog(String title, String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.show();
	}

	@Override
	public void onClick(View arg0) {
		++clickCount;
		sortColumnTMP = !sortColumnTMP;
		if (clickCount % 2 == 0) {
			sortColumn = getResources().getString(R.string.profitability);
		} else {
			sortColumn = getResources().getString(R.string.coin);
		}

		showTable();
	}

}
