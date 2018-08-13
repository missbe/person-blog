import cn.missbe.missbe_www.util.MD5Util;
import org.junit.Test;

/**
 * Created by Administrator on 2016/12/30 0030.
 */
public class Md5Test {
	@Test
	public void md5Test(){
		System.out.println(MD5Util.string2MD5("123456"));
	}
}
