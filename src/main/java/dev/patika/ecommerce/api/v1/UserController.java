package dev.patika.ecommerce.api.v1;

import dev.patika.ecommerce.business.abstracts.IUserService;
import dev.patika.ecommerce.core.config.modelMapper.IModelMapperService;
import dev.patika.ecommerce.core.result.ResultData;
import dev.patika.ecommerce.core.utilities.JwtUtils;
import dev.patika.ecommerce.core.utilities.Msg;
import dev.patika.ecommerce.core.utilities.ResultHelper;
import dev.patika.ecommerce.dao.UserRepository;
import dev.patika.ecommerce.dto.request.login.LoginRequest;
import dev.patika.ecommerce.dto.request.login.LoginResponse;
import dev.patika.ecommerce.dto.response.supplier.SupplierResponse;
import dev.patika.ecommerce.dto.response.user.UserResponse;
import dev.patika.ecommerce.entities.Supplier;
import dev.patika.ecommerce.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users/")
public class UserController {
    private IUserService userService;
    private final IModelMapperService modelMapperService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserController(IUserService userService, IModelMapperService modelMapperService) {
        this.userService = userService;
        this.modelMapperService = modelMapperService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<UserResponse> get(@PathVariable int id) {
        User user = this.userService.get(id);
        return ResultHelper.success(this.modelMapperService.forResponse().map(user, UserResponse.class));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getuserpassword")
    @ResponseStatus(HttpStatus.OK)
    public String userEndpoint(){
        return "This is your password.";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getadminpassword")
    @ResponseStatus(HttpStatus.OK)
    public String adminEndpoint(){
        return "This is your admin password.";
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtToken);

        return ResponseEntity.ok(response);
    }
}
