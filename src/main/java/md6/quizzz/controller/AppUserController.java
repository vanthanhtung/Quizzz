package md6.quizzz.controller;

import md6.quizzz.model.AppUser;
import md6.quizzz.model.Exam;
import md6.quizzz.service.appUserService.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping()
    public ResponseEntity<Iterable<AppUser>> findAll(){
        return new ResponseEntity<>(appUserService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AppUser>> findById(@PathVariable Long id){
        Optional<AppUser> appUser = appUserService.findById(id);
        if (!appUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appUser, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<AppUser> save(@RequestBody AppUser appUser) {
        appUserService.save(appUser);
        return new ResponseEntity<>(appUser, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AppUser> edit(@PathVariable Long id, @RequestBody AppUser appUser){
        Optional<AppUser> currentAppUser = appUserService.findById(id);
        if (!currentAppUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        currentAppUser.get().setAddress(appUser.getAddress());
        currentAppUser.get().setAge(appUser.getAge());
        currentAppUser.get().setEmail(appUser.getEmail());
        currentAppUser.get().setImage(appUser.getImage());
        currentAppUser.get().setPassword(appUser.getPassword());
        currentAppUser.get().setRoles(appUser.getRoles());
        currentAppUser.get().setUsername(appUser.getUsername());
        appUserService.save(currentAppUser.get());
        return new ResponseEntity<>(currentAppUser.get(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AppUser> delete(@PathVariable Long id){
        appUserService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
