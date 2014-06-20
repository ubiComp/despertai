package com.great.despertai.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

import com.great.despertai.R;

public class Weather extends AsyncTask<String, Void, Boolean> implements
		OnInitListener {
	private String baseXml;

	private static final String TAG = "Loccam-Despertai";

	static final public String CIDADE = "<nome>";
	static final public String ESTADO = "<uf>";
	static final public String DIA = "<dia>";
	static final public String TEMPO = "<tempo>";
	static final public String MAX_TEMPERATURA = "<maxima>";
	static final public String MIN_TEMPERATURA = "<minima>";
	static final public String RADIACAO_UV = "<iuv>";
	static final public String BR = "pt_BR";
	static final public String US = "en_US";
	static final public String ID = "<id>";
	private String textWeather;
	SharedPreferences prefs = null;
	Context context;

	private TextToSpeech tts;

	public Weather(Context context) {
		this.context = context;
		tts = new TextToSpeech(context, this);
	}

	public String getAtributo(String req) {
		try {
			int initIndex = baseXml.indexOf(req) + req.length();
			int endIndex = baseXml.indexOf(req.replace("<", "</"));
			String atributo = baseXml.substring(initIndex, endIndex);
			if (req.equals(TEMPO)) {
				if (Locale.getDefault() == Locale.US)
					atributo = parseWeather(atributo, US);
				else
					atributo = parseWeather(atributo, BR);
			}

			return atributo;
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getAtributo(String baseXml, String req) {
		try {
			int initIndex = baseXml.indexOf(req) + req.length();
			int endIndex = baseXml.indexOf(req.replace("<", "</"));
			String atributo = baseXml.substring(initIndex, endIndex);
			if (req.equals(TEMPO)) {
				atributo = parseWeather(atributo, US);
			}

			return atributo;
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getText() {
		return textWeather;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		boolean ok;

		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.registerOnSharedPreferenceChangeListener(prefListener);

		String xml, code;
		if (params.length > 0) {
			// requisitando o código da cidade
			xml = getxml("http://servicos.cptec.inpe.br/XML/listaCidades?city="
					+ params[0]);
			code = this.getAtributo(xml, ID);
			Log.v(TAG, "id city: " + code);

			// requisitando a previsão do tempo
			xml = getxml("http://servicos.cptec.inpe.br/XML/cidade/" + code
					+ "/previsao.xml");
			baseXml = xml;
			// Log.v(TAG, "Previsão enfim: " + xml);
			setTextWeather();

			speakOut(textWeather);

			ok = true;
		} else
			ok = false;
		return ok;
	}

	private String getxml(String http) {
		URL url;
		HttpURLConnection urlConnection = null;

		// Log.v(TAG, "Weather: " + http);

		try {
			url = new URL(http);
			urlConnection = (HttpURLConnection) url.openConnection();
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder out = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			// Log.v(TAG, "Resultado clima tempo: " + out.toString());
			reader.close();

			return out.toString();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			urlConnection.disconnect();
		}

		return null;

	}

	// setup TTS
	@Override
	public void onInit(int initStatus) {

		// check for successful instantiation
		if (initStatus == TextToSpeech.SUCCESS) {
			if (tts.isLanguageAvailable(Locale.getDefault()) == TextToSpeech.LANG_AVAILABLE) {
				tts.setLanguage(Locale.getDefault());
				tts.setSpeechRate(0.8f);
			}
		} else if (initStatus == TextToSpeech.ERROR) {
			Log.e("TTS", "Initilization Failed!");
		}
	}

	public void speakOut(final String text) {
		try {
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private String parseWeather(String tempo, String language) {
		String weather = new String();
		if (language.equals(BR)) {

			if (tempo.equals("ec")) {
				weather = "Encoberto com Chuvas Isoladas";
			} else if (tempo.equals("ci")) {
				weather = "Chuvas Isoladas";
			} else if (tempo.equals("c")) {
				prefs = PreferenceManager.getDefaultSharedPreferences(context);
				prefs.registerOnSharedPreferenceChangeListener(prefListener);
				weather = "Chuva";
			} else if (tempo.equals("in")) {
				weather = "Instável";
			} else if (tempo.equals("pp")) {
				weather = "Possibilidade de Pancadas de Chuva";
			} else if (tempo.equals("cm")) {
				weather = "Chuva pela Manhã";
			} else if (tempo.equals("cn")) {
				weather = "Chuva a Noite";
			} else if (tempo.equals("pt")) {
				weather = "Pancadas de Chuva a Tarde";
			} else if (tempo.equals("pm")) {
				weather = "Pancadas de Chuva pela Manhã";
			} else if (tempo.equals("np")) {
				weather = "Nublado e Pancadas de Chuva";
			} else if (tempo.equals("pc")) {
				weather = "Pancadas de Chuva";
			} else if (tempo.equals("pn")) {
				weather = "Parcialmente Nublado";
			} else if (tempo.equals("cv")) {
				weather = "Chuvisco";
			} else if (tempo.equals("ch")) {
				weather = "Chuvoso";
			} else if (tempo.equals("t")) {
				weather = "Tempestade";
			} else if (tempo.equals("ps")) {
				weather = "Predomínio de Sol";
			} else if (tempo.equals("e")) {
				weather = "Encoberto";
			} else if (tempo.equals("n")) {
				weather = "Nublado";
			} else if (tempo.equals("cl")) {
				weather = "Céu Claro";
			} else if (tempo.equals("nv")) {
				weather = "Nevoeiro";
			} else if (tempo.equals("g")) {
				weather = "Geada";
			} else if (tempo.equals("ne")) {
				weather = "Neve";
			} else if (tempo.equals("nd")) {
				weather = "Não Definido";
			} else if (tempo.equals("pnt")) {
				weather = "Pancadas de Chuva a Noite";
			} else if (tempo.equals("psc")) {
				weather = "Possibilidade de Chuva";
			} else if (tempo.equals("pcm")) {
				weather = "Possibilidade de Chuva pela Manhã";
			} else if (tempo.equals("pct")) {
				weather = "Possibilidade de Chuva a Tarde";
			} else if (tempo.equals("pcn")) {
				weather = "Possibilidade de Chuva a Noite";
			} else if (tempo.equals("npt")) {
				weather = "Nublado com Pancadas a Tarde";
			} else if (tempo.equals("npn")) {
				weather = "Nublado com Pancadas a Noite";
			} else if (tempo.equals("ncn")) {
				weather = "Nublado com Possibilidade de Chuva a Noite";
			} else if (tempo.equals("nct")) {
				weather = "Nublado com Possibilidade de Chuva a Tarde";
			} else if (tempo.equals("ncm")) {
				weather = "Nublado com Possibilidade de Chuva pela Manhã";
			} else if (tempo.equals("npp")) {
				weather = "Nublado com Possibilidade de Chuva";
			} else if (tempo.equals("vn")) {
				weather = "Variação de Nebulosidade";
			} else if (tempo.equals("ct")) {
				weather = "Chuva a Tarde";
			} else if (tempo.equals("ppn")) {
				weather = "Possibilidade de Pancada de Chuva a Noite";
			} else if (tempo.equals("ppt")) {
				weather = "Possibilidade de Pancada de Chuva a Tarde";
			} else if (tempo.equals("ppm")) {
				weather = "Possibilidade de Pancada de Chuva pela Manhã";
			}

		}

		else if (language.equals(US)) {
			if (tempo.equals("ec")) {
				weather = "Mostly cloudy with Isolated Showers";
			} else if (tempo.equals("ci")) {
				weather = "Isolated Showers";
			} else if (tempo.equals("c")) {
				weather = "Rain";
			} else if (tempo.equals("in")) {
				weather = "Unstable";
			} else if (tempo.equals("pp")) {
				weather = "Possible Scattered Showers";
			} else if (tempo.equals("cm")) {
				weather = "Rain in the Morning";
			} else if (tempo.equals("cn")) {
				weather = "Rain in the Night";
			} else if (tempo.equals("pt")) {
				weather = "Scattered Showers in the Afternoon";
			} else if (tempo.equals("pm")) {
				weather = "Scattered Showers in the Morning";
			} else if (tempo.equals("np")) {
				weather = "Cloudy and Scattered Showers";
			} else if (tempo.equals("pc")) {
				weather = "Scattered Showers";
			} else if (tempo.equals("pn")) {
				weather = "Partly Cloudy";
			} else if (tempo.equals("cv")) {
				weather = "Drizzle";
			} else if (tempo.equals("ch")) {
				weather = "Rainy";
			} else if (tempo.equals("t")) {
				weather = "Storm";
			} else if (tempo.equals("ps")) {
				weather = "Sunny";
			} else if (tempo.equals("e")) {
				weather = "Overcast";
			} else if (tempo.equals("n")) {
				weather = "Cloudy";
			} else if (tempo.equals("cl")) {
				weather = "Clear Sky";
			} else if (tempo.equals("nv")) {
				weather = "Fog";
			} else if (tempo.equals("g")) {
				weather = "Frost";
			} else if (tempo.equals("ne")) {
				weather = "Snow";
			} else if (tempo.equals("nd")) {
				weather = "Not Defined";
			} else if (tempo.equals("pnt")) {
				weather = "Scattered Showers in the Evening";
			} else if (tempo.equals("psc")) {
				weather = "Chance of Rain";
			} else if (tempo.equals("pcm")) {
				weather = "Chance of Rain in the Morning";
			} else if (tempo.equals("pct")) {
				weather = "Chance of Rain in the Afternoon";
			} else if (tempo.equals("pcn")) {
				weather = "Chance of Rain in the Evening";
			} else if (tempo.equals("npt")) {
				weather = "Cloudy with  Scattered Showers in the Afternoon";
			} else if (tempo.equals("npn")) {
				weather = "Cloudy with  Scattered Showers in the Evening";
			} else if (tempo.equals("ncn")) {
				weather = "Cloudy with Chance of Rain in the Evening";
			} else if (tempo.equals("nct")) {
				weather = "Cloudy with Chance of Rain in the Afternoon";
			} else if (tempo.equals("ncm")) {
				weather = "Cloudy with Chance of Rain in the Morning";
			} else if (tempo.equals("npp")) {
				weather = "Cloudy with Chance of Rain";
			} else if (tempo.equals("vn")) {
				weather = "Changes in Cloudiness";
			} else if (tempo.equals("ct")) {
				weather = "Rain in the afternoon";
			} else if (tempo.equals("ppn")) {
				weather = "Chance of Scattered Showers in the Evening";
			} else if (tempo.equals("ppt")) {
				weather = "Chance of Scattered Showers in the Afternoon";
			} else if (tempo.equals("ppm")) {
				weather = "Chance of Scattered Showers in the Morning";
			} else {
				weather = "Not Defined";
			}
		}

		return weather;
	}

	private void setTextWeather() {

		Set<String> selections = prefs.getStringSet(context.getResources()
				.getString(R.string.settings_forecast), null);
		String[] selected = selections.toArray(new String[] {});

		// english = 2 is default
		String language = prefs.getString(
				context.getResources().getString(R.string.settings_language),
				"2");

		textWeather = period(language);

		// if English
		// if (Locale.getDefault() == Locale.US)
		if (language.equals("2")) {
			textWeather = textWeather + ". At " + getAtributo(Weather.CIDADE);
			if (lookForString(selected, "data"))
				textWeather = textWeather + ". Today is "
						+ getAtributo(Weather.DIA).substring(8) + ".";
			if (lookForString(selected, "clima"))
				textWeather = textWeather
						+ "The forecast weather for tomorrow is "
						+ getAtributo(Weather.TEMPO) + ".";
			if (lookForString(selected, "tmax"))
				textWeather = textWeather + "The maximum temperature will be"
						+ getAtributo(Weather.MAX_TEMPERATURA) + ".";
			if (lookForString(selected, "tmin"))
				textWeather = textWeather + "The minimum temperature will be"
						+ getAtributo(Weather.MIN_TEMPERATURA) + ".";
			if (lookForString(selected, "iuv"))
				textWeather = textWeather + "Ultraviolet radiation will be "
						+ getAtributo(Weather.RADIACAO_UV) + ".";

			textWeather = textWeather + "Have a nice day!";
		}

		// if Portuguese
		// else if (Locale.getDefault().toString().equals(BR))

		if (language.equals("1")) {
			textWeather = textWeather + ". Em " + getAtributo(Weather.CIDADE);
			if (lookForString(selected, "data"))
				textWeather = textWeather + ". Hoje é "
						+ getAtributo(Weather.DIA).substring(8) + ".";
			if (lookForString(selected, "clima"))
				textWeather = textWeather + " O clima previsto para amanhã é "
						+ getAtributo(Weather.TEMPO) + ".";
			if (lookForString(selected, "tmax"))
				textWeather = textWeather + " A temperatura máxima será "
						+ getAtributo(Weather.MAX_TEMPERATURA) + ".";
			if (lookForString(selected, "tmin"))
				textWeather = textWeather + " A temperatura mínima será "
						+ getAtributo(Weather.MIN_TEMPERATURA) + ".";
			if (lookForString(selected, "iuv"))
				textWeather = textWeather + " A radiação ultravioleta será "
						+ getAtributo(Weather.RADIACAO_UV) + ".";

			textWeather = textWeather + "Tenha um bom dia!";

		}

		Log.v(TAG, textWeather);
	}

	private boolean lookForString(String[] selections, String s) {
		for (int i = 0; i < selections.length; i++) {
			if (selections[i].equals(s))
				return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	private String period(String language) {
		Calendar c = Calendar.getInstance();

		if (language.equals("1")) {
			if (c.getTime().getHours() > 3 && c.getTime().getHours() < 12)
				return "Bom dia";
			else if (c.getTime().getHours() > 12 && c.getTime().getHours() < 18)
				return "Boa tarde";
			else
				return "Boa noite";
		} else {
			if (c.getTime().getHours() > 3 && c.getTime().getHours() < 12)
				return "Good Morning";
			else if (c.getTime().getHours() > 12 && c.getTime().getHours() < 18)
				return "Good Afternoon";
			else
				return "Good Evening";
		}

	}

	private OnSharedPreferenceChangeListener prefListener = new OnSharedPreferenceChangeListener() {
		
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			if (key.equals(context.getResources().getString(R.string.settings_forecast))) {
				setTextWeather();
				Log.v("Alarm-Despertai", "Mudaram as opções de previsão.");
			}
		}
	};

}
