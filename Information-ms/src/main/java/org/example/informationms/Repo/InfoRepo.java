package org.example.informationms.Repo;

import org.example.informationms.Enum.InfoType;
import org.example.informationms.Model.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InfoRepo extends JpaRepository<Info,Long> {

    Optional<Info> getInfoByType(InfoType type);


    Optional<Info> findByType(InfoType type);
}
