package helper;

import java.util.Map;
import org.json.JSONObject;
import android.content.Context;
import android.os.AsyncTask;

public abstract class BaseAsyncTask extends AsyncTask<String, String, Double> {
	private String url;
	private Map<String,Object> maps;
	private HttpType type;
	private BaseHttpHelper httphelper=null; 
	private Context context;
	private Map<String,Object> param;
	private String model; 
	private JSONObject jsonObj;
	public BaseAsyncTask(String url,Map<String,Object> param,HttpType type,String model,Context context){
		this.url=url;
		this.maps=param;
		this.type=type;
		this.context = context;
		httphelper=new BaseHttpHelper(this.context);
		this.param=param;
		this.model=model; 
	}
	@Override
	protected Double doInBackground(String... params) {
 
		String res="";
		if(type==HttpType.Get)
		{
			res=httphelper.GetDataByClient(url, param);
		}else if(type==HttpType.Post){
			res=httphelper.PostDataByClient(url, param, model);
		}
		publishProgress(res);
		return null;
	}
	
	@Override
	protected void onPostExecute(Double result) {
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(String... values) {
		if(values!=null&&values.length>0){
			handler(values[0]);
		}else{
			handler("");
		} 
	}
	public abstract void handler(String param) throws RuntimeException;
	public enum HttpType{
		Get,Post
	}
}
