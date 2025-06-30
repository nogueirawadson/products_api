package api_products.controllers.userController;

import api_products.DTOS.userDTOs.CreateUserDto;
import api_products.DTOS.userDTOs.LoginUserDto;
import api_products.DTOS.RecoveryJwtTokenDto;
import api_products.models.User;
import api_products.repository.userRepository;
import api_products.service.user.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
   private userService userService;
    @Autowired
   private userRepository repository;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll(){
        List<User> users = new ArrayList<>();
        users = repository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com Sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/customer")
    public ResponseEntity<String> geCustomertAuthenticationTest(){
return new ResponseEntity<>("Autenticado com Sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/admin")
    public ResponseEntity<String> geAdminAuthenticationTest(){
        return new ResponseEntity<>("Autenticado com Sucesso", HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<User> update(@RequestBody CreateUserDto createUserDto, @PathVariable Integer id
                                       ) {
        User userupadate = userService.update(createUserDto, id);
        return new ResponseEntity<User>(userupadate,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> delete(User user ) {
        this.userService.delete(user.getId());
        return new ResponseEntity<User>(HttpStatus.OK);

    }
}
