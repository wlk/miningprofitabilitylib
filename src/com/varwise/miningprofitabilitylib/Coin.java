package com.varwise.miningprofitabilitylib;

import java.util.Comparator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

public class Coin {
	protected String name;
	protected double ratio;
	protected double price;
	protected double difficulty;
	protected String currentBlocks;

	TableRow.LayoutParams coinTableLayout;

	public Coin() {
		coinTableLayout = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}

	public Coin(JSONObject json) throws JSONException {
		this();

		this.name = json.getString("name");
		this.ratio = json.getDouble("ratio");
		this.price = json.getDouble("price");
		this.difficulty = json.getDouble("difficulty");
		this.currentBlocks = json.getString("currentBlocks");

	}

	public TableRow toTableRow(Context context, OnClickListener clickListener) {
		TableRow tr = new TableRow(context);
		tr.setLayoutParams(coinTableLayout);

		tr.addView(getViewForProperty(context, clickListener, name));
		tr.addView(getViewForProperty(context, clickListener, ratio, "%.3f%%"));
		tr.addView(getViewForProperty(context, clickListener, price, "%.7f"));
		tr.addView(getViewForProperty(context, clickListener, difficulty, "%.3f"));
		tr.addView(getViewForProperty(context, clickListener, currentBlocks));
		return tr;
	}

	protected TextView getViewForProperty(Context context, OnClickListener clickListener, Double property, String format) {
		TextView tv = new TextView(context);
		tv.setPadding(5, 5, 5, 5);
		tv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.cell_shape));
		tv.setText(String.format(format, property));
		tv.setOnClickListener(clickListener);
		return tv;
	}

	protected TextView getViewForProperty(Context context, OnClickListener clickListener, String property) {
		TextView tv = new TextView(context);
		tv.setPadding(5, 5, 5, 5);
		tv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.cell_shape));
		tv.setText(property);
		tv.setOnClickListener(clickListener);
		return tv;
	}

	public static class NameComparator implements Comparator<Coin> {
		@Override
		public int compare(Coin a, Coin b) {
			return a.name.compareTo(b.name);
		}
	}

	public static class ProfitabilityComparator implements Comparator<Coin> {
		@Override
		public int compare(Coin a, Coin b) {
			return (b.ratio - a.ratio > 0) ? 1 : -1;
		}

	}

}
