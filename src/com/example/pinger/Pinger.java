package com.example.pinger;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class Pinger extends AppWidgetProvider
{
	AppWidgetManager awm;
	RemoteViews rv;
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		if(Constants.timer!=null)
			Constants.timer.cancel();
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		awm=appWidgetManager;
		rv=new RemoteViews("com.example.pinger", R.layout.widget);
		
		rv.setTextViewText(R.id.hostName, Constants.HOSTNAME);
		rv.setTextViewText(R.id.scount, "0");
		rv.setTextViewText(R.id.fcount, "0");
		
		Intent i1=new Intent(context,SetHost.class);
		PendingIntent pi1=PendingIntent.getActivity(context, 0, i1, 0);
		
		Intent intent2=new Intent(context,pingerService.class);
		PendingIntent pi=PendingIntent.getService(context, 0, intent2, 0);
		
		rv.setOnClickPendingIntent(R.id.start, pi);
		rv.setOnClickPendingIntent(R.id.set, pi1);
		update(context);
	}
	
	public void update(Context context)
	{
		ComponentName me=new ComponentName(context,Pinger.class);
		
		awm.updateAppWidget(me, rv);
		
	}
}