package pb.wi.mmw.e_sejm.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pb.wi.mmw.e_sejm.dto.UserDto;
import pb.wi.mmw.e_sejm.dto.request.UserEditRequest;
import pb.wi.mmw.e_sejm.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class UserAdminController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserDto> editUser(@PathVariable Long id, @RequestBody UserEditRequest userDto){
        return new ResponseEntity<>(userService.editUser(id,userDto), HttpStatus.OK);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>("User deletion successful", HttpStatus.OK);
    }

}
