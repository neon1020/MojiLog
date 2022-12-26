package moji;

import java.sql.Timestamp;

public class MojiDTO {
	private int num;
	private String subject;
	private String content;
	private Timestamp date;
	private int readcount;
	private String original_file;
	private String real_file;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public String getOriginal_file() {
		return original_file;
	}
	public void setOriginal_file(String original_file) {
		this.original_file = original_file;
	}
	public String getReal_file() {
		return real_file;
	}
	public void setReal_file(String real_file) {
		this.real_file = real_file;
	}
}
