package com.varwise.miningprofitabilitylib;

import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.TableRow;

public class CoinHeader extends Coin {	
	private static final String SORT_DESC= "▼";
	private static final String SORT_ASC= "▲";

	public CoinHeader(){
		super();
	}
	
	public TableRow toTableRow(Context context, OnClickListener clickListener, String sortColumn, boolean sortDesc){
		TableRow tr = new TableRow(context);
		tr.setLayoutParams(coinTableLayout);
		
		tr.addView(getViewForProperty(context, clickListener, makeHeaderWithSortIndicator(context.getResources().getString(R.string.coin), sortColumn, sortDesc)));
		tr.addView(getViewForProperty(context, clickListener, makeHeaderWithSortIndicator(context.getResources().getString(R.string.profitability), sortColumn, sortDesc)));
		tr.addView(getViewForProperty(context, clickListener, makeHeaderWithSortIndicator(context.getResources().getString(R.string.price), sortColumn, sortDesc)));
		tr.addView(getViewForProperty(context, clickListener, makeHeaderWithSortIndicator(context.getResources().getString(R.string.difficulty), sortColumn, sortDesc)));
		tr.addView(getViewForProperty(context, clickListener, makeHeaderWithSortIndicator(context.getResources().getString(R.string.blocks), sortColumn, sortDesc)));
		return tr;		
	}
	
	private String makeHeaderWithSortIndicator(String header, String sortColumn, boolean sortDesc){
		if(header.equals(sortColumn)){
			if(sortDesc){
				header = SORT_DESC + header;
			}
			else{
				header = SORT_ASC + header;
			}
		}
		return header;
	}
}
