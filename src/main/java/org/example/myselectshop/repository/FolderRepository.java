package org.example.myselectshop.repository;

import org.example.myselectshop.entity.Folder;
import org.example.myselectshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    List<Folder> findAllByUser(User user);

    List<Folder> findAllByUserAndNameIn(User user, Collection<String> names);
    // select * from folder where user_id = ? and name in ()
}
