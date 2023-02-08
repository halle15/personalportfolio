package mmd.personalportfolio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// sample post request curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"message\":\"This is a sample message.\"}" http://localhost:8080/messages


@Entity
public class Message {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	String name;
	String email;
	String message;
	
	
	protected Message() {}
	@Override
	public String toString() {
		return "Message [id=" + id + ", name=" + name + ", email=" + email + ", message=" + message + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Message(String name, String email, String message) {
		super();
		this.name = name;
		this.email = email;
		this.message = message;
	}
	
}
