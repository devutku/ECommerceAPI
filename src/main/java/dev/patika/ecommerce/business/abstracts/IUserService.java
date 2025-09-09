package dev.patika.ecommerce.business.abstracts;

import dev.patika.ecommerce.entities.Supplier;
import dev.patika.ecommerce.entities.User;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IUserService {
    User save(User user);
    User get(int id);
    Page<User> cursor (int page, int pageSize);
    User update(User user);
    boolean delete(int id);
}
