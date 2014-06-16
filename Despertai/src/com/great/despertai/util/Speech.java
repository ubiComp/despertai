package com.great.despertai.util;

import java.util.HashMap;
import java.util.Locale;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

/**
 * @author ufc74.mesquita
 *
 */
public class Speech implements TextToSpeech.OnInitListener {

	private TextToSpeech tts;
	private static final HashMap<String, String> parameters = new HashMap<String, String>();

	public Speech(Context context) {
		tts = new TextToSpeech(context, this);
//		parameters.put(TextToSpeech.Engine.KEY_PARAM_VOLUME,
//				Float.toString(50f));
//		// tts.setPitch(2f);
		tts.setSpeechRate(0.5f);
	}

	@Override
	public void onInit(int status) {

		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.US);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
			} else {
				// speakOut();
			}

		} else {
			Log.e("TTS", "Initilization Failed!");
		}

	}

	
	public void speakOut(String text) {

		tts.speak(text, TextToSpeech.QUEUE_FLUSH, parameters);
	}

	public void playSilence(long time) {

		tts.playSilence(time, TextToSpeech.QUEUE_FLUSH, null);
	}

	public void turnOff() {
		// Don't forget to shutdown tts!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}

	}

	public TextToSpeech tts() {
		return tts;
	}

}
