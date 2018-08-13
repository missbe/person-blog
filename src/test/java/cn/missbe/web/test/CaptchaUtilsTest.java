package cn.missbe.web.test;

import cn.missbe.missbe_www.util.CaptchaUtils;
import org.junit.Test;

/**
 * Created by missbe_www on 2017/7/6 0006.
 */
public class CaptchaUtilsTest {
	@Test
	public void testFile(){
		CaptchaUtils captchaUtils=new CaptchaUtils();
		System.out.println(captchaUtils.getWords());
	}
}
