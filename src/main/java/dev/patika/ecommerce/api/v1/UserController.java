package dev.patika.ecommerce.api.v1;

import dev.patika.ecommerce.business.abstracts.IUserService;
import dev.patika.ecommerce.core.config.modelMapper.IModelMapperService;
import dev.patika.ecommerce.core.result.ResultData;
import dev.patika.ecommerce.core.utilities.Msg;
import dev.patika.ecommerce.core.utilities.ResultHelper;
import dev.patika.ecommerce.dao.UserRepository;
import dev.patika.ecommerce.dto.response.supplier.SupplierResponse;
import dev.patika.ecommerce.dto.response.user.UserResponse;
import dev.patika.ecommerce.entities.Supplier;
import dev.patika.ecommerce.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users/")
public class UserController {
    private IUserService userService;
    private final IModelMapperService modelMapperService;
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
}
