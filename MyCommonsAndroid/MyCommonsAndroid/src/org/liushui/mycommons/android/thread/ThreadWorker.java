package org.liushui.mycommons.android.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadWorker
{

	static ExecutorService executorService;

	public static void execute(Runnable runnable)
	{
		submit(runnable);
	}

	public static Future<?> submit(Runnable runnable)
	{
		if (executorService == null)
		{
			executorService = Executors.newFixedThreadPool(8);
		}
		return executorService.submit(runnable);
	}
}