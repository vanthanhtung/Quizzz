package md6.quizzz.service.appUserService;

import md6.quizzz.model.AppUser;
import md6.quizzz.model.Record;
import md6.quizzz.service.GeneralService;

import java.util.Optional;

public interface AppUserService extends GeneralService<AppUser> {
    Optional<AppUser> findByUsername(String name);
}
