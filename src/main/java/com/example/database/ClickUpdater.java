package com.example.database;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClickUpdater implements Runnable {
	private BlockingQueue<Integer> idQueue;
	private Sql2oDao sql2oDao;
	static final Logger logger = LoggerFactory.getLogger(ClickUpdater.class);

	public ClickUpdater(BlockingQueue<Integer> idQueue) {
		this.idQueue = idQueue;
		this.sql2oDao = new Sql2oDao();
	}

	public void run() {
		while (true) {
			try {
				Integer id = 0;
				while ((id = idQueue.take()) != null) {
					sql2oDao.updateClicks(id.intValue());
				}
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
		}
	}
}
