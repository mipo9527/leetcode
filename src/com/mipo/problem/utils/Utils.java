package  com.mipo.problem.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Utils {
	static Gson gson = new Gson();
	public static int[][] to2DArray(String str){
		TypeToken<?> typeToken = TypeToken.getArray(int[].class);
		int[][] res =  gson.fromJson(str, typeToken.getType());
		return res;
	}
	
	public static String toJson(Object obj){
		return gson.toJson(obj);
	}
}
