package com.baidu.ueditor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.ueditor.define.ActionMap;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置管理器
 * @author hancong03@baidu.com
 *
 */
public final class ConfigManager {

	private final String rootPath;
	private static final String configFileName = "ueditor-config.json";
    public static JSONObject jsonConfig = null;
    // 涂鸦上传filename定义
    private final static String SCRAWL_FILE_NAME = "scrawl";
	// 远程图片抓取filename定义
	private final static String REMOTE_FILE_NAME = "remote";
	
	/*
	 * 通过一个给定的路径构建一个配置管理器， 该管理器要求地址路径所在目录下必须存在config.properties文件
	 */
    private ConfigManager(String rootPath) throws IOException {

        rootPath = rootPath.replace("\\", "/");

        this.rootPath = rootPath;
		
		this.initEnv();
		
	}
	
	/**
	 * 配置管理器构造工厂
	 * @param rootPath 服务器根路径
	 * @return 配置管理器实例或者null
	 */
    public static ConfigManager getInstance(String rootPath) {

        try {
            return new ConfigManager(rootPath);
        } catch (Exception e) {
            return null;
		}
		
	}
	
	// 验证配置文件加载是否正确
	public boolean valid () {
        return jsonConfig != null;
    }

    public JSONObject getAllConfig () {

        return jsonConfig;

    }

    public Map<String, Object> getConfig ( int type ) {
		
		Map<String, Object> conf = new HashMap<>();
		String savePath = null;
		
		switch ( type ) {
		
			case ActionMap.UPLOAD_FILE:
				conf.put( "isBase64", "false" );
                conf.put("maxSize", jsonConfig.getLong("fileMaxSize"));
                conf.put("allowFiles", this.getArray("fileAllowFiles"));
                conf.put("fieldName", jsonConfig.getString("fileFieldName"));
                savePath = jsonConfig.getString("filePathFormat");
                break;

            case ActionMap.UPLOAD_IMAGE:
				conf.put( "isBase64", "false" );
                conf.put("maxSize", jsonConfig.getLong("imageMaxSize"));
                conf.put("allowFiles", this.getArray("imageAllowFiles"));
                conf.put("fieldName", jsonConfig.getString("imageFieldName"));
                savePath = jsonConfig.getString("imagePathFormat");
                break;

            case ActionMap.UPLOAD_VIDEO:
                conf.put("maxSize", jsonConfig.getLong("videoMaxSize"));
                conf.put("allowFiles", this.getArray("videoAllowFiles"));
                conf.put("fieldName", jsonConfig.getString("videoFieldName"));
                savePath = jsonConfig.getString("videoPathFormat");
                break;

            case ActionMap.UPLOAD_SCRAWL:
				conf.put( "filename", SCRAWL_FILE_NAME );
                conf.put("maxSize", jsonConfig.getLong("scrawlMaxSize"));
                conf.put("fieldName", jsonConfig.getString("scrawlFieldName"));
                conf.put("isBase64", "true");
                savePath = jsonConfig.getString("scrawlPathFormat");
                break;

            case ActionMap.CATCH_IMAGE:
				conf.put( "filename", REMOTE_FILE_NAME );
				conf.put( "filter", this.getArray( "catcherLocalDomain" ) );
                conf.put("maxSize", jsonConfig.getLong("catcherMaxSize"));
                conf.put("allowFiles", this.getArray("catcherAllowFiles"));
                conf.put("fieldName", jsonConfig.getString("catcherFieldName") + "[]");
                savePath = jsonConfig.getString("catcherPathFormat");
                break;

            case ActionMap.LIST_IMAGE:
				conf.put( "allowFiles", this.getArray( "imageManagerAllowFiles" ) );
                conf.put("dir", jsonConfig.getString("imageManagerListPath"));
                int count = Integer.valueOf(jsonConfig.getString("imageManagerListSize"));
                conf.put("count", count);
                break;

			case ActionMap.LIST_FILE:
				conf.put( "allowFiles", this.getArray( "fileManagerAllowFiles" ) );
                conf.put("dir", jsonConfig.getString("fileManagerListPath"));
                int count2 = Integer.valueOf(jsonConfig.getString("fileManagerListSize"));
                conf.put("count", count2);
                break;
				
		}
		
		conf.put( "savePath", savePath );
		conf.put( "rootPath", this.rootPath );
		
		return conf;
		
	}
	
	private void initEnv () throws IOException {

        String configContent = this.readFile( this.getConfigPath() );


		try{
            jsonConfig = JSON.parseObject(configContent);
        } catch (Exception e) {
            jsonConfig = null;
        }

    }
	
	private String getConfigPath () {
        URL res = this.getClass().getClassLoader().getResource("");
        String classPath = res != null ? res.getPath() : "";
        return classPath + configFileName;
    }

	private String[] getArray ( String key ) {

        JSONArray jsonArray = jsonConfig.getJSONArray(key);
        String[] result = new String[jsonArray.size()];

        for ( int i = 0, len = jsonArray.size(); i < len; i++ ) {
			result[i] = jsonArray.getString( i );
		}
		
		return result;
		
	}
	
	private String readFile ( String path ) throws IOException {
		
		StringBuilder builder = new StringBuilder();
		
		try {
			
			InputStreamReader reader = new InputStreamReader( new FileInputStream( path ), "UTF-8" );
			BufferedReader bfReader = new BufferedReader( reader );
			
			String tmpContent;
			
			while ( ( tmpContent = bfReader.readLine() ) != null ) {
				builder.append( tmpContent );
			}
			
			bfReader.close();
			
		} catch ( UnsupportedEncodingException e ) {
			// 忽略
		}
		
		return this.filter( builder.toString() );
		
	}
	
	// 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
	private String filter ( String input ) {
		
		return input.replaceAll( "/\\*[\\s\\S]*?\\*/", "" );
		
	}
	
}
