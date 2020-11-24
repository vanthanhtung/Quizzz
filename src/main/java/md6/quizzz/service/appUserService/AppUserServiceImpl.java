package md6.quizzz.service.appUserService;

import md6.quizzz.model.App_User;
import md6.quizzz.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public Iterable<App_User> findAll() {
        return appUserRepository.findAll();
    }

    @Override
    public Optional<App_User> findById(Long id) {
        return appUserRepository.findById(id);
    }

    @Override
    public App_User save(App_User app_user) {
        return appUserRepository.save(app_user);
    }

    @Override
    public void remove(Long id) {
        appUserRepository.deleteById(id);
    }
}
