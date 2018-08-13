package cn.missbe.missbe_www.util;

import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * @author missbe
 * @date 16/9/10 01:48
 */
public class CaptchaUtils {
	//使用集合存储成语
	private static List<String> words = new ArrayList<String>();
	private static String captcha;

	static {
		// getServletContext().getRealPath(path)获取指定路径的磁盘的完整路径
		String fileName="new_words.txt";
		ClassPathResource cp = new ClassPathResource(fileName);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(cp.getInputStream(), "UTF-8"));
			String line;
			//文件中的一个成语占一行
			while ((line = reader.readLine()) != null) {
				words.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getWords(){
		return words;
	}
	public static String getCaptcha(){
		return captcha;
	}

	public static ByteArrayOutputStream genrateCaptcha() throws IOException {
		int width = 120;
		int height = 30;

		//创建图片绘制对象，参数1:宽度，参数2：高度 ，参数3：三元基色
		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		//得到画笔对象
		Graphics graphics = bufferedImage.getGraphics();

		//设置画笔颜色，getRandColor():随机颜色
		graphics.setColor(getRandColor(200, 250));
		//绘制验证码的背景
		graphics.fillRect(0, 0, width, height);

		//设置画笔颜色
		graphics.setColor(Color.BLUE);
		//绘制验证码的边框
		graphics.drawRect(0, 0, width - 1, height - 1);


		Graphics2D graphics2d = (Graphics2D) graphics;

		//绘制文字(成语)
		graphics2d.setFont(new Font("宋体", Font.BOLD, 18));

		//生成随机对象
		Random random = new Random();
		int index = random.nextInt(words.size());
		//根据随机值获取成语
		String word = words.get(index);

		//x坐标
		int x = 10;
		for (int i = 0; i < word.length(); i++) {

			graphics2d.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));

			int jiaodu = random.nextInt(60) - 30;

			double theta = jiaodu * Math.PI / 180;


			char c = word.charAt(i);

			//按照指定角度绘制
			graphics2d.rotate(theta, x, 20);
			graphics2d.drawString(String.valueOf(c), x, 20);
			graphics2d.rotate(-theta, x, 20);
			x += 30;
		}

		CaptchaUtils.captcha=word;////保存验证码

		graphics.setColor(getRandColor(160, 200));
		int x1;
		int x2;
		int y1;
		int y2;
		for (int i = 0; i < 30; i++) {
			x1 = random.nextInt(width);
			x2 = random.nextInt(12);
			y1 = random.nextInt(height);
			y2 = random.nextInt(12);
			//绘制干扰线
			graphics.drawLine(x1, y1, x1 + x2, x2 + y2);
		}

		//释放资源
		graphics.dispose();
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		//把绘制的内容输出到页面上，把绘制的内容输出到响应流中
		ImageIO.write(bufferedImage, "jpg", jpegOutputStream);
		return jpegOutputStream;
	}
	/**
	 * 取其某一范围的color
	 *
	 * @param fc
	 *            int 范围参数1
	 * @param bc
	 *            int 范围参数2
	 * @return Color
	 */
	private static Color getRandColor(int fc, int bc) {
		// 取其随机颜色
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
