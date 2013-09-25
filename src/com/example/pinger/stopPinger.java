package com.example.pinger;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class stopPinger extends IntentService
{

	public stopPinger() {
		super("stopPinger");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub
		update(getApplicationContext(),arg0);
		if(Constants.timer!=null)
		{
		Constants.timer.cancel();
		Constants.timer=null;
		}
	}
	
	public void update(Context context,Intent arg0)
	{
		ComponentName me=new ComponentName(this, Pinger.class);
		RemoteViews rv=new RemoteViews(arg0.getPackage(), R.layout.widget);
		AppWidgetManager mgr=AppWidgetManager.getInstance(this);
		
		rv.setTextViewText(R.id.start, "START");
		
		Intent i=new Intent(context,pingerService.class);
		PendingIntent pi=PendingIntent.getService(context, 0, i, 0);
		
		Intent i1=new Intent(context,SetHost.class);
		PendingIntent pi1=PendingIntent.getActivity(context, 0, i1, 0);
		
		context.stopService(i);
		
		rv.setOnClickPendingIntent(R.id.start, pi);
		rv.setOnClickPendingIntent(R.id.set, pi1);
		
		mgr.updateAppWidget(me, rv);
	}
	
}