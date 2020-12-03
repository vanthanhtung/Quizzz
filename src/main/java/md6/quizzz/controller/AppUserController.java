package md6.quizzz.controller;

import md6.quizzz.model.AppUser;
import md6.quizzz.model.ERole;
import md6.quizzz.model.Exam;
import md6.quizzz.model.UserRole;
import md6.quizzz.repository.UserRoleRepository;
import md6.quizzz.service.UserDetailsImpl;
import md6.quizzz.service.appUserService.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/users")

public class AppUserController {

    final long idRoleAdmin =1;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    private AppUserService appUserService;

    @Secured({"ROLE_ADMIN"})
    @GetMapping()
    public ResponseEntity<Iterable<AppUser>> findAll(){
        return new ResponseEntity<>(appUserService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Optional<AppUser>> findById(@PathVariable Long id){
        Optional<AppUser> appUser = appUserService.findById(id);
        if (!appUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appUser, HttpStatus.OK);
    }

    @PostMapping()
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<AppUser> save(@RequestBody AppUser appUser) {
        appUserService.save(appUser);
        return new ResponseEntity<>(appUser, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @Secured({"ROLE_USER"})
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
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<AppUser> delete(@PathVariable Long id){
        appUserService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/changeRole/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppUser> changeRole(@PathVariable Long id){
        Optional<AppUser> currentAppUser = appUserService.findById(id);
        if(!currentAppUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Set<UserRole> roles = new HashSet<>();
        roles.add(userRoleRepository.findById(idRoleAdmin).get());
        currentAppUser.get().setRoles(roles);
        appUserService.save(currentAppUser.get());
        return new ResponseEntity<>(currentAppUser.get(), HttpStatus.OK);
    }

    @PutMapping("/changePassword")
    @Secured({"ROLE_USER"})
    public ResponseEntity<AppUser> changePassword(@RequestBody AppUser user){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<AppUser> currentUser = appUserService.findById(userDetails.getId());
        if (!currentUser.isPresent()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        currentUser.get().setPassword(user.getPassword());
        appUserService.save(currentUser.get());
        return new ResponseEntity<>(currentUser.get(), HttpStatus.OK);
    }

    @GetMapping("/profile")
    @Secured({"ROLE_USER"})
    public ResponseEntity<AppUser> getUser(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<AppUser> currentUser = appUserService.findById(userDetails.getId());
        return currentUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
