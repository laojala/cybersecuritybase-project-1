/*
 * Course project for https://cybersecuritybase.github.io/
 * Implementation contains security flaws
 */

package sec.project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;


@Controller
public class SignupController {
    
    @Autowired
    private SignupRepository signupRepository;

    CustomDbHelpers db_injection = new CustomDbHelpers();
    
    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm(Authentication authentication) {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String message, Model model) {
        db_injection.addSignupToDb(name, message);
        //signupRepository.save(new Signup(name, message, null));
        return "done";
    }
    
    @RequestMapping(value = "/admin/admin", method = RequestMethod.GET)
    public String loadAdmin(Model model) { 
        List<Signup> allSignups = db_injection.readSignupsFromDb();
        //List<Signup> allSignups = signupRepository.findAll();
        model.addAttribute("signups", allSignups);
        return "admin/admin";
    }

    
}
