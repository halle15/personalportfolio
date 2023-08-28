package mmd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mmd.models.Settings;
import mmd.repositories.SettingsRepository;

@Service
public class SettingsService {
	
	@Autowired
	SettingsRepository settingsRepository;
	
	public boolean getEmailStatus() {
		if(settingsRepository.findById(1L) == null) {
			System.out.println("Setting settings...");
			settingsRepository.save(new Settings(1L, false, ""));
			return false;
		}
		else {
			return settingsRepository.findById(1L).isEnabled();
		}
	}
	
	public boolean setEmailStatus(boolean status) {
		if(settingsRepository.findById(1L) == null) {
			System.out.println("Setting settings...");
			settingsRepository.save(new Settings(1L, status, ""));
			return status;
		}
		else {
			Settings s = settingsRepository.findById(1L);
			s.setEnabled(status);
			settingsRepository.save(s);
			return status;
		}
	}
	
	public boolean setEmailTarget(String target) {
		if(settingsRepository.findById(1L) == null) {
			System.out.println("Setting settings...");
			settingsRepository.save(new Settings(1L, false, target));
			return true;
		}
		else {
			Settings s = settingsRepository.findById(1L);
			s.setEmailTarget(target);
			settingsRepository.save(s);
			return true;
		}
		
	}
}
