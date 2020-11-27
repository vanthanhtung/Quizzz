package md6.quizzz.repository;

import md6.quizzz.model.ERole;
import md6.quizzz.model.User_Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<User_Role, Long> {
    Optional<User_Role> findByName(ERole name);

}
