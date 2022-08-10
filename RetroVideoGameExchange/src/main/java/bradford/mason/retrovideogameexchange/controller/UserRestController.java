package bradford.mason.retrovideogameexchange.controller;


import bradford.mason.retrovideogameexchange.model.User;
import bradford.mason.retrovideogameexchange.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/users")
public class UserRestController {

    public String getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        return dtf.format(now);
    }

    @Autowired
    private UserRepo users;


    @Autowired
    PasswordEncoder passwordEncoder;



    @PostMapping(path = "/newUser")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        users.save(user);
    }


    @GetMapping("/resetPass/{username}")
    public String resetPassword(@PathVariable String username,@RequestBody Map<String, String> map) {
        Random random = new Random();
        User user = users.getById(username);

        String email = map.get("email");
        String userEmail = user.getEmail();
        if(!email.equals(userEmail)) {
            return "Please provide correct email connected to the username";
        }


        String lets = "abcdefghijklmnopqrstuvwxyz";
        int[] ints = {1,2,3,4,5,6,7,8,9,0};
        char[] chars = lets.toCharArray();

        int length = random.nextInt(20,25);
        String tempPass = "";

        for(int i = 0; i <= length; i++) {
            int letters = random.nextInt(26);
            int numbers = random.nextInt(10);
            int numOrLetter = random.nextInt(2);
            int upperOrLower = random.nextInt(2);

            if(numOrLetter == 0) {
                if(upperOrLower == 1) {
                    tempPass = tempPass + String.valueOf(chars[letters]).toUpperCase();
                }
                else {
                    tempPass = tempPass + chars[letters];
                }
            }
            if(numOrLetter == 1) {
                tempPass = tempPass + ints[numbers];
            }

        }
        user.setPassword(passwordEncoder.encode(tempPass));
        users.save(user);

        //        TODO
//        LET USER KNOW PASSWORD HAS BEEN RESET
        return "Temporary password sent to your email\n This password will only last for 3 hours from " + getTime() + ",  "+tempPass;
    }

    @PatchMapping(path = "/changePass")
    public String changePass(@AuthenticationPrincipal User user, @RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        String newPassword = map.get("newPassword");
        if(!username.equals(user.getUsername()) ||
                !passwordEncoder.matches(password, user.getPassword())) {
            return "Incorrect information";
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        users.save(user);

//        TODO
//        LET USER KNOW PASSWORD HAS BEEN CHANGED
        return "Password has been changed!";
    }
}
