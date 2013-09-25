package com.example.pinger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class pingerService extends Service
{
	Timer timer;
	int success,failure;
	boolean running=true;
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		try {
			update(getApplicationContext(),intent);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(Constants.timer==null)
		{
			Constants.timer=new Timer();
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.SECOND, 1);
			cal.add(Calendar.MILLISECOND, 0);
			Constants.timer.schedule(new MyTime(getApplicationContext(),intent),cal.getTime(), Constants.TIMEOUT*1000);
		}
		return START_STICKY;
	}
	
	public boolean getRes() throws UnknownHostException
	{
		InetAddress address=InetAddress.getByName(Constants.HOSTNAME);
		boolean reachable=false;
		try {
			reachable=address.isReachable(Constants.TIMEOUT*1000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reachable;
	}
	
	public void update(Context context,Intent arg0) throws UnknownHostException
	{
		ComponentName me=new ComponentName(this, Pinger.class);
		RemoteViews rv=new RemoteViews(context.getPackageName(), R.layout.widget);
		AppWidgetManager mgr=AppWidgetManager.getInstance(this);
		
		if(getRes())
		{
			success++;
			System.out.println("Reached");
		}
		else
		{
			failure++;
			System.out.println("No Reached");
		}
		rv.setTextViewText(R.id.hostName, Constants.HOSTNAME);
		rv.setTextViewText(R.id.scount, String.valueOf(success));
		rv.setTextViewText(R.id.fcount, String.valueOf(failure));
		
		rv.setCharSequence(R.id.start, "setText", "STOP");
		
		Intent i=new Intent(context,SetHost.class);
		PendingIntent pi1=PendingIntent.getActivity(context, 0, i, 0);
		
		Intent i2=new Intent(context,stopPinger.class);
		PendingIntent pi2=PendingIntent.getService(context, 0, i2, 0);
		
		rv.setOnClickPendingIntent(R.id.set, pi1);
		rv.setOnClickPendingIntent(R.id.start, pi2);
		
		
		mgr.updateAppWidget(me, rv);
	}
	
	private class MyTime extends TimerTask
	{
		Context context;
		Intent intent;
		public MyTime(Context c,Intent i)
		{
			this.context=c;
			this.intent=i;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try
			{
				update(context,intent);
			}
			catch(Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}