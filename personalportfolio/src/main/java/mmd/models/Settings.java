package mmd.models;

import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;



@Entity
public class Settings {
	
	@Id
	public long id;
	public boolean isEnabled;
	public String emailTarget;
	
	public Settings() {
		super();
		this.id = 1L;
		this.isEnabled = false;
		this.emailTarget = "";
	}
	
	public Settings(long id, boolean isEnabled, String emailTarget) {
		super();
		this.id = id;
		this.isEnabled = isEnabled;
		this.emailTarget = emailTarget;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getEmailTarget() {
		return emailTarget;
	}
	public void setEmailTarget(String emailTarget) {
		this.emailTarget = emailTarget;
	}
	
}
