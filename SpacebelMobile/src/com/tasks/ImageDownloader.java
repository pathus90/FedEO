package com.tasks;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.spacebelmobile.R;
import com.utils.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageDownloader { 
	/**
	 *  This method will download the image specified by the imageURL
	 *  and show it with the imageView
	 *  @param imageView The imageView which will show the image 
	 *                   that is hosted online.
	 *  @param imageURL the url of the image to be shown.
	 */
	public static Bitmap downloadAndShowImage(final String imageURL) 
	{
		Bitmap imageBitmap = null;
		try {
			Log.d("ImageDownloader", "About to do an HTTPRequest to: "
					+ imageURL);
			final HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is established.
			HttpConnectionParams.setConnectionTimeout(httpParameters, 7000);

			// Set the default socket timeout (SO_TIMEOUT)
			// in milliseconds which is the timeout for waiting for data.
			HttpConnectionParams.setSoTimeout(httpParameters, 10000);

			final HttpClient client = new DefaultHttpClient(httpParameters);
			final HttpResponse response = client.execute(new HttpGet(imageURL));
			//Getting the response entity.
			final HttpEntity entity = response.getEntity();
			//Getting the input stream of the imae content.
			final InputStream imageContentStream = entity.getContent();

			//Decoding the image input stream using the 
			//BitmapFactory and converting the InputStream into Bitmap.
			imageBitmap = 
					BitmapFactory.decodeStream(imageContentStream);
			//Attaching the imageBitmap with the imageView 
			//this step actually shows the downloaded image
		} catch (Exception e) {
			Log.e("ImageDownloader", 
					"Error while downloading the image: "
							+ e.getMessage(), e);
		}
		return imageBitmap;
	}
}