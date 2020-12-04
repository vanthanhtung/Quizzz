package md6.quizzz.service.appUserService;

import md6.quizzz.model.AppUser;
import md6.quizzz.model.ERole;
import md6.quizzz.model.Record;
import md6.quizzz.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public Iterable<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        return appUserRepository.findById(id);
    }

    @Override
    public AppUser save(AppUser app_user) {
        return appUserRepository.save(app_user);
    }

    @Override
    public void remove(Long id) {
        appUserRepository.deleteById(id);
    }

    @Override
    public Optional<AppUser> findByUsername(String name) {
        return appUserRepository.findByUsername(name);
    }
}
