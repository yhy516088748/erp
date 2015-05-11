package erp;

import static org.junit.Assert.*;

import org.junit.Test;

import com.kjq.erp.util.Mail;

public class mailTest {

	@Test
	public void test() {
		fail("Not yet implemented");
		// String smtp = "SMTP服务器";
		// String from = "发信人";
		// String to = "收信人";
		// String copyto = "抄送人";
		// String subject = "邮件主题";
		// String content = "邮件内容";
		// String username="用户名";
		// String password="密码";
		// String filename = "附件路径，如：F:\\test\\test.txt";
		String smtp = "smtp.ym.163.com";
		String from = "yanghongya@sh-tpa.com";
		String to = "516088748@qq.com";
		String copyto = "2636948474@qq.com";
		String subject = "testMail";
		String content = "邮件内容";
		String username = "yanghongya@sh-tpa.com";
		String password = "yhy87321";
		String filename = "d:\\test\\test.txt";

		Mail testMail = new Mail(smtp);
		testMail.createMimeMessage();
		testMail.sendAndCc(smtp, from, to, copyto, subject, content, username, password, filename);
		
	}

}
