package org.liushui.mycommons.android.msg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class McMsgQueue {

	List<McMsg> queue = new ArrayList<McMsg>();;

	/**
	 * 事件有效期 小于0 时表示没有过期时间
	 */
	long validTime = 3000000;

	/**
	 * 有效事件个数 超过个数之前的事件会被覆盖
	 */
	int validSize = 20;

	/**
	 * 添加一个时间
	 * 
	 * @param McMsg
	 */
	public void addMsg(McMsg mm) {
		if (mm != null) {
			mm.time = System.currentTimeMillis();
			queue.add(mm);
		}
		clearInValideMcMsg();
	}

	/**
	 * 获取某个时间后发生的事件 按时间倒序排
	 * 
	 * @param time
	 */
	public List<McMsg> getMcMsgs(long time) {
		List<McMsg> ets = null;
		for (int i = queue.size() - 1; i > -1; i--) {
			McMsg McMsg = queue.get(i);
			long t = McMsg.time;
			if (t > time) {
				if (ets == null) {
					ets = new ArrayList<McMsg>();
				}
				ets.add(McMsg);
			} else {
				break;
			}
		}
		return ets;
	}

	public void clearMsgs() {
		queue.clear();
	}

	/**
	 * 清空过期事件
	 */
	public void clearInValideMcMsg() {
		long current = System.currentTimeMillis();
		List<McMsg> toRemovedMcMsgs = new ArrayList<McMsg>();
		/***
		 * 清除超过有效个数的事件
		 */
		for (int i = 0; i < queue.size() - validSize; i++) {
			toRemovedMcMsgs.add(queue.get(i));
		}
		/**
		 * 清除过期事件
		 */
		if (validTime > 0) {
			for (int i = toRemovedMcMsgs.size(); i < queue.size(); i++) {
				McMsg mm = queue.get(i);
				if (current - mm.time > validTime) {
					toRemovedMcMsgs.add(mm);
				} else {
					break;
				}
			}
		}
		if (toRemovedMcMsgs != null) {
			for (Iterator<McMsg> iterator = toRemovedMcMsgs.iterator(); iterator.hasNext();) {
				McMsg McMsg = iterator.next();
				queue.remove(McMsg);
			}
		}
	}
}