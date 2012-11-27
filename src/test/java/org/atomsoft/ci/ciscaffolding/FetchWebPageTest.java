package org.atomsoft.ci.ciscaffolding;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class FetchWebPageTest {

	static final Log logger = LogFactory.getLog(FetchWebPageTest.class);
	/**
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public static final HttpClient httpclient = new DefaultHttpClient();
	
	String host = "";
    String pagePrefix = "thread-htm-fid-47-page-";
	@Test
	public void findLinks() throws Exception {
		
		
		for (int i = 1; i < 107; i++) {
			String pageIndex = pagePrefix + i + ".html";			
			new FetchLink(pageIndex).call();
			Thread.sleep(1);
			
		}
		
	}

	final static Map<String, File> dfiles = Maps.newHashMap();

	class FetchLink implements Callable<String> {

		private String url;

		public FetchLink(String url) {
			super();
			this.url = url;
		}

		@Override
		public String call() throws Exception {
			fetchLineLink(url);
			return null;
		}

		void fetchLineLink(String thread) throws ClassNotFoundException,
				InstantiationException, IllegalAccessException, IOException,
				InterruptedException {

			String page = host +"/"+ thread;
			HttpGet httpget =setupRequest(url);
			logger.info("正在抓取页面链接:" + page);
			
			HttpResponse response = httpclient.execute(httpget);

			HttpEntity entity = response.getEntity();

			if (entity != null) {

				try (InputStream instream = entity.getContent()) {
				   
				    GZIPInputStream gzin = new GZIPInputStream(instream);
					InputStreamReader isr = new InputStreamReader(gzin, "UTF-8");
					BufferedReader reader1 = new BufferedReader(isr);

					StringBuilder sb = new StringBuilder();
					String s = null;
					while ((s = reader1.readLine()) != null) {
						sb.append(s).append(
								System.getProperty("line.separator"));
					}
					httpget.releaseConnection();
					isr.close();
					gzin.close();

					Document doc = Jsoup.parse(sb.toString());

					Elements tables = doc.select("table#ajaxtable");
					Element table = tables.first();
					Elements links = table.select("a.subject");
					Map<String, String> acnts = new HashMap<String, String>();
					for (Iterator<Element> itr = links.iterator(); itr
							.hasNext();) {
						Element a = itr.next();
						Elements bs = a.children();
						if (bs.size() > 0) {
							Element font = bs.first();
							if ("font".equalsIgnoreCase(font.tagName())) {
								if (!"#FF0000".equalsIgnoreCase(font
										.attr("color"))) {
									acnts.put(a.attr("href"), font.text());
								}
							}
						} else {
							acnts.put(a.attr("href"), a.text());
						}
					}

					for (Map.Entry<String, String> entry : acnts.entrySet()) {
						readTopic(entry.getKey(), entry.getValue());
						Thread.sleep(500);
					}

				}

			}
		}

		void readTopic(String url, String name) throws ClientProtocolException,
				IOException {
			HttpGet httpget =setupRequest(url);
			HttpResponse response = httpclient.execute(httpget);

			HttpEntity entity = response.getEntity();
			if (entity != null) {

				try (InputStream instream = entity.getContent()) {
					GZIPInputStream gzin = new GZIPInputStream(instream);
					InputStreamReader isr = new InputStreamReader(gzin, "UTF-8");
					BufferedReader reader1 = new BufferedReader(isr);

					StringBuilder sb = new StringBuilder();
					String s = null;
					while ((s = reader1.readLine()) != null) {
						sb.append(s).append(
								System.getProperty("line.separator"));
						;
					}
					httpget.releaseConnection();
					isr.close();
					gzin.close();

					Document doc = Jsoup.parse(sb.toString());
					Elements cdiv = doc.select("div#read_tpc");
					Element cnt = cdiv.first();
					if (cnt != null) {
						Elements spans = cnt.select("span");
						for (Iterator<Element> itr = spans.iterator(); itr
								.hasNext();) {
							Element span = itr.next();
							Node ntx = new TextNode(span.ownText()
									+ "\r\n\r\n\r\n", "");
							span.replaceWith(ntx);
						}
						Elements brs = cnt.select("br");
						for (Iterator<Element> itr = brs.iterator(); itr
								.hasNext();) {
							Element br = itr.next();
							br.replaceWith(new TextNode("\r\n\r\n\r\n", ""));
						}

						name = name.replaceAll("[ \\\\\\/\\:\\*\\?\\\"\\<\\>\\|]", "");
						File txtFile = new File("j:/apps/test/" + name + ".txt");
						logger.info("保存文件:" + txtFile.getAbsolutePath());
						
						FileUtils.write(txtFile, cnt.ownText());
						
						// dfiles.put(name, txtFile);
						// logger.info("以保存文件 :"+dfiles.size());
					}

				}
			}
		}

	}
	
	HttpGet setupRequest(String url){
		HttpGet httpget = new HttpGet(host + url);
		httpget.addHeader(new BasicHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		httpget.addHeader(new BasicHeader("Accept-Encoding","gzip, deflate"));
		httpget.addHeader(new BasicHeader("Accept-Language",
				"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3"));
		httpget.addHeader(new BasicHeader("Cache-Control", "max-age=0"));
		httpget.addHeader(new BasicHeader("Connection", "keep-alive"));
		httpget.addHeader(new BasicHeader(
				"Cookie",
				"02b84_lastvisit=14%091354021682%09%2Fread.php%3Ftid-3104579-search-1000-orderway-postdate-asc-DESC.html; 02b84_lastpos=T3104579; __utma=266886620.1563735775.1354021670.1354021670.1354021670.1; __utmb=266886620.2.10.1354021670; __utmc=266886620; __utmz=266886620.1354021670.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); 02b84_readlog=%2C3104579%2C"));
		httpget.addHeader(new BasicHeader("Host", "cnbbs6.com"));
		httpget.addHeader(new BasicHeader("Referer",host+"/"+url));
		httpget.addHeader(new BasicHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.2; WOW64; rv:17.0) Gecko/17.0 Firefox/17.0 FirePHP/0.7.1"));
		httpget.addHeader(new BasicHeader("x-insight", "activate"));
		return httpget;
	}
}
