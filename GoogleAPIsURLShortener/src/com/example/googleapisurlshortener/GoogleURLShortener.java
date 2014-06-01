package com.example.googleapisurlshortener;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GoogleURLShortener {
	final static AsyncHttpClient client = new AsyncHttpClient();

	private final static String apiUrl = "https://www.googleapis.com/urlshortener/v1/url";
	private static String shortURL;

	public static void attachToEditText(final EditText editText) {
		editText.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_SPACE:

					String[] wordler = editText.getText().toString().split(" ");
					for (String word : wordler) {
						if (word.startsWith("http"))
							if (!word.contains("//goo.gl/"))
								GoogleURLShortener.getShortURL(word, editText);
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

	public static final String getShortURL(final String url,
			final EditText editText) {
		try {

			client.post(null, apiUrl,

			new StringEntity("{\"longUrl\": \"" + url + "\"}")

			, "application/json",

			new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject responseJsonObject) {
					try {
						shortURL = responseJsonObject.getString("id");
						editText.setEnabled(false);
						editText.setText(editText.getText().toString()
								.replaceAll(url, shortURL));
						editText.setSelection(editText.getText().toString()
								.length());
						editText.setEnabled(true);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(responseJsonObject.toString());
				}

			}

			);

			// client.set

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
}
