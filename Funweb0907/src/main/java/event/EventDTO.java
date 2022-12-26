package event;

import java.sql.Date;
import java.sql.Timestamp;

public class EventDTO {
	private int num;
	private String subject;
	private String content;
	private Timestamp start_date;
	private Timestamp end_date;
	private String link;
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
	public Timestamp getStart_date() {
		return start_date;
	}
	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}
	public Timestamp getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Timestamp end_date) {
		this.end_date = end_date;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
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
