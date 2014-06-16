package com.great.despertai.util;

import android.content.Context;

public class Weather {
	private String baseXml;

	static final public String CIDADE = "<nome>";
	static final public String ESTADO = "<uf>";
	static final public String DIA = "<dia>";
	static final public String TEMPO = "<tempo>";
	static final public String MAX_TEMPERATURA = "<maxima>";
	static final public String MIN_TEMPERATURA = "<minima>";
	static final public String RADIACAO_UV = "<iuv>";
	static final public String BR = "en_US";
	static final public String US = "pt_BR";
	private Speech speech;

	public Weather(Context context) {
		speech = new Speech(context);
	}

	public String getAtributo(String req) {
		int initIndex = baseXml.indexOf(req) + req.length();
		int endIndex = baseXml.indexOf(req.replace("<", "</"));
		String atributo = baseXml.substring(initIndex, endIndex);
		if (req.equals(TEMPO)) {
			atributo = parseWeather(atributo, US);
		}

		return atributo;
	}

	public void speechWeather() {

		speech.speakOut("Good morning. At" + getAtributo(Weather.CIDADE)
				+ "The Weather Forecast for today is:"
				+ getAtributo(Weather.TEMPO)+
				"Have a nice day!");

	}

	public void turnSpeechOff() {
		speech.turnOff();
	}

	private String parseWeather(String tempo, String language) {
		String weather = new String();
		if (language == BR) {
			
			if(tempo.equals("ec")){
				weather = "Encoberto com Chuvas Isoladas";
			} else if(tempo.equals("ci")){
				weather = "Chuvas Isoladas";
			}else if(tempo.equals("c")){
				weather = "Chuva";
			}else if(tempo.equals("in")){
				weather = "Instável";
			}else if(tempo.equals("pp")){
				weather = "Possibilidade de Pancadas de Chuva";
			}else if(tempo.equals("cm")){
				weather = "Chuva pela Manhã";
			}else if(tempo.equals("cn")){
				weather = "Chuva a Noite";
			}else if(tempo.equals("pt")){
				weather = "Pancadas de Chuva a Tarde";
			}else if(tempo.equals("pm")){
				weather = "Pancadas de Chuva pela Manhã";
			}else if(tempo.equals("np")){
				weather = "Nublado e Pancadas de Chuva";
			}else if(tempo.equals("pc")){
				weather = "Pancadas de Chuva";
			}else if(tempo.equals("pn")){
				weather = "Parcialmente Nublado";
			}else if(tempo.equals("cv")){
				weather = "Chuvisco";
			}else if(tempo.equals("ch")){
				weather = "Chuvoso";
			}else if(tempo.equals("t")){
				weather = "Tempestade";
			}else if(tempo.equals("ps")){
				weather = "Predomínio de Sol";
			}else if(tempo.equals("e")){
				weather = "Encoberto";
			}else if(tempo.equals("n")){
				weather = "Nublado";
			}else if(tempo.equals("cl")){
				weather = "Céu Claro";
			}else if(tempo.equals("nv")){
				weather = "Nevoeiro";
			}else if(tempo.equals("g")){
				weather = "Geada";
			}else if(tempo.equals("ne")){
				weather = "Neve";
			}else if(tempo.equals("nd")){
				weather = "Não Definido";
			}else if(tempo.equals("pnt")){
				weather = "Pancadas de Chuva a Noite";
			}else if(tempo.equals("psc")){
				weather = "Possibilidade de Chuva";
			}else if(tempo.equals("pcm")){
				weather = "Possibilidade de Chuva pela Manhã";
			}else if(tempo.equals("pct")){
				weather = "Possibilidade de Chuva a Tarde";
			}else if(tempo.equals("pcn")){
				weather = "Possibilidade de Chuva a Noite";
			}else if(tempo.equals("npt")){
				weather = "Nublado com Pancadas a Tarde";
			}else if(tempo.equals("npn")){
				weather = "Nublado com Pancadas a Noite";
			}else if(tempo.equals("ncn")){
				weather = "Nublado com Possibilidade de Chuva a Noite";
			}else if(tempo.equals("nct")){
				weather = "Nublado com Possibilidade de Chuva a Tarde";
			}else if(tempo.equals("ncm")){
				weather = "Nublado com Possibilidade de Chuva pela Manhã";
			}else if(tempo.equals("npp")){
				weather ="Nublado com Possibilidade de Chuva";
			}else if(tempo.equals("vn")){
				weather ="Variação de Nebulosidade";
			}else if(tempo.equals("ct")){
				weather = "Chuva a Tarde";
			}else if(tempo.equals("ppn")){
				weather = "Possibilidade de Pancada de Chuva a Noite";
			}else if(tempo.equals("ppt")){
				weather = "Possibilidade de Pancada de Chuva a Tarde";
			}else if(tempo.equals("ppm")){
				weather = "Possibilidade de Pancada de Chuva pela Manhã";
			}

		}

		/*else if (language == US) {
			switch (tempo) {
			case "ec":
				weather = "Mostly cloudy with Isolated Showers";
				break;
			case "ci":
				weather = "Isolated Showers";
				break;
			case "c":
				weather = "Rain";
				break;
			case "in":
				weather = "Unstable";
				break;
			case "pp":
				weather = "Possibilidade de Pancadas de Chuva";
				break;
			case "cm":
				weather = "Rain in the Morning";
				break;
			case "cn":
				weather = "Rain in the Night";
				break;
			case "pt":
				weather = "Scattered Showers in the Afternoon";
				break;
			case "pm":
				weather = "Scattered Showers in the Morning";
				break;
			case "np":
				weather = "Cloudy and Scattered Showers";
				break;
			case "pc":
				weather = "Scattered Showers";
				break;
			case "pn":
				weather = "Partly Cloudy";
				break;
			case "cv":
				weather = "Drizzle";
				break;
			case "ch":
				weather = "Rainy";
				break;
			case "t":
				weather = "Storm";
				break;
			case "ps":
				weather = "Sunny";
				break;
			case "e":
				weather = "Overcast";
				break;
			case "n":
				weather = "Cloudy";
				break;
			case "cl":
				weather = "Clear Sky";
				break;
			case "nv":
				weather = "Fog";
				break;
			case "g":
				weather = "Frost";
				break;
			case "ne":
				weather = "Snow";
				break;
			case "nd":
				weather = "Not Defined";
				break;
			case "pnt":
				weather = "Scattered Showers in the Evening";
				break;
			case "psc":
				weather = "Chance of Rain";
				break;
			case "pcm":
				weather = "Chance of Rain in the Morning";
				break;
			case "pct":
				weather = "Chance of Rain in the Afternoon";
				break;
			case "pcn":
				weather = "Chance of Rain in the Evening";
				break;
			case "npt":
				weather = "Cloudy with  Scattered Showers in the Afternoon";
				break;
			case "npn":
				weather = "Cloudy with  Scattered Showers in the Evening";
				break;
			case "ncn":
				weather = "Cloudy with Chance of Rain in the Evening";
				break;
			case "nct":
				weather = "Cloudy with Chance of Rain in the Afternoon";
				break;
			case "ncm":
				weather = "Cloudy with Chance of Rain in the Morning";
				break;
			case "npp":
				weather = "Cloudy with Chance of Rain";
				break;
			case "vn":
				weather = "Changes in Cloudiness";
				break;
			case "ct":
				weather = "Rain in the afternoon";
				break;
			case "ppn":
				weather = "Chance of Scattered Showers in the Evening";
				break;
			case "ppt":
				weather = "Chance of Scattered Showers in the Afternoon";
				break;
			case "ppm":
				weather = "Chance of Scattered Showers in the Morning";
				break;
			default:
				weather = "Not Defined";
				break;
			}
		}*/
		return weather;
	}
	
	public boolean setCity(String city){

//		URL url = new URL("http://servicos.cptec.inpe.br/XML/listaCidades?city="+city);
//		   HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//		   try {
//		     InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//		     readStream(in);
//		    finally {
//		     urlConnection.disconnect();
//		   }
//		 }
		return true;
	}

}
