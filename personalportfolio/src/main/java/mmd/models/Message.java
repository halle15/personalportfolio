package mmd.models;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// sample post request curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"John Doe\",\"contactInfo\":\"john.doe@example.com\",\"message\":\"This is a sample message.\"}" http://localhost:8080/messages

@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;
	String name;
	String contactInfo;
	String message;
	public boolean isRead;

	@DateTimeFormat
	Date date;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	protected Message() {
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", name=" + name + ", contactInfo=" + contactInfo + ", message=" + message + ", date=" + date
				+ "]";
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String email) {
		this.contactInfo = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}
	
	public String getFormattedDate() {
		String formattedDate;
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy kk:mm:ss");
		
		formattedDate = simpleDateFormat.format(getDate());
		
		return formattedDate;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public boolean getIsRead() {
		return isRead;
	}
	
	public void setRead(boolean status) {
		this.isRead = status;
	}

	public Message(String name, String contactInfo, String message) {
		super();
		this.name = name;
		this.contactInfo = contactInfo;
		this.message = message;
		this.date = Date.from(Instant.now());
		this.isRead = false;
	}

}
