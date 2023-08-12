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
}
