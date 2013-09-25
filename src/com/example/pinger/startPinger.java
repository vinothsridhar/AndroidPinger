package com.example.pinger;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.RemoteViews;

public class startPinger extends IntentService
{

	public startPinger() {
		super("startPinger");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub
		ComponentName me=new ComponentName(this, Pinger.class);
		RemoteViews rv=new RemoteViews(arg0.getPackage(), R.layout.widget);
		AppWidgetManager mgr=AppWidgetManager.getInstance(this);
		
		if(Constants.timer!=null)
		{
		Constants.timer.cancel();
		Constants.timer=null;
		}
		
		rv.setCharSequence(R.id.start, "setText", "START");
		rv.setTextViewText(R.id.hostName, Constants.HOSTNAME);
		rv.setTextViewText(R.id.scount, "0");
		rv.setTextViewText(R.id.fcount, "0");
		
		Intent i=new Intent(getApplicationContext(),pingerService.class);
		PendingIntent pi1=PendingIntent.getService(getApplicationContext(), 0, i, 0);
		
		rv.setOnClickPendingIntent(R.id.start, pi1);
		
		mgr.updateAppWidget(me, rv);
	}
	
}