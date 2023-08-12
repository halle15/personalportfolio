package mmd.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import mmd.models.Settings;

public interface SettingsRepository extends CrudRepository<Settings, Long>{
	Settings findById(long ID);
}
