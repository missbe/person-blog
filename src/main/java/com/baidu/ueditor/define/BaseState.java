package com.baidu.ueditor.define;

import java.util.HashMap;
import java.util.Map;

import com.baidu.ueditor.Encoder;

public class BaseState implements State {

	private boolean state = false;
	private String info = null;
	
	private Map<String, String> infoMap = new HashMap<>();
	
	public BaseState () {
		this.state = true;
	}
	
	public BaseState ( boolean state ) {
		this.setState( state );
	}
	
	public BaseState ( boolean state, String info ) {
		this.setState( state );
		this.info = info;
	}
	
	public BaseState ( boolean state, int infoCode ) {
		this.setState( state );
		this.info = AppInfo.getStateInfo( infoCode );
	}
	
	public boolean isSuccess () {
		return this.state;
	}
	
	public void setState ( boolean state ) {
		this.state = state;
	}

	public boolean getState() { return state; }
	
	public void setInfo ( String info ) {
		this.info = info;
	}

	public String getInfo () { return info;  }

	public String getInfo(String key) {
		return infoMap.get(key);
	}

	public void setInfo ( int infoCode ) {
		this.info = AppInfo.getStateInfo( infoCode );
	}
	
	@Override
	public String toJSONString() {
		return this.toString();
	}
	
	public String toString () {
		
		String key;
		String stateVal = this.isSuccess() ? AppInfo.getStateInfo( AppInfo.SUCCESS ) : this.info;
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("{\"state\": \"").append(stateVal).append("\"");

		for (String s : this.infoMap.keySet()) {

			key = s;

			builder.append(",\"").append(key).append("\": \"").append(this.infoMap.get(key)).append("\"");

		}
		
		builder.append( "}" );

		return Encoder.toUnicode( builder.toString() );

	}

	@Override
	public void putInfo(String name, String val) {
		this.infoMap.put(name, val);
	}

	@Override
	public void putInfo(String name, long val) {
		this.putInfo(name, val+"");
	}

}
