package md6.quizzz.repository;

import md6.quizzz.model.App_User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends CrudRepository<App_User,Long> {
}
