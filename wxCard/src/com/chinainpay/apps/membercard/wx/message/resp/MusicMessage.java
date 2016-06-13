package com.chinainpay.apps.membercard.wx.message.resp;

import com.chinainpay.apps.membercard.wx.message.resp.BaseMessage;
import com.chinainpay.apps.membercard.wx.message.resp.Music;

/**
 * 音乐消息
 */
public class MusicMessage extends BaseMessage {
	// 音乐
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
}
